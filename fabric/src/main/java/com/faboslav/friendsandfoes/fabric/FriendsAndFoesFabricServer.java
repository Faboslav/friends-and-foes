package com.faboslav.friendsandfoes.fabric;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.FriendsAndFoesServer;
import com.faboslav.friendsandfoes.util.ServerTickDeltaCounter;
import com.faboslav.friendsandfoes.util.ServerWorldSpawnersUtil;
import com.faboslav.friendsandfoes.world.spawner.IceologerSpawner;
import com.faboslav.friendsandfoes.world.spawner.IllusionerSpawner;
import net.minecraft.util.Util;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;

public class FriendsAndFoesFabricServer implements DedicatedServerModInitializer
{
	public static final ServerTickDeltaCounter serverTickDeltaCounter = new ServerTickDeltaCounter(20.0F, 0L);

	@Override
	@Environment(EnvType.SERVER)
	public void onInitializeServer() {
		FriendsAndFoesServer.init();

		FriendsAndFoes.LOGGER.info("server init");
		ServerWorldEvents.LOAD.register(((server, world) -> {
			FriendsAndFoes.LOGGER.info("registering spawners");
			ServerWorldSpawnersUtil.register(world, new IceologerSpawner());
			ServerWorldSpawnersUtil.register(world, new IllusionerSpawner());
		}));
		/*
		ServerTickEvents.START_SERVER_TICK.register((server) -> {
			serverTickDeltaCounter.beginRenderTick(Util.getMeasuringTimeMs());
		});*/
	}
}
