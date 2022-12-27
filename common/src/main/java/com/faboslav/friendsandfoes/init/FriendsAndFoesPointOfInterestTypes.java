package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * @see PointOfInterestTypes
 */
public final class FriendsAndFoesPointOfInterestTypes
{
	private static final HashMap<String, Supplier<PointOfInterestType>> REGISTERED_POINT_OF_INTEREST_TYPES;

	public final static Supplier<PointOfInterestType> ACACIA_BEEHIVE;
	public final static Supplier<PointOfInterestType> BIRCH_BEEHIVE;
	public final static Supplier<PointOfInterestType> CRIMSON_BEEHIVE;
	public final static Supplier<PointOfInterestType> DARK_OAK_BEEHIVE;
	public final static Supplier<PointOfInterestType> JUNGLE_BEEHIVE;
	public final static Supplier<PointOfInterestType> MANGROVE_BEEHIVE;
	public final static Supplier<PointOfInterestType> SPRUCE_BEEHIVE;
	public final static Supplier<PointOfInterestType> WARPED_BEEHIVE;
	public static final Supplier<PointOfInterestType> EXPOSED_LIGHTNING_ROD;
	public static final Supplier<PointOfInterestType> WEATHERED_LIGHTNING_ROD;
	public static final Supplier<PointOfInterestType> OXIDIZED_LIGHTNING_ROD;
	public static final Supplier<PointOfInterestType> WAXED_LIGHTNING_ROD;
	public static final Supplier<PointOfInterestType> WAXED_EXPOSED_LIGHTNING_ROD;
	public static final Supplier<PointOfInterestType> WAXED_WEATHERED_LIGHTNING_ROD;
	public static final Supplier<PointOfInterestType> WAXED_OXIDIZED_LIGHTNING_ROD;

	static {
		REGISTERED_POINT_OF_INTEREST_TYPES = new HashMap<>();
		ACACIA_BEEHIVE = registerPointOfInterest("acacia_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.ACACIA_BEEHIVE.get()), 1, 1));
		BIRCH_BEEHIVE = registerPointOfInterest("birch_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.BIRCH_BEEHIVE.get()), 1, 1));
		CRIMSON_BEEHIVE = registerPointOfInterest("crimson_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.CRIMSON_BEEHIVE.get()), 1, 1));
		DARK_OAK_BEEHIVE = registerPointOfInterest("dark_oak_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.DARK_OAK_BEEHIVE.get()), 1, 1));
		JUNGLE_BEEHIVE = registerPointOfInterest("jungle_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.JUNGLE_BEEHIVE.get()), 1, 1));
		MANGROVE_BEEHIVE = registerPointOfInterest("mangrove_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.MANGROVE_BEEHIVE.get()), 1, 1));
		SPRUCE_BEEHIVE = registerPointOfInterest("spruce_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.SPRUCE_BEEHIVE.get()), 1, 1));
		WARPED_BEEHIVE = registerPointOfInterest("warped_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.WARPED_BEEHIVE.get()), 1, 1));
		EXPOSED_LIGHTNING_ROD = registerPointOfInterest("exposed_lightning_rod", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get()), 0, 1));
		WEATHERED_LIGHTNING_ROD = registerPointOfInterest("weathered_lightning_rod", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get()), 0, 1));
		OXIDIZED_LIGHTNING_ROD = registerPointOfInterest("oxidized_lightning_rod", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD.get()), 0, 1));
		WAXED_LIGHTNING_ROD = registerPointOfInterest("waxed_lightning_rod", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get()), 0, 1));
		WAXED_EXPOSED_LIGHTNING_ROD = registerPointOfInterest("waxed_exposed_lightning_rod", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get()), 0, 1));
		WAXED_WEATHERED_LIGHTNING_ROD = registerPointOfInterest("waxed_weathered_lightning_rod", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get()), 0, 1));
		WAXED_OXIDIZED_LIGHTNING_ROD = registerPointOfInterest("waxed_oxidized_lightning_rod", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get()), 0, 1));
	}

	public static void init() {
	}

	public static void postInit() {
		fillMissingPointOfInterestMapValues();
	}

	private static Supplier<PointOfInterestType> registerPointOfInterest(
		String name,
		Supplier<PointOfInterestType> pointOfInterestType
	) {
		REGISTERED_POINT_OF_INTEREST_TYPES.put(name, pointOfInterestType);
		return RegistryHelper.registerPointOfInterestType(name, pointOfInterestType);
	}

	private static void fillMissingPointOfInterestMapValues() {
		REGISTERED_POINT_OF_INTEREST_TYPES.forEach((name, pointOfInterestType) -> {
			fillMissingPointOfInterestMapValueForBlock(name, pointOfInterestType.get().blockStates().iterator().next().getBlock());
		});
	}

	private static void fillMissingPointOfInterestMapValueForBlock(
		String name,
		Block pointOfInterestBlock
	) {
		var blockStates = PointOfInterestTypes.getStatesOfBlock(pointOfInterestBlock);
		blockStates.forEach((state) -> {
			PointOfInterestTypes.POI_STATES_TO_TYPE.put(
				state,
				Registries.POINT_OF_INTEREST_TYPE.getEntry(
					RegistryKey.of(
						RegistryKeys.POINT_OF_INTEREST_TYPE, FriendsAndFoes.makeID(name)
					)
				).get()
			);
		});
	}

	private FriendsAndFoesPointOfInterestTypes() {
	}
}
