package com.faboslav.friendsandfoes.network;

import com.faboslav.friendsandfoes.network.base.PacketType;

public interface Packet<T extends Packet<T>>
{
	PacketType<T> type();
}
