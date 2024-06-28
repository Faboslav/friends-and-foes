package com.faboslav.friendsandfoes.network.defaults;

import com.faboslav.friendsandfoes.network.Packet;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public abstract class DatalessPacketType<T extends Packet<T>> extends AbstractPacketType<T>
{

	protected final Supplier<T> factory;

	public DatalessPacketType(Class<T> clazz, Identifier id, Supplier<T> factory) {
		super(clazz, id);
		this.factory = factory;
	}

	@Override
	public void encode(T message, RegistryByteBuf buffer) {

	}

	@Override
	public T decode(RegistryByteBuf buffer) {
		return factory.get();
	}
}
