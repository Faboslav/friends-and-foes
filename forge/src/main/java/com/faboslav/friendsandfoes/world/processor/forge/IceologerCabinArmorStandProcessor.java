package com.faboslav.friendsandfoes.world.processor.forge;

import com.faboslav.friendsandfoes.platform.forge.StructureEntityProcessorTypesImpl;
import com.faboslav.friendsandfoes.util.world.processor.IceologerCabinArmorStandProcessorHelper;
import com.mojang.serialization.Codec;
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
	public static final IceologerCabinArmorStandProcessor INSTANCE = new IceologerCabinArmorStandProcessor();
	public static final Codec<IceologerCabinArmorStandProcessor> CODEC = Codec.unit(() -> INSTANCE);

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
