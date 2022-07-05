package com.faboslav.friendsandfoes.platform.forge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.platform.CustomSpawnGroup;
import net.minecraft.entity.SpawnGroup;

public final class CustomSpawnGroupImpl
{
	/**
	 * @see CustomSpawnGroup#getGlaresCategory()
	 */
	public static SpawnGroup getGlaresCategory() {
		var spawnGroup = SpawnGroup.byName(CustomSpawnGroup.GLARES_INTERNAL_NAME);

		if(spawnGroup == null) {
			spawnGroup = SpawnGroup.create(
				CustomSpawnGroup.GLARES_INTERNAL_NAME,
				FriendsAndFoes.makeStringID(CustomSpawnGroup.NAME),
				CustomSpawnGroup.SPAWN_CAP,
				CustomSpawnGroup.PEACEFUL,
				CustomSpawnGroup.RARE,
				CustomSpawnGroup.IMMEDIATE_DESPAWN_RANGE
			);
		}

		return spawnGroup;
	}
}
