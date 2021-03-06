package com.faboslav.friendsandfoes;

import com.faboslav.friendsandfoes.init.ModEntityModelLayer;
import com.faboslav.friendsandfoes.init.ModEntityRenderer;
import com.faboslav.friendsandfoes.init.ModRenderType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public final class FriendsAndFoesClient
{
	@Environment(EnvType.CLIENT)
	public static void initRegisters() {
		ModEntityRenderer.init();
		ModEntityModelLayer.init();
	}

	@Environment(EnvType.CLIENT)
	public static void initCustomRegisters() {
		ModRenderType.init();
	}
}
