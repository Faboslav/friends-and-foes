package com.faboslav.friendsandfoes.platform.fabric;

import com.faboslav.friendsandfoes.platform.CustomSpawnGroup;
import net.minecraft.entity.SpawnGroup;

public final class CustomSpawnGroupImpl
{
	static {
		SpawnGroup.values();
	}

	public static SpawnGroup GLARES;

	/**
	 * @see CustomSpawnGroup#getGlaresCategory()
	 */
	public static SpawnGroup getGlaresCategory() {
		return GLARES;
	}
}
