package com.faboslav.friendsandfoes.integrations.fabric;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.config.ConfigScreenBuilder;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.CLIENT)
public final class ModMenuIntegration implements ModMenuApi
{
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return (parent) -> {
			if (FabricLoader.getInstance().isModLoaded("cloth-config") == false) {
				return null;
			}

			return ConfigScreenBuilder.createConfigScreen(FriendsAndFoes.getConfig(), parent);
		};
	}
}