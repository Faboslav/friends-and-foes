package com.faboslav.friendsandfoes.platform.forge;

import com.faboslav.friendsandfoes.platform.TotemHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.function.Predicate;

/**
 * @see TotemHelper
 */
public final class TotemHelperImpl
{
	public static ItemStack getTotemFromModdedSlots(PlayerEntity player, Predicate<ItemStack> totemFilter) {
		if (ModList.get().isLoaded(TotemHelper.CURIOS_MOD_ID)) {
			return CuriosApi.getCuriosHelper().findFirstCurio(player, totemFilter).map(SlotResult::stack).orElse(null);
		}

		return null;
	}
}

