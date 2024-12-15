package com.faboslav.friendsandfoes.common.events;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record AddItemGroupEntriesEvent(Type type, CreativeModeTab itemGroup, boolean hasPermission, Consumer<ItemStack> adder)
{
	public static final EventHandler<AddItemGroupEntriesEvent> EVENT = new EventHandler<>();

	public void add(ItemStack stack) {
		adder.accept(stack);
	}

	public void add(ItemLike item) {
		adder.accept(new ItemStack(item));
	}

	public enum Type
	{
		BUILDING_BLOCKS,
		COLORED_BLOCKS,
		NATURAL,
		FUNCTIONAL,
		REDSTONE,
		HOTBAR,
		SEARCH,
		TOOLS,
		COMBAT,
		FOOD_AND_DRINK,
		INGREDIENTS,
		SPAWN_EGGS,
		OPERATOR,
		INVENTORY,
		CUSTOM;

		public static Type toType(ResourceKey<CreativeModeTab> itemGroup) {
			if (CreativeModeTabs.BUILDING_BLOCKS.equals(itemGroup)) return BUILDING_BLOCKS;
			else if (CreativeModeTabs.COLORED_BLOCKS.equals(itemGroup)) return COLORED_BLOCKS;
			else if (CreativeModeTabs.NATURAL_BLOCKS.equals(itemGroup)) return NATURAL;
			else if (CreativeModeTabs.FUNCTIONAL_BLOCKS.equals(itemGroup)) return FUNCTIONAL;
			else if (CreativeModeTabs.REDSTONE_BLOCKS.equals(itemGroup)) return REDSTONE;
			else if (CreativeModeTabs.HOTBAR.equals(itemGroup)) return HOTBAR;
			else if (CreativeModeTabs.SEARCH.equals(itemGroup)) return SEARCH;
			else if (CreativeModeTabs.TOOLS_AND_UTILITIES.equals(itemGroup)) return TOOLS;
			else if (CreativeModeTabs.COMBAT.equals(itemGroup)) return COMBAT;
			else if (CreativeModeTabs.FOOD_AND_DRINKS.equals(itemGroup)) return FOOD_AND_DRINK;
			else if (CreativeModeTabs.INGREDIENTS.equals(itemGroup)) return INGREDIENTS;
			else if (CreativeModeTabs.SPAWN_EGGS.equals(itemGroup)) return SPAWN_EGGS;
			else if (CreativeModeTabs.OP_BLOCKS.equals(itemGroup)) return OPERATOR;
			else if (CreativeModeTabs.INVENTORY.equals(itemGroup)) return INVENTORY;
			return CUSTOM;
		}
	}
}