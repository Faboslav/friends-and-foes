package com.faboslav.friendsandfoes.forge.util;

import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;

public final class OxidizableBlocksRegistry
{
	public static void registerOxidizableBlockPair(Block before, Block after) {
		Oxidizable.OXIDATION_LEVEL_INCREASES.get().remove(before);
		Oxidizable.OXIDATION_LEVEL_INCREASES.get().put(before, after);
	}

	private OxidizableBlocksRegistry() {
	}
}