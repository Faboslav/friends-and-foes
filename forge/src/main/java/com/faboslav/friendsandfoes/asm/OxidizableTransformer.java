package com.faboslav.friendsandfoes.asm;

import com.faboslav.friendsandfoes.util.OxidizableBlocksProvider;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;

public class OxidizableTransformer
{
	public static ImmutableBiMap.Builder<Block, Block> injectCustomOxidizableBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
		return builder.putAll(OxidizableBlocksProvider.OXIDIZABLE_BLOCKS.get());
	}
}