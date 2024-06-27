package com.faboslav.friendsandfoes.network.internal;

import com.faboslav.friendsandfoes.network.Packet;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public record NetworkPacketPayload<T extends Packet<T>>(
	T packet,
	CustomPayload.Id<NetworkPacketPayload<T>> getId
) implements CustomPayload {
	public NetworkPacketPayload(T packet, Identifier channel) {
		this(packet, packet.type().type(channel));
	}
}
