package com.faboslav.friendsandfoes.forge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.FriendsAndFoesServer;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.platform.forge.RegistryHelperImpl;
import com.faboslav.friendsandfoes.util.CustomRaidMember;
import com.faboslav.friendsandfoes.util.ServerWorldSpawnersUtil;
import com.faboslav.friendsandfoes.world.spawner.IceologerSpawner;
import com.faboslav.friendsandfoes.world.spawner.IllusionerSpawner;
import net.minecraft.SharedConstants;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Map;
import java.util.function.Supplier;

import static com.faboslav.friendsandfoes.FriendsAndFoes.serverTickDeltaCounter;

@Mod(FriendsAndFoes.MOD_ID)
@Mod.EventBusSubscriber(modid = FriendsAndFoes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class FriendsAndFoesForge
{
	public FriendsAndFoesForge() {
		FriendsAndFoes.checkForNewUpdates();

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		MinecraftForge.EVENT_BUS.register(modEventBus);

		RegistryHelperImpl.BLOCKS.register(modEventBus);

		FriendsAndFoesEntityTypes.previousUseChoiceTypeRegistrations = SharedConstants.useChoiceTypeRegistrations;
		SharedConstants.useChoiceTypeRegistrations = false;
		RegistryHelperImpl.ENTITY_TYPES.register(modEventBus);
		SharedConstants.useChoiceTypeRegistrations = FriendsAndFoesEntityTypes.previousUseChoiceTypeRegistrations;

		RegistryHelperImpl.ITEMS.register(modEventBus);
		RegistryHelperImpl.POINT_OF_INTEREST_TYPES.register(modEventBus);
		RegistryHelperImpl.SOUND_EVENTS.register(modEventBus);
		RegistryHelperImpl.VILLAGER_PROFESSIONS.register(modEventBus);

		FriendsAndFoes.init();

		modEventBus.addListener(FriendsAndFoesForge::init);
		modEventBus.addListener(FriendsAndFoesForge::serverInit);
		modEventBus.addListener(FriendsAndFoesForge::registerLayerDefinitions);
		modEventBus.addListener(FriendsAndFoesForge::registerEntityAttributes);

		var forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(FriendsAndFoesForge::initSpawners);
		forgeBus.addListener(FriendsAndFoesForge::initTickDeltaCounter);
	}

	private static void init(final FMLCommonSetupEvent event) {
		FriendsAndFoes.postInit();

		if (FriendsAndFoes.getConfig().enableIceologer && FriendsAndFoes.getConfig().enableIceologerInRaids) {
			Raid.Member.create(
				CustomRaidMember.ICEOLOGER_INTERNAL_NAME,
				FriendsAndFoesEntityTypes.ICEOLOGER.get(),
				CustomRaidMember.ICEOLOGER_COUNT_IN_WAVE
			);
		}

		if (FriendsAndFoes.getConfig().enableIllusioner && FriendsAndFoes.getConfig().enableIllusionerInRaids) {
			Raid.Member.create(
				CustomRaidMember.ILLUSIONER_INTERNAL_NAME,
				EntityType.ILLUSIONER,
				CustomRaidMember.ILLUSIONER_COUNT_IN_WAVE
			);
		}
	}

	private static void serverInit(final FMLDedicatedServerSetupEvent event) {
		event.enqueueWork(() -> {
			// TODO maybe check if this is server, but should be...
			FriendsAndFoesServer.init();
		});
	}

	public static void registerLayerDefinitions(RegisterLayerDefinitions event) {
		for (Map.Entry<EntityModelLayer, Supplier<TexturedModelData>> entry : RegistryHelperImpl.ENTITY_MODEL_LAYERS.entrySet()) {
			event.registerLayerDefinition(entry.getKey(), entry.getValue());
		}
	}

	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		for (Map.Entry<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<DefaultAttributeContainer.Builder>> entry : RegistryHelperImpl.ENTITY_ATTRIBUTES.entrySet()) {
			event.put(entry.getKey().get(), entry.getValue().get().build());
		}
	}

	private static void initSpawners(final WorldEvent.Load event) {
		if (
			event.getWorld().isClient()
			|| ((ServerWorld) event.getWorld()).getDimensionKey() != DimensionTypes.OVERWORLD) {
			return;
		}

		var server = event.getWorld().getServer();

		if (server == null) {
			return;
		}

		var world = server.getOverworld();

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
