package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.events.entity.RegisterVillagerTradesEvent;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import com.faboslav.friendsandfoes.common.util.TradeOffersUtil;
import com.google.common.collect.ImmutableSet;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.PointOfInterestTypeTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.village.VillagerProfession;

/**
 * @see VillagerProfession
 */
public final class FriendsAndFoesVillagerProfessions
{
	public static final ResourcefulRegistry<VillagerProfession> VILLAGER_PROFESSIONS = ResourcefulRegistries.create(Registries.VILLAGER_PROFESSION, FriendsAndFoes.MOD_ID);

	public static final RegistryEntry<VillagerProfession> BEEKEEPER = VILLAGER_PROFESSIONS.register("beekeeper", () -> new VillagerProfession("beekeeper", pointOfInterest -> pointOfInterest.isIn(PointOfInterestTypeTags.BEE_HOME), pointOfInterest -> pointOfInterest.isIn(PointOfInterestTypeTags.BEE_HOME), ImmutableSet.of(Items.HONEYCOMB), ImmutableSet.of(), SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM));

	public static void registerVillagerTrades(RegisterVillagerTradesEvent event) {
		event.register(1, new TradeOffersUtil.BuyForOneEmeraldFactory(FriendsAndFoesItems.BUTTERCUP.get(), 10, 16, 2));
		event.register(1, new TradeOffersUtil.BuyForOneEmeraldFactory(Items.DANDELION, 10, 16, 2));
		event.register(1, new TradeOffersUtil.BuyForOneEmeraldFactory(Items.SUNFLOWER, 10, 16, 2));
		event.register(2, new TradeOffersUtil.BuyForOneEmeraldFactory(Items.GLASS_BOTTLE, 9, 12, 10));
		event.register(2, new TradeOffersUtil.SellItemFactory(Items.HONEY_BOTTLE, 3, 1, 12, 5));
		event.register(3, new TradeOffersUtil.BuyForOneEmeraldFactory(Items.SHEARS, 1, 12, 20));
		event.register(3, new TradeOffersUtil.SellItemFactory(Items.HONEY_BLOCK, 10, 1, 12, 10));
		event.register(4, new TradeOffersUtil.SellItemFactory(Items.HONEYCOMB, 4, 1, 12, 15));
		event.register(5, new TradeOffersUtil.SellItemFactory(Items.HONEYCOMB_BLOCK, 12, 1, 12, 30));
	}

	private FriendsAndFoesVillagerProfessions() {
	}
}
