package com.faboslav.friendsandfoes.fabric.platform;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class BiomeModifications implements com.faboslav.friendsandfoes.common.platform.BiomeModifications
{
	@Override
	public void addButtercupFeature() {
		TagKey<Biome> flowerForestTag = TagKey.create(Registries.BIOME, FriendsAndFoes.makeID("has_buttercup_patch"));
		net.fabricmc.fabric.api.biome.v1.BiomeModifications.create(FriendsAndFoes.makeID("add_buttercup_patch"))
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
