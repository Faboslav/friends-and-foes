package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.ZombieHorseEntityAccess;
import com.faboslav.friendsandfoes.tag.FriendsAndFoesTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.mob.ZombieHorseEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.*;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World implements StructureWorldAccess
{
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

	@Shadow
	protected abstract BlockPos getLightningPos(BlockPos pos);

	@Inject(
		method = "tickChunk",
		at = @At("TAIL")
	)
	public void friendsandfoes_addZombieHorseSpawnEvent(
		WorldChunk chunk, int randomTickSpeed, CallbackInfo ci
	) {
		if (FriendsAndFoes.getConfig().enableZombieHorseTrap) {
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
					ZombieHorseEntity zombieHorse = EntityType.ZOMBIE_HORSE.create(this);
					((ZombieHorseEntityAccess) zombieHorse).setTrapped(true);
					zombieHorse.setBreedingAge(0);
					zombieHorse.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
					this.spawnEntity(zombieHorse);
				}

				LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(this);
				lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
				lightningEntity.setCosmetic(canZombieHorseSpawn);

				this.spawnEntity(lightningEntity);
			}
		}
	}

	@Inject(
		method = "getLightningRodPos",
		at = @At("TAIL"),
		cancellable = true
	)
	public void friendsandfoes_getLightningRodPos(
		BlockPos pos,
		CallbackInfoReturnable<Optional<BlockPos>> cir
	) {
		if (cir.getReturnValue().isEmpty()) {
			ServerWorld serverWorld = (ServerWorld) (Object) this;

			Optional<BlockPos> optional = serverWorld.getPointOfInterestStorage().getNearestPosition((registryEntry) -> {
				return registryEntry.isIn(FriendsAndFoesTags.LIGHTNING_ROD_POI);
			}, (posx) -> {
				return posx.getY() == this.getTopY(Heightmap.Type.WORLD_SURFACE, posx.getX(), posx.getZ()) - 1;
			}, pos, 128, PointOfInterestStorage.OccupationStatus.ANY);


			if (optional.isPresent()) {
				cir.setReturnValue(optional.map((posx) -> posx.up(1)));
			}
		}
	}
}
