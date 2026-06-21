package com.faboslav.friendsandfoes.common.network.packet;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.network.MessageHandler;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

//? if >= 1.21.1 {
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;
import java.util.function.Consumer;
//?} else {
/*import net.minecraft.network.FriendlyByteBuf;
 *///?}

public record SyncCopperGolemWalkAnimationPacket(UUID copperGolemUuid, float walkAnimationPos, float walkAnimationSpeed) implements Packet<SyncCopperGolemWalkAnimationPacket>
{
	public static final Identifier ID = FriendsAndFoes.makeID("sync_copper_golem_frozen_walk_animation");
	public static final ServerboundPacketType<SyncCopperGolemWalkAnimationPacket> TYPE = new Handler();

	public static void sendToServer(UUID copperGolemUuid, float walkAnimationPos, float walkAnimationSpeed) {
		MessageHandler.DEFAULT_CHANNEL.sendToServer(new SyncCopperGolemWalkAnimationPacket(copperGolemUuid, walkAnimationPos, walkAnimationSpeed));
	}

	@Override
	public PacketType<SyncCopperGolemWalkAnimationPacket> type() {
		return TYPE;
	}

	private static final class Handler implements ServerboundPacketType<SyncCopperGolemWalkAnimationPacket>
	{
		@Override
		public void encode(
			SyncCopperGolemWalkAnimationPacket message,
			//? if >= 1.21.1 {
			RegistryFriendlyByteBuf buffer
			//?} else {
			/*FriendlyByteBuf buffer
			 *///?}
		) {
			buffer.writeUUID(message.copperGolemUuid());
			buffer.writeFloat(message.walkAnimationPos());
			buffer.writeFloat(message.walkAnimationSpeed());
		}

		@Override
		public SyncCopperGolemWalkAnimationPacket decode(
			//? if >= 1.21.1 {
			RegistryFriendlyByteBuf buffer
			//?} else {
			/*FriendlyByteBuf buffer
			 *///?}
		) {
			return new SyncCopperGolemWalkAnimationPacket(buffer.readUUID(), buffer.readFloat(), buffer.readFloat());
		}

		@Override
		public Consumer<Player> handle(SyncCopperGolemWalkAnimationPacket packet) {
			return (player) -> {
				Entity entity = ((ServerLevel)player.level()).getEntity(packet.copperGolemUuid());
				if (entity instanceof CopperGolemEntity copperGolem) {
					copperGolem.setWalkAnimation(packet.walkAnimationPos(), packet.walkAnimationSpeed());
				}
			};
		}

		//? if < 1.21.1 {
		/*@Override
		public Class<SyncCopperGolemFrozenWalkAnimationPacket> type() {
			return SyncCopperGolemFrozenWalkAnimationPacket.class;
		}
		*///?}

		@Override
		public Identifier id() {
			return ID;
		}
	}
}