package com.faboslav.friendsandfoes.forge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.FriendsAndFoesClient;
import com.faboslav.friendsandfoes.FriendsAndFoesServer;
import dev.architectury.platform.Platform;
import dev.architectury.platform.forge.EventBuses;
import dev.architectury.utils.Env;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FriendsAndFoes.MOD_ID)
@Mod.EventBusSubscriber(modid = FriendsAndFoes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FriendsAndFoesForge
{
    public FriendsAndFoesForge() {
		var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		EventBuses.registerModEventBus(FriendsAndFoes.MOD_ID, modEventBus);

		modEventBus.addListener(FriendsAndFoesForge::init);
		modEventBus.addListener(FriendsAndFoesForge::clientInit);
		modEventBus.addListener(FriendsAndFoesForge::serverInit);
	}

	private static void init(FMLCommonSetupEvent event) {
		FriendsAndFoes.init();
	}

	private static void clientInit(FMLClientSetupEvent event) {
		if (Platform.getEnvironment() == Env.CLIENT) {
			FriendsAndFoesClient.init();
		}
	}

	private static void serverInit(FMLDedicatedServerSetupEvent event) {
		if (Platform.getEnvironment() == Env.SERVER) {
			FriendsAndFoesServer.init();
		}
	}
}
