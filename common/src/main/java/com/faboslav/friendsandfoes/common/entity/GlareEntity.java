package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.common.entity.ai.pathing.CachedPathHolder;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesCriterias;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.util.MovementUtil;
import com.faboslav.friendsandfoes.common.util.RandomGenerator;
import com.faboslav.friendsandfoes.common.util.particle.ParticleSpawner;
import com.faboslav.friendsandfoes.common.versions.VersionedInteractionResult;
import com.faboslav.friendsandfoes.common.versions.VersionedProfilerProvider;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.function.Predicate;

//? if >=1.21.3 {
import net.minecraft.world.entity.EntitySpawnReason;
//?} else {
/*import net.minecraft.world.entity.MobSpawnType;
 *///?}

@SuppressWarnings({"unchecked"})
public final class GlareEntity extends TamableAnimal implements FlyingAnimal, AnimatedEntity
{
	public static final float ADULT_SCALE = 0.8F;
	public static final float BABY_SCALE = 0.4F;

	private static final Vec3i ITEM_PICKUP_RANGE_EXPANDER = new Vec3i(1, 1, 1);
	public static final Predicate<ItemEntity> PICKABLE_FOOD_FILTER;
	private static final int GRUMPY_BITMASK = 2;
	private static final float MOVEMENT_SPEED = 0.1F;
	public static final int MIN_EYE_ANIMATION_TICK_AMOUNT = 10;
	public static final int LIGHT_THRESHOLD = 5;

	private static final EntityDataAccessor<Byte> GLARE_FLAGS;

	private Vec2 targetEyesPositionOffset;

	public CachedPathHolder cachedPathHolder = new CachedPathHolder();
	private AnimationContextTracker animationContextTracker;

	@Override
	public AnimationContextTracker getAnimationContextTracker() {
		if (this.animationContextTracker == null) {
			this.animationContextTracker = new AnimationContextTracker();
		}

		return this.animationContextTracker;
	}

	@Override
	public ArrayList<AnimationHolder> getTrackedAnimations() {
		return new ArrayList<>();
	}

	@Override
	public AnimationHolder getMovementAnimation() {
		return null;
	}

	@Override
	public int getCurrentAnimationTick() {
		return 0;
	}

	@Override
	public void setCurrentAnimationTick(int currentAnimationTick) {

	}

	@Override
	public @Nullable AnimationHolder getAnimationByPose() {
		return null;
	}

	public GlareEntity(EntityType<? extends GlareEntity> entityType, Level world) {
		super(entityType, world);

		this.setTame(false, false);
		this.moveControl = new GlareMoveControl(this, 24, true);
		this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
		this.setPathfindingMalus(PathType.WATER, -1.0F);
		this.setPathfindingMalus(PathType.LAVA, -1.0F);
		this.setPathfindingMalus(PathType.WATER_BORDER, 16.0F);
		this.setPathfindingMalus(PathType.COCOA, -1.0F);
		this.setPathfindingMalus(PathType.FENCE, -1.0F);
		this.setCanPickUpLoot(true);

		this.targetEyesPositionOffset = new Vec2(0.0F, 0.0F);
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
	) {
		GlareBrain.setDarkSpotLocatingCooldown(this);
		GlareBrain.setLocatingGlowBerriesCooldown(this);
		GlareBrain.setItemPickupCooldown(this);

		return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
	}

	static {
		GLARE_FLAGS = SynchedEntityData.defineId(GlareEntity.class, EntityDataSerializers.BYTE);
		PICKABLE_FOOD_FILTER = (itemEntity) -> {
			return itemEntity.getItem().is(FriendsAndFoesTags.GLARE_FOOD_ITEMS) && itemEntity.isAlive() && !itemEntity.hasPickUpDelay();
		};
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);

