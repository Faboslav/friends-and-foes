package com.faboslav.friendsandfoes.world;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.ModificationPhase;

public final class BiomeModification
{
	public static void addFeatures() {
		TagKey<Biome> flowerForestTag = TagKey.of(RegistryKeys.BIOME, new Identifier(FriendsAndFoes.MOD_ID, "has_buttercup_patch"));
		BiomeModifications.create(new Identifier(FriendsAndFoes.MOD_ID, "add_buttercup_patch"))
			.add(ModificationPhase.ADDITIONS,
				(context) -> context.isIn(flowerForestTag),
				context -> context.getGenerationSettings().addFeature(
					GenerationStep.Feature.VEGETAL_DECORATION,
					RegistryKey.of(
						RegistryKeys.PLACED_FEATURE,
						new Identifier(FriendsAndFoes.MOD_ID, "buttercup_patch")
					)
				)
			);

	}
}
