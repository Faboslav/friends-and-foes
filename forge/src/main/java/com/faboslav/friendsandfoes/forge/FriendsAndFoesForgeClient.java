package com.faboslav.friendsandfoes.forge;

import com.faboslav.friendsandfoes.common.FriendsAndFoesClient;
import com.faboslav.friendsandfoes.common.events.client.RegisterEntityLayersEvent;
import com.faboslav.friendsandfoes.common.events.client.RegisterEntityRenderersEvent;
import com.faboslav.friendsandfoes.common.events.client.RegisterParticlesEvent;
import com.faboslav.friendsandfoes.common.events.client.RegisterRenderLayersEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.ClientSetupEvent;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Function;

@SuppressWarnings({"all", "deprecated", "removal"})
public final class FriendsAndFoesForgeClient
{
	public static void init(IEventBus modEventBus, IEventBus eventBus) {
		FriendsAndFoesClient.init();

		modEventBus.addListener(FriendsAndFoesForgeClient::onClientSetup);
		modEventBus.addListener(FriendsAndFoesForgeClient::onRegisterParticles);
		modEventBus.addListener(FriendsAndFoesForgeClient::onRegisterEntityRenderers);
		modEventBus.addListener(FriendsAndFoesForgeClient::onRegisterEntityLayers);
	}

	private static void onClientSetup(final FMLClientSetupEvent event) {
		ClientSetupEvent.EVENT.invoke(new ClientSetupEvent(Runnable::run));
		RegisterRenderLayersEvent.EVENT.invoke(new RegisterRenderLayersEvent(ItemBlockRenderTypes::setRenderLayer, ItemBlockRenderTypes::setRenderLayer));

		event.enqueueWork(() -> {
			if (!ModList.get().isLoaded("yet_another_config_lib_v3")) {
				return;
			}

			ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
				new ConfigScreenHandler.ConfigScreenFactory(
					(client, screen) -> FriendsAndFoesClient.getConfigScreen(screen)
				)
			);
		});
	}

	private static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		RegisterEntityRenderersEvent.EVENT.invoke(new RegisterEntityRenderersEvent(event::registerEntityRenderer));
	}

	private static void onRegisterEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		RegisterEntityLayersEvent.EVENT.invoke(new RegisterEntityLayersEvent(event::registerLayerDefinition));
	}

	private static void onRegisterParticles(RegisterParticleProvidersEvent event) {
		RegisterParticlesEvent.EVENT.invoke(new RegisterParticlesEvent(FriendsAndFoesForgeClient.registerParticle(event)));
	}

	private static RegisterParticlesEvent.Registrar registerParticle(RegisterParticleProvidersEvent event) {
		return new RegisterParticlesEvent.Registrar()
		{
			@Override
			public <T extends ParticleOptions> void register(
				ParticleType<T> type,
				Function<SpriteSet, ParticleProvider<T>> registration
			) {
				event.registerSpriteSet(type, registration::apply);
			}
		};
	}
}
