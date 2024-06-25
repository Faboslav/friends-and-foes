package com.faboslav.friendsandfoes.forge.util;

import net.minecraft.block.Block;
import net.minecraft.item.HoneycombItem;

public final class WaxableBlocksRegistry
{
	public static void registerWaxableBlockPair(Block unwaxed, Block waxed) {
		HoneycombItem.UNWAXED_TO_WAXED_BLOCKS.get().remove(unwaxed);
		HoneycombItem.UNWAXED_TO_WAXED_BLOCKS.get().put(unwaxed, waxed);
	}

	private WaxableBlocksRegistry() {
	}
}