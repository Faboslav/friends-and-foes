package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.entity.ai.brain.WildfireBrain;
import com.faboslav.friendsandfoes.init.FriendsAndFoesSoundEvents;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public final class WildfireEntity extends HostileEntity
{
	private float damageAmountCounter = 0;
	private float eyeOffset = 0.5F;
	private int eyeOffsetCooldown;

	public static final int DEFAULT_ACTIVE_SHIELDS_COUNT = 4;
	private static final String ACTIVE_SHIELDS_NBT_NAME = "ActiveShields";
	private static final TrackedData<Integer> ACTIVE_SHIELDS;

	public WildfireEntity(EntityType<? extends WildfireEntity> entityType, World world) {
		super(entityType, world);
		this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.experiencePoints = 10;
	}

	@Override
	protected Brain.Profile<?> createBrainProfile() {
		return Brain.createProfile(WildfireBrain.MEMORY_MODULES, WildfireBrain.SENSORS);
	}

	@Override
	@SuppressWarnings("all")
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
		return WildfireBrain.create((Brain<WildfireEntity>) this.createBrainProfile().deserialize(dynamic));
	}

	@Override
	@SuppressWarnings("all")
	public Brain<WildfireEntity> getBrain() {
		return (Brain<WildfireEntity>) super.getBrain();
	}

	@Override
	protected void mobTick() {
		this.world.getProfiler().push("wildfireBrain");
		this.getBrain().tick((ServerWorld) this.getWorld(), this);
		this.world.getProfiler().pop();
		this.world.getProfiler().push("wildfireActivityUpdate");
		WildfireBrain.updateActivities(this);
		this.world.getProfiler().pop();

		--this.eyeOffsetCooldown;
		if (this.eyeOffsetCooldown <= 0) {
			this.eyeOffsetCooldown = 100;
			this.eyeOffset = (float) this.random.nextTriangular(0.5, 6.891);
		}

		LivingEntity livingEntity = this.getTarget();
		if (livingEntity != null && livingEntity.getEyeY() > this.getEyeY() + (double) this.eyeOffset && this.canTarget(livingEntity)) {
			Vec3d vec3d = this.getVelocity();
			this.setVelocity(this.getVelocity().add(0.0, (0.30000001192092896 - vec3d.y) * 0.30000001192092896, 0.0));
			this.velocityDirty = true;
		}

		super.mobTick();
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return HostileEntity.createHostileAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 80.0)
			.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23000000417232513)
			.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(ACTIVE_SHIELDS, DEFAULT_ACTIVE_SHIELDS_COUNT);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt(ACTIVE_SHIELDS_NBT_NAME, this.getActiveShieldsCount());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.setActiveShieldsCount(nbt.getInt(ACTIVE_SHIELDS_NBT_NAME));
	}

	public SoundEvent getShieldBreakSound() {
		return FriendsAndFoesSoundEvents.ENTITY_WILDFIRE_SHIELD_BREAK.get();
	}

	public void playShieldBreakSound() {
		this.playSound(this.getShieldBreakSound(), 1.0F, 1.0F);
	}

	public void breakShield() {
		this.setActiveShieldsCount(this.getActiveShieldsCount() - 1);
	}

	public void regenerateShield() {
		this.setActiveShieldsCount(this.getActiveShieldsCount() + 1);
	}

	public int getActiveShieldsCount() {
		return this.dataTracker.get(ACTIVE_SHIELDS);
	}

	public void setActiveShieldsCount(int activeShields) {
		this.dataTracker.set(ACTIVE_SHIELDS, activeShields);
	}

	public boolean hasActiveShields() {
		return this.getActiveShieldsCount() > 0;
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

	public void tickMovement() {
		if (!this.onGround && this.getVelocity().y < 0.0) {
			this.setVelocity(this.getVelocity().multiply(1.0, 0.6, 1.0));
		}

		/*
		if (this.world.isClient) {
			if (this.random.nextInt(24) == 0 && !this.isSilent()) {
				this.world.playSound(this.getX() + 0.5, this.getY() + 0.5, this.getZ() + 0.5, SoundEvents.ENTITY_BLAZE_BURN, this.getSoundCategory(), 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);
			}

			for (int i = 0; i < 2; ++i) {
				this.world.addParticle(ParticleTypes.LARGE_SMOKE, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), 0.0, 0.0, 0.0);
			}
		}*/

		super.tickMovement();
	}

	public boolean damage(
		DamageSource source,
		float amount
	) {
		if (this.hasActiveShields()) {
			this.damageAmountCounter += amount;
			float shieldBreakDamageThreshold = (float) this.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH) * 0.25F;

			if (this.damageAmountCounter >= shieldBreakDamageThreshold) {
				this.breakShield();
				this.playShieldBreakSound();
				this.damageAmountCounter = 0;
			}

			amount = 0.0F;
		}

		return super.damage(source, amount);
	}

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
		ACTIVE_SHIELDS = DataTracker.registerData(WildfireEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}
}

