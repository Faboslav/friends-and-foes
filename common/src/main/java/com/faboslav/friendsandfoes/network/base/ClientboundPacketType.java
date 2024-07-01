package com.faboslav.friendsandfoes.network.base;

import com.faboslav.friendsandfoes.network.Packet;

public interface ClientboundPacketType<T extends Packet<T>> extends PacketType<T> {

    Runnable handle(T message);
}
