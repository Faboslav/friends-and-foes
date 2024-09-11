package com.faboslav.friendsandfoes.forge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.FriendsAndFoesClient;
import com.faboslav.friendsandfoes.common.config.ConfigScreenBuilder;
import com.faboslav.friendsandfoes.common.events.client.*;
import com.faboslav.friendsandfoes.common.events.lifecycle.ClientSetupEvent;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.common.item.DispenserAddedSpawnEgg;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Function;

@SuppressWarnings({"all", "removal", "deprecated"})
public final class FriendsAndFoesForgeClient
{
	public static void init(IEventBus modEventBus, IEventBus eventBus) {
		FriendsAndFoesClient.init();

		modEventBus.addListener(FriendsAndFoesForgeClient::onClientSetup);
		modEventBus.addListener(FriendsAndFoesForgeClient::onRegisterParticles);
		modEventBus.addListener(FriendsAndFoesForgeClient::onRegisterItemColors);
		modEventBus.addListener(FriendsAndFoesForgeClient::onRegisterEntityRenderers);
		modEventBus.addListener(FriendsAndFoesForgeClient::onRegisterEntityLayers);
	}

	private static void onClientSetup(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ClientSetupEvent.EVENT.invoke(new ClientSetupEvent(Runnable::run));
			RegisterRenderLayersEvent.EVENT.invoke(new RegisterRenderLayersEvent(RenderLayers::setRenderLayer, RenderLayers::setRenderLayer));

			if (ModList.get().isLoaded("cloth_config")) {
				ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
					new ConfigScreenHandler.ConfigScreenFactory(
						(mc, screen) -> ConfigScreenBuilder.createConfigScreen(FriendsAndFoes.getConfig(), screen)
					)
				);
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
		RegisterParticlesEvent.EVENT.invoke(new RegisterParticlesEvent(FriendsAndFoesForgeClient.registerParticle(event)));
	}

	private static RegisterParticlesEvent.Registrar registerParticle(RegisterParticleProvidersEvent event) {
		return new RegisterParticlesEvent.Registrar()
		{
			@Override
			public <T extends ParticleEffect> void register(
				ParticleType<T> type,
				Function<SpriteProvider, ParticleFactory<T>> registration
			) {
				event.register(type, registration::apply);
			}
		};
	}

	private static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
		RegisterItemColorEvent.EVENT.invoke(new RegisterItemColorEvent(event::register, event.getBlockColors()::getColor));
		FriendsAndFoesItems.ITEMS.stream()
			.map(RegistryEntry::get)
			.filter(item -> item instanceof DispenserAddedSpawnEgg)
			.map(item -> (DispenserAddedSpawnEgg) item)
			.forEach(item -> event.register((stack, index) -> item.getColor(index), item));
	}
}
