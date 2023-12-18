package com.faboslav.friendsandfoes.platform.neoforge;

import com.faboslav.friendsandfoes.network.base.Packet;
import com.faboslav.friendsandfoes.network.base.PacketHandler;
import com.faboslav.friendsandfoes.platform.ModVersion;
import com.faboslav.friendsandfoes.util.client.PlayerProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.simple.SimpleChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * Network related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public class PacketChannelManagerImpl
{
	public static final Map<Identifier, Channel> CHANNELS = new HashMap<>();

	public static void registerChannel(Identifier name) {
		String protocolVersion = ModVersion.getModVersion();
		Channel channel = new Channel(0, NetworkRegistry.newSimpleChannel(name, () -> protocolVersion, protocolVersion::equals, protocolVersion::equals));
		CHANNELS.put(name, channel);
	}

	public static <T extends Packet<T>> void registerS2CPacket(
		Identifier name,
		Identifier id,
		PacketHandler<T> handler,
		Class<T> packetClass
	) {
		Channel channel = CHANNELS.get(name);
		if (channel == null) {
			throw new IllegalStateException("Channel " + name + " not registered");
		}
		channel.channel.registerMessage(++channel.packets, packetClass, handler::encode, handler::decode, (msg, ctx) -> {
			ctx.enqueueWork(() -> {
				PlayerEntity player = null;
				if (ctx.getSender() == null) {
					player = PlayerProvider.getClientPlayer();
				}

				if (player != null) {
					PlayerEntity finalPlayer = player;
					ctx.enqueueWork(() -> handler.handle(msg).apply(finalPlayer, finalPlayer.getWorld()));
				}
			});

			ctx.setPacketHandled(true);
		});
	}

	public static <T extends Packet<T>> void registerC2SPacket(
		Identifier name,
		Identifier id,
		PacketHandler<T> handler,
		Class<T> packetClass
	) {
		Channel channel = CHANNELS.get(name);
		if (channel == null) {
			throw new IllegalStateException("Channel " + name + " not registered");
		}
		channel.channel.registerMessage(++channel.packets, packetClass, handler::encode, handler::decode, (msg, ctx) -> {
			PlayerEntity player = ctx.getSender();
			if (player != null) {
				ctx.enqueueWork(() -> handler.handle(msg).apply(player, player.getWorld()));
			}
			ctx.setPacketHandled(true);
		});
	}

	public static <T extends Packet<T>> void sendToServer(Identifier name, T packet) {
		Channel channel = CHANNELS.get(name);
		if (channel == null) {
			throw new IllegalStateException("Channel " + name + " not registered");
		}
		channel.channel.sendToServer(packet);
	}

	public static <T extends Packet<T>> void sendToPlayer(Identifier name, T packet, PlayerEntity player) {
		Channel channel = CHANNELS.get(name);
		if (channel == null) {
			throw new IllegalStateException("Channel " + name + " not registered");
		}
		if (player instanceof ServerPlayerEntity serverPlayer) {
			channel.channel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), packet);
		}
	}

	private static final class Channel
	{
		private int packets;
		private final SimpleChannel channel;

		private Channel(int packets, SimpleChannel channel) {
			this.packets = packets;
			this.channel = channel;
		}
	}
}
