package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.KeyframeAnimation;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.TuffGolemAnimations;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.ai.brain.TuffGolemBrain;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.pose.TuffGolemEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.util.particle.ParticleSpawner;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public final class TuffGolemEntity extends GolemEntity implements AnimatedEntity
{
	private static final TrackedData<String> COLOR;
	private static final TrackedData<EntityPose> PREV_POSE;
	private static final TrackedData<Integer> POSE_TICKS;
	private static final TrackedData<Boolean> IS_GLUED;
	private static final TrackedData<NbtCompound> HOME;

	private static final float MOVEMENT_SPEED = 0.225F;
	private static final float MOVEMENT_SPEED_WITH_ITEM = 0.175F;
	private static final int TUFF_HEAL_AMOUNT = 5;
	private static final int INACTIVE_TICKS_AFTER_SPAWN = 200;

	private static final String COLOR_NBT_NAME = "Color";
	private static final String PREV_POSE_NBT_NAME = "PrevPose";
	private static final String POSE_NBT_NAME = "Pose";
	private static final String IS_GLUED_NBT_NAME = "IsGlued";
	private static final String HOME_NBT_NAME = "Home";
	private static final String HOME_NBT_NAME_X = "x";
	private static final String HOME_NBT_NAME_Y = "y";
	private static final String HOME_NBT_NAME_Z = "z";
	private static final String HOME_NBT_NAME_YAW = "yaw";

	private int inactiveTicksAfterSpawn = 0;

	static {
		COLOR = DataTracker.registerData(TuffGolemEntity.class, TrackedDataHandlerRegistry.STRING);
		PREV_POSE = DataTracker.registerData(TuffGolemEntity.class, TrackedDataHandlerRegistry.ENTITY_POSE);
		POSE_TICKS = DataTracker.registerData(TuffGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
		IS_GLUED = DataTracker.registerData(TuffGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		HOME = DataTracker.registerData(TuffGolemEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
	}

	private AnimationContextTracker animationContextTracker;

	@Override
	public AnimationContextTracker getAnimationContextTracker() {
		if (this.animationContextTracker == null) {
			this.animationContextTracker = new AnimationContextTracker();

			for (KeyframeAnimation keyframeAnimation : this.getAnimations()) {
				this.animationContextTracker.add(keyframeAnimation);
			}
		}

		return this.animationContextTracker;
	}

	@Override
	public ArrayList<KeyframeAnimation> getAnimations() {
		return TuffGolemAnimations.ANIMATIONS;
	}

	@Override
	public KeyframeAnimation getMovementAnimation() {
		if (this.isHoldingItem()) {
			return TuffGolemAnimations.WALK_WITH_ITEM;
		}

		return TuffGolemAnimations.WALK;
	}

	@Override
	public int getKeyframeAnimationTicks() {
		return this.dataTracker.get(POSE_TICKS);
	}

	public void setKeyframeAnimationTicks(int keyframeAnimationTicks) {
		this.dataTracker.set(POSE_TICKS, keyframeAnimationTicks);
	}

	public TuffGolemEntity(
		EntityType<? extends TuffGolemEntity> entityType,
		World world
	) {
		super(entityType, world);
	}

	@Override
	public EntityData initialize(
		ServerWorldAccess world,
		LocalDifficulty difficulty,
		SpawnReason spawnReason,
		@Nullable EntityData entityData
	) {
		EntityData superEntityData = super.initialize(world, difficulty, spawnReason, entityData);

		if (spawnReason == SpawnReason.SPAWN_EGG || spawnReason == SpawnReason.COMMAND) {
			float randomSpawnYaw = 90.0F * (float) this.getRandom().nextBetween(0, 3);
			this.setSpawnYaw(randomSpawnYaw);
		}

		this.setPrevPose(TuffGolemEntityPose.STANDING.get());
		this.setPoseWithoutPrevPose(TuffGolemEntityPose.STANDING.get());
		this.setHome(this.getNewHome());
		TuffGolemBrain.setSleepCooldown(this);
		this.inactiveTicksAfterSpawn = INACTIVE_TICKS_AFTER_SPAWN;

		return superEntityData;
	}

	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
		return TuffGolemBrain.create(dynamic);
	}

	@Override
	@SuppressWarnings("all")
	public Brain<TuffGolemEntity> getBrain() {
		return (Brain<TuffGolemEntity>) super.getBrain();
	}

	@Override
	protected void mobTick() {
		this.getWorld().getProfiler().push("tuffGolemBrain");
		this.getBrain().tick((ServerWorld) this.getWorld(), this);
		this.getWorld().getProfiler().pop();
		this.getWorld().getProfiler().push("tuffGolemActivityUpdate");
		TuffGolemBrain.updateActivities(this);
		this.getWorld().getProfiler().pop();

		super.mobTick();
	}

	public static DefaultAttributeContainer.Builder createTuffGolemAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED)
			.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);

		builder.add(COLOR, Color.RED.getName());
		builder.add(PREV_POSE, TuffGolemEntityPose.STANDING.get());
		builder.add(POSE_TICKS, 0);
		builder.add(IS_GLUED, false);
		builder.add(HOME, new NbtCompound());
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putString(COLOR_NBT_NAME, this.getColor().getName());
		nbt.putString(PREV_POSE_NBT_NAME, this.getPrevPose().name());
		nbt.putString(POSE_NBT_NAME, this.getPose().name());
		nbt.putBoolean(IS_GLUED_NBT_NAME, this.isGlued());
		nbt.put(HOME_NBT_NAME, this.getHome());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.setColor(TuffGolemEntity.Color.fromName(nbt.getString(COLOR_NBT_NAME)));
		this.setGlued(nbt.getBoolean(IS_GLUED_NBT_NAME));
		this.setHome(nbt.getCompound(HOME_NBT_NAME));

		if (this.isAtHomePos()) {
			this.setSpawnYaw(this.getHomeYaw());
		}

		String prevSavedPose = nbt.getString(PREV_POSE_NBT_NAME);
		if (prevSavedPose != "") {
			this.setPrevPose(EntityPose.valueOf(nbt.getString(PREV_POSE_NBT_NAME)));
		}

		String savedPose = nbt.getString(POSE_NBT_NAME);
		if (savedPose != "") {
			this.setPoseWithoutPrevPose(EntityPose.valueOf(nbt.getString(POSE_NBT_NAME)));
			EntityPose entityPose = EntityPose.valueOf(nbt.getString(POSE_NBT_NAME));

			if (
				this.getWorld().isClient() == false
				&& (
					entityPose == TuffGolemEntityPose.SLEEPING.get()
					|| entityPose == TuffGolemEntityPose.SLEEPING_WITH_ITEM.get()
				)
			) {
				this.getBrain().forget(MemoryModuleType.WALK_TARGET);
				this.getBrain().forget(MemoryModuleType.LOOK_TARGET);
				TuffGolemBrain.resetSleepCooldown(this);
			} else {
				this.setPoseWithoutPrevPose(entityPose);
			}
		}
	}

	private SoundEvent getGlueOnSound() {
		return FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_GLUE_ON.get();
	}

	private void playGlueOnSound() {
		this.playSound(this.getGlueOnSound(), 1.0F, 1.0F);
	}

	private SoundEvent getGlueOffSound() {
		return FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_GLUE_OFF.get();
	}

	private void playGlueOffSound() {
		this.playSound(this.getGlueOffSound(), 1.0F, 1.0F);
	}

	public SoundEvent getMoveSound() {
		return FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_MOVE.get();
	}

	public void playMoveSound() {
		this.playSound(this.getMoveSound(), 1.0F, 1.05F + this.getRandom().nextFloat() * 0.05F);
	}

	private SoundEvent getRepairSound() {
		return FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_REPAIR.get();
	}

	private void playRepairSound() {
		this.playSound(this.getRepairSound(), 1.0F, 1.0F + (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F);
	}

	public SoundEvent getWakeSound() {
		return FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_WAKE.get();
	}

	public void playWakeSound() {
		this.playSound(this.getWakeSound(), 1.0F, 1.05F + this.getRandom().nextFloat() * 0.05F);
	}

	public SoundEvent getSleepSound() {
		return FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_SLEEP.get();
	}

	public void playSleepSound() {
		this.playSound(this.getSleepSound(), 1.0F, 1.05F + this.getRandom().nextFloat() * 0.05F);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_HURT.get();
	}

	@Override
	protected void playHurtSound(DamageSource source) {
		this.ambientSoundChance = -this.getMinAmbientSoundDelay();
		this.playSound(this.getHurtSound(source), 2.0F, 0.7F + this.getRandom().nextFloat() * 0.15F);
	}

	@Override
	protected void playStepSound(
		BlockPos pos,
		BlockState state
	) {
		if (
			this.isInSleepingPose()
			|| state.isLiquid()
		) {
			return;
		}

		BlockState blockState = this.getWorld().getBlockState(pos.up());
		BlockSoundGroup blockSoundGroup = blockState.isIn(BlockTags.INSIDE_STEP_SOUND_BLOCKS) ? blockState.getSoundGroup():state.getSoundGroup();
		this.playSound(FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_STEP.get(), blockSoundGroup.getVolume(), 0.75F + this.getRandom().nextFloat() * 0.15F);
	}

	@Override
	public ActionResult interactMob(
		PlayerEntity player,
		Hand hand
	) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item itemInHand = itemStack.getItem();
		boolean interactionResult = false;

		if (itemInHand == Items.TUFF) {
			interactionResult = this.tryToInteractMobWithTuff(player, itemStack);
		} else if (itemInHand instanceof DyeItem) {
			interactionResult = this.tryToInteractMobWithDye(player, itemStack);
		} else if (itemInHand instanceof HoneycombItem) {
			interactionResult = this.tryToInteractMobWithHoneycomb(player, itemStack);
		} else if (itemInHand instanceof AxeItem) {
			interactionResult = this.tryToInteractMobWithAxe(player, hand, itemStack);
		}

		if (interactionResult == false) {
			interactionResult = this.tryToInteractMobWithItem(player, itemStack);
		}

		if (interactionResult) {
			this.emitGameEvent(GameEvent.ENTITY_INTERACT, this);
			return ActionResult.success(this.getWorld().isClient());
		}

		return super.interactMob(player, hand);
	}

	private boolean tryToInteractMobWithTuff(
		PlayerEntity player,
		ItemStack itemStack
	) {
		if (this.getHealth() >= this.getMaxHealth()) {
			return false;
		}

		this.heal(TUFF_HEAL_AMOUNT);

		if (player.getAbilities().creativeMode == false) {
			itemStack.decrement(1);
		}

		this.playRepairSound();

		return true;
	}

	private boolean tryToInteractMobWithDye(
		PlayerEntity player,
		ItemStack itemStack
	) {
		Color usedColor = TuffGolemEntity.Color.fromDyeColor(((DyeItem) itemStack.getItem()).getColor());

		if (this.getColor() == usedColor) {
			return false;
		}

		this.setColor(usedColor);

		if (player.getAbilities().creativeMode == false) {
			itemStack.decrement(1);
		}

		this.playSound(SoundEvents.ITEM_DYE_USE, 1.0F, 1.0F);

		return true;
	}

	private boolean tryToInteractMobWithHoneycomb(
		PlayerEntity player,
		ItemStack itemStack
	) {
		if (this.isGlued()) {
			return false;
		}

		this.setGlued(true);

		if (!player.getAbilities().creativeMode) {
			itemStack.decrement(1);
		}

		this.playGlueOnSound();
		ParticleSpawner.spawnParticles(this, ParticleTypes.WAX_ON, 7, 1.0F);

		return true;
	}

	private boolean tryToInteractMobWithAxe(
		PlayerEntity player,
		Hand hand,
		ItemStack itemStack
	) {
		if (!this.isGlued()) {
			return false;
		}

		this.setGlued(false);

		this.playGlueOffSound();
		ParticleSpawner.spawnParticles(this, ParticleTypes.WAX_OFF, 7, 1.0F);

		if (this.getWorld().isClient() == false && !player.getAbilities().creativeMode) {
			itemStack.damage(1, player, PlayerEntity.getSlotForHand(hand));
		}

		return true;
	}

	private boolean tryToInteractMobWithItem(
		PlayerEntity player,
		ItemStack itemStack
	) {
		if (
			(
				this.getEquippedStack(EquipmentSlot.MAINHAND).getItem() == Items.AIR
				&& itemStack.getItem() == Items.AIR
			) || itemStack.getItem() instanceof SpawnEggItem
			|| isAnyKeyframeAnimationRunning()
		) {
			return false;
		}

		this.stopMovement();

		if (this.isHoldingItem()) {
			if (player.getAbilities().creativeMode == false) {
				this.dropStack(this.getEquippedStack(EquipmentSlot.MAINHAND));
			}

			this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
			this.startStanding();
		} else {
			ItemStack itemsStackToBeEquipped = itemStack.copy();

			if (player.getAbilities().creativeMode == false) {
				itemStack.decrement(1);
			}

			itemsStackToBeEquipped.setCount(1);

			this.equipStack(EquipmentSlot.MAINHAND, itemsStackToBeEquipped);
			this.startStandingWithItem();
		}

		return true;
	}

	public void setColor(TuffGolemEntity.Color color) {
		this.dataTracker.set(COLOR, color.getName());
	}

	public TuffGolemEntity.Color getColor() {
		return TuffGolemEntity.Color.fromName(this.dataTracker.get(COLOR));
	}

	@Override
	public void setPose(EntityPose pose) {
		if (this.getWorld().isClient()) {
			return;
		}

		this.setPrevPose(this.getPose());
		super.setPose(pose);
	}

	public void setPoseWithoutPrevPose(EntityPose pose) {
		if (this.getWorld().isClient()) {
			return;
		}

		super.setPose(pose);
	}

	public void setPrevPose(EntityPose pose) {
		if (this.getWorld().isClient()) {
			return;
		}

		this.dataTracker.set(PREV_POSE, pose);
	}

	public EntityPose getPrevPose() {
		return this.dataTracker.get(PREV_POSE);
	}

	public boolean isInPose(TuffGolemEntityPose pose) {
		return this.getPose() == pose.get();
	}

	public boolean wasInPose(TuffGolemEntityPose pose) {
		return this.getPrevPose() == pose.get();
	}

	public boolean isInStandingPose() {
		return
			this.isInPose(TuffGolemEntityPose.STANDING)
			|| this.isInPose(TuffGolemEntityPose.STANDING_WITH_ITEM);
	}

	public boolean isInSleepingPose() {
		return
			this.isInPose(TuffGolemEntityPose.SLEEPING)
			|| this.isInPose(TuffGolemEntityPose.SLEEPING_WITH_ITEM);
	}

	public boolean isInHoldingItemPose() {
		return
			this.isInPose(TuffGolemEntityPose.STANDING_WITH_ITEM)
			|| this.isInPose(TuffGolemEntityPose.SLEEPING_WITH_ITEM);
	}

	public void setGlued(boolean isGlued) {
		dataTracker.set(IS_GLUED, isGlued);
	}

	public boolean isGlued() {
		return dataTracker.get(IS_GLUED);
	}

	public NbtCompound getNewHome() {
		NbtCompound home = new NbtCompound();

		home.putDouble(HOME_NBT_NAME_X, this.getPos().getX());
		home.putDouble(HOME_NBT_NAME_Y, this.getPos().getY());
		home.putDouble(HOME_NBT_NAME_Z, this.getPos().getZ());
		home.putFloat(HOME_NBT_NAME_YAW, this.bodyYaw);

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

	public float getHomeYaw() {
		return this.getHome().getFloat(HOME_NBT_NAME_YAW);
	}

	public boolean isAtHomePos() {
		return this.squaredDistanceTo(this.getHomePos()) < 0.1D;
	}

	public boolean isCloseToHomePos(float distance) {
		return this.squaredDistanceTo(this.getHomePos()) < distance;
	}

	public boolean isAtHomeYaw() {
		return this.serverYaw == this.getHomeYaw()
			   && this.prevYaw == this.getHomeYaw()
			   && this.getYaw() == this.getHomeYaw()
			   && this.prevBodyYaw == this.getHomeYaw()
			   && this.getBodyYaw() == this.getHomeYaw()
			   && this.prevHeadYaw == this.getHomeYaw()
			   && this.getHeadYaw() == this.getHomeYaw();
	}

	public boolean isAtHome() {
		return this.isAtHomePos() && this.isAtHomeYaw();
	}

	public boolean isHoldingItem() {
		return this.getEquippedStack(EquipmentSlot.MAINHAND).getItem() != Items.AIR;
	}

	public boolean isShowingItem() {
		return
			this.isHoldingItem()
			&& this.isInHoldingItemPose();
	}

	public boolean isNotImmobilized() {
		return this.getKeyframeAnimationTicks() == 0
			   && this.inactiveTicksAfterSpawn == 0
			   && this.isGlued() == false
			   && this.isInSleepingPose() == false;
	}

	public void startSleeping() {
		if (this.isInPose(TuffGolemEntityPose.SLEEPING)) {
			return;
		}

		this.playSleepSound();
		this.setPose(TuffGolemEntityPose.SLEEPING.get());
	}

	public void startSleepingWithItem() {
		if (this.isInPose(TuffGolemEntityPose.SLEEPING_WITH_ITEM)) {
			return;
		}

		this.playSleepSound();
		this.setPose(TuffGolemEntityPose.SLEEPING_WITH_ITEM.get());
	}

	public void startStanding() {
		if (this.isInPose(TuffGolemEntityPose.STANDING)) {
			return;
		}

		if (this.isInSleepingPose()) {
			this.playWakeSound();
		} else {
			this.playMoveSound();
		}

		this.setPose(TuffGolemEntityPose.STANDING.get());
	}

	public void startStandingWithItem() {
		if (this.isInPose(TuffGolemEntityPose.STANDING_WITH_ITEM)) {
			return;
		}

		if (this.isInSleepingPose()) {
			this.playWakeSound();
		} else {
			this.playMoveSound();
		}

		this.setPose(TuffGolemEntityPose.STANDING_WITH_ITEM.get());
	}

	@Override
	public void tick() {
		if (this.getWorld().isClient() == false && FriendsAndFoes.getConfig().enableTuffGolem == false) {
			this.discard();
		}

		if (this.getWorld().isClient() == false && this.inactiveTicksAfterSpawn > 0) {
			this.inactiveTicksAfterSpawn--;
		}

		this.updateKeyframeAnimations();

		super.tick();
	}

	private void updateKeyframeAnimations() {
		if (this.getWorld().isClient() == false) {
			this.updateKeyframeAnimationTicks();
		}

		KeyframeAnimation keyframeAnimationToStart = this.getKeyframeAnimationByPose();

		if (
			keyframeAnimationToStart != null
			&& this.isKeyframeAnimationRunning(keyframeAnimationToStart) == false
		) {
			if (this.getWorld().isClient() == false) {
				this.setKeyframeAnimationTicks(keyframeAnimationToStart.getAnimationLengthInTicks());
			}

			this.startKeyframeAnimation(keyframeAnimationToStart);
		}
	}

	@Nullable
	private KeyframeAnimation getKeyframeAnimationByPose() {
		EntityPose prevPose = this.getPrevPose();
		EntityPose pose = this.getPose();

		if (pose == prevPose) {
			return null;
		}

		KeyframeAnimation keyframeAnimation = null;

		if (this.wasInPose(TuffGolemEntityPose.STANDING)) {
			if (this.isInPose(TuffGolemEntityPose.STANDING_WITH_ITEM)) {
				keyframeAnimation = TuffGolemAnimations.SHOW_ITEM;
			} else if (this.isInPose(TuffGolemEntityPose.SLEEPING)) {
				keyframeAnimation = TuffGolemAnimations.SLEEP;
			}
		} else if (this.wasInPose(TuffGolemEntityPose.STANDING_WITH_ITEM)) {
			if (this.isInPose(TuffGolemEntityPose.STANDING)) {
				keyframeAnimation = TuffGolemAnimations.HIDE_ITEM;
			} else if (this.isInPose(TuffGolemEntityPose.SLEEPING_WITH_ITEM)) {
				keyframeAnimation = TuffGolemAnimations.SLEEP_WITH_ITEM;
			}
		} else if (this.wasInPose(TuffGolemEntityPose.SLEEPING)) {
			if (this.isInPose(TuffGolemEntityPose.SLEEPING_WITH_ITEM)) {
				keyframeAnimation = TuffGolemAnimations.SHOW_ITEM;
			} else if (this.isInPose(TuffGolemEntityPose.STANDING_WITH_ITEM)) {
				keyframeAnimation = TuffGolemAnimations.WAKE_AND_SHOW_ITEM;
			} else if (this.isInPose(TuffGolemEntityPose.STANDING)) {
				keyframeAnimation = TuffGolemAnimations.WAKE;
			}
		} else if (this.wasInPose(TuffGolemEntityPose.SLEEPING_WITH_ITEM)) {
			if (this.isInPose(TuffGolemEntityPose.SLEEPING)) {
				keyframeAnimation = TuffGolemAnimations.HIDE_ITEM;
			} else if (this.isInPose(TuffGolemEntityPose.STANDING)) {
				keyframeAnimation = TuffGolemAnimations.WAKE_AND_HIDE_ITEM;
			} else if (this.isInPose(TuffGolemEntityPose.STANDING_WITH_ITEM)) {
				keyframeAnimation = TuffGolemAnimations.WAKE_WITH_ITEM;
			}
		}

		return keyframeAnimation;
	}

	private void startKeyframeAnimation(KeyframeAnimation keyframeAnimationToStart) {
		for (KeyframeAnimation keyframeAnimation : TuffGolemAnimations.ANIMATIONS) {
			if (keyframeAnimation == keyframeAnimationToStart) {
				continue;
			}

			this.stopKeyframeAnimation(keyframeAnimation);
		}

		this.startKeyframeAnimation(keyframeAnimationToStart, this.age);
	}

	@Override
	public boolean damage(
		DamageSource source,
		float amount
	) {
		Entity attacker = source.getAttacker();

		if (
			attacker instanceof LightningEntity
			|| source == this.getDamageSources().sweetBerryBush()
		) {
			return false;
		}

		if (
			this.getWorld().isClient() == false
			&& this.isInSleepingPose()
		) {
			if (this.isHoldingItem()) {
				this.startStandingWithItem();
			} else {
				this.startStanding();
			}
		}

		return super.damage(source, amount);
	}

	@Override
	public boolean canBeLeashedBy(PlayerEntity player) {
		return super.canBeLeashedBy(player) && this.isInSleepingPose() == false;
	}

	@Override
	public Vec3d getLeashOffset() {
		return new Vec3d(0.0D, this.getStandingEyeHeight() * 0.45D, 0.0D);
	}

	// TODO fix eyes
	/*
	@Override
	protected float getActiveEyeHeight(EntityPose poseIn, EntityDimensions sizeIn) {
		return 0.8F;
	}
	 */

	@Override
	public float getMovementSpeed() {
		return this.isHoldingItem() ? MOVEMENT_SPEED_WITH_ITEM:MOVEMENT_SPEED;
	}

	@Override
	protected void dropLoot(DamageSource source, boolean causedByPlayer) {
		if (this.isHoldingItem()) {
			this.dropStack(this.getEquippedStack(EquipmentSlot.MAINHAND));
		}

		super.dropLoot(source, causedByPlayer);
	}

	public float getMovementSpeedModifier() {
		if (this.isHoldingItem()) {
			return 1.0F;
		}

		return MOVEMENT_SPEED_WITH_ITEM / MOVEMENT_SPEED;
	}

	@Override
	protected int getNextAirUnderwater(int air) {
		return air;
	}

	public void setSpawnYaw(float yaw) {
		this.serverYaw = yaw;
		this.prevYaw = yaw;
		this.setYaw(yaw);
		this.prevBodyYaw = yaw;
		this.setBodyYaw(yaw);
		this.prevHeadYaw = yaw;
		this.setHeadYaw(yaw);
	}

	public void stopMovement() {
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

	public enum Color
	{
		RED("red"),
		BLACK("black"),
		BLUE("blue"),
		BROWN("brown"),
		CYAN("cyan"),
		GRAY("gray"),
		GREEN("green"),
		LIGHT_BLUE("light_blue"),
		LIGHT_GRAY("light_gray"),
		LIME("lime"),
		MAGENTA("magenta"),
		ORANGE("orange"),
		PINK("pink"),
		PURPLE("purple"),
		WHITE("white"),
		YELLOW("yellow");

		private final String name;

		Color(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		private static TuffGolemEntity.Color fromName(String name) {
			TuffGolemEntity.Color[] colors = values();

			for (TuffGolemEntity.Color color : colors) {
				if (color.name.equals(name)) {
					return color;
				}
			}

			return RED;
		}

		public static TuffGolemEntity.Color fromDyeColor(DyeColor dyeColor) {
			return switch (dyeColor) {
				case BLACK -> Color.BLACK;
				case BLUE -> Color.BLUE;
				case BROWN -> Color.BROWN;
				case CYAN -> Color.CYAN;
				case GRAY -> Color.GRAY;
				case GREEN -> Color.GREEN;
				case LIGHT_BLUE -> Color.LIGHT_BLUE;
				case LIGHT_GRAY -> Color.LIGHT_GRAY;
				case LIME -> Color.LIME;
				case MAGENTA -> Color.MAGENTA;
				case ORANGE -> Color.ORANGE;
				case PINK -> Color.PINK;
				case PURPLE -> Color.PURPLE;
				case WHITE -> Color.WHITE;
				case YELLOW -> Color.YELLOW;
				default -> Color.RED;
			};
		}

		public static TuffGolemEntity.Color fromWool(Block block) {
			if (block == Blocks.BLACK_WOOL) {
				return Color.BLACK;
			} else if (block == Blocks.BLUE_WOOL) {
				return Color.BLUE;
			} else if (block == Blocks.BROWN_WOOL) {
				return Color.BROWN;
			} else if (block == Blocks.CYAN_WOOL) {
				return Color.CYAN;
			} else if (block == Blocks.GRAY_WOOL) {
				return Color.GRAY;
			} else if (block == Blocks.GREEN_WOOL) {
				return Color.GREEN;
			} else if (block == Blocks.LIGHT_BLUE_WOOL) {
				return Color.LIGHT_BLUE;
			} else if (block == Blocks.LIGHT_GRAY_WOOL) {
				return Color.LIGHT_GRAY;
			} else if (block == Blocks.LIME_WOOL) {
				return Color.LIME;
			} else if (block == Blocks.MAGENTA_WOOL) {
				return Color.MAGENTA;
			} else if (block == Blocks.ORANGE_WOOL) {
				return Color.ORANGE;
			} else if (block == Blocks.PINK_WOOL) {
				return Color.PINK;
			} else if (block == Blocks.PURPLE_WOOL) {
				return Color.PURPLE;
			} else if (block == Blocks.WHITE_WOOL) {
				return Color.WHITE;
			} else if (block == Blocks.YELLOW_WOOL) {
				return Color.YELLOW;
			}

			return Color.RED;
		}
	}
}
