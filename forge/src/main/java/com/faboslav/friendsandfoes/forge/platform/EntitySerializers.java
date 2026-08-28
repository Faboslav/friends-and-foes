package com.faboslav.friendsandfoes.forge.platform;

import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public final class EntitySerializers implements com.faboslav.friendsandfoes.common.platform.EntitySerializers
{
	@Override
	public void register(String id, EntityDataSerializer<?> serializer) {
		EntityDataSerializers.registerSerializer(serializer);
	}
}
