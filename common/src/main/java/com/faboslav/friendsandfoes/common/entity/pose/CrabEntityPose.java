package com.faboslav.friendsandfoes.common.entity.pose;

import net.minecraft.world.entity.Pose;

public enum CrabEntityPose
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