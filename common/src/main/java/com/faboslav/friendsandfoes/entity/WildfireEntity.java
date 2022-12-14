package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.ai.brain.WildfireBrain;
import com.faboslav.friendsandfoes.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.tag.FriendsAndFoesTags;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public final class WildfireEntity extends HostileEntity
{
	private float damageAmountCounter = 0.0F;

	public static final float GENERIC_ATTACK_DAMAGE = 8.0F;
	public static final float GENERIC_FOLLOW_RANGE = 32.0F;

	public static final int DEFAULT_ACTIVE_SHIELDS_COUNT = 4;
	public static final int DEFAULT_TICKS_UNTIL_SHIELD_REGENERATION = 300;
	public static final int DEFAULT_SUMMONED_BLAZES_COUNT = 0;

	public static final int MAXIMUM_SUMMONED_BLAZES_COUNT = 2;

	private static final String ACTIVE_SHIELDS_NBT_NAME = "ActiveShieldsCount";
	private static final String TICKS_UNTIL_SHIELD_REGENERATION_NBT_NAME = "TicksUntilShieldRegeneration";
	private static final String SUMMONED_BLAZES_COUNT_NBT_NAME = "SummonedBlazesCount";

	private static final TrackedData<Integer> ACTIVE_SHIELDS_COUNT;
	private static final TrackedData<Integer> TICKS_UNTIL_SHIELD_REGENERATION;
	private static final TrackedData<Integer> SUMMONED_BLAZES_COUNT;

	public WildfireEntity(EntityType<? extends WildfireEntity> entityType, World world) {
		super(entityType, world);
		this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.experiencePoints = 10;
	}

	@Nullable
	public EntityData initialize(
		ServerWorldAccess world,
		LocalDifficulty difficulty,
		SpawnReason spawnReason,
		@Nullable EntityData entityData,
		@Nullable NbtCompound entityNbt
	) {
		this.setActiveShieldsCount(DEFAULT_ACTIVE_SHIELDS_COUNT);
		this.setSummonedBlazesCount(DEFAULT_SUMMONED_BLAZES_COUNT);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
		return WildfireBrain.create(dynamic);
	}

	@Override
	@SuppressWarnings("all")
	public Brain<WildfireEntity> getBrain() {
		return (Brain<WildfireEntity>) super.getBrain();
	}

	@Override
	protected void mobTick() {
		this.getWorld().getProfiler().push("wildfireBrain");
		this.getBrain().tick((ServerWorld) this.getWorld(), this);
		this.getWorld().getProfiler().pop();
		this.getWorld().getProfiler().push("wildfireActivityUpdate");
		WildfireBrain.updateActivities(this);
		this.getWorld().getProfiler().pop();

		super.mobTick();
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return HostileEntity.createHostileAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 120.0F)
			.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0F)
			.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 32.0F)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23000000417232513)
			.add(EntityAttributes.GENERIC_FOLLOW_RANGE, GENERIC_FOLLOW_RANGE)
			.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0F);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(ACTIVE_SHIELDS_COUNT, DEFAULT_ACTIVE_SHIELDS_COUNT);
		this.dataTracker.startTracking(TICKS_UNTIL_SHIELD_REGENERATION, DEFAULT_TICKS_UNTIL_SHIELD_REGENERATION);
		this.dataTracker.startTracking(SUMMONED_BLAZES_COUNT, DEFAULT_SUMMONED_BLAZES_COUNT);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt(ACTIVE_SHIELDS_NBT_NAME, this.getActiveShieldsCount());
		nbt.putInt(TICKS_UNTIL_SHIELD_REGENERATION_NBT_NAME, this.getTicksUntilShieldRegeneration());
		nbt.putInt(SUMMONED_BLAZES_COUNT_NBT_NAME, this.getSummonedBlazesCount());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.setActiveShieldsCount(nbt.getInt(ACTIVE_SHIELDS_NBT_NAME));
		this.setTicksUntilShieldRegeneration(nbt.getInt(TICKS_UNTIL_SHIELD_REGENERATION_NBT_NAME));
		this.setSummonedBlazesCount(nbt.getInt(SUMMONED_BLAZES_COUNT_NBT_NAME));
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		if (state.getMaterial().isLiquid()) {
			return;
		}

		BlockState blockState = this.getWorld().getBlockState(pos.up());
		BlockSoundGroup blockSoundGroup = blockState.isIn(BlockTags.INSIDE_STEP_SOUND_BLOCKS) ? blockState.getSoundGroup():state.getSoundGroup();
		this.playSound(FriendsAndFoesSoundEvents.ENTITY_WILDFIRE_STEP.get(), blockSoundGroup.getVolume() * 0.15F, blockSoundGroup.getPitch());
	}

	public SoundEvent getShootSound() {
		return FriendsAndFoesSoundEvents.ENTITY_WILDFIRE_SHOOT.get();
	}

	public void playShootSound() {
		this.playSound(this.getShootSound(), this.getSoundVolume(), this.getSoundPitch());
	}

	public SoundEvent getShockwaveSound() {
		return FriendsAndFoesSoundEvents.ENTITY_WILDFIRE_SHOCKWAVE.get();
	}

	public void playShockwaveSound() {
		this.playSound(this.getShockwaveSound(), this.getSoundVolume(), this.getSoundPitch());
	}

	public void breakShield() {
		this.setActiveShieldsCount(this.getActiveShieldsCount() - 1);
	}

	public void regenerateShield() {
		if (this.getActiveShieldsCount() >= DEFAULT_ACTIVE_SHIELDS_COUNT) {
			return;
		}

		this.setActiveShieldsCount(this.getActiveShieldsCount() + 1);
	}

	public int getActiveShieldsCount() {
		return this.dataTracker.get(ACTIVE_SHIELDS_COUNT);
	}

	public void setActiveShieldsCount(int activeShields) {
		this.dataTracker.set(ACTIVE_SHIELDS_COUNT, activeShields);
	}

	public boolean hasActiveShields() {
		return this.getActiveShieldsCount() > 0;
	}

	public int getTicksUntilShieldRegeneration() {
		return this.dataTracker.get(TICKS_UNTIL_SHIELD_REGENERATION);
	}

	public void setTicksUntilShieldRegeneration(int ticksUntilShieldRegeneration) {
		this.dataTracker.set(TICKS_UNTIL_SHIELD_REGENERATION, ticksUntilShieldRegeneration);
	}

	public void resetTicksUntilShieldRegeneration() {
		this.setTicksUntilShieldRegeneration(DEFAULT_TICKS_UNTIL_SHIELD_REGENERATION);
	}

	public int getSummonedBlazesCount() {
		return this.dataTracker.get(SUMMONED_BLAZES_COUNT);
	}

	public void setSummonedBlazesCount(int summonedBlazesCount) {
		this.dataTracker.set(SUMMONED_BLAZES_COUNT, summonedBlazesCount);
	}

	public boolean areBlazesSummoned() {
		return this.getSummonedBlazesCount() > 0;
	}

	public SoundEvent getShieldBreakSound() {
		return FriendsAndFoesSoundEvents.ENTITY_WILDFIRE_SHIELD_BREAK.get();
	}

	public void playShieldBreakSound() {
		this.playSound(this.getShieldBreakSound(), this.getSoundVolume(), this.getSoundPitch());
	}

	protected SoundEvent getAmbientSound() {
		return FriendsAndFoesSoundEvents.ENTITY_WILDFIRE_AMBIENT.get();
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return FriendsAndFoesSoundEvents.ENTITY_WILDFIRE_HURT.get();
	}

	protected SoundEvent getDeathSound() {
		return FriendsAndFoesSoundEvents.ENTITY_WILDFIRE_DEATH.get();
	}

	public SoundEvent getSummonBlazeSound() {
		return FriendsAndFoesSoundEvents.ENTITY_WILDFIRE_SUMMON_BLAZE.get();
	}

	public void playSummonBlazeSound() {
		this.playSound(this.getSummonBlazeSound(), this.getSoundVolume(), this.getSoundPitch());
	}

	public void tick() {
		if (FriendsAndFoes.getConfig().enableWildfire == false) {
			this.discard();
		}

		super.tick();

		this.setTicksUntilShieldRegeneration(this.getTicksUntilShieldRegeneration() - 1);

		if (this.getTicksUntilShieldRegeneration() == 0) {
			this.regenerateShield();
			this.resetTicksUntilShieldRegeneration();
		}
	}

	public void tickMovement() {
		if (this.isOnGround() == false && this.getVelocity().y < 0.0F) {
			this.setVelocity(this.getVelocity().multiply(1.0F, 0.6F, 1.0F));
		}

		super.tickMovement();
	}

	public boolean damage(
		DamageSource source,
		float amount
	) {
		if (
			source == DamageSource.IN_FIRE
			|| (
				source.getAttacker() != null
				&& source.getAttacker().getType().isIn(FriendsAndFoesTags.WILDFIRE_ALLIES)
			)
		) {
			return false;
		}

		if (this.hasActiveShields() && source.getAttacker() != null) {
			this.damageAmountCounter += amount;
			float shieldBreakDamageThreshold = (float) this.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH) * 0.25F;

			if (this.damageAmountCounter >= shieldBreakDamageThreshold) {
				source.getAttacker().damage(DamageSource.mob(this), GENERIC_ATTACK_DAMAGE);
				this.breakShield();
				this.playShieldBreakSound();
				this.damageAmountCounter = 0;
			}

			amount = 0.0F;
		}

		this.resetTicksUntilShieldRegeneration();

		boolean damageResult = super.damage(source, amount);

		if (damageResult && source.getAttacker() instanceof LivingEntity) {
			WildfireBrain.onAttacked(this, (LivingEntity) source.getAttacker());
		}

		return damageResult;
	}

	@Override
	public float getBrightnessAtEyes() {
		return 1.0F;
	}

	@Override
	public boolean hurtByWater() {
		return true;
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	static {
		ACTIVE_SHIELDS_COUNT = DataTracker.registerData(WildfireEntity.class, TrackedDataHandlerRegistry.INTEGER);
		TICKS_UNTIL_SHIELD_REGENERATION = DataTracker.registerData(WildfireEntity.class, TrackedDataHandlerRegistry.INTEGER);
		SUMMONED_BLAZES_COUNT = DataTracker.registerData(WildfireEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}
}

