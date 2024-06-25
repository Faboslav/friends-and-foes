package com.faboslav.friendsandfoes.neoforge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.FriendsAndFoesClient;
import com.faboslav.friendsandfoes.events.lifecycle.DatapackSyncEvent;
import com.faboslav.friendsandfoes.events.lifecycle.RegisterReloadListenerEvent;
import com.faboslav.friendsandfoes.events.lifecycle.SetupEvent;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.init.FriendsAndFoesStructurePoolElements;
import com.faboslav.friendsandfoes.neoforge.world.MobSpawnBiomeModifier;
import com.faboslav.friendsandfoes.platform.neoforge.RegistryHelperImpl;
import com.faboslav.friendsandfoes.util.CustomRaidMember;
import com.faboslav.friendsandfoes.util.ServerWorldSpawnersUtil;
import com.faboslav.friendsandfoes.util.UpdateChecker;
import com.faboslav.friendsandfoes.world.spawner.IceologerSpawner;
import com.faboslav.friendsandfoes.world.spawner.IllusionerSpawner;
import com.mojang.serialization.Codec;
import net.minecraft.SharedConstants;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.item.ItemGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.dimension.DimensionTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Map;
import java.util.function.Supplier;

@Mod(FriendsAndFoes.MOD_ID)
public final class FriendsAndFoesNeoForge
{
	public FriendsAndFoesNeoForge() {
		UpdateChecker.checkForNewUpdates();

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		var eventBus = NeoForge.EVENT_BUS;

		final DeferredRegister<Codec<? extends BiomeModifier>> biomeModifiers = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, FriendsAndFoes.MOD_ID);
		biomeModifiers.register(modEventBus);
		biomeModifiers.register("mob_spawns", MobSpawnBiomeModifier::makeCodec);


		FriendsAndFoes.init();

		if (FMLEnvironment.dist == Dist.CLIENT) {
			FriendsAndFoesClient.init();
		}

		modEventBus.addListener(FriendsAndFoesNeoForge::onSetup);

		RegistryHelperImpl.ACTIVITIES.register(modEventBus);
		RegistryHelperImpl.BLOCKS.register(modEventBus);
		FriendsAndFoesEntityTypes.previousUseChoiceTypeRegistrations = SharedConstants.useChoiceTypeRegistrations;
		SharedConstants.useChoiceTypeRegistrations = false;
		RegistryHelperImpl.ENTITY_TYPES.register(modEventBus);
		SharedConstants.useChoiceTypeRegistrations = FriendsAndFoesEntityTypes.previousUseChoiceTypeRegistrations;
		RegistryHelperImpl.ITEMS.register(modEventBus);
		RegistryHelperImpl.MEMORY_MODULE_TYPES.register(modEventBus);
		RegistryHelperImpl.SENSOR_TYPES.register(modEventBus);
		RegistryHelperImpl.PARTICLE_TYPES.register(modEventBus);
		RegistryHelperImpl.POINT_OF_INTEREST_TYPES.register(modEventBus);
		RegistryHelperImpl.SOUND_EVENTS.register(modEventBus);
		RegistryHelperImpl.STRUCTURE_TYPES.register(modEventBus);
		RegistryHelperImpl.STRUCTURE_PROCESSOR_TYPES.register(modEventBus);
		RegistryHelperImpl.VILLAGER_PROFESSIONS.register(modEventBus);

		modEventBus.addListener(FriendsAndFoesNeoForge::init);
		modEventBus.addListener(FriendsAndFoesNeoForge::registerEntityAttributes);
		modEventBus.addListener(FriendsAndFoesNeoForge::addItemsToTabs);

		eventBus.addListener(FriendsAndFoesNeoForge::initSpawners);
		eventBus.addListener(FriendsAndFoesNeoForge::onServerAboutToStartEvent);
		eventBus.addListener(FriendsAndFoesNeoForge::onAddReloadListeners);
		eventBus.addListener(FriendsAndFoesNeoForge::onDatapackSync);
	}

	private static void init(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
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
		});
	}

	private static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		for (Map.Entry<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<DefaultAttributeContainer.Builder>> entry : RegistryHelperImpl.ENTITY_ATTRIBUTES.entrySet()) {
			event.put(entry.getKey().get(), entry.getValue().get().build());
		}
	}

	private static void addItemsToTabs(BuildCreativeModeTabContentsEvent event) {
		RegistryHelperImpl.ITEMS_TO_ADD_BEFORE.forEach((itemGroup, itemPairs) -> {
			if (event.getTabKey() == itemGroup) {
				itemPairs.forEach((item, before) -> {
					event.getEntries().putBefore(before.getDefaultStack(), item.getDefaultStack(), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
				});
			}
		});

		RegistryHelperImpl.ITEMS_TO_ADD_AFTER.forEach((itemGroup, itemPairs) -> {
			if (event.getTabKey() == itemGroup) {
				itemPairs.forEach((item, after) -> {
					event.getEntries().putAfter(after.getDefaultStack(), item.getDefaultStack(), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
				});
			}
		});
	}

	private static void onAddReloadListeners(AddReloadListenerEvent event) {
		RegisterReloadListenerEvent.EVENT.invoke(new RegisterReloadListenerEvent((id, listener) -> event.addListener(listener)));
	}

	private static void onSetup(FMLCommonSetupEvent event) {
		SetupEvent.EVENT.invoke(new SetupEvent(event::enqueueWork));
	}

	private static void onDatapackSync(OnDatapackSyncEvent event) {
		if (event.getPlayer() != null) {
			DatapackSyncEvent.EVENT.invoke(new DatapackSyncEvent(event.getPlayer()));
		} else {
			event.getPlayerList().getPlayerList().forEach(player -> DatapackSyncEvent.EVENT.invoke(new DatapackSyncEvent(player)));
		}
	}

	private static void initSpawners(final LevelEvent.Load event) {
		if (
			event.getLevel().isClient()
			|| ((ServerWorld) event.getLevel()).getDimensionKey() != DimensionTypes.OVERWORLD) {
			return;
		}

		var server = event.getLevel().getServer();

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

	public static void onServerAboutToStartEvent(ServerAboutToStartEvent event) {
		FriendsAndFoesStructurePoolElements.init(event.getServer());
	}
}
