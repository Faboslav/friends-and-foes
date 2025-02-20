package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.events.entity.RegisterVillagerTradesEvent;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.util.BasicItemTrade;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

/**
 * @see VillagerProfession
 */
public final class FriendsAndFoesVillagerProfessions
{
	public static final ResourcefulRegistry<VillagerProfession> VILLAGER_PROFESSIONS = ResourcefulRegistries.create(BuiltInRegistries.VILLAGER_PROFESSION, FriendsAndFoes.MOD_ID);

	@Nullable
	public static final RegistryEntry<VillagerProfession> BEEKEEPER = registerBeekeeperVillagerProfessions();

	public static void registerVillagerTrades(RegisterVillagerTradesEvent event) {
		if (
			FriendsAndFoes.getConfig().enableBeekeeperVillagerProfession
			&& FriendsAndFoesVillagerProfessions.BEEKEEPER != null
			&& event.type() == BEEKEEPER.get()
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

	@Nullable
	private static RegistryEntry<VillagerProfession> registerBeekeeperVillagerProfessions() {
		if(!FriendsAndFoes.getConfig().enableBeekeeperVillagerProfession) {
			return null;
		}

		return VILLAGER_PROFESSIONS.register("beekeeper", () -> new VillagerProfession("beekeeper", pointOfInterest -> pointOfInterest.is(FriendsAndFoesTags.BEEKEEPER_ACQUIRABLE_JOB_SITE), pointOfInterest -> pointOfInterest.is(FriendsAndFoesTags.BEEKEEPER_ACQUIRABLE_JOB_SITE), ImmutableSet.of(Items.HONEYCOMB), ImmutableSet.of(), SoundEvents.ITEM_FRAME_REMOVE_ITEM));
	}

	private FriendsAndFoesVillagerProfessions() {
	}
}
