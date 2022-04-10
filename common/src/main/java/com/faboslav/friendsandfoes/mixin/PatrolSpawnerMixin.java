package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.init.ModEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.spawner.PatrolSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(PatrolSpawner.class)
public class PatrolSpawnerMixin
{
	boolean isBiomeSpecificIllagerSpawned = false;

	@ModifyVariable(
		method = "spawnPillager",
		ordinal = 0,
		at = @At(
			value = "LOAD"
		)
	)
	private PatrolEntity modifyPatrolEntity(
		PatrolEntity patrolEntity,
		ServerWorld world,
		BlockPos pos,
		Random random,
		boolean captain
	) {
		RegistryEntry<Biome> registryEntry = world.getBiome(pos);
		var category = Biome.getCategory(registryEntry);

		if (category != Biome.Category.TAIGA || this.isBiomeSpecificIllagerSpawned) {
			return patrolEntity;
		}

		this.isBiomeSpecificIllagerSpawned = true;
		var precipitation = registryEntry.value().getPrecipitation();

		if (precipitation == Biome.Precipitation.SNOW) {
			patrolEntity = ModEntity.ICEOLOGER.get().create(world);
		} else {
			patrolEntity = EntityType.ILLUSIONER.create(world);
		}

		return patrolEntity;
	}

	@Inject(
		method = "spawn",
		at = @At("RETURN"),
		cancellable = true
	)
	private void resetBiomeSpecificIllagerSpawnFlag(
		ServerWorld world,
		boolean spawnMonsters,
		boolean spawnAnimals,
		CallbackInfoReturnable<Integer> cir
	) {
		var spawnerPatrolMembersCount = cir.getReturnValue();

		if (spawnerPatrolMembersCount > 0) {
			this.isBiomeSpecificIllagerSpawned = false;
		}

		cir.setReturnValue(spawnerPatrolMembersCount);
	}
}
