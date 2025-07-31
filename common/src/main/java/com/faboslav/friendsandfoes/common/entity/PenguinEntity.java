package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CrabBrain;
import com.faboslav.friendsandfoes.common.entity.ai.brain.PenguinBrain;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.animation.PenguinAnimations;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.pose.PenguinEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
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
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public final class PenguinEntity extends Animal implements AnimatedEntity {
	private AnimationContextTracker animationContextTracker;
	private static final EntityDataAccessor<Integer> POSE_TICKS;
	private static final float MOVEMENT_SPEED = 0.2F;

	public static final float GENERIC_ATTACK_DAMAGE = 8.0F;
	public static final float GENERIC_FOLLOW_RANGE = 32.0F;

	public PenguinEntity(EntityType<? extends Animal> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.1f, 0.5f, false);
		this.lookControl = new SmoothSwimmingLookControl(this, 10);
		this.setPose(PenguinEntityPose.IDLE);
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

		this.setPose(PenguinEntityPose.IDLE);

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

			for (var animation: this.getTrackedAnimations()) {
				this.animationContextTracker.add(animation);
			}
		}

		return this.animationContextTracker;
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

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new AmphibiousPathNavigation(this, world);
	}

	public boolean isMoving() {
		return (this.isUnderWater() || (this.onGround() || this.onClimbable())) && this.getDeltaMovement().lengthSqr() >= 0.0001;
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

		if (VersionedGameRulesProvider.getGameRules(this).getBoolean(GameRules.RULE_DOMOBLOOT)) {
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

		if (animationToStart != null) {
			this.tryToStartAnimation(animationToStart);
		}
	}

	@Nullable
	public AnimationHolder getAnimationByPose() {
		AnimationHolder animation = null;

		if(this.isInPose(PenguinEntityPose.IDLE) && !this.isMoving()) {
			if (this.isUnderWater()) {
				animation = PenguinAnimations.IDLE_WATER;
			} else {
				animation = PenguinAnimations.IDLE;
			}
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

	public void setPose(PenguinEntityPose pose) {
		if (this.level().isClientSide()) {
			return;
		}

		super.setPose(pose.get());
	}

	public boolean isInPose(PenguinEntityPose pose) {
		return this.getPose() == pose.get();
	}

	static {
		POSE_TICKS = SynchedEntityData.defineId(PenguinEntity.class, EntityDataSerializers.INT);
	}
}