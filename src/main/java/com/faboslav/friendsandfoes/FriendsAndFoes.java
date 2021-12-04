package com.faboslav.friendsandfoes;

import com.faboslav.friendsandfoes.init.RegistryInit;
import com.faboslav.friendsandfoes.init.StructurePoolInit;
import com.faboslav.friendsandfoes.util.ServerTickDeltaCounter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.util.Util;

public class FriendsAndFoes implements ModInitializer
{
    @Override
    public void onInitialize() {
        RegistryInit.init();
    }
}