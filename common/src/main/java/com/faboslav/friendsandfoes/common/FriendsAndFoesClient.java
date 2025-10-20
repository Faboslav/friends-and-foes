package com.faboslav.friendsandfoes.common;

import com.faboslav.friendsandfoes.common.config.FriendsAndFoesConfig;
import com.faboslav.friendsandfoes.common.events.client.RegisterEntityLayersEvent;
import com.faboslav.friendsandfoes.common.events.client.RegisterEntityRenderersEvent;
import com.faboslav.friendsandfoes.common.events.client.RegisterParticlesEvent;
import com.faboslav.friendsandfoes.common.events.client.RegisterRenderLayersEvent;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityRenderers;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesParticleTypes;
import net.minecraft.client.gui.screens.Screen;

public final class FriendsAndFoesClient
{
	public static void init() {
		RegisterParticlesEvent.EVENT.addListener(FriendsAndFoesParticleTypes::registerParticlesEvent);
		RegisterEntityRenderersEvent.EVENT.addListener(FriendsAndFoesEntityRenderers::registerEntityRenderers);
		RegisterRenderLayersEvent.EVENT.addListener(FriendsAndFoesBlocks::registerRenderLayers);
		RegisterEntityLayersEvent.EVENT.addListener(FriendsAndFoesEntityModelLayers::registerEntityLayers);
	}

	public static Screen getConfigScreen(Screen parentScreen) {
		return FriendsAndFoesConfig.HANDLER.generateGui().generateScreen(parentScreen);
	}
}
