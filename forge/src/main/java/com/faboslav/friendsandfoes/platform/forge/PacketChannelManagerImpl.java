package com.faboslav.friendsandfoes.platform.forge;

import com.faboslav.friendsandfoes.network.base.Packet;
import com.faboslav.friendsandfoes.network.base.PacketHandler;
import com.faboslav.friendsandfoes.platform.ModVersion;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.HashMap;
import java.util.Map;

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
			NetworkEvent.Context context = ctx.get();
			PlayerEntity player = null;
			if (context.getSender() == null) {
				player = MinecraftClient.getInstance().player;
			}

			if (player != null) {
				PlayerEntity finalPlayer = player;
				context.enqueueWork(() -> handler.handle(msg).apply(finalPlayer, finalPlayer.getWorld()));
			}
			context.setPacketHandled(true);
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
			NetworkEvent.Context context = ctx.get();
			PlayerEntity player = context.getSender();
			if (player != null) {
				context.enqueueWork(() -> handler.handle(msg).apply(player, player.getWorld()));
			}
			context.setPacketHandled(true);
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
