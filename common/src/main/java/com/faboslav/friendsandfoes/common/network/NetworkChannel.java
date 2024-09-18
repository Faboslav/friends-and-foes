package com.faboslav.friendsandfoes.common.network;

import com.faboslav.friendsandfoes.common.network.base.NetworkDirection;
import com.faboslav.friendsandfoes.common.network.base.Packet;
import com.faboslav.friendsandfoes.common.network.base.PacketHandler;
import com.faboslav.friendsandfoes.common.platform.PacketChannelManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

import java.util.Collection;

/**
 * Network related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public final class NetworkChannel
{

	private final Identifier channel;

	public NetworkChannel(String modid, String channel) {
		this.channel = new Identifier(modid, channel);
	}

	public <T extends Packet<T>> void registerPacket(
		NetworkDirection direction,
		Identifier id,
		PacketHandler<T> handler,
		Class<T> packetClass
	) {
		if (direction == NetworkDirection.CLIENT_TO_SERVER) {
			PacketChannelManager.registerC2SPacket(this.channel, id, handler, packetClass);
		} else {
			PacketChannelManager.registerS2CPacket(this.channel, id, handler, packetClass);
		}
	}

	public <T extends Packet<T>> void sendToServer(T packet) {
		PacketChannelManager.sendToServer(this.channel, packet);
	}

	public <T extends Packet<T>> void sendToPlayer(T packet, PlayerEntity player) {
		PacketChannelManager.sendToPlayer(this.channel, packet, player);
	}

	public <T extends Packet<T>> void sendToPlayers(T packet, Collection<? extends PlayerEntity> players) {
		players.forEach(player -> sendToPlayer(packet, player));
	}

	public <T extends Packet<T>> void sendToAllPlayers(T packet, MinecraftServer server) {
		sendToPlayers(packet, server.getPlayerManager().getPlayerList());
	}

	public <T extends Packet<T>> void sendToPlayersInLevel(T packet, World world) {
		sendToPlayers(packet, world.getPlayers());
	}

	public <T extends Packet<T>> void sendToAllLoaded(T packet, World world, BlockPos pos) {
		WorldChunk chunk = world.getWorldChunk(pos);
		if (chunk != null && world.getChunkManager() instanceof ServerChunkManager serverCache) {
			serverCache.threadedAnvilChunkStorage.getPlayersWatchingChunk(chunk.getPos(), false).forEach(player -> sendToPlayer(packet, player));
		}
	}

	public <T extends Packet<T>> void sendToPlayersInRange(T packet, World world, BlockPos pos, double range) {
		for (PlayerEntity player : world.getPlayers()) {
			if (player.getBlockPos().getSquaredDistance(pos) <= range) {
				sendToPlayer(packet, player);
			}
		}
	}


}
