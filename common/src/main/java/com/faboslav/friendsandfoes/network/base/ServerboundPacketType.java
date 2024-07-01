package com.faboslav.friendsandfoes.network.base;

import com.faboslav.friendsandfoes.network.Packet;
import java.util.function.Consumer;
import net.minecraft.entity.player.PlayerEntity;

public interface ServerboundPacketType<T extends Packet<T>> extends PacketType<T> {
    Consumer<PlayerEntity> handle(T message);
}
