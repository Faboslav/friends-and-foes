package com.faboslav.friendsandfoes.util;

import com.faboslav.friendsandfoes.init.ModBlocks;
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
		.put(ModBlocks.WAXED_COPPER_BUTTON.get(), ModBlocks.COPPER_BUTTON.get())
		.put(ModBlocks.WAXED_EXPOSED_COPPER_BUTTON.get(), ModBlocks.EXPOSED_COPPER_BUTTON.get())
		.put(ModBlocks.WAXED_WEATHERED_COPPER_BUTTON.get(), ModBlocks.WEATHERED_COPPER_BUTTON.get())
		.put(ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get(), ModBlocks.OXIDIZED_COPPER_BUTTON.get())
		.put(ModBlocks.WAXED_LIGHTNING_ROD.get(), Blocks.LIGHTNING_ROD)
		.put(ModBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get(), ModBlocks.EXPOSED_LIGHTNING_ROD.get())
		.put(ModBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get(), ModBlocks.WEATHERED_LIGHTNING_ROD.get())
		.put(ModBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get(), ModBlocks.OXIDIZED_LIGHTNING_ROD.get())
		.build());

	public static final Supplier<BiMap<Block, Block>> UNWAXED_TO_WAXED_BUTTON_BLOCKS = Suppliers.memoize(() -> (BiMap) ImmutableBiMap.builder()
		.put(ModBlocks.COPPER_BUTTON.get(), ModBlocks.WAXED_COPPER_BUTTON.get())
		.put(ModBlocks.EXPOSED_COPPER_BUTTON.get(), ModBlocks.WAXED_EXPOSED_COPPER_BUTTON.get())
		.put(ModBlocks.WEATHERED_COPPER_BUTTON.get(), ModBlocks.WAXED_WEATHERED_COPPER_BUTTON.get())
		.put(ModBlocks.OXIDIZED_COPPER_BUTTON.get(), ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get())
		.put(Blocks.LIGHTNING_ROD, ModBlocks.WAXED_LIGHTNING_ROD.get())
		.put(ModBlocks.EXPOSED_LIGHTNING_ROD.get(), ModBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get())
		.put(ModBlocks.WEATHERED_LIGHTNING_ROD.get(), ModBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get())
		.put(ModBlocks.OXIDIZED_LIGHTNING_ROD.get(), ModBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get())
		.build());
}
