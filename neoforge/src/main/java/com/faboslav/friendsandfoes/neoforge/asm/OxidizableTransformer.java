package com.faboslav.friendsandfoes.neoforge.asm;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.block.Block;

public class OxidizableTransformer
{
	public static BiMap<Block, Block> createMutableMap(BiMap<Block, Block> immutableBiMap) {
		return HashBiMap.create(immutableBiMap);
	}
}