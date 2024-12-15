package com.faboslav.friendsandfoes.common.network.defaults;

import com.faboslav.friendsandfoes.common.network.Packet;
import com.faboslav.friendsandfoes.common.network.base.PacketType;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractPacketType<T extends Packet<T>> implements PacketType<T>
{
	protected final Class<T> clazz;
	protected final ResourceLocation id;

	public AbstractPacketType(Class<T> clazz, ResourceLocation id) {
		this.clazz = clazz;
		this.id = id;
	}

	@Override
	public Class<T> type() {
		return clazz;
	}

	@Override
	public ResourceLocation id() {
		return id;
	}
}
