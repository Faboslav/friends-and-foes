package com.faboslav.friendsandfoes.entity.pose;

import net.minecraft.entity.EntityPose;

public enum TuffGolemEntityPose
{
	STANDING,
	STANDING_WITH_ITEM,
	SLEEPING,
	SLEEPING_WITH_ITEM;

	public String getName() {
		return "TUFF_GOLEM_" + this.name();
	}

	public EntityPose get() {
		return EntityPose.valueOf(this.getName());
	}

	static {
		EntityPose.values();
	}
}