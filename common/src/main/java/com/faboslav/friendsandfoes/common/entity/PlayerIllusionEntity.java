package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public final class PlayerIllusionEntity extends MobEntity
{
	private static final String TICKS_UNTIL_DESPAWN_NBT_NAME = "TicksUntilDespawn";
	private static final TrackedData<Integer> TICKS_UNTIL_DESPAWN;
	private static final String PLAYER_UUID_NBT_NAME = "PlayerUuid";
	private static final TrackedData<Optional<UUID>> PLAYER_UUID;
	private static final TrackedData<Byte> PLAYER_MODEL_PARTS;

	@Nullable
	private PlayerEntity player;

	@Nullable
	private PlayerListEntry playerListEntry;

	public double prevCapeX;
	public double prevCapeY;
	public double prevCapeZ;
	public double capeX;
	public double capeY;
	public double capeZ;
	public float prevStrideDistance;
	public float strideDistance;

	public PlayerIllusionEntity(EntityType<? extends MobEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();

		this.dataTracker.startTracking(PLAYER_MODEL_PARTS, (byte) 0);
		this.dataTracker.startTracking(TICKS_UNTIL_DESPAWN, 0);
		this.dataTracker.startTracking(PLAYER_UUID, Optional.empty());
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);

		nbt.putInt(TICKS_UNTIL_DESPAWN_NBT_NAME, this.getTicksUntilDespawn());
		nbt.putUuid(PLAYER_UUID_NBT_NAME, this.getPlayerUuid());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);

		this.setPlayerUuid(nbt.getUuid(PLAYER_UUID_NBT_NAME));
		this.setTicksUntilDespawn(nbt.getInt(TICKS_UNTIL_DESPAWN_NBT_NAME));
	}

	@Override
	public boolean shouldDropXp() {
		return false;
	}

	@Override
	protected boolean shouldDropLoot() {
		return false;
	}

	@Override
	public void tickMovement() {
		super.tickMovement();

		if (this.getTicksUntilDespawn() > 0) {
			this.setTicksUntilDespawn(this.getTicksUntilDespawn() - 1);
		}

		boolean isPlayerNonExistingOrDead = this.getPlayer() != null && !this.getPlayer().isAlive();

		if (
			this.getTicksUntilDespawn() == 0
			|| isPlayerNonExistingOrDead
		) {
			this.discardIllusion();
		}
	}

	@Override
	public boolean damage(
		DamageSource source,
		float amount
	) {
		this.discardIllusion();
		return true;
	}

	@Environment(EnvType.CLIENT)
	public boolean isPartVisible(PlayerModelPart modelPart) {
		return (this.getDataTracker().get(PLAYER_MODEL_PARTS) & modelPart.getBitFlag()) == modelPart.getBitFlag();
	}

	@Environment(EnvType.CLIENT)
	public SkinTextures getSkinTextures() {
		PlayerListEntry playerListEntry = this.getPlayerListEntry();

		if (playerListEntry != null) {
			return playerListEntry.getSkinTextures();
		}

		UUID uuid = this.getPlayerUuid();

		if (uuid == null) {
			uuid = this.getUuid();
		}

		return DefaultSkinHelper.getSkinTextures(uuid);
	}

	@Environment(EnvType.CLIENT)
	public Vec3d lerpVelocity(float tickDelta) {
		return Vec3d.ZERO.lerp(this.getVelocity(), tickDelta);
	}

	@Environment(EnvType.CLIENT)
	@Nullable
	private PlayerListEntry getPlayerListEntry() {
		if (this.playerListEntry == null) {
			UUID uuid = this.getPlayerUuid();

			if (uuid == null) {
				uuid = this.getUuid();
			}

			this.playerListEntry = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(uuid);
		}

		return this.playerListEntry;
	}

	@Nullable
	public UUID getPlayerUuid() {
		return this.dataTracker.get(PLAYER_UUID).orElse(null);
	}

	public void setPlayerUuid(UUID uuid) {
		this.dataTracker.set(PLAYER_UUID, Optional.ofNullable(uuid));
	}

	@Nullable
	public PlayerEntity getPlayer() {
		return this.player;
	}

	public void setPlayer(PlayerEntity player) {
		this.player = player;
	}

	public int getTicksUntilDespawn() {
		return this.dataTracker.get(TICKS_UNTIL_DESPAWN);
	}

	public void setTicksUntilDespawn(int ticksUntilDespawn) {
		this.dataTracker.set(TICKS_UNTIL_DESPAWN, ticksUntilDespawn);
	}

	private void discardIllusion() {
		this.playMirrorSound();
		this.spawnCloudParticles();
		this.discard();
	}

	private void playMirrorSound() {
		this.playSound(
			FriendsAndFoesSoundEvents.ENTITY_PLAYER_MIRROR_MOVE.get(),
			this.getSoundVolume(),
			this.getSoundPitch()
		);
	}

	public boolean tryToTeleport(int x, int y, int z) {
		y -= 8;
		double bottomY = Math.max(y, getWorld().getBottomY());
		double topY = Math.min(bottomY + 16, ((ServerWorld) this.getWorld()).getLogicalHeight() - 1);

		for (int i = 0; i < 16; ++i) {
			y = (int) MathHelper.clamp(y + 1, bottomY, topY);
			boolean teleportResult = this.teleport(x, y, z, false);

			if (teleportResult) {
				return true;
			}
		}

		return false;
	}

	public void spawnCloudParticles() {
		this.spawnParticles(ParticleTypes.CLOUD, 16);
	}

	private void spawnParticles(
		DefaultParticleType particleType,
		int amount
	) {
		if (this.getWorld().isClient()) {
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

	static {
		PLAYER_MODEL_PARTS = DataTracker.registerData(PlayerIllusionEntity.class, TrackedDataHandlerRegistry.BYTE);
		TICKS_UNTIL_DESPAWN = DataTracker.registerData(PlayerIllusionEntity.class, TrackedDataHandlerRegistry.INTEGER);
		PLAYER_UUID = DataTracker.registerData(PlayerIllusionEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
	}
}
