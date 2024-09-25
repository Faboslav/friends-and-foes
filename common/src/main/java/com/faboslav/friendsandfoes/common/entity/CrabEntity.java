package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.block.CrabEggBlock;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.CrabAnimations;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.KeyframeAnimation;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CrabBrain;
import com.faboslav.friendsandfoes.common.entity.ai.control.WallClimbNavigation;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.pose.CrabEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.mojang.serialization.Dynamic;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class CrabEntity extends AnimalEntity implements Flutterer, AnimatedEntity
{
	private static final float MOVEMENT_SPEED = 0.225F;

	private static final String SIZE_NBT_NAME = "Size";
	private static final String HOME_NBT_NAME = "Home";
	private static final String HOME_NBT_NAME_X = "x";
	private static final String HOME_NBT_NAME_Y = "y";
	private static final String HOME_NBT_NAME_Z = "z";
	private static final String HAS_EGG_NBT_NAME = "HasEgg";

	private AnimationContextTracker animationContextTracker;
	private static final TrackedData<Integer> POSE_TICKS = DataTracker.registerData(CrabEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Boolean> IS_CLIMBING_WALL = DataTracker.registerData(CrabEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	private static final TrackedData<String> SIZE = DataTracker.registerData(CrabEntity.class, TrackedDataHandlerRegistry.STRING);
	private static final TrackedData<NbtCompound> HOME = DataTracker.registerData(CrabEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
	private static final TrackedData<Boolean> HAS_EGG = DataTracker.registerData(CrabEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	private static final TrackedData<Boolean> IS_DANCING = DataTracker.registerData(CrabEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	private int climbingTicks = 0;

	public CrabEntity(EntityType<? extends CrabEntity> entityType, World world) {
		super(entityType, world);

		this.setPose(CrabEntityPose.IDLE);
		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DOOR_IRON_CLOSED, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DOOR_WOOD_CLOSED, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DOOR_OPEN, -1.0F);
		this.lookControl = new CrabLookControl(this, 10);
		this.navigation = new WallClimbNavigation(this, world);
	}

	@Override
	public EntityData initialize(
		ServerWorldAccess world,
		LocalDifficulty difficulty,
		SpawnReason spawnReason,
		@Nullable EntityData entityData
	) {
		EntityData superEntityData = super.initialize(world, difficulty, spawnReason, entityData);

		this.setHome(this.getNewHome());
		this.setSize(CrabSize.getRandomCrabSize(world.getRandom()));
		this.setPose(CrabEntityPose.IDLE);
		CrabBrain.setWaveCooldown(this);

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
	public boolean isPushedByFluids() {
		return !this.isSwimming();
	}

	@Override
	public boolean hasNoDrag() {
		return this.isSwimming();
	}

	@Override
	protected void jump() {
	}

	public boolean isInAir() {
		return this.isClimbing();
	}

	@Override
	public float getMovementSpeed() {
		if (this.isBaby()) {
			return MOVEMENT_SPEED / 2.0F;
		}

		return MOVEMENT_SPEED;
	}

	@Override
	public int getKeyframeAnimationTicks() {
		return this.dataTracker.get(POSE_TICKS);
	}

	public void setKeyframeAnimationTicks(int keyframeAnimationTicks) {
		this.dataTracker.set(POSE_TICKS, keyframeAnimationTicks);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);

		builder.add(POSE_TICKS, 0);
		builder.add(IS_CLIMBING_WALL, false);
		builder.add(SIZE, CrabSize.getDefaultCrabSize().getName());
		builder.add(HOME, new NbtCompound());
		builder.add(HAS_EGG, false);
		builder.add(IS_DANCING, false);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);

		nbt.putString(SIZE_NBT_NAME, this.getSize().getName());
		nbt.put(HOME_NBT_NAME, this.getHome());
		nbt.putBoolean(HAS_EGG_NBT_NAME, this.hasEgg());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);

		CrabSize crabSize = CrabSize.getCrabSizeByName(nbt.getString(SIZE_NBT_NAME));

		if (crabSize == null) {
			crabSize = CrabSize.getDefaultCrabSize();
		}

		this.setSize(crabSize);
		this.setHome(nbt.getCompound(HOME_NBT_NAME));
		this.setHasEgg(nbt.getBoolean(HAS_EGG_NBT_NAME));
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
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
	}

	@Override
	protected float calculateNextStepSoundDistance() {
		return this.distanceTraveled + 0.175f;
	}

	@Override
	public boolean isClimbing() {
		return this.climbingTicks > 8 && this.isClimbingWall();
	}

	public boolean isClimbingWall() {
		return this.dataTracker.get(IS_CLIMBING_WALL);
	}

	public void setClimbingWall(boolean isClimbingWall) {
		this.dataTracker.set(IS_CLIMBING_WALL, isClimbingWall);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return FriendsAndFoesSoundEvents.ENTITY_CRAB_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return FriendsAndFoesSoundEvents.ENTITY_CRAB_DEATH.get();
	}

	@Override
	protected void playStepSound(
		BlockPos pos,
		BlockState state
	) {
		if (!this.isOnGround() && state.isLiquid()) {
			return;
		}

		this.playSound(FriendsAndFoesSoundEvents.ENTITY_CRAB_STEP.get(), 0.15f, 1.0f + this.random.nextFloat() * 0.2f);
	}

	@Override
	public void tick() {

		if (!this.getWorld().isClient() && !FriendsAndFoes.getConfig().enableCrab) {
			this.discard();
		}

		this.updateKeyframeAnimations();
		this.calculateSize();
		super.tick();

		if (!this.getWorld().isClient()) {
			this.setClimbingWall(this.horizontalCollision);
		}

		if (this.isClimbingWall()) {
			this.climbingTicks++;

			var blockStateAtPos = this.getBlockStateAtPos();
			if (this.isMoving() && !blockStateAtPos.isLiquid() && this.climbingTicks % 6 == 0) {
				this.playStepSound(this.getBlockPos(), blockStateAtPos);
			}
		} else {
			this.climbingTicks = 0;
		}

		if (this.isClimbing()) {
			Vec3d velocity = this.getVelocity();
			this.setVelocity(velocity.x, velocity.y * 0.33F, velocity.z);
		}
	}

	@Override
	public void tickMovement() {
		super.tickMovement();

		if (this.getWorld().isClient()) {
			return;
		}

		if (this.age % 10 == 0) {
			boolean isDancing = false;

			for (BlockPos blockPos : BlockPos.iterateOutwards(this.getBlockPos(), 7, 7, 7)) {
				BlockPos possibleJukeboxBlockPos = blockPos.mutableCopy();
				BlockState possibleJukeboxBlockState = this.getWorld().getBlockState(possibleJukeboxBlockPos);

				if (
					!possibleJukeboxBlockState.isOf(Blocks.JUKEBOX)
					|| !possibleJukeboxBlockState.hasBlockEntity()
				) {
					continue;
				}

				BlockEntity possibleJukeboxBlockEntity = this.getWorld().getBlockEntity(possibleJukeboxBlockPos);
				if (!(possibleJukeboxBlockEntity instanceof JukeboxBlockEntity)) {
					continue;
				}

				if (((JukeboxBlockEntity) possibleJukeboxBlockEntity).isPlayingRecord()) {
					isDancing = true;
					break;
				}
			}

			this.setIsDancing(isDancing);
		}
	}

	private void updateKeyframeAnimations() {
		if (!this.getWorld().isClient()) {
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

		if (this.isInPose(CrabEntityPose.IDLE) && !this.isMoving()) {
			keyframeAnimation = CrabAnimations.IDLE;
		} else if (this.isInPose(CrabEntityPose.WAVE)) {
			keyframeAnimation = CrabAnimations.WAVE;
		} else if (this.isInPose(CrabEntityPose.DANCE)) {
			keyframeAnimation = CrabAnimations.DANCE;
		}

		return keyframeAnimation;
	}

	private void tryToStartKeyframeAnimation(KeyframeAnimation keyframeAnimationToStart) {
		if (this.isKeyframeAnimationRunning(keyframeAnimationToStart)) {
			return;
		}

		if (!this.getWorld().isClient()) {
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

	public void startWaveAnimation() {
		if (this.isInPose(CrabEntityPose.WAVE)) {
			return;
		}

		this.setPose(CrabEntityPose.WAVE);
	}

	public void startDanceAnimation() {
		if (this.isInPose(CrabEntityPose.DANCE)) {
			return;
		}

		this.setPose(CrabEntityPose.DANCE);
	}

	public void setPose(CrabEntityPose pose) {
		if (this.getWorld().isClient()) {
			return;
		}

		setPose(pose.get());
	}

	public boolean isInPose(EntityPose pose) {
		return this.getPose() == pose;
	}

	public boolean isInPose(CrabEntityPose pose) {
		return this.getPose() == pose.get();
	}

	@Override
	protected void mobTick() {
		this.getWorld().getProfiler().push("crabBrain");
		this.getBrain().tick((ServerWorld) this.getWorld(), this);

		this.getWorld().getProfiler().pop();
		this.getWorld().getProfiler().push("crabMemoryUpdate");
		CrabBrain.updateMemories(this);
		this.getWorld().getProfiler().pop();

		this.getWorld().getProfiler().push("crabActivityUpdate");
		CrabBrain.updateActivities(this);
		this.getWorld().getProfiler().pop();

		super.mobTick();
	}

	@Override
	protected void onGrowUp() {
		super.onGrowUp();

		if (!this.isBaby() && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
			this.dropItem(FriendsAndFoesItems.CRAB_CLAW.get(), 1);
		}
	}

	public boolean isMoving() {
		return (this.isOnGround() || this.isClimbing()) && this.getVelocity().lengthSquared() >= 0.0001;
	}

	public static boolean canSpawn(
		EntityType<? extends AnimalEntity> type,
		WorldAccess world,
		SpawnReason reason,
		BlockPos pos,
		Random random
	) {
		return world.getBlockState(pos.down()).isIn(FriendsAndFoesTags.CRABS_SPAWNABLE_ON) && CrabEggBlock.isSuitableBelow(world, pos) && isLightLevelValidForNaturalSpawn(world, pos);
	}

	@Override
	public boolean isBreedingItem(ItemStack itemStack) {
		return CrabBrain.getTemptItems().test(itemStack);
	}

	@Override
	@Nullable
	public PassiveEntity createChild(ServerWorld serverWorld, PassiveEntity entity) {
		CrabEntity crab = FriendsAndFoesEntityTypes.CRAB.get().create(serverWorld);

		CrabBrain.setWaveCooldown(crab);

		return crab;
	}

	@Override
	public void breed(ServerWorld world, AnimalEntity mate) {
		ServerPlayerEntity serverPlayerEntity = this.getLovingPlayer();

		if (serverPlayerEntity == null && mate.getLovingPlayer() != null) {
			serverPlayerEntity = mate.getLovingPlayer();
		}

		if (serverPlayerEntity != null) {
			serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
			Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, this, mate, null);
		}

		this.setHasEgg(true);
		this.resetLoveTicks();
		mate.resetLoveTicks();
		Random random = this.getRandom();

		if (this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
			this.getWorld().spawnEntity(new ExperienceOrbEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), random.nextInt(7) + 1));
		}
	}

	@Nullable
	public GlobalPos getBurrowSpotPos() {
		return this.getBrain().getOptionalMemory(FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get()).orElse(null);
	}

	public boolean isBurrowSpotAccessible(BlockPos pos) {
		var world = this.getWorld();
		boolean isBlockSand = world.getBlockState(pos.down()).isIn(FriendsAndFoesTags.CRAB_BURROW_SPOT_BLOCKS);
		boolean isBlockAccessible = world.isAir(pos) && world.isAir(pos.up());

		return isBlockSand && isBlockAccessible;
	}

	public NbtCompound getNewHome() {
		NbtCompound home = new NbtCompound();

		home.putDouble(HOME_NBT_NAME_X, this.getPos().getX());
		home.putDouble(HOME_NBT_NAME_Y, this.getPos().getY());
		home.putDouble(HOME_NBT_NAME_Z, this.getPos().getZ());

		return home;
	}

	public void setHome(NbtCompound home) {
		dataTracker.set(HOME, home);
	}

	public NbtCompound getHome() {
		return dataTracker.get(HOME);
	}

	public Vec3d getHomePos() {
		return new Vec3d(
			this.getHome().getDouble(HOME_NBT_NAME_X),
			this.getHome().getDouble(HOME_NBT_NAME_Y),
			this.getHome().getDouble(HOME_NBT_NAME_Z)
		);
	}

	public boolean isAtHomePos() {
		return this.squaredDistanceTo(this.getHomePos()) < 0.1D;
	}

	public boolean isCloseToHomePos(float distance) {
		return this.squaredDistanceTo(this.getHomePos()) < distance;
	}

	public boolean hasEgg() {
		return this.dataTracker.get(HAS_EGG);
	}

	public void setHasEgg(boolean hasEgg) {
		this.dataTracker.set(HAS_EGG, hasEgg);
	}

	public boolean isDancing() {
		return this.dataTracker.get(IS_DANCING);
	}

	public void setIsDancing(boolean isDancing) {
		this.dataTracker.set(IS_DANCING, isDancing);
	}

	private void setSize(CrabSize size) {
		this.dataTracker.set(SIZE, size.getName());
		this.calculateSize();
	}

	private void calculateSize() {
		this.calculateDimensions();
		this.calculateBoundingBox();
	}

	public CrabSize getSize() {
		return CrabSize.getCrabSizeByName(this.dataTracker.get(SIZE));
	}

	@Override
	public float getScaleFactor() {
		// TODO check
		return this.getSize().getScaleModifier();
	}

	public enum CrabSize
	{
		SMALL("small", 0.9F),
		MEDIUM("medium", 0.95F),
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

	class CrabLookControl extends YawAdjustingLookControl
	{
		public CrabLookControl(CrabEntity crab, int yawAdjustThreshold) {
			super(crab, yawAdjustThreshold);
		}

		public void tick() {
			if (!CrabEntity.this.isClimbing()) {
				super.tick();
			}
		}
	}
}

