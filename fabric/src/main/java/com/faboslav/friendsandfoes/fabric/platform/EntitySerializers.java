package com.faboslav.friendsandfoes.fabric.platform;

import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public final class EntitySerializers implements com.faboslav.friendsandfoes.common.platform.EntitySerializers
{
	@Override
	public void register(String id, EntityDataSerializer<?> serializer) {
		//? if >= 1.21.9 {
		FabricTrackedDataRegistry.register(FriendsAndFoes.makeID(id), serializer);
		//?} else {
		/*EntityDataSerializers.registerSerializer(serializer);
		*///?}
	}
}
