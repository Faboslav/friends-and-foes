package com.faboslav.friendsandfoes.entity.pose;

import net.minecraft.entity.EntityPose;

public enum TuffGolemEntityPose
{
	DEFAULT,
	SHOWING_ITEM,
	SLEEPING;

	public String getName() {
		return "TUFF_GOLEM_" + this.name();
	}

	public EntityPose get() {
		return EntityPose.valueOf(this.getName());
	}
}