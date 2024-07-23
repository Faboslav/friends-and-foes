package com.faboslav.friendsandfoes.common.entity.pose;

import net.minecraft.entity.EntityPose;

public enum CrabEntityPose implements CustomEntityPose
{
	IDLE,
	WAVE;

	public String getName() {
		return "CRAB_" + this.name();
	}

	public EntityPose get() {
		return EntityPose.valueOf(this.getName());
	}

	static {
		EntityPose.values();
	}
}