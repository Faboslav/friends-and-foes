//? if <= 1.21.8 {
/*package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.CopperGolemAnimations;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CopperGolemBrain;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.pose.FriendsAndFoesEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityDataSerializers;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.mixin.LimbAnimatorAccessor;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.util.MovementUtil;
import com.faboslav.friendsandfoes.common.util.particle.ParticleSpawner;
import com.faboslav.friendsandfoes.common.versions.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

//? if >=1.21.6 {
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
//?}

//? if >=1.21.3 {
import net.minecraft.world.entity.EntitySpawnReason;
//?} else {
/^import net.minecraft.world.entity.MobSpawnType;
 ^///?}

public final class CopperGolemEntity extends AbstractGolem implements AnimatedEntity
{
	private static final String POSE_NBT_NAME = "Pose";
	private static final String POSE_TICKS_NBT_NAME = "PoseTicks";
	private static final String OXIDATION_LEVEL_NBT_NAME = "OxidationLevel";
	private static final String IS_WAXED_NBT_NAME = "IsWaxed";
	private static final String ENTITY_SNAPSHOT_NBT_NAME = "EntitySnapshot";

	private AnimationContextTracker animationContextTracker;
	private static final EntityDataAccessor<Integer> POSE_TICKS = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<FriendsAndFoesEntityPose> ENTITY_POSE = SynchedEntityData.defineId(CopperGolemEntity.class, FriendsAndFoesEntityDataSerializers.ENTITY_POSE);
	private static final EntityDataAccessor<Integer> OXIDATION_LEVEL = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> STRUCT_BY_LIGHTNING_TICKS = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> WAS_STATUE = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_WAXED = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<CompoundTag> ENTITY_SNAPSHOT = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.COMPOUND_TAG);

	private static final float MOVEMENT_SPEED = 0.2F;
	private static final int COPPER_INGOT_HEAL_AMOUNT = 5;
	private static final float SPARK_CHANCE = 0.025F;
	private static final float OXIDATION_CHANCE = 0.00002F;
	public static final int MIN_STRUCT_BY_LIGHTNING_TICKS = 1200;
	public static final int MAX_STRUCT_BY_LIGHTNING_TICKS = 2400;

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
		return CopperGolemAnimations.TRACKED_ANIMATIONS;
	}

	@Override
	public AnimationHolder getMovementAnimation() {
		return CopperGolemAnimations.WALK;
	}

	@Override
	public int getCurrentAnimationTick() {
		return this.entityData.get(POSE_TICKS);
	}

	@Override
	public void setCurrentAnimationTick(int keyframeAnimationTicks) {
		this.entityData.set(POSE_TICKS, keyframeAnimationTicks);
	}

	public CopperGolemEntity(
		EntityType<? extends CopperGolemEntity> entityType,
		Level world
	) {
		super(entityType, world);
		this.moveControl = new CopperGolemEntity.CopperGolemMoveControl(this);
		this.lookControl = new CopperGolemEntity.CopperGolemLookControl(this);
		this.jumpControl = new CopperGolemEntity.CopperGolemJumpControl(this);
	}

	@Override
	public SpawnGroupData finalizeSpawn(
		ServerLevelAccessor world,
		DifficultyInstance difficulty,
		/^? if >=1.21.3 {^/
		EntitySpawnReason spawnReason,
		/^?} else {^/
		/^MobSpawnType spawnReason,
		 ^//^?}^/
		@Nullable SpawnGroupData entityData
	) {
		SpawnGroupData superEntityData = super.finalizeSpawn(world, difficulty, spawnReason, entityData);

		if (spawnReason == VersionedEntitySpawnReason.STRUCTURE) {
			return superEntityData;
		}

		this.setEntityPose(FriendsAndFoesEntityPose.IDLE);
		CopperGolemBrain.setSpinHeadCooldown(this);
		CopperGolemBrain.setPressButtonCooldown(this);

		return superEntityData;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);

		builder.define(POSE_TICKS, 0);
		builder.define(ENTITY_POSE, FriendsAndFoesEntityPose.IDLE);
		builder.define(OXIDATION_LEVEL, WeatheringCopper.WeatherState.UNAFFECTED.ordinal());
		builder.define(STRUCT_BY_LIGHTNING_TICKS, 0);
		builder.define(WAS_STATUE, false);
		builder.define(IS_WAXED, false);
		builder.define(ENTITY_SNAPSHOT, new CompoundTag());
	}

	@Override
	//? if >= 1.21.6 {
	public void addAdditionalSaveData(ValueOutput nbt)
	//?} else {
	/^public void addAdditionalSaveData(CompoundTag nbt)
	^///?}
	{
		super.addAdditionalSaveData(nbt);
		nbt.putInt(OXIDATION_LEVEL_NBT_NAME, this.getOxidationLevel().ordinal());
		nbt.putBoolean(IS_WAXED_NBT_NAME, this.isWaxed());

		if (isOxidized()) {
			nbt.putString(POSE_NBT_NAME, this.getEntityPose().name());
			nbt.putInt(POSE_TICKS_NBT_NAME, this.getCurrentAnimationTick());

			var entitySnapshot = this.takeEntitySnapshot();

			//? if >= 1.21.6 {
			nbt.store(ENTITY_SNAPSHOT_NBT_NAME, EntitySnapshot.CODEC, entitySnapshot);
			//?} else {
			/^nbt.put(ENTITY_SNAPSHOT_NBT_NAME, EntitySnapshot.toNbt(entitySnapshot));
			 ^///?}
		}
	}

	@Override
	//? if >= 1.21.6 {
	public void readAdditionalSaveData(ValueInput nbt)
	//?} else {
	/^public void readAdditionalSaveData(CompoundTag nbt)
	^///?}
	{
		super.readAdditionalSaveData(nbt);

		String savedEntityPose = VersionedNbt.getString(nbt, POSE_NBT_NAME, FriendsAndFoesEntityPose.IDLE.name());
		FriendsAndFoesEntityPose entityPose;

		try {
			entityPose = FriendsAndFoesEntityPose.valueOf(savedEntityPose);
		} catch (Exception e) {
			entityPose = FriendsAndFoesEntityPose.IDLE;
		}

		this.setEntityPose(entityPose);

		this.setCurrentAnimationTick(VersionedNbt.getInt(nbt, POSE_TICKS_NBT_NAME, 0));
		this.setOxidationLevel(WeatheringCopper.WeatherState.values()[VersionedNbt.getInt(nbt, OXIDATION_LEVEL_NBT_NAME, 0)]);
		this.setIsWaxed(VersionedNbt.getBoolean(nbt, IS_WAXED_NBT_NAME, false));
		//? if >= 1.21.6 {
		this.setEntitySnapshot(nbt.read(ENTITY_SNAPSHOT_NBT_NAME, EntitySnapshot.CODEC).orElseGet(this::takeEntitySnapshot));
		//?} else {
		/^this.setEntitySnapshot(EntitySnapshot.fromNbt(VersionedNbt.getCompound(nbt, ENTITY_SNAPSHOT_NBT_NAME)));
		^///?}

		if(this.isOxidized()) {
			this.applyEntitySnapshot();
		}
	}

	public static AttributeSupplier.Builder createCopperGolemAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 20.0D)
			.add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
			//? if >= 1.21.4 {
			.add(Attributes.TEMPT_RANGE, 10.0D)
			//?}
			.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	protected BodyRotationControl createBodyControl() {
		return new CopperGolemEntity.CopperGolemBodyRotationControl(this);
	}

	@Override
	protected Brain<?> makeBrain(Dynamic<?> dynamic) {
		return CopperGolemBrain.create(dynamic);
	}

	@Override
	@SuppressWarnings("all")
	public Brain<CopperGolemEntity> getBrain() {
		return (Brain<CopperGolemEntity>) super.getBrain();
	}

	public void setEntitySnapshot(EntitySnapshot entitySnapshot) {
		entityData.set(ENTITY_SNAPSHOT, EntitySnapshot.toNbt(entitySnapshot));
	}

	public void applyEntitySnapshot() {
		EntitySnapshot entitySnapshot = this.getEntitySnapshot();

		if (this.isPassenger() == false) {
			this.yRotO = entitySnapshot.prevYaw;
			this.setYRot(this.yRotO);
			this.yBodyRotO = entitySnapshot.prevBodyYaw;
			this.yBodyRot = this.yBodyRotO;
			this.lerpYHeadRot = entitySnapshot.serverHeadYaw;
			this.yHeadRotO = entitySnapshot.prevHeadYaw;
			this.yHeadRot = this.yHeadRotO;
			//this.animStepO = VersionedNbt.getFloat(entitySnapshot, "prevLookDirection", 0.0F);
			//this.animStep = this.animStepO;
		}

		this.xRotO = entitySnapshot.prevPitch;
		this.setXRot(this.xRotO);
		this.oAttackAnim = entitySnapshot.lastHandSwingProgress;
		this.attackAnim = this.oAttackAnim;
		((LimbAnimatorAccessor) this.walkAnimation).setPrevSpeed(entitySnapshot.limbAnimatorPrevSpeed);
		this.walkAnimation.setSpeed(entitySnapshot.limbAnimatorSpeed);
		((LimbAnimatorAccessor) this.walkAnimation).setPos(entitySnapshot.limbAnimatorPos);
		//this.oRun = entitySnapshot.getFloat("prevStepBobbingAmount");
		//this.run = this.oRun;
	}

	public EntitySnapshot getEntitySnapshot() {
		return EntitySnapshot.fromNbt(this.entityData.get(ENTITY_SNAPSHOT));
	}

	private EntitySnapshot takeEntitySnapshot() {
		return new EntitySnapshot(
			this.yRotO,
			this.xRotO,
			this.yBodyRotO,
			this.lerpYHeadRot,
			this.yHeadRotO,
			this.oAttackAnim,
			((LimbAnimatorAccessor) this.walkAnimation).getPresSpeed(),
			this.walkAnimation.speed(),
			this.walkAnimation.position()
		);
	}

	@Override
	protected int decreaseAirSupply(int air) {
		return air;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		var oxidationLevel = this.getOxidationLevel();

		if(oxidationLevel == WeatheringCopper.WeatherState.OXIDIZED) {
			return FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_OXIDIZED_HURT.get();
		} else if(oxidationLevel == WeatheringCopper.WeatherState.WEATHERED) {
			return FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_WEATHERED_HURT.get();
		}

		return FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		var oxidationLevel = this.getOxidationLevel();

		if(oxidationLevel == WeatheringCopper.WeatherState.OXIDIZED) {
			return FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_OXIDIZED_DEATH.get();
		} else if(oxidationLevel == WeatheringCopper.WeatherState.WEATHERED) {
			return FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_WEATHERED_DEATH.get();
		}

		return FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_DEATH.get();
	}

	@Override
	protected void playStepSound(
		BlockPos pos,
		BlockState state
	) {
		if (
			this.isImmobilized()
			|| state.liquid()
		) {
			return;
		}

		BlockState blockState = this.level().getBlockState(pos.above());
		SoundType blockSoundGroup = blockState.is(BlockTags.INSIDE_STEP_SOUND_BLOCKS) ? blockState.getSoundType():state.getSoundType();

		var oxidationLevel = this.getOxidationLevel();
		SoundEvent stepSound;

		if(oxidationLevel == WeatheringCopper.WeatherState.OXIDIZED) {
			stepSound = FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_OXIDIZED_STEP.get();
		} else if(oxidationLevel == WeatheringCopper.WeatherState.WEATHERED) {
			stepSound = FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_WEATHERED_STEP.get();
		} else {
			stepSound = FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_STEP.get();
		}

		this.playSound(stepSound, blockSoundGroup.getVolume() * 0.15F, this.getVoicePitch());
	}

	@Override
		/^? if >=1.21.3 {^/
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount)
		/^?} else {^/
		/^public boolean hurt(DamageSource damageSource, float amount)
		 ^//^?}^/
	{
		Entity attacker = damageSource.getEntity();

		if (
			attacker instanceof LightningBolt
			|| damageSource == this.damageSources().sweetBerryBush()
		) {
			return false;
		}

		/^? if >=1.21.3 {^/
		return super.hurtServer(level, damageSource, amount);
		/^?} else {^/
		/^return super.hurt(damageSource, amount);
		 ^//^?}^/
	}

	@Override
	public Vec3 getLeashOffset() {
		return new Vec3(0.0D, this.getEyeHeight() * 0.45D, 0.0D);
	}

	public float getMovementSpeedModifier() {
		if (this.isStructByLightning()) {
			return (MOVEMENT_SPEED + MOVEMENT_SPEED / 2.0F) / MOVEMENT_SPEED;
		}

		return (MOVEMENT_SPEED - this.getOxidationModifier() * 0.03333333333F) / MOVEMENT_SPEED;
	}

	@Override
	public float getAnimationSpeedModifier() {
		if (this.isStructByLightning()) {
			return MOVEMENT_SPEED / (MOVEMENT_SPEED + MOVEMENT_SPEED / 2.0F);
		}

		return MOVEMENT_SPEED / (MOVEMENT_SPEED - (this.getOxidationModifier() * 0.03333333333F));
	}

	private int getOxidationModifier() {
		return Math.min(this.getOxidationLevel().ordinal(), WeatheringCopper.WeatherState.WEATHERED.ordinal());
	}

	@Override
	public InteractionResult mobInteract(
		Player player,
		InteractionHand hand
	) {
		ItemStack itemStack = player.getItemInHand(hand);
		Item itemInHand = itemStack.getItem();
		boolean interactionResult = false;

		if (itemInHand == Items.COPPER_INGOT) {
			interactionResult = this.tryToInteractMobWithCopperIngot(player, itemStack);
		} else if (itemInHand == Items.HONEYCOMB) {
			interactionResult = this.tryToInteractMobWithHoneycomb(player, itemStack);
		} else if (itemInHand instanceof AxeItem) {
			interactionResult = this.tryToInteractMobWithAxe(player, hand, itemStack);
		} else {
			interactionResult = this.tryToInteractMobWithHand();
		}

		if (interactionResult) {
			this.gameEvent(GameEvent.ENTITY_INTERACT, this);
			return VersionedInteractionResult.success(this);
		}

		return super.mobInteract(player, hand);
	}

	private boolean tryToInteractMobWithCopperIngot(
		Player player,
		ItemStack itemStack
	) {
		if (this.getHealth() == this.getMaxHealth()) {
			return false;
		}

		if (this.level() instanceof ServerLevel serverLevel) {
			this.heal(COPPER_INGOT_HEAL_AMOUNT);
			itemStack.consume(1, player);
			this.playSound(FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_REPAIR.get(), 1.0F, this.getVoicePitch() - 1.0F);
		}

		return true;
	}

	private boolean tryToInteractMobWithHoneycomb(
		Player player,
		ItemStack itemStack
	) {
		if (this.isWaxed() || this.isImmobilized()) {
			return false;
		}

		if (this.level() instanceof ServerLevel serverLevel) {
			this.setIsWaxed(true);

			if (!player.getAbilities().instabuild) {
				itemStack.shrink(1);
			}

			this.playSound(SoundEvents.HONEYCOMB_WAX_ON, 1.0F, 1.0F);
			ParticleSpawner.spawnParticles(this, ParticleTypes.WAX_ON, 7, 1.0);
		}

		return true;
	}

	private boolean tryToInteractMobWithAxe(
		Player player,
		InteractionHand hand,
		ItemStack itemStack
	) {
		if ((!this.isWaxed() && !this.isDegraded()) || (this.wasStatue() && !this.isOxidized())) {
			return false;
		}

		if (this.level() instanceof ServerLevel serverLevel) {
			if (this.isWaxed()) {
				this.setIsWaxed(false);

				this.playSound(SoundEvents.AXE_WAX_OFF, 1.0F, 1.0F);
				ParticleSpawner.spawnParticles(this, ParticleTypes.WAX_OFF, 7, 1.0);

			} else if (isDegraded()) {
				int increasedOxidationLevelOrdinal = getOxidationLevel().ordinal() - 1;
				WeatheringCopper.WeatherState[] OxidationLevels = WeatheringCopper.WeatherState.values();
				this.setOxidationLevel(OxidationLevels[increasedOxidationLevelOrdinal]);

				this.playSound(SoundEvents.AXE_SCRAPE, 1.0F, 1.0F);
				ParticleSpawner.spawnParticles(this, ParticleTypes.SCRAPE, 7, 1.0);
			}

			if (!player.getAbilities().instabuild) {
				itemStack.hurtAndBreak(1, player, VersionedEntity.getEquipmentSlotForItem(hand));
			}
		}

		return true;
	}

	private boolean tryToInteractMobWithHand() {
		if (this.isWaxed() || !this.isOxidized()) {
			return false;
		}

		if (this.level() instanceof ServerLevel) {
			ArrayList<AnimationHolder> possibleAnimations = new ArrayList<>()
			{{
				add(CopperGolemAnimations.IDLE);
				add(CopperGolemAnimations.SPIN_HEAD);
				add(CopperGolemAnimations.PRESS_BUTTON_UP);
				add(CopperGolemAnimations.PRESS_BUTTON_DOWN);
			}};
			int randomPoseIndex = this.getRandom().nextInt(possibleAnimations.size());
			AnimationHolder randomAnimation = possibleAnimations.get(randomPoseIndex);
			var copperGolemEntityPose = FriendsAndFoesEntityPose.IDLE;

			if(randomAnimation == CopperGolemAnimations.SPIN_HEAD) {
				copperGolemEntityPose = FriendsAndFoesEntityPose.SPIN_HEAD;
			} else if(randomAnimation == CopperGolemAnimations.PRESS_BUTTON_UP) {
				copperGolemEntityPose = FriendsAndFoesEntityPose.PRESS_BUTTON_UP;
			} else if(randomAnimation == CopperGolemAnimations.PRESS_BUTTON_DOWN) {
				copperGolemEntityPose = FriendsAndFoesEntityPose.PRESS_BUTTON_DOWN;
			}

			this.setEntityPose(copperGolemEntityPose);
			int keyFrameAnimationLengthInTicks = randomAnimation.get().lengthInTicks();
			int randomKeyframeAnimationTick = this.getRandom().nextIntBetweenInclusive(0, keyFrameAnimationLengthInTicks);
			this.setCurrentAnimationTick(randomKeyframeAnimationTick);
			this.playSound(FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_OXIDATION.get(), this.getSoundVolume(), this.getVoicePitch());
		}

		return true;
	}

	@Override
	protected void updateWalkAnimation(float f) {
		if(this.isImmobilized()) {
			return;
		}

		super.updateWalkAnimation(f);
	}

	@Override
	protected void customServerAiStep(/^? if >=1.21.3 {^/ServerLevel level/^?}^/)
	{
		//? if <1.21.3 {
		/^var level = (ServerLevel) this.level();
		 ^///?}

		if (this.isImmobilized()) {
			super.customServerAiStep(/^? if >=1.21.3 {^/level/^?}^/);
			return;
		}

		var profiler = VersionedProfilerProvider.getProfiler(this);

		profiler.push("copperGolemBrain");
		this.getBrain().tick(level, this);
		profiler.pop();

		profiler.push("copperGolemActivityUpdate");
		CopperGolemBrain.updateActivities(this);
		profiler.pop();

		super.customServerAiStep(/^? if >=1.21.3 {^/level/^?}^/);
	}

	@Override
	public void tick() {
		if (!FriendsAndFoes.getConfig().enableCopperGolem) {
			this.discard();
		}

		this.updateKeyframeAnimations();
		super.tick();

		if (this.isOxidized()) {
			this.applyEntitySnapshot();
			MovementUtil.stopMovement(this);
			return;
		}

		if (this.isStructByLightning() && !this.level().isClientSide()) {
			this.setStructByLightningTicks(this.getStructByLightningTicks() - 1);

			if (this.getRandom().nextFloat() < SPARK_CHANCE) {
				for (int i = 0; i < 7; i++) {
					((ServerLevel) this.level()).sendParticles(
						ParticleTypes.ELECTRIC_SPARK,
						this.getRandomX(0.35D),
						this.getRandomY() + 0.25D,
						this.getRandomZ(0.35D),
						1,
						this.getRandom().nextGaussian() * 0.01D,
						this.getRandom().nextGaussian() * 0.01D,
						this.getRandom().nextGaussian() * 0.01D,
						0.1D
					);
				}
			}
		}

		this.handleOxidationIncrease();
	}

	@Override
	public boolean isPushable() {
		return !this.isImmobilized();
	}

	@Override
	public void thunderHit(
		ServerLevel serverWorld,
		LightningBolt lightning
	) {
		super.thunderHit(serverWorld, lightning);

		this.setRemainingFireTicks(0);
		this.setSharedFlagOnFire(false);
		this.setHealth(this.getMaxHealth());

		if (this.isDegraded()) {
			ParticleSpawner.spawnParticles(this, ParticleTypes.WAX_OFF, 7, 1.0);
		}

		if (!this.level().isClientSide()) {
			this.refreshStructByLightningTicks();

			if (!this.isWaxed()) {
				this.setOxidationLevel(WeatheringCopper.WeatherState.UNAFFECTED);
			}
		}
	}

	private void updateKeyframeAnimations() {
		if (!this.level().isClientSide() && !this.isOxidized()) {
			this.updateCurrentAnimationTick();
		}

		AnimationHolder keyframeAnimationToStart = this.getAnimationByPose();

		if (keyframeAnimationToStart != null) {
			this.tryToStartAnimation(keyframeAnimationToStart);
		}
	}

	@Override
	public void updateCurrentAnimationTick() {
		if (!this.isAnyKeyframeAnimationRunning()) {
			return;
		}

		this.setCurrentAnimationTick(this.getCurrentAnimationTick() - 1);

		if (
			!this.level().isClientSide()
			&& this.wasStatue()
			&& this.getCurrentAnimationTick() == 1
		) {
			this.setEntityPose(FriendsAndFoesEntityPose.IDLE);
			this.setWasStatue(false);
		}

		if (this.getCurrentAnimationTick() > 1) {
			return;
		}

		for (AnimationHolder animation : this.getTrackedAnimations()) {
			if (!animation.get().looping()) {
				continue;
			}

			var keyframeAnimationContext = this.getAnimationContextTracker().get(animation);
			if (!keyframeAnimationContext.isRunning()) {
				continue;
			}

			this.setCurrentAnimationTick(animation.get().lengthInTicks(this.getAnimationSpeedModifier()));
		}
	}

	@Nullable
	public AnimationHolder getAnimationByPose() {
		AnimationHolder animationHolder = null;

		if (this.isInEntityPose(FriendsAndFoesEntityPose.IDLE)) {
			animationHolder = CopperGolemAnimations.IDLE;
		} else if (this.isInEntityPose(FriendsAndFoesEntityPose.SPIN_HEAD)) {
			animationHolder = CopperGolemAnimations.SPIN_HEAD;
		} else if (this.isInEntityPose(FriendsAndFoesEntityPose.PRESS_BUTTON_UP)) {
			animationHolder = CopperGolemAnimations.PRESS_BUTTON_UP;
		} else if (this.isInEntityPose(FriendsAndFoesEntityPose.PRESS_BUTTON_DOWN)) {
			animationHolder = CopperGolemAnimations.PRESS_BUTTON_DOWN;
		}

		return animationHolder;
	}

	private void tryToStartAnimation(AnimationHolder animationHolder) {
		if (this.isKeyframeAnimationRunning(animationHolder)) {
			return;
		}

		if (this.level().isClientSide() == false && this.isOxidized() == false) {
			this.setCurrentAnimationTick(animationHolder.get().lengthInTicks(this.getAnimationSpeedModifier()));
		}

		this.startKeyframeAnimation(animationHolder);
	}

	private void startKeyframeAnimation(AnimationHolder animationToStart) {
		for (AnimationHolder animation : this.getTrackedAnimations()) {
			if (animation == animationToStart) {
				continue;
			}

			this.stopKeyframeAnimation(animation);
		}

		int initialTick = this.isOxidized() ? this.tickCount - this.getCurrentKeyframeAnimationTick():this.tickCount;
		this.startKeyframeAnimation(animationToStart, initialTick);
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


	public void startSpinHeadAnimation() {
		if (this.isInEntityPose(FriendsAndFoesEntityPose.SPIN_HEAD)) {
			return;
		}

		var oxidationLevel = this.getOxidationLevel();
		SoundEvent spinHeadSound;

		if(oxidationLevel == WeatheringCopper.WeatherState.OXIDIZED) {
			spinHeadSound = FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_OXIDIZED_SPIN.get();
		} else if(oxidationLevel == WeatheringCopper.WeatherState.WEATHERED) {
			spinHeadSound = FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_WEATHERED_SPIN.get();
		} else {
			spinHeadSound = FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_SPIN.get();
		}

		this.playSound(spinHeadSound, this.getSoundVolume(), this.getVoicePitch());
		this.gameEvent(GameEvent.ENTITY_ACTION);
		this.setEntityPose(FriendsAndFoesEntityPose.SPIN_HEAD);
	}

	public void startPressButtonUpAnimation() {
		if (this.isInEntityPose(FriendsAndFoesEntityPose.PRESS_BUTTON_UP)) {
			return;
		}

		this.gameEvent(GameEvent.ENTITY_ACTION);
		this.setEntityPose(FriendsAndFoesEntityPose.PRESS_BUTTON_UP);
	}

	public void startPressButtonDownAnimation() {
		if (this.isInEntityPose(FriendsAndFoesEntityPose.PRESS_BUTTON_DOWN)) {
			return;
		}

		this.gameEvent(GameEvent.ENTITY_ACTION);
		this.setEntityPose(FriendsAndFoesEntityPose.PRESS_BUTTON_DOWN);
	}

	public void handleOxidationIncrease() {
		if (this.level().isClientSide() || this.isImmobilized() || this.isWaxed()) {
			return;
		}

		if (this.getRandom().nextFloat() < OXIDATION_CHANCE) {
			int degradedOxidationLevelOrdinal = getOxidationLevel().ordinal() + 1;
			WeatheringCopper.WeatherState[] OxidationLevels = WeatheringCopper.WeatherState.values();
			this.setOxidationLevel(OxidationLevels[degradedOxidationLevelOrdinal]);
			this.playSound(FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_OXIDATION.get(), this.getSoundVolume(), this.getVoicePitch());
		}
	}

	public boolean isOxidized() {
		return this.getOxidationLevel() == WeatheringCopper.WeatherState.OXIDIZED;
	}

	public boolean isDegraded() {
		return this.getOxidationLevel().ordinal() > WeatheringCopper.WeatherState.UNAFFECTED.ordinal();
	}

	public WeatheringCopper.WeatherState getOxidationLevel() {
		return WeatheringCopper.WeatherState.values()[this.entityData.get(OXIDATION_LEVEL)];
	}

	public void setOxidationLevel(WeatheringCopper.WeatherState oxidationLevel) {
		this.entityData.set(OXIDATION_LEVEL, oxidationLevel.ordinal());
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(MOVEMENT_SPEED * this.getMovementSpeedModifier());

		if (this.isOxidized()) {
			this.setWasStatue(true);
		}

		if (this.isOxidized() && this.getBrain().getMemoryInternal(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get()).isEmpty()) {
			this.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get(), true);
			this.becomeStatue();
		} else if (this.isOxidized() == false && this.getBrain().getMemoryInternal(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get()).isPresent()) {
			this.getBrain().eraseMemory(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get());
			this.becomeEntity();
		}
	}

	public void setStructByLightningTicks(int structByLightningTicks) {
		this.entityData.set(STRUCT_BY_LIGHTNING_TICKS, structByLightningTicks);
	}

	public int getStructByLightningTicks() {
		return this.entityData.get(STRUCT_BY_LIGHTNING_TICKS);
	}

	public void refreshStructByLightningTicks() {
		this.setStructByLightningTicks(this.getRandom().nextIntBetweenInclusive(MIN_STRUCT_BY_LIGHTNING_TICKS, MAX_STRUCT_BY_LIGHTNING_TICKS));
	}

	public boolean isStructByLightning() {
		return this.getStructByLightningTicks() > 0;
	}

	@Nullable
	public GlobalPos getButtonPos() {
		return this.getBrain().getMemoryInternal(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get()).orElse(null);
	}

	public boolean isButtonValidToBePressed(BlockPos pos) {
		return this.level().getBlockState(pos).is(FriendsAndFoesTags.COPPER_BUTTONS);
	}

	private void becomeStatue() {
		this.setEntitySnapshot(this.takeEntitySnapshot());
	}

	private void becomeEntity() {
		CopperGolemBrain.setSpinHeadCooldown(this);
		CopperGolemBrain.setPressButtonCooldown(this);
	}

	public boolean isWaxed() {
		return this.entityData.get(IS_WAXED);
	}

	public void setIsWaxed(boolean isWaxed) {
		this.entityData.set(IS_WAXED, isWaxed);
	}

	public boolean wasStatue() {
		return this.entityData.get(WAS_STATUE);
	}

	public void setWasStatue(boolean wasStatue) {
		this.entityData.set(WAS_STATUE, wasStatue);
	}

	public boolean isImmobilized() {
		return this.isOxidized() || this.wasStatue();
	}

	public void setSpawnYaw(float yaw) {
		this.yRotO = yaw;
		this.setYRot(yaw);
		this.yBodyRotO = yaw;
		this.yBodyRot = yaw;
		this.lerpYHeadRot = yaw;
		this.yHeadRotO = yaw;
		this.yHeadRot = yaw;
	}

	final class CopperGolemMoveControl extends MoveControl
	{
		public CopperGolemMoveControl(final CopperGolemEntity copperGolem) {
			super(copperGolem);
		}

		@Override
		public void tick() {
			if (CopperGolemEntity.this.isImmobilized()) {
				return;
			}

			super.tick();
		}
	}

	final class CopperGolemLookControl extends LookControl
	{
		public CopperGolemLookControl(final CopperGolemEntity copperGolem) {
			super(copperGolem);
		}

		@Override
		public void tick() {
			if (CopperGolemEntity.this.isImmobilized()) {
				return;
			}

			super.tick();
		}
	}

	final class CopperGolemJumpControl extends JumpControl
	{
		public CopperGolemJumpControl(final CopperGolemEntity copperGolem) {
			super(copperGolem);
		}

		public void tick() {
			if (CopperGolemEntity.this.isImmobilized()) {
				CopperGolemEntity.this.setJumping(false);
			}

			super.tick();
		}
	}

	final class CopperGolemBodyRotationControl extends BodyRotationControl
	{
		public CopperGolemBodyRotationControl(final CopperGolemEntity copperGolem) {
			super(copperGolem);
		}

		public void clientTick() {
			if (CopperGolemEntity.this.isImmobilized()) {
				return;
			}

			super.clientTick();
		}
	}

	public record EntitySnapshot(
		float prevYaw,
		float prevPitch,
		float prevBodyYaw,
		double serverHeadYaw,
		float prevHeadYaw,
		float lastHandSwingProgress,
		float limbAnimatorPrevSpeed,
		float limbAnimatorSpeed,
		float limbAnimatorPos
	) implements StringRepresentable {
		public static final Codec<EntitySnapshot> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.FLOAT.fieldOf("prevYaw").forGetter(EntitySnapshot::prevYaw),
			Codec.FLOAT.fieldOf("prevPitch").forGetter(EntitySnapshot::prevPitch),
			Codec.FLOAT.fieldOf("prevBodyYaw").forGetter(EntitySnapshot::prevBodyYaw),
			Codec.DOUBLE.fieldOf("serverHeadYaw").forGetter(EntitySnapshot::serverHeadYaw),
			Codec.FLOAT.fieldOf("prevHeadYaw").forGetter(EntitySnapshot::prevHeadYaw),
			Codec.FLOAT.fieldOf("lastHandSwingProgress").forGetter(EntitySnapshot::lastHandSwingProgress),
			Codec.FLOAT.fieldOf("limbAnimatorPrevSpeed").forGetter(EntitySnapshot::limbAnimatorPrevSpeed),
			Codec.FLOAT.fieldOf("limbAnimatorSpeed").forGetter(EntitySnapshot::limbAnimatorSpeed),
			Codec.FLOAT.fieldOf("limbAnimatorPos").forGetter(EntitySnapshot::limbAnimatorPos)
		).apply(instance, EntitySnapshot::new));

		public static CompoundTag toNbt(EntitySnapshot snapshot) {
			CompoundTag tag = new CompoundTag();
			tag.putFloat("prevYaw", snapshot.prevYaw);
			tag.putFloat("prevPitch", snapshot.prevPitch);
			tag.putFloat("prevBodyYaw", snapshot.prevBodyYaw);
			tag.putDouble("serverHeadYaw", snapshot.serverHeadYaw);
			tag.putFloat("prevHeadYaw", snapshot.prevHeadYaw);
			tag.putFloat("lastHandSwingProgress", snapshot.lastHandSwingProgress);
			tag.putFloat("limbAnimatorPrevSpeed", snapshot.limbAnimatorPrevSpeed);
			tag.putFloat("limbAnimatorSpeed", snapshot.limbAnimatorSpeed);
			tag.putFloat("limbAnimatorPos", snapshot.limbAnimatorPos);
			return tag;
		}

		public static EntitySnapshot fromNbt(CompoundTag tag) {
			return new EntitySnapshot(
				VersionedNbt.getFloat(tag, "prevYaw", 0.0F),
				VersionedNbt.getFloat(tag, "prevPitch", 0.0F),
				VersionedNbt.getFloat(tag, "prevBodyYaw", 0.0F),
				VersionedNbt.getDouble(tag, "serverHeadYaw", 0.0D),
				VersionedNbt.getFloat(tag, "prevHeadYaw", 0.0F),
				VersionedNbt.getFloat(tag, "lastHandSwingProgress", 0.0F),
				VersionedNbt.getFloat(tag, "limbAnimatorPrevSpeed", 0.0F),
				VersionedNbt.getFloat(tag, "limbAnimatorSpeed", 0.0F),
				VersionedNbt.getFloat(tag, "limbAnimatorPos", 0.0F)
			);
		}

		@Override
		public String getSerializedName() {
			return "";
		}
	}
}
*///?}