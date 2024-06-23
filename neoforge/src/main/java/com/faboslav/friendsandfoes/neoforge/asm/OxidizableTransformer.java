package com.faboslav.friendsandfoes.neoforge.asm;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.util.OxidizableBlocksProvider;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;

public class OxidizableTransformer
{
	public static ImmutableBiMap.Builder<Block, Block> injectCustomOxidizableBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
		FriendsAndFoes.getLogger().info("OXIDIZABLE_BLOCKS");
		FriendsAndFoes.getLogger().info("OXIDIZABLE_BLOCKS");
		FriendsAndFoes.getLogger().info("OXIDIZABLE_BLOCKS");
		FriendsAndFoes.getLogger().info("OXIDIZABLE_BLOCKS");
		FriendsAndFoes.getLogger().info("OXIDIZABLE_BLOCKS");
		FriendsAndFoes.getLogger().info("OXIDIZABLE_BLOCKS");
		FriendsAndFoes.getLogger().info("OXIDIZABLE_BLOCKS");
		FriendsAndFoes.getLogger().info("OXIDIZABLE_BLOCKS");
		FriendsAndFoes.getLogger().info("OXIDIZABLE_BLOCKS");
		return builder.putAll(OxidizableBlocksProvider.OXIDIZABLE_BLOCKS.get());
	}
}