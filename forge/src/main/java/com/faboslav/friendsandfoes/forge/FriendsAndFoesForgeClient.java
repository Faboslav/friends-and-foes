package com.faboslav.friendsandfoes.forge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.FriendsAndFoesClient;
import com.faboslav.friendsandfoes.config.ConfigScreenBuilder;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = FriendsAndFoes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class FriendsAndFoesForgeClient
{
	@SubscribeEvent
	public static void clientInit(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			FriendsAndFoesClient.init();
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
}
