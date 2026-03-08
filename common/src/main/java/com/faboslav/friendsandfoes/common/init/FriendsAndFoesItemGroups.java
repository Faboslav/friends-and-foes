package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.events.AddItemGroupEntriesEvent;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.BeehiveBlock;

/**
 * @see net.minecraft.world.item.CreativeModeTabs
 */
public class FriendsAndFoesItemGroups
{
	public static final ResourcefulRegistry<CreativeModeTab> ITEM_GROUPS = ResourcefulRegistries.create(BuiltInRegistries.CREATIVE_MODE_TAB, FriendsAndFoes.MOD_ID);

	public static final RegistryEntry<CreativeModeTab> MAIN_TAB = ITEM_GROUPS.register("main_tab", () ->
		CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
			.title((Component.translatable("item_group." + FriendsAndFoes.MOD_ID + ".main_tab")))
			.icon(() -> {
				ItemStack iconStack = FriendsAndFoesItems.WILDFIRE_CROWN.get().getDefaultInstance();
				CompoundTag nbtCompound = new CompoundTag();
				nbtCompound.putBoolean("isCreativeTabIcon", true);
				iconStack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbtCompound));
				return iconStack;
			})
			.displayItems((itemDisplayParameters, entries) -> {
				FriendsAndFoes.getLogger().info(FriendsAndFoesItems.CONFIG_ITEMS.toString());
				FriendsAndFoesItems.ITEMS.stream().filter(entry -> {
					FriendsAndFoes.getLogger().info(entry.get().toString() +": " + FriendsAndFoesItems.CONFIG_ITEMS.containsKey(entry.getId()));
					return FriendsAndFoesItems.CONFIG_ITEMS.getOrDefault(entry, () -> true).getAsBoolean();
				}).toList().stream().map(item -> item.get().getDefaultInstance()).forEach(entries::accept);
			}).build());

	public static void addItemGroupEntries(AddItemGroupEntriesEvent event) {
		var config = FriendsAndFoes.getConfig();
		if (event.type() == AddItemGroupEntriesEvent.Type.SPAWN_EGGS) {
			Stream.of(
				FriendsAndFoesItems.BARNACLE_SPAWN_EGG,
				//? if <= 1.21.8 {
				/*FriendsAndFoesItems.COPPER_GOLEM_SPAWN_EGG,
				*///?}
				FriendsAndFoesItems.CRAB_SPAWN_EGG,
				FriendsAndFoesItems.GLARE_SPAWN_EGG,
				FriendsAndFoesItems.ICEOLOGER_SPAWN_EGG,
				FriendsAndFoesItems.ILLUSIONER_SPAWN_EGG,
				FriendsAndFoesItems.MAULER_SPAWN_EGG,
				FriendsAndFoesItems.MOOBLOOM_SPAWN_EGG,
				FriendsAndFoesItems.PENGUIN_SPAWN_EGG,
				FriendsAndFoesItems.TUFF_GOLEM_SPAWN_EGG,
				FriendsAndFoesItems.RASCAL_SPAWN_EGG,
				FriendsAndFoesItems.WILDFIRE_SPAWN_EGG
			).map(item -> item.get().getDefaultInstance()).forEach(event::add);
		} else if (event.type() == AddItemGroupEntriesEvent.Type.NATURAL) {
			Stream.of(
				FriendsAndFoesItems.BUTTERCUP,
				FriendsAndFoesItems.CRAB_EGG,
				FriendsAndFoesItems.PENGUIN_EGG
			).map(item -> item.get().getDefaultInstance()).forEach(event::add);
		} else if (event.type() == AddItemGroupEntriesEvent.Type.FUNCTIONAL) {
			Set<Supplier<Item>> beehives = new LinkedHashSet<>();

			FriendsAndFoes.getLogger().info("ITEM GROUPS");

			if (config.enableAcaciaBeehive) {
				FriendsAndFoes.getLogger().info("ACACIA");
				beehives.add(FriendsAndFoesItems.ACACIA_BEEHIVE);
			}

			if (config.enableBambooBeehive) {
				beehives.add(FriendsAndFoesItems.BAMBOO_BEEHIVE);
			}

			if (config.enableBirchBeehive) {
				beehives.add(FriendsAndFoesItems.BIRCH_BEEHIVE);
			}

			if (config.enableCherryBeehive) {
				beehives.add(FriendsAndFoesItems.CHERRY_BEEHIVE);
			}

			if (config.enableCrimsonBeehive) {
				beehives.add(FriendsAndFoesItems.CRIMSON_BEEHIVE);
			}

			if (config.enableDarkOakBeehive) {
				beehives.add(FriendsAndFoesItems.DARK_OAK_BEEHIVE);
			}

			if (config.enableJungleBeehive) {
				beehives.add(FriendsAndFoesItems.JUNGLE_BEEHIVE);
			}

			if (config.enableMangroveBeehive) {
				beehives.add(FriendsAndFoesItems.MANGROVE_BEEHIVE);
			}

			if (config.enableSpruceBeehive) {
				beehives.add(FriendsAndFoesItems.SPRUCE_BEEHIVE);
			}

			//? if >=1.21.4 {
			if (config.enablePaleOakBeehive) {
				beehives.add(FriendsAndFoesItems.PALE_OAK_BEEHIVE);
			}
			//?}

			if (config.enableWarpedBeehive) {
				beehives.add(FriendsAndFoesItems.WARPED_BEEHIVE);
			}

			beehives.stream()
				.map(Supplier::get)
				.map(Item::getDefaultInstance)
				.forEach(event::add);
		} else if (event.type() == AddItemGroupEntriesEvent.Type.REDSTONE) {
			Stream.of(
				FriendsAndFoesItems.COPPER_BUTTON,
				FriendsAndFoesItems.EXPOSED_COPPER_BUTTON,
				FriendsAndFoesItems.WEATHERED_COPPER_BUTTON,
				FriendsAndFoesItems.OXIDIZED_COPPER_BUTTON,
				FriendsAndFoesItems.WAXED_COPPER_BUTTON,
				FriendsAndFoesItems.WAXED_EXPOSED_COPPER_BUTTON,
				FriendsAndFoesItems.WAXED_WEATHERED_COPPER_BUTTON,
				FriendsAndFoesItems.WAXED_OXIDIZED_COPPER_BUTTON
				//? if <=1.21.8 {
				/*,FriendsAndFoesItems.EXPOSED_LIGHTNING_ROD,
				FriendsAndFoesItems.WEATHERED_LIGHTNING_ROD,
				FriendsAndFoesItems.OXIDIZED_LIGHTNING_ROD,
				FriendsAndFoesItems.WAXED_LIGHTNING_ROD,
				FriendsAndFoesItems.WAXED_EXPOSED_LIGHTNING_ROD,
				FriendsAndFoesItems.WAXED_WEATHERED_LIGHTNING_ROD,
				FriendsAndFoesItems.WAXED_OXIDIZED_LIGHTNING_ROD
				*///?}
			).map(item -> item.get().getDefaultInstance()).forEach(event::add);
		} else if (event.type() == AddItemGroupEntriesEvent.Type.INGREDIENTS) {
			Stream.of(
				FriendsAndFoesItems.WILDFIRE_CROWN_FRAGMENT,
				FriendsAndFoesItems.CRAB_CLAW,
				FriendsAndFoesItems.PENGUIN_FEATHER
			).map(item -> item.get().getDefaultInstance()).forEach(event::add);
		} else if (event.type() == AddItemGroupEntriesEvent.Type.COMBAT) {
			Stream.of(
				FriendsAndFoesItems.WILDFIRE_CROWN,
				FriendsAndFoesItems.TOTEM_OF_FREEZING,
				FriendsAndFoesItems.TOTEM_OF_ILLUSION
			).map(item -> item.get().getDefaultInstance()).forEach(event::add);
		}
	}
}
