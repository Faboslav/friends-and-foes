package com.faboslav.friendsandfoes.util;

import com.faboslav.friendsandfoes.init.FriendsAndFoesBlocks;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.function.Supplier;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class WaxableBlocksMap
{
	public static final Supplier<BiMap<Block, Block>> WAXED_TO_UNWAXED_BLOCKS = Suppliers.memoize(() -> (BiMap) ImmutableBiMap.builder()
		.put(FriendsAndFoesBlocks.WAXED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.COPPER_BUTTON.get())
		.put(FriendsAndFoesBlocks.WAXED_EXPOSED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.EXPOSED_COPPER_BUTTON.get())
		.put(FriendsAndFoesBlocks.WAXED_WEATHERED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.WEATHERED_COPPER_BUTTON.get())
		.put(FriendsAndFoesBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.OXIDIZED_COPPER_BUTTON.get())
		.put(FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get(), Blocks.LIGHTNING_ROD)
		.put(FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get(), FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get())
		.put(FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get(), FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get())
		.put(FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get(), FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD.get())
		.build());

	public static final Supplier<BiMap<Block, Block>> UNWAXED_TO_WAXED_BUTTON_BLOCKS = Suppliers.memoize(() -> (BiMap) ImmutableBiMap.builder()
		.put(FriendsAndFoesBlocks.COPPER_BUTTON.get(), FriendsAndFoesBlocks.WAXED_COPPER_BUTTON.get())
		.put(FriendsAndFoesBlocks.EXPOSED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.WAXED_EXPOSED_COPPER_BUTTON.get())
		.put(FriendsAndFoesBlocks.WEATHERED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.WAXED_WEATHERED_COPPER_BUTTON.get())
		.put(FriendsAndFoesBlocks.OXIDIZED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get())
		.put(Blocks.LIGHTNING_ROD, FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get())
		.put(FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get(), FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get())
		.put(FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get(), FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get())
		.put(FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD.get(), FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get())
		.build());
}
