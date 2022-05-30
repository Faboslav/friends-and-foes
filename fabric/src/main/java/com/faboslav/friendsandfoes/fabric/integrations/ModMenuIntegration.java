package com.faboslav.friendsandfoes.fabric.integrations;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.config.ConfigScreenBuilder;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class ModMenuIntegration implements ModMenuApi
{
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return (parent) -> ConfigScreenBuilder.createConfigScreen(FriendsAndFoes.getConfig(), parent);
	}
}