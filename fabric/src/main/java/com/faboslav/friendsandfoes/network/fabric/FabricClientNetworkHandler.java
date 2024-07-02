package com.faboslav.friendsandfoes.network.fabric;

import com.faboslav.friendsandfoes.network.Packet;
import com.faboslav.friendsandfoes.network.base.ClientboundPacketType;
import com.faboslav.friendsandfoes.network.internal.NetworkPacketPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

/**
 * Network related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
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
