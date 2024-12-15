package com.faboslav.friendsandfoes.common.platform.neoforge;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;

public final class BiomeModificationsImpl
{
	public static void addMobSpawn(
		TagKey<Biome> tag,
		EntityType<?> entityType,
		MobCategory spawnGroup,
		int weight,
		int minGroupSize,
		int maxGroupSize
	) {
	}

	public static void addButtercupFeature() {
	}
}
