package com.faboslav.friendsandfoes.util;

import com.faboslav.friendsandfoes.init.FriendsAndFoesBlocks;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.function.Supplier;

public final class OxidizableBlocksProvider
{
	public static final Supplier<BiMap<Block, Block>> OXIDIZABLE_BLOCKS = Suppliers.memoize(() -> (BiMap) ImmutableBiMap.builder()
		.build());

}