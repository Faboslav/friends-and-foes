package com.faboslav.friendsandfoes.network.fabric;

import com.faboslav.friendsandfoes.network.Packet;
import com.faboslav.friendsandfoes.network.base.ClientboundPacketType;
import com.faboslav.friendsandfoes.network.internal.NetworkPacketPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FabricClientNetworkHandler
{

	public static <T extends Packet<T>> void register(
		CustomPayload.Id<NetworkPacketPayload<T>> payloadType,
		ClientboundPacketType<T> type
	) {
		ClientPlayNetworking.registerGlobalReceiver(
			payloadType,
			(payload, context) -> type.handle(payload.packet()).run()
		);
	}

	public static <T extends Packet<T>> void send(Identifier channel, T message) {
		ClientPlayNetworking.send(new NetworkPacketPayload<>(message, channel));
	}
}
