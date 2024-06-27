package com.faboslav.friendsandfoes.packets;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.init.FriendsAndFoesParticleTypes;
import com.faboslav.friendsandfoes.network.Packet;
import com.faboslav.friendsandfoes.network.base.ClientboundPacketType;
import com.faboslav.friendsandfoes.network.base.PacketType;
import com.faboslav.friendsandfoes.util.TotemUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.util.Identifier;

/**
 * Network related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record TotemEffectPacket(ItemStack itemStack, int entityId) implements Packet<TotemEffectPacket>
{
	public static final Identifier ID = FriendsAndFoes.makeID("totem_effect_packet");
	public static final ClientboundPacketType<TotemEffectPacket> TYPE = new TotemEffectPacket.Handler();

	public static void sendToClient(PlayerEntity player, ItemStack itemStack) {
		TotemEffectPacket totemEffectPacket = new TotemEffectPacket(itemStack, player.getId());
		MessageHandler.DEFAULT_CHANNEL.sendToPlayer(totemEffectPacket, player);
		MessageHandler.DEFAULT_CHANNEL.sendToAllLoaded(totemEffectPacket, player.getWorld(), player.getBlockPos());
	}

	@Override
	public PacketType<TotemEffectPacket> type() {
		return TYPE;
	}

	public static class Handler implements ClientboundPacketType<TotemEffectPacket>
	{
		@Override
		public Class<TotemEffectPacket> type() {
			return TotemEffectPacket.class;
		}

		@Override
		public Identifier id() {
			return ID;
		}

		@Override
		public Runnable handle(final TotemEffectPacket packet) {
			return () -> {
				Entity entity = MinecraftClient.getInstance().world.getEntityById(packet.entityId());

				if (entity instanceof Entity) {
					if (packet.itemStack.getItem() == FriendsAndFoesItems.TOTEM_OF_FREEZING.get()) {
						TotemUtil.playActivateAnimation(packet.itemStack, entity, FriendsAndFoesParticleTypes.TOTEM_OF_FREEZING);
					} else if (packet.itemStack.getItem() == FriendsAndFoesItems.TOTEM_OF_ILLUSION.get()) {
						TotemUtil.playActivateAnimation(packet.itemStack, entity, FriendsAndFoesParticleTypes.TOTEM_OF_FREEZING);
					}
				}
			};
		}

		public TotemEffectPacket decode(final RegistryByteBuf buf) {
			return new TotemEffectPacket(ItemStack.PACKET_CODEC.decode(buf), buf.readInt());
		}

		public void encode(final TotemEffectPacket packet, final RegistryByteBuf buf) {
			ItemStack.PACKET_CODEC.encode(buf, packet.itemStack);
			buf.writeInt(packet.entityId);
		}
	}
}

