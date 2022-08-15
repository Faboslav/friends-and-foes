package com.faboslav.friendsandfoes.world.processor;

import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate.StructureEntityInfo;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;

/**
 * Originally from YUNG's API by.
 * YUNGNICKYOUNG (https://github.com/YUNG-GANG/YUNGs-API)
 */
public abstract class StructureEntityProcessor extends StructureProcessor
{
	public abstract StructureEntityInfo processEntity(
		ServerWorldAccess serverWorldAccess,
		BlockPos pos,
		BlockPos pivot,
		StructureEntityInfo originalBlockInfo,
		StructureEntityInfo currentBlockInfo,
		StructurePlacementData structurePlacementData
	);
}