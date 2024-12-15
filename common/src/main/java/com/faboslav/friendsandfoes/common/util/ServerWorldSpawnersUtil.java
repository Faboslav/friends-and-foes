package com.faboslav.friendsandfoes.common.util;

import com.faboslav.friendsandfoes.common.mixin.ServerWorldAccessor;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.CustomSpawner;

public final class ServerWorldSpawnersUtil
{
	public static void register(ServerLevel world, CustomSpawner spawner) {
		List<CustomSpawner> spawnerList = new ArrayList<>(((ServerWorldAccessor) world).getSpawners());
		spawnerList.add(spawner);
		((ServerWorldAccessor) world).setSpawners(spawnerList);
	}

	private ServerWorldSpawnersUtil() {
	}
}
