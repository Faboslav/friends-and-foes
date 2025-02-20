package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.TuffGolemAnimations;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.ai.brain.TuffGolemBrain;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.pose.TuffGolemEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.util.MovementUtil;
import com.faboslav.friendsandfoes.common.util.particle.ParticleSpawner;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import com.faboslav.friendsandfoes.common.versions.VersionedEntitySpawnReason;
import com.faboslav.friendsandfoes.common.versions.VersionedInteractionResult;
import com.faboslav.friendsandfoes.common.versions.VersionedProfilerProvider;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
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

public final class TuffGolemEntity extends AbstractGolem implements AnimatedEntity
{
	private static final EntityDataAccessor<String> COLOR;
	private static final EntityDataAccessor<Pose> PREV_POSE;
	private static final EntityDataAccessor<Integer> POSE_TICKS;
	private static final EntityDataAccessor<Boolean> IS_GLUED;
	private static final EntityDataAccessor<CompoundTag> HOME;

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
		COLOR = SynchedEntityData.defineId(TuffGolemEntity.class, EntityDataSerializers.STRING);
		PREV_POSE = SynchedEntityData.defineId(TuffGolemEntity.class, EntityDataSerializers.POSE);
		POSE_TICKS = SynchedEntityData.defineId(TuffGolemEntity.class, EntityDataSerializers.INT);
		IS_GLUED = SynchedEntityData.defineId(TuffGolemEntity.class, EntityDataSerializers.BOOLEAN);
		HOME = SynchedEntityData.defineId(TuffGolemEntity.class, EntityDataSerializers.COMPOUND_TAG);
	}

	private AnimationContextTracker animationContextTracker;

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
		return TuffGolemAnimations.ANIMATIONS;
	}

	@Override
	public AnimationHolder getMovementAnimation() {
		if (this.isHoldingItem()) {
			return TuffGolemAnimations.WALK_WITH_ITEM;
		}

		return TuffGolemAnimations.WALK;
	}

	@Override
	public int getCurrentAnimationTick() {
		return this.entityData.get(POSE_TICKS);
	}

	@Override
	public void setCurrentAnimationTick(int keyframeAnimationTicks) {
		this.entityData.set(POSE_TICKS, keyframeAnimationTicks);
	}

	public TuffGolemEntity(
		EntityType<? extends TuffGolemEntity> entityType,
		Level world
	) {
		super(entityType, world);
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

		if (spawnReason == VersionedEntitySpawnReason.MOB_SUMMONED || spawnReason == VersionedEntitySpawnReason.COMMAND) {
			float randomSpawnYaw = 90.0F * (float) this.getRandom().nextIntBetweenInclusive(0, 3);
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
	protected Brain<?> makeBrain(Dynamic<?> dynamic) {
		return TuffGolemBrain.create(dynamic);
	}

	@Override
	@SuppressWarnings("all")
	public Brain<TuffGolemEntity> getBrain() {
		return (Brain<TuffGolemEntity>) super.getBrain();
	}

	@Override
	protected void sendDebugPackets() {
		super.sendDebugPackets();
		DebugPackets.sendEntityBrain(this);
	}

	@Override
	protected void customServerAiStep(/*? >=1.21.3 {*/ServerLevel level/*?}*/)
	{
		//? <1.21.3 {
		/*var level = (ServerLevel) this.level();
		 *///?}

		var profiler = VersionedProfilerProvider.getProfiler(this);
		profiler.push("tuffGolemBrain");
		this.getBrain().tick(level, this);
		profiler.pop();

		profiler.push("tuffGolemActivityUpdate");
		TuffGolemBrain.updateActivities(this);
		profiler.pop();

		super.customServerAiStep(/*? >=1.21.3 {*/level/*?}*/);
	}

	public static AttributeSupplier.Builder createTuffGolemAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 20.0D)
			.add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
			.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);

		builder.define(COLOR, Color.RED.getName());
		builder.define(PREV_POSE, TuffGolemEntityPose.STANDING.get());
		builder.define(POSE_TICKS, 0);
		builder.define(IS_GLUED, false);
		builder.define(HOME, new CompoundTag());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putString(COLOR_NBT_NAME, this.getColor().getName());
		nbt.putString(PREV_POSE_NBT_NAME, this.getPrevPose().name());
		nbt.putString(POSE_NBT_NAME, this.getPose().name());
		nbt.putBoolean(IS_GLUED_NBT_NAME, this.isGlued());
		nbt.put(HOME_NBT_NAME, this.getHome());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);
		this.setColor(TuffGolemEntity.Color.fromName(nbt.getString(COLOR_NBT_NAME)));
		this.setGlued(nbt.getBoolean(IS_GLUED_NBT_NAME));
		this.setHome(nbt.getCompound(HOME_NBT_NAME));

		if (this.isAtHomePos()) {
			this.setSpawnYaw(this.getHomeYaw());
		}

		String prevSavedPose = nbt.getString(PREV_POSE_NBT_NAME);
		if (prevSavedPose != "") {
			this.setPrevPose(Pose.valueOf(nbt.getString(PREV_POSE_NBT_NAME)));
		}

		String savedPose = nbt.getString(POSE_NBT_NAME);
		if (savedPose != "") {
			this.setPoseWithoutPrevPose(Pose.valueOf(nbt.getString(POSE_NBT_NAME)));
			Pose entityPose = Pose.valueOf(nbt.getString(POSE_NBT_NAME));

			if (
				this.level().isClientSide() == false
				&& (
					entityPose == TuffGolemEntityPose.SLEEPING.get()
					|| entityPose == TuffGolemEntityPose.SLEEPING_WITH_ITEM.get()
				)
			) {
				this.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
				this.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
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
		this.ambientSoundTime = -this.getAmbientSoundInterval();
		this.playSound(this.getHurtSound(source), 2.0F, 0.7F + this.getRandom().nextFloat() * 0.15F);
	}

	@Override
	protected void playStepSound(
		BlockPos pos,
		BlockState state
	) {
		if (
			this.isInSleepingPose()
			|| state.liquid()
		) {
			return;
		}

		BlockState blockState = this.level().getBlockState(pos.above());
		SoundType blockSoundGroup = blockState.is(BlockTags.INSIDE_STEP_SOUND_BLOCKS) ? blockState.getSoundType():state.getSoundType();
		this.playSound(FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_STEP.get(), blockSoundGroup.getVolume(), 0.75F + this.getRandom().nextFloat() * 0.15F);
	}

	@Override
	public InteractionResult mobInteract(
		Player player,
		InteractionHand hand
	) {
		ItemStack itemStack = player.getItemInHand(hand);
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
			this.gameEvent(GameEvent.ENTITY_INTERACT, this);
			return VersionedInteractionResult.success(this);
		}

		return super.mobInteract(player, hand);
	}

	private boolean tryToInteractMobWithTuff(
		Player player,
		ItemStack itemStack
	) {
		if (this.getHealth() >= this.getMaxHealth()) {
			return false;
		}

		this.heal(TUFF_HEAL_AMOUNT);

		if (player.getAbilities().instabuild == false) {
			itemStack.shrink(1);
		}

		this.playRepairSound();

		return true;
	}

	private boolean tryToInteractMobWithDye(
		Player player,
		ItemStack itemStack
	) {
		Color usedColor = TuffGolemEntity.Color.fromDyeColor(((DyeItem) itemStack.getItem()).getDyeColor());

		if (this.getColor() == usedColor) {
			return false;
		}

		this.setColor(usedColor);

		if (player.getAbilities().instabuild == false) {
			itemStack.shrink(1);
		}

		this.playSound(SoundEvents.DYE_USE, 1.0F, 1.0F);

		return true;
	}

	private boolean tryToInteractMobWithHoneycomb(
		Player player,
		ItemStack itemStack
	) {
		if (this.isGlued()) {
			return false;
		}

		this.setGlued(true);

		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}

		this.playGlueOnSound();
		ParticleSpawner.spawnParticles(this, ParticleTypes.WAX_ON, 7, 1.0F);

		return true;
	}

	private boolean tryToInteractMobWithAxe(
		Player player,
		InteractionHand hand,
		ItemStack itemStack
	) {
		if (!this.isGlued()) {
			return false;
		}

		this.setGlued(false);

		this.playGlueOffSound();
		ParticleSpawner.spawnParticles(this, ParticleTypes.WAX_OFF, 7, 1.0F);

		if (this.level().isClientSide() == false && !player.getAbilities().instabuild) {
			itemStack.hurtAndBreak(1, player, Player.getSlotForHand(hand));
		}

		return true;
	}

	private boolean tryToInteractMobWithItem(
		Player player,
		ItemStack itemStack
	) {
		if (
			(
				this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() == Items.AIR
				&& itemStack.getItem() == Items.AIR
			) || itemStack.getItem() instanceof SpawnEggItem
			|| isAnyKeyframeAnimationRunning()
		) {
			return false;
		}

		MovementUtil.stopMovement(this);

		if (this.isHoldingItem()) {
			if (player.getAbilities().instabuild == false) {
				VersionedEntity.spawnAtLocation(this, this.getItemBySlot(EquipmentSlot.MAINHAND));
			}

			this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
			this.startStanding();
		} else {
			ItemStack itemsStackToBeEquipped = itemStack.copy();

			if (player.getAbilities().instabuild == false) {
				itemStack.shrink(1);
			}

			itemsStackToBeEquipped.setCount(1);

			this.setItemSlot(EquipmentSlot.MAINHAND, itemsStackToBeEquipped);
			this.startStandingWithItem();
		}

		return true;
	}

	public void setColor(TuffGolemEntity.Color color) {
		this.entityData.set(COLOR, color.getName());
	}

	public TuffGolemEntity.Color getColor() {
		return TuffGolemEntity.Color.fromName(this.entityData.get(COLOR));
	}

	@Override
	public void setPose(Pose pose) {
		if (this.level().isClientSide()) {
			return;
		}

		this.setPrevPose(this.getPose());
		super.setPose(pose);
	}

	public void setPoseWithoutPrevPose(Pose pose) {
		if (this.level().isClientSide()) {
			return;
		}

		super.setPose(pose);
	}

	public void setPrevPose(Pose pose) {
		if (this.level().isClientSide()) {
			return;
		}

		this.entityData.set(PREV_POSE, pose);
	}

	public Pose getPrevPose() {
		return this.entityData.get(PREV_POSE);
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
		entityData.set(IS_GLUED, isGlued);
	}

	public boolean isGlued() {
		return entityData.get(IS_GLUED);
	}

	public CompoundTag getNewHome() {
		CompoundTag home = new CompoundTag();

		home.putDouble(HOME_NBT_NAME_X, this.position().x());
		home.putDouble(HOME_NBT_NAME_Y, this.position().y());
		home.putDouble(HOME_NBT_NAME_Z, this.position().z());
		home.putFloat(HOME_NBT_NAME_YAW, this.yBodyRot);

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

	public float getHomeYaw() {
		return this.getHome().getFloat(HOME_NBT_NAME_YAW);
	}

	public boolean isAtHomePos() {
		return this.distanceToSqr(this.getHomePos()) < 0.1D;
	}

	public boolean isCloseToHomePos(float distance) {
		return this.distanceToSqr(this.getHomePos()) < distance;
	}

	public boolean isAtHomeYaw() {
		return this.lerpYRot == this.getHomeYaw()
			   && this.yRotO == this.getHomeYaw()
			   && this.getYRot() == this.getHomeYaw()
			   && this.yBodyRotO == this.getHomeYaw()
			   && this.getVisualRotationYInDegrees() == this.getHomeYaw()
			   && this.yHeadRotO == this.getHomeYaw()
			   && this.getYHeadRot() == this.getHomeYaw();
	}

	public boolean isAtHome() {
		return this.isAtHomePos() && this.isAtHomeYaw();
	}

	public boolean isHoldingItem() {
		return this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() != Items.AIR;
	}

	public boolean isShowingItem() {
		return
			this.isHoldingItem()
			&& this.isInHoldingItemPose();
	}

	public boolean isNotImmobilized() {
		return this.getCurrentAnimationTick() == 0
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
		if (this.level().isClientSide() == false && FriendsAndFoes.getConfig().enableTuffGolem == false) {
			this.discard();
		}

		if (this.level().isClientSide() == false && this.inactiveTicksAfterSpawn > 0) {
			this.inactiveTicksAfterSpawn--;
		}

		this.updateKeyframeAnimations();

		super.tick();
	}

	@Override
	public boolean isPushable() {
		return !this.isSleeping();
	}

	private void updateKeyframeAnimations() {
		if (this.level().isClientSide() == false) {
			this.updateCurrentAnimationTick();
		}

		AnimationHolder animationToStart = this.getAnimationByPose();

		if (
			animationToStart != null
			&& this.isKeyframeAnimationRunning(animationToStart) == false
		) {
			if (this.level().isClientSide() == false) {
				this.setCurrentAnimationTick(animationToStart.get().lengthInTicks());
			}

			this.startKeyframeAnimation(animationToStart);
		}
	}

	private void startKeyframeAnimation(AnimationHolder animationToStart) {
		for (AnimationHolder animation : this.getTrackedAnimations()) {
			if (animation == animationToStart) {
				continue;
			}

			this.stopKeyframeAnimation(animation);
		}

		this.startKeyframeAnimation(animationToStart, this.tickCount);
	}

	@Nullable
	public AnimationHolder getAnimationByPose() {
		Pose prevPose = this.getPrevPose();
		Pose pose = this.getPose();

		if (pose == prevPose) {
			return null;
		}

		AnimationHolder animationHolder = null;

		if (this.wasInPose(TuffGolemEntityPose.STANDING)) {
			if (this.isInPose(TuffGolemEntityPose.STANDING_WITH_ITEM)) {
				animationHolder = TuffGolemAnimations.SHOW_ITEM;
			} else if (this.isInPose(TuffGolemEntityPose.SLEEPING)) {
				animationHolder = TuffGolemAnimations.SLEEP;
			}
		} else if (this.wasInPose(TuffGolemEntityPose.STANDING_WITH_ITEM)) {
			if (this.isInPose(TuffGolemEntityPose.STANDING)) {
				animationHolder = TuffGolemAnimations.HIDE_ITEM;
			} else if (this.isInPose(TuffGolemEntityPose.SLEEPING_WITH_ITEM)) {
				animationHolder = TuffGolemAnimations.SLEEP_WITH_ITEM;
			}
		} else if (this.wasInPose(TuffGolemEntityPose.SLEEPING)) {
			if (this.isInPose(TuffGolemEntityPose.SLEEPING_WITH_ITEM)) {
				animationHolder = TuffGolemAnimations.SHOW_ITEM;
			} else if (this.isInPose(TuffGolemEntityPose.STANDING_WITH_ITEM)) {
				animationHolder = TuffGolemAnimations.WAKE_AND_SHOW_ITEM;
			} else if (this.isInPose(TuffGolemEntityPose.STANDING)) {
				animationHolder = TuffGolemAnimations.WAKE;
			}
		} else if (this.wasInPose(TuffGolemEntityPose.SLEEPING_WITH_ITEM)) {
			if (this.isInPose(TuffGolemEntityPose.SLEEPING)) {
				animationHolder = TuffGolemAnimations.HIDE_ITEM;
			} else if (this.isInPose(TuffGolemEntityPose.STANDING)) {
				animationHolder = TuffGolemAnimations.WAKE_AND_HIDE_ITEM;
			} else if (this.isInPose(TuffGolemEntityPose.STANDING_WITH_ITEM)) {
				animationHolder = TuffGolemAnimations.WAKE_WITH_ITEM;
			}
		}

		return animationHolder;
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

		Entity attacker = damageSource.getEntity();

		if (
			attacker instanceof LightningBolt
			|| damageSource == this.damageSources().sweetBerryBush()
		) {
			return false;
		}

		if (
			!this.level().isClientSide()
			&& this.isInSleepingPose()
		) {
			if (this.isHoldingItem()) {
				this.startStandingWithItem();
			} else {
				this.startStanding();
			}
		}

		/*? >=1.21.3 {*/
		return super.hurtServer(level, damageSource, amount);
		/*?} else {*/
		/*return super.hurt(damageSource, amount);
		*//*?}*/
	}

	@Override
	public boolean canBeLeashed() {
		return super.canBeLeashed() && this.isInSleepingPose() == false;
	}

	@Override
	public Vec3 getLeashOffset() {
		return new Vec3(0.0D, this.getEyeHeight() * 0.45D, 0.0D);
	}

	// TODO fix eyes
	/*
	@Override
	protected float getActiveEyeHeight(EntityPose poseIn, EntityDimensions sizeIn) {
		return 0.8F;
	}
	 */

	@Override
	public float getSpeed() {
		return this.isHoldingItem() ? MOVEMENT_SPEED_WITH_ITEM:MOVEMENT_SPEED;
	}

	@Override
	protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
		super.dropCustomDeathLoot(level, damageSource, recentlyHit);

		if (this.isHoldingItem()) {
			VersionedEntity.spawnAtLocation(this, this.getItemBySlot(EquipmentSlot.MAINHAND));
			this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
		}
	}

	public float getMovementSpeedModifier() {
		if (this.isHoldingItem()) {
			return 1.0F;
		}

		return MOVEMENT_SPEED_WITH_ITEM / MOVEMENT_SPEED;
	}

	@Override
	protected int decreaseAirSupply(int air) {
		return air;
	}

	public void setSpawnYaw(float yaw) {
		this.lerpYRot = yaw;
		this.yRotO = yaw;
		this.setYRot(yaw);
		this.yBodyRotO = yaw;
		this.setYBodyRot(yaw);
		this.yHeadRotO = yaw;
		this.setYHeadRot(yaw);
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
