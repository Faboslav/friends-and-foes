package com.faboslav.friendsandfoes.platform.fabric;

import com.faboslav.friendsandfoes.platform.TotemHelper;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

/**
 * @see TotemHelper
 */
public final class TotemHelperImpl
{
	@Nullable
	public static ItemStack getTotemFromModdedSlots(PlayerEntity player, Predicate<ItemStack> totemFilter) {
		if (FabricLoader.getInstance().isModLoaded(TotemHelper.TRINKETS_MOD_ID)) {
			return TrinketsApi.getTrinketComponent(player).map(component -> {
				List<Pair<SlotReference, ItemStack>> res = component.getEquipped(totemFilter);
				return res.size() > 0 ? res.get(0).getRight():null;
			}).orElse(null);
		}

		return null;
	}
}