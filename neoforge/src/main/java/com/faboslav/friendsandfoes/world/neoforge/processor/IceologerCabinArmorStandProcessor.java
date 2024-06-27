package com.faboslav.friendsandfoes.world.neoforge.processor;

import com.faboslav.friendsandfoes.platform.neoforge.StructureEntityProcessorTypesImpl;
import com.faboslav.friendsandfoes.util.world.processor.IceologerCabinArmorStandProcessorHelper;
import com.faboslav.friendsandfoes.world.processor.IllusionerShackBrewingStandProcessor;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
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
		WorldView world,
		BlockPos seedPos,
		StructureTemplate.StructureEntityInfo rawEntityInfo,
		StructureTemplate.StructureEntityInfo entityInfo,
		StructurePlacementData placementSettings,
		StructureTemplate template
	) {
		return IceologerCabinArmorStandProcessorHelper.processEntity(
			entityInfo,
			placementSettings
		);
	}

	@Nullable
	@Override
	public StructureTemplate.StructureBlockInfo process(
		WorldView world,
		BlockPos pos,
		BlockPos pivot,
		StructureTemplate.StructureBlockInfo localEntityInfo,
		StructureTemplate.StructureBlockInfo globalEntityInfo,
		StructurePlacementData data
	) {
		return globalEntityInfo;
	}

	@Override
	protected StructureProcessorType<?> getType() {
		return StructureEntityProcessorTypesImpl.ICEOLOGER_CABIN_ARMOR_STAND_PROCESSOR;
	}
}
