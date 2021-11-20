package com.faboslav.friendsandfoes;

import com.faboslav.friendsandfoes.init.RegistryInit;
import com.faboslav.friendsandfoes.util.ServerTickDeltaCounter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.util.Util;

public class FriendsAndFoes implements ModInitializer
{
    // Find better place for this
    public static final ServerTickDeltaCounter serverTickDeltaCounter = new ServerTickDeltaCounter(20.0F, 0L);

    @Override
    public void onInitialize() {
        RegistryInit.init();

        ServerTickEvents.START_SERVER_TICK.register((server) -> {
            serverTickDeltaCounter.beginRenderTick(Util.getMeasuringTimeMs());
        });
    }
}