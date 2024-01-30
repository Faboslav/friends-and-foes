package com.faboslav.friendsandfoes.network.packet;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.init.FriendsAndFoesParticleTypes;
import com.faboslav.friendsandfoes.network.MessageHandler;
import com.faboslav.friendsandfoes.network.base.Packet;
import com.faboslav.friendsandfoes.network.base.PacketContext;
import com.faboslav.friendsandfoes.network.base.PacketHandler;
import com.faboslav.friendsandfoes.util.TotemUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
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
	public static Gson gson = new GsonBuilder().create();

	public static final Identifier ID = FriendsAndFoes.makeID("totem_effect_packet");
	public static final TotemEffectPacket.Handler HANDLER = new TotemEffectPacket.Handler();

	public static void sendToClient(PlayerEntity player, ItemStack itemStack) {
		TotemEffectPacket totemEffectPacket = new TotemEffectPacket(itemStack, player.getId());
		MessageHandler.DEFAULT_CHANNEL.sendToPlayer(totemEffectPacket, player);
		MessageHandler.DEFAULT_CHANNEL.sendToAllLoaded(totemEffectPacket, player.getWorld(), player.getBlockPos());
	}

	@Override
	public Identifier getID() {
		return ID;
	}

	@Override
	public PacketHandler<TotemEffectPacket> getHandler() {
		return HANDLER;
	}

	public static class Handler implements PacketHandler<TotemEffectPacket>
	{
		public PacketContext handle(final TotemEffectPacket packet) {
			return (player, level) -> {
				Entity entity = level.getEntityById(packet.entityId());

				if (
					entity == null
					|| player.getId() != entity.getId()
				) {
					return;
				}

				if (packet.itemStack.getItem() == FriendsAndFoesItems.TOTEM_OF_FREEZING.get()) {
					TotemUtil.playActivateAnimation(packet.itemStack, entity, FriendsAndFoesParticleTypes.TOTEM_OF_FREEZING);
				} else if (packet.itemStack.getItem() == FriendsAndFoesItems.TOTEM_OF_ILLUSION.get()) {
					TotemUtil.playActivateAnimation(packet.itemStack, entity, FriendsAndFoesParticleTypes.TOTEM_OF_FREEZING);
				}
			};
		}

		public TotemEffectPacket decode(final PacketByteBuf buf) {
			FriendsAndFoes.getLogger().info(String.valueOf(buf));
			return new TotemEffectPacket(buf.readItemStack(), buf.readInt());
		}

		public void encode(final TotemEffectPacket packet, final PacketByteBuf buf) {
			buf.writeItemStack(packet.itemStack);
			buf.writeInt(packet.entityId);
		}
	}
}

