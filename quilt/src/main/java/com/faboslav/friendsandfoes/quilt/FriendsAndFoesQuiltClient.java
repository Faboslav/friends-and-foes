package com.faboslav.friendsandfoes.quilt;

import com.faboslav.friendsandfoes.FriendsAndFoesClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public final class FriendsAndFoesQuiltClient implements ClientModInitializer
{
	@Override
	@Environment(EnvType.CLIENT)
	public void onInitializeClient(ModContainer mod) {
		FriendsAndFoesClient.init();
		FriendsAndFoesClient.postInit();
	}
}

