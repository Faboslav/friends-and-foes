package com.faboslav.friendsandfoes.util;

import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.HoneycombItem;

public final class OxidizableBlockHelper
{
	private OxidizableBlockHelper() {
	}

	public static void registerOxidizableBlockPair(Block less, Block more) {
		Oxidizable.OXIDATION_LEVEL_INCREASES.get().put(less, more);
	}

	public static void registerWaxableBlockPair(Block unwaxed, Block waxed) {
		HoneycombItem.UNWAXED_TO_WAXED_BLOCKS.get().put(unwaxed, waxed);
	}
}
