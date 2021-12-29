package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.config.Settings;
import com.google.common.collect.ImmutableList;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.util.Identifier;

public class StructurePoolInit
{
	public static void init() {
		if (FriendsAndFoes.CONFIG.generateBeekeeperArea) {
			System.out.println("register");
			StructurePools.register(
				new StructurePool(
					Settings.makeID("village/common/bee"),
					new Identifier("empty"),
					ImmutableList.of(),
					StructurePool.Projection.RIGID
				)
			);
		}
	}

}