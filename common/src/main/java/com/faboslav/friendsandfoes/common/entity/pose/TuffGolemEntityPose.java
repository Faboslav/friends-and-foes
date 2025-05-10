package com.faboslav.friendsandfoes.common.entity.pose;

import net.minecraft.world.entity.Pose;

public enum TuffGolemEntityPose implements SpecificEntityPose
{
	STANDING,
	STANDING_WITH_ITEM,
	SLEEPING,
	SLEEPING_WITH_ITEM;

	public String getName() {
		return "TUFF_GOLEM_" + this.name();
	}

	public Pose get() {
		return Pose.valueOf(this.getName());
	}

	static {
		Pose.values();
	}
}