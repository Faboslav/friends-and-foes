package com.faboslav.friendsandfoes.network.fabric;

import com.faboslav.friendsandfoes.network.Packet;
import com.faboslav.friendsandfoes.network.base.ClientboundPacketType;
import com.faboslav.friendsandfoes.network.base.Networking;
import com.faboslav.friendsandfoes.network.base.PacketType;
import com.faboslav.friendsandfoes.network.base.ServerboundPacketType;
import com.faboslav.friendsandfoes.network.internal.NetworkPacketPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class FabricNetworking implements Networking
{

    private static final boolean IS_CLIENT = FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT);

    private final Identifier channel;

    public FabricNetworking(Identifier channel, int protocolVersion) {
        this.channel = channel.withSuffixedPath("/v" + protocolVersion);
    }

    @Override
    public <T extends Packet<T>> void register(ClientboundPacketType<T> type) {
        final var payloadType = type.type(this.channel);
        PayloadTypeRegistry.playS2C().register(payloadType, type.codec(payloadType));
        if (!IS_CLIENT) return;
        FabricClientNetworkHandler.register(payloadType, type);
    }

    @Override
    public <T extends Packet<T>> void register(ServerboundPacketType<T> type) {
        final var payloadType = type.type(this.channel);
        PayloadTypeRegistry.playC2S().register(payloadType, type.codec(payloadType));
        ServerPlayNetworking.registerGlobalReceiver(
                payloadType,
                (payload, context) -> type.handle(payload.packet()).accept(context.player())
        );
    }

    @Override
    public <T extends Packet<T>> void sendToServer(T message) {
        if (!IS_CLIENT) return;
        FabricClientNetworkHandler.send(this.channel, message);
    }

    @Override
    public <T extends Packet<T>> void sendToPlayer(T message, ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, new NetworkPacketPayload<>(message, this.channel));
    }

    @Override
    public boolean canSendToPlayer(ServerPlayerEntity player, PacketType<?> type) {
        return ServerPlayNetworking.canSend(player, type.type(this.channel));
    }

}
