package com.faboslav.friendsandfoes.common.network.base;

import com.faboslav.friendsandfoes.common.network.Packet;

/**
 * Network related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public interface ClientboundPacketType<T extends Packet<T>> extends PacketType<T>
{
	Runnable handle(T message);
}