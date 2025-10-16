package com.faboslav.friendsandfoes.fabric.platform;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import net.minecraft.network.syncher.EntityDataSerializer;

public final class EntitySerializers implements com.faboslav.friendsandfoes.common.platform.EntitySerializers
{
	@Override
	public void register(String id, EntityDataSerializer<?> serializer) {
		//? if >= 1.21.9 {
		net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry.register(FriendsAndFoes.makeID(id), serializer);
		//?}
	}
}
