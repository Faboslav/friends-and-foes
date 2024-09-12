package com.faboslav.friendsandfoes.common.entity.pose;

import net.minecraft.entity.EntityPose;

public enum CopperGolemEntityPose
{
	IDLE,
	SPIN_HEAD,
	PRESS_BUTTON_UP,
	PRESS_BUTTON_DOWN;

	public String getName() {
		return "COPPER_GOLEM_" + this.name();
	}

	public EntityPose get() {
		return EntityPose.valueOf(this.getName());
	}

	static {
		EntityPose.values();
	}
}