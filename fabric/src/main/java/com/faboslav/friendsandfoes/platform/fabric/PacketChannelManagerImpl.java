package com.faboslav.friendsandfoes.platform.fabric;

import com.faboslav.friendsandfoes.common.network.base.Packet;
import com.faboslav.friendsandfoes.common.network.base.PacketHandler;
import com.faboslav.friendsandfoes.network.fabric.FabricClientPacketHelper;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

/**
 * Network related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public final class PacketChannelManagerImpl
{
	private PacketChannelManagerImpl() {
	}

	public static void registerChannel(Identifier channel) {
	}

	public static <T extends Packet<T>> void registerS2CPacket(
		Identifier channel,
		Identifier id,
		PacketHandler<T> handler,
		Class<T> packetClass
	) {
		if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT)) {
			FabricClientPacketHelper.clientOnlyRegister(createChannelLocation(channel, id), handler);
		}
	}

	public static <T extends Packet<T>> void registerC2SPacket(
		Identifier channel,
		Identifier id,
		PacketHandler<T> handler,
		Class<T> packetClass
	) {
		ServerPlayNetworking.registerGlobalReceiver(createChannelLocation(channel, id), (server, player, handler1, buf, responseSender) -> {
			T decode = handler.decode(buf);
			server.execute(() -> handler.handle(decode).apply(player, player.getWorld()));
		});
	}

	public static <T extends Packet<T>> void sendToServer(Identifier channel, T packet) {
		if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT))
			FabricClientPacketHelper.sendToServerClientOnly(channel, packet);
	}

	public static <T extends Packet<T>> void sendToPlayer(Identifier channel, T packet, PlayerEntity player) {
		if (player instanceof ServerPlayerEntity serverPlayer) {
			PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
			packet.getHandler().encode(packet, buf);
			ServerPlayNetworking.send(serverPlayer, createChannelLocation(channel, packet.getID()), buf);
		}
	}

	private static Identifier createChannelLocation(Identifier channel, Identifier id) {
		return new Identifier(channel.getNamespace(), channel.getPath() + "/" + id.getNamespace() + "/" + id.getPath());
	}
}
