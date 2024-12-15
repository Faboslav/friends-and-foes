package com.faboslav.friendsandfoes.common.entity.pose;

import net.minecraft.world.entity.Pose;

public enum TuffGolemEntityPose
{
	STANDING,
	STANDING_WITH_ITEM,
	SLEEPING,
	SLEEPING_WITH_ITEM;

	private int index = 0;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

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