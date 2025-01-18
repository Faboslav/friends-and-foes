package com.faboslav.friendsandfoes.neoforge;

import com.faboslav.friendsandfoes.common.FriendsAndFoesClient;
import com.faboslav.friendsandfoes.common.config.FriendsAndFoesConfigScreen;
import com.faboslav.friendsandfoes.common.events.client.*;
import com.faboslav.friendsandfoes.common.events.lifecycle.ClientSetupEvent;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.common.item.DispenserAddedSpawnEgg;
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
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.function.Function;

public final class FriendsAndFoesNeoForgeClient
{
	public static void init(IEventBus modEventBus, IEventBus eventBus) {
		FriendsAndFoesClient.init();

		modEventBus.addListener(FriendsAndFoesNeoForgeClient::onClientSetup);
		modEventBus.addListener(FriendsAndFoesNeoForgeClient::onRegisterParticles);
		modEventBus.addListener(FriendsAndFoesNeoForgeClient::onRegisterItemColors);
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

	private static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
		RegisterItemColorEvent.EVENT.invoke(new RegisterItemColorEvent(event::register, event.getBlockColors()::getColor));
		FriendsAndFoesItems.ITEMS.stream()
			.map(RegistryEntry::get)
			.filter(item -> item instanceof DispenserAddedSpawnEgg)
			.map(item -> (DispenserAddedSpawnEgg) item)
			.forEach(item -> event.register((stack, index) -> item.getColor(index), item));
	}
}
