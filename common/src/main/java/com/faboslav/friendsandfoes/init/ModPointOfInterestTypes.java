package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

import java.util.HashSet;
import java.util.Set;

/**
 * @see PointOfInterestType
 */
public final class ModPointOfInterestTypes
{
	private static final Set<BlockState> BEEHIVE_STATES = ImmutableList.of(
			ModBlocks.ACACIA_BEEHIVE.get(),
			ModBlocks.BIRCH_BEEHIVE.get(),
			ModBlocks.CRIMSON_BEEHIVE.get(),
			ModBlocks.DARK_OAK_BEEHIVE.get(),
			ModBlocks.JUNGLE_BEEHIVE.get(),
			ModBlocks.MANGROVE_BEEHIVE.get(),
			ModBlocks.SPRUCE_BEEHIVE.get(),
			ModBlocks.WARPED_BEEHIVE.get()
		).stream()
		.flatMap((block) -> block.getStateManager().getStates().stream())
		.collect(ImmutableSet.toImmutableSet());

	public static void init() {
		expandBeehive();
	}

	private static void expandBeehive() {
		var beehiveRegistryEntry = Registry.POINT_OF_INTEREST_TYPE.getEntry(PointOfInterestTypes.BEEHIVE).get();
		var beehivePointOfType = beehiveRegistryEntry.value();

		Set<BlockState> beehiveStates = new HashSet<>();
		beehiveStates.addAll(beehivePointOfType.blockStates);
		beehiveStates.addAll(BEEHIVE_STATES);

		beehivePointOfType.blockStates = beehiveStates;
		BEEHIVE_STATES.forEach((state) -> {
			PointOfInterestTypes.POI_STATES_TO_TYPE.put(state, beehiveRegistryEntry);
			PointOfInterestTypes.POI_STATES.add(state);
		});

		if (FriendsAndFoes.getConfig().enableBeekeeperVillagerProfession) {
			beehivePointOfType.ticketCount = 1;
		}
	}

	private ModPointOfInterestTypes() {
	}
}
