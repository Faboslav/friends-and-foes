package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.mixin.StructureFeatureAccessor;
import com.faboslav.friendsandfoes.world.gen.feature.IceologerCabinFeature;
import com.faboslav.friendsandfoes.world.gen.feature.IllusionerShackFeature;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.structure.StructureType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;

public final class ModStructureFeatures
{
	private static final DeferredRegister<StructureType<?>> STRUCTURE_FEATURES = DeferredRegister.create(FriendsAndFoes.MOD_ID, Registry.STRUCTURE_TYPE_KEY);

	public static final RegistrySupplier<StructureType<StructurePoolFeatureConfig>> ILLUSIONER_SHACK;
	public static final RegistrySupplier<StructureType<StructurePoolFeatureConfig>> ICEOLOGER_CABIN;

	static {
		ILLUSIONER_SHACK = STRUCTURE_FEATURES.register("illusioner_shack", () -> new IllusionerShackFeature(StructurePoolFeatureConfig.CODEC));
		ICEOLOGER_CABIN = STRUCTURE_FEATURES.register("iceologer_cabin", () -> new IceologerCabinFeature(StructurePoolFeatureConfig.CODEC));
	}

	public static void initRegister() {
		STRUCTURE_FEATURES.register();
	}

	public static void init() {
		initFeatureSteps();
	}

	private static void initFeatureSteps() {
		StructureFeatureAccessor.getStructureToGenerationStep().put(ILLUSIONER_SHACK.get(), GenerationStep.Feature.SURFACE_STRUCTURES);
		StructureFeatureAccessor.getStructureToGenerationStep().put(ICEOLOGER_CABIN.get(), GenerationStep.Feature.SURFACE_STRUCTURES);
	}

	private ModStructureFeatures() {
	}
}
