package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.init.ModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.GoToWalkTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public final class WildfireEntity extends HostileEntity
{
	private float eyeOffset = 0.5F;
	private int eyeOffsetCooldown;
	private static final TrackedData<Byte> BLAZE_FLAGS;

	public WildfireEntity(EntityType<? extends WildfireEntity> entityType, World world) {
		super(entityType, world);
		this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.experiencePoints = 10;
	}

	protected void initGoals() {
		//this.goalSelector.add(4, new net.minecraft.entity.mob.BlazeEntity.ShootFireballGoal(this));
		this.goalSelector.add(5, new GoToWalkTargetGoal(this, 1.0));
		this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0, 0.0F));
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.targetSelector.add(1, (new RevengeGoal(this, new Class[0])).setGroupRevenge());
		this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, true));
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return HostileEntity.createHostileAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 120.0)
			.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23000000417232513)
			.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0);
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(BLAZE_FLAGS, (byte)0);
	}

	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.ENTITY_WILDFIRE_AMBIENT.get();
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSoundEvents.ENTITY_WILDFIRE_HURT.get();
	}

	protected SoundEvent getDeathSound() {
		return ModSoundEvents.ENTITY_WILDFIRE_DEATH.get();
	}

	public float getBrightnessAtEyes() {
		return 1.0F;
	}

	public void tickMovement() {
		if (!this.onGround && this.getVelocity().y < 0.0) {
			this.setVelocity(this.getVelocity().multiply(1.0, 0.6, 1.0));
		}

		if (this.world.isClient) {
			if (this.random.nextInt(24) == 0 && !this.isSilent()) {
				this.world.playSound(this.getX() + 0.5, this.getY() + 0.5, this.getZ() + 0.5, SoundEvents.ENTITY_BLAZE_BURN, this.getSoundCategory(), 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);
			}

			for(int i = 0; i < 2; ++i) {
				this.world.addParticle(ParticleTypes.LARGE_SMOKE, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), 0.0, 0.0, 0.0);
			}
		}

		super.tickMovement();
	}

	public boolean hurtByWater() {
		return true;
	}

	protected void mobTick() {
		--this.eyeOffsetCooldown;
		if (this.eyeOffsetCooldown <= 0) {
			this.eyeOffsetCooldown = 100;
			this.eyeOffset = (float)this.random.nextTriangular(0.5, 6.891);
		}

		LivingEntity livingEntity = this.getTarget();
		if (livingEntity != null && livingEntity.getEyeY() > this.getEyeY() + (double)this.eyeOffset && this.canTarget(livingEntity)) {
			Vec3d vec3d = this.getVelocity();
			this.setVelocity(this.getVelocity().add(0.0, (0.30000001192092896 - vec3d.y) * 0.30000001192092896, 0.0));
			this.velocityDirty = true;
		}

		super.mobTick();
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	public boolean isOnFire() {
		return this.isFireActive();
	}

	private boolean isFireActive() {
		return ((Byte)this.dataTracker.get(BLAZE_FLAGS) & 1) != 0;
	}

	void setFireActive(boolean fireActive) {
		byte b = (Byte)this.dataTracker.get(BLAZE_FLAGS);
		if (fireActive) {
			b = (byte)(b | 1);
		} else {
			b &= -2;
		}

		this.dataTracker.set(BLAZE_FLAGS, b);
	}

	static {
		BLAZE_FLAGS = DataTracker.registerData(net.minecraft.entity.mob.BlazeEntity.class, TrackedDataHandlerRegistry.BYTE);
	}
}

