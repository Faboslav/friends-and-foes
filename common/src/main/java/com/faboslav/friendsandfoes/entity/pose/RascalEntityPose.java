package com.faboslav.friendsandfoes.entity.pose;

import net.minecraft.entity.EntityPose;

public enum RascalEntityPose
{
	IDLE,
	NOD,
	GIVE_REWARD;

	public String getName() {
		return "RASCAL_" + this.name();
	}

	public EntityPose get() {
		return EntityPose.valueOf(this.getName());
	}

	static {
		EntityPose.values();
	}
}