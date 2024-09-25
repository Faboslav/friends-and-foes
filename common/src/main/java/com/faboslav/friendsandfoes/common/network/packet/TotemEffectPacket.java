package com.faboslav.friendsandfoes.common.network.packet;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesParticleTypes;
import com.faboslav.friendsandfoes.common.network.Packet;
import com.faboslav.friendsandfoes.common.network.base.ClientboundPacketType;
import com.faboslav.friendsandfoes.common.network.base.PacketType;
import com.faboslav.friendsandfoes.common.util.TotemUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

/**
 * Network related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record TotemEffectPacket(Item item, int entityId) implements Packet<TotemEffectPacket>
{
	public static final Identifier ID = FriendsAndFoes.makeID("totem_effect_packet");
	public static final ClientboundPacketType<TotemEffectPacket> TYPE = new TotemEffectPacket.Handler();

	public static void sendToClient(PlayerEntity player, Item totem) {
		TotemEffectPacket totemEffectPacket = new TotemEffectPacket(totem, player.getId());
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
					var item = packet.item;
					var itemStack = item.getDefaultStack();
					if (item == FriendsAndFoesItems.TOTEM_OF_FREEZING.get()) {
						TotemUtil.playActivateAnimation(itemStack, entity, FriendsAndFoesParticleTypes.TOTEM_OF_FREEZING.get());
					} else if (item == FriendsAndFoesItems.TOTEM_OF_ILLUSION.get()) {
						TotemUtil.playActivateAnimation(itemStack, entity, FriendsAndFoesParticleTypes.TOTEM_OF_FREEZING.get());
					}
				}
			};
		}

		public TotemEffectPacket decode(final RegistryByteBuf buf) {
			return new TotemEffectPacket(Registries.ITEM.getEntry(buf.readIdentifier()).get().value(), buf.readInt());
		}

		public void encode(final TotemEffectPacket packet, final RegistryByteBuf buf) {
			buf.writeIdentifier(Registries.ITEM.getId(packet.item));
			buf.writeInt(packet.entityId);
		}
	}
}

