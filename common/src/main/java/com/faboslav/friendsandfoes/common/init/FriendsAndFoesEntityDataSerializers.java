package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.platform.PlatformHooks;
import net.minecraft.network.syncher.EntityDataSerializer;

public class FriendsAndFoesEntityDataSerializers
{
	//? if >= 1.21.9 {
	public static final EntityDataSerializer<CopperGolemEntity.EntitySnapshot> COPPER_GOLEM_SNAPSHOT = EntityDataSerializer.forValueType(CopperGolemEntity.EntitySnapshot.STREAM_CODEC);

	public static void init() {
		PlatformHooks.ENTITY_SERIALIZERS.register("copper_golem_snapshot", COPPER_GOLEM_SNAPSHOT);
	}
	//?}
}
