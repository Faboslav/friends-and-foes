package com.faboslav.friendsandfoes.fabric.world.processor;

import com.faboslav.friendsandfoes.common.util.world.processor.IllusionerShackItemFrameProcessorHelper;
import com.faboslav.friendsandfoes.common.world.processor.StructureEntityProcessor;
import com.faboslav.friendsandfoes.fabric.platform.ProcessorTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureEntityInfo;
import org.jetbrains.annotations.Nullable;

//? if <26.2 {
/*import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
 *///?}

public final class IllusionerShackItemFrameProcessor extends StructureEntityProcessor
{
	public static final MapCodec<IllusionerShackItemFrameProcessor> CODEC = MapCodec.unit(IllusionerShackItemFrameProcessor::new);

	private IllusionerShackItemFrameProcessor() {
	}

	@Override
	public StructureEntityInfo processEntity(
		ServerLevelAccessor serverWorldAccess,
		BlockPos structurePiecePos,
		BlockPos structurePieceBottomCenterPos,
		StructureEntityInfo localEntityInfo,
		StructureEntityInfo globalEntityInfo,
		StructurePlaceSettings structurePlacementData
	) {
		return IllusionerShackItemFrameProcessorHelper.processEntity(
			globalEntityInfo,
			structurePlacementData
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
	public MapCodec<? extends net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor> codec() {
		return ProcessorTypes.ILLUSIONER_SHACK_ITEM_FRAME_PROCESSOR.get();
	}
	//?} else {
	/*protected StructureProcessorType<?> getType() {
		return ProcessorTypes.ILLUSIONER_SHACK_ITEM_FRAME_PROCESSOR.get();
	}
	*///?}
}