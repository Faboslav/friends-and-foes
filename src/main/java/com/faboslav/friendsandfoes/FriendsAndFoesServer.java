package com.faboslav.friendsandfoes;

import com.faboslav.friendsandfoes.init.RegistryInit;
import com.faboslav.friendsandfoes.util.ServerTickDeltaCounter;
import net.minecraft.util.Util;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class FriendsAndFoesServer implements DedicatedServerModInitializer
{
    public static final ServerTickDeltaCounter serverTickDeltaCounter = new ServerTickDeltaCounter(20.0F, 0L);

    @Override
    public void onInitializeServer() {
        RegistryInit.initServer();

        ServerTickEvents.START_SERVER_TICK.register((server) -> {
            serverTickDeltaCounter.beginRenderTick(Util.getMeasuringTimeMs());
        });
    }
}
