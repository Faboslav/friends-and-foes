package com.faboslav.friendsandfoes.fabric.modcompat;

//? trinkets {
/*import com.faboslav.friendsandfoes.common.modcompat.ModCompat;

import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;

public final class TrinketsCompat implements ModCompat
{
	@Override
	public EnumSet<Type> compatTypes() {
		return EnumSet.of(Type.CUSTOM_EQUIPMENT_SLOTS);
	}

	@Override
	@Nullable
	public ItemStack getEquippedItemFromCustomSlots(Entity entity, Predicate<ItemStack> itemStackPredicate) {
		if (entity instanceof Player player) {
			return TrinketsApi.getTrinketComponent(player).map(component -> {
				List<Tuple<SlotReference, ItemStack>> res = component.getEquipped(itemStackPredicate);
				return !res.isEmpty() ? res.get(0).getB():null;
			}).orElse(null);
		}

		return null;
	}
}
*///?}