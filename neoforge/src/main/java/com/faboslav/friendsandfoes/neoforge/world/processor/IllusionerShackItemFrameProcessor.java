package com.faboslav.friendsandfoes.neoforge.world.processor;

import com.faboslav.friendsandfoes.common.util.world.processor.IllusionerShackItemFrameProcessorHelper;
import com.faboslav.friendsandfoes.neoforge.platform.ProcessorTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

//? if <26.2 {
/*import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
 *///?}

//? if >=26.2 {
public final class IllusionerShackItemFrameProcessor implements StructureProcessor
//?} else {
/*public final class IllusionerShackItemFrameProcessor extends StructureProcessor
*///?}
{
	public static final MapCodec<IllusionerShackItemFrameProcessor> CODEC = MapCodec.unit(IllusionerShackItemFrameProcessor::new);

	private IllusionerShackItemFrameProcessor() {
	}

	@Override
	public StructureTemplate.StructureEntityInfo processEntity(
		LevelReader world,
		BlockPos seedPos,
		StructureTemplate.StructureEntityInfo rawEntityInfo,
		StructureTemplate.StructureEntityInfo entityInfo,
		StructurePlaceSettings placementSettings,
		StructureTemplate template
	) {
		return IllusionerShackItemFrameProcessorHelper.processEntity(
			entityInfo,
			placementSettings
		);
	}

	@Nullable
	@Override
	//? if >=26.2 {
	public StructureTemplate.StructureBlockInfo processBlock(
		LevelReader world,
		BlockPos pos,
		BlockPos pivot,
		BlockPos templateRelativePos,
		StructureTemplate.StructureBlockInfo globalEntityInfo,
		StructurePlaceSettings data
	)
	//?} else {
	/*public StructureTemplate.StructureBlockInfo processBlock(
		LevelReader world,
		BlockPos pos,
		BlockPos pivot,
		StructureTemplate.StructureBlockInfo localEntityInfo,
		StructureTemplate.StructureBlockInfo globalEntityInfo,
		StructurePlaceSettings data
	)
	*///?}
	{
		return globalEntityInfo;
	}

	@Override
	//? if >=26.2 {
	public MapCodec<? extends StructureProcessor> codec() {
		return ProcessorTypes.ILLUSIONER_SHACK_ITEM_FRAME_PROCESSOR.get();
	}
	//?} else {
	/*protected StructureProcessorType<?> getType() {
		return ProcessorTypes.ILLUSIONER_SHACK_ITEM_FRAME_PROCESSOR.get();
	}
	*///?}
}