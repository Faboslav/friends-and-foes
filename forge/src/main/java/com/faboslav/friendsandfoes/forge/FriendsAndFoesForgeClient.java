package com.faboslav.friendsandfoes.forge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.FriendsAndFoesClient;
import com.faboslav.friendsandfoes.config.ConfigScreenBuilder;
import com.faboslav.friendsandfoes.platform.forge.RegistryHelperImpl;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
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
				ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
					new ConfigScreenHandler.ConfigScreenFactory(
						(mc, screen) -> ConfigScreenBuilder.createConfigScreen(FriendsAndFoes.getConfig(), screen)
					)
				);
			}
		});
	}

	@SubscribeEvent
	public static void registerLayerDefinitions(RegisterLayerDefinitions event) {
		for (Map.Entry<EntityModelLayer, Supplier<TexturedModelData>> entry : RegistryHelperImpl.ENTITY_MODEL_LAYERS.entrySet()) {
			event.registerLayerDefinition(entry.getKey(), entry.getValue());
		}
	}
}
