package com.faboslav.friendsandfoes;

import com.faboslav.friendsandfoes.init.FriendAndFoesEntityRenderer;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityModelLayer;
import com.faboslav.friendsandfoes.init.FriendsAndFoesRenderType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public final class FriendsAndFoesClient
{
	@Environment(EnvType.CLIENT)
	public static void init() {
		FriendsAndFoesEntityModelLayer.init();
	}

	@Environment(EnvType.CLIENT)
	public static void postInit() {
		FriendAndFoesEntityRenderer.postInit();
		FriendsAndFoesRenderType.postInit();
	}
}
