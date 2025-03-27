package com.faboslav.friendsandfoes.neoforge.world.processor;

import com.faboslav.friendsandfoes.common.util.world.processor.IceologerCabinArmorStandProcessorHelper;
import com.faboslav.friendsandfoes.neoforge.platform.ProcessorTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

/**
 * Inspired by use in Better Strongholds mod
 *
 * @author YUNGNICKYOUNG
 * <a href="https://github.com/YUNG-GANG/YUNGs-Better-Strongholds">https://github.com/YUNG-GANG/YUNGs-Better-Strongholds</a>
 */
public final class IceologerCabinArmorStandProcessor extends StructureProcessor
{
	public static final MapCodec<IceologerCabinArmorStandProcessor> CODEC = MapCodec.unit(IceologerCabinArmorStandProcessor::new);

	private IceologerCabinArmorStandProcessor() {
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
		return IceologerCabinArmorStandProcessorHelper.processEntity(
			entityInfo,
			placementSettings
		);
	}

	@Nullable
	@Override
	public StructureTemplate.StructureBlockInfo processBlock(
		LevelReader world,
		BlockPos pos,
		BlockPos pivot,
		StructureTemplate.StructureBlockInfo localEntityInfo,
		StructureTemplate.StructureBlockInfo globalEntityInfo,
		StructurePlaceSettings data
	) {
		return globalEntityInfo;
	}

	@Override
	protected StructureProcessorType<?> getType() {
		return ProcessorTypes.ILLUSIONER_SHACK_ITEM_FRAME_PROCESSOR.get();
	}
}