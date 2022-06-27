package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

/**
 * @see PointOfInterestTypes
 */
public final class ModPointOfInterestTypes
{
	private static final DeferredRegister<PointOfInterestType> POINT_OF_INTEREST_TYPES = DeferredRegister.create(FriendsAndFoes.MOD_ID, Registry.POINT_OF_INTEREST_TYPE_KEY);

	public final static RegistrySupplier<PointOfInterestType> ACACIA_BEEHIVE;
	public final static RegistrySupplier<PointOfInterestType> BIRCH_BEEHIVE;
	public final static RegistrySupplier<PointOfInterestType> CRIMSON_BEEHIVE;
	public final static RegistrySupplier<PointOfInterestType> DARK_OAK_BEEHIVE;
	public final static RegistrySupplier<PointOfInterestType> JUNGLE_BEEHIVE;
	public final static RegistrySupplier<PointOfInterestType> MANGROVE_BEEHIVE;
	public final static RegistrySupplier<PointOfInterestType> SPRUCE_BEEHIVE;
	public final static RegistrySupplier<PointOfInterestType> WARPED_BEEHIVE;

	static {
		ACACIA_BEEHIVE = POINT_OF_INTEREST_TYPES.register("acacia_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(ModBlocks.ACACIA_BEEHIVE.get()), 1, 1));
		BIRCH_BEEHIVE = POINT_OF_INTEREST_TYPES.register("birch_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(ModBlocks.BIRCH_BEEHIVE.get()), 1, 1));
		CRIMSON_BEEHIVE = POINT_OF_INTEREST_TYPES.register("crimson_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(ModBlocks.CRIMSON_BEEHIVE.get()), 1, 1));
		DARK_OAK_BEEHIVE = POINT_OF_INTEREST_TYPES.register("dark_oak_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(ModBlocks.DARK_OAK_BEEHIVE.get()), 1, 1));
		JUNGLE_BEEHIVE = POINT_OF_INTEREST_TYPES.register("jungle_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(ModBlocks.JUNGLE_BEEHIVE.get()), 1, 1));
		MANGROVE_BEEHIVE = POINT_OF_INTEREST_TYPES.register("mangrove_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(ModBlocks.MANGROVE_BEEHIVE.get()), 1, 1));
		SPRUCE_BEEHIVE = POINT_OF_INTEREST_TYPES.register("spruce_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(ModBlocks.SPRUCE_BEEHIVE.get()), 1, 1));
		WARPED_BEEHIVE = POINT_OF_INTEREST_TYPES.register("warped_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(ModBlocks.WARPED_BEEHIVE.get()), 1, 1));
	}

	public static void initRegister() {
		POINT_OF_INTEREST_TYPES.register();
	}

	public static void postInit() {
		fillMissingPointOfInterestMapValues();
	}

	private static void fillMissingPointOfInterestMapValues() {
		var acaciaBeehiveStates = PointOfInterestTypes.getStatesOfBlock(ModBlocks.ACACIA_BEEHIVE.get());
		PointOfInterestTypes.POI_STATES.addAll(acaciaBeehiveStates);
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

		var birchBeehiveStates = PointOfInterestTypes.getStatesOfBlock(ModBlocks.BIRCH_BEEHIVE.get());
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

		var crimsonBeehiveStates = PointOfInterestTypes.getStatesOfBlock(ModBlocks.CRIMSON_BEEHIVE.get());
		PointOfInterestTypes.POI_STATES.addAll(crimsonBeehiveStates);
		crimsonBeehiveStates.forEach((state) -> {
			PointOfInterestTypes.POI_STATES_TO_TYPE.put(
				state,
				Registry.POINT_OF_INTEREST_TYPE.getEntry(
					RegistryKey.of(
						Registry.POINT_OF_INTEREST_TYPE_KEY, FriendsAndFoes.makeID("crimson_beehive")
					)
				).get()
			);
		});

		var darkOakBeehiveStates = PointOfInterestTypes.getStatesOfBlock(ModBlocks.DARK_OAK_BEEHIVE.get());
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

		var jungleBeehiveStates = PointOfInterestTypes.getStatesOfBlock(ModBlocks.JUNGLE_BEEHIVE.get());
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

		var mangroveBeehiveStates = PointOfInterestTypes.getStatesOfBlock(ModBlocks.MANGROVE_BEEHIVE.get());
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

		var spruceBeehiveStates = PointOfInterestTypes.getStatesOfBlock(ModBlocks.SPRUCE_BEEHIVE.get());
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

		var warpedBeehiveStates = PointOfInterestTypes.getStatesOfBlock(ModBlocks.WARPED_BEEHIVE.get());
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

	private ModPointOfInterestTypes() {
	}
}
