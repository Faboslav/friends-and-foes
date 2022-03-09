package com.faboslav.friendsandfoes.entity.mob;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.init.ModEntity;
import com.faboslav.friendsandfoes.init.ModSounds;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class IceologerIceChunkEntity extends Entity
{
	private static final String OWNER_UUID_NBT_NAME = "OwnerUuid";
	private static final String TARGET_UUID_NBT_NAME = "TargetUuid";
	private static final String TICKS_UNTIL_FALL_NBT_NAME = "TicksUntilFall";
	private static final String IDLE_TICKS_NBT_NAME = "IdleTicks";

	private static final int MIN_FLYING_TICKS = 60;
	private static final int MAX_FLYING_TICKS = 100;
	private static final int MIN_IDLE_TICKS = 10;
	private static final int MAX_IDLE_TICKS = 20;
	private static final int FALLING_TICKS = 20;
	private static final TrackedData<Integer> TICKS_UNTIL_FALL;
	private static final TrackedData<Integer> IDLE_TICKS;

	@Nullable
	private LivingEntity owner;
	@Nullable
	private UUID ownerUuid;
	@Nullable
	private LivingEntity target;
	@Nullable
	private UUID targetUuid;

	private int lifetimeTicks;

	static {
		TICKS_UNTIL_FALL = DataTracker.registerData(IceologerIceChunkEntity.class, TrackedDataHandlerRegistry.INTEGER);
		IDLE_TICKS = DataTracker.registerData(IceologerIceChunkEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}

	public IceologerIceChunkEntity(
		EntityType<? extends IceologerIceChunkEntity> entityType,
		World world
	) {
		super(entityType, world);

		if (this.getWorld().isClient()) {
			return;
		}

		this.setInvulnerable(true);
		this.setNoGravity(true);

		this.lifetimeTicks = 0;
	}

	@Override
	protected void initDataTracker() {
		this.dataTracker.startTracking(TICKS_UNTIL_FALL, RandomGenerator.generateInt(MIN_FLYING_TICKS, MAX_FLYING_TICKS));
		this.dataTracker.startTracking(IDLE_TICKS, RandomGenerator.generateInt(MIN_IDLE_TICKS, MAX_IDLE_TICKS));
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		if (nbt.containsUuid(OWNER_UUID_NBT_NAME)) {
			this.ownerUuid = nbt.getUuid(OWNER_UUID_NBT_NAME);
		}

		if (nbt.containsUuid(TARGET_UUID_NBT_NAME)) {
			this.targetUuid = nbt.getUuid(TARGET_UUID_NBT_NAME);
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
		if (this.ownerUuid != null) {
			nbt.putUuid(OWNER_UUID_NBT_NAME, this.ownerUuid);
		}

		if (this.targetUuid != null) {
			nbt.putUuid(TARGET_UUID_NBT_NAME, this.targetUuid);
		}

		nbt.putInt(TICKS_UNTIL_FALL_NBT_NAME, this.getTicksUntilFall());
		nbt.putInt(IDLE_TICKS_NBT_NAME, this.getIdleTicks());
	}

	public void setOwner(@Nullable LivingEntity owner) {
		this.owner = owner;
		this.ownerUuid = owner == null ? null:owner.getUuid();
	}

	@Nullable
	public LivingEntity getOwner() {
		if (this.owner == null && this.ownerUuid != null && this.getWorld().isClient() == false) {
			Entity entity = ((ServerWorld) this.world).getEntity(this.ownerUuid);
			if (entity instanceof LivingEntity) {
				this.owner = (LivingEntity) entity;
			}
		}

		return this.owner;
	}

	public void setTarget(@Nullable LivingEntity target) {
		this.target = target;
		this.targetUuid = target == null ? null:target.getUuid();
	}

	@Nullable
	public LivingEntity getTarget() {
		if (this.target == null && this.targetUuid != null && this.getWorld().isClient() == false) {
			Entity entity = ((ServerWorld) this.world).getEntity(this.targetUuid);
			if (entity instanceof LivingEntity) {
				this.target = (LivingEntity) entity;
			}
		}

		return this.target;
	}

	@Override
	public void tick() {
		if (lifetimeTicks == 0) {
			this.playSummonSound();
		} else if (lifetimeTicks == 40) {
			this.playAmbientSound();
		}

		this.lifetimeTicks++;

		if (this.target != null && this.getWorld().isClient() == false) {
			FriendsAndFoes.LOGGER.info(this.target.toString());
			if (this.target.isPlayer()) {
				var playerTarget = (PlayerEntity) this.target;
				if (
					playerTarget.isSpectator()
					|| playerTarget.isCreative()
				) {
					this.customDiscard();
					return;
				}
			}

			if (this.getTicksUntilFall() > 0) {
				this.setPositionAboveTarget();
			}
		}


		if (this.getTicksUntilFall() > 0) {
			this.setTicksUntilFall(this.getTicksUntilFall() - 1);
			return;
		}

		this.addVelocity(0.0F, -0.05F, 0.0F);
		this.move(MovementType.SELF, this.getVelocity());

		if (this.verticalCollision == true) {
			FriendsAndFoes.LOGGER.info("hit");
			this.damageHitEntities();
			this.customDiscard();
		}
	}

	public Packet<?> createSpawnPacket() {
		return new EntitySpawnS2CPacket(this);
	}

	private int getTicksUntilFall() {
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
		List<LivingEntity> hitEntities = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(0.2D, 0.0D, 0.2D));
		Iterator hitEntitiesIterator = hitEntities.iterator();

		while (hitEntitiesIterator.hasNext()) {
			LivingEntity hitEntity = (LivingEntity) hitEntitiesIterator.next();
			this.damage(hitEntity);
		}
	}

	private void damage(LivingEntity hitEntity) {
		LivingEntity livingEntity = this.getOwner();

		if (
			hitEntity.isAlive() == false
			|| hitEntity.isInvulnerable() == true
			|| hitEntity == livingEntity
		) {
			return;
		}

		if (livingEntity == null) {
			hitEntity.damage(DamageSource.MAGIC, 12.0F);
			return;
		}

		if (livingEntity.isTeammate(hitEntity)) {
			return;
		}

		hitEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 80));
	}

	private void customDiscard() {
		this.playHitSound();
		this.spawnHitParticles();
		this.discard();
	}

	private void setPositionAboveTarget() {
		this.setPosition(
			this.target.getX(),
			this.target.getY() + target.getHeight() * target.getHeight(),
			this.target.getZ()
		);
	}

	private SoundEvent getAmbientSound() {
		return ModSounds.ENTITY_ICE_CHUNK_AMBIENT;
	}

	private void playAmbientSound() {
		SoundEvent soundEvent = this.getAmbientSound();
		this.playSound(soundEvent, 1.0F, RandomGenerator.generateFloat(0.95F, 1.05F));
	}

	private SoundEvent getHitSound() {
		return ModSounds.ENTITY_ICE_CHUNK_HIT;
	}

	private void playHitSound() {
		SoundEvent soundEvent = this.getHitSound();
		this.playSound(soundEvent, 1.0F, RandomGenerator.generateFloat(0.95F, 1.05F));
	}

	private SoundEvent getSummonSound() {
		return ModSounds.ENTITY_ICE_CHUNK_SUMMON;
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
		ParticleEffect particleType,
		int amount
	) {
		if (this.world.isClient()) {
			return;
		}

		for (int i = 0; i < amount; i++) {
			((ServerWorld) this.getEntityWorld()).spawnParticles(
				particleType,
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
		var summonAnimationProgress = (float) this.lifetimeTicks / 30;
		return Math.min(1.0F, summonAnimationProgress);
	}

	public static IceologerIceChunkEntity createWithOwnerAndTarget(
		World world,
		LivingEntity owner,
		LivingEntity target
	) {
		var chunkEntity = new IceologerIceChunkEntity(
			ModEntity.ICE_CHUNK,
			world
		);

		chunkEntity.setOwner(owner);
		chunkEntity.setTarget(target);

		return chunkEntity;
	}
}
