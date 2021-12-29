package com.faboslav.friendsandfoes;

import com.faboslav.friendsandfoes.util.ServerTickDeltaCounter;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.util.Util;

public class FriendsAndFoesServer implements DedicatedServerModInitializer
{
	public static final ServerTickDeltaCounter serverTickDeltaCounter = new ServerTickDeltaCounter(20.0F, 0L);

	@Override
	public void onInitializeServer() {
		ServerTickEvents.START_SERVER_TICK.register((server) -> {
			serverTickDeltaCounter.beginRenderTick(Util.getMeasuringTimeMs());
		});
	}
}
