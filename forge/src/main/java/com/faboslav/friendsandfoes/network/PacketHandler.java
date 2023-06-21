package com.faboslav.friendsandfoes.network;

import com.faboslav.friendsandfoes.platform.TotemHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public final class PacketHandler
{
	private static final String PROTOCOL_VERSION = "1";

	public static final SimpleChannel TOTEM_CHANNEL = NetworkRegistry.newSimpleChannel(TotemHelper.TOTEM_EFFECT_PACKET, () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static void registerMessages() {
		TOTEM_CHANNEL.registerMessage(0, TotemEffectPacket.class, TotemEffectPacket::encode, TotemEffectPacket::new, TotemEffectPacket::handle);
	}

	public static <MSG> void sendToPlayer(MSG message, ServerPlayerEntity player) {
		TOTEM_CHANNEL.sendTo(message, player.networkHandler.connection, NetworkDirection.PLAY_TO_CLIENT);
	}

	public static <MSG> void sendToAllTracking(MSG message, LivingEntity entity) {
		TOTEM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), message);
	}
}
