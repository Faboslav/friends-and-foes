package com.faboslav.friendsandfoes.common;

import com.faboslav.friendsandfoes.common.config.client.gui.FriendsAndFoesConfigScreen;
import com.faboslav.friendsandfoes.common.events.client.RegisterEntityLayersEvent;
import com.faboslav.friendsandfoes.common.events.client.RegisterEntityRenderersEvent;
import com.faboslav.friendsandfoes.common.events.client.RegisterParticlesEvent;
//? if <= 1.21.11 {
/*import com.faboslav.friendsandfoes.common.events.client.RegisterRenderLayersEvent;
*///?}
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityRenderers;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesParticleTypes;
import com.faboslav.friendsandfoes.common.platform.PlatformHooks;
import net.minecraft.client.gui.screens.Screen;

public final class FriendsAndFoesClient
{
	public static void init() {
		RegisterParticlesEvent.EVENT.addListener(FriendsAndFoesParticleTypes::registerParticlesEvent);
		RegisterEntityRenderersEvent.EVENT.addListener(FriendsAndFoesEntityRenderers::registerEntityRenderers);
		//? if <= 1.21.11 {
		/*RegisterRenderLayersEvent.EVENT.addListener(FriendsAndFoesBlocks::registerRenderLayers);
		*///?}
		RegisterEntityLayersEvent.EVENT.addListener(FriendsAndFoesEntityModelLayers::registerEntityLayers);
	}

	public static Screen getConfigScreen(Screen screen) {
		if(!PlatformHooks.PLATFORM_HELPER.isModLoaded("yet_another_config_lib_v3")) {
			return null;
		}

		return new FriendsAndFoesConfigScreen().generateScreen(screen);
	}
}
