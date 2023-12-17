package com.faboslav.friendsandfoes.platform.forge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import com.faboslav.friendsandfoes.world.forge.processor.IceologerCabinArmorStandProcessor;
import com.faboslav.friendsandfoes.world.forge.processor.IllusionerShackItemFrameProcessor;
import net.minecraft.structure.processor.StructureProcessorType;

public final class StructureEntityProcessorTypesImpl
{
	public static StructureProcessorType<IceologerCabinArmorStandProcessor> ICEOLOGER_CABIN_ARMOR_STAND_PROCESSOR = () -> IceologerCabinArmorStandProcessor.CODEC;
	public static StructureProcessorType<IllusionerShackItemFrameProcessor> ILLUSIONER_SHACK_ITEM_FRAME_PROCESSOR = () -> IllusionerShackItemFrameProcessor.CODEC;

	public static void postInit() {
		RegistryHelper.registerStructureProcessorType(FriendsAndFoes.makeID("iceologer_cabin_armor_stand_processor"), ICEOLOGER_CABIN_ARMOR_STAND_PROCESSOR);
		RegistryHelper.registerStructureProcessorType(FriendsAndFoes.makeID("illusioner_shack_item_frame_processor"), ILLUSIONER_SHACK_ITEM_FRAME_PROCESSOR);
	}
}
