package com.faboslav.friendsandfoes.fabric.world.processor;

import com.faboslav.friendsandfoes.common.util.world.processor.IceologerCabinArmorStandProcessorHelper;
import com.faboslav.friendsandfoes.common.world.processor.StructureEntityProcessor;
import com.faboslav.friendsandfoes.fabric.platform.ProcessorTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

//? if <26.2 {
/*import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
 *///?}

public final class IceologerCabinArmorStandProcessor extends StructureEntityProcessor
{
	public static final MapCodec<IceologerCabinArmorStandProcessor> CODEC = MapCodec.unit(IceologerCabinArmorStandProcessor::new);

	private IceologerCabinArmorStandProcessor() {
	}

	@Override
	public StructureTemplate.StructureEntityInfo processEntity(
		ServerLevelAccessor serverWorldAccess,
		BlockPos structurePiecePos,
		BlockPos structurePieceBottomCenterPos,
		StructureTemplate.StructureEntityInfo localEntityInfo,
		StructureTemplate.StructureEntityInfo globalEntityInfo,
		StructurePlaceSettings structurePlacementData
	) {
		return IceologerCabinArmorStandProcessorHelper.processEntity(
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
		return ProcessorTypes.ICEOLOGER_CABIN_ARMOR_STAND_PROCESSOR.get();
	}
	//?} else {
	/*protected StructureProcessorType<?> getType() {
		return ProcessorTypes.ICEOLOGER_CABIN_ARMOR_STAND_PROCESSOR.get();
	}
	*///?}
}