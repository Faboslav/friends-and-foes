package com.faboslav.friendsandfoes.neoforge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.FriendsAndFoesClient;
import com.faboslav.friendsandfoes.client.particle.FreezingTotemParticle;
import com.faboslav.friendsandfoes.client.particle.IllusionTotemParticle;
import com.faboslav.friendsandfoes.config.ConfigScreenBuilder;
import com.faboslav.friendsandfoes.events.lifecycle.ClientSetupEvent;
import com.faboslav.friendsandfoes.init.FriendsAndFoesParticleTypes;
import com.faboslav.friendsandfoes.platform.neoforge.RegistryHelperImpl;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.Map;
import java.util.function.Supplier;

public final class FriendsAndFoesNeoForgeClient
{
	public static void init(IEventBus modEventBus, IEventBus eventBus) {
		FriendsAndFoesClient.init();

		modEventBus.addListener(FriendsAndFoesNeoForgeClient::onClientSetup);
		modEventBus.addListener(FriendsAndFoesNeoForgeClient::registerLayerDefinitions);
		modEventBus.addListener(FriendsAndFoesNeoForgeClient::registerParticleFactory);
	}

	private static void onClientSetup(final FMLClientSetupEvent event) {
		ClientSetupEvent.EVENT.invoke(new ClientSetupEvent(Runnable::run));

		event.enqueueWork(() -> {
			FriendsAndFoesClient.postInit();

			if (ModList.get().isLoaded("cloth_config")) {
				ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (client, screen) -> {
					return ConfigScreenBuilder.createConfigScreen(FriendsAndFoes.getConfig(), screen);
				});
			}
		});
	}

	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		for (Map.Entry<EntityModelLayer, Supplier<TexturedModelData>> entry : RegistryHelperImpl.ENTITY_MODEL_LAYERS.entrySet()) {
			event.registerLayerDefinition(entry.getKey(), entry.getValue());
		}
	}

	public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(FriendsAndFoesParticleTypes.TOTEM_OF_FREEZING, FreezingTotemParticle.Factory::new);
		event.registerSpriteSet(FriendsAndFoesParticleTypes.TOTEM_OF_ILLUSION, IllusionTotemParticle.Factory::new);
	}
}