package com.faboslav.friendsandfoes.common.entity.pose;

import net.minecraft.world.entity.Pose;

public enum WildfireEntityPose implements SpecificEntityPose
{
	IDLE,
	SHOCKWAVE;

	public String getName() {
		return "WILDFIRE_" + this.name();
	}

	public Pose get() {
		return Pose.valueOf(this.getName());
	}

	static {
		Pose.values();
	}
}