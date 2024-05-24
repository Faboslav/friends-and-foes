package com.faboslav.friendsandfoes.common.network.base;

import net.minecraft.network.PacketByteBuf;

/**
 * Network related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public interface PacketHandler<T extends Packet<T>>
{

	void encode(T message, PacketByteBuf buffer);

	T decode(PacketByteBuf buffer);

	PacketContext handle(T message);
}
