package com.faboslav.friendsandfoes.network.fabric;

import com.faboslav.friendsandfoes.network.base.Packet;
import com.faboslav.friendsandfoes.network.base.PacketHandler;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

;

@Environment(EnvType.CLIENT)
public class FabricClientPacketHelper
{

	@Environment(EnvType.CLIENT)
	public static <T extends Packet<T>> void clientOnlyRegister(Identifier location, PacketHandler<T> handler) {
		ClientPlayNetworking.registerGlobalReceiver(location, (client, handler1, buf, responseSender) -> {
			T decode = handler.decode(buf);
			client.execute(() -> handler.handle(decode).apply(client.player, client.world));
		});
	}

	@Environment(EnvType.CLIENT)
	public static <T extends Packet<T>> void sendToServerClientOnly(Identifier channel, T packet) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		packet.getHandler().encode(packet, buf);
		ClientPlayNetworking.send(createChannelLocation(channel, packet.getID()), buf);
	}

	private static Identifier createChannelLocation(Identifier channel, Identifier id) {
		return new Identifier(channel.getNamespace(), channel.getPath() + "/" + id.getNamespace() + "/" + id.getPath());
	}
}