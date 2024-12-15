package com.faboslav.friendsandfoes.common.platform;

import net.minecraft.world.entity.MobCategory;

public final class CustomSpawnGroup
{
	public static MobCategory GLARES;
	public static MobCategory RASCALS;

	public static final String GLARES_INTERNAL_NAME = "GLARES";
	public static final String GLARES_NAME = "glares";
	public static final int GLARES_SPAWN_CAP = 15;
	public static final boolean GLARES_PEACEFUL = true;
	public static final boolean GLARES_RARE = false;
	public static final int GLARES_IMMEDIATE_DESPAWN_RANGE = 128;

	public static final String RASCALS_INTERNAL_NAME = "RASCALS";
	public static final String RASCALS_NAME = "rascals";
	public static final int RASCALS_SPAWN_CAP = 1;
	public static final boolean RASCALS_PEACEFUL = true;
	public static final boolean RASCALS_RARE = false;
	public static final int RASCALS_IMMEDIATE_DESPAWN_RANGE = 128;

	public static MobCategory getGlaresCategory() {
		return GLARES;
	}

	public static MobCategory getRascalsCategory() {
		return RASCALS;
	}

	private CustomSpawnGroup() {
	}
}
