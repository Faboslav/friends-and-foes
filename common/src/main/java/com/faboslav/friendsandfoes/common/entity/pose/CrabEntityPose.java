package com.faboslav.friendsandfoes.common.entity.pose;

import net.minecraft.world.entity.Pose;

public enum CrabEntityPose implements SpecificEntityPose
{
	IDLE,
	WAVE,
	DANCE;

	public String getName() {
		return "CRAB_" + this.name();
	}

	public Pose get() {
		return Pose.valueOf(this.getName());
	}

	static {
		Pose.values();
	}
}