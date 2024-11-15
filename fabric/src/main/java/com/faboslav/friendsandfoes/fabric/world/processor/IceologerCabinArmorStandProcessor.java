package com.faboslav.friendsandfoes.fabric.world.processor;

import com.faboslav.friendsandfoes.common.init.fabric.FriendsAndFoesStructureProcessorTypesImpl;
import com.faboslav.friendsandfoes.common.util.world.processor.IceologerCabinArmorStandProcessorHelper;
import com.mojang.serialization.Codec;
import com.faboslav.friendsandfoes.common.world.processor.StructureEntityProcessor;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
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
public final class IceologerCabinArmorStandProcessor extends StructureEntityProcessor
{
	public static final IceologerCabinArmorStandProcessor INSTANCE = new IceologerCabinArmorStandProcessor();
	public static final Codec<IceologerCabinArmorStandProcessor> CODEC = Codec.unit(() -> INSTANCE);

	@Override
	public StructureTemplate.StructureEntityInfo processEntity(
		ServerWorldAccess serverWorldAccess,
		BlockPos structurePiecePos,
		BlockPos structurePieceBottomCenterPos,
		StructureTemplate.StructureEntityInfo localEntityInfo,
		StructureTemplate.StructureEntityInfo globalEntityInfo,
		StructurePlacementData structurePlacementData
	) {
		return IceologerCabinArmorStandProcessorHelper.processEntity(
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
		return FriendsAndFoesStructureProcessorTypesImpl.ICEOLOGER_CABIN_ARMOR_STAND_PROCESSOR.get();
	}
}
