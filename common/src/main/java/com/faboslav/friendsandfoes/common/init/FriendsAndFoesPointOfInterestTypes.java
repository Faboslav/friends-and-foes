package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import java.util.function.Supplier;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

/**
 * @see PoiTypes
 */
public final class FriendsAndFoesPointOfInterestTypes
{
	public static final ResourcefulRegistry<PoiType> POINT_OF_INTEREST_TYPES = ResourcefulRegistries.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, FriendsAndFoes.MOD_ID);

	public final static Supplier<PoiType> ACACIA_BEEHIVE = registerPoi("acacia_beehive", FriendsAndFoesBlocks.ACACIA_BEEHIVE, 1, 1);
	public final static Supplier<PoiType> BAMBOO_BEEHIVE = registerPoi("bamboo_beehive", FriendsAndFoesBlocks.BAMBOO_BEEHIVE, 1, 1);
	public final static Supplier<PoiType> BIRCH_BEEHIVE = registerPoi("birch_beehive", FriendsAndFoesBlocks.BIRCH_BEEHIVE, 1, 1);
	public final static Supplier<PoiType> CHERRY_BEEHIVE = registerPoi("cherry_beehive", FriendsAndFoesBlocks.CHERRY_BEEHIVE, 1, 1);
	public final static Supplier<PoiType> CRIMSON_BEEHIVE = registerPoi("crimson_beehive", FriendsAndFoesBlocks.CRIMSON_BEEHIVE, 1, 1);
	public final static Supplier<PoiType> DARK_OAK_BEEHIVE = registerPoi("dark_oak_beehive", FriendsAndFoesBlocks.DARK_OAK_BEEHIVE, 1, 1);
	public final static Supplier<PoiType> JUNGLE_BEEHIVE = registerPoi("jungle_beehive", FriendsAndFoesBlocks.JUNGLE_BEEHIVE, 1, 1);
	public final static Supplier<PoiType> MANGROVE_BEEHIVE = registerPoi("mangrove_beehive", FriendsAndFoesBlocks.MANGROVE_BEEHIVE, 1, 1);
	//? >=1.21.4 {
	public static final Supplier<PoiType> PALE_OAK_BEEHIVE =  registerPoi("pale_oak_beehive", FriendsAndFoesBlocks.PALE_OAK_BEEHIVE, 1, 1);
	//?}
	public final static Supplier<PoiType> SPRUCE_BEEHIVE = registerPoi("spruce_beehive", FriendsAndFoesBlocks.SPRUCE_BEEHIVE, 1, 1);
	public final static Supplier<PoiType> WARPED_BEEHIVE = registerPoi("warped_beehive", FriendsAndFoesBlocks.WARPED_BEEHIVE, 1, 1);
	public static final Supplier<PoiType> EXPOSED_LIGHTNING_ROD = registerPoi("exposed_lightning_rod", FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD, 0, 1);
	public static final Supplier<PoiType> WEATHERED_LIGHTNING_ROD = registerPoi("weathered_lightning_rod", FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD, 0, 1);
	public static final Supplier<PoiType> OXIDIZED_LIGHTNING_ROD = registerPoi("oxidized_lightning_rod", FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD, 0, 1);
	public static final Supplier<PoiType> WAXED_LIGHTNING_ROD = registerPoi("waxed_lightning_rod", FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD, 0, 1);
	public static final Supplier<PoiType> WAXED_EXPOSED_LIGHTNING_ROD = registerPoi("waxed_exposed_lightning_rod", FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD, 0, 1);
	public static final Supplier<PoiType> WAXED_WEATHERED_LIGHTNING_ROD = registerPoi("waxed_weathered_lightning_rod", FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD, 0, 1);
	public static final Supplier<PoiType> WAXED_OXIDIZED_LIGHTNING_ROD = registerPoi("waxed_oxidized_lightning_rod", FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD, 0, 1);

	@Nullable
	private static Supplier<PoiType> registerPoi(String name, RegistryEntry<Block> block, int maxTickets, int validRange) {
		// TODO check if this works
		if(block == null) {
			return null;
		}

		return POINT_OF_INTEREST_TYPES.register(name, () -> new PoiType(PoiTypes.getBlockStates(block.get()), maxTickets, validRange));
	}
	
	private FriendsAndFoesPointOfInterestTypes() {
	}
}
