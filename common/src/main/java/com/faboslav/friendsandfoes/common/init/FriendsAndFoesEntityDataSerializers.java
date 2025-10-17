package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.platform.PlatformHooks;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

/**
 * @see net.minecraft.network.syncher.EntityDataSerializers
 */
public class FriendsAndFoesEntityDataSerializers
{
	//? if >= 1.21.9 {
	public static final EntityDataSerializer<CopperGolemEntity.EntitySnapshot> COPPER_GOLEM_SNAPSHOT = EntityDataSerializer.forValueType(CopperGolemEntity.EntitySnapshot.STREAM_CODEC);

	public static void init() {
		//EntityDataSerializers.registerSerializer(COPPER_GOLEM_SNAPSHOT);
		PlatformHooks.ENTITY_SERIALIZERS.register("copper_golem_snapshot", COPPER_GOLEM_SNAPSHOT);
	}
	//?}
}
