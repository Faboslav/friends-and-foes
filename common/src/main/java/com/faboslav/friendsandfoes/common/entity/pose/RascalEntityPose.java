package com.faboslav.friendsandfoes.common.entity.pose;

import net.minecraft.world.entity.Pose;

public enum RascalEntityPose implements SpecificEntityPose
{
	IDLE,
	NOD,
	GIVE_REWARD;

	public String getName() {
		return "RASCAL_" + this.name();
	}

	public Pose get() {
		return Pose.valueOf(this.getName());
	}

	static {
		Pose.values();
	}
}