package com.faboslav.friendsandfoes.common.network;

import com.faboslav.friendsandfoes.common.network.base.ClientboundPacketType;
import com.faboslav.friendsandfoes.common.network.base.Networking;
import com.faboslav.friendsandfoes.common.network.base.PacketType;
import com.faboslav.friendsandfoes.common.network.base.ServerboundPacketType;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;

/**
 * Network related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public class Network implements Networking
{
	private final Networking networking;
	private final boolean optional;

	public Network(Identifier channel, int protocolVersion) {
		this(channel, protocolVersion, false);
	}

	public Network(Identifier channel, int protocolVersion, boolean optional) {
		this.networking = getNetwork(channel, protocolVersion, optional);
		this.optional = optional;
	}

	@Deprecated
	public Network(String modid, int protocolVersion, String channel) {
		this(modid, protocolVersion, channel, false);
	}

	@Deprecated
	public Network(String modid, int protocolVersion, String channel, boolean optional) {
		this.networking = getNetwork(new Identifier(modid, channel), protocolVersion, optional);
		this.optional = optional;
	}

	@Override
	public final <T extends Packet<T>> void register(ClientboundPacketType<T> type) {
		this.networking.register(type);
	}

	@Override
	public final <T extends Packet<T>> void register(ServerboundPacketType<T> type) {
		this.networking.register(type);
	}

	@Override
	public final <T extends Packet<T>> void sendToServer(T message) {
		this.networking.sendToServer(message);
	}

	@Override
	public final <T extends Packet<T>> void sendToPlayer(T message, ServerPlayerEntity player) {
		if (optional && !canSendToPlayer(player, message.type())) return;
		this.networking.sendToPlayer(message, player);
	}

	public final <T extends Packet<T>> void sendToPlayer(T message, PlayerEntity player) {
		if (player instanceof ServerPlayerEntity serverPlayer) {
			sendToPlayer(message, serverPlayer);
		}
	}

	public final <T extends Packet<T>> void sendToPlayers(T message, Collection<? extends PlayerEntity> players) {
		players.forEach(player -> sendToPlayer(message, player));
	}

	public final <T extends Packet<T>> void sendToAllPlayers(T message, MinecraftServer server) {
		sendToPlayers(message, server.getPlayerManager().getPlayerList());
	}

	public final <T extends Packet<T>> void sendToPlayersInLevel(T message, World level) {
		sendToPlayers(message, level.getPlayers());
	}

	public final <T extends Packet<T>> void sendToAllLoaded(T message, World level, BlockPos pos) {
		WorldChunk chunk = level.getWorldChunk(pos);
		if (chunk != null && level.getChunkManager() instanceof ServerChunkManager serverCache) {
			serverCache.threadedAnvilChunkStorage.getPlayersWatchingChunk(chunk.getPos(), false).forEach(player -> sendToPlayer(message, player));
		}
	}

	public final <T extends Packet<T>> void sendToPlayersInRange(T message, World level, BlockPos pos, double range) {
		for (PlayerEntity player : level.getPlayers()) {
			if (player.getBlockPos().getSquaredDistance(pos) <= range) {
				sendToPlayer(message, player);
			}
		}
	}

	/**
	 * Checks if the player can receive packets from this channel.
	 *
	 * @param player The player to check.
	 * @return True if the player can receive packets from this channel, false otherwise.
	 * @implNote On forge this will only check if it has the channel not if it can receive that specific packet.
	 */
	@Override
	public final boolean canSendToPlayer(ServerPlayerEntity player, PacketType<?> type) {
		return this.networking.canSendToPlayer(player, type);
	}

	public final boolean canSendToPlayer(PlayerEntity player, PacketType<?> type) {
		return player instanceof ServerPlayerEntity serverPlayer && canSendToPlayer(serverPlayer, type);
	}

	@ExpectPlatform
	@ApiStatus.Internal
	public static Networking getNetwork(Identifier channel, int protocolVersion, boolean optional) {
		throw new NotImplementedException();
	}
}