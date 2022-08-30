package com.faboslav.friendsandfoes.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.SpawnGroup;

public final class CustomSpawnGroup
{
	public static final String GLARES_INTERNAL_NAME = "GLARES";
	public static final String NAME = "glares";
	public static final int SPAWN_CAP = 15;
	public static final boolean PEACEFUL = true;
	public static final boolean RARE = false;
	public static final int IMMEDIATE_DESPAWN_RANGE = 128;

	@ExpectPlatform
	public static SpawnGroup getGlaresCategory() {
		throw new AssertionError();
	}
}
