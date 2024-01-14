package com.faboslav.friendsandfoes.platform;

import com.faboslav.friendsandfoes.network.base.Packet;
import com.faboslav.friendsandfoes.network.base.PacketHandler;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.ApiStatus;

/**
 * Network related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
@ApiStatus.Internal
public final class PacketChannelManager
{

	@ExpectPlatform
	public static <T extends Packet<T>> void registerS2CPacket(
		Identifier channel,
		Identifier id,
		PacketHandler<T> handler,
		Class<T> packetClass
	) {
		throw new NotImplementedException();
	}

	@ExpectPlatform
	public static <T extends Packet<T>> void registerC2SPacket(
		Identifier channel,
		Identifier id,
		PacketHandler<T> handler,
		Class<T> packetClass
	) {
		throw new NotImplementedException();
	}

	@ExpectPlatform
	public static <T extends Packet<T>> void sendToServer(Identifier channel, T packet) {
		throw new NotImplementedException();
	}

	@ExpectPlatform
	public static <T extends Packet<T>> void sendToPlayer(Identifier channel, T packet, PlayerEntity player) {
		throw new NotImplementedException();
	}
}
