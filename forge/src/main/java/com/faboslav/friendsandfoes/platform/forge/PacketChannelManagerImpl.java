package com.faboslav.friendsandfoes.platform.forge;

import com.faboslav.friendsandfoes.common.network.base.Packet;
import com.faboslav.friendsandfoes.common.network.base.PacketHandler;
import com.faboslav.friendsandfoes.common.platform.ModVersion;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

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
			NetworkEvent.Context context = ctx.get();

			context.enqueueWork(() -> {
				PlayerEntity player = context.getSender() == null ? getPlayer():null;

				if (player != null) {
					handler.handle(msg).apply(player, player.getWorld());
				}
			});

			context.setPacketHandled(true);
		});
	}

	@OnlyIn(Dist.CLIENT)
	private static PlayerEntity getPlayer() {
		return MinecraftClient.getInstance().player;
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
