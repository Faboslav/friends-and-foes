package com.faboslav.friendsandfoes.platform;

import com.faboslav.friendsandfoes.network.base.Packet;
import com.faboslav.friendsandfoes.network.base.PacketHandler;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.ApiStatus;

/**
 * All network/event related code is based on The Bumblezone mod with permission of TelepathicGrunt and ThatGravyBoat
 */
@ApiStatus.Internal
public final class PacketChannelManager
{

	@ExpectPlatform
	public static void registerChannel(Identifier channel) {
		throw new NotImplementedException();
	}

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
