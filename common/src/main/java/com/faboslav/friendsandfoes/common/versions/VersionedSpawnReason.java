package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.entity.SpawnReason;

public class VersionedSpawnReason
{
	public static final SpawnReason SPAWN_EGG;

	static {
		/*? >=1.21.3 {*/
		SPAWN_EGG = SpawnReason.SPAWN_ITEM_USE;
		/*?} else {*/
		/*SPAWN_EGG = SpawnReason.SPAWN_EGG;
		*//*?}*/
	}
}
