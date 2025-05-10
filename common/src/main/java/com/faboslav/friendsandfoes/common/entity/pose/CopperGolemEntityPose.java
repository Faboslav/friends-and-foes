package com.faboslav.friendsandfoes.common.entity.pose;

import net.minecraft.world.entity.Pose;

public enum CopperGolemEntityPose implements SpecificEntityPose
{
	IDLE,
	SPIN_HEAD,
	PRESS_BUTTON_UP,
	PRESS_BUTTON_DOWN;

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