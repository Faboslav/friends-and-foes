package com.faboslav.friendsandfoes.network.forge;

import com.faboslav.friendsandfoes.platform.TotemHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public final class PacketHandler
{
	public static final SimpleChannel TOTEM_CHANNEL = ChannelBuilder.named(TotemHelper.TOTEM_EFFECT_PACKET).simpleChannel();

	public static void registerMessages() {
		TOTEM_CHANNEL.messageBuilder(TotemEffectPacket.class, NetworkDirection.PLAY_TO_CLIENT).encoder(TotemEffectPacket::encode).decoder(TotemEffectPacket::new).consumerNetworkThread(TotemEffectPacket::handle).add();
	}

	public static <MSG> void sendToPlayer(MSG message, ServerPlayerEntity player) {
		TOTEM_CHANNEL.send(message, player.networkHandler.getConnection());
	}

	public static <MSG> void sendToAllTracking(MSG message, LivingEntity entity) {
		TOTEM_CHANNEL.send(message, PacketDistributor.TRACKING_ENTITY.with(entity));
	}
}
