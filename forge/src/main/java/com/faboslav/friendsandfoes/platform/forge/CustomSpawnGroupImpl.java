package com.faboslav.friendsandfoes.platform.forge;

import com.faboslav.friendsandfoes.platform.CustomSpawnGroup;
import net.minecraft.entity.SpawnGroup;

public final class CustomSpawnGroupImpl
{
	public static SpawnGroup GLARES;

	/**
	 * @see CustomSpawnGroup#getGlaresCategory()
	 */
	public static SpawnGroup getGlaresCategory() {
		var spawnGroup = SpawnGroup.byName(CustomSpawnGroup.NAME);

		if (spawnGroup == null) {
			spawnGroup = SpawnGroup.create(
				CustomSpawnGroup.NAME,
				CustomSpawnGroup.NAME,
				CustomSpawnGroup.SPAWN_CAP,
				CustomSpawnGroup.PEACEFUL,
				CustomSpawnGroup.RARE,
				CustomSpawnGroup.IMMEDIATE_DESPAWN_RANGE
			);
		}

		return spawnGroup;
	}
}
