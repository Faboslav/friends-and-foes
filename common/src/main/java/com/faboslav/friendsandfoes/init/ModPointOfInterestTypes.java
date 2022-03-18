package com.faboslav.friendsandfoes.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Set;

/**
 * @see PointOfInterestType
 */
public final class ModPointOfInterestTypes
{
	public static final Set<BlockState> FRIENDS_AND_FOES_BEEHIVE_STATES = ImmutableList.of(Blocks.BEEHIVE, ModBlocks.ACACIA_BEEHIVE.get(), ModBlocks.BIRCH_BEEHIVE.get(), ModBlocks.CRIMSON_BEEHIVE.get(), ModBlocks.DARK_OAK_BEEHIVE.get(), ModBlocks.JUNGLE_BEEHIVE.get(), ModBlocks.SPRUCE_BEEHIVE.get(), ModBlocks.WARPED_BEEHIVE.get()).stream().flatMap((block) -> block.getStateManager().getStates().stream()).collect(ImmutableSet.toImmutableSet());

	public static void init() {
		expandBeehive();
	}

	private static void expandBeehive() {
		PointOfInterestType.BEEHIVE.ticketCount = 1;
		PointOfInterestType.BEEHIVE.blockStates = FRIENDS_AND_FOES_BEEHIVE_STATES;
		FRIENDS_AND_FOES_BEEHIVE_STATES.forEach((state) -> {
			PointOfInterestType.BLOCK_STATE_TO_POINT_OF_INTEREST_TYPE.put(state, PointOfInterestType.BEEHIVE);
		});
	}

	private ModPointOfInterestTypes() {}
}
