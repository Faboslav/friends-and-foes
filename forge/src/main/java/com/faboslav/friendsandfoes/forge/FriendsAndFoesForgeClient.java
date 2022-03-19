package com.faboslav.friendsandfoes.forge;

import com.faboslav.friendsandfoes.FriendsAndFoesClient;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class FriendsAndFoesForgeClient
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
		});
	}
}
