package com.faboslav.friendsandfoes.forge.util;

import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;

public final class OxidizableBlocksRegistry
{
	public static void registerOxidizableBlockPair(Block before, Block after) {
		Oxidizable.OXIDATION_LEVEL_INCREASES.get().put(before, after);
		Oxidizable.OXIDATION_LEVEL_DECREASES.get().put(after, before);
	}

	private OxidizableBlocksRegistry() {
	}
}