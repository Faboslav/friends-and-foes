package com.faboslav.friendsandfoes.common.world.processor;

import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;

import java.util.ArrayList;
import java.util.List;

/**
 * Originally from YUNG's API by.
 * YUNGNICKYOUNG (https://github.com/YUNG-GANG/YUNGs-API)
 */
public record StructureProcessingContext(
	ServerWorldAccess serverWorldAccess,
	StructurePlacementData structurePlacementData,
	BlockPos structurePiecePos,
	BlockPos structurePiecePivotPos,
	List<StructureTemplate.StructureEntityInfo> rawEntityInfos)
{
	public StructureProcessingContext(
		ServerWorldAccess serverWorldAccess,
		StructurePlacementData structurePlacementData,
		BlockPos structurePiecePos,
		BlockPos structurePiecePivotPos,
		List<StructureTemplate.StructureEntityInfo> rawEntityInfos
	) {
		this.serverWorldAccess = serverWorldAccess;
		this.structurePlacementData = structurePlacementData;
		this.structurePiecePos = structurePiecePos;
		this.structurePiecePivotPos = structurePiecePivotPos;
		this.rawEntityInfos = Util.make(() -> {
			List<StructureTemplate.StructureEntityInfo> list = new ArrayList<>(rawEntityInfos.size());
			rawEntityInfos.forEach((entityInfo) ->
				list.add(new StructureTemplate.StructureEntityInfo(entityInfo.pos, entityInfo.blockPos, entityInfo.nbt)));
			return list;
		});
	}
}