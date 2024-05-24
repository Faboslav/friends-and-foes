package com.faboslav.friendsandfoes.entity.pose;

import net.minecraft.entity.EntityPose;

public enum PenguinEntityPose
{
	IDLE;

	public String getName() {
		return "PENGUIN_" + this.name();
	}

	public EntityPose get() {
		return EntityPose.valueOf(this.getName());
	}

	static {
		EntityPose.values();
	}
}