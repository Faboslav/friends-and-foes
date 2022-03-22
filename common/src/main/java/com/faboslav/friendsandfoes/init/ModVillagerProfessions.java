package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.util.TradeOffersUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

/**
 * @see VillagerProfession
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class ModVillagerProfessions
{
	public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(FriendsAndFoes.MOD_ID, Registry.VILLAGER_PROFESSION_KEY);
	public static final RegistrySupplier<VillagerProfession> BEEKEEPER;

	static {
		BEEKEEPER = FriendsAndFoes.getConfig().enableBeekeeperVillagerProfession ? VILLAGER_PROFESSIONS.register("beekeeper", () -> new VillagerProfession(FriendsAndFoes.makeStringID("beekeeper"), PointOfInterestType.BEEHIVE, ImmutableSet.of(Items.HONEYCOMB), ImmutableSet.of(), SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM)):null;
	}

	public static void initRegister() {
		VILLAGER_PROFESSIONS.register();
	}

	public static void init() {
		initTradeOffers();
	}

	private static void initTradeOffers() {
		if (FriendsAndFoes.getConfig().enableBeekeeperVillagerProfession) {
			TradeOffers.PROFESSION_TO_LEVELED_TRADE.put(
				BEEKEEPER.get(), new Int2ObjectOpenHashMap(ImmutableMap.of(
					1,
					new TradeOffers.Factory[]{
						new TradeOffersUtil.BuyForOneEmeraldFactory(ModItems.BUTTERCUP.get(), 10, 16, 2),
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
	}

	private ModVillagerProfessions() {
	}
}
