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
public final class FriendsAndFoesBlockEntityTypes
{
	private static final Set<Block> BEEHIVE_BLOCKS = ImmutableList.of(
		FriendsAndFoesBlocks.ACACIA_BEEHIVE.get(),
		FriendsAndFoesBlocks.BIRCH_BEEHIVE.get(),
		FriendsAndFoesBlocks.CRIMSON_BEEHIVE.get(),
		FriendsAndFoesBlocks.DARK_OAK_BEEHIVE.get(),
		FriendsAndFoesBlocks.JUNGLE_BEEHIVE.get(),
		FriendsAndFoesBlocks.MANGROVE_BEEHIVE.get(),
		FriendsAndFoesBlocks.SPRUCE_BEEHIVE.get(),
		FriendsAndFoesBlocks.WARPED_BEEHIVE.get()
	).stream().collect(ImmutableSet.toImmutableSet());

	public static void postInit() {
		Set<Block> beehiveBlocks = new HashSet<>();
		beehiveBlocks.addAll(BlockEntityType.BEEHIVE.blocks);
		beehiveBlocks.addAll(BEEHIVE_BLOCKS);
		BlockEntityType.BEEHIVE.blocks = beehiveBlocks;
	}

	private FriendsAndFoesBlockEntityTypes() {
	}
}
