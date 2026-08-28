package com.faboslav.friendsandfoes.common.network.packet;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesParticleTypes;
import com.faboslav.friendsandfoes.common.network.MessageHandler;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.faboslav.friendsandfoes.common.util.TotemUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

//? if >= 1.21.1 {
import net.minecraft.network.RegistryFriendlyByteBuf;
//?} else {
/*import net.minecraft.network.FriendlyByteBuf;
*///?}

public record TotemEffectPacket(Item item, int entityId) implements Packet<TotemEffectPacket>
{
	public static final Identifier ID = FriendsAndFoes.makeID("totem_effect_packet");
	public static final ClientboundPacketType<TotemEffectPacket> TYPE = new TotemEffectPacket.Handler();

	public static void sendToClient(Player player, Item totem) {
		TotemEffectPacket totemEffectPacket = new TotemEffectPacket(totem, player.getId());
		MessageHandler.DEFAULT_CHANNEL.sendToPlayer(totemEffectPacket, player);
		MessageHandler.DEFAULT_CHANNEL.sendToAllLoaded(totemEffectPacket, player.level(), player.blockPosition());
	}

	@Override
	public PacketType<TotemEffectPacket> type() {
		return TYPE;
	}

	public static class Handler implements ClientboundPacketType<TotemEffectPacket>
	{
		@Override
		public Identifier id() {
			return ID;
		}

		@Override
		public Runnable handle(final TotemEffectPacket packet) {
			return () -> {
				Entity entity = Minecraft.getInstance().level.getEntity(packet.entityId());

				if (entity instanceof Entity) {
					var item = packet.item;
					var itemStack = item.getDefaultInstance();
					if (item == FriendsAndFoesItems.TOTEM_OF_FREEZING.get()) {
						TotemUtil.playActivateAnimation(itemStack, entity, FriendsAndFoesParticleTypes.TOTEM_OF_FREEZING.get());
					} else if (item == FriendsAndFoesItems.TOTEM_OF_ILLUSION.get()) {
						TotemUtil.playActivateAnimation(itemStack, entity, FriendsAndFoesParticleTypes.TOTEM_OF_FREEZING.get());
					}
				}
			};
		}

		public TotemEffectPacket decode(
			//? if >= 1.21.1 {
			final RegistryFriendlyByteBuf buf
			//?} else {
			/*final FriendlyByteBuf buf
			*///?}
		) {
			//? if >=1.21.3 {
			return new TotemEffectPacket(BuiltInRegistries.ITEM.getValue(buf.readIdentifier()), buf.readInt());
			//?} else if >= 1.21.1 {
			/*return new TotemEffectPacket(BuiltInRegistries.ITEM.getHolder(buf.readIdentifier()).get().value(), buf.readInt());
			*///?} else {
			/*return new TotemEffectPacket(BuiltInRegistries.ITEM.get(buf.readIdentifier()), buf.readInt());
			*///?}
		}

		public void encode(
			final TotemEffectPacket packet,
			//? if >= 1.21.1 {
			final RegistryFriendlyByteBuf buf
			//?} else {
			/*final FriendlyByteBuf buf
			*///?}
		) {
			buf.writeIdentifier(BuiltInRegistries.ITEM.getKey(packet.item));
			buf.writeInt(packet.entityId);
		}

		//? if < 1.21.1 {
		/*@Override
		public Class<TotemEffectPacket> type() {
			return TotemEffectPacket.class;
		}
		*///?}
	}
}

