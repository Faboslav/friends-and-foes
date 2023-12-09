package com.faboslav.friendsandfoes.network.base;

import net.minecraft.network.PacketByteBuf;

/**
 * All network related stuff is based on The Bumblezone mod with permission of TelepathicGrunt and ThatGravyBoat
 */
public interface PacketHandler<T extends Packet<T>>
{

	void encode(T message, PacketByteBuf buffer);

	T decode(PacketByteBuf buffer);

	PacketContext handle(T message);
}
