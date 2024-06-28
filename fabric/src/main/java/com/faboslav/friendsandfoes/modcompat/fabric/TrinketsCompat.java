package com.faboslav.friendsandfoes.modcompat.fabric;

import com.faboslav.friendsandfoes.modcompat.ModCompat;

import java.util.EnumSet;

public final class TrinketsCompat implements ModCompat
{
	@Override
	public EnumSet<Type> compatTypes() {
		return EnumSet.of(Type.CUSTOM_EQUIPMENT_SLOTS);
	}

	/*
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
	}*/
}
