package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.init.registry.RegistryEntry;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;

import java.util.HashSet;
import java.util.Set;

/**
 * @see BlockEntityType
 */
public final class FriendsAndFoesBlockEntityTypes
{
	public static void lateInit() {
		Set<Block> beehiveBlocks = new HashSet<>(BlockEntityType.BEEHIVE.blocks);
		Set<Block> modBeehiveBlocks = FriendsAndFoesBlocks.BLOCKS.stream()
			.map(RegistryEntry::get)
			.filter(item -> item instanceof BeehiveBlock)
			.map(block -> (BeehiveBlock) block).collect(ImmutableSet.toImmutableSet());
		beehiveBlocks.addAll(modBeehiveBlocks);
		BlockEntityType.BEEHIVE.blocks = beehiveBlocks;
	}

	private FriendsAndFoesBlockEntityTypes() {
	}
}
