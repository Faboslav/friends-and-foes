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
		.put(FriendsAndFoesBlocks.COPPER_BUTTON.get(), FriendsAndFoesBlocks.EXPOSED_COPPER_BUTTON.get())
		.put(FriendsAndFoesBlocks.EXPOSED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.WEATHERED_COPPER_BUTTON.get())
		.put(FriendsAndFoesBlocks.WEATHERED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.OXIDIZED_COPPER_BUTTON.get())
		.put(Blocks.LIGHTNING_ROD, FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get())
		.put(FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get(), FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get())
		.put(FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get(), FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD.get())
		.build());

}
