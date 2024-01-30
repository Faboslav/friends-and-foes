package com.faboslav.friendsandfoes.util;

import com.faboslav.friendsandfoes.mixin.ServerWorldAccessor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.spawner.SpecialSpawner;

import java.util.ArrayList;
import java.util.List;

public final class ServerWorldSpawnersUtil
{
	public static void register(ServerWorld world, SpecialSpawner spawner) {
		List<SpecialSpawner> spawnerList = new ArrayList<>(((ServerWorldAccessor) world).getSpawners());
		spawnerList.add(spawner);
		((ServerWorldAccessor) world).setSpawners(spawnerList);
	}

	private ServerWorldSpawnersUtil() {
	}
}
