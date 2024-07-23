package com.faboslav.friendsandfoes.common.entity.pose;

import net.minecraft.entity.EntityPose;

public enum BarnacleEntityPose
{
	IDLE;

	public String getName() {
		return "BARNACLE_" + this.name();
	}

	public EntityPose get() {
		return EntityPose.valueOf(this.getName());
	}

	static {
		EntityPose.values();
	}
}