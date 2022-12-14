package com.faboslav.friendsandfoes.platform.fabric;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public final class BiomeModificationsImpl
{
	public static void addMobSpawn(
		TagKey<Biome> tag,
		EntityType<?> entityType,
		SpawnGroup spawnGroup,
		int weight,
		int minGroupSize,
		int maxGroupSize
	) {
		BiomeModifications.addSpawn(biomeSelector -> biomeSelector.hasTag(tag) && biomeSelector.hasTag(BiomeTags.IS_OVERWORLD), spawnGroup, entityType, weight, minGroupSize, maxGroupSize);
	}
}
