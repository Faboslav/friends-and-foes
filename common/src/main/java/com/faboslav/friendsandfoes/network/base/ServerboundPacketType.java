package com.faboslav.friendsandfoes.network.base;

import com.faboslav.friendsandfoes.network.Packet;
import net.minecraft.entity.player.PlayerEntity;

import java.util.function.Consumer;

public interface ServerboundPacketType<T extends Packet<T>> extends PacketType<T> {
    Consumer<PlayerEntity> handle(T message);
}
