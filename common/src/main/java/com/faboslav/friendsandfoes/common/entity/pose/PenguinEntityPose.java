package com.faboslav.friendsandfoes.common.entity.pose;

import net.minecraft.world.entity.Pose;

public enum PenguinEntityPose implements SpecificEntityPose
{
	IDLE;

	public String getName() {
		return "PENGUIN_" + this.name();
	}

	public Pose get() {
		return Pose.valueOf(this.getName());
	}

	static {
		Pose.values();
	}
}