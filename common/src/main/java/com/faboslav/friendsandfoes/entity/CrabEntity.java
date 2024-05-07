package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.animation.CrabAnimations;
import com.faboslav.friendsandfoes.client.render.entity.animation.KeyframeAnimation;
import com.faboslav.friendsandfoes.client.render.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.entity.ai.brain.CrabBrain;
import com.faboslav.friendsandfoes.entity.ai.control.WallClimbNavigation;
import com.faboslav.friendsandfoes.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.tag.FriendsAndFoesTags;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class CrabEntity extends AnimalEntity implements AnimatedEntity
{
	public static final String SIZE_NBT_NAME = "Size";

	private AnimationContextTracker animationContextTracker;
	private static final TrackedData<Integer> POSE_TICKS = DataTracker.registerData(CrabEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Boolean> IS_CLIMBING_WALL = DataTracker.registerData(CrabEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	private static final TrackedData<String> SIZE = DataTracker.registerData(CrabEntity.class, TrackedDataHandlerRegistry.STRING);

	public CrabEntity(EntityType<? extends CrabEntity> entityType, World world) {
		super(entityType, world);

		this.stepHeight = 0.0F;
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

		this.setSize(CrabSize.getRandomCrabSize(world.getRandom()));

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
		return CrabAnimations.ANIMATIONS;
	}

	@Override
	public KeyframeAnimation getMovementAnimation() {
		return CrabAnimations.WALK;
	}

	@Override
	public void updateLimbs(LivingEntity entity, boolean flutter) {
		double f;
		double e;
		entity.lastLimbDistance = entity.limbDistance;
		double d = entity.getX() - entity.prevX;
		float g = (float) Math.sqrt(d * d + (e = entity.getY() - entity.prevY) * e + (f = entity.getZ() - entity.prevZ) * f) * 4.0f;
		if (g > 1.0f) {
			g = 1.0f;
		}
		entity.limbDistance += (g - entity.limbDistance) * 0.4f;
		entity.limbAngle += entity.limbDistance;
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
		this.dataTracker.startTracking(IS_CLIMBING_WALL, false);
		this.dataTracker.startTracking(SIZE, CrabSize.getDefaultCrabSize().getName());
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);

		nbt.putString(SIZE_NBT_NAME, this.getSize().getName());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);

		CrabSize crabSize = CrabSize.getCrabSizeByName(nbt.getString(SIZE_NBT_NAME));

		if (crabSize == null) {
			crabSize = CrabSize.getDefaultCrabSize();
		}

		this.setSize(crabSize);
	}

	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
		return CrabBrain.create(dynamic);
	}

	@Override
	@SuppressWarnings("all")
	public Brain<CrabEntity> getBrain() {
		return (Brain<CrabEntity>) super.getBrain();
	}


	public static DefaultAttributeContainer.Builder createCrabAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		return new WallClimbNavigation(this, world);
	}

	@Override
	protected float calculateNextStepSoundDistance() {
		return this.distanceTraveled + 0.175f;
	}

	@Override
	public boolean isClimbing() {
		return this.isClimbingWall();
	}

	public boolean isClimbingWall() {
		return this.dataTracker.get(IS_CLIMBING_WALL);
	}

	public void setClimbingWall(boolean isClimbingWall) {
		this.dataTracker.set(IS_CLIMBING_WALL, isClimbingWall);
	}

	@Override
	protected void playStepSound(
		BlockPos pos,
		BlockState state
	) {
		if (state.getMaterial().isLiquid()) {
			return;
		}

		this.playSound(FriendsAndFoesSoundEvents.ENTITY_CRAB_STEP.get(), 0.15f, 0.8f + this.random.nextFloat() * 0.2f);
	}

	@Override
	public void tick() {

		if (this.getWorld().isClient() == false && FriendsAndFoes.getConfig().enableCrab == false) {
			this.discard();
		}

		this.updateKeyframeAnimations();
		super.tick();

		if (!this.getWorld().isClient()) {
			this.setClimbingWall(this.horizontalCollision);
		}

		if (this.horizontalCollision && this.isClimbing()) {
			Vec3d velocity = this.getVelocity();
			this.setVelocity(velocity.x, velocity.y * 0.5F, velocity.z);
		}
	}

	private void updateKeyframeAnimations() {
		if (this.getWorld().isClient() == false) {
			this.updateKeyframeAnimationTicks();
		}

		KeyframeAnimation keyframeAnimationToStart = this.getKeyframeAnimationByPose();

		if (keyframeAnimationToStart != null) {
			this.tryToStartKeyframeAnimation(keyframeAnimationToStart);
		}
	}

	@Nullable
	private KeyframeAnimation getKeyframeAnimationByPose() {
		KeyframeAnimation keyframeAnimation = null;

		if (this.isMoving() == false && this.isClimbing() == false) {
			keyframeAnimation = CrabAnimations.IDLE;
		}

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
	protected void mobTick() {
		this.getWorld().getProfiler().push("crabBrain");
		this.getBrain().tick((ServerWorld) this.world, this);
		this.getWorld().getProfiler().pop();
		this.getWorld().getProfiler().push("crabActivityUpdate");
		CrabBrain.updateActivities(this);
		this.getWorld().getProfiler().pop();
		super.mobTick();
	}

	public boolean isMoving() {
		return this.isOnGround() && this.getVelocity().lengthSquared() >= 0.0001;
	}

	public static boolean canSpawn(
		EntityType<? extends AnimalEntity> type,
		WorldAccess world,
		SpawnReason reason,
		BlockPos pos,
		Random random
	) {
		return world.getBlockState(pos.down()).isIn(FriendsAndFoesTags.CRABS_SPAWNABLE_ON) && FrogEntity.isLightLevelValidForNaturalSpawn(world, pos);
	}

	@Override
	public boolean canBreedWith(AnimalEntity other) {
		return false;
	}

	@Override
	@Nullable
	public PassiveEntity createChild(ServerWorld serverWorld, PassiveEntity entity) {
		return null;
	}

	private void setSize(CrabSize size) {
		this.dataTracker.set(SIZE, size.getName());

		this.calculateDimensions();
		this.calculateBoundingBox();
	}

	public CrabSize getSize() {
		return CrabSize.getCrabSizeByName(this.dataTracker.get(SIZE));
	}

	public enum CrabSize
	{
		SMALL("small", 0.8F),
		MEDIUM("medium", 0.9F),
		BIG("big", 1.0F);

		private final String name;
		private final float scaleModifier;

		CrabSize(String name, float scaleModifier) {
			this.name = name;
			this.scaleModifier = scaleModifier;
		}

		public String getName() {
			return this.name;
		}

		public float getScaleModifier() {
			return this.scaleModifier;
		}

		@Nullable
		public static CrabSize getCrabSizeByName(String name) {
			for (CrabSize crabSize : CrabSize.values()) {
				if (Objects.equals(crabSize.getName(), name)) {
					return crabSize;
				}
			}

			return null;
		}

		public static CrabSize getDefaultCrabSize() {
			return CrabSize.BIG;
		}

		public static CrabSize getRandomCrabSize(Random random) {
			Object[] values = CrabSize.values();
			int min = 0;
			int max = values.length - 1;
			return (CrabSize) values[random.nextInt((max - min) + 1) + min];
		}
	}
}

