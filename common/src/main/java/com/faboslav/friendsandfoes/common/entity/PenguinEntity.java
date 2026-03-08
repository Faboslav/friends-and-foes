package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.ai.brain.PenguinBrain;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.animation.PenguinAnimations;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.pose.FriendsAndFoesEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityDataSerializers;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.util.RandomGenerator;
import com.faboslav.friendsandfoes.common.versions.VersionedGameRulesProvider;
import com.faboslav.friendsandfoes.common.versions.VersionedProfilerProvider;
import com.mojang.serialization.Dynamic;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public final class PenguinEntity extends Animal implements AnimatedEntity {
	private AnimationContextTracker animationContextTracker;

	private static final EntityDataAccessor<Integer> POSE_TICKS = SynchedEntityData.defineId(PenguinEntity.class, EntityDataSerializers.INT);;
	private static final EntityDataAccessor<FriendsAndFoesEntityPose> ENTITY_POSE = SynchedEntityData.defineId(PenguinEntity.class, FriendsAndFoesEntityDataSerializers.ENTITY_POSE);

	private static final float MOVEMENT_SPEED = 0.2F;

	public static final float GENERIC_ATTACK_DAMAGE = 8.0F;
	public static final float GENERIC_FOLLOW_RANGE = 32.0F;

	public PenguinEntity(EntityType<? extends Animal> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new PenguinSmoothSwimmingMoveControl(this, 85, 10, 0.1f, 0.5f, false);
		this.jumpControl = new PenguinJumpControl(this);
		this.lookControl = new SmoothSwimmingLookControl(this, 10);
		this.setEntityPose(FriendsAndFoesEntityPose.IDLE);
	}

	@Override
	public SpawnGroupData finalizeSpawn(
		net.minecraft.world.level.ServerLevelAccessor world,
		DifficultyInstance difficulty,
		/*? >=1.21.3 {*/
		EntitySpawnReason spawnReason,
		/*?} else {*/
		/*MobSpawnType spawnReason,
		 *//*?}*/
		@Nullable SpawnGroupData entityData
	) {
		SpawnGroupData superEntityData = super.finalizeSpawn(world, difficulty, spawnReason, entityData);

		this.setEntityPose(FriendsAndFoesEntityPose.IDLE);
		PenguinBrain.setWingFlapCooldown(this);

		return superEntityData;
	}

	@Override
	public boolean isBaby() {
		return false;
	}

	@Override
	public void setBaby(boolean baby) {
	}

	@Override
	public AnimationContextTracker getAnimationContextTracker() {
		if (this.animationContextTracker == null) {
			this.animationContextTracker = new AnimationContextTracker();

			for (var trackedAnimation: this.getTrackedAnimations()) {
				this.animationContextTracker.add(trackedAnimation);
			}

			for (var idleAnimation: this.getIdleAnimations()) {
				this.animationContextTracker.add(idleAnimation);
			}
		}

		return this.animationContextTracker;
	}

	public ArrayList<AnimationHolder> getIdleAnimations() {
		return PenguinAnimations.IDLE_ANIMATIONS;
	}

	@Override
	public ArrayList<AnimationHolder> getTrackedAnimations() {
		return PenguinAnimations.TRACKED_ANIMATIONS;
	}

	@Override
	public AnimationHolder getMovementAnimation() {
		if(isUnderWater()) {
			return PenguinAnimations.SWIM;
		}

		return PenguinAnimations.WALK;
	}

	@Override
	public int getCurrentAnimationTick() {
		return this.entityData.get(POSE_TICKS);
	}

	@Override
	public void setCurrentAnimationTick(int keyframeAnimationTicks) {
		this.entityData.set(POSE_TICKS, keyframeAnimationTicks);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);

		builder.define(POSE_TICKS, 0);
		builder.define(ENTITY_POSE, FriendsAndFoesEntityPose.IDLE);
	}

	@Override
	protected Brain<?> makeBrain(Dynamic<?> dynamic) {
		return PenguinBrain.create(dynamic);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Brain<PenguinEntity> getBrain() {
		return (Brain<PenguinEntity>) super.getBrain();
	}

	@Override
	protected void customServerAiStep(/*? >=1.21.3 {*/ServerLevel level/*?}*/)
	{
		//? <1.21.3 {
		/*var level = (ServerLevel) this.level();
		 *///?}

		var profiler = VersionedProfilerProvider.getProfiler(this);
		profiler.push("penguinBrain");
		this.getBrain().tick(level, this);

		profiler.pop();
		profiler.push("penguinMemoryUpdate");
		PenguinBrain.updateMemories(this);
		profiler.pop();

		profiler.push("penguinActivityUpdate");
		PenguinBrain.updateActivities(this);
		profiler.pop();

		super.customServerAiStep(/*? >=1.21.3 {*/level/*?}*/);
	}

	public static AttributeSupplier.Builder createPenguinAttributes() {
		//? >= 1.21.4 {
		var attributes = Animal.createAnimalAttributes();
		//?} else {
		/*var attributes = Mob.createMobAttributes();
		 *///?}
		return attributes
			.add(Attributes.MAX_HEALTH, 12.0D)
			.add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
			.add(Attributes.STEP_HEIGHT, 1.0D);
	}

	@Override
	public void tick() {
		if (!this.level().isClientSide() && !FriendsAndFoes.getConfig().enablePenguin) {
			this.discard();
		}

		this.updateKeyframeAnimations();
		super.tick();
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> dataAccessor) {
		if (ENTITY_POSE.equals(dataAccessor)) {
			var animationToStart = this.getAnimationByPose();

			if (animationToStart != null) {
				this.tryToStartAnimation(animationToStart);
			}
		}

		super.onSyncedDataUpdated(dataAccessor);
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new AmphibiousPathNavigation(this, world);
	}

	public boolean isMoving() {
		return (this.isUnderWater() || this.onGround()) && this.getDeltaMovement().lengthSqr() >= 0.01;
	}

	public static boolean canSpawn(
		EntityType<? extends Animal> type,
		LevelAccessor world,
		/*? >=1.21.3 {*/
		EntitySpawnReason spawnReason,
		/*?} else {*/
		/*MobSpawnType spawnReason,
		 *//*?}*/
		BlockPos pos,
		RandomSource random
	) {
		return true;
	}

	@Override
	protected float nextStep() {
		return this.moveDist + 0.275f;
	}

	@Override
	public float getSpeed() {
		if (this.isUnderWater()) {
			return MOVEMENT_SPEED * 1.25F;
		}

		return MOVEMENT_SPEED;
	}

	@Override
	public boolean isFood(ItemStack itemStack) {
		return PenguinBrain.getTemptations().test(itemStack);
	}

	@Override
	@Nullable
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob entity) {
		PenguinEntity penguin = FriendsAndFoesEntityTypes.PENGUIN.get().create(serverWorld/*? >=1.21.3 {*/, EntitySpawnReason.BREEDING/*?}*/);

		PenguinBrain.setWingFlapCooldown(penguin);

		return penguin;
	}

	@Override
	public void spawnChildFromBreeding(ServerLevel world, Animal mate) {
		ServerPlayer serverPlayerEntity = this.getLoveCause();

		if (serverPlayerEntity == null && mate.getLoveCause() != null) {
			serverPlayerEntity = mate.getLoveCause();
		}

		if (serverPlayerEntity != null) {
			serverPlayerEntity.awardStat(Stats.ANIMALS_BRED);
			CriteriaTriggers.BRED_ANIMALS.trigger(serverPlayerEntity, this, mate, null);
		}

		//this.setHasEgg(true);
		this.resetLove();
		mate.resetLove();
		RandomSource random = this.getRandom();

		if (VersionedGameRulesProvider.getBoolean(this, VersionedGameRulesProvider.MOB_DROPS)) {
			this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY(), this.getZ(), random.nextInt(7) + 1));
		}
	}

	@Override
	public int getMaxAirSupply() {
		return 720;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return FriendsAndFoesSoundEvents.ENTITY_PENGUIN_AMBIENT.get();
	}

	@Override
	public void playAmbientSound() {
		if(this.isUnderWater()) {
			return;
		}

		SoundEvent soundEvent = this.getAmbientSound();
		super.playAmbientSound();
		this.playSound(soundEvent, this.getSoundVolume() * 0.5F, this.getVoicePitch());
	}

	/*
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return FriendsAndFoesSoundEvents.ENTITY_BARNACLE_HURT.get();
	}

	@Override
	protected void playHurtSound(DamageSource source) {
		this.ambientSoundTime = -this.getAmbientSoundInterval();
		this.playSound(this.getHurtSound(source), 0.5F, RandomGenerator.generateFloat(1.25F, 1.45F));
	}*/

	@Override
	protected void playStepSound(
		BlockPos pos,
		BlockState state
	) {
		if (!this.onGround() || state.liquid()) {
			return;
		}

		this.playSound(FriendsAndFoesSoundEvents.ENTITY_PENGUIN_STEP.get(), 0.1f, 1.0f + this.random.nextFloat() * 0.2f);
	}

	public SoundEvent getWingFlapSound() {
		return FriendsAndFoesSoundEvents.ENTITY_PENGUIN_WING_FLAP.get();
	}

	public void playWingFlapSound() {
		this.playSound(this.getWingFlapSound(), 1.0F, RandomGenerator.generateFloat(0.5F, 0.55F));
	}

	public boolean hasEgg() {
		return false;
	}

	public void travel(Vec3 travelVector) {
		if (this.isUnderWater()) {
			this.moveRelative(this.getSpeed(), travelVector);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
			if (this.getTarget() == null) {
				this.setDeltaMovement(this.getDeltaMovement().add((double)0.0F, -0.005, (double)0.0F));
			}
		} else {
			super.travel(travelVector);
		}
	}

	private void updateKeyframeAnimations() {
		if (!this.level().isClientSide()) {
			this.updateCurrentAnimationTick();
		}

		AnimationHolder animationToStart = this.getAnimationByPose();

		/*
		if (animationToStart != null) {
			//FriendsAndFoes.getLogger().info("Animation to start: " + animationToStart.get().name());
			this.tryToStartAnimation(animationToStart);
		}*/
	}

	@Nullable
	public AnimationHolder getAnimationByPose() {
		AnimationHolder animation = null;

		if(this.isInEntityPose(FriendsAndFoesEntityPose.IDLE) && !this.isMoving()) {
			if (this.isUnderWater()) {
				animation = PenguinAnimations.IDLE_WATER;
			} else {
				animation = PenguinAnimations.IDLE;
			}
		} else if (this.isInEntityPose(FriendsAndFoesEntityPose.WING_FLAP)) {
			animation = PenguinAnimations.WING_FLAP;
		}

		return animation;
	}

	private void tryToStartAnimation(AnimationHolder animationToStart) {
		if (this.isKeyframeAnimationRunning(animationToStart)) {
			return;
		}

		//FriendsAndFoes.getLogger().info("Starting animation: " + animationToStart.get().name());
		this.startKeyframeAnimation(animationToStart);
	}

	private void startKeyframeAnimation(AnimationHolder animationToStart) {
		for (var animation : this.getTrackedAnimations()) {
			if (animation == animationToStart) {
				continue;
			}

			if(!animation.get().looping() && isKeyframeAnimationAtLastKeyframe(animation)) {
				//FriendsAndFoes.getLogger().info("Stopping animation: " + animation.get().name());
			}

			this.stopKeyframeAnimation(animation);
		}

		if (!this.level().isClientSide()) {
			this.setCurrentAnimationTick(animationToStart.get().lengthInTicks());
		}

		//FriendsAndFoes.getLogger().info("Starting animation: " + animationToStart.get().name() + " with tick count: " + this.tickCount);
		this.startKeyframeAnimation(animationToStart, this.tickCount);
	}

	public void startWingFlapAnimation() {
		if (this.isInEntityPose(FriendsAndFoesEntityPose.WING_FLAP)) {
			return;
		}

		this.gameEvent(GameEvent.ENTITY_ACTION);
		this.playWingFlapSound();
		this.setEntityPose(FriendsAndFoesEntityPose.WING_FLAP);
	}

	public void setEntityPose(FriendsAndFoesEntityPose pose) {
		if (this.level().isClientSide()) {
			//FriendsAndFoes.getLogger().info("nope");
			return;
		}

		//FriendsAndFoes.getLogger().info("Setting entity pose to: " + pose);
		this.entityData.set(ENTITY_POSE, pose);
	}

	public FriendsAndFoesEntityPose getEntityPose() {
		return this.entityData.get(ENTITY_POSE);
	}

	public boolean isInEntityPose(FriendsAndFoesEntityPose pose) {
		return this.getEntityPose() == pose;
	}

	final class PenguinSmoothSwimmingMoveControl extends SmoothSwimmingMoveControl
	{
		public PenguinSmoothSwimmingMoveControl(final PenguinEntity penguin, int maxTurnX, int maxTurnY, float inWaterSpeedModifier, float outsideWaterSpeedModifier, boolean applyGravity) {
			super(penguin, maxTurnX, maxTurnY, inWaterSpeedModifier, outsideWaterSpeedModifier, applyGravity);
		}

		@Override
		public void tick() {
			if (PenguinEntity.this.isInEntityPose(FriendsAndFoesEntityPose.WING_FLAP)) {
				return;
			}

			super.tick();
		}
	}

	final class PenguinJumpControl extends JumpControl
	{
		public PenguinJumpControl(final PenguinEntity penguin) {
			super(penguin);
		}

		public void tick() {
			if (PenguinEntity.this.isInEntityPose(FriendsAndFoesEntityPose.WING_FLAP)) {
				PenguinEntity.this.setJumping(false);
			}

			super.tick();
		}
	}

}