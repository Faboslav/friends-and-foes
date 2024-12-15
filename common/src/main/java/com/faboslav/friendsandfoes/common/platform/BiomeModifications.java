package com.faboslav.friendsandfoes.common.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;

public final class BiomeModifications
{
	@ExpectPlatform
	public static void addMobSpawn(
		TagKey<Biome> tag,
		EntityType<?> entityType,
		MobCategory spawnGroup,
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
