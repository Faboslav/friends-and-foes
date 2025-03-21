package com.faboslav.friendsandfoes.fabric.modcompat;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.config.ConfigScreenBuilder;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.loader.api.FabricLoader;

public final class ModMenuCompat implements ModMenuApi
{
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return (parent) -> {
			if (FabricLoader.getInstance().isModLoaded("cloth-config")) {
				return ConfigScreenBuilder.createConfigScreen(FriendsAndFoes.getConfig(), parent);
			}

			return null;
		};
	}
}