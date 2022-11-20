package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

import java.util.function.Supplier;

/**
 * @see PointOfInterestTypes
 */
public final class FriendsAndFoesPointOfInterestTypes
{
	public final static Supplier<PointOfInterestType> ACACIA_BEEHIVE;
	public final static Supplier<PointOfInterestType> BIRCH_BEEHIVE;
	public final static Supplier<PointOfInterestType> CRIMSON_BEEHIVE;
	public final static Supplier<PointOfInterestType> DARK_OAK_BEEHIVE;
	public final static Supplier<PointOfInterestType> JUNGLE_BEEHIVE;
	public final static Supplier<PointOfInterestType> MANGROVE_BEEHIVE;
	public final static Supplier<PointOfInterestType> SPRUCE_BEEHIVE;
	public final static Supplier<PointOfInterestType> WARPED_BEEHIVE;

	static {
		ACACIA_BEEHIVE = RegistryHelper.registerPointOfInterestType("acacia_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.ACACIA_BEEHIVE.get()), 1, 1));
		BIRCH_BEEHIVE = RegistryHelper.registerPointOfInterestType("birch_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.BIRCH_BEEHIVE.get()), 1, 1));
		CRIMSON_BEEHIVE = RegistryHelper.registerPointOfInterestType("crimson_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.CRIMSON_BEEHIVE.get()), 1, 1));
		DARK_OAK_BEEHIVE = RegistryHelper.registerPointOfInterestType("dark_oak_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.DARK_OAK_BEEHIVE.get()), 1, 1));
		JUNGLE_BEEHIVE = RegistryHelper.registerPointOfInterestType("jungle_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.JUNGLE_BEEHIVE.get()), 1, 1));
		MANGROVE_BEEHIVE = RegistryHelper.registerPointOfInterestType("mangrove_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.MANGROVE_BEEHIVE.get()), 1, 1));
		SPRUCE_BEEHIVE = RegistryHelper.registerPointOfInterestType("spruce_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.SPRUCE_BEEHIVE.get()), 1, 1));
		WARPED_BEEHIVE = RegistryHelper.registerPointOfInterestType("warped_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.WARPED_BEEHIVE.get()), 1, 1));
	}

	public static void init() {
	}

	public static void postInit() {
		fillMissingPointOfInterestMapValues();
	}

	private static void fillMissingPointOfInterestMapValues() {
		var acaciaBeehiveStates = PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.ACACIA_BEEHIVE.get());
		PointOfInterestTypes.POI_STATES_TO_TYPE.addAll(acaciaBeehiveStates);
		acaciaBeehiveStates.forEach((state) -> {
			PointOfInterestTypes.POI_STATES_TO_TYPE.put(
				state,
				Registry.POINT_OF_INTEREST_TYPE.getEntry(
					RegistryKey.of(
						Registry.POINT_OF_INTEREST_TYPE_KEY, FriendsAndFoes.makeID("acacia_beehive")
					)
				).get()
			);
		});

		var birchBeehiveStates = PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.BIRCH_BEEHIVE.get());
		PointOfInterestTypes.POI_STATES.addAll(birchBeehiveStates);
		birchBeehiveStates.forEach((state) -> {
			PointOfInterestTypes.POI_STATES_TO_TYPE.put(
				state,
				Registry.POINT_OF_INTEREST_TYPE.getEntry(
					RegistryKey.of(
						Registry.POINT_OF_INTEREST_TYPE_KEY, FriendsAndFoes.makeID("birch_beehive")
					)
				).get()
			);
		});

		var crimsonBeehiveStates = PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.CRIMSON_BEEHIVE.get());
		PointOfInterestTypes.POI_STATES.addAll(crimsonBeehiveStates);
		crimsonBeehiveStates.forEach((state) -> {
			PointOfInterestTypes.POI_STATES_TO_TYPE.put(
				state,
				RegistryKeys.POINT_OF_INTEREST_TYPE.getEntry(
					RegistryKey.of(
						RegistryKeys.POINT_OF_INTEREST_TYPE, FriendsAndFoes.makeID("crimson_beehive")
					)
				).get()
			);
		});

		var darkOakBeehiveStates = PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.DARK_OAK_BEEHIVE.get());
		PointOfInterestTypes.POI_STATES.addAll(darkOakBeehiveStates);
		darkOakBeehiveStates.forEach((state) -> {
			PointOfInterestTypes.POI_STATES_TO_TYPE.put(
				state,
				Registry.POINT_OF_INTEREST_TYPE.getEntry(
					RegistryKey.of(
						Registry.POINT_OF_INTEREST_TYPE_KEY, FriendsAndFoes.makeID("dark_oak_beehive")
					)
				).get()
			);
		});

		var jungleBeehiveStates = PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.JUNGLE_BEEHIVE.get());
		PointOfInterestTypes.POI_STATES.addAll(jungleBeehiveStates);
		jungleBeehiveStates.forEach((state) -> {
			PointOfInterestTypes.POI_STATES_TO_TYPE.put(
				state,
				Registry.POINT_OF_INTEREST_TYPE.getEntry(
					RegistryKey.of(
						Registry.POINT_OF_INTEREST_TYPE_KEY, FriendsAndFoes.makeID("jungle_beehive")
					)
				).get()
			);
		});

		var mangroveBeehiveStates = PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.MANGROVE_BEEHIVE.get());
		PointOfInterestTypes.POI_STATES.addAll(mangroveBeehiveStates);
		mangroveBeehiveStates.forEach((state) -> {
			PointOfInterestTypes.POI_STATES_TO_TYPE.put(
				state,
				Registry.POINT_OF_INTEREST_TYPE.getEntry(
					RegistryKey.of(
						Registry.POINT_OF_INTEREST_TYPE_KEY, FriendsAndFoes.makeID("mangrove_beehive")
					)
				).get()
			);
		});

		var spruceBeehiveStates = PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.SPRUCE_BEEHIVE.get());
		PointOfInterestTypes.POI_STATES.addAll(spruceBeehiveStates);
		spruceBeehiveStates.forEach((state) -> {
			PointOfInterestTypes.POI_STATES_TO_TYPE.put(
				state,
				Registry.POINT_OF_INTEREST_TYPE.getEntry(
					RegistryKey.of(
						Registry.POINT_OF_INTEREST_TYPE_KEY, FriendsAndFoes.makeID("spruce_beehive")
					)
				).get()
			);
		});

		var warpedBeehiveStates = PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.WARPED_BEEHIVE.get());
		PointOfInterestTypes.POI_STATES.addAll(warpedBeehiveStates);
		warpedBeehiveStates.forEach((state) -> {
			PointOfInterestTypes.POI_STATES_TO_TYPE.put(
				state,
				Registry.POINT_OF_INTEREST_TYPE.getEntry(
					RegistryKey.of(
						Registry.POINT_OF_INTEREST_TYPE_KEY, FriendsAndFoes.makeID("warped_beehive")
					)
				).get()
			);
		});
	}

	private FriendsAndFoesPointOfInterestTypes() {
	}
}
