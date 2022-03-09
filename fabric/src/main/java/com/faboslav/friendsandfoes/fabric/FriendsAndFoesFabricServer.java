package com.faboslav.friendsandfoes.fabric;

import com.faboslav.friendsandfoes.FriendsAndFoesServer;
import com.faboslav.friendsandfoes.util.ServerTickDeltaCounter;
import net.minecraft.util.Util;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class FriendsAndFoesFabricServer implements DedicatedServerModInitializer
{
	public static final ServerTickDeltaCounter serverTickDeltaCounter = new ServerTickDeltaCounter(20.0F, 0L);

	@Override
	@Environment(EnvType.SERVER)
	public void onInitializeServer() {
		FriendsAndFoesServer.init();
		/*
		ServerTickEvents.START_SERVER_TICK.register((server) -> {
			serverTickDeltaCounter.beginRenderTick(Util.getMeasuringTimeMs());
		});*/
	}
}
