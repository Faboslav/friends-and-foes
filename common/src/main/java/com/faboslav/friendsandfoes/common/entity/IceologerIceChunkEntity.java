package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.util.RandomGenerator;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class IceologerIceChunkEntity extends Entity
{
	private static final String OWNER_UUID_NBT_NAME = "OwnerUuid";
	private static final String TARGET_UUID_NBT_NAME = "TargetUuid";
	private static final String TICKS_UNTIL_FALL_NBT_NAME = "TicksUntilFall";
	private static final String IDLE_TICKS_NBT_NAME = "IdleTicks";

	private static final int MIN_FLYING_TICKS = 60;
	private static final int MAX_FLYING_TICKS = 100;
	private static final int MIN_IDLE_TICKS = 10;
	private static final int MAX_IDLE_TICKS = 20;
	private static final int SUMMON_TICKS = 30;
	private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID;
	private static final EntityDataAccessor<Optional<UUID>> TARGET_UUID;
	private static final EntityDataAccessor<Integer> TICKS_UNTIL_FALL;
	private static final EntityDataAccessor<Integer> IDLE_TICKS;

	@Nullable
	private LivingEntity owner;
	@Nullable
	private LivingEntity target;

	private int lifetimeTicks;
	private float summonAnimationProgress;
	private float lastSummonAnimationProgress;

	static {
		OWNER_UUID = SynchedEntityData.defineId(IceologerIceChunkEntity.class, EntityDataSerializers.OPTIONAL_UUID);
		TARGET_UUID = SynchedEntityData.defineId(IceologerIceChunkEntity.class, EntityDataSerializers.OPTIONAL_UUID);
		TICKS_UNTIL_FALL = SynchedEntityData.defineId(IceologerIceChunkEntity.class, EntityDataSerializers.INT);
		IDLE_TICKS = SynchedEntityData.defineId(IceologerIceChunkEntity.class, EntityDataSerializers.INT);
	}

	public IceologerIceChunkEntity(
		EntityType<? extends IceologerIceChunkEntity> entityType,
		Level world
	) {
		super(entityType, world);

		this.setInvulnerable(true);
		this.setNoGravity(true);

		this.lifetimeTicks = 0;
		this.summonAnimationProgress = 0;
		this.lastSummonAnimationProgress = 0;
		this.playSummonSound();
	}


	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(OWNER_UUID, Optional.empty());
		builder.define(TARGET_UUID, Optional.empty());
		builder.define(TICKS_UNTIL_FALL, RandomGenerator.generateInt(MIN_FLYING_TICKS, MAX_FLYING_TICKS));
		builder.define(IDLE_TICKS, RandomGenerator.generateInt(MIN_IDLE_TICKS, MAX_IDLE_TICKS));
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag nbt) {
		if (nbt.hasUUID(OWNER_UUID_NBT_NAME)) {
			this.setOwnerUuid(nbt.getUUID(OWNER_UUID_NBT_NAME));
		}

		if (nbt.hasUUID(TARGET_UUID_NBT_NAME)) {
			this.setTargetUuid(nbt.getUUID(TARGET_UUID_NBT_NAME));
		}

		if (nbt.contains(TICKS_UNTIL_FALL_NBT_NAME)) {
			this.setTicksUntilFall(nbt.getInt(TICKS_UNTIL_FALL_NBT_NAME));
		}

		if (nbt.contains(IDLE_TICKS_NBT_NAME)) {
			this.setIdleTicks(nbt.getInt(IDLE_TICKS_NBT_NAME));
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
		if (this.getOwnerUuid() != null) {
			nbt.putUUID(OWNER_UUID_NBT_NAME, this.getOwnerUuid());
		}

		if (this.getTargetUuid() != null) {
			nbt.putUUID(TARGET_UUID_NBT_NAME, this.getTargetUuid());
		}

		nbt.putInt(TICKS_UNTIL_FALL_NBT_NAME, this.getTicksUntilFall());
		nbt.putInt(IDLE_TICKS_NBT_NAME, this.getIdleTicks());
	}

	@Nullable
	public UUID getOwnerUuid() {
		return (UUID) ((Optional) this.entityData.get(OWNER_UUID)).orElse(null);
	}

	public void setOwnerUuid(@Nullable UUID uuid) {
		this.entityData.set(OWNER_UUID, Optional.ofNullable(uuid));
	}

	@Nullable
	public LivingEntity getOwner() {
		if (this.owner == null && this.getOwnerUuid() != null && !this.level().isClientSide()) {
			Entity entity = ((ServerLevel) this.level()).getEntity(this.getOwnerUuid());
			if (entity instanceof LivingEntity) {
				this.owner = (LivingEntity) entity;
			}
		}

		return this.owner;
	}

	@Nullable
	public UUID getTargetUuid() {
		return (UUID) ((Optional) this.entityData.get(TARGET_UUID)).orElse(null);
	}

	public void setTargetUuid(@Nullable UUID uuid) {
		this.entityData.set(TARGET_UUID, Optional.ofNullable(uuid));
	}

	@Nullable
	public LivingEntity getTarget() {
		if (this.target == null && this.getTargetUuid() != null && !this.level().isClientSide()) {
			Entity entity = ((ServerLevel) this.level()).getEntity(this.getTargetUuid());
			if (entity instanceof LivingEntity) {
				this.target = (LivingEntity) entity;
			}
		}

		return this.target;
	}

	@Override
	public void tick() {
		super.tick();

		if (lifetimeTicks == 10) {
			this.playSummonSound();
		} else if (lifetimeTicks == 40) {
			this.playAmbientSound();
		}

		this.lifetimeTicks++;
		this.setSummonAnimationProgress();

		if (this.getTarget() != null && !this.level().isClientSide()) {
			if (this.getTarget().isAlwaysTicking()) {
				var playerTarget = (Player) this.getTarget();

				if (playerTarget.isSpectator() || playerTarget.isCreative()) {
					this.customDiscard();
					return;
				}
			}

			if (this.getTicksUntilFall() > 0) {
				this.moveTowardsTarget();
			}
		}

		if (this.getTicksUntilFall() > 0) {
			this.setTicksUntilFall(this.getTicksUntilFall() - 1);
			return;
		}

		this.push(0.0F, -0.05F, 0.0F);
		this.move(MoverType.SELF, this.getDeltaMovement());

		if (this.verticalCollision) {
			this.damageHitEntities();
			this.customDiscard();
		}
	}

	@Override
	/*? >=1.21.3 {*/
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount)
	/*?} else {*/
	/*public boolean hurt(DamageSource damageSource, float amount)
	*//*?}*/
	{
		return false;
	}

	public int getTicksUntilFall() {
		return this.entityData.get(TICKS_UNTIL_FALL);
	}

	private void setTicksUntilFall(int ticksUntilFall) {
		this.entityData.set(TICKS_UNTIL_FALL, ticksUntilFall);
	}

	private int getIdleTicks() {
		return this.entityData.get(IDLE_TICKS);
	}

	private void setIdleTicks(int idleTicks) {
		this.entityData.set(IDLE_TICKS, idleTicks);
	}

	private void damageHitEntities() {
		if (this.level().isClientSide()) {
			return;
		}

		List<LivingEntity> hitEntities = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2D, 0.0D, 0.2D));

		for (LivingEntity hitEntity : hitEntities) {
			this.damage(hitEntity);
		}
	}

	private void damage(LivingEntity hitEntity) {
		LivingEntity livingEntity = this.getOwner();

		if (
			!hitEntity.isAlive()
			|| hitEntity.isInvulnerable()
			|| hitEntity == livingEntity
			|| (
				livingEntity != null
				&& livingEntity.isAlliedTo(hitEntity)
			)
		) {
			return;
		}

		DamageSource damageSource;

		if (livingEntity == null) {
			damageSource = this.damageSources().magic();
		} else {
			damageSource = this.damageSources().indirectMagic(this, livingEntity);
		}

		VersionedEntity.hurt(hitEntity, damageSource, 12.0F);

		if (hitEntity.canFreeze()) {
			hitEntity.setTicksFrozen(400);
		}
	}

	private void customDiscard() {
		this.playHitSound();
		this.spawnHitParticles();
		this.discard();
	}

	public void moveTowardsTarget() {
		LivingEntity target = this.getTarget();

		if (
			target == null
			|| !target.isAlive()
			|| target.getDeltaMovement().lengthSqr() < 0.0001
		) {
			return;
		}

		Vec3 targetPosition = new Vec3(
			target.getX(),
			this.getYPositionWithHeightOffset(
				target.getY(),
				this.getTarget().getBbHeight()
			),
			target.getZ()
		);
		Vec3 targetDirection = targetPosition.subtract(this.position()).normalize();
		this.setDeltaMovement(targetDirection.scale(0.2));
		this.move(MoverType.SELF, this.getDeltaMovement());
	}

	private double getYPositionWithHeightOffset(double y, double height) {
		return Math.min(y + height * height, y + 6.0f);
	}

	private SoundEvent getAmbientSound() {
		return FriendsAndFoesSoundEvents.ENTITY_ICE_CHUNK_AMBIENT.get();
	}

	private void playAmbientSound() {
		SoundEvent soundEvent = this.getAmbientSound();
		this.playSound(soundEvent, 1.0F, RandomGenerator.generateFloat(0.95F, 1.05F));
	}

	private SoundEvent getHitSound() {
		return FriendsAndFoesSoundEvents.ENTITY_ICE_CHUNK_HIT.get();
	}

	private void playHitSound() {
		SoundEvent soundEvent = this.getHitSound();
		this.playSound(soundEvent, 1.0F, RandomGenerator.generateFloat(0.95F, 1.05F));
	}

	private SoundEvent getSummonSound() {
		return FriendsAndFoesSoundEvents.ENTITY_ICE_CHUNK_SUMMON.get();
	}

	private void playSummonSound() {
		SoundEvent soundEvent = this.getSummonSound();
		this.playSound(soundEvent, 1.0F, RandomGenerator.generateFloat(0.95F, 1.05F));
	}

	public void spawnHitParticles() {
		this.spawnParticles(
			new BlockParticleOption(ParticleTypes.BLOCK, Blocks.BLUE_ICE.defaultBlockState()),
			16
		);
	}

	private void spawnParticles(
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
				this.getRandomX(0.5D),
				this.getRandomY() + 0.5D,
				this.getRandomZ(0.5D),
				1,
				0.0D,
				0.0D,
				0.0D,
				0.0D
			);
		}
	}

	public float getSummonAnimationProgress() {
		return this.summonAnimationProgress;
	}

	public void setSummonAnimationProgress() {
		this.lastSummonAnimationProgress = this.summonAnimationProgress;
		this.summonAnimationProgress = Math.min(1.0F, (float) this.lifetimeTicks / SUMMON_TICKS);
	}

	public float getLastSummonAnimationProgress() {
		return this.lastSummonAnimationProgress;
	}

	public static IceologerIceChunkEntity createWithOwnerAndTarget(
		Level world,
		LivingEntity owner,
		LivingEntity target
	) {
		var chunkEntity = new IceologerIceChunkEntity(
			FriendsAndFoesEntityTypes.ICE_CHUNK.get(),
			world
		);

		chunkEntity.setOwnerUuid(owner.getUUID());
		chunkEntity.setTargetUuid(target.getUUID());
		chunkEntity.setPos(
			target.getX(),
			chunkEntity.getYPositionWithHeightOffset(
				target.getY(),
				target.getBbHeight()
			),
			target.getZ()
		);

		return chunkEntity;
	}
}
