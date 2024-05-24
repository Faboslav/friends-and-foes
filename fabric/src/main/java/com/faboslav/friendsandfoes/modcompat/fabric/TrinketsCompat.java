package com.faboslav.friendsandfoes.modcompat.fabric;

import com.faboslav.friendsandfoes.common.modcompat.ModCompat;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public final class TrinketsCompat implements ModCompat
{
	@Override
	public EnumSet<Type> compatTypes() {
		return EnumSet.of(Type.CUSTOM_EQUIPMENT_SLOTS);
	}

	@Override
	@Nullable
	public ItemStack getEquippedItemFromCustomSlots(Entity entity, Predicate<ItemStack> itemStackPredicate) {
		if (entity instanceof PlayerEntity player) {
			return TrinketsApi.getTrinketComponent(player).map(component -> {
				List<Pair<SlotReference, ItemStack>> res = component.getEquipped(itemStackPredicate);
				return !res.isEmpty() ? res.get(0).getRight():null;
			}).orElse(null);
		}

		return null;
	}
}
