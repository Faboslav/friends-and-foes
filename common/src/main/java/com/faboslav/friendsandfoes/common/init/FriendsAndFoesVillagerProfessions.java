package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.events.RegisterVillagerTradesEvent;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.util.BasicTradeOffer;
import com.google.common.collect.ImmutableSet;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvents;
import net.minecraft.village.VillagerProfession;

/**
 * @see VillagerProfession
 */
public final class FriendsAndFoesVillagerProfessions
{
	public static final ResourcefulRegistry<VillagerProfession> VILLAGER_PROFESSIONS = ResourcefulRegistries.create(Registries.VILLAGER_PROFESSION, FriendsAndFoes.MOD_ID);

	public static final RegistryEntry<VillagerProfession> BEEKEEPER = VILLAGER_PROFESSIONS.register("beekeeper", () -> new VillagerProfession("beekeeper", pointOfInterest -> {
		if(!FriendsAndFoes.getConfig().enableBeekeeperVillagerProfession) {
			return false;
		}

		return pointOfInterest.isIn(FriendsAndFoesTags.BEEKEEPER_ACQUIRABLE_JOB_SITE);
	}, pointOfInterest -> {
		if(!FriendsAndFoes.getConfig().enableBeekeeperVillagerProfession) {
			return false;
		}

		return pointOfInterest.isIn(FriendsAndFoesTags.BEEKEEPER_ACQUIRABLE_JOB_SITE);
	}, ImmutableSet.of(Items.HONEYCOMB), ImmutableSet.of(),  SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM));

	public static void registerVillagerTrades(RegisterVillagerTradesEvent event) {
		if (
			FriendsAndFoes.getConfig().enableBeekeeperVillagerProfession
			&& event.type() == BEEKEEPER.get()
		) {
			event.register(1, new BasicTradeOffer(FriendsAndFoesItems.BUTTERCUP.get(), Items.EMERALD, 10, 1, 16, 2,  0.05F));
			event.register(1, new BasicTradeOffer(Items.DANDELION, Items.EMERALD, 10, 1, 16, 2,  0.05F));
			event.register(1, new BasicTradeOffer(Items.SUNFLOWER, Items.EMERALD, 10, 1, 16, 2,  0.05F));
			event.register(2, new BasicTradeOffer(Items.GLASS_BOTTLE, Items.EMERALD, 9, 1, 12, 10,  0.05F));
			event.register(2, new BasicTradeOffer(Items.EMERALD, Items.HONEY_BOTTLE, 3, 1, 12, 5,  0.05F));
			event.register(3, new BasicTradeOffer(Items.SHEARS, Items.EMERALD, 1, 1, 12, 20,  0.05F));
			event.register(3, new BasicTradeOffer(Items.EMERALD, Items.HONEY_BLOCK, 10, 1, 12, 10,  0.05F));
			event.register(4, new BasicTradeOffer(Items.EMERALD, Items.HONEYCOMB, 4, 1, 12, 15,  0.05F));
			event.register(4, new BasicTradeOffer(Items.EMERALD, Items.HONEYCOMB_BLOCK, 12, 1, 12, 15,  0.05F));
			event.register(5, new BasicTradeOffer(Items.EMERALD, Items.BEEHIVE, 10, 1, 3, 30,  0.05F));
			event.register(5, new BasicTradeOffer(Items.EMERALD, Items.BEE_NEST, 20, 1, 3, 30,  0.05F));
		}
	}

	private FriendsAndFoesVillagerProfessions() {
	}
}
