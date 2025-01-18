package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.block.CrabEggBlock;
import com.faboslav.friendsandfoes.common.entity.animation.CrabAnimations;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CrabBrain;
import com.faboslav.friendsandfoes.common.entity.ai.control.WallClimbNavigation;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.pose.CrabEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.mojang.serialization.Dynamic;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class CrabEntity extends Animal implements FlyingAnimal, AnimatedEntity
{
	private static final float MOVEMENT_SPEED = 0.225F;

	private static final String SIZE_NBT_NAME = "Size";
	private static final String HOME_NBT_NAME = "Home";
	private static final String HOME_NBT_NAME_X = "x";
	private static final String HOME_NBT_NAME_Y = "y";
	private static final String HOME_NBT_NAME_Z = "z";
	private static final String HAS_EGG_NBT_NAME = "HasEgg";

	private AnimationContextTracker animationContextTracker;
	private static final EntityDataAccessor<Integer> POSE_TICKS = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_CLIMBING_WALL = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<String> SIZE = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<CompoundTag> HOME = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.COMPOUND_TAG);
	private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_DANCING = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.BOOLEAN);

	private int climbingTicks = 0;

	public CrabEntity(EntityType<? extends CrabEntity> entityType, Level world) {
		super(entityType, world);

		this.setPose(CrabEntityPose.IDLE);
		this.setPathfindingMalus(PathType.WATER, 0.0F);
		this.setPathfindingMalus(PathType.DOOR_IRON_CLOSED, -1.0F);
		this.setPathfindingMalus(PathType.DOOR_WOOD_CLOSED, -1.0F);
		this.setPathfindingMalus(PathType.DOOR_OPEN, -1.0F);
		this.lookControl = new CrabLookControl(this, 10);
		this.navigation = new CrabWallClimbNavigation(this, world);
	}

	@Override
	public SpawnGroupData finalizeSpawn(
		ServerLevelAccessor world,
		DifficultyInstance difficulty,
		MobSpawnType spawnReason,
		@Nullable SpawnGroupData entityData
	) {
		SpawnGroupData superEntityData = super.finalizeSpawn(world, difficulty, spawnReason, entityData);

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

			for (var animation: this.getTrackedAnimations()) {
				this.animationContextTracker.add(animation);
			}
		}

		return this.animationContextTracker;
	}

	@Override
	public ArrayList<AnimationHolder> getTrackedAnimations() {
		return CrabAnimations.ANIMATIONS;
	}

	@Override
	public AnimationHolder getMovementAnimation() {
		return CrabAnimations.WALK;
	}

	@Override
	public boolean isPushedByFluid() {
		return !this.isSwimming();
	}

	@Override
	public boolean shouldDiscardFriction() {
		return this.isSwimming();
	}

	@Override
	public void jumpFromGround() {
	}

	public boolean isFlying() {
		return this.onClimbable();
	}

	@Override
	public float getSpeed() {
		if (this.isBaby()) {
			return MOVEMENT_SPEED / 2.0F;
		}

		return MOVEMENT_SPEED;
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
		builder.define(IS_CLIMBING_WALL, false);
		builder.define(SIZE, CrabSize.getDefaultCrabSize().getName());
		builder.define(HOME, new CompoundTag());
		builder.define(HAS_EGG, false);
		builder.define(IS_DANCING, false);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);

		nbt.putString(SIZE_NBT_NAME, this.getSize().getName());
		nbt.put(HOME_NBT_NAME, this.getHome());
		nbt.putBoolean(HAS_EGG_NBT_NAME, this.hasEgg());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);

		CrabSize crabSize = CrabSize.getCrabSizeByName(nbt.getString(SIZE_NBT_NAME));

		if (crabSize == null) {
			crabSize = CrabSize.getDefaultCrabSize();
		}

		this.setSize(crabSize);
		this.setHome(nbt.getCompound(HOME_NBT_NAME));
		this.setHasEgg(nbt.getBoolean(HAS_EGG_NBT_NAME));
	}

	@Override
	protected Brain<?> makeBrain(Dynamic<?> dynamic) {
		return CrabBrain.create(dynamic);
	}

	@Override
	@SuppressWarnings("all")
	public Brain<CrabEntity> getBrain() {
		return (Brain<CrabEntity>) super.getBrain();
	}

	@Override
	protected void sendDebugPackets() {
		super.sendDebugPackets();
		DebugPackets.sendEntityBrain(this);
	}

	public static AttributeSupplier.Builder createCrabAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 15.0).add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED).add(Attributes.ATTACK_DAMAGE, 2.0);
	}

	@Override
	protected float nextStep() {
		return this.moveDist + 0.175f;
	}

	@Override
	public boolean onClimbable() {
		return this.climbingTicks > 8 && this.isClimbingWall();
	}

	public boolean isClimbingWall() {
		return this.entityData.get(IS_CLIMBING_WALL);
	}

	public void setClimbingWall(boolean isClimbingWall) {
		this.entityData.set(IS_CLIMBING_WALL, isClimbingWall);
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
		if (!this.onGround() && state.liquid()) {
			return;
		}

		this.playSound(FriendsAndFoesSoundEvents.ENTITY_CRAB_STEP.get(), 0.15f, 1.0f + this.random.nextFloat() * 0.2f);
	}

	@Override
	public void tick() {
		if (!this.level().isClientSide() && !FriendsAndFoes.getConfig().enableCrab) {
			this.discard();
		}

		this.updateKeyframeAnimations();
		this.calculateSize();
		super.tick();

		if (!this.level().isClientSide()) {
			this.setClimbingWall(this.horizontalCollision);
		}

		if (this.isClimbingWall()) {
			this.climbingTicks++;

			var blockStateAtPos = this.getInBlockState();
			if (this.isMoving() && !blockStateAtPos.liquid() && this.climbingTicks % 6 == 0) {
				this.playStepSound(this.blockPosition(), blockStateAtPos);
			}
		} else {
			this.climbingTicks = 0;
		}

		if (this.onClimbable()) {
			Vec3 velocity = this.getDeltaMovement();
			this.setDeltaMovement(velocity.x, velocity.y * 0.33F, velocity.z);
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (this.tickCount % 5 == 0) {
			boolean isDancing = false;

			for (BlockPos blockPos : BlockPos.withinManhattan(this.blockPosition(), 7, 7, 7)) {
				BlockPos possibleJukeboxBlockPos = blockPos.mutable();
				BlockState possibleJukeboxBlockState = this.level().getBlockState(possibleJukeboxBlockPos);

				if (
					!possibleJukeboxBlockState.is(Blocks.JUKEBOX)
					|| !possibleJukeboxBlockState.hasBlockEntity()
				) {
					continue;
				}

				BlockEntity possibleJukeboxBlockEntity = this.level().getBlockEntity(possibleJukeboxBlockPos);
				if (!(possibleJukeboxBlockEntity instanceof JukeboxBlockEntity)) {
					continue;
				}

				if (((JukeboxBlockEntity) possibleJukeboxBlockEntity).getSongPlayer().isPlaying()) {
					isDancing = true;
					break;
				}
			}

			this.setIsDancing(isDancing);
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

		if (this.isInPose(CrabEntityPose.IDLE) && !this.isMoving()) {
			animation = CrabAnimations.IDLE;
		} else if (this.isInPose(CrabEntityPose.WAVE)) {
			animation = CrabAnimations.WAVE;
		} else if (this.isInPose(CrabEntityPose.DANCE)) {
			animation = CrabAnimations.DANCE;
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
		if (this.level().isClientSide()) {
			return;
		}

		setPose(pose.get());
	}

	public boolean hasPose(Pose pose) {
		return this.getPose() == pose;
	}

	public boolean isInPose(CrabEntityPose pose) {
		return this.getPose() == pose.get();
	}

	@Override
	protected void customServerAiStep() {
		this.level().getProfiler().push("crabBrain");
		this.getBrain().tick((ServerLevel) this.level(), this);

		this.level().getProfiler().pop();
		this.level().getProfiler().push("crabMemoryUpdate");
		CrabBrain.updateMemories(this);
		this.level().getProfiler().pop();

		this.level().getProfiler().push("crabActivityUpdate");
		CrabBrain.updateActivities(this);
		this.level().getProfiler().pop();

		super.customServerAiStep();
	}

	@Override
	protected void ageBoundaryReached() {
		super.ageBoundaryReached();

		if (!this.isBaby() && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			this.spawnAtLocation(FriendsAndFoesItems.CRAB_CLAW.get(), 1);
		}
	}

	public boolean isMoving() {
		return (this.onGround() || this.onClimbable()) && this.getDeltaMovement().lengthSqr() >= 0.0001;
	}

	public static boolean canSpawn(
		EntityType<? extends Animal> type,
		LevelAccessor world,
		MobSpawnType reason,
		BlockPos pos,
		RandomSource random
	) {
		return world.getBlockState(pos.below()).is(FriendsAndFoesTags.CRABS_SPAWNABLE_ON) && CrabEggBlock.isSuitableBelow(world, pos) && isBrightEnoughToSpawn(world, pos);
	}

	@Override
	public boolean isFood(ItemStack itemStack) {
		return CrabBrain.getTemptItems().test(itemStack);
	}

	@Override
	@Nullable
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob entity) {
		CrabEntity crab = FriendsAndFoesEntityTypes.CRAB.get().create(serverWorld);

		CrabBrain.setWaveCooldown(crab);

		return crab;
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

		this.setHasEgg(true);
		this.resetLove();
		mate.resetLove();
		RandomSource random = this.getRandom();

		if (this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY(), this.getZ(), random.nextInt(7) + 1));
		}
	}

	@Nullable
	public GlobalPos getBurrowSpotPos() {
		return this.getBrain().getMemoryInternal(FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get()).orElse(null);
	}

	public boolean isBurrowSpotAccessible(BlockPos pos) {
		var world = this.level();
		boolean isBlockSand = world.getBlockState(pos.below()).is(FriendsAndFoesTags.CRAB_BURROW_SPOT_BLOCKS);
		boolean isBlockAccessible = world.isEmptyBlock(pos) && world.isEmptyBlock(pos.above());

		return isBlockSand && isBlockAccessible;
	}

	public CompoundTag getNewHome() {
		CompoundTag home = new CompoundTag();

		home.putDouble(HOME_NBT_NAME_X, this.position().x());
		home.putDouble(HOME_NBT_NAME_Y, this.position().y());
		home.putDouble(HOME_NBT_NAME_Z, this.position().z());

		return home;
	}

	public void setHome(CompoundTag home) {
		entityData.set(HOME, home);
	}

	public CompoundTag getHome() {
		return entityData.get(HOME);
	}

	public Vec3 getHomePos() {
		return new Vec3(
			this.getHome().getDouble(HOME_NBT_NAME_X),
			this.getHome().getDouble(HOME_NBT_NAME_Y),
			this.getHome().getDouble(HOME_NBT_NAME_Z)
		);
	}

	public boolean isAtHomePos() {
		return this.distanceToSqr(this.getHomePos()) < 0.1D;
	}

	public boolean isCloseToHomePos(float distance) {
		return this.distanceToSqr(this.getHomePos()) < distance;
	}

	public boolean hasEgg() {
		return this.entityData.get(HAS_EGG);
	}

	public void setHasEgg(boolean hasEgg) {
		this.entityData.set(HAS_EGG, hasEgg);
	}

	public boolean isDancing() {
		return this.entityData.get(IS_DANCING);
	}

	public void setIsDancing(boolean isDancing) {
		this.entityData.set(IS_DANCING, isDancing);
	}

	private void setSize(CrabSize size) {
		this.entityData.set(SIZE, size.getName());
		this.calculateSize();
	}

	private void calculateSize() {
		this.refreshDimensions();
		this.makeBoundingBox();
	}

	public CrabSize getSize() {
		return CrabSize.getCrabSizeByName(this.entityData.get(SIZE));
	}

	@Override
	public float getAgeScale() {
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

		public static CrabSize getRandomCrabSize(RandomSource random) {
			Object[] values = CrabSize.values();
			int min = 0;
			int max = values.length - 1;
			return (CrabSize) values[random.nextInt((max - min) + 1) + min];
		}
	}

	class CrabLookControl extends SmoothSwimmingLookControl
	{
		public CrabLookControl(CrabEntity crab, int yawAdjustThreshold) {
			super(crab, yawAdjustThreshold);
		}

		public void tick() {
			if (!CrabEntity.this.onClimbable()) {
				super.tick();
			}
		}
	}

	class CrabWallClimbNavigation extends WallClimbNavigation
	{
		public CrabWallClimbNavigation(Mob mobEntity, Level world) {
			super(mobEntity, world);
		}

		public void tick() {
			if (!CrabEntity.this.isDancing()) {
				super.tick();
			}
		}
	}
}

