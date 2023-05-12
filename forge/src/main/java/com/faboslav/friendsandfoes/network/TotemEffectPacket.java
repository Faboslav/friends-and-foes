package com.faboslav.friendsandfoes.network;

import com.faboslav.friendsandfoes.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.init.FriendsAndFoesParticleTypes;
import com.faboslav.friendsandfoes.util.TotemUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

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

	public void handle(Supplier<NetworkEvent.Context> context) {
		if (itemStack.getItem() == FriendsAndFoesItems.TOTEM_OF_FREEZING.get()) {
			context.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> TotemUtil.playActivateAnimation(itemStack, entity, FriendsAndFoesParticleTypes.TOTEM_OF_FREEZING)));
		} else if (itemStack.getItem() == FriendsAndFoesItems.TOTEM_OF_ILLUSION.get()) {
			context.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> TotemUtil.playActivateAnimation(itemStack, entity, FriendsAndFoesParticleTypes.TOTEM_OF_ILLUSION)));
		}

		context.get().setPacketHandled(true);
	}
}
