package com.faboslav.friendsandfoes.fabric;

import com.faboslav.friendsandfoes.common.FriendsAndFoesClient;
import com.faboslav.friendsandfoes.common.events.client.RegisterEntityLayersEvent;
import com.faboslav.friendsandfoes.common.events.client.RegisterEntityRenderersEvent;
import com.faboslav.friendsandfoes.common.events.client.RegisterItemColorEvent;
import com.faboslav.friendsandfoes.common.events.client.RegisterRenderLayersEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.ClientSetupEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public final class FriendsAndFoesFabricClient implements ClientModInitializer
{
	@Override
	@Environment(EnvType.CLIENT)
	public void onInitializeClient() {
		FriendsAndFoesClient.init();
		this.initEvents();
	}

	private void initEvents() {
		ClientSetupEvent.EVENT.invoke(new ClientSetupEvent(Runnable::run));
		RegisterEntityRenderersEvent.EVENT.invoke(new RegisterEntityRenderersEvent(EntityRendererRegistry::register));
		RegisterRenderLayersEvent.EVENT.invoke(new RegisterRenderLayersEvent(BlockRenderLayerMap.INSTANCE::putFluid, BlockRenderLayerMap.INSTANCE::putBlock));
		RegisterEntityLayersEvent.EVENT.invoke(new RegisterEntityLayersEvent((type, supplier) -> EntityModelLayerRegistry.registerModelLayer(type, supplier::get)));
		RegisterItemColorEvent.EVENT.invoke(
			new RegisterItemColorEvent(
				ColorProviderRegistry.ITEM::register,
				(state, level, pos, i) -> ColorProviderRegistry.BLOCK.get(state.getBlock()).getColor(state, level, pos, i)
			)
		);
	}
}

