package com.faboslav.friendsandfoes.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;

import java.util.HashSet;
import java.util.Set;

/**
 * @see BlockEntityType
 */
public final class ModBlockEntityTypes
{
	private static final Set<Block> BEEHIVE_BLOCKS = ImmutableList.of(
		ModBlocks.ACACIA_BEEHIVE.get(),
		ModBlocks.BIRCH_BEEHIVE.get(),
		ModBlocks.CRIMSON_BEEHIVE.get(),
		ModBlocks.DARK_OAK_BEEHIVE.get(),
		ModBlocks.JUNGLE_BEEHIVE.get(),
		ModBlocks.MANGROVE_BEEHIVE.get(),
		ModBlocks.SPRUCE_BEEHIVE.get(),
		ModBlocks.WARPED_BEEHIVE.get()
	).stream().collect(ImmutableSet.toImmutableSet());

	public static void init() {
		expandBeehive();
	}

	private static void expandBeehive() {
		Set<Block> beehiveBlocks = new HashSet<>();
		beehiveBlocks.addAll(BlockEntityType.BEEHIVE.blocks);
		beehiveBlocks.addAll(BEEHIVE_BLOCKS);
		BlockEntityType.BEEHIVE.blocks = beehiveBlocks;
	}

	private ModBlockEntityTypes() {
	}
}
