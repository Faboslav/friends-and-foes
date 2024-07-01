package com.faboslav.friendsandfoes.network.base;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.network.Packet;
import com.faboslav.friendsandfoes.network.internal.NetworkPacketPayload;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface PacketType<T extends Packet<T>> {

    Class<T> type();

    Identifier id();

    void encode(T message, RegistryByteBuf buffer);

    T decode(RegistryByteBuf buffer);

    @ApiStatus.Internal
    default PacketCodec<RegistryByteBuf, NetworkPacketPayload<T>> codec(CustomPayload.Id<NetworkPacketPayload<T>> type) {
        return PacketCodec.ofStatic(
                (buf, payload) -> {
					encode(payload.packet(), buf);
				},
                (buf) -> new NetworkPacketPayload<>(decode(buf), type)
        );
    }

    @ApiStatus.Internal
    default CustomPayload.Id<NetworkPacketPayload<T>> type(Identifier channel) {
        return new CustomPayload.Id<>(channel.withSuffixedPath("/" + this.id().getNamespace() + "/" + this.id().getPath()));
    }
}
