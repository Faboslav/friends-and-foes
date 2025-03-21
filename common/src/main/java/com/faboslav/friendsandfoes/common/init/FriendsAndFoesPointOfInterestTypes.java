package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

import java.util.function.Supplier;

/**
 * @see PointOfInterestTypes
 */
public final class FriendsAndFoesPointOfInterestTypes
{
	public static final ResourcefulRegistry<PointOfInterestType> POINT_OF_INTEREST_TYPES = ResourcefulRegistries.create(Registries.POINT_OF_INTEREST_TYPE, FriendsAndFoes.MOD_ID);

	public final static Supplier<PointOfInterestType> ACACIA_BEEHIVE = POINT_OF_INTEREST_TYPES.register("acacia_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.ACACIA_BEEHIVE.get()), 1, 1));
	public final static Supplier<PointOfInterestType> BAMBOO_BEEHIVE = POINT_OF_INTEREST_TYPES.register("bamboo_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.BAMBOO_BEEHIVE.get()), 1, 1));
	public final static Supplier<PointOfInterestType> BIRCH_BEEHIVE = POINT_OF_INTEREST_TYPES.register("birch_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.BIRCH_BEEHIVE.get()), 1, 1));
	public final static Supplier<PointOfInterestType> CHERRY_BEEHIVE = POINT_OF_INTEREST_TYPES.register("cherry_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.CHERRY_BEEHIVE.get()), 1, 1));
	public final static Supplier<PointOfInterestType> CRIMSON_BEEHIVE = POINT_OF_INTEREST_TYPES.register("crimson_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.CRIMSON_BEEHIVE.get()), 1, 1));
	public final static Supplier<PointOfInterestType> DARK_OAK_BEEHIVE = POINT_OF_INTEREST_TYPES.register("dark_oak_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.DARK_OAK_BEEHIVE.get()), 1, 1));
	public final static Supplier<PointOfInterestType> JUNGLE_BEEHIVE = POINT_OF_INTEREST_TYPES.register("jungle_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.JUNGLE_BEEHIVE.get()), 1, 1));
	public final static Supplier<PointOfInterestType> MANGROVE_BEEHIVE = POINT_OF_INTEREST_TYPES.register("mangrove_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.MANGROVE_BEEHIVE.get()), 1, 1));
	public final static Supplier<PointOfInterestType> SPRUCE_BEEHIVE = POINT_OF_INTEREST_TYPES.register("spruce_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.SPRUCE_BEEHIVE.get()), 1, 1));
	public final static Supplier<PointOfInterestType> WARPED_BEEHIVE = POINT_OF_INTEREST_TYPES.register("warped_beehive", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.WARPED_BEEHIVE.get()), 1, 1));
	public static final Supplier<PointOfInterestType> EXPOSED_LIGHTNING_ROD = POINT_OF_INTEREST_TYPES.register("exposed_lightning_rod", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get()), 0, 1));
	public static final Supplier<PointOfInterestType> WEATHERED_LIGHTNING_ROD = POINT_OF_INTEREST_TYPES.register("weathered_lightning_rod", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get()), 0, 1));
	public static final Supplier<PointOfInterestType> OXIDIZED_LIGHTNING_ROD = POINT_OF_INTEREST_TYPES.register("oxidized_lightning_rod", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD.get()), 0, 1));
	public static final Supplier<PointOfInterestType> WAXED_LIGHTNING_ROD = POINT_OF_INTEREST_TYPES.register("waxed_lightning_rod", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get()), 0, 1));
	public static final Supplier<PointOfInterestType> WAXED_EXPOSED_LIGHTNING_ROD = POINT_OF_INTEREST_TYPES.register("waxed_exposed_lightning_rod", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get()), 0, 1));
	public static final Supplier<PointOfInterestType> WAXED_WEATHERED_LIGHTNING_ROD = POINT_OF_INTEREST_TYPES.register("waxed_weathered_lightning_rod", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get()), 0, 1));
	public static final Supplier<PointOfInterestType> WAXED_OXIDIZED_LIGHTNING_ROD = POINT_OF_INTEREST_TYPES.register("waxed_oxidized_lightning_rod", () -> new PointOfInterestType(PointOfInterestTypes.getStatesOfBlock(FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get()), 0, 1));

	private FriendsAndFoesPointOfInterestTypes() {
	}
}
