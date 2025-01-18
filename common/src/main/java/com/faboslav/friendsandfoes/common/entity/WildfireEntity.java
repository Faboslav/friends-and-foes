package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.WildfireAnimations;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.ai.brain.WildfireBrain;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.pose.WildfireEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public final class WildfireEntity extends Monster implements AnimatedEntity
{
	private AnimationContextTracker animationContextTracker;
	private float damageAmountCounter = 0.0F;

	public static final float GENERIC_ATTACK_DAMAGE = 8.0F;
	public static final float GENERIC_FOLLOW_RANGE = 32.0F;

	public static final int DEFAULT_ACTIVE_SHIELDS_COUNT = 4;
	public static final int DEFAULT_TICKS_UNTIL_SHIELD_REGENERATION = 300;
	public static final int DEFAULT_SUMMONED_BLAZES_COUNT = 0;

	public static final int MAXIMUM_SUMMONED_BLAZES_COUNT = 2;

	private static final String ACTIVE_SHIELDS_NBT_NAME = "ActiveShieldsCount";
	private static final String TICKS_UNTIL_SHIELD_REGENERATION_NBT_NAME = "TicksUntilShieldRegeneration";
	private static final String SUMMONED_BLAZES_COUNT_NBT_NAME = "SummonedBlazesCount";

	private static final EntityDataAccessor<Integer> ACTIVE_SHIELDS_COUNT;
	private static final EntityDataAccessor<Integer> TICKS_UNTIL_SHIELD_REGENERATION;
	private static final EntityDataAccessor<Integer> SUMMONED_BLAZES_COUNT;
	private static final EntityDataAccessor<Integer> POSE_TICKS;

	public WildfireEntity(EntityType<? extends WildfireEntity> entityType, Level world) {
		super(entityType, world);
		this.setPathfindingMalus(PathType.WATER, -1.0F);
		this.setPathfindingMalus(PathType.LAVA, 8.0F);
		this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0F);
		this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0.0F);
		this.xpReward = 10;
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(
		ServerLevelAccessor world,
		DifficultyInstance difficulty,
		MobSpawnType spawnReason,
		@Nullable SpawnGroupData entityData
	) {
		this.setPose(WildfireEntityPose.IDLE);
		this.setActiveShieldsCount(DEFAULT_ACTIVE_SHIELDS_COUNT);
		this.setSummonedBlazesCount(DEFAULT_SUMMONED_BLAZES_COUNT);
		return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
	}

	@Override
	public AnimationContextTracker getAnimationContextTracker() {
		if (this.animationContextTracker == null) {
			this.animationContextTracker = new AnimationContextTracker();

			for (var animation: this.getTrackedAnimations()) {
				this.animationContextTracker.add(animation);
			}

			this.animationContextTracker.add(WildfireAnimations.SHIELD_ROTATION);
		}

		return this.animationContextTracker;
	}

	@Override
	public ArrayList<AnimationHolder> getTrackedAnimations() {
		return WildfireAnimations.TRACKED_ANIMATIONS;
	}

	@Override
	public AnimationHolder getMovementAnimation() {
		return WildfireAnimations.WALK;
	}

	@Override
	public int getCurrentAnimationTick() {
		return this.entityData.get(POSE_TICKS);
	}

	public void setCurrentAnimationTick(int keyframeAnimationTicks) {
		this.entityData.set(POSE_TICKS, keyframeAnimationTicks);
	}

	@Override
	protected Brain<?> makeBrain(Dynamic<?> dynamic) {
		return WildfireBrain.create(dynamic);
	}

	@Override
	@SuppressWarnings("all")
	public Brain<WildfireEntity> getBrain() {
		return (Brain<WildfireEntity>) super.getBrain();
	}

	@Override
	protected void sendDebugPackets() {
		super.sendDebugPackets();
		DebugPackets.sendEntityBrain(this);
	}

	@Override
	protected void customServerAiStep() {
		var profiler = this.level().getProfiler();

		profiler.push("wildfireBrain");
		this.getBrain().tick((ServerLevel) this.level(), this);
		profiler.pop();

		profiler.push("wildfireActivityUpdate");
		WildfireBrain.updateActivities(this);
		profiler.pop();

		super.customServerAiStep();
	}

	public static AttributeSupplier.Builder createWildfireAttributes() {
		return Monster.createMonsterAttributes()
			.add(Attributes.MAX_HEALTH, 120.0F)
			.add(Attributes.ATTACK_DAMAGE, 8.0F)
			.add(Attributes.ATTACK_KNOCKBACK, 32.0F)
			.add(Attributes.MOVEMENT_SPEED, 0.23000000417232513)
			.add(Attributes.FOLLOW_RANGE, GENERIC_FOLLOW_RANGE)
			.add(Attributes.KNOCKBACK_RESISTANCE, 1.0F);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);

		builder.define(ACTIVE_SHIELDS_COUNT, DEFAULT_ACTIVE_SHIELDS_COUNT);
		builder.define(TICKS_UNTIL_SHIELD_REGENERATION, DEFAULT_TICKS_UNTIL_SHIELD_REGENERATION);
		builder.define(SUMMONED_BLAZES_COUNT, DEFAULT_SUMMONED_BLAZES_COUNT);
		builder.define(POSE_TICKS, 0);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putInt(ACTIVE_SHIELDS_NBT_NAME, this.getActiveShieldsCount());
		nbt.putInt(TICKS_UNTIL_SHIELD_REGENERATION_NBT_NAME, this.getTicksUntilShieldRegeneration());
		nbt.putInt(SUMMONED_BLAZES_COUNT_NBT_NAME, this.getSummonedBlazesCount());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);
		this.setActiveShieldsCount(nbt.getInt(ACTIVE_SHIELDS_NBT_NAME));
		this.setTicksUntilShieldRegeneration(nbt.getInt(TICKS_UNTIL_SHIELD_REGENERATION_NBT_NAME));
		this.setSummonedBlazesCount(nbt.getInt(SUMMONED_BLAZES_COUNT_NBT_NAME));
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		if (state.liquid()) {
			return;
		}

		BlockState blockState = this.level().getBlockState(pos.above());
		SoundType blockSoundGroup = blockState.is(BlockTags.INSIDE_STEP_SOUND_BLOCKS) ? blockState.getSoundType():state.getSoundType();
		this.playSound(FriendsAndFoesSoundEvents.ENTITY_WILDFIRE_STEP.get(), blockSoundGroup.getVolume() * 0.15F, blockSoundGroup.getPitch());


	}

	public SoundEvent getShootSound() {
		return FriendsAndFoesSoundEvents.ENTITY_WILDFIRE_SHOOT.get();
	}

	public void playShootSound() {
		this.playSound(this.getShootSound(), this.getSoundVolume(), this.getVoicePitch());
	}

	public SoundEvent getShockwaveSound() {
		return FriendsAndFoesSoundEvents.ENTITY_WILDFIRE_SHOCKWAVE.get();
	}

	public void playShockwaveSound() {
		this.playSound(this.getShockwaveSound(), this.getSoundVolume(), this.getVoicePitch());
	}

	public void breakShield() {
		this.setActiveShieldsCount(this.getActiveShieldsCount() - 1);
	}

	public void regenerateShield() {
		if (this.getActiveShieldsCount() >= DEFAULT_ACTIVE_SHIELDS_COUNT) {
			return;
		}

		this.setActiveShieldsCount(this.getActiveShieldsCount() + 1);
	}

	public int getActiveShieldsCount() {
		return this.entityData.get(ACTIVE_SHIELDS_COUNT);
	}

	public void setActiveShieldsCount(int activeShields) {
		this.entityData.set(ACTIVE_SHIELDS_COUNT, activeShields);
	}

	public boolean hasActiveShields() {
		return this.getActiveShieldsCount() > 0;
	}

	public int getTicksUntilShieldRegeneration() {
		return this.entityData.get(TICKS_UNTIL_SHIELD_REGENERATION);
	}

	public void setTicksUntilShieldRegeneration(int ticksUntilShieldRegeneration) {
		this.entityData.set(TICKS_UNTIL_SHIELD_REGENERATION, ticksUntilShieldRegeneration);
	}

	public void resetTicksUntilShieldRegeneration() {
		this.setTicksUntilShieldRegeneration(DEFAULT_TICKS_UNTIL_SHIELD_REGENERATION);
	}

	public int getSummonedBlazesCount() {
		return this.entityData.get(SUMMONED_BLAZES_COUNT);
	}

	public void setSummonedBlazesCount(int summonedBlazesCount) {
		this.entityData.set(SUMMONED_BLAZES_COUNT, summonedBlazesCount);
	}

	public boolean areBlazesSummoned() {
		return this.getSummonedBlazesCount() > 0;
	}

	public SoundEvent getShieldBreakSound() {
		return FriendsAndFoesSoundEvents.ENTITY_WILDFIRE_SHIELD_BREAK.get();
	}

	public void playShieldBreakSound() {
		this.playSound(this.getShieldBreakSound(), this.getSoundVolume(), this.getVoicePitch());
	}

	protected SoundEvent getAmbientSound() {
		return FriendsAndFoesSoundEvents.ENTITY_WILDFIRE_AMBIENT.get();
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return FriendsAndFoesSoundEvents.ENTITY_WILDFIRE_HURT.get();
	}

	protected SoundEvent getDeathSound() {
		return FriendsAndFoesSoundEvents.ENTITY_WILDFIRE_DEATH.get();
	}

	public SoundEvent getSummonBlazeSound() {
		return FriendsAndFoesSoundEvents.ENTITY_WILDFIRE_SUMMON_BLAZE.get();
	}

	public void playSummonBlazeSound() {
		this.playSound(this.getSummonBlazeSound(), this.getSoundVolume(), this.getVoicePitch());
	}

	@Override
	public void tick() {
		if (!FriendsAndFoes.getConfig().enableWildfire) {
			this.discard();
		}

		if(this.isMoving()) {
			this.emitGroundParticles(20);
		} else {
			this.emitGroundParticles(1 + this.getRandom().nextInt(1));
		}

		super.tick();
		this.updateKeyframeAnimations();

		this.setTicksUntilShieldRegeneration(this.getTicksUntilShieldRegeneration() - 1);

		if (this.getTicksUntilShieldRegeneration() == 0) {
			this.regenerateShield();
			this.resetTicksUntilShieldRegeneration();
		}
	}

	private void updateKeyframeAnimations() {
		if (!this.level().isClientSide()) {
			this.updateCurrentAnimationTick();
		}

		AnimationHolder animationToStart = this.getAnimationByPose();

		if (animationToStart != null) {
			this.tryToStartAnimation(animationToStart);
		}
	}

	@Nullable
	public AnimationHolder getAnimationByPose() {
		AnimationHolder animation = null;

		if (this.isInPose(WildfireEntityPose.IDLE) && !this.isMoving()) {
			animation = WildfireAnimations.IDLE;
		} else if (this.isInPose(WildfireEntityPose.SHOCKWAVE)) {
			animation = WildfireAnimations.SHOCKWAVE;
		}

		return animation;
	}

	private void tryToStartAnimation(AnimationHolder animationToStart) {
		if (this.isKeyframeAnimationRunning(animationToStart)) {
			return;
		}

		if (!this.level().isClientSide()) {
			this.setCurrentAnimationTick(animationToStart.get().lengthInTicks());
		}

		this.startKeyframeAnimation(animationToStart);
	}

	private void startKeyframeAnimation(AnimationHolder animationToStart) {
		for (var animation : this.getTrackedAnimations()) {
			if (animation == animationToStart) {
				continue;
			}

			this.stopKeyframeAnimation(animation);
		}

		this.startKeyframeAnimation(animationToStart, this.tickCount);
	}

	public void startShockwaveAnimation() {
		if (this.isInPose(WildfireEntityPose.SHOCKWAVE)) {
			return;
		}

		this.gameEvent(GameEvent.ENTITY_ACTION);
		this.playShockwaveSound();
		this.setPose(WildfireEntityPose.SHOCKWAVE);
	}

	@Override
	public void setPose(Pose pose) {
		if (this.level().isClientSide()) {
			return;
		}

		super.setPose(pose);
	}

	public void setPose(WildfireEntityPose pose) {
		if (this.level().isClientSide()) {
			return;
		}

		super.setPose(pose.get());
	}

	public boolean isInPose(WildfireEntityPose pose) {
		return this.getPose() == pose.get();
	}

	public boolean isMoving() {
		return this.onGround() && this.getDeltaMovement().lengthSqr() >= 0.0001;
	}

	@Override
	public void aiStep() {
		if (!this.onGround() && this.getDeltaMovement().y < 0.0F) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(1.0F, 0.6F, 1.0F));
		}

		if (this.level().isClientSide()) {
			if (this.getRandom().nextInt(24) == 0 && !this.isSilent()) {
				// this.level().playLocalSound(this.getX() + 0.5, this.getY() + 0.5, this.getZ() + 0.5, SoundEvents.BLAZE_BURN, this.getSoundSource(), 1.0F + this.getRandom().nextFloat(), this.getRandom().nextFloat() * 0.7F + 0.3F, false);
			}

			for (int i = 0; i < 2; ++i) {
				this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
			}
		}

		super.aiStep();
	}

	public void emitGroundParticles(int i) {
		if (!this.isPassenger()) {
			Vec3 vec3 = this.getBoundingBox().getCenter();
			Vec3 vec32 = new Vec3(vec3.x, this.position().y, vec3.z);
			BlockState blockState = !this.getInBlockState().isAir() ? this.getInBlockState() : this.getBlockStateOn();

			if (blockState.getRenderShape() != RenderShape.INVISIBLE) {
				for(int j = 0; j < i; ++j) {
					this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockState), vec32.x, vec32.y, vec32.z, 0.0, 0.0, 0.0);
				}
			}
		}
	}

	@Override
	public boolean hurt(
		DamageSource source,
		float amount
	) {
		if (source == this.damageSources().generic() || source == this.damageSources().genericKill()) {
			return super.hurt(source, amount);
		}

		Entity attacker = source.getEntity();

		if (
			source == this.damageSources().inFire()
			|| (attacker != null && attacker.getType().is(FriendsAndFoesTags.WILDFIRE_ALLIES))
		) {
			return false;
		}

		if (this.hasActiveShields()) {
			this.damageAmountCounter += amount;
			float shieldBreakDamageThreshold = (float) this.getAttributeValue(Attributes.MAX_HEALTH) * 0.25F;

			if (this.damageAmountCounter >= shieldBreakDamageThreshold) {
				if (attacker instanceof LivingEntity) {
					attacker.hurt(this.damageSources().mobAttack(this), GENERIC_ATTACK_DAMAGE);
				}

				this.breakShield();
				this.playShieldBreakSound();
				this.damageAmountCounter = 0;
			}

			amount = 0.0F;
		}

		this.resetTicksUntilShieldRegeneration();

		boolean damageResult = super.hurt(source, amount);

		if (damageResult && attacker instanceof LivingEntity) {
			WildfireBrain.onAttacked(this, (LivingEntity) attacker);
		}

		return damageResult;
	}

	@Override
	public float getLightLevelDependentMagicValue() {
		return 1.0F;
	}

	@Override
	public boolean isSensitiveToWater() {
		return true;
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	static {
		ACTIVE_SHIELDS_COUNT = SynchedEntityData.defineId(WildfireEntity.class, EntityDataSerializers.INT);
		TICKS_UNTIL_SHIELD_REGENERATION = SynchedEntityData.defineId(WildfireEntity.class, EntityDataSerializers.INT);
		SUMMONED_BLAZES_COUNT = SynchedEntityData.defineId(WildfireEntity.class, EntityDataSerializers.INT);
		POSE_TICKS = SynchedEntityData.defineId(WildfireEntity.class, EntityDataSerializers.INT);
	}
}

