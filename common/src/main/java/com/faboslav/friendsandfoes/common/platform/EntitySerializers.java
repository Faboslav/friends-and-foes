package com.faboslav.friendsandfoes.common.platform;

import net.minecraft.network.syncher.EntityDataSerializer;

public interface EntitySerializers
{
	void register(String id, EntityDataSerializer<?> serializer);
}
