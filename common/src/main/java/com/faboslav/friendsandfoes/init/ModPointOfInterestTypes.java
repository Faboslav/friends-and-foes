package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BlockState;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.HashSet;
import java.util.Set;

/**
 * @see PointOfInterestType
 */
public final class ModPointOfInterestTypes
{
	private static final Set<BlockState> BEEHIVE_STATES = ImmutableList.of(ModBlocks.ACACIA_BEEHIVE.get(), ModBlocks.BIRCH_BEEHIVE.get(), ModBlocks.CRIMSON_BEEHIVE.get(), ModBlocks.DARK_OAK_BEEHIVE.get(), ModBlocks.JUNGLE_BEEHIVE.get(), ModBlocks.SPRUCE_BEEHIVE.get(), ModBlocks.WARPED_BEEHIVE.get()).stream().flatMap((block) -> block.getStateManager().getStates().stream()).collect(ImmutableSet.toImmutableSet());

	public static void init() {
		expandBeehive();
	}

	private static void expandBeehive() {
		Set<BlockState> beehiveStates = new HashSet<>();
		beehiveStates.addAll(PointOfInterestType.BEEHIVE.blockStates);
		beehiveStates.addAll(BEEHIVE_STATES);

		PointOfInterestType.BEEHIVE.blockStates = beehiveStates;
		BEEHIVE_STATES.forEach((state) -> {
			PointOfInterestType.BLOCK_STATE_TO_POINT_OF_INTEREST_TYPE.put(state, PointOfInterestType.BEEHIVE);
		});

		if (FriendsAndFoes.getConfig().enableBeekeeperVillagerProfession) {
			PointOfInterestType.BEEHIVE.ticketCount = 1;
		}
	}

	private ModPointOfInterestTypes() {
	}
}
