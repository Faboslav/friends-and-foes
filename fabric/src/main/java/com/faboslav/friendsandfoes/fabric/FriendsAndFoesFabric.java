package com.faboslav.friendsandfoes.fabric;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.init.FriendsAndFoesPointOfInterestTypes;
import com.faboslav.friendsandfoes.init.FriendsAndFoesStructurePoolElements;
import com.faboslav.friendsandfoes.util.ServerWorldSpawnersUtil;
import com.faboslav.friendsandfoes.util.UpdateChecker;
import com.faboslav.friendsandfoes.world.spawner.IceologerSpawner;
import com.faboslav.friendsandfoes.world.spawner.IllusionerSpawner;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.util.Util;
import net.minecraft.world.dimension.DimensionTypes;

import static com.faboslav.friendsandfoes.FriendsAndFoes.serverTickDeltaCounter;

public final class FriendsAndFoesFabric implements ModInitializer
{
	@Override
	public void onInitialize() {
		UpdateChecker.checkForNewUpdates();
		FriendsAndFoes.init();
		FriendsAndFoes.postInit();
		FriendsAndFoesPointOfInterestTypes.postInit();

		initSpawners();
		initTickDeltaCounter();
		addCustomStructurePoolElements();
	}

	private static void initSpawners() {
		ServerWorldEvents.LOAD.register(((server, world) -> {
			if (world.isClient() || world.getDimensionKey() != DimensionTypes.OVERWORLD) {
				return;
			}

			ServerWorldSpawnersUtil.register(world, new IceologerSpawner());
			ServerWorldSpawnersUtil.register(world, new IllusionerSpawner());
		}));
	}

	private static void initTickDeltaCounter() {
		ServerTickEvents.START_SERVER_TICK.register((server) -> {
			serverTickDeltaCounter.beginRenderTick(Util.getMeasuringTimeMs());
		});
	}

	private static void addCustomStructurePoolElements() {
		ServerLifecycleEvents.SERVER_STARTING.register(FriendsAndFoesStructurePoolElements::init);
	}
}
