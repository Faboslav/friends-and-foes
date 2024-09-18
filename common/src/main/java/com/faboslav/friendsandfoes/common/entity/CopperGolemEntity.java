package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.CopperGolemAnimations;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.KeyframeAnimation;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CopperGolemBrain;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.pose.CopperGolemEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.util.particle.ParticleSpawner;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public final class CopperGolemEntity extends GolemEntity implements AnimatedEntity
{
	private AnimationContextTracker animationContextTracker;
	private static final TrackedData<Integer> POSE_TICKS;

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

	private static final TrackedData<Integer> OXIDATION_LEVEL;
	private static final TrackedData<Integer> STRUCT_BY_LIGHTNING_TICKS;
	private static final TrackedData<Boolean> WAS_STATUE;
	private static final TrackedData<Boolean> IS_WAXED;
	private static final TrackedData<NbtCompound> ENTITY_SNAPSHOT;

	static {
		POSE_TICKS = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
		OXIDATION_LEVEL = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
		STRUCT_BY_LIGHTNING_TICKS = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
		WAS_STATUE = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		IS_WAXED = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		ENTITY_SNAPSHOT = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
	}

	@Override
	public AnimationContextTracker getAnimationContextTracker() {
		if (this.animationContextTracker == null) {
			this.refreshAnimationContextTracker();
		}

		return this.animationContextTracker;
	}

	private void refreshAnimationContextTracker() {
		this.animationContextTracker = new AnimationContextTracker();

		for (KeyframeAnimation keyframeAnimation : this.getAnimations()) {
			this.animationContextTracker.add(keyframeAnimation);
		}

		this.animationContextTracker.add(this.getMovementAnimation());
	}

	@Override
	public ArrayList<KeyframeAnimation> getAnimations() {
		return CopperGolemAnimations.getAnimations(this.getAnimationSpeedModifier());
	}

	@Override
	public KeyframeAnimation getMovementAnimation() {
		return CopperGolemAnimations.getWalkKeyframeAnimation(this.getAnimationSpeedModifier());
	}

	@Override
	public int getKeyframeAnimationTicks() {
		return this.dataTracker.get(POSE_TICKS);
	}

	@Override
	public void setKeyframeAnimationTicks(int keyframeAnimationTicks) {
		this.dataTracker.set(POSE_TICKS, keyframeAnimationTicks);
	}

	public CopperGolemEntity(
		EntityType<? extends CopperGolemEntity> entityType,
		World world
	) {
		super(entityType, world);
		this.moveControl = new CopperGolemEntity.CopperGolemMoveControl(this);
		this.lookControl = new CopperGolemEntity.CopperGolemLookControl(this);
		this.stepHeight = 0.3F;
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

		if (spawnReason == SpawnReason.STRUCTURE) {
			return superEntityData;
		}

		this.setPose(CopperGolemEntityPose.IDLE);
		CopperGolemBrain.setSpinHeadCooldown(this);
		CopperGolemBrain.setPressButtonCooldown(this);

		return superEntityData;
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(POSE_TICKS, 0);
		this.dataTracker.startTracking(OXIDATION_LEVEL, Oxidizable.OxidationLevel.UNAFFECTED.ordinal());
		this.dataTracker.startTracking(STRUCT_BY_LIGHTNING_TICKS, 0);
		this.dataTracker.startTracking(WAS_STATUE, false);
		this.dataTracker.startTracking(IS_WAXED, false);
		this.dataTracker.startTracking(ENTITY_SNAPSHOT, new NbtCompound());
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt(OXIDATION_LEVEL_NBT_NAME, this.getOxidationLevel().ordinal());
		nbt.putBoolean(IS_WAXED_NBT_NAME, this.isWaxed());

		if (isOxidized()) {
			nbt.putString(POSE_NBT_NAME, this.getPose().name());
			nbt.putInt(POSE_TICKS_NBT_NAME, this.getKeyframeAnimationTicks());
			nbt.put(ENTITY_SNAPSHOT_NBT_NAME, this.getEntitySnapshot());
		}
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);

		if (nbt.contains(POSE_NBT_NAME)) {
			EntityPose entityPose = EntityPose.valueOf(nbt.getString(POSE_NBT_NAME));
			this.setPose(entityPose);
		}

		if (nbt.contains(POSE_TICKS_NBT_NAME)) {
			this.setKeyframeAnimationTicks(nbt.getInt(POSE_TICKS_NBT_NAME));
		}

		if (nbt.contains(OXIDATION_LEVEL_NBT_NAME)) {
			this.setOxidationLevel(Oxidizable.OxidationLevel.values()[nbt.getInt(OXIDATION_LEVEL_NBT_NAME)]);
		}

		if (nbt.contains(IS_WAXED_NBT_NAME)) {
			this.setIsWaxed(nbt.getBoolean(IS_WAXED_NBT_NAME));
		}

		if (nbt.contains(ENTITY_SNAPSHOT_NBT_NAME)) {
			this.setEntitySnapshot(nbt.getCompound(ENTITY_SNAPSHOT_NBT_NAME));
			this.applyEntitySnapshot();
		}
	}

	public static DefaultAttributeContainer.Builder createCopperGolemAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED)
			.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
		return CopperGolemBrain.create(dynamic);
	}

	@Override
	@SuppressWarnings("all")
	public Brain<CopperGolemEntity> getBrain() {
		return (Brain<CopperGolemEntity>) super.getBrain();
	}

	public void setEntitySnapshot(NbtCompound entitySnapshot) {
		dataTracker.set(ENTITY_SNAPSHOT, entitySnapshot);
	}

	public void applyEntitySnapshot() {
		NbtCompound entitySnapshot = this.getEntitySnapshot();

		if (entitySnapshot.isEmpty()) {
			return;
		}

		if (!this.hasVehicle()) {
			this.serverYaw = entitySnapshot.getDouble("serverYaw");
			this.prevYaw = entitySnapshot.getFloat("prevYaw");
			this.setYaw(this.prevYaw);
			this.prevBodyYaw = entitySnapshot.getFloat("prevBodyYaw");
			this.bodyYaw = this.prevBodyYaw;
			this.serverHeadYaw = entitySnapshot.getDouble("serverHeadYaw");
			this.prevHeadYaw = entitySnapshot.getFloat("prevHeadYaw");
			this.headYaw = this.prevHeadYaw;
			this.prevLookDirection = entitySnapshot.getFloat("prevLookDirection");
			this.lookDirection = this.prevLookDirection;
		}

		this.prevPitch = entitySnapshot.getFloat("prevPitch");
		this.serverPitch = this.prevPitch;
		this.setPitch(this.prevPitch);
		this.roll = entitySnapshot.getInt("roll");
		this.lastHandSwingProgress = entitySnapshot.getFloat("lastHandSwingProgress");
		this.handSwingProgress = this.lastHandSwingProgress;
		this.lastLimbDistance = entitySnapshot.getFloat("lastLimbDistance");
		this.limbDistance = this.lastLimbDistance;
		this.limbAngle = entitySnapshot.getFloat("limbAngle");
		this.prevStepBobbingAmount = entitySnapshot.getFloat("prevStepBobbingAmount");
		this.stepBobbingAmount = this.prevStepBobbingAmount;
	}

	public NbtCompound getEntitySnapshot() {
		return this.dataTracker.get(ENTITY_SNAPSHOT);
	}

	private NbtCompound takeEntitySnapshot() {
		NbtCompound entitySnapshot = new NbtCompound();

		entitySnapshot.putDouble("serverYaw", this.serverYaw);
		entitySnapshot.putFloat("prevYaw", this.prevYaw); // Same as serverYaw and yaw
		entitySnapshot.putDouble("serverPitch", this.serverPitch);
		entitySnapshot.putFloat("prevPitch", this.prevPitch); // Same as pitch
		entitySnapshot.putInt("roll", this.getRoll());
		entitySnapshot.putFloat("prevBodyYaw", this.prevBodyYaw); // Same as bodyYaw
		entitySnapshot.putDouble("serverHeadYaw", this.serverHeadYaw);
		entitySnapshot.putFloat("prevHeadYaw", this.prevHeadYaw); // Same as headYaw
		entitySnapshot.putFloat("lastHandSwingProgress", this.lastHandSwingProgress); // Same as handSwingProgress
		entitySnapshot.putFloat("lastLimbDistance", this.lastLimbDistance); // Same as limbDistance
		entitySnapshot.putFloat("limbAngle", this.limbAngle);
		entitySnapshot.putFloat("prevLookDirection", this.prevLookDirection); // Same as lookDirection
		entitySnapshot.putFloat("prevStepBobbingAmount", this.prevStepBobbingAmount); // Same as stepBobbingAmount

		return entitySnapshot;
	}

	@Override
	protected int getNextAirUnderwater(int air) {
		return air;
	}

	@Override
	public float getSoundPitch() {
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
			|| state.getMaterial().isLiquid()
		) {
			return;
		}

		BlockState blockState = this.getWorld().getBlockState(pos.up());
		BlockSoundGroup blockSoundGroup = blockState.isIn(BlockTags.INSIDE_STEP_SOUND_BLOCKS) ? blockState.getSoundGroup():state.getSoundGroup();
		this.playSound(FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_STEP.get(), blockSoundGroup.getVolume() * 0.15F, this.getSoundPitch());
	}

	@Override
	public boolean damage(
		DamageSource source,
		float amount
	) {
		Entity attacker = source.getAttacker();

		if (
			attacker instanceof LightningEntity
			|| source == DamageSource.SWEET_BERRY_BUSH
		) {
			return false;
		}

		return super.damage(source, amount);
	}

	@Override
	public Vec3d getLeashOffset() {
		return new Vec3d(0.0D, this.getStandingEyeHeight() * 0.45D, 0.0D);
	}

	@Override
	protected float getActiveEyeHeight(EntityPose poseIn, EntityDimensions sizeIn) {
		return 0.75F;
	}

	public float getMovementSpeedModifier() {
		if (this.isStructByLightning()) {
			return (MOVEMENT_SPEED + MOVEMENT_SPEED / 2.0F) / MOVEMENT_SPEED;
		}

		return (MOVEMENT_SPEED - this.getOxidationModifier() * 0.03333333333F) / MOVEMENT_SPEED;
	}

	public float getAnimationSpeedModifier() {
		if (this.isStructByLightning()) {
			return MOVEMENT_SPEED / (MOVEMENT_SPEED + MOVEMENT_SPEED / 2.0F);
		}

		return MOVEMENT_SPEED / (MOVEMENT_SPEED - (this.getOxidationModifier() * 0.03333333333F));
	}

	private int getOxidationModifier() {
		return Math.min(this.getOxidationLevel().ordinal(), Oxidizable.OxidationLevel.WEATHERED.ordinal());
	}

	@Override
	public ActionResult interactMob(
		PlayerEntity player,
		Hand hand
	) {
		ItemStack itemStack = player.getStackInHand(hand);
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
			this.emitGameEvent(GameEvent.ENTITY_INTERACT, this);
			return ActionResult.success(this.getWorld().isClient());
		}

		return super.interactMob(player, hand);
	}

	private boolean tryToInteractMobWithCopperIngot(
		PlayerEntity player,
		ItemStack itemStack
	) {
		if (this.getHealth() == this.getMaxHealth()) {
			return false;
		}

		this.heal(COPPER_INGOT_HEAL_AMOUNT);

		if (!player.getAbilities().creativeMode) {
			itemStack.decrement(1);
		}

		this.playSound(FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_REPAIR.get(), 1.0F, this.getSoundPitch() - 1.0F);

		return true;
	}

	private boolean tryToInteractMobWithHoneycomb(
		PlayerEntity player,
		ItemStack itemStack
	) {
		if (this.isWaxed() || this.isImmobilized()) {
			return false;
		}

		this.setIsWaxed(true);

		if (!player.getAbilities().creativeMode) {
			itemStack.decrement(1);
		}

		this.playSound(SoundEvents.ITEM_HONEYCOMB_WAX_ON, 1.0F, 1.0F);
		ParticleSpawner.spawnParticles(this, ParticleTypes.WAX_ON, 7, 1.0);

		return true;
	}

	private boolean tryToInteractMobWithAxe(
		PlayerEntity player,
		Hand hand,
		ItemStack itemStack
	) {
		if ((!this.isWaxed() && !this.isDegraded()) || (this.wasStatue() && !this.isOxidized())) {
			return false;
		}

		if (this.isWaxed()) {
			this.setIsWaxed(false);

			this.playSound(SoundEvents.ITEM_AXE_WAX_OFF, 1.0F, 1.0F);
			ParticleSpawner.spawnParticles(this, ParticleTypes.WAX_OFF, 7, 1.0);

		} else if (isDegraded()) {
			if (!this.getWorld().isClient()) {
				int increasedOxidationLevelOrdinal = getOxidationLevel().ordinal() - 1;
				Oxidizable.OxidationLevel[] OxidationLevels = Oxidizable.OxidationLevel.values();
				this.setOxidationLevel(OxidationLevels[increasedOxidationLevelOrdinal]);
			}

			this.playSound(SoundEvents.ITEM_AXE_SCRAPE, 1.0F, 1.0F);
			ParticleSpawner.spawnParticles(this, ParticleTypes.SCRAPE, 7, 1.0);
		}

		if (!this.getWorld().isClient() && !player.getAbilities().creativeMode) {
			itemStack.damage(1, player, (playerEntity) -> {
				player.sendToolBreakStatus(hand);
			});
		}

		return true;
	}

	@Override
	protected void mobTick() {
		if (this.isImmobilized()) {
			super.mobTick();
			return;
		}

		this.getWorld().getProfiler().push("copperGolemBrain");
		this.getBrain().tick((ServerWorld) this.getWorld(), this);
		this.getWorld().getProfiler().pop();
		this.getWorld().getProfiler().push("copperGolemActivityUpdate");
		CopperGolemBrain.updateActivities(this);
		this.getWorld().getProfiler().pop();

		super.mobTick();
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
			this.stopMovement();
			return;
		}

		if (this.isStructByLightning() && !this.getEntityWorld().isClient()) {
			this.setStructByLightningTicks(this.getStructByLightningTicks() - 1);

			if (this.getRandom().nextFloat() < SPARK_CHANCE) {
				for (int i = 0; i < 7; i++) {
					((ServerWorld) world).spawnParticles(
						ParticleTypes.ELECTRIC_SPARK,
						this.getParticleX(0.35D),
						this.getRandomBodyY() + 0.25D,
						this.getParticleZ(0.35D),
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
	public void onStruckByLightning(
		ServerWorld serverWorld,
		LightningEntity lightning
	) {
		super.onStruckByLightning(serverWorld, lightning);

		this.setFireTicks(0);
		this.setOnFire(false);
		this.setHealth(this.getMaxHealth());

		if (this.isDegraded()) {
			ParticleSpawner.spawnParticles(this, ParticleTypes.WAX_OFF, 7, 1.0);
		}

		if (!this.getEntityWorld().isClient()) {
			this.refreshStructByLightningTicks();

			if (!this.isWaxed()) {
				this.setOxidationLevel(Oxidizable.OxidationLevel.UNAFFECTED);
			}
		}
	}

	private void updateKeyframeAnimations() {
		if (!this.getWorld().isClient() && !this.isOxidized()) {
			this.updateKeyframeAnimationTicks();
		}

		KeyframeAnimation keyframeAnimationToStart = this.getKeyframeAnimationByPose();

		if (keyframeAnimationToStart != null) {
			this.tryToStartKeyframeAnimation(keyframeAnimationToStart);
		}
	}

	@Override
	public void updateKeyframeAnimationTicks() {
		if (!this.isAnyKeyframeAnimationRunning()) {
			return;
		}

		this.setKeyframeAnimationTicks(this.getKeyframeAnimationTicks() - 1);

		if (
			!this.getWorld().isClient()
			&& this.wasStatue()
			&& this.getKeyframeAnimationTicks() == 1
		) {
			this.setPose(CopperGolemEntityPose.IDLE);
			this.setWasStatue(false);
		}

		if (this.getKeyframeAnimationTicks() > 1) {
			return;
		}

		for (KeyframeAnimation keyframeAnimation : this.getAnimations()) {
			if (!keyframeAnimation.getAnimation().looping()) {
				continue;
			}

			var keyframeAnimationContext = this.getAnimationContextTracker().get(keyframeAnimation);
			if (!keyframeAnimationContext.isRunning()) {
				continue;
			}

			this.setKeyframeAnimationTicks(keyframeAnimation.getAnimationLengthInTicks());
		}
	}

	@Nullable
	public KeyframeAnimation getKeyframeAnimationByPose() {
		KeyframeAnimation keyframeAnimation = null;

		if (this.isInPose(CopperGolemEntityPose.IDLE)) {
			keyframeAnimation = CopperGolemAnimations.IDLE;
		} else if (this.isInPose(CopperGolemEntityPose.SPIN_HEAD)) {
			keyframeAnimation = CopperGolemAnimations.getSpinHeadKeyframeAnimation(this.getAnimationSpeedModifier());
		} else if (this.isInPose(CopperGolemEntityPose.PRESS_BUTTON_UP)) {
			keyframeAnimation = CopperGolemAnimations.getPressButtonUpKeyframeAnimation(this.getAnimationSpeedModifier());
		} else if (this.isInPose(CopperGolemEntityPose.PRESS_BUTTON_DOWN)) {
			keyframeAnimation = CopperGolemAnimations.getPressButtonDownKeyframeAnimation(this.getAnimationSpeedModifier());
		}

		return keyframeAnimation;
	}

	public int getCurrentKeyframeAnimationTick() {
		KeyframeAnimation keyframeAnimation = this.getKeyframeAnimationByPose();

		if (keyframeAnimation == null) {
			return 0;
		}

		int totalAnimationTicks = keyframeAnimation.getAnimationLengthInTicks();
		int leftAnimationTicks = this.getKeyframeAnimationTicks();

		return totalAnimationTicks - leftAnimationTicks;
	}

	private void tryToStartKeyframeAnimation(KeyframeAnimation keyframeAnimationToStart) {
		if (this.isKeyframeAnimationRunning(keyframeAnimationToStart)) {
			return;
		}

		if (!this.getWorld().isClient() && !this.isOxidized()) {
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

		int initialTick = this.isOxidized() ? this.age - this.getCurrentKeyframeAnimationTick():this.age;
		this.startKeyframeAnimation(keyframeAnimationToStart, initialTick);
	}

	@Override
	public void setPose(EntityPose pose) {
		if (this.getWorld().isClient()) {
			return;
		}

		super.setPose(pose);
	}

	public void setPose(CopperGolemEntityPose pose) {
		if (this.getWorld().isClient()) {
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

		this.playSound(FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_HEAD_SPIN.get(), 1.0F, this.getSoundPitch() - 1.5F);
		this.setPose(CopperGolemEntityPose.SPIN_HEAD);
	}

	public void startPressButtonUpAnimation() {
		if (this.isInPose(CopperGolemEntityPose.PRESS_BUTTON_UP)) {
			return;
		}

		this.setPose(CopperGolemEntityPose.PRESS_BUTTON_UP);
	}

	public void startPressButtonDownAnimation() {
		if (this.isInPose(CopperGolemEntityPose.PRESS_BUTTON_DOWN)) {
			return;
		}

		this.setPose(CopperGolemEntityPose.PRESS_BUTTON_DOWN);
	}

	public void handleOxidationIncrease() {
		if (this.getEntityWorld().isClient() || this.isImmobilized() || this.isWaxed()) {
			return;
		}

		if (this.getRandom().nextFloat() < OXIDATION_CHANCE) {
			int degradedOxidationLevelOrdinal = getOxidationLevel().ordinal() + 1;
			Oxidizable.OxidationLevel[] OxidationLevels = Oxidizable.OxidationLevel.values();
			this.setOxidationLevel(OxidationLevels[degradedOxidationLevelOrdinal]);
		}
	}

	public boolean isOxidized() {
		return this.getOxidationLevel() == Oxidizable.OxidationLevel.OXIDIZED;
	}

	public boolean isDegraded() {
		return this.getOxidationLevel().ordinal() > Oxidizable.OxidationLevel.UNAFFECTED.ordinal();
	}

	public Oxidizable.OxidationLevel getOxidationLevel() {
		return Oxidizable.OxidationLevel.values()[this.dataTracker.get(OXIDATION_LEVEL)];
	}

	public void setOxidationLevel(Oxidizable.OxidationLevel oxidationLevel) {
		this.dataTracker.set(OXIDATION_LEVEL, oxidationLevel.ordinal());
		this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(MOVEMENT_SPEED * this.getMovementSpeedModifier());

		if (this.isOxidized()) {
			this.setWasStatue(true);
		}

		if (!this.isImmobilized()) {
			this.refreshAnimationContextTracker();
		}

		if (this.isOxidized() && this.getBrain().getOptionalMemory(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get()).isEmpty()) {
			this.getBrain().remember(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get(), true);
			this.becomeStatue();
		} else if (!this.isOxidized() && this.getBrain().getOptionalMemory(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get()).isPresent()) {
			this.getBrain().forget(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get());
			this.becomeEntity();
		}
	}

	public void setStructByLightningTicks(int structByLightningTicks) {
		this.dataTracker.set(STRUCT_BY_LIGHTNING_TICKS, structByLightningTicks);
	}

	public int getStructByLightningTicks() {
		return this.dataTracker.get(STRUCT_BY_LIGHTNING_TICKS);
	}

	public void refreshStructByLightningTicks() {
		this.setStructByLightningTicks(this.getRandom().nextBetween(MIN_STRUCT_BY_LIGHTNING_TICKS, MAX_STRUCT_BY_LIGHTNING_TICKS));
	}

	public boolean isStructByLightning() {
		return this.getStructByLightningTicks() > 0;
	}

	@Nullable
	public GlobalPos getButtonPos() {
		return this.getBrain().getOptionalMemory(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get()).orElse(null);
	}

	public boolean isButtonValidToBePressed(BlockPos pos) {
		return this.getWorld().getBlockState(pos).isIn(FriendsAndFoesTags.COPPER_BUTTONS);
	}

	private void becomeStatue() {
		NbtCompound entitySnapshot = this.takeEntitySnapshot();
		this.setEntitySnapshot(entitySnapshot);
	}

	private void becomeEntity() {
		CopperGolemBrain.setSpinHeadCooldown(this);
		CopperGolemBrain.setPressButtonCooldown(this);
	}

	public void stopMovement() {
		this.getBrain().forget(MemoryModuleType.AVOID_TARGET);
		this.getBrain().forget(MemoryModuleType.WALK_TARGET);
		this.getBrain().forget(MemoryModuleType.LOOK_TARGET);

		this.getNavigation().setSpeed(0);
		this.getNavigation().stop();
		this.getMoveControl().moveTo(this.getX(), this.getY(), this.getZ(), 0);
		this.getMoveControl().tick();
		this.getLookControl().lookAt(this.getLookControl().getLookX(), this.getLookControl().getLookY(), this.getLookControl().getLookZ());
		this.getLookControl().lookAt(Vec3d.ZERO);
		this.getLookControl().tick();

		this.setJumping(false);
		this.setMovementSpeed(0.0F);
		this.prevHorizontalSpeed = 0.0F;
		this.horizontalSpeed = 0.0F;
		this.sidewaysSpeed = 0.0F;
		this.upwardSpeed = 0.0F;
	}

	public boolean isWaxed() {
		return this.dataTracker.get(IS_WAXED);
	}

	public void setIsWaxed(boolean isWaxed) {
		this.dataTracker.set(IS_WAXED, isWaxed);
	}

	public boolean wasStatue() {
		return this.dataTracker.get(WAS_STATUE);
	}

	public void setWasStatue(boolean wasStatue) {
		this.dataTracker.set(WAS_STATUE, wasStatue);
	}

	public boolean isImmobilized() {
		return this.isOxidized() || this.wasStatue();
	}

	public void setSpawnYaw(float yaw) {
		this.serverYaw = yaw;
		this.prevYaw = yaw;
		this.setYaw(yaw);
		this.prevBodyYaw = yaw;
		this.bodyYaw = yaw;
		this.serverHeadYaw = yaw;
		this.prevHeadYaw = yaw;
		this.headYaw = yaw;
	}

	final class CopperGolemMoveControl extends MoveControl
	{
		public CopperGolemMoveControl(CopperGolemEntity copperGolem) {
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
		public CopperGolemLookControl(CopperGolemEntity copperGolem) {
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
}