package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.animation.AnimationContextTracker;
import com.faboslav.friendsandfoes.entity.ai.goal.*;
import com.faboslav.friendsandfoes.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer.Builder;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class MaulerEntity extends PathAwareEntity implements Angerable, AnimatedEntity
{
	private static final int HEALTH = 20;
	private static final float ANGERED_MOVEMENT_SPEED = 0.5F;
	private static final float MOVEMENT_SPEED = 0.3F;
	private static final float ATTACK_DAMAGE = 8.0F;
	private static final int MAXIMUM_STORED_EXPERIENCE_POINTS = 1395;
	public static final int MIN_TICKS_UNTIL_NEXT_BURROWING = 3000;
	public static final int MAX_TICKS_UNTIL_NEXT_BURROWING = 6000;
	private static final Predicate<Entity> PREY_PREDICATE_FILTER;

	private static final String TYPE_NBT_NAME = "Type";
	private static final String STORED_EXPERIENCE_POINTS_NBT_NAME = "StoredExperiencePoints";
	private static final String IS_BURROWED_DOWN_NBT_NAME = "IsBurrowedDown";
	private static final String TICKS_UNTIL_NEXT_BURROWING_DOWN_NBT_NAME = "TicksUntilNextBurrowingDown";
	private static final String BURROWING_DOWN_ANIMATION_PROGRESS_NBT_NAME = "BurrowingDownAnimationProgress";
	private static final String BURROWED_DOWN_TICKS_NBT_NAME = "BurrowedDownTicks";

	private static final TrackedData<String> TYPE;
	private static final TrackedData<Integer> ANGER_TIME;
	private static final TrackedData<Integer> STORED_EXPERIENCE_POINTS;
	private static final TrackedData<Boolean> IS_MOVING;
	private static final TrackedData<Boolean> IS_BURROWED_DOWN;
	private static final TrackedData<Integer> TICKS_UNTIL_NEXT_BURROWING_DOWN;
	private static final TrackedData<Float> BURROWING_DOWN_ANIMATION_PROGRESS;

	@Environment(EnvType.CLIENT)
	private AnimationContextTracker animationTickTracker;

	@Nullable
	private UUID angryAt;

	public MaulerBurrowDownGoal burrowDownGoal;

	public MaulerEntity(EntityType<? extends MaulerEntity> entityType, World world) {
		super(entityType, world);
	}

	static {
		PREY_PREDICATE_FILTER = (entity) -> {
			if (
				entity instanceof SlimeEntity slimeEntity && slimeEntity.getSize() != SlimeEntity.MIN_SIZE
				|| entity instanceof ZombieEntity && ((ZombieEntity) entity).isBaby() == false
			) {
				return false;
			}

			return entity.getType().isIn(FriendsAndFoesTags.MAULER_PREY);
		};
		TYPE = DataTracker.registerData(MaulerEntity.class, TrackedDataHandlerRegistry.STRING);
		ANGER_TIME = DataTracker.registerData(MaulerEntity.class, TrackedDataHandlerRegistry.INTEGER);
		STORED_EXPERIENCE_POINTS = DataTracker.registerData(MaulerEntity.class, TrackedDataHandlerRegistry.INTEGER);
		IS_MOVING = DataTracker.registerData(MaulerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		IS_BURROWED_DOWN = DataTracker.registerData(MaulerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		TICKS_UNTIL_NEXT_BURROWING_DOWN = DataTracker.registerData(MaulerEntity.class, TrackedDataHandlerRegistry.INTEGER);
		BURROWING_DOWN_ANIMATION_PROGRESS = DataTracker.registerData(MaulerEntity.class, TrackedDataHandlerRegistry.FLOAT);
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(TYPE, Type.DESERT.name());
		this.dataTracker.startTracking(ANGER_TIME, 0);
		this.dataTracker.startTracking(STORED_EXPERIENCE_POINTS, 0);
		this.dataTracker.startTracking(IS_MOVING, false);
		this.dataTracker.startTracking(IS_BURROWED_DOWN, false);
		this.dataTracker.startTracking(TICKS_UNTIL_NEXT_BURROWING_DOWN, RandomGenerator.generateInt(MIN_TICKS_UNTIL_NEXT_BURROWING, MAX_TICKS_UNTIL_NEXT_BURROWING));
		this.dataTracker.startTracking(BURROWING_DOWN_ANIMATION_PROGRESS, 0.0F);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		this.writeAngerToNbt(nbt);
		nbt.putString(TYPE_NBT_NAME, this.getMaulerType().getName());
		nbt.putInt(STORED_EXPERIENCE_POINTS_NBT_NAME, this.getStoredExperiencePoints());
		nbt.putBoolean(IS_BURROWED_DOWN_NBT_NAME, this.isBurrowedDown());
		nbt.putInt(TICKS_UNTIL_NEXT_BURROWING_DOWN_NBT_NAME, this.getTicksUntilNextBurrowingDown());
		nbt.putFloat(BURROWING_DOWN_ANIMATION_PROGRESS_NBT_NAME, this.getBurrowingDownAnimationProgress());

		if (this.isBurrowedDown() && this.burrowDownGoal.getBurrowedDownTicks() > 0) {
			nbt.putInt(BURROWED_DOWN_TICKS_NBT_NAME, this.burrowDownGoal.getBurrowedDownTicks());
		}
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.readAngerFromNbt(this.getWorld(), nbt);
		this.setType(Type.fromName(nbt.getString(TYPE_NBT_NAME)));
		this.setStoredExperiencePoints(nbt.getInt(STORED_EXPERIENCE_POINTS_NBT_NAME));

		this.setBurrowedDown(nbt.getBoolean(IS_BURROWED_DOWN_NBT_NAME));
		this.setTicksUntilNextBurrowingDown(nbt.getInt(TICKS_UNTIL_NEXT_BURROWING_DOWN_NBT_NAME));
		this.setBurrowingDownAnimationProgress(nbt.getFloat(BURROWING_DOWN_ANIMATION_PROGRESS_NBT_NAME));

		if (this.isBurrowedDown() && nbt.contains(BURROWED_DOWN_TICKS_NBT_NAME)) {
			this.burrowDownGoal.setBurrowedDownTicks(nbt.getInt(BURROWED_DOWN_TICKS_NBT_NAME));
			this.setInvulnerable(true);
			this.setInvisible(true);
		}
	}

	@Nullable
	public EntityData initialize(
		ServerWorldAccess world,
		LocalDifficulty difficulty,
		SpawnReason spawnReason,
		@Nullable EntityData entityData,
		@Nullable NbtCompound entityNbt
	) {
		RegistryKey<Biome> biomeKey = world.getBiome(this.getBlockPos()).getKey().orElse(BiomeKeys.SAVANNA);
		Type type = Type.getTypeByBiome(biomeKey);

		this.setPersistent();
		this.setType(type);
		this.setSize(false);

		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	@Override
	public boolean canImmediatelyDespawn(double distanceSquared) {
		return this.hasCustomName() == false;
	}

	public static boolean canSpawn(
		EntityType<MaulerEntity> maulerEntityType,
		ServerWorldAccess serverWorldAccess,
		SpawnReason spawnReason,
		BlockPos blockPos,
		Random random
	) {
		return serverWorldAccess.getBlockState(blockPos.down()).isIn(FriendsAndFoesTags.MAULERS_SPAWNABLE_ON);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(1, new SwimGoal(this));
		this.goalSelector.add(2, new MaulerMeeleAttackGoal(this, ANGERED_MOVEMENT_SPEED, false));
		this.goalSelector.add(3, new MaulerWanderAroundFarGoal(this, 0.6D));
		this.goalSelector.add(4, new MaulerLookAtEntityGoal(this, PlayerEntity.class, 10.0F));
		this.goalSelector.add(5, new MaulerLookAroundGoal(this));
		this.burrowDownGoal = new MaulerBurrowDownGoal(this);
		this.goalSelector.add(6, this.burrowDownGoal);
		this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge());
		this.targetSelector.add(2, new ActiveTargetGoal(this, PathAwareEntity.class, 10, true, true, PREY_PREDICATE_FILTER));
	}

	@Override
	public void tick() {
		if (FriendsAndFoes.getConfig().enableMauler == false) {
			this.discard();
		}

		super.tick();

		if (this.getWorld().isClient() == true) {
			return;
		}

		if (this.getTicksUntilNextBurrowingDown() > 0) {
			this.setTicksUntilNextBurrowingDown(this.getTicksUntilNextBurrowingDown() - 1);
		}

		this.updateBurrowingDownAnimation();
	}

	@Override
	public void tickMovement() {
		if (this.getWorld().isClient() == false && this.getBurrowingDownAnimationProgress() > 0.0F) {
			this.getNavigation().setSpeed(0);
			this.getNavigation().stop();
		}

		super.tickMovement();

		if (this.getWorld().isClient() == true) {
			return;
		}

		this.tickAngerLogic((ServerWorld) this.getWorld(), true);
		this.setMoving(this.getNavigation().isFollowingPath());
	}

	@Override
	public boolean damage(
		DamageSource source,
		float amount
	) {
		if (this.getWorld().isClient() == false && this.burrowDownGoal.isRunning()) {
			this.burrowDownGoal.stop();
		}

		return super.damage(source, amount);
	}

	@Override
	public float getMovementSpeed() {
		return this.hasAngerTime() ? ANGERED_MOVEMENT_SPEED:MOVEMENT_SPEED;
	}

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item itemInHand = itemStack.getItem();
		boolean interactionResult = false;

		if (
			this.hasAngerTime() == false
			&& (
				itemStack.hasEnchantments()
				|| itemInHand == Items.ENCHANTED_BOOK
			)
		) {
			interactionResult = this.tryToInteractWithEnhancedItem(itemStack);
		} else if (this.hasAngerTime() == false && itemInHand == Items.GLASS_BOTTLE) {
			interactionResult = this.tryToInteractWithGlassBottle(player, itemStack);
		}

		if (interactionResult) {
			this.emitGameEvent(GameEvent.ENTITY_INTERACT, this);
			return ActionResult.success(this.getWorld().isClient());
		}

		return super.interactMob(player, hand);
	}

	private boolean tryToInteractWithEnhancedItem(ItemStack itemStack) {
		int storedExperiencePoints = this.getStoredExperiencePoints();

		if (storedExperiencePoints >= MAXIMUM_STORED_EXPERIENCE_POINTS) {
			return false;
		}

		int experiencePoints = this.getExperiencePoints(itemStack);
		int recalculatedExperiencePoints = storedExperiencePoints + experiencePoints;

		if (recalculatedExperiencePoints > MAXIMUM_STORED_EXPERIENCE_POINTS) {
			recalculatedExperiencePoints = MAXIMUM_STORED_EXPERIENCE_POINTS;
		}

		this.setStoredExperiencePoints(recalculatedExperiencePoints);

		itemStack.decrement(1);

		this.playSound(FriendsAndFoesSoundEvents.ENTITY_MAULER_BITE.get(), 0.2F, RandomGenerator.generateFloat(0.9F, 0.95F));
		this.spawnParticles(ParticleTypes.ENCHANT, 7);

		return true;
	}

	private boolean tryToInteractWithGlassBottle(
		PlayerEntity player,
		ItemStack itemStack
	) {

		int storedExperiencePoints = this.getStoredExperiencePoints();

		if (storedExperiencePoints < 7) {
			return false;
		}

		int glassBottlesCount = itemStack.getCount();
		int experienceBottleCount = storedExperiencePoints / 7;

		if (experienceBottleCount > glassBottlesCount) {
			experienceBottleCount = glassBottlesCount;
		}

		itemStack.decrement(experienceBottleCount);
		ItemStack experienceBottleItemStack = new ItemStack(Items.EXPERIENCE_BOTTLE, experienceBottleCount);
		player.giveItemStack(experienceBottleItemStack);

		this.setStoredExperiencePoints(storedExperiencePoints - experienceBottleCount * 7);
		this.playSound(SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, 1.0F, 1.0F);

		return true;
	}

	public boolean shouldSpawnSprintingParticles() {
		return false;
	}

	public static Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, HEALTH)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED)
			.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, ATTACK_DAMAGE);
	}

	@Override
	public boolean isPushable() {
		return this.isBurrowedDown() == false && super.isPushable();
	}

	@Override
	public void pushAway(Entity entity) {
		if (this.isBurrowedDown()) {
			return;
		}

		super.pushAway(entity);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return FriendsAndFoesSoundEvents.ENTITY_MAULER_GROWL.get();
	}

	@Override
	public void playAmbientSound() {
		if ((this.hasAngerTime() && this.isMoving()) || this.isBurrowedDown()) {
			return;
		}

		this.playSound(this.getAmbientSound(), 0.5F, RandomGenerator.generateFloat(0.75F, 0.85F));
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return FriendsAndFoesSoundEvents.ENTITY_MAULER_HURT.get();
	}

	@Override
	public void playHurtSound(DamageSource source) {
		this.ambientSoundChance = -this.getMinAmbientSoundDelay();
		this.playSound(this.getHurtSound(source), 0.5F, RandomGenerator.generateFloat(0.85F, 0.95F));
	}

	@Override
	protected SoundEvent getDeathSound() {
		return FriendsAndFoesSoundEvents.ENTITY_MAULER_DEATH.get();
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState state) {
		super.playStepSound(pos, state);

		if (this.hasAngerTime() && this.isMoving() && this.isOnGround() && this.getVelocity().getY() <= 0.0001) {
			this.playSound(FriendsAndFoesSoundEvents.ENTITY_MAULER_BITE.get(), 0.2F, RandomGenerator.generateFloat(0.9F, 0.95F));
		}
	}

	@Override
	public boolean tryAttack(Entity target) {
		if (this.isBurrowedDown()) {
			return false;
		}

		return target.damage(DamageSource.mob(this), (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
	}

	public int getAngerTime() {
		return this.dataTracker.get(ANGER_TIME);
	}

	public void setAngerTime(int angerTime) {
		this.dataTracker.set(ANGER_TIME, angerTime);
	}

	public void chooseRandomAngerTime() {
		int angerTime = RandomGenerator.generateInt(400, 1000);
		this.setAngerTime(angerTime);
	}

	@Nullable
	public UUID getAngryAt() {
		return this.angryAt;
	}

	public void setAngryAt(@Nullable UUID angryAt) {
		this.angryAt = angryAt;
	}

	public Type getMaulerType() {
		return MaulerEntity.Type.fromName(this.dataTracker.get(TYPE));
	}

	private void setType(MaulerEntity.Type type) {
		this.dataTracker.set(TYPE, type.name);
	}

	public int getStoredExperiencePoints() {
		return this.dataTracker.get(STORED_EXPERIENCE_POINTS);
	}

	public void setStoredExperiencePoints(int storedExperiencePoints) {
		this.dataTracker.set(STORED_EXPERIENCE_POINTS, storedExperiencePoints);
		this.experiencePoints = storedExperiencePoints;
		this.setSize(false);
	}

	public void setSize(boolean heal) {
		float size = this.getSize();

		this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue((int) (HEALTH * size));
		this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(ATTACK_DAMAGE * (size / 2.0F));
		this.calculateDimensions();
		this.calculateBoundingBox();

		if (heal) {
			this.setHealth(this.getMaxHealth());
		}
	}

	public float getSize() {
		return 1.0F + ((float) this.getStoredExperiencePoints() / (float) MAXIMUM_STORED_EXPERIENCE_POINTS) * 0.5F;
	}

	public boolean isMoving() {
		return this.dataTracker.get(IS_MOVING);
	}

	public void setMoving(boolean isMoving) {
		this.dataTracker.set(IS_MOVING, isMoving);
	}

	public boolean isBurrowedDown() {
		return this.dataTracker.get(IS_BURROWED_DOWN);
	}

	public void setBurrowedDown(boolean isBurrowedDown) {
		this.dataTracker.set(IS_BURROWED_DOWN, isBurrowedDown);
	}

	public int getTicksUntilNextBurrowingDown() {
		return this.dataTracker.get(TICKS_UNTIL_NEXT_BURROWING_DOWN);
	}

	public void setTicksUntilNextBurrowingDown(int ticksUntilNextBurrowingDown) {
		this.dataTracker.set(TICKS_UNTIL_NEXT_BURROWING_DOWN, ticksUntilNextBurrowingDown);
	}

	public float getBurrowingDownAnimationProgress() {
		return this.dataTracker.get(BURROWING_DOWN_ANIMATION_PROGRESS);
	}

	public void setBurrowingDownAnimationProgress(float burrowingDownAnimationProgress) {
		this.dataTracker.set(BURROWING_DOWN_ANIMATION_PROGRESS, burrowingDownAnimationProgress);
	}

	private void updateBurrowingDownAnimation() {
		if (this.isBurrowedDown()) {
			this.setBurrowingDownAnimationProgress(Math.min(1.0F, this.getBurrowingDownAnimationProgress() + 0.04F));
		} else {
			this.setBurrowingDownAnimationProgress(Math.max(0.0F, this.getBurrowingDownAnimationProgress() - 0.04F));
		}
	}

	@Override
	public EntityDimensions getDimensions(EntityPose pose) {
		return super.getDimensions(pose).scaled(this.getSize());
	}

	public Vec3d getLeashOffset() {
		return new Vec3d(0.0D, 0.6F * this.getStandingEyeHeight(), this.getWidth() * 0.4F);
	}

	private int getExperiencePoints(ItemStack stack) {
		int experiencePoints = 0;
		Map<Enchantment, Integer> mappedEnchantments = EnchantmentHelper.get(stack);

		for (Map.Entry<Enchantment, Integer> enchantmentItem : mappedEnchantments.entrySet()) {
			Enchantment enchantment = enchantmentItem.getKey();
			Integer enchantmentExperiencePoints = enchantmentItem.getValue();

			if (enchantmentItem.getKey().isCursed()) {
				continue;
			}

			experiencePoints += enchantment.getMinPower(enchantmentExperiencePoints);
		}

		return experiencePoints;
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
				1.0D
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

	public enum Type
	{
		BADLANDS("badlands"),
		DESERT("desert"),
		SAVANNA("savanna");

		private final String name;

		Type(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		private static MaulerEntity.Type fromName(String name) {
			MaulerEntity.Type[] types = values();

			for (Type type : types) {
				if (type.name.equals(name)) {
					return type;
				}
			}

			return SAVANNA;
		}

		public static Type getTypeByBiome(RegistryKey<Biome> biome) {
			if (biome == BiomeKeys.DESERT) {
				return DESERT;
			} else if (
				biome == BiomeKeys.BADLANDS
				|| biome == BiomeKeys.ERODED_BADLANDS
				|| biome == BiomeKeys.WOODED_BADLANDS
			) {
				return BADLANDS;
			}

			return SAVANNA;
		}
	}
}
