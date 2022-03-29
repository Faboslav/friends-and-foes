package com.faboslav.friendsandfoes.forge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.FriendsAndFoesServer;
import com.faboslav.friendsandfoes.init.ModEntity;
import com.faboslav.friendsandfoes.util.ServerWorldSpawnersUtil;
import com.faboslav.friendsandfoes.world.spawner.IceologerSpawner;
import com.faboslav.friendsandfoes.world.spawner.IllusionerSpawner;
import dev.architectury.platform.Platform;
import dev.architectury.platform.forge.EventBuses;
import dev.architectury.utils.Env;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Util;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.faboslav.friendsandfoes.FriendsAndFoes.serverTickDeltaCounter;

@Mod(FriendsAndFoes.MOD_ID)
@Mod.EventBusSubscriber(modid = FriendsAndFoes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FriendsAndFoesForge
{
	public FriendsAndFoesForge() {
		var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		EventBuses.registerModEventBus(FriendsAndFoes.MOD_ID, modEventBus);

		FriendsAndFoes.initRegisters();
		FriendsAndFoesForgeClient.init();

		modEventBus.addListener(FriendsAndFoesForge::init);
		modEventBus.addListener(FriendsAndFoesForgeClient::clientInit);
		modEventBus.addListener(FriendsAndFoesForge::serverInit);

		var forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(FriendsAndFoesForge::initSpawners);
		forgeBus.addListener(FriendsAndFoesForge::initTickDeltaCounter);
	}

	private static void init(final FMLCommonSetupEvent event) {
		FriendsAndFoes.initCustomRegisters();

		if (FriendsAndFoes.getConfig().enableIllusionerInRaids) {
			Raid.Member.create("ILLUSIONER", EntityType.ILLUSIONER, new int[]{0, 0, 0, 0, 1, 0, 1, 1});
		}

		if (FriendsAndFoes.getConfig().enableIceologerInRaids) {
			Raid.Member.create("ICEOLOGER", ModEntity.ICEOLOGER.get(), new int[]{0, 0, 0, 0, 1, 1, 0, 1});
		}
	}

	private static void serverInit(final FMLDedicatedServerSetupEvent event) {
		event.enqueueWork(() -> {
			if (Platform.getEnvironment() != Env.SERVER) {
				return;
			}

			FriendsAndFoesServer.init();
		});
	}

	private static void initSpawners(final WorldEvent.Load event) {
		if (
			event.getWorld().isClient()
			|| event.getWorld().getDimension().getEffects() != DimensionType.OVERWORLD_ID
		) {
			return;
		}

		var world = event.getWorld().getServer().getOverworld();

		if (world == null) {
			return;
		}

		ServerWorldSpawnersUtil.register(world, new IceologerSpawner());
		ServerWorldSpawnersUtil.register(world, new IllusionerSpawner());
	}

	private static void initTickDeltaCounter(final ServerTickEvent event) {
		if (event.phase != TickEvent.Phase.START) {
			return;
		}

		serverTickDeltaCounter.beginRenderTick(Util.getMeasuringTimeMs());
	}
}
