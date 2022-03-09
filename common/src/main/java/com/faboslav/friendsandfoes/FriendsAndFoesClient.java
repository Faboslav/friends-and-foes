package com.faboslav.friendsandfoes;

import com.faboslav.friendsandfoes.init.ModEntityModelLayer;
import com.faboslav.friendsandfoes.init.ModEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public final class FriendsAndFoesClient
{
	@Environment(EnvType.CLIENT)
	public static void init() {
		ModEntityRenderer.init();
		ModEntityModelLayer.init();
	}
}
