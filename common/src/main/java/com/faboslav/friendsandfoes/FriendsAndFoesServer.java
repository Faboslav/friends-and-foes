package com.faboslav.friendsandfoes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public final class FriendsAndFoesServer
{
	@Environment(EnvType.SERVER)
	public static void init() {
		// TODO fabric and forge
		/*
		ServerTickEvents.START_SERVER_TICK.register((server) -> {
			serverTickDeltaCounter.beginRenderTick(Util.getMeasuringTimeMs());
		});
		 */
	}
}
