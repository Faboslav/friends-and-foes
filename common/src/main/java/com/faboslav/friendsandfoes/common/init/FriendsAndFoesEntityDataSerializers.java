package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.entity.pose.FriendsAndFoesEntityPose;
import com.faboslav.friendsandfoes.common.platform.PlatformHooks;
import net.minecraft.network.syncher.EntityDataSerializer;

/**
 * @see net.minecraft.network.syncher.EntityDataSerializers
 */
public class FriendsAndFoesEntityDataSerializers
{
	public static final EntityDataSerializer<FriendsAndFoesEntityPose> ENTITY_POSE = EntityDataSerializer.forValueType(FriendsAndFoesEntityPose.STREAM_CODEC);

	public static void init() {
		PlatformHooks.ENTITY_SERIALIZERS.register("entity_pose", ENTITY_POSE);
	}
}
