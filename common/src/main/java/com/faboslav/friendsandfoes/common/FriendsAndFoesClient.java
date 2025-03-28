package com.faboslav.friendsandfoes.common;

import com.faboslav.friendsandfoes.common.events.client.RegisterEntityLayersEvent;
import com.faboslav.friendsandfoes.common.events.client.RegisterEntityRenderersEvent;
import com.faboslav.friendsandfoes.common.events.client.RegisterParticlesEvent;
import com.faboslav.friendsandfoes.common.events.client.RegisterRenderLayersEvent;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityRenderers;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesParticleTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class FriendsAndFoesClient
{
	public static void init() {
		RegisterParticlesEvent.EVENT.addListener(FriendsAndFoesParticleTypes::registerParticlesEvent);
		RegisterEntityRenderersEvent.EVENT.addListener(FriendsAndFoesEntityRenderers::registerEntityRenderers);
		RegisterRenderLayersEvent.EVENT.addListener(FriendsAndFoesBlocks::registerRenderLayers);
		RegisterEntityLayersEvent.EVENT.addListener(FriendsAndFoesEntityModelLayers::registerEntityLayers);
	}
}
