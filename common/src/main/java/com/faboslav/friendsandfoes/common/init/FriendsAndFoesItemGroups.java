package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.events.AddItemGroupEntriesEvent;
import com.faboslav.friendsandfoes.common.events.RegisterItemGroupsEvent;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import net.minecraft.item.Item;
import net.minecraft.text.Text;

import java.util.List;
import java.util.stream.Stream;

/**
 * @see net.minecraft.item.ItemGroups
 */
public class FriendsAndFoesItemGroups
{
	public static final List<RegistryEntry<Item>> CUSTOM_CREATIVE_TAB_ITEMS = FriendsAndFoesItems.ITEMS.stream().toList();

	public static void registerItemGroups(RegisterItemGroupsEvent event)
	{
		event.register(
			FriendsAndFoes.makeID("main_tab"),
			builder -> builder.icon(() -> FriendsAndFoesItems.WILDFIRE_CROWN.get().getDefaultStack()).displayName(Text.translatable("item_group." + FriendsAndFoes.MOD_ID + ".main_tab")),
			items -> CUSTOM_CREATIVE_TAB_ITEMS.stream().map(item -> item.get().getDefaultStack()).forEach(items::add)

		);
	}

	public static void addItemGroupEntries(AddItemGroupEntriesEvent event)
	{
		if (event.type() == AddItemGroupEntriesEvent.Type.SPAWN_EGGS) {
			Stream.of(
				FriendsAndFoesItems.COPPER_GOLEM_SPAWN_EGG,
				FriendsAndFoesItems.CRAB_SPAWN_EGG,
				FriendsAndFoesItems.GLARE_SPAWN_EGG,
				FriendsAndFoesItems.ICEOLOGER_SPAWN_EGG,
				FriendsAndFoesItems.ILLUSIONER_SPAWN_EGG,
				FriendsAndFoesItems.MAULER_SPAWN_EGG,
				FriendsAndFoesItems.MOOBLOOM_SPAWN_EGG,
				FriendsAndFoesItems.TUFF_GOLEM_SPAWN_EGG,
				FriendsAndFoesItems.RASCAL_SPAWN_EGG,
				FriendsAndFoesItems.WILDFIRE_SPAWN_EGG
			).map(item -> item.get().getDefaultStack()).forEach(event::add);
		} else if (event.type() == AddItemGroupEntriesEvent.Type.NATURAL) {
			Stream.of(
				FriendsAndFoesItems.BUTTERCUP,
				FriendsAndFoesItems.CRAB_EGG
			).map(item -> item.get().getDefaultStack()).forEach(event::add);
		} else if (event.type() == AddItemGroupEntriesEvent.Type.FUNCTIONAL) {
			Stream.of(
				FriendsAndFoesItems.ACACIA_BEEHIVE,
				FriendsAndFoesItems.BIRCH_BEEHIVE,
				FriendsAndFoesItems.CRIMSON_BEEHIVE,
				FriendsAndFoesItems.DARK_OAK_BEEHIVE,
				FriendsAndFoesItems.JUNGLE_BEEHIVE,
				FriendsAndFoesItems.MANGROVE_BEEHIVE,
				FriendsAndFoesItems.SPRUCE_BEEHIVE,
				FriendsAndFoesItems.WARPED_BEEHIVE
			).map(item -> item.get().getDefaultStack()).forEach(event::add);
		} else if (event.type() == AddItemGroupEntriesEvent.Type.REDSTONE) {
			Stream.of(
				FriendsAndFoesItems.COPPER_BUTTON,
				FriendsAndFoesItems.EXPOSED_COPPER_BUTTON,
				FriendsAndFoesItems.WEATHERED_COPPER_BUTTON,
				FriendsAndFoesItems.OXIDIZED_COPPER_BUTTON,
				FriendsAndFoesItems.WAXED_COPPER_BUTTON,
				FriendsAndFoesItems.WAXED_EXPOSED_COPPER_BUTTON,
				FriendsAndFoesItems.WAXED_WEATHERED_COPPER_BUTTON,
				FriendsAndFoesItems.WAXED_OXIDIZED_COPPER_BUTTON,
				FriendsAndFoesItems.EXPOSED_LIGHTNING_ROD,
				FriendsAndFoesItems.WEATHERED_LIGHTNING_ROD,
				FriendsAndFoesItems.OXIDIZED_LIGHTNING_ROD,
				FriendsAndFoesItems.WAXED_LIGHTNING_ROD,
				FriendsAndFoesItems.WAXED_EXPOSED_LIGHTNING_ROD,
				FriendsAndFoesItems.WAXED_WEATHERED_LIGHTNING_ROD,
				FriendsAndFoesItems.WAXED_OXIDIZED_LIGHTNING_ROD
			).map(item -> item.get().getDefaultStack()).forEach(event::add);
		} else if (event.type() == AddItemGroupEntriesEvent.Type.INGREDIENTS) {
			Stream.of(
				FriendsAndFoesItems.WILDFIRE_CROWN_FRAGMENT,
				FriendsAndFoesItems.CRAB_CLAW
			).map(item -> item.get().getDefaultStack()).forEach(event::add);
		} else if (event.type() == AddItemGroupEntriesEvent.Type.COMBAT) {
			Stream.of(
				FriendsAndFoesItems.WILDFIRE_CROWN,
				FriendsAndFoesItems.TOTEM_OF_FREEZING,
				FriendsAndFoesItems.TOTEM_OF_ILLUSION
			).map(item -> item.get().getDefaultStack()).forEach(event::add);
		}
	}
}
