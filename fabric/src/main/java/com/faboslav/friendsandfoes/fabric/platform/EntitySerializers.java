package com.faboslav.friendsandfoes.fabric.platform;

import net.minecraft.network.syncher.EntityDataSerializer;

//? if >= 1.21.5 {
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry;
import com.faboslav.friendsandfoes.common.FriendsAndFoes;
//?} else {
/*import net.minecraft.network.syncher.EntityDataSerializers;
*///?}

public final class EntitySerializers implements com.faboslav.friendsandfoes.common.platform.EntitySerializers
{
	@Override
	public void register(String id, EntityDataSerializer<?> serializer) {
		//? if >= 1.21.5 {
		FabricTrackedDataRegistry.register(FriendsAndFoes.makeID(id), serializer);
		//?} else {
		/*EntityDataSerializers.registerSerializer(serializer);
		*///?}
	}
}
