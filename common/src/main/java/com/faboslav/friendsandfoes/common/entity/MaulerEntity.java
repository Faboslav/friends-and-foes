package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.ai.goal.mauler.*;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.util.RandomGenerator;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.function.Predicate;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class MaulerEntity extends PathfinderMob implements NeutralMob, AnimatedEntity
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

	private static final EntityDataAccessor<String> TYPE;
	private static final EntityDataAccessor<Integer> ANGER_TIME;
	private static final EntityDataAccessor<Integer> STORED_EXPERIENCE_POINTS;
	private static final EntityDataAccessor<Boolean> IS_MOVING;
	private static final EntityDataAccessor<Boolean> IS_BURROWED_DOWN;
	private static final EntityDataAccessor<Integer> TICKS_UNTIL_NEXT_BURROWING_DOWN;
	private static final EntityDataAccessor<Float> BURROWING_DOWN_ANIMATION_PROGRESS;

	@Nullable
	private UUID angryAt;

	public MaulerBurrowDownGoal burrowDownGoal;

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

	public MaulerEntity(EntityType<? extends MaulerEntity> entityType, Level world) {
		super(entityType, world);
	}

	static {
		PREY_PREDICATE_FILTER = (entity) -> {
			if (
				entity instanceof Slime slimeEntity && slimeEntity.getSize() != Slime.MIN_SIZE
				|| entity instanceof Zombie && !((Zombie) entity).isBaby()
			) {
				return false;
			}

			return entity.getType().is(FriendsAndFoesTags.MAULER_PREY);
		};
		TYPE = SynchedEntityData.defineId(MaulerEntity.class, EntityDataSerializers.STRING);
		ANGER_TIME = SynchedEntityData.defineId(MaulerEntity.class, EntityDataSerializers.INT);
		STORED_EXPERIENCE_POINTS = SynchedEntityData.defineId(MaulerEntity.class, EntityDataSerializers.INT);
		IS_MOVING = SynchedEntityData.defineId(MaulerEntity.class, EntityDataSerializers.BOOLEAN);
		IS_BURROWED_DOWN = SynchedEntityData.defineId(MaulerEntity.class, EntityDataSerializers.BOOLEAN);
		TICKS_UNTIL_NEXT_BURROWING_DOWN = SynchedEntityData.defineId(MaulerEntity.class, EntityDataSerializers.INT);
		BURROWING_DOWN_ANIMATION_PROGRESS = SynchedEntityData.defineId(MaulerEntity.class, EntityDataSerializers.FLOAT);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);

		builder.define(TYPE, Type.DESERT.name());
		builder.define(ANGER_TIME, 0);
		builder.define(STORED_EXPERIENCE_POINTS, 0);
		builder.define(IS_MOVING, false);
		builder.define(IS_BURROWED_DOWN, false);
		builder.define(TICKS_UNTIL_NEXT_BURROWING_DOWN, this.getRandom().nextIntBetweenInclusive(MIN_TICKS_UNTIL_NEXT_BURROWING, MAX_TICKS_UNTIL_NEXT_BURROWING));
		builder.define(BURROWING_DOWN_ANIMATION_PROGRESS, 0.0F);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);
		this.addPersistentAngerSaveData(nbt);
		nbt.putString(TYPE_NBT_NAME, this.getMaulerType().getName());
		nbt.putInt(STORED_EXPERIENCE_POINTS_NBT_NAME, this.getStoredExperiencePoints());
		nbt.putBoolean(IS_BURROWED_DOWN_NBT_NAME, this.isBurrowedDown());
		nbt.putInt(TICKS_UNTIL_NEXT_BURROWING_DOWN_NBT_NAME, this.getTicksUntilNextBurrowingDown());
		nbt.putFloat(BURROWING_DOWN_ANIMATION_PROGRESS_NBT_NAME, this.getBurrowingDownAnimationProgress());

		if (this.burrowDownGoal != null && this.isBurrowedDown() && this.burrowDownGoal.getBurrowedDownTicks() > 0) {
			nbt.putInt(BURROWED_DOWN_TICKS_NBT_NAME, this.burrowDownGoal.getBurrowedDownTicks());
		}
	}

	public void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);
		this.readPersistentAngerSaveData(this.level(), nbt);
		this.setType(Type.fromName(nbt.getString(TYPE_NBT_NAME)));
		this.setStoredExperiencePoints(nbt.getInt(STORED_EXPERIENCE_POINTS_NBT_NAME));

		this.setBurrowedDown(nbt.getBoolean(IS_BURROWED_DOWN_NBT_NAME));
		this.setTicksUntilNextBurrowingDown(nbt.getInt(TICKS_UNTIL_NEXT_BURROWING_DOWN_NBT_NAME));
		this.setBurrowingDownAnimationProgress(nbt.getFloat(BURROWING_DOWN_ANIMATION_PROGRESS_NBT_NAME));

		if (this.burrowDownGoal != null && this.isBurrowedDown() && nbt.contains(BURROWED_DOWN_TICKS_NBT_NAME)) {
			this.burrowDownGoal.setBurrowedDownTicks(nbt.getInt(BURROWED_DOWN_TICKS_NBT_NAME));
			this.setInvulnerable(true);
			this.setInvisible(true);
		}
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(
		ServerLevelAccessor world,
		DifficultyInstance difficulty,
		MobSpawnType spawnReason,
		@Nullable SpawnGroupData entityData
	) {
		ResourceKey<Biome> biomeKey = world.getBiome(this.blockPosition()).unwrapKey().orElse(Biomes.SAVANNA);
		Type type = Type.getTypeByBiome(biomeKey);

		this.setPersistenceRequired();
		this.setType(type);
		this.setSize();

		return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
	}

	@Override
	public boolean removeWhenFarAway(double distanceSquared) {
		return !this.hasCustomName();
	}

	public static boolean canSpawn(
		EntityType<MaulerEntity> maulerEntityType,
		ServerLevelAccessor serverWorldAccess,
		MobSpawnType spawnReason,
		BlockPos blockPos,
		RandomSource random
	) {
		return serverWorldAccess.getBlockState(blockPos.below()).is(FriendsAndFoesTags.MAULERS_SPAWNABLE_ON);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new MaulerMeeleAttackGoal(this, ANGERED_MOVEMENT_SPEED, false));
		this.goalSelector.addGoal(3, new MaulerWanderAroundFarGoal(this, 0.6D));
		this.goalSelector.addGoal(4, new MaulerLookAtEntityGoal(this, Player.class, 10.0F));
		this.goalSelector.addGoal(5, new MaulerLookAroundGoal(this));
		this.burrowDownGoal = new MaulerBurrowDownGoal(this);
		this.goalSelector.addGoal(6, this.burrowDownGoal);
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, PathfinderMob.class, 10, true, true, PREY_PREDICATE_FILTER));
	}

	@Override
	public void tick() {
		if (!FriendsAndFoes.getConfig().enableMauler) {
			this.discard();
		}

		this.setSize();

		super.tick();

		if (this.level().isClientSide()) {
			return;
		}

		if (this.getTicksUntilNextBurrowingDown() > 0) {
			this.setTicksUntilNextBurrowingDown(this.getTicksUntilNextBurrowingDown() - 1);
		}

		this.updateBurrowingDownAnimation();
	}

	@Override
	public void aiStep() {
		if (!this.level().isClientSide() && this.getBurrowingDownAnimationProgress() > 0.0F) {
			this.getNavigation().setSpeedModifier(0);
			this.getNavigation().stop();
		}

		super.aiStep();

		if (this.level().isClientSide()) {
			return;
		}

		this.updatePersistentAnger((ServerLevel) this.level(), true);
		this.setMoving(this.getNavigation().isInProgress());
	}

	@Override
	public boolean hurt(
		DamageSource source,
		float amount
	) {
		if (!this.level().isClientSide() && this.burrowDownGoal.isRunning()) {
			this.burrowDownGoal.stop();
		}

		return super.hurt(source, amount);
	}

	@Override
	public float getSpeed() {
		return this.isAngry() ? ANGERED_MOVEMENT_SPEED:MOVEMENT_SPEED;
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		Item itemInHand = itemStack.getItem();

		boolean interactionResult = false;

		if (
			!this.isAngry()
			&& (
				(
					itemStack.isEnchanted()
					|| itemInHand == Items.ENCHANTED_BOOK
				)
			)
		) {
			interactionResult = this.tryToInteractWithEnhancedItem(player, hand, itemStack);
		} else if (!this.isAngry() && itemInHand == Items.GLASS_BOTTLE) {
			interactionResult = this.tryToInteractWithGlassBottle(player, itemStack);
		}

		if (interactionResult) {
			this.gameEvent(GameEvent.ENTITY_INTERACT, this);
			return InteractionResult.sidedSuccess(this.level().isClientSide());
		}

		return super.mobInteract(player, hand);
	}

	private boolean tryToInteractWithEnhancedItem(Player player, InteractionHand hand, ItemStack itemStack) {
		int storedExperiencePoints = this.getStoredExperiencePoints();

		if (storedExperiencePoints >= MAXIMUM_STORED_EXPERIENCE_POINTS) {
			return false;
		}

		if (this.level().isClientSide()) {
			return true;
		}

		int experiencePoints = this.getExperiencePoints(itemStack);
		int recalculatedExperiencePoints = storedExperiencePoints + experiencePoints;

		if (recalculatedExperiencePoints > MAXIMUM_STORED_EXPERIENCE_POINTS) {
			recalculatedExperiencePoints = MAXIMUM_STORED_EXPERIENCE_POINTS;
		}

		this.setStoredExperiencePoints(recalculatedExperiencePoints);

		if (!player.getAbilities().instabuild) {
			if (itemStack.isStackable()) {
				itemStack.shrink(1);
			} else {
				EquipmentSlot equipmentSlot;

				if (hand == InteractionHand.MAIN_HAND) {
					equipmentSlot = EquipmentSlot.MAINHAND;
				} else {
					equipmentSlot = EquipmentSlot.OFFHAND;
				}

				player.setItemSlot(equipmentSlot, ItemStack.EMPTY);
			}
		}

		this.playSound(FriendsAndFoesSoundEvents.ENTITY_MAULER_BITE.get(), 0.2F, RandomGenerator.generateFloat(0.9F, 0.95F));
		this.spawnParticles(ParticleTypes.ENCHANT, 7);

		return true;
	}

	private boolean tryToInteractWithGlassBottle(
		Player player,
		ItemStack itemStack
	) {

		int storedExperiencePoints = this.getStoredExperiencePoints();

		if (storedExperiencePoints < 7) {
			return false;
		}

		if (this.level().isClientSide()) {
			return true;
		}

		int glassBottlesCount = itemStack.getCount();
		int experienceBottleCount = storedExperiencePoints / 7;

		if (experienceBottleCount > glassBottlesCount) {
			experienceBottleCount = glassBottlesCount;
		}

		itemStack.shrink(experienceBottleCount);
		ItemStack experienceBottleItemStack = new ItemStack(Items.EXPERIENCE_BOTTLE, experienceBottleCount);
		player.addItem(experienceBottleItemStack);

		this.setStoredExperiencePoints(storedExperiencePoints - experienceBottleCount * 7);
		this.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 1.0F, 1.0F);

		return true;
	}

	public boolean canSpawnSprintParticle() {
		return false;
	}

	public static Builder createMaulerAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.SCALE, 1.0F)
			.add(Attributes.MAX_HEALTH, HEALTH)
			.add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
			.add(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE);
	}

	@Override
	public boolean isPushable() {
		return !this.isBurrowedDown() && super.isPushable();
	}

	@Override
	public void doPush(Entity entity) {
		if (this.isBurrowedDown()) {
			return;
		}

		super.doPush(entity);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return FriendsAndFoesSoundEvents.ENTITY_MAULER_GROWL.get();
	}

	@Override
	public void playAmbientSound() {
		if ((this.isAngry() && this.isMoving()) || this.isBurrowedDown()) {
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
		this.ambientSoundTime = -this.getAmbientSoundInterval();
		this.playSound(this.getHurtSound(source), 0.5F, RandomGenerator.generateFloat(0.85F, 0.95F));
	}

	@Override
	protected SoundEvent getDeathSound() {
		return FriendsAndFoesSoundEvents.ENTITY_MAULER_DEATH.get();
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState state) {
		super.playStepSound(pos, state);

		if (this.isAngry() && this.isMoving() && this.onGround() && this.getDeltaMovement().y() <= 0.0001) {
			this.playSound(FriendsAndFoesSoundEvents.ENTITY_MAULER_BITE.get(), 0.2F, RandomGenerator.generateFloat(0.9F, 0.95F));
		}
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		if (this.isBurrowedDown()) {
			return false;
		}

		return target.hurt(this.damageSources().mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
	}

	public int getRemainingPersistentAngerTime() {
		return this.entityData.get(ANGER_TIME);
	}

	public void setRemainingPersistentAngerTime(int angerTime) {
		this.entityData.set(ANGER_TIME, angerTime);
	}

	public void startPersistentAngerTimer() {
		int angerTime = this.getRandom().nextIntBetweenInclusive(400, 1000);
		this.setRemainingPersistentAngerTime(angerTime);
	}

	@Nullable
	public UUID getPersistentAngerTarget() {
		return this.angryAt;
	}

	public void setPersistentAngerTarget(@Nullable UUID angryAt) {
		this.angryAt = angryAt;
	}

	public Type getMaulerType() {
		return MaulerEntity.Type.fromName(this.entityData.get(TYPE));
	}

	private void setType(MaulerEntity.Type type) {
		this.entityData.set(TYPE, type.name);
	}

	public int getStoredExperiencePoints() {
		return this.entityData.get(STORED_EXPERIENCE_POINTS);
	}

	public void setStoredExperiencePoints(int storedExperiencePoints) {
		this.entityData.set(STORED_EXPERIENCE_POINTS, storedExperiencePoints);
		this.xpReward = storedExperiencePoints;
	}

	public void setSize() {
		float size = this.getSize();

		this.getAttribute(Attributes.SCALE).setBaseValue(this.getSize());
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((int) (HEALTH * size));
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(ATTACK_DAMAGE * (size / 2.0F));
		this.refreshDimensions();
		this.makeBoundingBox();
	}

	public float getSize() {
		return 1.0F + ((float) this.getStoredExperiencePoints() / (float) MAXIMUM_STORED_EXPERIENCE_POINTS) * 1.25F;
	}

	public boolean isMoving() {
		return this.entityData.get(IS_MOVING);
	}

	public void setMoving(boolean isMoving) {
		this.entityData.set(IS_MOVING, isMoving);
	}

	public boolean isBurrowedDown() {
		return this.entityData.get(IS_BURROWED_DOWN);
	}

	public void setBurrowedDown(boolean isBurrowedDown) {
		this.entityData.set(IS_BURROWED_DOWN, isBurrowedDown);
	}

	public int getTicksUntilNextBurrowingDown() {
		return this.entityData.get(TICKS_UNTIL_NEXT_BURROWING_DOWN);
	}

	public void setTicksUntilNextBurrowingDown(int ticksUntilNextBurrowingDown) {
		this.entityData.set(TICKS_UNTIL_NEXT_BURROWING_DOWN, ticksUntilNextBurrowingDown);
	}

	public float getBurrowingDownAnimationProgress() {
		return this.entityData.get(BURROWING_DOWN_ANIMATION_PROGRESS);
	}

	public void setBurrowingDownAnimationProgress(float burrowingDownAnimationProgress) {
		this.entityData.set(BURROWING_DOWN_ANIMATION_PROGRESS, burrowingDownAnimationProgress);
	}

	private void updateBurrowingDownAnimation() {
		if (this.isBurrowedDown()) {
			this.setBurrowingDownAnimationProgress(Math.min(1.0F, this.getBurrowingDownAnimationProgress() + 0.04F));
		} else {
			this.setBurrowingDownAnimationProgress(Math.max(0.0F, this.getBurrowingDownAnimationProgress() - 0.04F));
		}
	}

	public Vec3 getLeashOffset() {
		return new Vec3(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
	}

	/**
	 * @see net.minecraft.world.inventory.GrindstoneMenu
	 */
	private int getExperiencePoints(ItemStack stack) {
		int i = 0;
		ItemEnchantments itemEnchantmentsComponent = EnchantmentHelper.getEnchantmentsForCrafting(stack);
		Iterator var4 = itemEnchantmentsComponent.entrySet().iterator();

		while (var4.hasNext()) {
			Object2IntMap.Entry<Holder<Enchantment>> entry = (Object2IntMap.Entry) var4.next();
			Holder<Enchantment> registryEntry = entry.getKey();
			int j = entry.getIntValue();
			if (!registryEntry.is(EnchantmentTags.CURSE)) {
				i += registryEntry.value().getMinCost(j);
			}
		}

		return i;
	}

	public void spawnParticles(
		ParticleOptions particleEffect,
		int amount
	) {
		Level world = this.level();

		if (world.isClientSide()) {
			return;
		}

		for (int i = 0; i < amount; i++) {
			((ServerLevel) world).sendParticles(
				particleEffect,
				this.getRandomX(1.0D),
				this.getRandomY() + 0.5D,
				this.getRandomZ(1.0D),
				1,
				this.getRandom().nextGaussian() * 0.02D,
				this.getRandom().nextGaussian() * 0.02D,
				this.getRandom().nextGaussian() * 0.02D,
				1.0D
			);
		}
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

		public static Type getTypeByBiome(ResourceKey<Biome> biome) {
			if (biome == Biomes.DESERT) {
				return DESERT;
			} else if (
				biome == Biomes.BADLANDS
				|| biome == Biomes.ERODED_BADLANDS
				|| biome == Biomes.WOODED_BADLANDS
			) {
				return BADLANDS;
			}

			return SAVANNA;
		}
	}
}
