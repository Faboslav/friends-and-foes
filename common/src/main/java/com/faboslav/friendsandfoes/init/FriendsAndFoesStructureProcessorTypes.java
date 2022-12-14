package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.platform.RegistryHelper;
import com.faboslav.friendsandfoes.platform.StructureEntityProcessorTypes;
import com.faboslav.friendsandfoes.world.processor.CitadelBottomProcessor;
import com.faboslav.friendsandfoes.world.processor.IllusionerShackBrewingStandProcessor;
import net.minecraft.structure.processor.StructureProcessorType;

/**
 * @see StructureProcessorType
 */
public final class FriendsAndFoesStructureProcessorTypes
{
	public static StructureProcessorType<IllusionerShackBrewingStandProcessor> ILLUSIONER_SHACK_BREWING_STAND_PROCESSOR = () -> IllusionerShackBrewingStandProcessor.CODEC;
	public static StructureProcessorType<CitadelBottomProcessor> CITADEL_BOTTOM_PROCESSOR = () -> CitadelBottomProcessor.CODEC;

	public static void init() {
		RegistryHelper.registerStructureProcessorType("illusioner_shack_brewing_stand_processor", ILLUSIONER_SHACK_BREWING_STAND_PROCESSOR);
		RegistryHelper.registerStructureProcessorType("citadel_bottom_processor", CITADEL_BOTTOM_PROCESSOR);
		StructureEntityProcessorTypes.init();
	}

	private FriendsAndFoesStructureProcessorTypes() {
	}
}
