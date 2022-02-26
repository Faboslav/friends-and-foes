package com.faboslav.friendsandfoes.init.structures;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.BiomeKeys;

public class IllusionerShack
{
	public static void init() {
		if (FriendsAndFoes.CONFIG.generateIllusionerShackStructure) {
			return;
			BiomeModifications.addFeature(
				BiomeSelectors.includeByKey(
					BiomeKeys.TAIGA,
					BiomeKeys.OLD_GROWTH_PINE_TAIGA,
					BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA
				),
				RegistryKey.of(
					Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
					BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(CONFIGURED_ILLUSIONER_SHACK)
				)
			);
		}

		BiomeInjection.addStructure(RSConfiguredStructures.WITCH_HUTS_OAK, (event) ->
			(RepurposedStructures.RSAllConfig.RSWitchHutsConfig.witchHutsOakAverageChunkDistance != 1001 &&
			 BiomeSelection.isBiomeAllowed(event, RSStructures.WITCH_HUTS_OAK,
				 () -> BiomeSelection.haveCategories(event, Biome.BiomeCategory.FOREST) &&
					   !BiomeSelection.hasName(event, "birch", "dark", "spooky", "dead", "haunted", "evil", "witch", "ominous", "ebony")))
		);
	}
}
