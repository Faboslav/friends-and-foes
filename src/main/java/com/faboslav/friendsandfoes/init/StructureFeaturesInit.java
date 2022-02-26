package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.config.FriendsAndFoesConfig;
import com.faboslav.friendsandfoes.config.Settings;
import com.faboslav.friendsandfoes.world.gen.feature.IllusionerShackFeature;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class StructureFeaturesInit
{
	public static StructureFeature<StructurePoolFeatureConfig> ILLUSIONER_SHACK = new IllusionerShackFeature(StructurePoolFeatureConfig.CODEC);

	public static void init() {
		registerStructures();
	}

	private static void registerStructures() {
		FabricStructureBuilder
			.create(Settings.makeID("illusioner_shack"), ILLUSIONER_SHACK)
			.step(GenerationStep.Feature.SURFACE_STRUCTURES)
			.defaultConfig(new StructureConfig(
				FriendsAndFoes.CONFIG.illusionerShackStructureSpacing,
				FriendsAndFoes.CONFIG.illusionerShackStructureSeparation,
				365262534
			))
			.adjustsSurface()
			.register();
	}
}
