package com.faboslav.friendsandfoes.fabric.platform;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStructureProcessorTypes;
import com.faboslav.friendsandfoes.fabric.world.processor.IceologerCabinArmorStandProcessor;
import com.faboslav.friendsandfoes.fabric.world.processor.IllusionerShackItemFrameProcessor;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public final class ProcessorTypes implements com.faboslav.friendsandfoes.common.platform.ProcessorTypes
{
	public static final RegistryEntry<StructureProcessorType<IllusionerShackItemFrameProcessor>> ILLUSIONER_SHACK_ITEM_FRAME_PROCESSOR = FriendsAndFoesStructureProcessorTypes.STRUCTURE_PROCESSOR.register("illusioner_shack_item_frame_processor", () -> () -> IllusionerShackItemFrameProcessor.CODEC);
	public static final RegistryEntry<StructureProcessorType<IceologerCabinArmorStandProcessor>> ICEOLOGER_CABIN_ARMOR_STAND_PROCESSOR = FriendsAndFoesStructureProcessorTypes.STRUCTURE_PROCESSOR.register("iceologer_cabin_armor_stand_processor", () -> () -> IceologerCabinArmorStandProcessor.CODEC);

	@Override
	public void init() {

	}
}
