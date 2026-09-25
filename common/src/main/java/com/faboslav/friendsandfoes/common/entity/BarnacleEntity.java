package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.ai.brain.BarnacleBrain;
import com.faboslav.friendsandfoes.common.entity.pose.FriendsAndFoesEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityDataSerializers;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.util.RandomGenerator;
import com.faboslav.friendsandfoes.common.util.animation.AnimationMath;
import com.faboslav.friendsandfoes.common.versions.VersionedBlockPathType;
import com.faboslav.friendsandfoes.common.versions.VersionedLevel;
import com.faboslav.friendsandfoes.common.versions.VersionedProfilerProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import java.util.OptionalInt;

//? if >= 1.21.4 {
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
//?} else {
/*import net.minecraft.world.entity.vehicle.Boat;
*///?}

//? if < 1.20.6 {
/*import net.minecraft.world.entity.ai.memory.MemoryModuleType;
*///?}

//? if <= 1.21.11 {
/*import com.mojang.serialization.Dynamic;
*///?}

//? if <1.21.1 {
/*import net.minecraft.nbt.CompoundTag;
*///?}

@SuppressWarnings({"deprecation", "unchecked"})
public final class BarnacleEntity extends Monster
{
	private static final EntityDataAccessor<FriendsAndFoesEntityPose> ENTITY_POSE = SynchedEntityData.defineId(BarnacleEntity.class, FriendsAndFoesEntityDataSerializers.ENTITY_POSE);
	private static final EntityDataAccessor<OptionalInt> TENTACLE_TARGET_ID = SynchedEntityData.defineId(BarnacleEntity.class, EntityDataSerializers.OPTIONAL_UNSIGNED_INT);

	public static final float GENERIC_ATTACK_DAMAGE = 4.0F;
	public static final float GENERIC_FOLLOW_RANGE = 32.0F;
	public static final float TENTACLE_ATTACK_RANGE = 6.0F;
	public static final float MAX_TENTACLE_LENGTH = 9.0F;
	public static final float TENTACLE_HOLD_DISTANCE = 1.0F;
	public static final int TENTACLE_GRAB_DURATION = 14;
	public static final float AMBIENT_SOUND_RANGE = 32.0F;
	private static final int TENTACLE_RETRACT_SPEED = 3;
	private static final int ATTACK_ANIMATION_DURATION = AnimationMath.toLengthInTicks(0.3333F);

	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState tentacleAttackAnimationState = new AnimationState();
	public final AnimationState attackAnimationState = new AnimationState();

	@Nullable
	private Entity clientSideTentacleTarget;
	private int clientSideTentacleTicks;
	private int attackAnimationTicks;

