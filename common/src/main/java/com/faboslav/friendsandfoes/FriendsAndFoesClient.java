package com.faboslav.friendsandfoes;

import com.faboslav.friendsandfoes.events.client.RegisterEntityLayersEvent;
import com.faboslav.friendsandfoes.events.client.RegisterEntityRenderersEvent;
import com.faboslav.friendsandfoes.events.client.RegisterParticlesEvent;
import com.faboslav.friendsandfoes.events.client.RegisterRenderLayersEvent;
import com.faboslav.friendsandfoes.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityModelLayers;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityRenderers;
import com.faboslav.friendsandfoes.init.FriendsAndFoesParticleTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public final class FriendsAndFoesClient
{
	@Environment(EnvType.CLIENT)
	public static void init() {
		RegisterParticlesEvent.EVENT.addListener(FriendsAndFoesParticleTypes::registerParticlesEvent);
		RegisterEntityRenderersEvent.EVENT.addListener(FriendsAndFoesEntityRenderers::registerEntityRenderers);
		RegisterRenderLayersEvent.EVENT.addListener(FriendsAndFoesBlocks::registerRenderLayers);
		RegisterEntityLayersEvent.EVENT.addListener(FriendsAndFoesEntityModelLayers::registerEntityLayers);
	}
}
