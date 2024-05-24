package com.faboslav.friendsandfoes.platform.forge;

import com.faboslav.friendsandfoes.common.platform.CustomSpawnGroup;
import net.minecraft.entity.SpawnGroup;

public final class CustomSpawnGroupImpl
{
	public static SpawnGroup GLARES;

	public static SpawnGroup RASCALS;

	/**
	 * @see CustomSpawnGroup#getGlaresCategory()
	 */
	public static SpawnGroup getGlaresCategory() {
		var spawnGroup = SpawnGroup.byName(CustomSpawnGroup.GLARES_NAME);

		if (spawnGroup == null) {
			spawnGroup = SpawnGroup.create(
				CustomSpawnGroup.GLARES_NAME,
				CustomSpawnGroup.GLARES_NAME,
				CustomSpawnGroup.GLARES_SPAWN_CAP,
				CustomSpawnGroup.GLARES_PEACEFUL,
				CustomSpawnGroup.GLARES_RARE,
				CustomSpawnGroup.GLARES_IMMEDIATE_DESPAWN_RANGE
			);
		}

		return spawnGroup;
	}

	/**
	 * @see CustomSpawnGroup#getRascalsCategory()
	 */
	public static SpawnGroup getRascalsCategory() {
		var spawnGroup = SpawnGroup.byName(CustomSpawnGroup.RASCALS_NAME);

		if (spawnGroup == null) {
			spawnGroup = SpawnGroup.create(
				CustomSpawnGroup.RASCALS_NAME,
				CustomSpawnGroup.RASCALS_NAME,
				CustomSpawnGroup.RASCALS_SPAWN_CAP,
				CustomSpawnGroup.RASCALS_PEACEFUL,
				CustomSpawnGroup.RASCALS_RARE,
				CustomSpawnGroup.RASCALS_IMMEDIATE_DESPAWN_RANGE
			);
		}

		return spawnGroup;
	}
}
