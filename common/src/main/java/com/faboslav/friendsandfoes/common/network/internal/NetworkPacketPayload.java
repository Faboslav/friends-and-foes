package com.faboslav.friendsandfoes.common.network.internal;

import com.faboslav.friendsandfoes.common.network.Packet;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

/**
 * Network related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
@ApiStatus.Internal
public record NetworkPacketPayload<T extends Packet<T>>(
	T packet,
	CustomPacketPayload.Type<NetworkPacketPayload<T>> type
) implements CustomPacketPayload {

	public NetworkPacketPayload(T packet, ResourceLocation channel) {
		this(packet, packet.type().type(channel));
	}
}
