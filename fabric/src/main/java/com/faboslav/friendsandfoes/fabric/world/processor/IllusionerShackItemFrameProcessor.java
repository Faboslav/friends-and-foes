package com.faboslav.friendsandfoes.fabric.world.processor;

import com.faboslav.friendsandfoes.common.init.fabric.FriendsAndFoesStructureProcessorTypesImpl;
import com.faboslav.friendsandfoes.common.util.world.processor.IllusionerShackItemFrameProcessorHelper;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureEntityInfo;
import com.faboslav.friendsandfoes.common.world.processor.StructureEntityProcessor;
import org.jetbrains.annotations.Nullable;

/**
 * Inspired by use in Better Strongholds mod
 *
 * @author YUNGNICKYOUNG
 * <a href="https://github.com/YUNG-GANG/YUNGs-Better-Strongholds">https://github.com/YUNG-GANG/YUNGs-Better-Strongholds</a>
 */
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
		return FriendsAndFoesStructureProcessorTypesImpl.ILLUSIONER_SHACK_ITEM_FRAME_PROCESSOR.get();
	}
}
