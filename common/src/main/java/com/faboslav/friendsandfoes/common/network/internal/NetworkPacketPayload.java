package com.faboslav.friendsandfoes.common.network.internal;

import com.faboslav.friendsandfoes.common.network.Packet;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
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
	Id<NetworkPacketPayload<T>> getId
) implements CustomPayload
{

	public NetworkPacketPayload(T packet, Identifier channel) {
		this(packet, packet.type().type(channel));
	}
}
