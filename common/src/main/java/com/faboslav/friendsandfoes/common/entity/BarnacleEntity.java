package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.ai.brain.BarnacleBrain;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.animation.BarnacleAnimations;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.pose.FriendsAndFoesEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityDataSerializers;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.util.RandomGenerator;
import com.faboslav.friendsandfoes.common.versions.VersionedProfilerProvider;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public final class BarnacleEntity extends Monster implements AnimatedEntity {
	private AnimationContextTracker animationContextTracker;

	private static final EntityDataAccessor<Integer> POSE_TICKS = SynchedEntityData.defineId(BarnacleEntity.class, EntityDataSerializers.INT);;
	private static final EntityDataAccessor<FriendsAndFoesEntityPose> ENTITY_POSE = SynchedEntityData.defineId(BarnacleEntity.class, FriendsAndFoesEntityDataSerializers.ENTITY_POSE);

	public static final float GENERIC_ATTACK_DAMAGE = 1.0F;
	public static final float GENERIC_FOLLOW_RANGE = 32.0F;

	public BarnacleEntity(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.1f, 0.5f, false);
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
		return superEntityData;
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
		return BarnacleAnimations.TRACKED_ANIMATIONS;
	}

	@Override
	public AnimationHolder getMovementAnimation() {
		return BarnacleAnimations.SWIM;
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
		return BarnacleBrain.create(dynamic);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Brain<BarnacleEntity> getBrain() {
		return (Brain<BarnacleEntity>) super.getBrain();
	}

	@Override
	protected void customServerAiStep(/*? >=1.21.3 {*/ServerLevel level/*?}*/)
	{
		//? <1.21.3 {
		/*var level = (ServerLevel) this.level();
		 *///?}

		var profiler = VersionedProfilerProvider.getProfiler(this);
		profiler.push("barnacleBrain");
		this.getBrain().tick(level, this);

		profiler.pop();
		profiler.push("barnacleMemoryUpdate");
		BarnacleBrain.updateMemories(this);
		profiler.pop();

		profiler.push("barnacleActivityUpdate");
		BarnacleBrain.updateActivities(this);
		profiler.pop();

		super.customServerAiStep(/*? >=1.21.3 {*/level/*?}*/);
	}

	public static AttributeSupplier.Builder createBarnacleAttributes() {
		//? >= 1.21.4 {
		var attributes = Animal.createAnimalAttributes();
		//?} else {
		/*var attributes = Mob.createMobAttributes();
		 *///?}
		return attributes
			.add(Attributes.MAX_HEALTH, 40.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.55D)
			.add(Attributes.ATTACK_DAMAGE, GENERIC_ATTACK_DAMAGE)
			.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
			.add(Attributes.SCALE);
	}

	@Override
	public void tick() {
		if (!this.level().isClientSide() && !FriendsAndFoes.getConfig().enableBarnacle) {
			this.discard();
		}

		this.updateKeyframeAnimations();
		super.tick();
	}

	@Override
	/*? >=1.21.3 {*/
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount)
	/*?} else {*/
	/*public boolean hurt(DamageSource damageSource, float amount)
	*//*?}*/
	{
		//? <1.21.3 {
		/*var level = this.level();
		 *///?}
		/*? >=1.21.3 {*/
		boolean damageResult = super.hurtServer(level, damageSource, amount);
		/*?} else {*/
		/*boolean damageResult = super.hurt(damageSource, amount);
		 *//*?}*/

		Entity attacker = damageSource.getEntity();

		if (damageResult && attacker instanceof LivingEntity) {
			BarnacleBrain.onAttacked(this, (LivingEntity) attacker);
		}

		return damageResult;
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new WaterBoundPathNavigation(this, world);
	}

	public boolean isMoving() {
		return this.isInWater() && this.getDeltaMovement().lengthSqr() >= 0.0001;
	}

	public static boolean canSpawn(
		EntityType<? extends Monster> type,
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

	private static boolean isValidSpawnDepth(LevelAccessor world, BlockPos pos) {
		return pos.getY() < world.getSeaLevel() - 5;
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	/*
	@Override
	public MobType getMobType() {
		return MobType.WATER;
	}*/

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public int getMaxHeadYRot() {
		return 180;
	}

	@Override
	protected Entity.MovementEmission getMovementEmission() {
		return Entity.MovementEmission.EVENTS;
	}

	@Override
	public int getAmbientSoundInterval() {
		return 160;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return FriendsAndFoesSoundEvents.ENTITY_BARNACLE_AMBIENT.get();
	}

	@Override
	public void playAmbientSound() {
		SoundEvent soundEvent = this.getAmbientSound();
		this.playSound(soundEvent, 0.5F, RandomGenerator.generateFloat(1.25F, 1.45F));
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return FriendsAndFoesSoundEvents.ENTITY_BARNACLE_HURT.get();
	}

	@Override
	protected void playHurtSound(DamageSource source) {
		this.ambientSoundTime = -this.getAmbientSoundInterval();
		this.playSound(this.getHurtSound(source), 0.5F, RandomGenerator.generateFloat(1.25F, 1.45F));
	}

	public void travel(Vec3 travelVector) {
		if (this.isInWater()) {
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

	// TODO check
	/*
	@Override
	protected AABB getAttackBoundingBox() {
		AABB original = super.getAttackBoundingBox();
		return original.inflate(
			-original.getXsize() / 4.0,
			-original.getYsize() / 4.0,
			-original.getZsize() / 4.0
		);
	}*/

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

		if (this.isInEntityPose(FriendsAndFoesEntityPose.IDLE) && !this.isMoving()) {
			animation = BarnacleAnimations.IDLE;
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

	public void setEntityPose(FriendsAndFoesEntityPose pose) {
		if (this.level().isClientSide()) {
			return;
		}

		this.entityData.set(ENTITY_POSE, pose);
	}

	public FriendsAndFoesEntityPose getEntityPose() {
		return this.entityData.get(ENTITY_POSE);
	}

	public boolean isInEntityPose(FriendsAndFoesEntityPose pose) {
		return this.getEntityPose() == pose;
	}
}