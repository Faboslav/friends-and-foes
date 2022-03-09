package com.faboslav.friendsandfoes.world.gen.feature;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.mixin.StructureFeaturesAccessor;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

/**
 * @see net.minecraft.world.gen.feature.StructureFeature
 */
public class StructureFeature
{
	public static final net.minecraft.world.gen.feature.StructureFeature<StructurePoolFeatureConfig> ILLUSIONER_SHACK = register("illusioner_shack", new IllusionerShackFeature(StructurePoolFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);

	private static <F extends net.minecraft.world.gen.feature.StructureFeature<?>> F register(
		String name,
		F structureFeature,
		GenerationStep.Feature step
	) {

		return StructureFeaturesAccessor.invokeRegister(
			FriendsAndFoes.makeStringID(name),
			structureFeature,
			step
		);
	}

	public static void init() {
	}
}
