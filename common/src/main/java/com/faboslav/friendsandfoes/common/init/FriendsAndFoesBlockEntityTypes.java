package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import com.google.common.collect.ImmutableSet;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * @see BlockEntityType
 */
public final class FriendsAndFoesBlockEntityTypes
{
	public static void lateInit() {
		Set<Block> beehiveBlocks = new HashSet<>(BlockEntityType.BEEHIVE.validBlocks);
		Set<Block> modBeehiveBlocks = FriendsAndFoesBlocks.BLOCKS.stream()
			.map(RegistryEntry::get)
			.filter(item -> item instanceof BeehiveBlock)
			.map(block -> (BeehiveBlock) block).collect(ImmutableSet.toImmutableSet());
		beehiveBlocks.addAll(modBeehiveBlocks);
		BlockEntityType.BEEHIVE.validBlocks = beehiveBlocks;
	}

	private FriendsAndFoesBlockEntityTypes() {
	}
}
