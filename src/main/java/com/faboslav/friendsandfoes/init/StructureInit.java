package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.init.structures.IllusionerShack;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;


import java.util.function.Predicate;

public class StructureInit
{
	public static void init() {
		IllusionerShack.init();
	}

	public static void addStructure(ConfiguredStructureFeature<?,?> configuredStructureFeature, Predicate<BiomeSelectionContext> check) {
		BiomeModifications.addFeature(Registry.STRUCTURE_FEATURE.getKey(configuredStructureFeature.feature)).add(
			ModificationPhase.ADDITIONS,
			check,
			context -> context.getGenerationSettings().addStructure(
				ResourceKey.create(
					Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
					Registry.STRUCTURE_FEATURE.getKey(configuredStructureFeature.feature)
				)
			)
		);
	}
}
