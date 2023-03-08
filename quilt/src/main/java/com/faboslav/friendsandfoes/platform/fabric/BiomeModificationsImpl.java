package com.faboslav.friendsandfoes.platform.fabric;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;

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

	public static void addButtercupFeature() {
		TagKey<Biome> flowerForestTag = TagKey.of(Registry.BIOME_KEY, FriendsAndFoes.makeID("has_buttercup_patch"));
		BiomeModifications.create(FriendsAndFoes.makeID("add_buttercup_patch"))
			.add(ModificationPhase.ADDITIONS,
				(context) -> context.hasTag(flowerForestTag),
				context -> context.getGenerationSettings().addFeature(
					GenerationStep.Feature.VEGETAL_DECORATION,
					RegistryKey.of(
						Registry.PLACED_FEATURE_KEY,
						FriendsAndFoes.makeID("buttercup_patch")
					)
				)
			);
	}
}
