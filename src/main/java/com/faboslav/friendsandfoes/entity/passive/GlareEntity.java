package com.faboslav.friendsandfoes.entity.passive;

import com.faboslav.friendsandfoes.entity.ai.pathing.GlareNavigation;
import com.faboslav.friendsandfoes.entity.passive.ai.goal.*;
import com.faboslav.friendsandfoes.mixin.EntityNavigationAccessor;
import com.faboslav.friendsandfoes.registry.CriteriaRegistry;
import com.faboslav.friendsandfoes.registry.SoundRegistry;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer.Builder;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

@SuppressWarnings({"rawtypes", "unchecked"})
public class GlareEntity extends PathAwareEntity implements Tameable, Flutterer
{
	public static final Predicate<ItemEntity> PICKABLE_FOOD_FILTER;
	private static final int GRUMPY_BITMASK = 2;
	private static final float MOVEMENT_SPEED = 0.6F;
	public static final int MIN_TICKS_UNTIL_CAN_FIND_DARK_SPOT = 200;
	public static final int MAX_TICKS_UNTIL_CAN_FIND_DARK_SPOT = 400;
	public static final int MIN_TICKS_UNTIL_CAN_EAT_GLOW_BERRIES = 300;
	public static final int MAX_TICKS_UNTIL_CAN_EAT_GLOW_BERRIES = 600;

	private static final TrackedData<Byte> TAMEABLE_FLAGS;
	private static final TrackedData<Byte> GLARE_FLAGS;
	private static final TrackedData<Optional<UUID>> OWNER_UUID;
	private static final TrackedData<Integer> TICKS_UNTIL_CAN_FIND_DARK_SPOT;
	private static final TrackedData<Integer> TICKS_UNTIL_CAN_EAT_GLOW_BERRIES;

	private Vec2f currentEyesPositionOffset;
	private Vec2f targetEyesPositionOffset;
	private float currentLayerPitch;
	private float currentLayerRoll;
	private float currentLayerPitchAnimationProgress;
	private float currentLayerRollAnimationProgress;
	private GlareEatGlowBerriesGoal eatGlowBerriesGoal;
	private GlareFlyToDarkSpotGoal flyToDarkSpotGoal;
	private boolean sitting;

	public GlareEntity(EntityType<? extends GlareEntity> entityType, World world) {
		super(entityType, world);
		this.setPersistent();
		this.moveControl = new FlightMoveControl(this, 4, true);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_CACTUS, -1.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
		this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
		this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);
		this.setCanPickUpLoot(true);