		builder.define(GLARE_FLAGS, (byte) 0);
	}

	private boolean hasGlareFlag(int bitmask) {
		return (this.entityData.get(GLARE_FLAGS) & bitmask) != 0;
	}

	private void setGlareFlag(int bitmask, boolean value) {
		byte glareFlags = this.entityData.get(GLARE_FLAGS);

		if (value) {
			this.entityData.set(GLARE_FLAGS, (byte) (glareFlags | bitmask));
		} else {
			this.entityData.set(GLARE_FLAGS, (byte) (glareFlags & ~bitmask));
		}
	}

	public static boolean canSpawn(
		EntityType<GlareEntity> glareEntityEntityType,
		ServerLevelAccessor serverWorldAccess,
		/*? if >=1.21.3 {*/
		EntitySpawnReason spawnReason,
		/*?} else {*/
		/*MobSpawnType spawnReason,
		 *//*?}*/
		BlockPos blockPos,
		RandomSource random
	) {
		BlockState blockState = serverWorldAccess.getBlockState(blockPos.below());

		boolean isBelowSurfaceLevel = blockPos.getY() < 63;
		boolean isSkyHidden = serverWorldAccess.canSeeSky(blockPos) == false;
		boolean isBlockPosLightEnough = serverWorldAccess.getMaxLocalRawBrightness(blockPos, 0) > LIGHT_THRESHOLD;
		boolean isRelatedBlock = blockState.is(FriendsAndFoesTags.GLARES_SPAWNABLE_ON);

		return isBelowSurfaceLevel
			   && isRelatedBlock
			   && isSkyHidden
			   && isBlockPosLightEnough;
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (this.level().isClientSide() == false && this.isAlive() && this.tickCount % 10 == 0) {
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
	protected void customServerAiStep(/*? if >=1.21.3 {*/ServerLevel level/*?}*/)
	{
		//? if <1.21.3 {
		/*var level = (ServerLevel) this.level();
		*///?}

		var profiler = VersionedProfilerProvider.getProfiler(this);
		profiler.push("glareBrain");
		this.getBrain().tick(level, this);
		profiler.pop();

		profiler.push("glareMemoryUpdate");
		GlareBrain.updateMemories(this);
		profiler.pop();

		profiler.push("glareActivityUpdate");
		GlareBrain.updateActivities(this);
		profiler.pop();

		super.customServerAiStep(/*? if >=1.21.3 {*/level/*?}*/);
	}

	@Override
	public void travel(Vec3 movementInput) {
		//? if <=1.21.4 {
		/*if (!this.isControlledByLocalInstance()) {
			return;
		}
		*///?}

		if (this.isInWater()) {
			this.moveRelative(0.02F, movementInput);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.800000011920929));
		} else if (this.isInLava()) {
			this.moveRelative(0.02F, movementInput);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
		} else {
			this.moveRelative(this.getSpeed(), movementInput);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9100000262260437));
		}
	}

	@Override
	protected Vec3i getPickupReach() {
		return ITEM_PICKUP_RANGE_EXPANDER;
	}

	@Override
	public boolean wantsToPickUp(/*? if >=1.21.3 {*/ServerLevel level, /*?}*/ItemStack itemStack) {
		return !itemStack.isEmpty() && itemStack.getItem() == Items.GLOW_BERRIES && super.wantsToPickUp(/*? if >=1.21.3 {*/level, /*?}*/itemStack);
	}

	@Override
	public boolean canPickUpLoot() {
		return !this.isItemPickupCoolingDown() && !this.isHoldingItem();
	}

	public boolean isHoldingItem() {
		return !this.getItemInHand(InteractionHand.MAIN_HAND).isEmpty();
	}

	//? if <1.21.3 {
	/*@Override
	public boolean canTakeItem(ItemStack stack) {
		return false;
	}
	*///?}

	private boolean isItemPickupCoolingDown() {
		return this.getBrain().checkMemory(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, MemoryStatus.VALUE_PRESENT);
	}

	private void dropItem(ItemStack stack) {
		ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), stack);
		this.level().addFreshEntity(itemEntity);
	}

	@Override
	protected void pickUpItem(/*? if >=1.21.3 {*/ServerLevel level, /*?}*/ItemEntity item) {
		ItemStack itemStack = item.getItem();

		if (this.canHoldItem(itemStack) && PICKABLE_FOOD_FILTER.test(item)) {
			int i = itemStack.getCount();
			if (i > 1) {
				this.dropItem(itemStack.split(i - 1));
			}

			ItemParticleOption particleEffect = new ItemParticleOption(ParticleTypes.ITEM, itemStack);
			FoodProperties foodComponent = itemStack.get(DataComponents.FOOD);
			float foodNutritionMultiplier = foodComponent != null ? (float) foodComponent.nutrition():1.0F;
			this.heal(2.0F * foodNutritionMultiplier);
			this.playEatSound(itemStack);
			ParticleSpawner.spawnParticles(this, particleEffect, 7, 0.1D);
			GlareBrain.setItemPickupCooldown(this);

			item.discard();
		}
	}

	public Vec2 getTargetEyesPositionOffset() {
		return this.targetEyesPositionOffset;
	}

	public void setTargetEyesPositionOffset(float xEyePositionOffset, float yEyePositionOffset) {
		this.targetEyesPositionOffset = new Vec2(xEyePositionOffset, yEyePositionOffset);
	}

	private void updateTargetEyesPositionOffset() {
		if (
			this.tickCount % MIN_EYE_ANIMATION_TICK_AMOUNT != 0
			|| this.getRandom().nextIntBetweenInclusive(0, 2) != 0
		) {
			return;
		}

		this.setTargetEyesPositionOffset(
			-0.5F + this.getRandom().nextFloat(),
			-0.4F + this.getRandom().nextFloat() * (0.8F)
		);
	}

	public static Builder createGlareAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 10.0D)
			.add(Attributes.FLYING_SPEED, MOVEMENT_SPEED)
			.add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
			//? if >= 1.21.4 {
			.add(Attributes.TEMPT_RANGE, 10.0D)
			//?}
			.add(Attributes.FOLLOW_RANGE, 48.0D);
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, world)
		{
			public boolean isStableDestination(BlockPos pos) {
				return !this.level.getBlockState(pos.below()).isAir() && !this.level.getBlockState(pos.below()).liquid();
			}

			@Override
			public void tick() {
				if (
					GlareEntity.this.isOrderedToSit()
					|| GlareEntity.this.isGrumpy()
				) {
					return;
				}

				super.tick();
			}
		};

		flyingPathNavigation.setCanOpenDoors(false);
		flyingPathNavigation.setCanFloat(false);

		// TODO check this on other classes
		//flyingPathNavigation.setCanPassDoors(true);

		// TODO check this if this is useful or not
		//? if >=1.21.3 {
		flyingPathNavigation.setRequiredPathLength(48.0F);
		//?}

		return flyingPathNavigation;
	}

	@Override
	protected Brain<?> makeBrain(Dynamic<?> dynamic) {
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
		this.ambientSoundTime = -this.getAmbientSoundInterval();
		this.playSound(this.getHurtSound(source), 1.0F, 0.5F);
	}

	protected SoundEvent getDeathSound() {
		return FriendsAndFoesSoundEvents.ENTITY_GLARE_DEATH.get();
	}

	protected float getSoundVolume() {
		return 1.0F;
	}

	@Override
	public InteractionResult mobInteract(
		Player player,
		InteractionHand hand
	) {
		ItemStack itemStack = player.getItemInHand(hand);
		Item itemInHand = itemStack.getItem();
		boolean interactionResult = false;

		if (itemInHand == Items.GLOW_BERRIES) {
			if (
				this.isTame()
				&& this.getHealth() < this.getMaxHealth()
				&& this.isFood(itemStack) == false
			) {
				interactionResult = this.tryToHealWithGlowBerries(player, itemStack);
			} else if (this.isTame() == false) {
				interactionResult = this.tryToTameWithGlowBerries(player, itemStack);
			}
		}

		if (interactionResult) {
			this.gameEvent(GameEvent.ENTITY_INTERACT, this);
			return VersionedInteractionResult.success(this);
		}

		InteractionResult actionResult = super.mobInteract(player, hand);

		if (
			this.isOwnedBy(player)
			&& actionResult.consumesAction() == false
		) {
			this.setOrderedToSit(!this.isOrderedToSit());

			if (this.isOrderedToSit()) {
				MovementUtil.stopMovement(this);
			}

			return InteractionResult.SUCCESS;
		}

		return actionResult;
	}

	private boolean tryToHealWithGlowBerries(
		Player player,
		ItemStack itemStack
	) {
		if (this.getHealth() == this.getMaxHealth()) {
			return false;
		}

		if (this.level() instanceof ServerLevel serverLevel) {
			FoodProperties foodComponent = itemStack.get(DataComponents.FOOD);
			if (foodComponent == null) {
				return false;
			}

			this.heal(2.0F * foodComponent.nutrition());
			this.playEatSound(itemStack);
			itemStack.consume(1, player);

			ItemParticleOption particleEffect = new ItemParticleOption(ParticleTypes.ITEM, itemStack);
			ParticleSpawner.spawnParticles(this, particleEffect, 7, 0.1D);
		}

		return true;
	}

	private boolean tryToTameWithGlowBerries(
		Player player,
		ItemStack itemStack
	) {
		if (this.level() instanceof ServerLevel serverLevel) {
			if (!player.getAbilities().instabuild) {
				itemStack.shrink(1);
			}

			this.playEatSound(itemStack);

			if (this.getRandom().nextInt(3) == 0) {
				this.tame(player);
				this.level().broadcastEntityEvent(this, EntityEvent.TAMING_SUCCEEDED);
			} else {
				this.level().broadcastEntityEvent(this, EntityEvent.TAMING_FAILED);
			}
		}

		return true;
	}

	public boolean isOrderedToSit() {
		return isInSittingPose();
	}

	public void setOrderedToSit(boolean isSitting) {
		// This is just needed, because original bad implementation
		super.setOrderedToSit(isSitting);
		super.setInSittingPose(isSitting);
	}

	@Nullable
	public GlobalPos getGlowBerriesPos() {
		return this.getBrain().getMemoryInternal(FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get()).orElse(null);
	}

	public boolean canEatGlowBerriesAt(BlockPos pos) {
		BlockState blockState = this.level().getBlockState(pos);
		return CaveVines.hasGlowBerries(blockState);
	}

	@Nullable
	public GlobalPos getDarkSpotPos() {
		return this.getBrain().getMemoryInternal(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get()).orElse(null);
	}

	public boolean isDarkSpotDark(BlockPos pos) {
		return this.level().getBrightness(LightLayer.BLOCK, pos) == 0;
	}

	public boolean isFlying() {
		return this.onGround() == false;
	}

	@Override
	protected void jumpInLiquid(TagKey<Fluid> tagKey) {
		this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.01, 0.0));
	}

	//? if <=1.21.4 {
	/*@Override
	public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}*///?}

	@Override
	protected void checkFallDamage(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
	}

	@Override
	public float getAgeScale() {
		return this.isBaby() ? BABY_SCALE : ADULT_SCALE;
	}

	public boolean canBeLeashedBy(Player player) {
		return !this.isLeashed();
	}

	protected void spawnTamingParticles(boolean positive) {
		ParticleOptions particleEffect;

		if (positive) {
			particleEffect = ParticleTypes.HEART;
		} else {
			particleEffect = ParticleTypes.SMOKE;
		}

		for (int i = 0; i < 7; ++i) {
			double d = this.getRandom().nextGaussian() * 0.02D;
			double e = this.getRandom().nextGaussian() * 0.02D;
			double f = this.getRandom().nextGaussian() * 0.02D;
			this.level().addParticle(particleEffect, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d, e, f);
		}
	}

	@Override
	protected void applyTamingSideEffects() {
		if (this.isTame()) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30.0D);
			this.setHealth(this.getMaxHealth());
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10.0D);
		}
	}

	public void tame(Player owner) {
		this.setTame(true, true);
		//? if >=1.21.5 {
		this.setOwner(owner);
		//?} else {
		/*this.setOwnerUUID(owner.getUUID());
		*///?}

		if (owner instanceof ServerPlayer) {
			FriendsAndFoesCriterias.TAME_GLARE.get().trigger((ServerPlayer) owner, this);
		}
	}

	@Override
	public boolean isFood(ItemStack itemStack) {
		return GlareBrain.getTemptations().test(itemStack);
	}

	@Override
	@Nullable
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob entity) {
		GlareEntity glareEntity = FriendsAndFoesEntityTypes.GLARE.get().create(serverWorld/*? if >=1.21.3 {*/, EntitySpawnReason.BREEDING/*?}*/);

		GlareBrain.setDarkSpotLocatingCooldown(this);
		GlareBrain.setLocatingGlowBerriesCooldown(this);
		GlareBrain.setItemPickupCooldown(this);

		if (this.isTame()) {
			//? if >=1.21.5 {
			glareEntity.setOwner(this.getOwner());
			 //?} else {
			/*glareEntity.setOwnerUUID(this.getOwnerUUID());
			*///?}

			glareEntity.setTame(true, true);

		}

		return glareEntity;
	}

	@Override
	public boolean canMate(Animal other) {
		if (
			other == this
			|| !this.isTame()
			|| !(other instanceof GlareEntity)
		) {
			return false;
		}

		GlareEntity glare = (GlareEntity) other;
		if (
			!glare.isTame()
			|| glare.isInSittingPose()
		) {
			return false;
		}

		return this.isInLove() && glare.isInLove();
	}

	@Override
	/*? if >=1.21.3 {*/
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount)
	/*?} else {*/
	/*public boolean hurt(DamageSource damageSource, float amount)
	*//*?}*/
	{
		//? if <1.21.3 {
		/*var level = this.level();
		*///?}
		if (!level.isClientSide()) {
			this.setOrderedToSit(false);
			this.getNavigation().setSpeedModifier(0);
			this.getNavigation().stop();
		}

		/*? if >=1.21.3 {*/
		return super.hurtServer(level, damageSource, amount);
		/*?} else {*/
		/*return super.hurt(damageSource, amount);
		 *//*?}*/
	}

	@Override
	public float getSpeed() {
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

	final class GlareMoveControl extends FlyingMoveControl
	{
		public GlareMoveControl(GlareEntity glare, int maxPitchChange, boolean noGravity) {
			super(glare, maxPitchChange, noGravity);
		}

		@Override
		public void tick() {
			if (GlareEntity.this.isOrderedToSit()) {
				return;
			}

			super.tick();
		}
	}
}
