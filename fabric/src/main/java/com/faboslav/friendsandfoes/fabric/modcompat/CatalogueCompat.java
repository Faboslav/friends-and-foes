package com.faboslav.friendsandfoes.fabric.modcompat;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.config.ConfigScreenBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.gui.screen.Screen;

public final class CatalogueCompat
{
	public static Screen createConfigScreen(Screen currentScreen, ModContainer container) {
		if (!FabricLoader.getInstance().isModLoaded("catalogue")) {
			return null;
		}

		return ConfigScreenBuilder.createConfigScreen(FriendsAndFoes.getConfig(), currentScreen);
	}
}
