package com.faboslav.friendsandfoes.common.entity.pose;

import net.minecraft.world.entity.Pose;

public enum BarnacleEntityPose implements SpecificEntityPose
{
	IDLE;

	public String getName() {
		return "BARNACLE_" + this.name();
	}

	public Pose get() {
		return Pose.valueOf(this.getName());
	}

	static {
		Pose.values();
	}
}