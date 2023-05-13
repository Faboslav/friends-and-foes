package com.faboslav.friendsandfoes.platform.forge;

import com.faboslav.friendsandfoes.network.PacketHandler;
import com.faboslav.friendsandfoes.network.TotemEffectPacket;
import com.faboslav.friendsandfoes.platform.TotemPacketHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * @see TotemPacketHelper
 */
public final class TotemPacketHelperImpl
{
	public static void sendTotemEffectPacket(ItemStack itemStack, LivingEntity livingEntity) {
		TotemEffectPacket totemEffectPacket = new TotemEffectPacket(itemStack, livingEntity);

		if (livingEntity instanceof ServerPlayerEntity player) {
			PacketHandler.sendToPlayer(totemEffectPacket, player);
		}

		PacketHandler.sendToAllTracking(totemEffectPacket, livingEntity);
	}
}

