package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.CopperGolemAnimations;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CopperGolemBrain;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.pose.CopperGolemEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.mixin.LimbAnimatorAccessor;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.util.MovementUtil;
import com.faboslav.friendsandfoes.common.util.particle.ParticleSpawner;
import com.faboslav.friendsandfoes.common.versions.VersionedEntitySpawnReason;
import com.faboslav.friendsandfoes.common.versions.VersionedInteractionResult;
import com.faboslav.friendsandfoes.common.versions.VersionedNbt;
import com.faboslav.friendsandfoes.common.versions.VersionedProfilerProvider;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.entity.monster.creaking.Creaking;
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

//? >=1.21.3 {
import net.minecraft.world.entity.EntitySpawnReason;
//?} else {
/*import net.minecraft.world.entity.MobSpawnType;
 *///?}

public final class CopperGolemEntity extends AbstractGolem implements AnimatedEntity
{
	private AnimationContextTracker animationContextTracker;
	private static final EntityDataAccessor<Integer> POSE_TICKS;

	private static final float MOVEMENT_SPEED = 0.2F;
	private static final int COPPER_INGOT_HEAL_AMOUNT = 5;
	private static final float SPARK_CHANCE = 0.025F;
	private static final float OXIDATION_CHANCE = 0.00002F;
	public static final int MIN_STRUCT_BY_LIGHTNING_TICKS = 1200;
	public static final int MAX_STRUCT_BY_LIGHTNING_TICKS = 2400;

	private static final String POSE_NBT_NAME = "Pose";
	private static final String POSE_TICKS_NBT_NAME = "PoseTicks";
	private static final String OXIDATION_LEVEL_NBT_NAME = "OxidationLevel";
	private static final String IS_WAXED_NBT_NAME = "IsWaxed";
	private static final String ENTITY_SNAPSHOT_NBT_NAME = "EntitySnapshot";

	private static final EntityDataAccessor<Integer> OXIDATION_LEVEL;
	private static final EntityDataAccessor<Integer> STRUCT_BY_LIGHTNING_TICKS;
	private static final EntityDataAccessor<Boolean> WAS_STATUE;
	private static final EntityDataAccessor<Boolean> IS_WAXED;
	private static final EntityDataAccessor<CompoundTag> ENTITY_SNAPSHOT;

