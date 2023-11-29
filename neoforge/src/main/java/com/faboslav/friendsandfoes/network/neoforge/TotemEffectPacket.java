package com.faboslav.friendsandfoes.network.neoforge;

import com.faboslav.friendsandfoes.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.init.FriendsAndFoesParticleTypes;
import com.faboslav.friendsandfoes.util.TotemUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.network.NetworkEvent;

@SuppressWarnings({"removal"})
public class TotemEffectPacket
{
	private final ItemStack itemStack;
	private final Entity entity;

	public TotemEffectPacket(PacketByteBuf buf) {
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		this.itemStack = buf.readItemStack();
		this.entity = minecraftClient.world.getEntityById(buf.readInt());
	}

	public TotemEffectPacket(ItemStack itemStack, Entity entity) {
		this.itemStack = itemStack;
		this.entity = entity;
	}

	public void encode(PacketByteBuf buf) {
		buf.writeItemStack(itemStack);
		buf.writeInt(entity.getId());
	}

	public static void handle(TotemEffectPacket msg, NetworkEvent.ClientCustomPayloadEvent.Context ctx) {
		if (msg.itemStack.getItem() == FriendsAndFoesItems.TOTEM_OF_FREEZING.get()) {
			ctx.enqueueWork(() -> net.neoforged.fml.DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> TotemUtil.playActivateAnimation(msg.itemStack, msg.entity, FriendsAndFoesParticleTypes.TOTEM_OF_FREEZING)));
		} else if (msg.itemStack.getItem() == FriendsAndFoesItems.TOTEM_OF_ILLUSION.get()) {
			ctx.enqueueWork(() -> net.neoforged.fml.DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> TotemUtil.playActivateAnimation(msg.itemStack, msg.entity, FriendsAndFoesParticleTypes.TOTEM_OF_ILLUSION)));
		}

		ctx.setPacketHandled(true);
	}
}
