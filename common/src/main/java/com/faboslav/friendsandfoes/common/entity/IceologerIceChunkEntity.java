package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.util.RandomGenerator;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
	private static final TrackedData<Optional<UUID>> OWNER_UUID;
	private static final TrackedData<Optional<UUID>> TARGET_UUID;
	private static final TrackedData<Integer> TICKS_UNTIL_FALL;
	private static final TrackedData<Integer> IDLE_TICKS;

	@Nullable
	private LivingEntity owner;
	@Nullable
	private LivingEntity target;

	private int lifetimeTicks;
	private float summonAnimationProgress;
	private float lastSummonAnimationProgress;

	static {
		OWNER_UUID = DataTracker.registerData(IceologerIceChunkEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
		TARGET_UUID = DataTracker.registerData(IceologerIceChunkEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
		TICKS_UNTIL_FALL = DataTracker.registerData(IceologerIceChunkEntity.class, TrackedDataHandlerRegistry.INTEGER);
		IDLE_TICKS = DataTracker.registerData(IceologerIceChunkEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}

	public IceologerIceChunkEntity(
		EntityType<? extends IceologerIceChunkEntity> entityType,
		World world
	) {
		super(entityType, world);

		this.setInvulnerable(true);
		this.setNoGravity(true);

		this.lifetimeTicks = 0;
		this.summonAnimationProgress = 0;
		this.lastSummonAnimationProgress = 0;
		this.playSummonSound();
	}


	protected void initDataTracker(DataTracker.Builder builder) {
		builder.add(OWNER_UUID, Optional.empty());
		builder.add(TARGET_UUID, Optional.empty());
		builder.add(TICKS_UNTIL_FALL, RandomGenerator.generateInt(MIN_FLYING_TICKS, MAX_FLYING_TICKS));
		builder.add(IDLE_TICKS, RandomGenerator.generateInt(MIN_IDLE_TICKS, MAX_IDLE_TICKS));
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		if (nbt.containsUuid(OWNER_UUID_NBT_NAME)) {
			this.setOwnerUuid(nbt.getUuid(OWNER_UUID_NBT_NAME));
		}

		if (nbt.containsUuid(TARGET_UUID_NBT_NAME)) {
			this.setTargetUuid(nbt.getUuid(TARGET_UUID_NBT_NAME));
		}

		if (nbt.contains(TICKS_UNTIL_FALL_NBT_NAME)) {
			this.setTicksUntilFall(nbt.getInt(TICKS_UNTIL_FALL_NBT_NAME));
		}

		if (nbt.contains(IDLE_TICKS_NBT_NAME)) {
			this.setIdleTicks(nbt.getInt(IDLE_TICKS_NBT_NAME));
		}
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		if (this.getOwnerUuid() != null) {
			nbt.putUuid(OWNER_UUID_NBT_NAME, this.getOwnerUuid());
		}

		if (this.getTargetUuid() != null) {
			nbt.putUuid(TARGET_UUID_NBT_NAME, this.getTargetUuid());
		}

		nbt.putInt(TICKS_UNTIL_FALL_NBT_NAME, this.getTicksUntilFall());
		nbt.putInt(IDLE_TICKS_NBT_NAME, this.getIdleTicks());
	}

	@Nullable
	public UUID getOwnerUuid() {
		return (UUID) ((Optional) this.dataTracker.get(OWNER_UUID)).orElse(null);
	}

	public void setOwnerUuid(@Nullable UUID uuid) {
		this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
	}

	@Nullable
	public LivingEntity getOwner() {
		if (this.owner == null && this.getOwnerUuid() != null && !this.getWorld().isClient()) {
			Entity entity = ((ServerWorld) this.getWorld()).getEntity(this.getOwnerUuid());
			if (entity instanceof LivingEntity) {
				this.owner = (LivingEntity) entity;
			}
		}

		return this.owner;
	}

	@Nullable
	public UUID getTargetUuid() {
		return (UUID) ((Optional) this.dataTracker.get(TARGET_UUID)).orElse(null);
	}

	public void setTargetUuid(@Nullable UUID uuid) {
		this.dataTracker.set(TARGET_UUID, Optional.ofNullable(uuid));
	}

	@Nullable
	public LivingEntity getTarget() {
		if (this.target == null && this.getTargetUuid() != null && !this.getWorld().isClient()) {
			Entity entity = ((ServerWorld) this.getWorld()).getEntity(this.getTargetUuid());
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

		if (this.getTarget() != null && !this.getWorld().isClient()) {
			if (this.getTarget().isPlayer()) {
				var playerTarget = (PlayerEntity) this.getTarget();

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

		this.addVelocity(0.0F, -0.05F, 0.0F);
		this.move(MovementType.SELF, this.getVelocity());

		if (this.verticalCollision) {
			this.damageHitEntities();
			this.customDiscard();
		}
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		return false;
	}

	public int getTicksUntilFall() {
		return this.dataTracker.get(TICKS_UNTIL_FALL);
	}

	private void setTicksUntilFall(int ticksUntilFall) {
		this.dataTracker.set(TICKS_UNTIL_FALL, ticksUntilFall);
	}

	private int getIdleTicks() {
		return this.dataTracker.get(IDLE_TICKS);
	}

	private void setIdleTicks(int idleTicks) {
		this.dataTracker.set(IDLE_TICKS, idleTicks);
	}

	private void damageHitEntities() {
		if (this.getWorld().isClient()) {
			return;
		}

		List<LivingEntity> hitEntities = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(0.2D, 0.0D, 0.2D));

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
				&& livingEntity.isTeammate(hitEntity)
			)
		) {
			return;
		}

		DamageSource damageSource;

		if (livingEntity == null) {
			damageSource = this.getDamageSources().magic();
		} else {
			damageSource = this.getDamageSources().indirectMagic(this, livingEntity);
		}

		hitEntity.damage(damageSource, 12.0F);

		if (hitEntity.canFreeze()) {
			hitEntity.setFrozenTicks(400);
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
			|| target.getVelocity().lengthSquared() < 0.0001
		) {
			return;
		}

		Vec3d targetPosition = new Vec3d(
			target.getX(),
			this.getYPositionWithHeightOffset(
				target.getY(),
				this.getTarget().getHeight()
			),
			target.getZ()
		);
		Vec3d targetDirection = targetPosition.subtract(this.getPos()).normalize();
		this.setVelocity(targetDirection.multiply(0.2));
		this.move(MovementType.SELF, this.getVelocity());
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
			new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.BLUE_ICE.getDefaultState()),
			16
		);
	}

	private void spawnParticles(
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
				this.getParticleX(0.5D),
				this.getRandomBodyY() + 0.5D,
				this.getParticleZ(0.5D),
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
		World world,
		LivingEntity owner,
		LivingEntity target
	) {
		var chunkEntity = new IceologerIceChunkEntity(
			FriendsAndFoesEntityTypes.ICE_CHUNK.get(),
			world
		);

		chunkEntity.setOwnerUuid(owner.getUuid());
		chunkEntity.setTargetUuid(target.getUuid());
		chunkEntity.setPosition(
			target.getX(),
			chunkEntity.getYPositionWithHeightOffset(
				target.getY(),
				target.getHeight()
			),
			target.getZ()
		);

		return chunkEntity;
	}
}
