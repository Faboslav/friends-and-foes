package com.faboslav.friendsandfoes.fabric.modcompat;

//? if modMenu {
import com.faboslav.friendsandfoes.common.FriendsAndFoesClient;
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

			return FriendsAndFoesClient.getConfigScreen(screen);
		};
	}
}
//?}