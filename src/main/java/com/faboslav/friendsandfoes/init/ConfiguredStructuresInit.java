package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.config.Settings;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.structure.PlainsVillageData;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class ConfiguredStructuresInit
{

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_ILLUSIONER_SHACK = StructureFeaturesInit.ILLUSIONER_SHACK
		.configure(new StructurePoolFeatureConfig(() -> PlainsVillageData.STRUCTURE_POOLS, 0));

	public static void init() {
		registerConfiguredStructures();
		addConfiguredStructuresToBiomes();
	}

	private static void registerConfiguredStructures() {
		Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, Settings.makeID("configured_illusioner_shack"), CONFIGURED_ILLUSIONER_SHACK);
	}

	private static void addConfiguredStructuresToBiomes() {
		BiomeModifications.addStructure(
			BiomeSelectors.categories(
				Biome.Category.TAIGA
			),
			RegistryKey.of(
				Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
				BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(CONFIGURED_ILLUSIONER_SHACK)
			)
		);
	}
}