		this.currentEyesPositionOffset = new Vec2f(0.0F, 0.0F);
		this.targetEyesPositionOffset = new Vec2f(0.0F, 0.0F);
		this.currentLayerPitch = 0.0F;
		this.currentLayerRoll = 0.0F;
		this.currentLayerPitchAnimationProgress = 0.0F;
		this.currentLayerRollAnimationProgress = 0.0F;
	}

	static {
		GLARE_FLAGS = DataTracker.registerData(GlareEntity.class, TrackedDataHandlerRegistry.BYTE);
		TAMEABLE_FLAGS = DataTracker.registerData(GlareEntity.class, TrackedDataHandlerRegistry.BYTE);
		OWNER_UUID = DataTracker.registerData(GlareEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
		TICKS_UNTIL_CAN_EAT_GLOW_BERRIES = DataTracker.registerData(GlareEntity.class, TrackedDataHandlerRegistry.INTEGER);
		TICKS_UNTIL_CAN_FIND_DARK_SPOT = DataTracker.registerData(GlareEntity.class, TrackedDataHandlerRegistry.INTEGER);
		PICKABLE_FOOD_FILTER = (itemEntity) -> {
			Item item = itemEntity.getStack().getItem();
			return item == Items.GLOW_BERRIES.asItem() && itemEntity.isAlive() && !itemEntity.cannotPickup();
		};
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(GLARE_FLAGS, (byte) 0);
		this.dataTracker.startTracking(TAMEABLE_FLAGS, (byte) 0);
		this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
		this.dataTracker.startTracking(
			TICKS_UNTIL_CAN_FIND_DARK_SPOT,
			this.generateRandomTicksUntilCanFindDarkSpot()
		);
		this.dataTracker.startTracking(
			TICKS_UNTIL_CAN_EAT_GLOW_BERRIES,
			this.generateRandomTicksUntilCanEatGlowBerries()
		);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		if (this.getOwnerUuid() != null) {
			nbt.putUuid("Owner", this.getOwnerUuid());
		}
		nbt.putBoolean("Sitting", this.sitting);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		UUID uUID;
		if (nbt.containsUuid("Owner")) {
			uUID = nbt.getUuid("Owner");
		} else {
			String string = nbt.getString("Owner");
			uUID = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
		}

		if (uUID != null) {
			try {
				this.setOwnerUuid(uUID);
				this.setTamed(true);
			} catch (Throwable var4) {
				this.setTamed(false);
			}
		}

		this.sitting = nbt.getBoolean("Sitting");
		this.setInSittingPose(this.sitting);
	}

	private boolean hasGlareFlag(int bitmask) {
		return (this.dataTracker.get(GLARE_FLAGS) & bitmask) != 0;
	}

	private void setGlareFlag(int bitmask, boolean value) {
		byte glareFlags = this.dataTracker.get(GLARE_FLAGS);

		if (value) {
			this.dataTracker.set(GLARE_FLAGS, (byte) (glareFlags | bitmask));
		} else {
			this.dataTracker.set(GLARE_FLAGS, (byte) (glareFlags & ~bitmask));
		}
	}

	public static boolean canSpawn(
		EntityType<GlareEntity> glareEntityEntityType,
		ServerWorldAccess serverWorldAccess,
		SpawnReason spawnReason,
		BlockPos blockPos,
		Random random
	) {
		BlockState blockState = serverWorldAccess.getBlockState(blockPos.down());
		boolean isAboveSurfaceLevel = blockPos.getY() >= 63;
		boolean isSkyVisible = serverWorldAccess.isSkyVisible(blockPos);
		boolean isBlockPosDarkSpot = serverWorldAccess.getBaseLightLevel(blockPos, 0) == 0;
		boolean isRelatedBlock = (
			blockState.isOf(Blocks.MOSS_BLOCK)
			|| blockState.isOf(Blocks.MOSS_CARPET)
			|| blockState.isOf(Blocks.AZALEA)
			|| blockState.isOf(Blocks.FLOWERING_AZALEA)
			|| blockState.isOf(Blocks.GRASS)
			|| blockState.isOf(Blocks.SMALL_DRIPLEAF)
			|| blockState.isOf(Blocks.BIG_DRIPLEAF)
			|| blockState.isOf(Blocks.CLAY)
			|| blockState.isOf(Blocks.DIRT)
			|| blockState.isOf(Blocks.GRAVEL)
		);
		return !isAboveSurfaceLevel
			   && !isSkyVisible
			   && !isBlockPosDarkSpot
			   && isRelatedBlock;
	}

	protected void initGoals() {
		this.goalSelector.add(1, new GlareSitGoal(this));
		this.goalSelector.add(2, new GlareAvoidMonsterGoal(this, AbstractSkeletonEntity.class, 24.0F));
		this.goalSelector.add(2, new GlareAvoidMonsterGoal(this, CreeperEntity.class, 24.0F));
		this.goalSelector.add(2, new GlareAvoidMonsterGoal(this, EndermanEntity.class, 24.0F));
		this.goalSelector.add(2, new GlareAvoidMonsterGoal(this, SpiderEntity.class, 24.0F));
		this.goalSelector.add(2, new GlareAvoidMonsterGoal(this, WitchEntity.class, 24.0F));
		this.goalSelector.add(2, new GlareAvoidMonsterGoal(this, ZombieEntity.class, 24.0F));
		this.goalSelector.add(3, new GlareFollowOwnerGoal(this, 8.0F, 2.0F, false));
		this.eatGlowBerriesGoal = new GlareEatGlowBerriesGoal(this);
		this.goalSelector.add(4, this.eatGlowBerriesGoal);
		this.goalSelector.add(4, new GlareShakeOffGlowBerriesGoal(this));
		this.flyToDarkSpotGoal = new GlareFlyToDarkSpotGoal(this);
		this.goalSelector.add(5, this.flyToDarkSpotGoal);
		this.goalSelector.add(6, new GlareWanderAroundGoal(this));
		this.goalSelector.add(6, new SwimGoal(this));
	}

	@Override
	public void tick() {
		super.tick();

		if (this.getTicksUntilCanFindDarkSpot() > 0) {
			this.setTicksUntilCanFindDarkSpot(this.getTicksUntilCanFindDarkSpot() - 1);
		}

		if (this.getTicksUntilCanEatGlowBerries() > 0) {
			this.setTicksUntilCanEatGlowBerries(this.getTicksUntilCanEatGlowBerries() - 1);
		} else if (
			!this.getWorld().isClient()
			&& !this.eatGlowBerriesGoal.isRunning
			&& this.getTicksUntilCanEatGlowBerries() == 0
			&& !this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()
		) {
			this.dropItem(this.getMainHandStack().getItem());
			this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
		}

		this.updateTargetEyesPositionOffset();
	}

	public boolean canEquip(ItemStack stack) {
		EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(stack);
		if (!this.getEquippedStack(equipmentSlot).isEmpty()) {
			return false;
		} else {
			return equipmentSlot == EquipmentSlot.MAINHAND && super.canEquip(stack);
		}
	}

	public boolean canPickupItem(ItemStack stack) {
		return this.eatGlowBerriesGoal.isRunning
			   && this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
	}

	private void dropItem(ItemStack stack) {
		ItemEntity itemEntity = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), stack);
		this.world.spawnEntity(itemEntity);
	}

	protected void loot(ItemEntity item) {
		ItemStack itemStack = item.getStack();
		if (this.canPickupItem(itemStack) && PICKABLE_FOOD_FILTER.test(item)) {
			int i = itemStack.getCount();
			if (i > 1) {
				this.dropItem(itemStack.split(i - 1));
			}

			this.triggerItemPickedUpByEntityCriteria(item);
			this.equipStack(EquipmentSlot.MAINHAND, itemStack.split(1));
			this.sendPickup(item, itemStack.getCount());
			item.discard();
		}
	}

	public Vec2f getCurrentEyesPositionOffset() {
		return this.currentEyesPositionOffset;
	}

	public void setCurrentEyesPositionOffset(Vec2f currentEyesPositionOffset) {
		this.currentEyesPositionOffset = currentEyesPositionOffset;
	}

	public Vec2f getTargetEyesPositionOffset() {
		return this.targetEyesPositionOffset;
	}

	private void updateTargetEyesPositionOffset() {
		if (this.getRandom().nextInt(40) == 0) {

			float xEyePosition = RandomGenerator.generateFloat(-0.5F, 0.5F);
			float yEyePosition = RandomGenerator.generateFloat(-0.4F, 0.4F);
			this.targetEyesPositionOffset = new Vec2f(
				xEyePosition,
				yEyePosition
			);
		}
	}

	public float getCurrentLayersPitch() {
		return this.currentLayerPitch;
	}

	public void setCurrentLayerPitch(float currentLayersPitch) {
		this.currentLayerPitch = currentLayersPitch;
	}

	public float getCurrentLayersRoll() {
		return this.currentLayerRoll;
	}

	public void setCurrentLayerRoll(float currentLayersRoll) {
		this.currentLayerRoll = currentLayersRoll;
	}

	public float getCurrentLayerPitchAnimationProgress() {
		return this.currentLayerPitchAnimationProgress;
	}

	public void setCurrentLayerPitchAnimationProgress(float currentLayersPitchAnimationProgress) {
		this.currentLayerPitchAnimationProgress = currentLayersPitchAnimationProgress;
	}

	public float getCurrentLayerRollAnimationProgress() {
		return this.currentLayerRollAnimationProgress;
	}

	public void setCurrentLayerRollAnimationProgress(float currentLayersRollAnimationProgress) {
		this.currentLayerRollAnimationProgress = currentLayersRollAnimationProgress;
	}

	public static Builder createGlareAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
			.add(EntityAttributes.GENERIC_FLYING_SPEED, MOVEMENT_SPEED)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED * 0.5F)
			.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0D);
	}

	protected EntityNavigation createNavigation(World world) {
		GlareNavigation glareNavigation = new GlareNavigation(this, world)
		{
			public boolean isValidPosition(BlockPos pos) {
				return !this.world.getBlockState(pos.down()).isAir();
			}
		};

		glareNavigation.setCanPathThroughDoors(false);
		glareNavigation.setCanSwim(false);
		glareNavigation.setCanEnterOpenDoors(true);
		EntityNavigationAccessor entityNavigation = (EntityNavigationAccessor) glareNavigation;
		entityNavigation.setNodeReachProximity(0.1F);

		return glareNavigation;
	}

	protected void playStepSound(BlockPos pos, BlockState state) {
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundRegistry.ENTITY_GLARE_AMBIENT;
	}

	@Override
	public void playAmbientSound() {
		SoundEvent soundEvent = this.getAmbientSound();
		this.playSound(soundEvent, 0.25F, 0.1F);
	}

	@Override
	public SoundEvent getEatSound(ItemStack stack) {
		return SoundRegistry.ENTITY_GLARE_EAT;
	}

	public void playEatSound(ItemStack stack) {
		SoundEvent soundEvent = this.getEatSound(stack);
		this.playSound(soundEvent, 1.0F, 1.0F);
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundRegistry.ENTITY_GLARE_HURT;
	}

	@Override
	protected void playHurtSound(DamageSource source) {
		this.playSound(this.getHurtSound(source), 1.0F, 0.5F);
	}

	protected SoundEvent getDeathSound() {
		return SoundRegistry.ENTITY_GLARE_DEATH;
	}

	protected float getSoundVolume() {
		return 1.0F;
	}

	@Override
	public ActionResult interactMob(
		PlayerEntity player,
		Hand hand
	) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item itemInHand = itemStack.getItem();
		boolean interactionResult = false;

		if (itemInHand == Items.GLOW_BERRIES) {
			if (this.isTamed()) {
				interactionResult = this.tryToHealWithGlowBerries(player, itemStack);
			} else {
				interactionResult = this.tryToTameWithGlowBerries(player, itemStack);
			}
		}

		if (interactionResult) {
			this.emitGameEvent(GameEvent.MOB_INTERACT, this.getCameraBlockPos());
			return ActionResult.success(this.world.isClient);
		}

		ActionResult actionResult = super.interactMob(player, hand);

		if (
			actionResult.isAccepted() == false
			&& this.isOwner(player) == true
		) {
			this.setSitting(!this.isSitting());
			this.jumping = false;
			this.navigation.stop();
			return ActionResult.SUCCESS;
		}

		return actionResult;
	}

	private boolean tryToHealWithGlowBerries(
		PlayerEntity player,
		ItemStack itemStack
	) {
		if (this.getHealth() == this.getMaxHealth()) {
			return false;
		}

		if (this.world.isClient) {
			return true;
		}

		Item glowBerries = itemStack.getItem();

		if (glowBerries.getFoodComponent() == null) {
			return false;
		}

		this.heal((float) glowBerries.getFoodComponent().getHunger());

		if (!player.getAbilities().creativeMode) {
			itemStack.decrement(1);
		}

		this.playEatSound(itemStack);

		ItemStackParticleEffect particleEffect = new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
		this.spawnParticles(particleEffect, 7);

		return true;
	}

	private boolean tryToTameWithGlowBerries(
		PlayerEntity player,
		ItemStack itemStack
	) {
		if (this.world.isClient) {
			return true;
		}

		if (!player.getAbilities().creativeMode) {
			itemStack.decrement(1);
		}

		this.playEatSound(itemStack);

		if (this.random.nextInt(3) == 0) {
			this.setOwner(player);
			this.world.sendEntityStatus(this, (byte) 7);
		} else {
			this.world.sendEntityStatus(this, (byte) 6);
		}

		return true;
	}

	public boolean isInAir() {
		return !this.onGround;
	}

	protected void swimUpward(Tag<Fluid> fluid) {
		this.setVelocity(this.getVelocity().add(0.0D, 0.01D, 0.0D));
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
	}

	public boolean canBeLeashedBy(PlayerEntity player) {
		return !this.isLeashed();
	}

	protected void showEmoteParticle(boolean positive) {
		ParticleEffect particleEffect;

		if (positive) {
			particleEffect = ParticleTypes.HEART;
		} else {
			particleEffect = ParticleTypes.SMOKE;
		}

		for (int i = 0; i < 7; ++i) {
			double d = this.random.nextGaussian() * 0.02D;
			double e = this.random.nextGaussian() * 0.02D;
			double f = this.random.nextGaussian() * 0.02D;
			this.world.addParticle(particleEffect, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D, this.getParticleZ(1.0D), d, e, f);
		}
	}

	public void handleStatus(byte status) {
		if (status == 7) {
			this.showEmoteParticle(true);
		} else if (status == 6) {
			this.showEmoteParticle(false);
		} else {
			super.handleStatus(status);
		}
	}

	public boolean isTamed() {
		return (this.dataTracker.get(TAMEABLE_FLAGS) & 4) != 0;
	}

	public void setTamed(boolean tamed) {
		byte b = this.dataTracker.get(TAMEABLE_FLAGS);
		if (tamed) {
			this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b | 4));
			this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0D);
			this.setHealth(this.getMaxHealth());
		} else {
			this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b & -5));
			this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(10.0D);
		}

		this.onTamedChanged();
	}

	protected void onTamedChanged() {
	}

	@Nullable
	public UUID getOwnerUuid() {
		return (UUID) ((Optional) this.dataTracker.get(OWNER_UUID)).orElse(null);
	}

	public void setOwnerUuid(@Nullable UUID uuid) {
		this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
	}

	public void setOwner(PlayerEntity player) {
		this.setTamed(true);
		this.setOwnerUuid(player.getUuid());
		if (player instanceof ServerPlayerEntity) {
			CriteriaRegistry.TAME_GLARE.trigger((ServerPlayerEntity) player, this);
		}
	}

	@Nullable
	public LivingEntity getOwner() {
		try {
			UUID uUID = this.getOwnerUuid();
			return uUID == null ? null:this.world.getPlayerByUuid(uUID);
		} catch (IllegalArgumentException var2) {
			return null;
		}
	}

	public boolean isOwner(LivingEntity entity) {
		return entity == this.getOwner();
	}

	public boolean isInSittingPose() {
		return (this.dataTracker.get(TAMEABLE_FLAGS) & 1) != 0;
	}

	public void setInSittingPose(boolean inSittingPose) {
		byte b = this.dataTracker.get(TAMEABLE_FLAGS);
		if (inSittingPose) {
			this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b | 1));
		} else {
			this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b & -2));
		}
	}

	public boolean isSitting() {
		return this.sitting;
	}

	public void setSitting(boolean sitting) {
		this.sitting = sitting;
	}

	public void onDeath(DamageSource source) {
		if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES) && this.getOwner() instanceof ServerPlayerEntity) {
			this.getOwner().sendSystemMessage(this.getDamageTracker().getDeathMessage(), Util.NIL_UUID);
		}

		super.onDeath(source);
	}

	@Override
	public boolean damage(
		DamageSource source,
		float amount
	) {
		if (!this.getWorld().isClient()) {
			this.flyToDarkSpotGoal.stop();
		}

		this.setSitting(false);

		return super.damage(source, amount);
	}

	public int getTicksUntilCanFindDarkSpot() {
		return this.dataTracker.get(TICKS_UNTIL_CAN_FIND_DARK_SPOT);
	}

	public void setTicksUntilCanFindDarkSpot(int ticksUntilCanFindDarkSpot) {
		this.dataTracker.set(TICKS_UNTIL_CAN_FIND_DARK_SPOT, ticksUntilCanFindDarkSpot);
	}

	public int generateRandomTicksUntilCanFindDarkSpot() {
		return RandomGenerator.generateInt(
			MIN_TICKS_UNTIL_CAN_FIND_DARK_SPOT,
			MAX_TICKS_UNTIL_CAN_FIND_DARK_SPOT
		);
	}

	public int getTicksUntilCanEatGlowBerries() {
		return this.dataTracker.get(TICKS_UNTIL_CAN_EAT_GLOW_BERRIES);
	}

	public void setTicksUntilCanEatGlowBerries(int ticksUntilCanEatGlowBerries) {
		this.dataTracker.set(TICKS_UNTIL_CAN_EAT_GLOW_BERRIES, ticksUntilCanEatGlowBerries);
	}

	public int generateRandomTicksUntilCanEatGlowBerries() {
		return RandomGenerator.generateInt(
			MIN_TICKS_UNTIL_CAN_EAT_GLOW_BERRIES,
			MAX_TICKS_UNTIL_CAN_EAT_GLOW_BERRIES
		);
	}

	@Override
	public Vec3d getLeashOffset() {
		return new Vec3d(0.0D, this.getStandingEyeHeight() * 0.6D, 0.0D);
	}

	@Override
	protected float getActiveEyeHeight(EntityPose poseIn, EntityDimensions sizeIn) {
		return 1.1F;
	}

	@Override
	public float getMovementSpeed() {
		return MOVEMENT_SPEED;
	}

	public float getFastMovementSpeed() {
		return MOVEMENT_SPEED * 1.25F;
	}

	public void setGrumpy(boolean grumpy) {
		this.setGlareFlag(GRUMPY_BITMASK, grumpy);
	}

	public boolean isGrumpy() {
		return this.hasGlareFlag(GRUMPY_BITMASK);
	}

	public boolean isMoving() {
		return !this.isOnGround() && this.getVelocity().lengthSquared() >= 0.0001;
	}

	public void spawnParticles(
		ParticleEffect particleEffect,
		int amount
	) {
		if (!this.world.isClient()) {
			for (int i = 0; i < amount; i++) {
				((ServerWorld) this.getEntityWorld()).spawnParticles(
					particleEffect,
					this.getParticleX(1.0D),
					this.getRandomBodyY() + 0.5D,
					this.getParticleZ(1.0D),
					1,
					this.getRandom().nextGaussian() * 0.02D,
					this.getRandom().nextGaussian() * 0.02D,
					this.getRandom().nextGaussian() * 0.02D,
					1.0D
				);
			}
		}
	}
}
