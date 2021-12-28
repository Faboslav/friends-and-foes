package com.faboslav.friendsandfoes;

import com.faboslav.friendsandfoes.init.RegistryInit;
import net.fabricmc.api.ClientModInitializer;

public class FriendsAndFoesClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient() {
		RegistryInit.initClient();
	}
}
