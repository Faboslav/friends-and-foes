package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import com.faboslav.friendsandfoes.util.TradeOffersUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @see VillagerProfession
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class FriendsAndFoesVillagerProfessions
{
	public static final Supplier<VillagerProfession> BEEKEEPER;

	public static final Predicate<RegistryEntry<PointOfInterestType>> BEEHIVE_PREDICATE = (registryEntry) -> {
		return FriendsAndFoes.getConfig().enableBeekeeperVillagerProfession
			   && (
				   registryEntry.matchesKey(PointOfInterestTypes.BEEHIVE)
				   || registryEntry.value() == FriendsAndFoesPointOfInterestTypes.ACACIA_BEEHIVE.get()
				   || registryEntry.value() == FriendsAndFoesPointOfInterestTypes.BIRCH_BEEHIVE.get()
				   || registryEntry.value() == FriendsAndFoesPointOfInterestTypes.CRIMSON_BEEHIVE.get()
				   || registryEntry.value() == FriendsAndFoesPointOfInterestTypes.DARK_OAK_BEEHIVE.get()
				   || registryEntry.value() == FriendsAndFoesPointOfInterestTypes.JUNGLE_BEEHIVE.get()
				   || registryEntry.value() == FriendsAndFoesPointOfInterestTypes.MANGROVE_BEEHIVE.get()
				   || registryEntry.value() == FriendsAndFoesPointOfInterestTypes.SPRUCE_BEEHIVE.get()
				   || registryEntry.value() == FriendsAndFoesPointOfInterestTypes.WARPED_BEEHIVE.get()
			   );
	};

	static {
		BEEKEEPER = RegistryHelper.registerVillagerProfession("beekeeper", () -> new VillagerProfession(
			FriendsAndFoes.makeStringID("beekeeper"),
			BEEHIVE_PREDICATE,
			BEEHIVE_PREDICATE,
			ImmutableSet.of(Items.HONEYCOMB),
			ImmutableSet.of(),
			SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM)
		);
	}

	public static void init() {
	}

	public static void postInit() {
		initTradeOffers();
	}

	private static void initTradeOffers() {
		TradeOffers.PROFESSION_TO_LEVELED_TRADE.put(
			BEEKEEPER.get(), new Int2ObjectOpenHashMap(ImmutableMap.of(
				1,
				new TradeOffers.Factory[]{
					new TradeOffersUtil.BuyForOneEmeraldFactory(FriendsAndFoesItems.BUTTERCUP.get(), 10, 16, 2),
					new TradeOffersUtil.BuyForOneEmeraldFactory(Items.DANDELION, 10, 16, 2),
					new TradeOffersUtil.BuyForOneEmeraldFactory(Items.SUNFLOWER, 10, 16, 2),
				},
				2,
				new TradeOffers.Factory[]{
					new TradeOffersUtil.BuyForOneEmeraldFactory(Items.GLASS_BOTTLE, 9, 12, 10),
					new TradeOffersUtil.SellItemFactory(Items.HONEY_BOTTLE, 3, 1, 12, 5),
				},
				3,
				new TradeOffers.Factory[]{
					new TradeOffersUtil.BuyForOneEmeraldFactory(Items.SHEARS, 1, 12, 20),
					new TradeOffersUtil.SellItemFactory(Items.HONEY_BLOCK, 10, 1, 12, 10),
				},
				4,
				new TradeOffers.Factory[]{
					new TradeOffersUtil.SellItemFactory(Items.HONEYCOMB, 4, 1, 12, 15),
				},
				5,
				new TradeOffers.Factory[]{
					new TradeOffersUtil.SellItemFactory(Items.HONEYCOMB_BLOCK, 12, 1, 12, 30),
				}
			))
		);
	}

	private FriendsAndFoesVillagerProfessions() {
	}
}
