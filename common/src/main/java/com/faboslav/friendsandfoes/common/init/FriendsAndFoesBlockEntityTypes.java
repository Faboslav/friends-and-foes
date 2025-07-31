package com.faboslav.friendsandfoes.common.init;

import com.google.common.collect.ImmutableSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * @see BlockEntityType
 */
public final class FriendsAndFoesBlockEntityTypes
{
	public static void earlyInit() {
		//BeaconBlockEntity.BEACON_EFFECTS.get(0).add(FriendsAndFoesStatusEffects.REACH.holder());
		//BeaconBlockEntity.BEACON_EFFECTS.add(List.of(FriendsAndFoesStatusEffects.BOAT_SPEED.holder()));
	}

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
