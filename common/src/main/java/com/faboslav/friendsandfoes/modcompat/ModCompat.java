package com.faboslav.friendsandfoes.modcompat;


import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.Predicate;

/**
 * Related code is based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public interface ModCompat
{
	default EnumSet<Type> compatTypes() {
		return EnumSet.noneOf(Type.class);
	}

	@Nullable
	default ItemStack getEquippedItemFromCustomSlots(Entity entity, Predicate<ItemStack> itemStackPredicate) {
		return null;
	}

	enum Type
	{
		CUSTOM_EQUIPMENT_SLOTS,
	}
}
