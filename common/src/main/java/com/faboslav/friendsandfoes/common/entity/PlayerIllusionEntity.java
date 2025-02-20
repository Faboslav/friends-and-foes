package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.versions.VersionedEntitySpawnReason;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

//? >=1.21.3 {
import net.minecraft.world.entity.EntitySpawnReason;
//?} else {
/*import net.minecraft.world.entity.MobSpawnType;
*///?}

public final class PlayerIllusionEntity extends Mob
{
	private static final String TICKS_UNTIL_DESPAWN_NBT_NAME = "TicksUntilDespawn";
	private static final EntityDataAccessor<Integer> TICKS_UNTIL_DESPAWN;
	private static final String PLAYER_UUID_NBT_NAME = "PlayerUuid";
	private static final EntityDataAccessor<Optional<UUID>> PLAYER_UUID;
	private static final EntityDataAccessor<Byte> PLAYER_MODEL_PARTS;

	@Nullable
	private Player player;

	@Nullable
	private PlayerInfo playerListEntry;

	public double prevCapeX;
	public double prevCapeY;
	public double prevCapeZ;
	public double capeX;
	public double capeY;
	public double capeZ;
	public float prevStrideDistance;
	public float strideDistance;

	public PlayerIllusionEntity(EntityType<? extends Mob> entityType, Level world) {
		super(entityType, world);
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(
		ServerLevelAccessor world,
		DifficultyInstance difficulty,
		/*? >=1.21.3 {*/
		EntitySpawnReason spawnReason,
		/*?} else {*/
		/*MobSpawnType spawnReason,
		*//*?}*/
		@Nullable SpawnGroupData entityData
	) {
		if(spawnReason == VersionedEntitySpawnReason.COMMAND) {
			//this.setPlayerUuid(this.getUUID());
			this.setTicksUntilDespawn(600);
		}

		/*
		this.shouldShowName()

		this.createCommandSourceStack()

		this.getCommandSenderWorld()
		 */

		return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);

		builder.define(PLAYER_MODEL_PARTS, (byte) 0);
		builder.define(TICKS_UNTIL_DESPAWN, 0);
		builder.define(PLAYER_UUID, Optional.empty());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);

		nbt.putInt(TICKS_UNTIL_DESPAWN_NBT_NAME, this.getTicksUntilDespawn());

		var playerUuid = this.getPlayerUuid();

		if(playerUuid != null) {
			nbt.putUUID(PLAYER_UUID_NBT_NAME, playerUuid);
		}
	}

	public void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);

		if(nbt.contains(PLAYER_UUID_NBT_NAME)) {
			this.setPlayerUuid(nbt.getUUID(PLAYER_UUID_NBT_NAME));
		}

		this.setTicksUntilDespawn(nbt.getInt(TICKS_UNTIL_DESPAWN_NBT_NAME));
	}

	@Override
	public boolean shouldDropExperience() {
		return false;
	}

	@Override
	protected boolean shouldDropLoot() {
		return false;
	}

	@Override
	public boolean shouldShowName() {
		return false;
	}

	@Override
	public void aiStep() {
		super.aiStep();

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
	/*? >=1.21.3 {*/
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount)
	/*?} else {*/
	/*public boolean hurt(DamageSource damageSource, float amount)
	*//*?}*/
	{
		this.discardIllusion();
		return true;
	}

	@Environment(EnvType.CLIENT)
	public boolean isPartVisible(PlayerModelPart modelPart) {
		return (this.getEntityData().get(PLAYER_MODEL_PARTS) & modelPart.getMask()) == modelPart.getMask();
	}

	@Environment(EnvType.CLIENT)
	public PlayerSkin getSkinTextures() {
		PlayerInfo playerListEntry = this.getPlayerListEntry();

		if (playerListEntry != null) {
			return playerListEntry.getSkin();
		}

		UUID uuid = this.getPlayerUuid();

		if (uuid == null) {
			uuid = this.getUUID();
		}

		return DefaultPlayerSkin.get(uuid);
	}

	@Environment(EnvType.CLIENT)
	public Vec3 lerpVelocity(float tickDelta) {
		return Vec3.ZERO.lerp(this.getDeltaMovement(), tickDelta);
	}

	@Environment(EnvType.CLIENT)
	@Nullable
	private PlayerInfo getPlayerListEntry() {
		if (this.playerListEntry == null) {
			UUID uuid = this.getPlayerUuid();

			if (uuid == null) {
				uuid = this.getUUID();
			}

			this.playerListEntry = Minecraft.getInstance().getConnection().getPlayerInfo(uuid);
		}

		return this.playerListEntry;
	}

	@Nullable
	public UUID getPlayerUuid() {
		return this.entityData.get(PLAYER_UUID).orElse(null);
	}

	public void setPlayerUuid(@Nullable UUID uuid) {
		this.entityData.set(PLAYER_UUID, Optional.ofNullable(uuid));
	}

	@Nullable
	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getTicksUntilDespawn() {
		return this.entityData.get(TICKS_UNTIL_DESPAWN);
	}

	public void setTicksUntilDespawn(int ticksUntilDespawn) {
		this.entityData.set(TICKS_UNTIL_DESPAWN, ticksUntilDespawn);
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
			this.getVoicePitch()
		);
	}

	public boolean tryToTeleport(int x, int y, int z) {
		y -= 8;
		//? >=1.21.3 {
		int worldBottomY = this.level().getMinY();
		//?} else {
		/*int worldBottomY = this.level().getMinBuildHeight();
		*///?}
		int logicalHeight = ((ServerLevel)(this.level())).getLogicalHeight();
		double bottomY = Math.max(y, worldBottomY);
		double topY = Math.min(bottomY + 16, logicalHeight- 1);

		for (int i = 0; i < 16; ++i) {
			y = (int) Mth.clamp(y + 1, bottomY, topY);
			boolean teleportResult = this.randomTeleport(x, y, z, false);

			if (teleportResult) {
				return true;
			}
		}

		return false;
	}

	public void spawnCloudParticles() {
		this.spawnParticles(ParticleTypes.CLOUD, 16);
	}

	private <T extends ParticleOptions> void spawnParticles(
		T particleType,
		int amount
	) {
		if (this.level().isClientSide()) {
			return;
		}

		for (int i = 0; i < amount; i++) {
			((ServerLevel) this.getCommandSenderWorld()).sendParticles(
				particleType,
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

	static {
		PLAYER_MODEL_PARTS = SynchedEntityData.defineId(PlayerIllusionEntity.class, EntityDataSerializers.BYTE);
		TICKS_UNTIL_DESPAWN = SynchedEntityData.defineId(PlayerIllusionEntity.class, EntityDataSerializers.INT);
		PLAYER_UUID = SynchedEntityData.defineId(PlayerIllusionEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	}
}
