package com.faboslav.friendsandfoes.common.network.defaults;

import com.faboslav.friendsandfoes.common.network.Packet;
import java.util.function.Supplier;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public abstract class DatalessPacketType<T extends Packet<T>> extends AbstractPacketType<T>
{
	protected final Supplier<T> factory;

	public DatalessPacketType(Class<T> clazz, ResourceLocation id, Supplier<T> factory) {
		super(clazz, id);
		this.factory = factory;
	}

	@Override
	public void encode(T message, RegistryFriendlyByteBuf buffer) {

	}

	@Override
	public T decode(RegistryFriendlyByteBuf buffer) {
		return factory.get();
	}
}
