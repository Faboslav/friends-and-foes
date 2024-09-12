package com.faboslav.friendsandfoes.common.platform.neoforge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.network.base.Packet;
import com.faboslav.friendsandfoes.common.network.base.PacketHandler;
import com.google.common.base.Preconditions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

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
public final class PacketChannelManagerImpl
{
	private static final Map<Identifier, PacketRegistration<?>> PACKETS = new HashMap<>();
	private static boolean frozen = false;

	public static void registerPayloads(RegisterPayloadHandlerEvent event) {
		frozen = true;

		var registrar = event.registrar(FriendsAndFoes.MOD_ID);
		for (var registration : PACKETS.values()) {
			registration.register(registrar);
		}
	}

	public static <T extends Packet<T>> void registerS2CPacket(
		Identifier name,
		Identifier id,
		PacketHandler<T> handler,
		Class<T> packetClass
	) {
		Preconditions.checkState(!frozen, "Packets were already registered with the platform");
		Preconditions.checkState(!PACKETS.containsKey(id), "Duplicate packet id %s", id);
		PACKETS.put(id, new PacketRegistration<>(id, handler, packetClass, NetworkSide.CLIENTBOUND));
	}

	public static <T extends Packet<T>> void registerC2SPacket(
		Identifier name,
		Identifier id,
		PacketHandler<T> handler,
		Class<T> packetClass
	) {
		Preconditions.checkState(!frozen, "Packets were already registered with the platform");
		Preconditions.checkState(!PACKETS.containsKey(id), "Duplicate packet id %s", id);
		PACKETS.put(id, new PacketRegistration<>(id, handler, packetClass, NetworkSide.SERVERBOUND));
	}

	public static <T extends Packet<T>> void sendToServer(Identifier name, T packet) {
		PacketDistributor.SERVER.noArg().send(new PayloadWrapper<>(packet));
	}

	public static <T extends Packet<T>> void sendToPlayer(Identifier name, T packet, PlayerEntity player) {
		if (player instanceof ServerPlayerEntity serverPlayer) {
			PacketDistributor.PLAYER.with(serverPlayer).send(new PayloadWrapper<>(packet));
		}
	}

	private record PacketRegistration<T extends Packet<T>>(
		Identifier packetId,
		PacketHandler<T> packetHandler,
		Class<T> packetClass,
		NetworkSide side
	)
	{
		public void register(IPayloadRegistrar registrar) {
			registrar.play(packetId, this::decode, builder -> {
				if (side.isClientbound()) {
					builder.client(this::handlePacketOnMainThread);
				} else if (side.isServerbound()) {
					builder.server(this::handlePacketOnMainThread);
				}
			});
		}

		private PayloadWrapper<T> decode(PacketByteBuf buffer) {
			return new PayloadWrapper<>(packetHandler.decode(buffer));
		}

		private void handlePacketOnMainThread(PayloadWrapper<T> payload, PlayPayloadContext context) {
			var player = context.player().orElse(null);
			var level = context.level().orElse(null);

			context.workHandler().execute(() -> {
				var packet = payload.packet();
				packet.getHandler().handle(packet).apply(player, level);
			});
		}
	}

	private record PayloadWrapper<T extends Packet<T>>(T packet) implements CustomPayload
	{
		@Override
		public void write(PacketByteBuf buffer) {
			packet.getHandler().encode(packet, buffer);
		}

		@Override
		public Identifier id() {
			return packet.getID();
		}
	}
}
