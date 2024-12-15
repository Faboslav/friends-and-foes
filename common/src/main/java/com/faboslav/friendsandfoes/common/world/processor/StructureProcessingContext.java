package com.faboslav.friendsandfoes.common.world.processor;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/**
 * Originally from YUNG's API by.
 * YUNGNICKYOUNG (https://github.com/YUNG-GANG/YUNGs-API)
 */
public record StructureProcessingContext(
	ServerLevelAccessor serverWorldAccess,
	StructurePlaceSettings structurePlacementData,
	BlockPos structurePiecePos,
	BlockPos structurePiecePivotPos,
	List<StructureTemplate.StructureEntityInfo> rawEntityInfos)
{
	public StructureProcessingContext(
		ServerLevelAccessor serverWorldAccess,
		StructurePlaceSettings structurePlacementData,
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