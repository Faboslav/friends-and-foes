package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.animation.BarnacleAnimations;
import com.faboslav.friendsandfoes.client.render.entity.animation.KeyframeAnimation;
import com.faboslav.friendsandfoes.client.render.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.entity.ai.brain.BarnacleBrain;
import com.faboslav.friendsandfoes.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.entity.pose.BarnacleEntityPose;
import com.faboslav.friendsandfoes.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AxolotlSwimNavigation;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public final class BarnacleEntity extends HostileEntity implements AnimatedEntity
{
	private AnimationContextTracker animationContextTracker;
	private static final TrackedData<Integer> POSE_TICKS;

	public BarnacleEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.moveControl = new AquaticMoveControl(this, 85, 10, 0.1f, 0.5f, false);
		this.lookControl = new YawAdjustingLookControl(this, 10);
		this.stepHeight = 1.0f;

		this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
		this.setPose(BarnacleEntityPose.IDLE);
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

		this.setPose(BarnacleEntityPose.IDLE);

		return superEntityData;
	}

	@Override
	public AnimationContextTracker getAnimationContextTracker() {
		if (this.animationContextTracker == null) {
			this.animationContextTracker = new AnimationContextTracker();

			for (KeyframeAnimation keyframeAnimation : this.getAnimations()) {
				this.animationContextTracker.add(keyframeAnimation);
			}

			this.animationContextTracker.add(this.getMovementAnimation());
		}

		return this.animationContextTracker;
	}

	@Override
	public ArrayList<KeyframeAnimation> getAnimations() {
		return BarnacleAnimations.ANIMATIONS;
	}

	@Override
	public KeyframeAnimation getMovementAnimation() {
		return BarnacleAnimations.SWIM;
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
	}

	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
		return BarnacleBrain.create(dynamic);
	}

	@Override
	@SuppressWarnings("all")
	public Brain<BarnacleEntity> getBrain() {
		return (Brain<BarnacleEntity>) super.getBrain();
	}

	@Override
	protected void mobTick() {
		this.getWorld().getProfiler().push("barnacleBrain");
		this.getBrain().tick((ServerWorld) this.getWorld(), this);
		this.getWorld().getProfiler().pop();
		this.getWorld().getProfiler().push("barnacleActivityUpdate");
		BarnacleBrain.updateActivities(this);
		this.getWorld().getProfiler().pop();

		super.mobTick();
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0D)
			.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0F)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.55D)
			.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	public void tick() {
		if (FriendsAndFoes.getConfig().enableBarnacle == false) {
			this.discard();
		}

		if (this.getWorld().isClient() == false) {
			this.updateKeyframeAnimationTicks();
		}

		KeyframeAnimation keyframeAnimationToStart = this.getKeyframeAnimationByPose();

		if (keyframeAnimationToStart != null) {
			this.tryToStartKeyframeAnimation(keyframeAnimationToStart);
		}

		super.tick();
	}

	@Override
	public boolean damage(
		DamageSource source,
		float amount
	) {
		Entity attacker = source.getAttacker();
		boolean damageResult = super.damage(source, amount);

		if (damageResult && attacker instanceof LivingEntity) {
			BarnacleBrain.onAttacked(this, (LivingEntity) attacker);
		}

		return damageResult;
	}

	/*
	@Override
	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		if (world.getFluidState(pos).isIn(FluidTags.WATER)) {
			return 10.0f + world.getPhototaxisFavor(pos);
		}
		return super.getPathfindingFavor(pos, world);
	}*/

	@Override
	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		return 0.0f;
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		return new AxolotlSwimNavigation(this, world);
	}

	public static boolean canSpawn(
		EntityType<BarnacleEntity> type,
		ServerWorldAccess world,
		SpawnReason spawnReason,
		BlockPos pos,
		Random random
	) {
		if (
			!world.getFluidState(pos.down()).isIn(FluidTags.WATER)
			|| !isValidSpawnDepth(world, pos)
			|| random.nextInt(40) != 0
		) {
			return false;
		}

		FriendsAndFoes.getLogger().info("Spawning Barnacle");
		return true;
	}

	private static boolean isValidSpawnDepth(WorldAccess world, BlockPos pos) {
		return pos.getY() < world.getSeaLevel() - 5;
	}

	@Override
	public boolean canBreatheInWater() {
		return true;
	}

	@Override
	public boolean isPushedByFluids() {
		return false;
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.AQUATIC;
	}

	@Override
	public int getLimitPerChunk() {
		return 1;
	}

	@Override
	public int getMaxLookPitchChange() {
		return 180;
	}

	@Override
	protected Entity.MoveEffect getMoveEffect() {
		return Entity.MoveEffect.EVENTS;
	}

	@Override
	protected float getActiveEyeHeight(EntityPose poseIn, EntityDimensions sizeIn) {
		return 0.5F;
	}

	@Override
	public int getMinAmbientSoundDelay() {
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
		this.ambientSoundChance = -this.getMinAmbientSoundDelay();
		this.playSound(this.getHurtSound(source), 0.5F, RandomGenerator.generateFloat(1.25F, 1.45F));
	}

	@Override
	public void travel(Vec3d movementInput) {
		if (this.canMoveVoluntarily() && this.isTouchingWater()) {
			this.updateVelocity(this.getMovementSpeed(), movementInput);
			this.move(MovementType.SELF, this.getVelocity());
			this.setVelocity(this.getVelocity().multiply(0.9));
		} else {
			super.travel(movementInput);
		}
	}

	@Nullable
	private KeyframeAnimation getKeyframeAnimationByPose() {
		KeyframeAnimation keyframeAnimation = null;

		return keyframeAnimation;
	}

	private void tryToStartKeyframeAnimation(KeyframeAnimation keyframeAnimationToStart) {
		if (this.isKeyframeAnimationRunning(keyframeAnimationToStart)) {
			return;
		}

		if (this.getWorld().isClient() == false) {
			this.setKeyframeAnimationTicks(keyframeAnimationToStart.getAnimationLengthInTicks());
		}

		this.startKeyframeAnimation(keyframeAnimationToStart);
	}

	private void startKeyframeAnimation(KeyframeAnimation keyframeAnimationToStart) {
		for (KeyframeAnimation keyframeAnimation : this.getAnimations()) {
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

	public void setPose(BarnacleEntityPose pose) {
		if (this.getWorld().isClient()) {
			return;
		}

		super.setPose(pose.get());
	}

	public boolean isInPose(BarnacleEntityPose pose) {
		return this.getPose() == pose.get();
	}

	static {
		POSE_TICKS = DataTracker.registerData(BarnacleEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}
}
