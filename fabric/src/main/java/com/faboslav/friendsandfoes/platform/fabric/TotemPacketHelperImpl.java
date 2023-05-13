package com.faboslav.friendsandfoes.platform.fabric;

import com.faboslav.friendsandfoes.platform.TotemPacketHelper;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

/**
 * @see TotemPacketHelper
 */
public final class TotemPacketHelperImpl
{
	public static void sendTotemEffectPacket(ItemStack itemStack, LivingEntity livingEntity) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeItemStack(itemStack);
		buf.writeInt(livingEntity.getId());

		if (livingEntity instanceof ServerPlayerEntity player) {
			ServerPlayNetworking.send(player, TotemPacketHelper.TOTEM_EFFECT_PACKET, buf);
		}

		for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) livingEntity.getEntityWorld(), livingEntity.getBlockPos())) {
			ServerPlayNetworking.send(player, TotemPacketHelper.TOTEM_EFFECT_PACKET, buf);
		}
	}
}

