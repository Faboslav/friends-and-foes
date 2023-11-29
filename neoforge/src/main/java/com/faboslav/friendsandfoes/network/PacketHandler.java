package com.faboslav.friendsandfoes.network;

import com.faboslav.friendsandfoes.platform.TotemHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.PlayNetworkDirection;
import net.neoforged.neoforge.network.simple.SimpleChannel;

public final class PacketHandler
{
	public static final SimpleChannel TOTEM_CHANNEL = NetworkRegistry.ChannelBuilder.named(TotemHelper.TOTEM_EFFECT_PACKET).simpleChannel();

	public static void registerMessages() {
		TOTEM_CHANNEL.messageBuilder(TotemEffectPacket.class, 0).encoder(TotemEffectPacket::encode);
	}

	public static <MSG> void sendToPlayer(MSG message, ServerPlayerEntity player) {
		TOTEM_CHANNEL.sendTo(message, player.networkHandler.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
	}

	public static <MSG> void sendToAllTracking(MSG message, LivingEntity entity) {
		TOTEM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), message);
	}
}
