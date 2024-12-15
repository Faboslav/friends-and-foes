package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.RascalAnimations;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.ai.brain.RascalBrain;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.animation.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.pose.RascalEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.util.RandomGenerator;
import com.faboslav.friendsandfoes.common.util.particle.ParticleSpawner;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public final class RascalEntity extends AgeableMob implements AnimatedEntity
{
	private AnimationContextTracker animationContextTracker;
	private static final EntityDataAccessor<Integer> POSE_TICKS;
	private static final EntityDataAccessor<Integer> CAUGHT_COUNT;
	private boolean ambientSounds;

	public RascalEntity(EntityType<? extends AgeableMob> entityType, Level world) {
		super(entityType, world);
		this.setPose(RascalEntityPose.IDLE);
		this.enableAmbientSounds();
		this.setPathfindingMalus(PathType.RAIL, 0.0F);
		this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0.0F);
		this.setPathfindingMalus(PathType.WATER, 0.0F);
		this.setPathfindingMalus(PathType.WATER_BORDER, 0.0F);
	}

	@Override
	public SpawnGroupData finalizeSpawn(
		ServerLevelAccessor world,
		DifficultyInstance difficulty,
		MobSpawnType spawnReason,
		@Nullable SpawnGroupData entityData
	) {
		SpawnGroupData superEntityData = super.finalizeSpawn(world, difficulty, spawnReason, entityData);

		this.setPose(RascalEntityPose.IDLE);
		RascalBrain.setNodCooldown(this);

		return superEntityData;
	}

	public static boolean canSpawn(
		EntityType<? extends Mob> rascalEntityType,
		ServerLevelAccessor serverWorldAccess,
		MobSpawnType spawnReason,
		BlockPos blockPos,
		RandomSource random
	) {
		if (spawnReason == MobSpawnType.NATURAL) {
			ServerLevel serverWorld = serverWorldAccess.getLevel();
			Registry<Structure> structureRegistry = serverWorldAccess.registryAccess().registryOrThrow(Registries.STRUCTURE);
			StructureManager structureAccessor = serverWorld.structureManager();

			if (
				blockPos.getY() > 63
				|| serverWorldAccess.canSeeSky(blockPos)
				|| serverWorldAccess.getMaxLocalRawBrightness(blockPos, 0) == 0
				|| (
					!serverWorldAccess.getBlockState(blockPos.below()).is(BlockTags.PLANKS)
					&& !serverWorldAccess.getBlockState(blockPos.above()).is(BlockTags.PLANKS)
					&& !serverWorldAccess.getBlockState(blockPos.north()).is(BlockTags.PLANKS)
					&& !serverWorldAccess.getBlockState(blockPos.west()).is(BlockTags.PLANKS)
					&& !serverWorldAccess.getBlockState(blockPos.south()).is(BlockTags.PLANKS)
					&& !serverWorldAccess.getBlockState(blockPos.east()).is(BlockTags.PLANKS)
				)
			) {
				return false;
			}

			for (Holder<Structure> structure : structureRegistry.getOrCreateTag(StructureTags.MINESHAFT)) {
				if (structureAccessor.getStructureAt(blockPos, structure.value()).isValid()) {
					return true;
				}
			}

			return false;
		}

		return true;
	}

	@Override
	public AnimationContextTracker getAnimationContextTracker() {
		if (this.animationContextTracker == null) {
			this.animationContextTracker = new AnimationContextTracker();

			for (var animation: this.getTrackedAnimations()) {
				this.animationContextTracker.add(animation);
			}
		}

		return this.animationContextTracker;
	}

	@Override
	public ArrayList<AnimationHolder> getTrackedAnimations() {
		return RascalAnimations.ANIMATIONS;
	}

	@Override
	public AnimationHolder getMovementAnimation() {
		return RascalAnimations.WALK;
	}

	@Override
	public int getCurrentAnimationTick() {
		return this.entityData.get(POSE_TICKS);
	}

	public void setCurrentAnimationTick(int keyframeAnimationTicks) {
		this.entityData.set(POSE_TICKS, keyframeAnimationTicks);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);

		builder.define(POSE_TICKS, 0);
		builder.define(CAUGHT_COUNT, 0);
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
		return null;
	}

	@Override
	protected Brain<?> makeBrain(Dynamic<?> dynamic) {
		return RascalBrain.create(dynamic);
	}

	@Override
	@SuppressWarnings("all")
	public Brain<RascalEntity> getBrain() {
		return (Brain<RascalEntity>) super.getBrain();
	}

	@Override
	protected void sendDebugPackets() {
		super.sendDebugPackets();
		DebugPackets.sendEntityBrain(this);
	}

	@Override
	protected void customServerAiStep() {
		this.level().getProfiler().push("rascalBrain");
		this.getBrain().tick((ServerLevel) this.level(), this);
		this.level().getProfiler().pop();
		this.level().getProfiler().push("rascalActivityUpdate");
		RascalBrain.updateActivities(this);
		this.level().getProfiler().pop();

		super.customServerAiStep();
	}

	public static AttributeSupplier.Builder createRascalAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 20.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.55D)
			.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	public void tick() {
		if (!FriendsAndFoes.getConfig().enableRascal) {
			this.discard();
		}

		MobEffectInstance invisibilityStatusEffect = this.getEffect(MobEffects.INVISIBILITY);

		if (this.isHidden() && invisibilityStatusEffect != null && invisibilityStatusEffect.getDuration() == 1) {
			this.playReappearSound();
		}

		this.updateKeyframeAnimations();

		super.tick();
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

		if (this.isInPose(RascalEntityPose.IDLE) && !this.isMoving()) {
			animation = RascalAnimations.IDLE;
		} else if (this.isInPose(RascalEntityPose.NOD)) {
			animation = RascalAnimations.NOD;
		} else if (this.isInPose(RascalEntityPose.GIVE_REWARD)) {
			animation = RascalAnimations.GIVE_REWARD;
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

	@Override
	public void setPose(Pose pose) {
		if (this.level().isClientSide()) {
			return;
		}

		super.setPose(pose);
	}

	public void setPose(RascalEntityPose pose) {
		if (this.level().isClientSide()) {
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
		this.gameEvent(GameEvent.ENTITY_ACTION);
		this.setPose(RascalEntityPose.NOD);
	}

	public void startGiveRewardAnimation() {
		if (this.isInPose(RascalEntityPose.GIVE_REWARD)) {
			return;
		}

		this.playRewardSound();
		this.gameEvent(GameEvent.ENTITY_ACTION);
		this.setPose(RascalEntityPose.GIVE_REWARD);
	}

	@Override
	public boolean hurt(
		DamageSource source, float amount
	) {
		Entity attacker = source.getEntity();

		if (
			!(attacker instanceof Player)
			|| this.hasCustomName()
		) {
			return super.hurt(source, amount);
		}

		this.playHurtSound(source);
		this.playDisappearSound();
		this.spawnCloudParticles();
		this.spawnAngerParticles();
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
		if (this.isHidden() || !this.ambientSounds) {
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
		this.ambientSoundTime = -this.getAmbientSoundInterval();
		this.playSound(this.getHurtSound(source), 1.0F, RandomGenerator.generateFloat(1.15F, 1.3F));
	}

	public SoundEvent getDisappearSound() {
		return FriendsAndFoesSoundEvents.ENTITY_RASCAL_DISAPPEAR.get();
	}

	public void playDisappearSound() {
		SoundEvent soundEvent = this.getDisappearSound();
		this.playSound(soundEvent, 2.0F, RandomGenerator.generateFloat(1.5F, 1.6F));
	}

	public SoundEvent getReappearSound() {
		return FriendsAndFoesSoundEvents.ENTITY_RASCAL_REAPPEAR.get();
	}

	public void playReappearSound() {
		SoundEvent soundEvent = this.getReappearSound();
		this.playSound(soundEvent, 2.0F, RandomGenerator.generateFloat(1.5F, 1.6F));
	}

	@Override
	protected void playStepSound(
		BlockPos pos,
		BlockState state
	) {
		if (
			this.isHidden()
			|| state.liquid()
		) {
			return;
		}

		super.playStepSound(pos, state);
	}

	public boolean isHidden() {
		return this.getBrain().getMemoryInternal(MemoryModuleType.AVOID_TARGET).orElse(null) instanceof Player;
	}

	public boolean isMoving() {
		return this.onGround() && this.getDeltaMovement().lengthSqr() >= 0.0001;
	}

	public int getCaughtCount() {
		return this.entityData.get(CAUGHT_COUNT);
	}

	public void addToCaughtCount() {
		this.entityData.set(CAUGHT_COUNT, this.getCaughtCount() + 1);
	}

	public boolean shouldGiveReward() {
		return this.getCaughtCount() == 3;
	}

	public boolean disableAmbientSounds() {
		return this.ambientSounds = false;
	}

	public boolean enableAmbientSounds() {
		return this.ambientSounds = true;
	}

	public void spawnCloudParticles() {
		ParticleSpawner.spawnParticles(this, ParticleTypes.CLOUD, 16, 0.1D);
	}

	public void spawnAngerParticles() {
		ParticleSpawner.spawnParticles(this, ParticleTypes.ANGRY_VILLAGER, 16, 0.1D);
	}

	static {
		POSE_TICKS = SynchedEntityData.defineId(RascalEntity.class, EntityDataSerializers.INT);
		CAUGHT_COUNT = SynchedEntityData.defineId(RascalEntity.class, EntityDataSerializers.INT);
	}
}
