package com.faboslav.friendsandfoes;

import com.faboslav.friendsandfoes.init.RegistryInit;
import net.fabricmc.api.ModInitializer;

public class FriendsAndFoes implements ModInitializer
{
	@Override
	public void onInitialize() {
		RegistryInit.init();
	}
}