package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.animation.AnimationContextTracker;
import com.faboslav.friendsandfoes.entity.ai.goal.*;
import com.faboslav.friendsandfoes.init.FriendsAndFoesCriteria;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer.Builder;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class GlareEntity extends TameableEntity implements Flutterer, AnimatedEntity
{
	public static final Predicate<ItemEntity> PICKABLE_FOOD_FILTER;
	private static final Ingredient TEMPT_ITEMS = Ingredient.fromTag(FriendsAndFoesTags.GLARE_TEMPT_ITEMS);
	private static final int GRUMPY_BITMASK = 2;
	private static final float MOVEMENT_SPEED = 0.6F;
	public static final int MIN_EYE_ANIMATION_TICK_AMOUNT = 10;
	public static final int LIGHT_THRESHOLD = 5;
	public static final int MIN_TICKS_UNTIL_CAN_FIND_DARK_SPOT = 200;
	public static final int MAX_TICKS_UNTIL_CAN_FIND_DARK_SPOT = 400;
	public static final int MIN_TICKS_UNTIL_CAN_EAT_GLOW_BERRIES = 300;
	public static final int MAX_TICKS_UNTIL_CAN_EAT_GLOW_BERRIES = 600;

	private static final TrackedData<Byte> GLARE_FLAGS;
	private static final TrackedData<Integer> TICKS_UNTIL_CAN_FIND_DARK_SPOT;
	private static final TrackedData<Integer> TICKS_UNTIL_CAN_EAT_GLOW_BERRIES;

	public GlareEatGlowBerriesGoal eatGlowBerriesGoal;
	public GlareShakeOffGlowBerriesGoal shakeOffGlowBerriesGoal;
	private GlareFlyToDarkSpotGoal flyToDarkSpotGoal;

	@Environment(EnvType.CLIENT)
	private AnimationContextTracker animationTickTracker;

	private Vec2f targetEyesPositionOffset;
	private float currentLayerPitch;
	private float currentLayerRoll;
	private float currentLayerPitchAnimationProgress;
	private float currentLayerRollAnimationProgress;

	public GlareEntity(EntityType<? extends GlareEntity> entityType, World world) {
		super(entityType, world);

		this.moveControl = new FlightMoveControl(this, 10, true);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_CACTUS, -1.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
		this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);
		this.setCanPickUpLoot(true);

		this.targetEyesPositionOffset = new Vec2f(0.0F, 0.0F);
		this.currentLayerPitch = 0.0F;
		this.currentLayerRoll = 0.0F;
		this.currentLayerPitchAnimationProgress = 0.0F;
		this.currentLayerRollAnimationProgress = 0.0F;
	}

	static {
		GLARE_FLAGS = DataTracker.registerData(GlareEntity.class, TrackedDataHandlerRegistry.BYTE);
		TICKS_UNTIL_CAN_EAT_GLOW_BERRIES = DataTracker.registerData(GlareEntity.class, TrackedDataHandlerRegistry.INTEGER);
		TICKS_UNTIL_CAN_FIND_DARK_SPOT = DataTracker.registerData(GlareEntity.class, TrackedDataHandlerRegistry.INTEGER);
		PICKABLE_FOOD_FILTER = (itemEntity) -> {
			return itemEntity.getStack().isIn(FriendsAndFoesTags.GLARE_FOOD_ITEMS) && itemEntity.isAlive() && itemEntity.cannotPickup() == false;
		};
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(GLARE_FLAGS, (byte) 0);
		this.dataTracker.startTracking(
			TICKS_UNTIL_CAN_FIND_DARK_SPOT,
			this.generateRandomTicksUntilCanFindDarkSpot()
		);
		this.dataTracker.startTracking(
			TICKS_UNTIL_CAN_EAT_GLOW_BERRIES,
			this.generateRandomTicksUntilCanEatGlowBerries()
		);
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

		boolean isBelowSurfaceLevel = blockPos.getY() < 63;
		boolean isSkyHidden = serverWorldAccess.isSkyVisible(blockPos) == false;
		boolean isBlockPosLightEnough = serverWorldAccess.getLightLevel(blockPos, 0) > LIGHT_THRESHOLD;
		boolean isRelatedBlock = blockState.isIn(FriendsAndFoesTags.GLARES_SPAWNABLE_ON);

		return isBelowSurfaceLevel
			   && isRelatedBlock
			   && isSkyHidden
			   && isBlockPosLightEnough;
	}

	protected void initGoals() {
		this.goalSelector.add(1, new GlareSitGoal(this));
		this.goalSelector.add(2, new GlareFollowOwnerGoal(this, 8.0F, 2.0F, false));
		this.goalSelector.add(3, new GlareAvoidMonsterGoal(this, HostileEntity.class, 16.0F));
		this.goalSelector.add(4, new AnimalMateGoal(this, 1.0));
		this.goalSelector.add(4, new TemptGoal(this, 1.2, TEMPT_ITEMS, false));
		this.goalSelector.add(4, new FollowParentGoal(this, 1.1));
		this.eatGlowBerriesGoal = new GlareEatGlowBerriesGoal(this);
		this.goalSelector.add(5, this.eatGlowBerriesGoal);
		this.shakeOffGlowBerriesGoal = new GlareShakeOffGlowBerriesGoal(this);
		this.goalSelector.add(5, this.shakeOffGlowBerriesGoal);
		this.flyToDarkSpotGoal = new GlareFlyToDarkSpotGoal(this);
		this.goalSelector.add(6, this.flyToDarkSpotGoal);
		this.goalSelector.add(7, new GlareWanderAroundGoal(this));
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(9, new SwimGoal(this));
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		if (this.getWorld().isClient() == false && this.isAlive() && this.age % 10 == 0) {
			this.heal(1.0F);
		}
	}

	@Override
	public void tick() {
		if (FriendsAndFoes.getConfig().enableGlare == false) {
			this.discard();
		}

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
		ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), stack);
		this.getWorld().spawnEntity(itemEntity);
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

	public Vec2f getTargetEyesPositionOffset() {
		return this.targetEyesPositionOffset;
	}

	public void setTargetEyesPositionOffset(float xEyePositionOffset, float yEyePositionOffset) {
		this.targetEyesPositionOffset = new Vec2f(xEyePositionOffset, yEyePositionOffset);
	}

	private void updateTargetEyesPositionOffset() {
		if (
			this.age % MIN_EYE_ANIMATION_TICK_AMOUNT != 0
			|| RandomGenerator.generateInt(0, 2) != 0
		) {
			return;
		}

		this.setTargetEyesPositionOffset(
			RandomGenerator.generateFloat(-0.5F, 0.5F),
			RandomGenerator.generateFloat(-0.4F, 0.4F)
		);
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

	public static Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
			.add(EntityAttributes.GENERIC_FLYING_SPEED, MOVEMENT_SPEED)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED * 0.5F)
			.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0D);
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		BirdNavigation birdNavigation = new BirdNavigation(this, world)
		{
			public boolean isValidPosition(BlockPos pos) {
				return this.world.getBlockState(pos.down()).isAir() == false;
			}
		};

		birdNavigation.setCanPathThroughDoors(false);
		birdNavigation.setCanSwim(false);
		birdNavigation.setCanEnterOpenDoors(true);

		return birdNavigation;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return FriendsAndFoesSoundEvents.ENTITY_GLARE_AMBIENT.get();
	}

	@Override
	public void playAmbientSound() {
		SoundEvent soundEvent = this.getAmbientSound();
		this.playSound(soundEvent, 0.025F, RandomGenerator.generateFloat(0.85F, 1.25F));
	}

	private SoundEvent getGrumpinessSound() {
		return FriendsAndFoesSoundEvents.ENTITY_GLARE_GRUMPINESS.get();
	}

	public void playGrumpinessSound() {
		SoundEvent soundEvent = this.getGrumpinessSound();
		this.playSound(soundEvent, 0.05F, RandomGenerator.generateFloat(1.2F, 1.3F));
	}

	private SoundEvent getGrumpinessShortSound() {
		return FriendsAndFoesSoundEvents.ENTITY_GLARE_GRUMPINESS_SHORT.get();
	}

	public void playGrumpinessShortSound() {
		SoundEvent soundEvent = this.getGrumpinessShortSound();
		this.playSound(soundEvent, 0.05F, RandomGenerator.generateFloat(1.2F, 1.3F));
	}

	private SoundEvent getRustleSound() {
		return FriendsAndFoesSoundEvents.ENTITY_GLARE_RUSTLE.get();
	}

	public void playRustleSound() {
		SoundEvent soundEvent = this.getRustleSound();
		this.playSound(soundEvent, 0.1F, 0.1F);
	}

	@Override
	public SoundEvent getEatSound(ItemStack stack) {
		return FriendsAndFoesSoundEvents.ENTITY_GLARE_EAT.get();
	}

	public void playEatSound(ItemStack stack) {
		SoundEvent soundEvent = this.getEatSound(stack);
		this.playSound(soundEvent, 1.0F, 1.0F);
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return FriendsAndFoesSoundEvents.ENTITY_GLARE_HURT.get();
	}

	@Override
	protected void playHurtSound(DamageSource source) {
		this.ambientSoundChance = -this.getMinAmbientSoundDelay();
		this.playSound(this.getHurtSound(source), 1.0F, 0.5F);
	}

	protected SoundEvent getDeathSound() {
		return FriendsAndFoesSoundEvents.ENTITY_GLARE_DEATH.get();
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
			if (
				this.isTamed()
				&& this.getHealth() < this.getMaxHealth()
				&& this.isBreedingItem(itemStack) == false
			) {
				interactionResult = this.tryToHealWithGlowBerries(player, itemStack);
			} else if (this.isTamed() == false) {
				interactionResult = this.tryToTameWithGlowBerries(player, itemStack);
			}
		}

		if (interactionResult) {
			this.emitGameEvent(GameEvent.ENTITY_INTERACT, this);
			return ActionResult.success(this.getWorld().isClient());
		}

		ActionResult actionResult = super.interactMob(player, hand);

		if (
			this.isOwner(player)
			&& actionResult.isAccepted() == false
			&& this.isGrumpy() == false
		) {
			this.setSitting(!this.isSitting());
			this.getNavigation().setSpeed(0);
			this.getNavigation().stop();
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

		if (this.getWorld().isClient()) {
			return true;
		}

		Item glowBerries = itemStack.getItem();

		if (glowBerries.getFoodComponent() == null) {
			return false;
		}

		this.heal((float) glowBerries.getFoodComponent().getHunger());

		if (player.getAbilities().creativeMode == false) {
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
		if (this.getWorld().isClient()) {
			return true;
		}

		if (player.getAbilities().creativeMode == false) {
			itemStack.decrement(1);
		}

		this.playEatSound(itemStack);

		if (this.getRandom().nextInt(3) == 0) {
			this.setOwner(player);
			this.getWorld().sendEntityStatus(this, (byte) 7);
		} else {
			this.getWorld().sendEntityStatus(this, (byte) 6);
		}

		return true;
	}

	public boolean isSitting() {
		return isInSittingPose();
	}

	public void setSitting(boolean isSitting) {
		// This is just needed, because original bad implementation
		super.setSitting(isSitting);
		super.setInSittingPose(isSitting);
	}

	public boolean isInAir() {
		return this.isOnGround() == false;
	}

	protected void swimUpward(TagKey<Fluid> tagKey) {
		this.setVelocity(this.getVelocity().add(0.0, 0.01, 0.0));
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
			double d = this.getRandom().nextGaussian() * 0.02D;
			double e = this.getRandom().nextGaussian() * 0.02D;
			double f = this.getRandom().nextGaussian() * 0.02D;
			this.getWorld().addParticle(particleEffect, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D, this.getParticleZ(1.0D), d, e, f);
		}
	}

	public void setTamed(boolean tamed) {
		super.setTamed(tamed);

		if (tamed) {
			this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0D);
			this.setHealth(this.getMaxHealth());
		} else {
			this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(10.0D);
		}
	}

	public void setOwner(PlayerEntity owner) {
		this.setTamed(true);
		this.setOwnerUuid(owner.getUuid());

		if (owner instanceof ServerPlayerEntity) {
			FriendsAndFoesCriteria.TAME_GLARE.trigger((ServerPlayerEntity) owner, this);
		}
	}

	@Override
	public boolean isBreedingItem(ItemStack itemStack) {
		return TEMPT_ITEMS.test(itemStack);
	}

	@Override
	@Nullable
	public PassiveEntity createChild(ServerWorld serverWorld, PassiveEntity entity) {
		GlareEntity glareEntity = FriendsAndFoesEntityTypes.GLARE.get().create(serverWorld);

		if (this.isTamed() == false) {
			return null;
		}

		glareEntity.setOwnerUuid(this.getOwnerUuid());
		glareEntity.setTamed(true);

		return glareEntity;
	}

	@Override
	public boolean canBreedWith(AnimalEntity other) {
		if (
			this.isTamed() == false
			|| (other instanceof GlareEntity) == false
		) {
			return false;
		}

		GlareEntity glare = (GlareEntity) other;
		return glare.isTamed() && super.canBreedWith(other);
	}

	@Override
	public boolean damage(
		DamageSource source,
		float amount
	) {
		if (this.getWorld().isClient() == false) {
			if (this.eatGlowBerriesGoal.isRunning) {
				this.eatGlowBerriesGoal.stop();
			} else if (this.shakeOffGlowBerriesGoal.isRunning) {
				this.shakeOffGlowBerriesGoal.stop();
			} else if (this.flyToDarkSpotGoal.isRunning) {
				this.flyToDarkSpotGoal.stop();
			}
		}

		this.setSitting(false);
		this.getNavigation().setSpeed(0);
		this.getNavigation().stop();

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
		if (this.isBaby()) {
			return MOVEMENT_SPEED / 2.0F;
		}

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
		return this.isOnGround() == false && this.getVelocity().lengthSquared() >= 0.0001;
	}

	public void spawnParticles(
		ParticleEffect particleEffect,
		int amount
	) {
		World world = this.getWorld();

		if (world.isClient()) {
			return;
		}

		for (int i = 0; i < amount; i++) {
			((ServerWorld) world).spawnParticles(
				particleEffect,
				this.getParticleX(1.0D),
				this.getRandomBodyY() + 0.5D,
				this.getParticleZ(1.0D),
				1,
				this.getRandom().nextGaussian() * 0.02D,
				this.getRandom().nextGaussian() * 0.02D,
				this.getRandom().nextGaussian() * 0.02D,
				0.1D
			);
		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public AnimationContextTracker getAnimationContextTracker() {
		if (this.animationTickTracker == null) {
			this.animationTickTracker = new AnimationContextTracker();
		}

		return this.animationTickTracker;
	}
}
