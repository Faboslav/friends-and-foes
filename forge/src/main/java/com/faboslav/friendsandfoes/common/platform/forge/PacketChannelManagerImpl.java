package com.faboslav.friendsandfoes.common.platform.forge;

import com.faboslav.friendsandfoes.common.network.base.Packet;
import com.faboslav.friendsandfoes.common.network.base.PacketHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

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
	public static final Map<Identifier, SimpleChannel> CHANNELS = new HashMap<>();

	public static void registerChannel(Identifier name, int protocolVersion, BooleanSupplier optional) {
		Channel.VersionTest test = optional.getAsBoolean() ?
			Channel.VersionTest.ACCEPT_MISSING.or(Channel.VersionTest.ACCEPT_VANILLA):
			Channel.VersionTest.exact(protocolVersion);

		CHANNELS.put(name, ChannelBuilder.named(name)
			.networkProtocolVersion(protocolVersion)
			.acceptedVersions(test)
			.simpleChannel());
	}

	public static <T extends Packet<T>> void registerS2CPacket(
		Identifier name,
		Identifier id,
		PacketHandler<T> handler,
		Class<T> packetClass
	) {
		SimpleChannel channel = CHANNELS.get(name);
		if (channel == null) {
			throw new IllegalStateException("Channel " + name + " not registered");
		}
		channel.messageBuilder(packetClass)
			.decoder(handler::decode)
			.encoder(handler::encode)
			.consumerNetworkThread((msg, context) -> {
				PlayerEntity player = context.getSender() == null ? getPlayer():null;
				if (player != null) {
					context.enqueueWork(() -> handler.handle(msg).apply(player, player.getEntityWorld()));
				}
				context.setPacketHandled(true);
			})
			.add();
	}

	public static <T extends Packet<T>> void registerC2SPacket(
		Identifier name,
		Identifier id,
		PacketHandler<T> handler,
		Class<T> packetClass
	) {
		SimpleChannel channel = CHANNELS.get(name);
		if (channel == null) {
			throw new IllegalStateException("Channel " + name + " not registered");
		}
		channel.messageBuilder(packetClass)
			.decoder(handler::decode)
			.encoder(handler::encode)
			.consumerNetworkThread((msg, context) -> {
				PlayerEntity player = context.getSender();
				if (player != null) {
					context.enqueueWork(() -> handler.handle(msg).apply(player, player.getWorld()));
				}
				context.setPacketHandled(true);
			})
			.add();
	}

	@OnlyIn(Dist.CLIENT)
	private static PlayerEntity getPlayer() {
		return MinecraftClient.getInstance().player;
	}

	public static <T extends Packet<T>> void sendToServer(Identifier name, T packet) {
		SimpleChannel channel = CHANNELS.get(name);
		if (channel == null) {
			throw new IllegalStateException("Channel " + name + " not registered");
		}
		channel.send(packet, PacketDistributor.SERVER.noArg());
	}

	public static <T extends Packet<T>> void sendToPlayer(Identifier name, T packet, PlayerEntity player) {
		SimpleChannel channel = CHANNELS.get(name);
		if (channel == null) {
			throw new IllegalStateException("Channel " + name + " not registered");
		}
		if (player instanceof ServerPlayerEntity serverPlayer) {
			channel.send(packet, PacketDistributor.PLAYER.with(serverPlayer));
		}
	}

	public static boolean canSendPlayerPackets(Identifier name, PlayerEntity player) {
		SimpleChannel channel = CHANNELS.get(name);
		if (channel != null && player instanceof ServerPlayerEntity serverPlayer) {
			return channel.isRemotePresent(serverPlayer.networkHandler.getConnection());
		}
		return false;
	}
}
