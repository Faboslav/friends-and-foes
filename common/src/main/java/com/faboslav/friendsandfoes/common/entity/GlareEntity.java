package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.common.entity.ai.pathing.CachedPathHolder;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesCriterias;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.util.RandomGenerator;
import com.faboslav.friendsandfoes.common.util.particle.ParticleSpawner;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.CaveVines;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer.Builder;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class GlareEntity extends TameableEntity implements Flutterer, AnimatedEntity
{
	private static final Vec3i ITEM_PICKUP_RANGE_EXPANDER = new Vec3i(1, 1, 1);
	public static final Predicate<ItemEntity> PICKABLE_FOOD_FILTER;
	private static final int GRUMPY_BITMASK = 2;
	private static final float MOVEMENT_SPEED = 0.1F;
	public static final int MIN_EYE_ANIMATION_TICK_AMOUNT = 10;
	public static final int LIGHT_THRESHOLD = 5;

	private static final TrackedData<Byte> GLARE_FLAGS;

	private Vec2f targetEyesPositionOffset;

	public CachedPathHolder cachedPathHolder = new CachedPathHolder();
	private AnimationContextTracker animationContextTracker;

	@Override
	public AnimationContextTracker getAnimationContextTracker() {
		if (this.animationContextTracker == null) {
			this.animationContextTracker = new AnimationContextTracker();
		}

		return this.animationContextTracker;
	}

	public GlareEntity(EntityType<? extends GlareEntity> entityType, World world) {
		super(entityType, world);

		this.moveControl = new GlareMoveControl(this, 24, true);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
		this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);
		this.setCanPickUpLoot(true);

		this.targetEyesPositionOffset = new Vec2f(0.0F, 0.0F);
	}

	@Override
	public EntityData initialize(
		ServerWorldAccess world,
		LocalDifficulty difficulty,
		SpawnReason spawnReason,
		@Nullable EntityData entityData,
		@Nullable NbtCompound entityNbt
	) {
		GlareBrain.setDarkSpotLocatingCooldown(this);
		GlareBrain.setLocatingGlowBerriesCooldown(this);
		GlareBrain.setItemPickupCooldown(this);

		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	static {
		GLARE_FLAGS = DataTracker.registerData(GlareEntity.class, TrackedDataHandlerRegistry.BYTE);
		PICKABLE_FOOD_FILTER = (itemEntity) -> {
			return itemEntity.getStack().isIn(FriendsAndFoesTags.GLARE_FOOD_ITEMS) && itemEntity.isAlive() && itemEntity.cannotPickup() == false;
		};
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(GLARE_FLAGS, (byte) 0);
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
		this.updateTargetEyesPositionOffset();
	}

	@Override
	protected void mobTick() {
		this.getWorld().getProfiler().push("glareBrain");
		this.getBrain().tick((ServerWorld) this.getWorld(), this);
		this.getWorld().getProfiler().pop();
		this.getWorld().getProfiler().push("glareMemoryUpdate");
		GlareBrain.updateMemories(this);
		this.getWorld().getProfiler().pop();
		this.getWorld().getProfiler().push("glareActivityUpdate");
		GlareBrain.updateActivities(this);
		this.getWorld().getProfiler().pop();

		super.mobTick();
	}

	@Override
	public void travel(Vec3d movementInput) {
		if (this.isLogicalSideForUpdatingMovement()) {
			if (this.isTouchingWater()) {
				this.updateVelocity(0.02F, movementInput);
				this.move(MovementType.SELF, this.getVelocity());
				this.setVelocity(this.getVelocity().multiply(0.800000011920929));
			} else if (this.isInLava()) {
				this.updateVelocity(0.02F, movementInput);
				this.move(MovementType.SELF, this.getVelocity());
				this.setVelocity(this.getVelocity().multiply(0.5));
			} else {
				this.updateVelocity(this.getMovementSpeed(), movementInput);
				this.move(MovementType.SELF, this.getVelocity());
				this.setVelocity(this.getVelocity().multiply(0.9100000262260437));
			}
		}

		this.updateLimbs(false);
	}

	@Override
	protected Vec3i getItemPickUpRangeExpander() {
		return ITEM_PICKUP_RANGE_EXPANDER;
	}

	@Override
	public boolean canGather(ItemStack itemStack) {
		return itemStack.isEmpty() == false && itemStack.getItem() == Items.GLOW_BERRIES && super.canGather(itemStack);
	}

	@Override
	public boolean canPickUpLoot() {
		return !this.isItemPickupCoolingDown() && !this.isHoldingItem();
	}

	public boolean isHoldingItem() {
		return !this.getStackInHand(Hand.MAIN_HAND).isEmpty();
	}

	@Override
	public boolean canEquip(ItemStack stack) {
		return false;
	}

	private boolean isItemPickupCoolingDown() {
		return this.getBrain().isMemoryInState(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, MemoryModuleState.VALUE_PRESENT);
	}

	private void dropItem(ItemStack stack) {
		ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), stack);
		this.getWorld().spawnEntity(itemEntity);
	}

	@Override
	protected void loot(ItemEntity item) {
		ItemStack itemStack = item.getStack();

		if (this.canPickupItem(itemStack) && PICKABLE_FOOD_FILTER.test(item)) {
			int i = itemStack.getCount();
			if (i > 1) {
				this.dropItem(itemStack.split(i - 1));
			}

			ItemStackParticleEffect particleEffect = new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
			this.heal(itemStack.getItem().getFoodComponent().getHunger());
			this.playEatSound(itemStack);
			ParticleSpawner.spawnParticles(this, particleEffect, 7, 0.1D);
			GlareBrain.setItemPickupCooldown(this);

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
			|| this.getRandom().nextBetween(0, 2) != 0
		) {
			return;
		}

		this.setTargetEyesPositionOffset(
			-0.5F + this.getRandom().nextFloat(),
			-0.4F + this.getRandom().nextFloat() * (0.4F - -0.4F)
		);
	}

	public static Builder createGlareAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
			.add(EntityAttributes.GENERIC_FLYING_SPEED, MOVEMENT_SPEED)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED)
			.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0D);
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		BirdNavigation birdNavigation = new BirdNavigation(this, world)
		{
			public boolean isValidPosition(BlockPos pos) {
				boolean isValidPos = this.world.getBlockState(pos.down()).isAir() == false && this.world.getBlockState(pos.down()).getMaterial().isLiquid() == false;

				return isValidPos;
			}
		};

		birdNavigation.setCanPathThroughDoors(false);
		birdNavigation.setCanSwim(false);
		birdNavigation.setCanEnterOpenDoors(true);

		return birdNavigation;
	}

	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
		return GlareBrain.create(dynamic);
	}

	@Override
	@SuppressWarnings("all")
	public Brain<GlareEntity> getBrain() {
		return (Brain<GlareEntity>) super.getBrain();
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

	private SoundEvent getShakeSound() {
		return FriendsAndFoesSoundEvents.ENTITY_GLARE_SHAKE.get();
	}

	public void playShakeSound() {
		SoundEvent soundEvent = this.getShakeSound();
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
		) {
			this.setSitting(!this.isSitting());

			if (this.isSitting()) {
				this.stopMovement();
			}

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
		ParticleSpawner.spawnParticles(this, particleEffect, 7, 0.1D);

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
			this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
		} else {
			this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
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

	@Nullable
	public GlobalPos getGlowBerriesPos() {
		return this.getBrain().getOptionalMemory(FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get()).orElse(null);
	}

	public boolean canEatGlowBerriesAt(BlockPos pos) {
		BlockState blockState = this.getWorld().getBlockState(pos);
		return CaveVines.hasBerries(blockState);
	}

	@Nullable
	public GlobalPos getDarkSpotPos() {
		return this.getBrain().getOptionalMemory(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get()).orElse(null);
	}

	public boolean isDarkSpotDark(BlockPos pos) {
		return this.getWorld().getLightLevel(LightType.BLOCK, pos) == 0;
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
			FriendsAndFoesCriterias.TAME_GLARE.trigger((ServerPlayerEntity) owner, this);
		}
	}

	@Override
	public EntityView method_48926() {
		return this.getWorld();
	}

	@Override
	public boolean isBreedingItem(ItemStack itemStack) {
		return GlareBrain.getTemptItems().test(itemStack);
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

		GlareBrain.setDarkSpotLocatingCooldown(this);
		GlareBrain.setLocatingGlowBerriesCooldown(this);
		GlareBrain.setItemPickupCooldown(this);

		return glareEntity;
	}

	@Override
	public boolean canBreedWith(AnimalEntity other) {
		if (
			other == this
			|| !this.isTamed()
			|| !(other instanceof GlareEntity)
		) {
			return false;
		}

		GlareEntity glare = (GlareEntity) other;
		if (
			!glare.isTamed()
			|| glare.isInSittingPose()
		) {
			return false;
		}

		return this.isInLove() && glare.isInLove();
	}

	@Override
	public boolean damage(
		DamageSource source,
		float amount
	) {
		if (this.getWorld().isClient() == false) {
			this.setSitting(false);
			this.getNavigation().setSpeed(0);
			this.getNavigation().stop();
		}

		return super.damage(source, amount);
	}

	@Override
	public Vec3d getLeashOffset() {
		return new Vec3d(0.0D, this.getStandingEyeHeight() * 0.6D, 0.0D);
	}

	@Override
	protected float getActiveEyeHeight(EntityPose poseIn, EntityDimensions sizeIn) {
		if (this.isBaby()) {
			// TODO use size modifier
			return 0.5F;
		}

		return 1.0F;
	}

	@Override
	public float getMovementSpeed() {
		if (this.isBaby()) {
			return MOVEMENT_SPEED / 2.0F;
		}

		return MOVEMENT_SPEED;
	}

	public void setGrumpy(boolean grumpy) {
		this.setGlareFlag(GRUMPY_BITMASK, grumpy);
	}

	public boolean isGrumpy() {
		return this.hasGlareFlag(GRUMPY_BITMASK);
	}

	final class GlareMoveControl extends FlightMoveControl
	{
		public GlareMoveControl(GlareEntity glare, int maxPitchChange, boolean noGravity) {
			super(glare, maxPitchChange, noGravity);
		}

		@Override
		public void tick() {
			if (GlareEntity.this.isSitting()) {
				return;
			}

			super.tick();
		}
	}
}