	public BarnacleEntity(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 10;
		this.setPathfindingMalus(VersionedBlockPathType.WATER, 0.0F);
		this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.1f, 0.5f, false);
		this.lookControl = new SmoothSwimmingLookControl(this, 20);
		this.setEntityPose(FriendsAndFoesEntityPose.IDLE);
	}

	@Override
	public SpawnGroupData finalizeSpawn(
		ServerLevelAccessor world,
		DifficultyInstance difficulty,
		/*? if >=1.21.3 {*/
		EntitySpawnReason spawnReason,
		/*?} else {*/
		/*MobSpawnType spawnReason,
		*//*?}*/
		@Nullable SpawnGroupData entityData
		//? if <1.21.1 {
		/*, CompoundTag dataTag
		*///?}
	) {
		SpawnGroupData superEntityData = super.finalizeSpawn(world, difficulty, spawnReason, entityData
			//? if <1.21.1 {
			/*, dataTag
			*///?}
		);

		this.setEntityPose(FriendsAndFoesEntityPose.IDLE);
		return superEntityData;
	}

	@Override
	//? if >= 1.20.5 {
	protected void defineSynchedData(SynchedEntityData.Builder builder)
	//?} else {
	/*protected void defineSynchedData()
	*///?}
	{
		//? if >= 1.20.5 {
		super.defineSynchedData(builder);
		//?} else {
		/*super.defineSynchedData();
		*///?}

		//? if < 1.20.5 {
		/*var builder = this.getEntityData();
		*///?}

		builder.define(ENTITY_POSE, FriendsAndFoesEntityPose.IDLE);
		builder.define(TENTACLE_TARGET_ID, OptionalInt.empty());
	}

	@Override
	//? if >= 26.1 {
	protected Brain<BarnacleEntity> makeBrain(final Brain.Packed packedBrain) {
		return BarnacleBrain.create(this, packedBrain);
	}
	//?} else {
	/*protected Brain<BarnacleEntity> makeBrain(Dynamic<?> dynamic) {
		return BarnacleBrain.create(dynamic);
	}
	*///?}

	@Override
	@SuppressWarnings("all")
	public Brain<BarnacleEntity> getBrain() {
		return (Brain<BarnacleEntity>) super.getBrain();
	}

	@Override
	protected void customServerAiStep(/*? if >=1.21.3 {*/ServerLevel level/*?}*/)
	{
		//? if <1.21.3 {
		/*var level = (ServerLevel) this.level();
		*///?}

		var profiler = VersionedProfilerProvider.getProfiler(this);
		profiler.push("barnacleBrain");
		this.getBrain().tick(level, this);
		profiler.pop();

		profiler.push("barnacleActivityUpdate");
		BarnacleBrain.updateActivities(this);
		profiler.pop();

		if (this.isInEntityPose(FriendsAndFoesEntityPose.ATTACK) && --this.attackAnimationTicks <= 0) {
			this.setEntityPose(FriendsAndFoesEntityPose.IDLE);
		}

		super.customServerAiStep(/*? if >=1.21.3 {*/level/*?}*/);
	}

	public static AttributeSupplier.Builder createBarnacleAttributes() {
		return Monster.createMonsterAttributes()
			.add(Attributes.MAX_HEALTH, 40.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.55D)
			.add(Attributes.ATTACK_DAMAGE, GENERIC_ATTACK_DAMAGE)
			.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
			.add(Attributes.FOLLOW_RANGE, GENERIC_FOLLOW_RANGE);
	}

	@Override
	public void tick() {
		if (!this.level().isClientSide() && !FriendsAndFoes.getConfig().enableBarnacle) {
			this.discard();
		}

		if (this.level().isClientSide()) {
			this.idleAnimationState.animateWhen(this.isInWater(), this.tickCount);

			if (this.hasTentacleTarget()) {
				if (this.clientSideTentacleTicks < TENTACLE_GRAB_DURATION) {
					this.clientSideTentacleTicks++;
				}
			} else if (this.clientSideTentacleTicks > 0) {
				this.clientSideTentacleTicks = Math.max(0, this.clientSideTentacleTicks - TENTACLE_RETRACT_SPEED);
			}
		}

		super.tick();
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		super.onSyncedDataUpdated(key);

		if (TENTACLE_TARGET_ID.equals(key) && this.hasTentacleTarget()) {
			this.clientSideTentacleTarget = null;
			this.clientSideTentacleTicks = this.firstTick ? TENTACLE_GRAB_DURATION : 0;
		}

		if (ENTITY_POSE.equals(key)) {
			if (this.isInEntityPose(FriendsAndFoesEntityPose.TENTACLE_ATTACK)) {
				int animationStartTick = this.firstTick ? this.tickCount - TENTACLE_GRAB_DURATION * 2 : this.tickCount;
				this.tentacleAttackAnimationState.start(animationStartTick);
			} else {
				this.tentacleAttackAnimationState.stop();
			}

			if (this.isInEntityPose(FriendsAndFoesEntityPose.ATTACK)) {
				this.attackAnimationState.start(this.tickCount);
			} else {
				this.attackAnimationState.stop();
			}
		}
	}

	@Override
	public boolean doHurtTarget(/*? if >=1.21.3 {*/ServerLevel level, /*?}*/Entity target) {
		if (!this.isInEntityPose(FriendsAndFoesEntityPose.TENTACLE_ATTACK)) {
			this.setEntityPose(FriendsAndFoesEntityPose.ATTACK);
			this.attackAnimationTicks = ATTACK_ANIMATION_DURATION;
		}

		boolean hasHurtTarget = super.doHurtTarget(/*? if >=1.21.3 {*/level, /*?}*/target);

		//? if <1.21.1 {
		/*if (hasHurtTarget) {
			this.playAttackSound();
		}
		*///?}

		return hasHurtTarget;
	}

	@Override
	/*? if >=1.21.3 {*/
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount)
	/*?} else {*/
	/*public boolean hurt(DamageSource damageSource, float amount)
	*//*?}*/
	{
		/*? if >=1.21.3 {*/
		boolean damageResult = super.hurtServer(level, damageSource, amount);
		/*?} else {*/
		/*boolean damageResult = super.hurt(damageSource, amount);
		*//*?}*/

		if (!damageResult || !(this.level() instanceof ServerLevel serverLevel)) {
			return damageResult;
		}

		BarnacleBrain.onHurt(this);

		if (damageSource.getEntity() instanceof LivingEntity attacker) {
			BarnacleBrain.setAngerTarget(serverLevel, this, attacker);
		}

		return damageResult;
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new WaterBoundPathNavigation(this, world);
	}

	//? if <1.21.1 {
	/*@Override
	public boolean canBreatheUnderwater() {
		return true;
	}
	*///?}

	@Override
	@Nullable
	public LivingEntity getTarget() {
		//? if >= 1.20.6 {
		return this.getTargetFromBrain();
		//?} else {
		/*return this.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
		*///?}
	}

	public static boolean canSpawn(
		EntityType<? extends Monster> type,
		ServerLevelAccessor world,
		/*? if >=1.21.3 {*/
		EntitySpawnReason spawnReason,
		/*?} else {*/
		/*MobSpawnType spawnReason,
		*//*?}*/
		BlockPos pos,
		RandomSource random
	) {
		/*? if >=1.21.3 {*/
		boolean isSpawner = EntitySpawnReason.isSpawner(spawnReason);
		/*?} else {*/
		/*boolean isSpawner = spawnReason == MobSpawnType.SPAWNER;
		*//*?}*/

		return world.getDifficulty() != Difficulty.PEACEFUL
			   && world.getFluidState(pos.below()).is(FluidTags.WATER)
			   && (
				   isSpawner
				   || (
					   world.getFluidState(pos).is(FluidTags.WATER)
					   && VersionedLevel.isNight(world.getLevel())
					   && isValidSpawnDepth(world, pos)
					   && (random.nextInt(20) == 0 || !world.canSeeSkyFromBelowWater(pos))
				   )
			   );
	}

	private static boolean isValidSpawnDepth(LevelAccessor world, BlockPos pos) {
		return pos.getY() < world.getSeaLevel() - 5;
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader level) {
		return level.isUnobstructed(this);
	}

	@Override
	public float getWalkTargetValue(BlockPos pos, LevelReader level) {
		if (level.getBlockState(pos).is(FriendsAndFoesTags.BARNACLE_HIDING_SPOT_BLOCKS)) {
			return 20.0F;
		}

		if (level.getFluidState(pos).is(FluidTags.WATER)) {
			return 10.0F;
		}

		return super.getWalkTargetValue(pos, level);
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return FriendsAndFoes.getConfig().barnacleSpawnMaxGroupSize;
	}

	@Override
	public int getMaxHeadXRot() {
		return 1;
	}

	@Override
	public int getMaxHeadYRot() {
		return 1;
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

	@Override
	protected SoundEvent getDeathSound() {
		return FriendsAndFoesSoundEvents.ENTITY_BARNACLE_DEATH.get();
	}

	//? if >=1.21.1 {
	@Override
	//?}
	protected void playAttackSound() {
		this.playSound(FriendsAndFoesSoundEvents.ENTITY_BARNACLE_ATTACK.get(), 0.5F, RandomGenerator.generateFloat(1.25F, 1.45F));
	}

	public void startTentacleAttackAnimation() {
		if (this.isInEntityPose(FriendsAndFoesEntityPose.TENTACLE_ATTACK)) {
			return;
		}

		this.setEntityPose(FriendsAndFoesEntityPose.TENTACLE_ATTACK);
	}

	public void stopTentacleAttackAnimation() {
		if (!this.isInEntityPose(FriendsAndFoesEntityPose.TENTACLE_ATTACK)) {
			return;
		}

		this.setEntityPose(FriendsAndFoesEntityPose.IDLE);
	}

	@Override
	public void travel(Vec3 travelVector) {
		if (this.isInWater()) {
			this.moveRelative(this.getSpeed(), travelVector);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9));

			if (this.getTarget() == null && !this.hasTentacleTarget()) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
			}
		} else {
			super.travel(travelVector);
		}
	}

	public boolean hasTentacleTarget() {
		return this.entityData.get(TENTACLE_TARGET_ID).isPresent();
	}

	@Nullable
	public Entity getTentacleTarget() {
		OptionalInt tentacleTargetId = this.entityData.get(TENTACLE_TARGET_ID);

		if (!this.level().isClientSide()) {
			return tentacleTargetId.isPresent() ? this.level().getEntity(tentacleTargetId.getAsInt()) : null;
		}

		if (tentacleTargetId.isPresent() && this.clientSideTentacleTarget == null) {
			this.clientSideTentacleTarget = this.level().getEntity(tentacleTargetId.getAsInt());
		}

		if (tentacleTargetId.isEmpty() && this.clientSideTentacleTicks <= 0) {
			this.clientSideTentacleTarget = null;
		}

		return this.clientSideTentacleTarget;
	}

	public void setTentacleTarget(Entity target) {
		this.entityData.set(TENTACLE_TARGET_ID, OptionalInt.of(target.getId()));
	}

	public void eraseTentacleTarget() {
		this.entityData.set(TENTACLE_TARGET_ID, OptionalInt.empty());
	}

	public float getTentacleExtension(float partialTick) {
		float tickDirection = this.hasTentacleTarget() ? partialTick : -partialTick * TENTACLE_RETRACT_SPEED;
		return Mth.clamp((this.clientSideTentacleTicks + tickDirection) / TENTACLE_GRAB_DURATION, 0.0F, 1.0F);
	}

	public Vec3 getMouthPosition() {
		Vec3 bodyCenter = this.position().add(0.0D, 0.5625D, 0.0D);
		Entity tentacleTarget = this.getTentacleTarget();
		Vec3 direction = tentacleTarget == null ? Vec3.ZERO : getTentacleHoldPosition(tentacleTarget).subtract(bodyCenter).normalize();

		if (direction.equals(Vec3.ZERO)) {
			float bodyYaw = this.yBodyRot * Mth.DEG_TO_RAD;
			direction = new Vec3(-Mth.sin(bodyYaw), 0.0D, Mth.cos(bodyYaw));
		}

		return bodyCenter.add(direction.scale(1.03125D));
	}

	public static Vec3 getTentacleHoldPosition(Entity entity) {
		if (isBoat(entity)) {
			return entity.position();
		}

		return entity.position().add(0.0D, entity.getBbHeight() * 0.5D, 0.0D);
	}

	public static Vec3 getTentacleAttachPosition(Entity target, float partialTick) {
		if (target instanceof Player) {
			return target.getRopeHoldPosition(partialTick);
		}

		Vec3 position = target.getPosition(partialTick);

		if (isBoat(target)) {
			return position;
		}

		//? if >= 1.21.6 {
		if (target instanceof Leashable leashable) {
			float bodyRot = target.getPreciseBodyRotation(partialTick) * Mth.DEG_TO_RAD;
			return position.add(leashable.getLeashOffset(partialTick).yRot(-bodyRot));
		}
		//?} else if >= 1.21.1 {
		/*if (target instanceof Mob mob) {
			float bodyRot = mob.getPreciseBodyRotation(partialTick) * Mth.DEG_TO_RAD;
			return position.add(mob.getLeashOffset(partialTick).yRot(-bodyRot));
		}
		*///?} else {
		/*if (target instanceof Mob mob) {
			float bodyRot = Mth.rotLerp(partialTick, mob.yBodyRotO, mob.yBodyRot) * Mth.DEG_TO_RAD;
			return position.add(mob.getLeashOffset(partialTick).yRot(-bodyRot));
		}
		*///?}

		return position.add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
	}

	public boolean isValidTentacleTarget(@Nullable LivingEntity target) {
		if (!(target instanceof Player player) || !player.isAlive() || player.level() != this.level()) {
			return false;
		}

		if (player.isCreative() || player.isSpectator()) {
			return false;
		}

		return isInWaterOrBoat(player);
	}

	public static boolean isInWaterOrBoat(Entity entity) {
		if (isTouchingWater(entity)) {
			return true;
		}

		Entity vehicle = entity.getVehicle();

		return isBoat(vehicle) && vehicle.isInWater();
	}

	public static boolean isTouchingWater(Entity entity) {
		if (entity.isInWater()) {
			return true;
		}

		BlockPos pos = entity.blockPosition();
		Level level = entity.level();

		return level.getFluidState(pos).is(FluidTags.WATER) || level.getFluidState(pos.below()).is(FluidTags.WATER);
	}

	public static boolean isBoat(@Nullable Entity entity) {
		//? if >= 1.21.4 {
		return entity instanceof AbstractBoat;
		//?} else {
		/*return entity instanceof Boat;
		*///?}
	}

	@Nullable
	public GlobalPos getHidingSpotPos() {
		return this.getBrain().getMemory(FriendsAndFoesMemoryModuleTypes.BARNACLE_HIDING_SPOT_POS.get()).orElse(null);
	}

	public static boolean isHidingSpot(LevelReader level, BlockPos pos) {
		return level.getBlockState(pos).is(FriendsAndFoesTags.BARNACLE_HIDING_SPOT_BLOCKS)
			   && level.getFluidState(pos.above()).is(FluidTags.WATER)
			   && !level.getFluidState(pos.below()).is(FluidTags.WATER);
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
