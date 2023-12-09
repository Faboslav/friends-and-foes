package com.faboslav.friendsandfoes.network.base;

import net.minecraft.util.Identifier;

/**
 * All network related stuff is based on The Bumblezone mod with permission of TelepathicGrunt and ThatGravyBoat
 */
public interface Packet<T extends Packet<T>>
{
	Identifier getID();

	PacketHandler<T> getHandler();
}
