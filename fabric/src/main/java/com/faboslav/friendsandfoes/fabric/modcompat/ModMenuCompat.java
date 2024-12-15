package com.faboslav.friendsandfoes.fabric.modcompat;

import com.faboslav.friendsandfoes.common.config.FriendsAndFoesConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.loader.api.FabricLoader;

public final class ModMenuCompat implements ModMenuApi
{
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return (screen) -> {
			if (!FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
				return null;
			}

			return new FriendsAndFoesConfigScreen(screen);
		};
	}
}