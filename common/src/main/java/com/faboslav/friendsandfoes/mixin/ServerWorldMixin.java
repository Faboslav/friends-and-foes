package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.tag.FriendsAndFoesTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.mob.ZombieHorseEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.*;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World implements StructureWorldAccess
{
	@Shadow
	protected abstract BlockPos getLightningPos(BlockPos pos);

	protected ServerWorldMixin(
		MutableWorldProperties properties,
		RegistryKey<World> registryRef,
		RegistryEntry<DimensionType> dimension,
		Supplier<Profiler> profiler,
		boolean isClient,
		boolean debugWorld,
		long seed,
		int maxChainedNeighborUpdates
	) {
		super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
	}

	@Inject(
		method = "tickChunk",
		at = @At("TAIL"),
		cancellable = true
	)
	public void friendsandfoes_addZombieHorseSpawnEvent(
		WorldChunk chunk, int randomTickSpeed, CallbackInfo ci
	) {
		if(FriendsAndFoes.getConfig().enableZombieHorseTrap) {
			BlockPos blockPos;
			ChunkPos chunkPos = chunk.getPos();
			int i = chunkPos.getStartX();
			int j = chunkPos.getStartZ();
			Profiler profiler = this.getProfiler();
			profiler.push("thunder2");
			if (
				this.isRaining()
				&& this.isThundering()
				&& this.getRandom().nextInt(100000) == 0
				&& this.hasRain(blockPos = this.getLightningPos(this.getRandomPosInChunk(i, 0, j, 15)))
			) {
				LocalDifficulty localDifficulty = this.getLocalDifficulty(blockPos);
				boolean canZombieHorseSpawn = this.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING) && this.random.nextDouble() < (double) localDifficulty.getLocalDifficulty() * 0.01 && !this.getBlockState(blockPos.down()).isIn(FriendsAndFoesTags.LIGHTNING_RODS);

				if (canZombieHorseSpawn) {
					ZombieHorseEntity zombieHorseEntity = EntityType.ZOMBIE_HORSE.create(this);
					//zombieHorseEntity.setTrapped(true);
					//zombieHorseEntity.setBreedingAge(0);
					//zombieHorseEntity.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
					this.spawnEntity(zombieHorseEntity);
				}

				LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(this);
				lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
				lightningEntity.setCosmetic(canZombieHorseSpawn);

				this.spawnEntity(lightningEntity);
			}
		}
	}
}