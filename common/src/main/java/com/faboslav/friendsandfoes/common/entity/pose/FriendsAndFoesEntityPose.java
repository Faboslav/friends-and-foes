package com.faboslav.friendsandfoes.common.entity.pose;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;

import java.util.function.IntFunction;

public enum FriendsAndFoesEntityPose
{
	IDLE(0),
	SPIN_HEAD(1),
	PRESS_BUTTON_UP(2),
	PRESS_BUTTON_DOWN(3),
	WAVE(4),
	DANCE(5),
	NOD(6),
	GIVE_REWARD(7),
	STANDING(8),
	STANDING_WITH_ITEM(9),
	SLEEPING(10),
	SLEEPING_WITH_ITEM(11),
	SHOCKWAVE(12);

	public static final IntFunction<FriendsAndFoesEntityPose> BY_ID = ByIdMap.continuous(FriendsAndFoesEntityPose::id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
	public static final StreamCodec<ByteBuf, FriendsAndFoesEntityPose> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, FriendsAndFoesEntityPose::id);

	private final int id;

	FriendsAndFoesEntityPose(int id) {
		this.id = id;
	}

	public int id() {
		return this.id;
	}
}