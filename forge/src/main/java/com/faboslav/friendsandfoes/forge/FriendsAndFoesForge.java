package com.faboslav.friendsandfoes.forge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.FriendsAndFoesClient;
import com.faboslav.friendsandfoes.FriendsAndFoesServer;
import com.faboslav.friendsandfoes.init.ModBlocks;
import com.faboslav.friendsandfoes.init.ModEntity;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import dev.architectury.platform.Platform;
import dev.architectury.platform.forge.EventBuses;
import dev.architectury.utils.Env;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.entity.EntityType;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.poi.PointOfInterestType;

@Mod(FriendsAndFoes.MOD_ID)
@Mod.EventBusSubscriber(modid = FriendsAndFoes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FriendsAndFoesForge
{
    public FriendsAndFoesForge() {
		var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		EventBuses.registerModEventBus(FriendsAndFoes.MOD_ID, modEventBus);

		FriendsAndFoes.initRegisters();
		modEventBus.addListener(FriendsAndFoesForge::init);
		modEventBus.addListener(FriendsAndFoesForge::clientInit);
		modEventBus.addListener(FriendsAndFoesForge::serverInit);
		// TODO init spawners
		//modEventBus.addListener(FriendsAndFoesForge::initSpawners);
	}

	private static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			Raid.Member.create("ILLUSIONER", EntityType.ILLUSIONER, new int[]{0, 0, 0, 0, 1, 0, 1, 1});
			Raid.Member.create("ICEOLOGER", ModEntity.ICEOLOGER.get(), new int[]{0, 0, 0, 0, 1, 1, 0, 1});
			FriendsAndFoes.initCustomRegisters();
		});
	}

	private static void clientInit(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			if (Platform.getEnvironment() == Env.CLIENT) {
				return;
			}

			FriendsAndFoesClient.init();
		});
	}

	private static void serverInit(FMLDedicatedServerSetupEvent event) {
		event.enqueueWork(() -> {
			if (Platform.getEnvironment() == Env.SERVER) {
				return;
			}

			FriendsAndFoesServer.init();
		});
	}

	/*
	private static void initSpawners(WorldEvent.Load event) {
		var world = event.getWorld().getServer().getOverworld();

		if(world == null) {
			return;
		}

		ServerWorldSpawnersUtil.register(world, new IceologerSpawner());
		ServerWorldSpawnersUtil.register(world, new IllusionerSpawner());
	}*/
}
