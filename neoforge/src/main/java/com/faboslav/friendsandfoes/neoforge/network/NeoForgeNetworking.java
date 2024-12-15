package com.faboslav.friendsandfoes.neoforge.network;

import com.faboslav.friendsandfoes.common.network.Packet;
import com.faboslav.friendsandfoes.common.network.base.ClientboundPacketType;
import com.faboslav.friendsandfoes.common.network.base.Networking;
import com.faboslav.friendsandfoes.common.network.base.PacketType;
import com.faboslav.friendsandfoes.common.network.base.ServerboundPacketType;
import com.faboslav.friendsandfoes.common.network.internal.NetworkPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Network related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public class NeoForgeNetworking implements Networking
{
	private static final List<Consumer<RegisterPayloadHandlersEvent>> LISTENERS = Collections.synchronizedList(new ArrayList<>());

	private final List<ClientboundPacketType<?>> clientPackets = new ArrayList<>();
	private final List<ServerboundPacketType<?>> serverPackets = new ArrayList<>();

	private final ResourceLocation channel;
	private final String version;
	private final boolean optional;

	public NeoForgeNetworking(ResourceLocation channel, int protocolVersion, boolean optional) {
		this.channel = channel.withSuffix("/v" + protocolVersion);
		this.version = "v" + protocolVersion;
		this.optional = optional;

		LISTENERS.add(this::onNetworkSetup);
	}

	@Override
	public <T extends Packet<T>> void register(ClientboundPacketType<T> type) {
		this.clientPackets.add(type);
	}

	@Override
	public <T extends Packet<T>> void register(ServerboundPacketType<T> type) {
		this.serverPackets.add(type);
	}

	@Override
	public <T extends Packet<T>> void sendToServer(T message) {
		PacketDistributor.sendToServer(new NetworkPacketPayload<>(message, this.channel));
	}

	@Override
	public <T extends Packet<T>> void sendToPlayer(T message, ServerPlayer player) {
		PacketDistributor.sendToPlayer(player, new NetworkPacketPayload<>(message, this.channel));
	}

	@Override
	public boolean canSendToPlayer(ServerPlayer player, PacketType<?> type) {
		return player.connection.hasChannel(type.type(this.channel));
	}

	public void onNetworkSetup(RegisterPayloadHandlersEvent event) {
		PayloadRegistrar registrar = event.registrar(this.version);
		if (this.optional) {
			registrar = registrar.optional();
		}

		for (ClientboundPacketType<?> type : this.clientPackets) {
			registerClientbound(registrar, type);
		}

		for (ServerboundPacketType<?> type : this.serverPackets) {
			registerServerbound(registrar, type);
		}
	}

	private <T extends Packet<T>> void registerClientbound(PayloadRegistrar registrar, ClientboundPacketType<T> type) {
		final var payloadType = type.type(this.channel);
		registrar.playToClient(
			payloadType,
			type.codec(payloadType),
			(payload, context) -> type.handle(payload.packet()).run()
		);
	}

	private <T extends Packet<T>> void registerServerbound(PayloadRegistrar registrar, ServerboundPacketType<T> type) {
		final var payloadType = type.type(this.channel);
		registrar.playToServer(
			payloadType,
			type.codec(payloadType),
			(payload, context) -> type.handle(payload.packet()).accept(context.player())
		);
	}

	public static void setupNetwork(RegisterPayloadHandlersEvent event) {
		LISTENERS.forEach(listener -> listener.accept(event));
	}
}
