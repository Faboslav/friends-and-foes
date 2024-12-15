package com.faboslav.friendsandfoes.common.platform.fabric;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;

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
		BiomeModifications.addSpawn(biomeSelector -> biomeSelector.hasTag(tag) && biomeSelector.hasTag(BiomeTags.IS_OVERWORLD), spawnGroup, entityType, weight, minGroupSize, maxGroupSize);
	}

	public static void addButtercupFeature() {
		TagKey<Biome> flowerForestTag = TagKey.create(Registries.BIOME, FriendsAndFoes.makeID("has_buttercup_patch"));
		BiomeModifications.create(FriendsAndFoes.makeID("add_buttercup_patch"))
			.add(ModificationPhase.ADDITIONS,
				(context) -> context.hasTag(flowerForestTag),
				context -> context.getGenerationSettings().addFeature(
					GenerationStep.Decoration.VEGETAL_DECORATION,
					ResourceKey.create(
						Registries.PLACED_FEATURE,
						FriendsAndFoes.makeID("buttercup_patch")
					)
				)
			);
	}
}
