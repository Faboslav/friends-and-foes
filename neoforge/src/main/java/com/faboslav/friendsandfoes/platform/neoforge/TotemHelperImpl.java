package com.faboslav.friendsandfoes.platform.neoforge;

import com.faboslav.friendsandfoes.network.neoforge.PacketHandler;
import com.faboslav.friendsandfoes.network.neoforge.TotemEffectPacket;
import com.faboslav.friendsandfoes.platform.TotemHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.function.Predicate;

/**
 * @see TotemHelper
 */
public final class TotemHelperImpl
{
	public static void sendTotemEffectPacket(ItemStack itemStack, LivingEntity livingEntity) {
		TotemEffectPacket totemEffectPacket = new TotemEffectPacket(itemStack, livingEntity);

		if (livingEntity instanceof ServerPlayerEntity player) {
			PacketHandler.sendToPlayer(totemEffectPacket, player);
		}

		PacketHandler.sendToAllTracking(totemEffectPacket, livingEntity);
	}

	public static ItemStack getTotemFromModdedSlots(PlayerEntity player, Predicate<ItemStack> totemFilter) {
		return null;
	}
}

