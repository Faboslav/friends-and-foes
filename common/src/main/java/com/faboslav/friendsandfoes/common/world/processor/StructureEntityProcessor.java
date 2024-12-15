package com.faboslav.friendsandfoes.common.world.processor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureEntityInfo;

/**
 * Originally from YUNG's API by.
 * YUNGNICKYOUNG (https://github.com/YUNG-GANG/YUNGs-API)
 */
public abstract class StructureEntityProcessor extends StructureProcessor
{
	public abstract StructureEntityInfo processEntity(
		ServerLevelAccessor serverWorldAccess,
		BlockPos pos,
		BlockPos pivot,
		StructureEntityInfo originalBlockInfo,
		StructureEntityInfo currentBlockInfo,
		StructurePlaceSettings structurePlacementData
	);
}