	static {
		POSE_TICKS = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.INT);
		OXIDATION_LEVEL = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.INT);
		STRUCT_BY_LIGHTNING_TICKS = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.INT);
		WAS_STATUE = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.BOOLEAN);
		IS_WAXED = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.BOOLEAN);
		ENTITY_SNAPSHOT = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.COMPOUND_TAG);
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
		/*? >=1.21.3 {*/
		EntitySpawnReason spawnReason,
		/*?} else {*/
		/*MobSpawnType spawnReason,
		*//*?}*/
		@Nullable SpawnGroupData entityData
	) {
		SpawnGroupData superEntityData = super.finalizeSpawn(world, difficulty, spawnReason, entityData);

		if (spawnReason == VersionedEntitySpawnReason.STRUCTURE) {
			return superEntityData;
		}

		this.setPose(CopperGolemEntityPose.IDLE);
		CopperGolemBrain.setSpinHeadCooldown(this);
		CopperGolemBrain.setPressButtonCooldown(this);

		return superEntityData;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);

		builder.define(POSE_TICKS, 0);
		builder.define(OXIDATION_LEVEL, WeatheringCopper.WeatherState.UNAFFECTED.ordinal());
		builder.define(STRUCT_BY_LIGHTNING_TICKS, 0);
		builder.define(WAS_STATUE, false);
		builder.define(IS_WAXED, false);
		builder.define(ENTITY_SNAPSHOT, new CompoundTag());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putInt(OXIDATION_LEVEL_NBT_NAME, this.getOxidationLevel().ordinal());
		nbt.putBoolean(IS_WAXED_NBT_NAME, this.isWaxed());

		if (isOxidized()) {
			nbt.putString(POSE_NBT_NAME, this.getPose().name());
			nbt.putInt(POSE_TICKS_NBT_NAME, this.getCurrentAnimationTick());
			nbt.put(ENTITY_SNAPSHOT_NBT_NAME, this.getEntitySnapshot());
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);

		this.setPose(Pose.valueOf(VersionedNbt.getString(nbt, POSE_NBT_NAME, CopperGolemEntityPose.IDLE.getName())));
		this.setCurrentAnimationTick(VersionedNbt.getInt(nbt, POSE_TICKS_NBT_NAME, 0));
		this.setOxidationLevel(WeatheringCopper.WeatherState.values()[VersionedNbt.getInt(nbt, OXIDATION_LEVEL_NBT_NAME, 0)]);
		this.setIsWaxed(VersionedNbt.getBoolean(nbt, IS_WAXED_NBT_NAME, false));
		this.setEntitySnapshot(VersionedNbt.getCompound(nbt, ENTITY_SNAPSHOT_NBT_NAME));
		this.applyEntitySnapshot();
	}

	public static AttributeSupplier.Builder createCopperGolemAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 20.0D)
			.add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
			//? >= 1.21.4 {
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

	@Override
	protected void sendDebugPackets() {
		super.sendDebugPackets();
		DebugPackets.sendEntityBrain(this);
	}

	public void setEntitySnapshot(CompoundTag entitySnapshot) {
		entityData.set(ENTITY_SNAPSHOT, entitySnapshot);
	}

	public void applyEntitySnapshot() {
		CompoundTag entitySnapshot = this.getEntitySnapshot();

		if (entitySnapshot.isEmpty()) {
			return;
		}

		if (this.isPassenger() == false) {
			this.yRotO = VersionedNbt.getFloat(entitySnapshot, "prevYaw", 0.0F);
			this.setYRot(this.yRotO);
			this.yBodyRotO = VersionedNbt.getFloat(entitySnapshot, "prevBodyYaw", 0.0F);
			this.yBodyRot = this.yBodyRotO;
			this.lerpYHeadRot = VersionedNbt.getDouble(entitySnapshot, "serverHeadYaw", 0.0D);
			this.yHeadRotO = VersionedNbt.getFloat(entitySnapshot, "prevHeadYaw", 0.0F);
			this.yHeadRot = this.yHeadRotO;
			//this.animStepO = VersionedNbt.getFloat(entitySnapshot, "prevLookDirection", 0.0F);
			//this.animStep = this.animStepO;
		}

		this.xRotO = VersionedNbt.getFloat(entitySnapshot, "prevPitch", 0.0F);
		this.setXRot(this.xRotO);
		this.oAttackAnim =  VersionedNbt.getFloat(entitySnapshot, "lastHandSwingProgress", 0.0F);
		this.attackAnim = this.oAttackAnim;
		((LimbAnimatorAccessor) this.walkAnimation).setPrevSpeed(VersionedNbt.getFloat(entitySnapshot, "limbAnimatorPrevSpeed", 0.0F));
		this.walkAnimation.setSpeed( VersionedNbt.getFloat(entitySnapshot, "limbAnimatorSpeed", 0.0F));
		((LimbAnimatorAccessor) this.walkAnimation).setPos(VersionedNbt.getFloat(entitySnapshot, "limbAnimatorPos", 0.0F));
		//this.oRun = entitySnapshot.getFloat("prevStepBobbingAmount");
		//this.run = this.oRun;
	}

	public CompoundTag getEntitySnapshot() {
		return this.entityData.get(ENTITY_SNAPSHOT);
	}

	private CompoundTag takeEntitySnapshot() {
		CompoundTag entitySnapshot = new CompoundTag();

		entitySnapshot.putFloat("prevYaw", this.yRotO);
		entitySnapshot.putFloat("prevPitch", this.xRotO);
		entitySnapshot.putFloat("prevBodyYaw", this.yBodyRotO);
		entitySnapshot.putDouble("serverHeadYaw", this.lerpYHeadRot);
		entitySnapshot.putFloat("prevHeadYaw", this.yHeadRotO);
		entitySnapshot.putFloat("lastHandSwingProgress", this.oAttackAnim);
		entitySnapshot.putFloat("limbAnimatorPrevSpeed", ((LimbAnimatorAccessor) this.walkAnimation).getPresSpeed());
		entitySnapshot.putFloat("limbAnimatorSpeed", this.walkAnimation.speed());
		entitySnapshot.putFloat("limbAnimatorPos", this.walkAnimation.position());
		//entitySnapshot.putFloat("prevLookDirection", this.animStepO);
		//entitySnapshot.putFloat("prevStepBobbingAmount", this.oRun);

		return entitySnapshot;
	}

	@Override
	protected int decreaseAirSupply(int air) {
		return air;
	}

	@Override
	public float getVoicePitch() {
		return (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 2.5F;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
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
		this.playSound(FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_STEP.get(), blockSoundGroup.getVolume() * 0.15F, this.getVoicePitch());
	}

	@Override
	/*? >=1.21.3 {*/
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount)
	/*?} else {*/
	/*public boolean hurt(DamageSource damageSource, float amount)
	*//*?}*/
	{
		Entity attacker = damageSource.getEntity();

		if (
			attacker instanceof LightningBolt
			|| damageSource == this.damageSources().sweetBerryBush()
		) {
			return false;
		}

		/*? >=1.21.3 {*/
		return super.hurtServer(level, damageSource, amount);
		/*?} else {*/
		/*return super.hurt(damageSource, amount);
		*//*?}*/
	}

	@Override
	public Vec3 getLeashOffset() {
		return new Vec3(0.0D, this.getEyeHeight() * 0.45D, 0.0D);
	}

	// TODO somehow fix
	/*
	@Override
	protected float getActiveEyeHeight(EntityPose poseIn, EntityDimensions sizeIn) {
		return 0.75F;
	} */

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
				itemStack.hurtAndBreak(1, player, Player.getSlotForHand(hand));
			}
		}

		return true;
	}

	@Override
	protected void customServerAiStep(/*? >=1.21.3 {*/ServerLevel level/*?}*/)
	{
		//? <1.21.3 {
		/*var level = (ServerLevel) this.level();
		*///?}

		if (this.isImmobilized()) {
			super.customServerAiStep(/*? >=1.21.3 {*/level/*?}*/);
			return;
		}

		var profiler = VersionedProfilerProvider.getProfiler(this);

		profiler.push("copperGolemBrain");
		this.getBrain().tick(level, this);
		profiler.pop();

		profiler.push("copperGolemActivityUpdate");
		CopperGolemBrain.updateActivities(this);
		profiler.pop();

		super.customServerAiStep(/*? >=1.21.3 {*/level/*?}*/);
	}

	@Override
	public void tick() {
		if (FriendsAndFoes.getConfig().enableCopperGolem == false) {
			this.discard();
		}

		this.updateKeyframeAnimations();
		super.tick();

		if (this.isOxidized()) {
			this.applyEntitySnapshot();
			MovementUtil.stopMovement(this);
			return;
		}

		if (this.isStructByLightning() && this.getCommandSenderWorld().isClientSide() == false) {
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

		if (this.getCommandSenderWorld().isClientSide() == false) {
			this.refreshStructByLightningTicks();

			if (this.isWaxed() == false) {
				this.setOxidationLevel(WeatheringCopper.WeatherState.UNAFFECTED);
			}
		}
	}

	private void updateKeyframeAnimations() {
		if (this.level().isClientSide() == false && this.isOxidized() == false) {
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
			this.setPose(CopperGolemEntityPose.IDLE);
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

		if (this.isInPose(CopperGolemEntityPose.IDLE)) {
			animationHolder = CopperGolemAnimations.IDLE;
		} else if (this.isInPose(CopperGolemEntityPose.SPIN_HEAD)) {
			animationHolder = CopperGolemAnimations.SPIN_HEAD;
		} else if (this.isInPose(CopperGolemEntityPose.PRESS_BUTTON_UP)) {
			animationHolder = CopperGolemAnimations.PRESS_BUTTON_UP;
		} else if (this.isInPose(CopperGolemEntityPose.PRESS_BUTTON_DOWN)) {
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

	@Override
	public void setPose(Pose pose) {
		if (this.level().isClientSide()) {
			return;
		}

		super.setPose(pose);
	}

	public void setPose(CopperGolemEntityPose pose) {
		if (this.level().isClientSide()) {
			return;
		}

		super.setPose(pose.get());
	}

	public boolean isInPose(CopperGolemEntityPose pose) {
		return this.getPose() == pose.get();
	}

	public void startSpinHeadAnimation() {
		if (this.isInPose(CopperGolemEntityPose.SPIN_HEAD)) {
			return;
		}

		this.playSound(FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_HEAD_SPIN.get(), 1.0F, this.getVoicePitch() - 1.5F);
		this.gameEvent(GameEvent.ENTITY_ACTION);
		this.setPose(CopperGolemEntityPose.SPIN_HEAD);
	}

	public void startPressButtonUpAnimation() {
		if (this.isInPose(CopperGolemEntityPose.PRESS_BUTTON_UP)) {
			return;
		}

		this.gameEvent(GameEvent.ENTITY_ACTION);
		this.setPose(CopperGolemEntityPose.PRESS_BUTTON_UP);
	}

	public void startPressButtonDownAnimation() {
		if (this.isInPose(CopperGolemEntityPose.PRESS_BUTTON_DOWN)) {
			return;
		}

		this.gameEvent(GameEvent.ENTITY_ACTION);
		this.setPose(CopperGolemEntityPose.PRESS_BUTTON_DOWN);
	}

	public void handleOxidationIncrease() {
		if (this.getCommandSenderWorld().isClientSide() || this.isImmobilized() || this.isWaxed()) {
			return;
		}

		if (this.getRandom().nextFloat() < OXIDATION_CHANCE) {
			int degradedOxidationLevelOrdinal = getOxidationLevel().ordinal() + 1;
			WeatheringCopper.WeatherState[] OxidationLevels = WeatheringCopper.WeatherState.values();
			this.setOxidationLevel(OxidationLevels[degradedOxidationLevelOrdinal]);
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
		CompoundTag entitySnapshot = this.takeEntitySnapshot();
		this.setEntitySnapshot(entitySnapshot);
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
		//this.lerpYRot = yaw;
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

}