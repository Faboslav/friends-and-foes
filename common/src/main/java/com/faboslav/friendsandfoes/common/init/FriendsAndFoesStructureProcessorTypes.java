package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.platform.PlatformHooks;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import com.faboslav.friendsandfoes.common.world.processor.CitadelBottomProcessor;
import com.faboslav.friendsandfoes.common.world.processor.IllusionerShackBrewingStandProcessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

/**
 * @see StructureProcessorType
 */
public final class FriendsAndFoesStructureProcessorTypes
{
	public static final ResourcefulRegistry<StructureProcessorType<?>> STRUCTURE_PROCESSOR = ResourcefulRegistries.create(BuiltInRegistries.STRUCTURE_PROCESSOR, FriendsAndFoes.MOD_ID);

	public static final RegistryEntry<StructureProcessorType<CitadelBottomProcessor>> CITADEL_BOTTOM_PROCESSOR = STRUCTURE_PROCESSOR.register("citadel_bottom_processor", () -> () -> CitadelBottomProcessor.CODEC);
	public static final RegistryEntry<StructureProcessorType<IllusionerShackBrewingStandProcessor>> ILLUSIONER_SHACK_BREWING_STAND_PROCESSOR = STRUCTURE_PROCESSOR.register("illusioner_shack_brewing_stand_processor", () -> () -> IllusionerShackBrewingStandProcessor.CODEC);

	private FriendsAndFoesStructureProcessorTypes() {
	}

	public static void init() {
		PlatformHooks.PROCESSOR_TYPES.init();
	}
}
