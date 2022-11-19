package com.faboslav.friendsandfoes.world.processor.quilt;

import com.faboslav.friendsandfoes.platform.fabric.StructureEntityProcessorTypesImpl;
import com.faboslav.friendsandfoes.util.world.processor.IllusionerShackItemFrameProcessorHelper;
import com.faboslav.friendsandfoes.world.processor.StructureEntityProcessor;
import com.mojang.serialization.Codec;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplate.StructureEntityInfo;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

/**
 * Inspired by use in Better Strongholds mod
 *
 * @author YUNGNICKYOUNG
 * <a href="https://github.com/YUNG-GANG/YUNGs-Better-Strongholds">https://github.com/YUNG-GANG/YUNGs-Better-Strongholds</a>
 */
public final class IllusionerShackItemFrameProcessor extends StructureEntityProcessor
{
	public static final IllusionerShackItemFrameProcessor INSTANCE = new IllusionerShackItemFrameProcessor();
	public static final Codec<IllusionerShackItemFrameProcessor> CODEC = Codec.unit(() -> INSTANCE);

	@Override
	public StructureEntityInfo processEntity(
		ServerWorldAccess serverWorldAccess,
		BlockPos structurePiecePos,
		BlockPos structurePieceBottomCenterPos,
		StructureEntityInfo localEntityInfo,
		StructureEntityInfo globalEntityInfo,
		StructurePlacementData structurePlacementData
	) {
		return IllusionerShackItemFrameProcessorHelper.processEntity(
			globalEntityInfo,
			structurePlacementData
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
		return StructureEntityProcessorTypesImpl.ILLUSIONER_SHACK_ITEM_FRAME_PROCESSOR;
	}
}
