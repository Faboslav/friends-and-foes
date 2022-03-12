package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.FriendsAndFoes;
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
import org.spongepowered.asm.mixin.injection.ModifyVariable;

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

		FriendsAndFoes.LOGGER.info(category.asString());

		if (
			category != Biome.Category.TAIGA
			|| this.isBiomeSpecificIllagerSpawned
		) {
			FriendsAndFoes.LOGGER.info("not a taiga");
			return patrolEntity;
		}

		this.isBiomeSpecificIllagerSpawned = true;
		var precipitation = registryEntry.value().getPrecipitation();
		FriendsAndFoes.LOGGER.info(precipitation.asString());

		if (precipitation == Biome.Precipitation.SNOW) {
			FriendsAndFoes.LOGGER.info("iceologer");
			patrolEntity = ModEntity.ICEOLOGER.create(world);
		} else {
			FriendsAndFoes.LOGGER.info("illusioner");
			patrolEntity = EntityType.ILLUSIONER.create(world);
		}

		return patrolEntity;
	}
}
