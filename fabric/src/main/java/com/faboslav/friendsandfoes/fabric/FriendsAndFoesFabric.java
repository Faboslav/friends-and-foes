package com.faboslav.friendsandfoes.fabric;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import net.fabricmc.api.ModInitializer;

public class FriendsAndFoesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
		FriendsAndFoes.init();
    }
}
