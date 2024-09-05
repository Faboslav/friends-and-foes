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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.ConfigScreenHandler;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = FriendsAndFoes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class FriendsAndFoesNeoForgeClient
{
	@SubscribeEvent
	public static void clientInit(final FMLClientSetupEvent event) {
		ClientSetupEvent.EVENT.invoke(new ClientSetupEvent(Runnable::run));

		event.enqueueWork(() -> {
			FriendsAndFoesClient.postInit();

			if (ModList.get().isLoaded("cloth_config")) {
				ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
					new ConfigScreenHandler.ConfigScreenFactory(
						(mc, screen) -> ConfigScreenBuilder.createConfigScreen(FriendsAndFoes.getConfig(), screen)
					)
				);
			}
		});
	}

	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		for (Map.Entry<EntityModelLayer, Supplier<TexturedModelData>> entry : RegistryHelperImpl.ENTITY_MODEL_LAYERS.entrySet()) {
			event.registerLayerDefinition(entry.getKey(), entry.getValue());
		}
	}

	@SubscribeEvent
	public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(FriendsAndFoesParticleTypes.TOTEM_OF_FREEZING, FreezingTotemParticle.Factory::new);
		event.registerSpriteSet(FriendsAndFoesParticleTypes.TOTEM_OF_ILLUSION, IllusionTotemParticle.Factory::new);
	}
}
