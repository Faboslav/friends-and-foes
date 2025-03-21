package com.faboslav.friendsandfoes.common.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public final class BiomeModifications
{
	@ExpectPlatform
	public static void addMobSpawn(
		TagKey<Biome> tag,
		EntityType<?> entityType,
		SpawnGroup spawnGroup,
		int weight,
		int minGroupSize,
		int maxGroupSize
	) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static void addButtercupFeature() {
		throw new AssertionError();
	}

	private BiomeModifications() {
	}
}
