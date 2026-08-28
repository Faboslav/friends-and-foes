package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.entity.pose.FriendsAndFoesEntityPose;
import com.faboslav.friendsandfoes.common.platform.PlatformHooks;
import net.minecraft.network.syncher.EntityDataSerializer;

/**
 * @see net.minecraft.network.syncher.EntityDataSerializers
 */
public class FriendsAndFoesEntityDataSerializers
{
	//? if >= 1.21.1 {
	public static final EntityDataSerializer<FriendsAndFoesEntityPose> ENTITY_POSE = EntityDataSerializer.forValueType(FriendsAndFoesEntityPose.STREAM_CODEC);
	//?} else {
	/*public static final EntityDataSerializer<FriendsAndFoesEntityPose> ENTITY_POSE = EntityDataSerializer.simple(
		(buf, pose) -> buf.writeVarInt(pose.id()),
		(buf) -> FriendsAndFoesEntityPose.BY_ID.apply(buf.readVarInt())
	);
	*///?}

	public static void init() {
		PlatformHooks.ENTITY_SERIALIZERS.register("entity_pose", ENTITY_POSE);
	}
}
