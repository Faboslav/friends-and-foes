package com.faboslav.friendsandfoes.fabric.modcompat;

import com.faboslav.friendsandfoes.common.config.FriendsAndFoesConfigScreen;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.gui.screens.Screen;

public final class CatalogueCompat
{
	public static Screen createConfigScreen(Screen screen, ModContainer container) {
		if (
			!FabricLoader.getInstance().isModLoaded("catalogue")
			|| !FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")
		) {
			return null;
		}

		return new FriendsAndFoesConfigScreen(screen);
	}
}
