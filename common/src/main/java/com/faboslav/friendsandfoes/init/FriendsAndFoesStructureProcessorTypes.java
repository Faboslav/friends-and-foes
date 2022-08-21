package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import com.faboslav.friendsandfoes.world.processor.IceologerCabinArmorStandProcessor;
import com.faboslav.friendsandfoes.world.processor.IllusionerShackBrewingStandProcessor;
import com.faboslav.friendsandfoes.world.processor.IllusionerShackItemFrameProcessor;
import net.minecraft.structure.processor.StructureProcessorType;

/**
 * @see StructureProcessorType
 */
public final class FriendsAndFoesStructureProcessorTypes
{
	public static StructureProcessorType<IllusionerShackBrewingStandProcessor> ILLUSIONER_SHACK_BREWING_STAND_PROCESSOR = () -> IllusionerShackBrewingStandProcessor.CODEC;
	public static StructureProcessorType<IllusionerShackItemFrameProcessor> ILLUSIONER_SHACK_ITEM_FRAME_PROCESSOR = () -> IllusionerShackItemFrameProcessor.CODEC;
	public static StructureProcessorType<IceologerCabinArmorStandProcessor> ICEOLOGER_CABIN_ARMOR_STAND_PROCESSOR = () -> IceologerCabinArmorStandProcessor.CODEC;

	public static void init() {
	}

	public static void postInit() {
		RegistryHelper.registerStructureProcessorType(FriendsAndFoes.makeID("illusioner_shack_brewing_stand_processor"), ILLUSIONER_SHACK_BREWING_STAND_PROCESSOR);
		RegistryHelper.registerStructureProcessorType(FriendsAndFoes.makeID("illusioner_shack_item_frame_processor"), ILLUSIONER_SHACK_ITEM_FRAME_PROCESSOR);
		RegistryHelper.registerStructureProcessorType(FriendsAndFoes.makeID("iceologer_cabin_armor_stand_processor"), ICEOLOGER_CABIN_ARMOR_STAND_PROCESSOR);
	}

	private FriendsAndFoesStructureProcessorTypes() {
	}
}
