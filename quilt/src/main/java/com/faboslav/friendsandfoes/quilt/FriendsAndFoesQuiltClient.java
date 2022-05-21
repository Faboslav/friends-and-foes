package com.faboslav.friendsandfoes.quilt;

import com.faboslav.friendsandfoes.FriendsAndFoesClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public final class FriendsAndFoesQuiltClient implements ClientModInitializer
{
	@Override
	@Environment(EnvType.CLIENT)
	public void onInitializeClient() {
		FriendsAndFoesClient.initRegisters();
		FriendsAndFoesClient.initCustomRegisters();
	}
}

