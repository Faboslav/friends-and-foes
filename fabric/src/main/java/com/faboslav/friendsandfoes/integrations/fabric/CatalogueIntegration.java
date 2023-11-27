package com.faboslav.friendsandfoes.integrations.fabric;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.config.ConfigScreenBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.gui.screen.Screen;

public class CatalogueIntegration
{
	public static Screen createConfigScreen(Screen currentScreen, ModContainer container) {
		if (FabricLoader.getInstance().isModLoaded("catalogue") == false) {
			return null;
		}

		return ConfigScreenBuilder.createConfigScreen(FriendsAndFoes.getConfig(), currentScreen);
	}
}
