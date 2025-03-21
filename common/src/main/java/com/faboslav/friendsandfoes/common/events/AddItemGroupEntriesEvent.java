package com.faboslav.friendsandfoes.common.events;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;

import java.util.function.Consumer;

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record AddItemGroupEntriesEvent(Type type, ItemGroup itemGroup, boolean hasPermission, Consumer<ItemStack> adder)
{
	public static final EventHandler<AddItemGroupEntriesEvent> EVENT = new EventHandler<>();

	public void add(ItemStack stack) {
		adder.accept(stack);
	}

	public void add(ItemConvertible item) {
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

		public static Type toType(RegistryKey<ItemGroup> itemGroup) {
			if (ItemGroups.BUILDING_BLOCKS.equals(itemGroup)) return BUILDING_BLOCKS;
			else if (ItemGroups.COLORED_BLOCKS.equals(itemGroup)) return COLORED_BLOCKS;
			else if (ItemGroups.NATURAL.equals(itemGroup)) return NATURAL;
			else if (ItemGroups.FUNCTIONAL.equals(itemGroup)) return FUNCTIONAL;
			else if (ItemGroups.REDSTONE.equals(itemGroup)) return REDSTONE;
			else if (ItemGroups.HOTBAR.equals(itemGroup)) return HOTBAR;
			else if (ItemGroups.SEARCH.equals(itemGroup)) return SEARCH;
			else if (ItemGroups.TOOLS.equals(itemGroup)) return TOOLS;
			else if (ItemGroups.COMBAT.equals(itemGroup)) return COMBAT;
			else if (ItemGroups.FOOD_AND_DRINK.equals(itemGroup)) return FOOD_AND_DRINK;
			else if (ItemGroups.INGREDIENTS.equals(itemGroup)) return INGREDIENTS;
			else if (ItemGroups.SPAWN_EGGS.equals(itemGroup)) return SPAWN_EGGS;
			else if (ItemGroups.OPERATOR.equals(itemGroup)) return OPERATOR;
			else if (ItemGroups.INVENTORY.equals(itemGroup)) return INVENTORY;
			return CUSTOM;
		}
	}
}