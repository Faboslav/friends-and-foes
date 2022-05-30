package com.faboslav.friendsandfoes.forge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.FriendsAndFoesClient;
import com.faboslav.friendsandfoes.config.ConfigScreenBuilder;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class FriendsAndFoesForgeClient
{
	public static void init() {
		if (Platform.getEnvironment() != Env.CLIENT) {
			return;
		}

		FriendsAndFoesClient.initRegisters();
	}

	public static void clientInit(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			if (Platform.getEnvironment() != Env.CLIENT) {
				return;
			}

			FriendsAndFoesClient.initCustomRegisters();

			if(ModList.get().isLoaded("cloth_config")) {
				ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () ->
					new ConfigGuiHandler.ConfigGuiFactory(
						(mc, screen) -> ConfigScreenBuilder.createConfigScreen(FriendsAndFoes.getConfig(), screen)
					)
				);
			}
		});
	}
}
