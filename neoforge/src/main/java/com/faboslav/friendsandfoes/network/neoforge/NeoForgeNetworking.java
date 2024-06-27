package com.faboslav.friendsandfoes.network.neoforge;

import com.faboslav.friendsandfoes.network.Packet;
import com.faboslav.friendsandfoes.network.base.ClientboundPacketType;
import com.faboslav.friendsandfoes.network.base.Networking;
import com.faboslav.friendsandfoes.network.base.PacketType;
import com.faboslav.friendsandfoes.network.base.ServerboundPacketType;
import com.faboslav.friendsandfoes.network.internal.NetworkPacketPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class NeoForgeNetworking implements Networking
{

    private static final List<Consumer<RegisterPayloadHandlersEvent>> LISTENERS = Collections.synchronizedList(new ArrayList<>());

    private final List<ClientboundPacketType<?>> clientPackets = new ArrayList<>();
    private final List<ServerboundPacketType<?>> serverPackets = new ArrayList<>();

    private final Identifier channel;
    private final String version;
    private final boolean optional;

    public NeoForgeNetworking(Identifier channel, int protocolVersion, boolean optional) {
        this.channel = channel.withSuffixedPath("/v" + protocolVersion);
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
    public <T extends Packet<T>> void sendToPlayer(T message, ServerPlayerEntity player) {
        PacketDistributor.sendToPlayer(player, new NetworkPacketPayload<>(message, this.channel));
    }

    @Override
    public boolean canSendToPlayer(ServerPlayerEntity player, PacketType<?> type) {
        return player.networkHandler.hasChannel(type.type(this.channel));
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
