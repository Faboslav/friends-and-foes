package com.faboslav.friendsandfoes.fabric.world.processor;

import com.faboslav.friendsandfoes.common.init.fabric.FriendsAndFoesStructureProcessorTypesImpl;
import com.faboslav.friendsandfoes.common.util.world.processor.IceologerCabinArmorStandProcessorHelper;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import com.faboslav.friendsandfoes.common.world.processor.StructureEntityProcessor;
import org.jetbrains.annotations.Nullable;

/**
 * Inspired by use in Better Strongholds mod
 *
 * @author YUNGNICKYOUNG
 * <a href="https://github.com/YUNG-GANG/YUNGs-Better-Strongholds">https://github.com/YUNG-GANG/YUNGs-Better-Strongholds</a>
 */
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
		return FriendsAndFoesStructureProcessorTypesImpl.ICEOLOGER_CABIN_ARMOR_STAND_PROCESSOR.get();
	}
}
