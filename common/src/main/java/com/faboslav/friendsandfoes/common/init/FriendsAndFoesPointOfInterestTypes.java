package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;

/**
 * @see PoiTypes
 */
public final class FriendsAndFoesPointOfInterestTypes
{
	public static final ResourcefulRegistry<PoiType> POINT_OF_INTEREST_TYPES = ResourcefulRegistries.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, FriendsAndFoes.MOD_ID);

	public final static Supplier<PoiType> ACACIA_BEEHIVE = POINT_OF_INTEREST_TYPES.register("acacia_beehive", () -> new PoiType(PoiTypes.getBlockStates(FriendsAndFoesBlocks.ACACIA_BEEHIVE.get()), 1, 1));
	public final static Supplier<PoiType> BAMBOO_BEEHIVE = POINT_OF_INTEREST_TYPES.register("bamboo_beehive", () -> new PoiType(PoiTypes.getBlockStates(FriendsAndFoesBlocks.BAMBOO_BEEHIVE.get()), 1, 1));
	public final static Supplier<PoiType> BIRCH_BEEHIVE = POINT_OF_INTEREST_TYPES.register("birch_beehive", () -> new PoiType(PoiTypes.getBlockStates(FriendsAndFoesBlocks.BIRCH_BEEHIVE.get()), 1, 1));
	public final static Supplier<PoiType> CHERRY_BEEHIVE = POINT_OF_INTEREST_TYPES.register("cherry_beehive", () -> new PoiType(PoiTypes.getBlockStates(FriendsAndFoesBlocks.CHERRY_BEEHIVE.get()), 1, 1));
	public final static Supplier<PoiType> CRIMSON_BEEHIVE = POINT_OF_INTEREST_TYPES.register("crimson_beehive", () -> new PoiType(PoiTypes.getBlockStates(FriendsAndFoesBlocks.CRIMSON_BEEHIVE.get()), 1, 1));
	public final static Supplier<PoiType> DARK_OAK_BEEHIVE = POINT_OF_INTEREST_TYPES.register("dark_oak_beehive", () -> new PoiType(PoiTypes.getBlockStates(FriendsAndFoesBlocks.DARK_OAK_BEEHIVE.get()), 1, 1));
	public final static Supplier<PoiType> JUNGLE_BEEHIVE = POINT_OF_INTEREST_TYPES.register("jungle_beehive", () -> new PoiType(PoiTypes.getBlockStates(FriendsAndFoesBlocks.JUNGLE_BEEHIVE.get()), 1, 1));
	public final static Supplier<PoiType> MANGROVE_BEEHIVE = POINT_OF_INTEREST_TYPES.register("mangrove_beehive", () -> new PoiType(PoiTypes.getBlockStates(FriendsAndFoesBlocks.MANGROVE_BEEHIVE.get()), 1, 1));
	public final static Supplier<PoiType> SPRUCE_BEEHIVE = POINT_OF_INTEREST_TYPES.register("spruce_beehive", () -> new PoiType(PoiTypes.getBlockStates(FriendsAndFoesBlocks.SPRUCE_BEEHIVE.get()), 1, 1));
	public final static Supplier<PoiType> WARPED_BEEHIVE = POINT_OF_INTEREST_TYPES.register("warped_beehive", () -> new PoiType(PoiTypes.getBlockStates(FriendsAndFoesBlocks.WARPED_BEEHIVE.get()), 1, 1));
	public static final Supplier<PoiType> EXPOSED_LIGHTNING_ROD = POINT_OF_INTEREST_TYPES.register("exposed_lightning_rod", () -> new PoiType(PoiTypes.getBlockStates(FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get()), 0, 1));
	public static final Supplier<PoiType> WEATHERED_LIGHTNING_ROD = POINT_OF_INTEREST_TYPES.register("weathered_lightning_rod", () -> new PoiType(PoiTypes.getBlockStates(FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get()), 0, 1));
	public static final Supplier<PoiType> OXIDIZED_LIGHTNING_ROD = POINT_OF_INTEREST_TYPES.register("oxidized_lightning_rod", () -> new PoiType(PoiTypes.getBlockStates(FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD.get()), 0, 1));
	public static final Supplier<PoiType> WAXED_LIGHTNING_ROD = POINT_OF_INTEREST_TYPES.register("waxed_lightning_rod", () -> new PoiType(PoiTypes.getBlockStates(FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get()), 0, 1));
	public static final Supplier<PoiType> WAXED_EXPOSED_LIGHTNING_ROD = POINT_OF_INTEREST_TYPES.register("waxed_exposed_lightning_rod", () -> new PoiType(PoiTypes.getBlockStates(FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get()), 0, 1));
	public static final Supplier<PoiType> WAXED_WEATHERED_LIGHTNING_ROD = POINT_OF_INTEREST_TYPES.register("waxed_weathered_lightning_rod", () -> new PoiType(PoiTypes.getBlockStates(FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get()), 0, 1));
	public static final Supplier<PoiType> WAXED_OXIDIZED_LIGHTNING_ROD = POINT_OF_INTEREST_TYPES.register("waxed_oxidized_lightning_rod", () -> new PoiType(PoiTypes.getBlockStates(FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get()), 0, 1));

	private FriendsAndFoesPointOfInterestTypes() {
	}
}
