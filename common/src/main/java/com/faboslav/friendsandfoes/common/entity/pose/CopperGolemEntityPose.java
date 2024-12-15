package com.faboslav.friendsandfoes.common.entity.pose;

import net.minecraft.world.entity.Pose;

public enum CopperGolemEntityPose
{
	IDLE,
	SPIN_HEAD,
	PRESS_BUTTON_UP,
	PRESS_BUTTON_DOWN;

	private int index = 0;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return "COPPER_GOLEM_" + this.name();
	}

	public Pose get() {
		return Pose.valueOf(this.getName());
	}

	static {
		Pose.values();
	}
}