package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.events.entity.RegisterVillagerTradesEvent;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.util.BasicItemTrade;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Items;

/**
 * @see VillagerProfession
 */
public final class FriendsAndFoesVillagerProfessions
{
	public static final ResourcefulRegistry<VillagerProfession> VILLAGER_PROFESSIONS = ResourcefulRegistries.create(BuiltInRegistries.VILLAGER_PROFESSION, FriendsAndFoes.MOD_ID);

	public static final ResourceKey<VillagerProfession> BEEKEEPER_KEY = ResourceKey.create(Registries.VILLAGER_PROFESSION, FriendsAndFoes.makeID("beekeeper"));
	// TODO add gift in GiveGiftToHero
	public static final RegistryEntry<VillagerProfession> BEEKEEPER = VILLAGER_PROFESSIONS.register("beekeeper", () -> new VillagerProfession(
		//? if >=1.21.5 {
		Component.translatable("entity.friendsandfoes.villager.beekeeper"),
		//?} else {
		/*"beekeeper",
		*///?}
		pointOfInterest -> {
		if(!FriendsAndFoes.getConfig().enableBeekeeperVillagerProfession) {
			return false;
		}

		return pointOfInterest.is(FriendsAndFoesTags.BEEKEEPER_ACQUIRABLE_JOB_SITE);
	}, pointOfInterest -> {
		if(!FriendsAndFoes.getConfig().enableBeekeeperVillagerProfession) {
			return false;
		}

		// TODO replace sound
		return pointOfInterest.is(FriendsAndFoesTags.BEEKEEPER_ACQUIRABLE_JOB_SITE);
	}, ImmutableSet.of(Items.HONEYCOMB), ImmutableSet.of(), SoundEvents.ITEM_FRAME_REMOVE_ITEM));

	public static void registerVillagerTrades(RegisterVillagerTradesEvent event) {
		if (
			FriendsAndFoes.getConfig().enableBeekeeperVillagerProfession
			//? if >=1.21.5 {
			&& event.type() == BEEKEEPER_KEY
			//?} else {
			/*&& event.type() == BEEKEEPER.get()
			*///?}
		) {
			event.register(1, new BasicItemTrade(FriendsAndFoesItems.BUTTERCUP.get(), Items.EMERALD, 10, 1, 16, 2,  0.05F));
			event.register(1, new BasicItemTrade(Items.DANDELION, Items.EMERALD, 10, 1, 16, 2,  0.05F));
			event.register(1, new BasicItemTrade(Items.SUNFLOWER, Items.EMERALD, 10, 1, 16, 2,  0.05F));
			event.register(2, new BasicItemTrade(Items.GLASS_BOTTLE, Items.EMERALD, 9, 1, 12, 10,  0.05F));
			event.register(2, new BasicItemTrade(Items.EMERALD, Items.HONEY_BOTTLE, 3, 1, 12, 5,  0.05F));
			event.register(3, new BasicItemTrade(Items.SHEARS, Items.EMERALD, 1, 1, 12, 20,  0.05F));
			event.register(3, new BasicItemTrade(Items.EMERALD, Items.HONEY_BLOCK, 10, 1, 12, 10,  0.05F));
			event.register(4, new BasicItemTrade(Items.EMERALD, Items.HONEYCOMB, 4, 1, 12, 15,  0.05F));
			event.register(4, new BasicItemTrade(Items.EMERALD, Items.HONEYCOMB_BLOCK, 12, 1, 12, 15,  0.05F));
			event.register(5, new BasicItemTrade(Items.EMERALD, Items.BEEHIVE, 10, 1, 3, 30,  0.05F));
			event.register(5, new BasicItemTrade(Items.EMERALD, Items.BEE_NEST, 20, 1, 3, 30,  0.05F));
		}
	}

	private FriendsAndFoesVillagerProfessions() {
	}
}
