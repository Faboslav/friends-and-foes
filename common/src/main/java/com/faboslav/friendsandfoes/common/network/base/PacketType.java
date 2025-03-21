package com.faboslav.friendsandfoes.common.network.base;

import com.faboslav.friendsandfoes.common.network.Packet;
import com.faboslav.friendsandfoes.common.network.internal.NetworkPacketPayload;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
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
@ApiStatus.NonExtendable
public interface PacketType<T extends Packet<T>>
{
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