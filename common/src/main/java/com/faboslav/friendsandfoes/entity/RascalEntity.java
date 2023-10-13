package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.animation.KeyframeAnimation;
import com.faboslav.friendsandfoes.client.render.entity.animation.RascalAnimations;
import com.faboslav.friendsandfoes.client.render.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.entity.ai.brain.RascalBrain;
import com.faboslav.friendsandfoes.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.entity.pose.RascalEntityPose;
import com.faboslav.friendsandfoes.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.StructureTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.StructureAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class RascalEntity extends PassiveEntity implements AnimatedEntity
{
	private AnimationContextTracker animationContextTracker;
	private static final TrackedData<Integer> POSE_TICKS;
	private static final TrackedData<Integer> CAUGHT_COUNT;
	private boolean ambientSounds;

	public RascalEntity(EntityType<? extends PassiveEntity> entityType, World world) {
		super(entityType, world);
		this.enableAmbientSounds();
		this.setPathfindingPenalty(PathNodeType.RAIL, 0.0F);
		this.setPathfindingPenalty(PathNodeType.UNPASSABLE_RAIL, 0.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 0.0F);
	}

	@Override
	public EntityData initialize(
		ServerWorldAccess world,
		LocalDifficulty difficulty,
		SpawnReason spawnReason,
		@Nullable EntityData entityData,
		@Nullable NbtCompound entityNbt
	) {
		EntityData superEntityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);

		this.setPose(RascalEntityPose.DEFAULT);
		RascalBrain.setNodCooldown(this);

		return superEntityData;
	}

	public static boolean canSpawn(
		EntityType<? extends MobEntity> rascalEntityType,
		ServerWorldAccess serverWorldAccess,
		SpawnReason spawnReason,
		BlockPos blockPos,
		Random random
	) {
		if (spawnReason == SpawnReason.NATURAL) {
			ServerWorld serverWorld = serverWorldAccess.toServerWorld();
			StructureAccessor structureAccessor = serverWorld.getStructureAccessor();

			if (structureAccessor.getStructureContaining(
				blockPos,
				StructureTags.MINESHAFT
			).hasChildren() == false) {
				return false;
			}

			List<RascalEntity> nearbyRascals = serverWorld.getEntitiesByClass(RascalEntity.class, new Box(blockPos).expand(32.0F), (rascalEntity) -> true);

			if (nearbyRascals.isEmpty() == false) {
				return false;
			}

			return blockPos.getY() < 63 && serverWorldAccess.isSkyVisible(blockPos) == false;
		}

		return false;
	}

	@Override
	public AnimationContextTracker getAnimationContextTracker() {
		if (this.animationContextTracker == null) {
			this.animationContextTracker = new AnimationContextTracker();

			for (KeyframeAnimation keyframeAnimation : this.getAnimations()) {
				this.animationContextTracker.add(keyframeAnimation);
			}
		}

		return this.animationContextTracker;
	}

	@Override
	public ArrayList<KeyframeAnimation> getAnimations() {
		return RascalAnimations.ANIMATIONS;
	}

	@Override
	public int getKeyframeAnimationTicks() {
		return this.dataTracker.get(POSE_TICKS);
	}

	public void setKeyframeAnimationTicks(int keyframeAnimationTicks) {
		this.dataTracker.set(POSE_TICKS, keyframeAnimationTicks);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(POSE_TICKS, 0);
		this.dataTracker.startTracking(CAUGHT_COUNT, 0);
	}

	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return null;
	}

	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
		return RascalBrain.create(dynamic);
	}

	@Override
	@SuppressWarnings("all")
	public Brain<RascalEntity> getBrain() {
		return (Brain<RascalEntity>) super.getBrain();
	}

	@Override
	protected void mobTick() {
		this.getWorld().getProfiler().push("rascalBrain");
		this.getBrain().tick((ServerWorld) this.getWorld(), this);
		this.getWorld().getProfiler().pop();
		this.getWorld().getProfiler().push("rascalActivityUpdate");
		RascalBrain.updateActivities(this);
		this.getWorld().getProfiler().pop();

		super.mobTick();
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.55D)
			.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	public void tick() {
		if (FriendsAndFoes.getConfig().enableRascal == false) {
			this.discard();
		}

		StatusEffectInstance invisibilityStatusEffect = this.getStatusEffect(StatusEffects.INVISIBILITY);

		if (this.isHidden() && invisibilityStatusEffect != null && invisibilityStatusEffect.getDuration() == 1) {
			this.playReappearSound();
		}

		if (this.getWorld().isClient() == false && this.isAnyKeyframeAnimationRunning()) {
			this.setKeyframeAnimationTicks(this.getKeyframeAnimationTicks() - 1);
		}

		KeyframeAnimation keyframeAnimationToStart = this.getKeyframeAnimationByPose();

		if (
			keyframeAnimationToStart != null
			&& this.isKeyframeAnimationRunning(keyframeAnimationToStart) == false
		) {
			if (this.getWorld().isClient() == false) {
				this.setKeyframeAnimationTicks(keyframeAnimationToStart.getAnimationLengthInTicks());
			}

			this.startKeyframeAnimation(keyframeAnimationToStart);
		}

		super.tick();
	}

	@Nullable
	private KeyframeAnimation getKeyframeAnimationByPose() {
		KeyframeAnimation keyframeAnimation = null;

		if (this.isInPose(RascalEntityPose.DEFAULT)) {
			keyframeAnimation = RascalAnimations.DEFAULT;
		} else if (this.isInPose(RascalEntityPose.NOD)) {
			keyframeAnimation = RascalAnimations.NOD;
		} else if (this.isInPose(RascalEntityPose.GIVE_REWARD)) {
			keyframeAnimation = RascalAnimations.GIVE_REWARD;
		}

		return keyframeAnimation;
	}

	private void startKeyframeAnimation(KeyframeAnimation keyframeAnimationToStart) {
		for (KeyframeAnimation keyframeAnimation : RascalAnimations.ANIMATIONS) {
			if (keyframeAnimation == keyframeAnimationToStart) {
				continue;
			}

			this.stopKeyframeAnimation(keyframeAnimation);
		}

		this.startKeyframeAnimation(keyframeAnimationToStart, this.age);
	}


	@Override
	public void setPose(EntityPose pose) {
		if (this.getWorld().isClient()) {
			return;
		}

		super.setPose(pose);
	}

	public void setPose(RascalEntityPose pose) {
		if (this.getWorld().isClient()) {
			return;
		}

		super.setPose(pose.get());
	}

	public boolean isInPose(RascalEntityPose pose) {
		return this.getPose() == pose.get();
	}

	public void startNodAnimation() {
		if (this.isInPose(RascalEntityPose.NOD)) {
			return;
		}

		this.playNodSound();
		this.setPose(RascalEntityPose.NOD);
	}

	public void startGiveRewardAnimation() {
		if (this.isInPose(RascalEntityPose.GIVE_REWARD)) {
			return;
		}

		this.playRewardSound();
		this.setPose(RascalEntityPose.GIVE_REWARD);
	}

	@Override
	public boolean damage(
		DamageSource source, float amount
	) {
		Entity attacker = source.getAttacker();

		if (
			attacker == null
			|| attacker instanceof PlayerEntity == false
		) {
			return super.damage(source, amount);
		}

		this.playHurtSound(source);
		this.playDisappearSound();
		this.spawnCloudParticles();
		this.discard();

		return false;
	}

	public SoundEvent getNodSound() {
		return FriendsAndFoesSoundEvents.ENTITY_RASCAL_NOD.get();
	}

	public void playNodSound() {
		this.playSound(this.getNodSound(), 1.0F, RandomGenerator.generateFloat(1.15F, 1.3F));
	}

	public SoundEvent getRewardSound() {
		return FriendsAndFoesSoundEvents.ENTITY_RASCAL_REWARD.get();
	}

	public void playRewardSound() {
		this.playSound(this.getRewardSound(), 1.0F, RandomGenerator.generateFloat(1.15F, 1.3F));
	}

	public SoundEvent getBadRewardSound() {
		return FriendsAndFoesSoundEvents.ENTITY_RASCAL_REWARD_BAD.get();
	}

	public void playBadRewardSound() {
		this.playSound(this.getBadRewardSound(), 1.0F, RandomGenerator.generateFloat(1.15F, 1.3F));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return FriendsAndFoesSoundEvents.ENTITY_RASCAL_AMBIENT.get();
	}

	@Override
	public void playAmbientSound() {
		if (this.isHidden() || this.ambientSounds == false) {
			return;
		}

		SoundEvent soundEvent = this.getAmbientSound();
		this.playSound(soundEvent, 1.5F, RandomGenerator.generateFloat(1.15F, 1.3F));
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return FriendsAndFoesSoundEvents.ENTITY_RASCAL_HURT.get();
	}

	@Override
	protected void playHurtSound(DamageSource source) {
		this.ambientSoundChance = -this.getMinAmbientSoundDelay();
		this.playSound(this.getHurtSound(source), 1.0F, RandomGenerator.generateFloat(1.15F, 1.3F));
	}

	public SoundEvent getDisappearSound() {
		return FriendsAndFoesSoundEvents.ENTITY_RASCAL_DISAPPEAR.get();
	}

	public void playDisappearSound() {
		SoundEvent soundEvent = this.getDisappearSound();
		this.playSound(soundEvent, 1.5F, RandomGenerator.generateFloat(1.5F, 1.6F));
	}

	public SoundEvent getReappearSound() {
		return FriendsAndFoesSoundEvents.ENTITY_RASCAL_REAPPEAR.get();
	}

	public void playReappearSound() {
		SoundEvent soundEvent = this.getReappearSound();
		this.playSound(soundEvent, 1.5F, RandomGenerator.generateFloat(1.5F, 1.6F));
	}

	@Override
	protected void playStepSound(
		BlockPos pos,
		BlockState state
	) {
		if (
			this.isHidden()
			|| state.getMaterial().isLiquid()
		) {
			return;
		}

		super.playStepSound(pos, state);
	}

	public boolean isHidden() {
		return this.getBrain().getOptionalMemory(MemoryModuleType.AVOID_TARGET).orElse(null) instanceof PlayerEntity;
	}

	public int getCaughtCount() {
		return this.dataTracker.get(CAUGHT_COUNT);
	}

	public void addToCaughtCount() {
		this.dataTracker.set(CAUGHT_COUNT, this.getCaughtCount() + 1);
	}

	public boolean shouldGiveReward() {
		return this.getCaughtCount() >= 3;
	}

	public boolean disableAmbientSounds() {
		return this.ambientSounds = false;
	}

	public boolean enableAmbientSounds() {
		return this.ambientSounds = true;
	}

	public void spawnCloudParticles() {
		this.spawnParticles(ParticleTypes.CLOUD, 16, 0.1D);
	}

	public void spawnParticles(
		ParticleEffect particleEffect,
		int amount,
		double speed
	) {
		World world = this.getWorld();

		if (world.isClient()) {
			return;
		}

		for (int i = 0; i < amount; i++) {
			((ServerWorld) world).spawnParticles(
				particleEffect,
				this.getParticleX(1.0D),
				this.getRandomBodyY() + 0.5D,
				this.getParticleZ(1.0D),
				1,
				this.getRandom().nextGaussian() * 0.02D,
				this.getRandom().nextGaussian() * 0.02D,
				this.getRandom().nextGaussian() * 0.02D,
				speed
			);
		}
	}

	static {
		POSE_TICKS = DataTracker.registerData(RascalEntity.class, TrackedDataHandlerRegistry.INTEGER);
		CAUGHT_COUNT = DataTracker.registerData(RascalEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}
}
