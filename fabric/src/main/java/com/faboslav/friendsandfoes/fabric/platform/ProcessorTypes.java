package com.faboslav.friendsandfoes.fabric.platform;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStructureProcessorTypes;
import com.faboslav.friendsandfoes.fabric.world.processor.IceologerCabinArmorStandProcessor;
import com.faboslav.friendsandfoes.fabric.world.processor.IllusionerShackItemFrameProcessor;
import com.mojang.serialization.MapCodec;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
//? if <26.2 {
/*import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
 *///?}
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;

public final class ProcessorTypes implements com.faboslav.friendsandfoes.common.platform.ProcessorTypes
{
	//? if >=26.2 {
	public static final RegistryEntry<MapCodec<IllusionerShackItemFrameProcessor>> ILLUSIONER_SHACK_ITEM_FRAME_PROCESSOR = FriendsAndFoesStructureProcessorTypes.STRUCTURE_PROCESSOR.register("illusioner_shack_item_frame_processor", () -> IllusionerShackItemFrameProcessor.CODEC);
	public static final RegistryEntry<MapCodec<IceologerCabinArmorStandProcessor>> ICEOLOGER_CABIN_ARMOR_STAND_PROCESSOR = FriendsAndFoesStructureProcessorTypes.STRUCTURE_PROCESSOR.register("iceologer_cabin_armor_stand_processor", () -> IceologerCabinArmorStandProcessor.CODEC);
	//?} else {
	/*public static final RegistryEntry<StructureProcessorType<IllusionerShackItemFrameProcessor>> ILLUSIONER_SHACK_ITEM_FRAME_PROCESSOR = FriendsAndFoesStructureProcessorTypes.STRUCTURE_PROCESSOR.register("illusioner_shack_item_frame_processor", () -> () -> IllusionerShackItemFrameProcessor.CODEC);
	public static final RegistryEntry<StructureProcessorType<IceologerCabinArmorStandProcessor>> ICEOLOGER_CABIN_ARMOR_STAND_PROCESSOR = FriendsAndFoesStructureProcessorTypes.STRUCTURE_PROCESSOR.register("iceologer_cabin_armor_stand_processor", () -> () -> IceologerCabinArmorStandProcessor.CODEC);
	*///?}

	@Override
	public void init() {
	}
}