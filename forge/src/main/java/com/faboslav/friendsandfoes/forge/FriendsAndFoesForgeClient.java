package com.faboslav.friendsandfoes.forge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.FriendsAndFoesClient;
import com.faboslav.friendsandfoes.config.ConfigScreenBuilder;
import com.faboslav.friendsandfoes.platform.forge.RegistryHelperImpl;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = FriendsAndFoes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class FriendsAndFoesForgeClient
{
	@SubscribeEvent
	public static void clientInit(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			FriendsAndFoesClient.postInit();

			if (ModList.get().isLoaded("cloth_config")) {
				ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () ->
					new ConfigGuiHandler.ConfigGuiFactory(
						(mc, screen) -> ConfigScreenBuilder.createConfigScreen(FriendsAndFoes.getConfig(), screen)
					)
				);
			}
		});
	}

	@SubscribeEvent
	public static void registerLayerDefinitions(RegisterLayerDefinitions event) {
		FriendsAndFoes.getLogger().info("registerLayerDefinitions");
		for (Map.Entry<EntityModelLayer, Supplier<TexturedModelData>> entry : RegistryHelperImpl.ENTITY_MODEL_LAYERS.entrySet()) {
			FriendsAndFoes.getLogger().info(entry.getKey().getName());
			event.registerLayerDefinition(entry.getKey(), entry.getValue());
		}
	}

	@SubscribeEvent
	public static <T extends Entity> void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		FriendsAndFoes.getLogger().info("registerEntityRenderers");
		/*
		event.registerEntityRenderer(FriendsAndFoesEntityTypes.COPPER_GOLEM.get(), CopperGolemEntityRenderer::new);
		event.registerEntityRenderer(FriendsAndFoesEntityTypes.GLARE.get(), GlareEntityRenderer::new);
		event.registerEntityRenderer(FriendsAndFoesEntityTypes.ICEOLOGER.get(), IceologerEntityRenderer::new);
		event.registerEntityRenderer(FriendsAndFoesEntityTypes.ICE_CHUNK.get(), IceologerIceChunkRenderer::new);
		event.registerEntityRenderer(FriendsAndFoesEntityTypes.MAULER.get(), MaulerEntityRenderer::new);
		event.registerEntityRenderer(FriendsAndFoesEntityTypes.MOOBLOOM.get(), MoobloomEntityRenderer::new);
		event.registerEntityRenderer(FriendsAndFoesEntityTypes.WILDFIRE.get(), WildfireEntityRenderer::new);
		 */
	}
}
