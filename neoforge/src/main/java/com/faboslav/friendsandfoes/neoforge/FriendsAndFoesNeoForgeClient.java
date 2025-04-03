package com.faboslav.friendsandfoes.neoforge;

import com.faboslav.friendsandfoes.common.FriendsAndFoesClient;
import com.faboslav.friendsandfoes.common.config.FriendsAndFoesConfigScreen;
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
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.function.Function;

public final class FriendsAndFoesNeoForgeClient
{
	public static void init(IEventBus modEventBus, IEventBus eventBus) {
		FriendsAndFoesClient.init();

		modEventBus.addListener(FriendsAndFoesNeoForgeClient::onClientSetup);
		modEventBus.addListener(FriendsAndFoesNeoForgeClient::onRegisterParticles);
		modEventBus.addListener(FriendsAndFoesNeoForgeClient::onRegisterEntityRenderers);
		modEventBus.addListener(FriendsAndFoesNeoForgeClient::onRegisterEntityLayers);
	}

	public static void onClientSetup(final FMLClientSetupEvent event) {
		ClientSetupEvent.EVENT.invoke(new ClientSetupEvent(Runnable::run));
		RegisterRenderLayersEvent.EVENT.invoke(new RegisterRenderLayersEvent(ItemBlockRenderTypes::setRenderLayer, ItemBlockRenderTypes::setRenderLayer));

		event.enqueueWork(() -> {
			if (ModList.get().isLoaded("yet_another_config_lib_v3")) {
				ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (client, screen) -> {
					return new FriendsAndFoesConfigScreen(screen);
				});
			}
		});
	}

	private static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		RegisterEntityRenderersEvent.EVENT.invoke(new RegisterEntityRenderersEvent(event::registerEntityRenderer));
	}

	private static void onRegisterEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		RegisterEntityLayersEvent.EVENT.invoke(new RegisterEntityLayersEvent(event::registerLayerDefinition));
	}

	private static void onRegisterParticles(RegisterParticleProvidersEvent event) {
		RegisterParticlesEvent.EVENT.invoke(new RegisterParticlesEvent(FriendsAndFoesNeoForgeClient.registerParticle(event)));
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
