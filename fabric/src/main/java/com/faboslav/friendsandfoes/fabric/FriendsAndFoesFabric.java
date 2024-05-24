package com.faboslav.friendsandfoes.fabric;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.events.RegisterVillagerTradesEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.*;
import com.faboslav.friendsandfoes.events.fabric.FabricReloadListener;
import com.faboslav.friendsandfoes.common.util.ServerWorldSpawnersUtil;
import com.faboslav.friendsandfoes.common.util.UpdateChecker;
import com.faboslav.friendsandfoes.common.world.spawner.IceologerSpawner;
import com.faboslav.friendsandfoes.common.world.spawner.IllusionerSpawner;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.resource.ResourceType;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffers;
import net.minecraft.world.dimension.DimensionTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class FriendsAndFoesFabric implements ModInitializer
{
	@Override
	public void onInitialize() {
		UpdateChecker.checkForNewUpdates();
		FriendsAndFoes.init();
		initEvents();
		FriendsAndFoes.lateInit();
	}

	private static void initEvents() {
		SetupEvent.EVENT.invoke(new SetupEvent(Runnable::run));

		RegisterReloadListenerEvent.EVENT.invoke(new RegisterReloadListenerEvent((id, listener) -> {
			ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new FabricReloadListener(id, listener));
		}));

		ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) ->
			DatapackSyncEvent.EVENT.invoke(new DatapackSyncEvent(player)));

		ServerWorldEvents.LOAD.register(((server, world) -> {
			registerVillagerTrades();

			if (world.isClient() || world.getDimensionKey() != DimensionTypes.OVERWORLD) {
				return;
			}

			ServerWorldSpawnersUtil.register(world, new IceologerSpawner());
			ServerWorldSpawnersUtil.register(world, new IllusionerSpawner());
		}));

		RegisterFlammabilityEvent.EVENT.invoke(new RegisterFlammabilityEvent(FlammableBlockRegistry.getDefaultInstance()::add));
		RegisterEntityAttributesEvent.EVENT.invoke(new RegisterEntityAttributesEvent(FabricDefaultAttributeRegistry::register));
		RegisterEntitySpawnRestrictionsEvent.EVENT.invoke(new RegisterEntitySpawnRestrictionsEvent(FriendsAndFoesFabric::registerPlacement));
		AddSpawnBiomeModificationsEvent.EVENT.invoke(new AddSpawnBiomeModificationsEvent((tag, spawnGroup, entityType, weight, minGroupSize, maxGroupSize) -> {
			BiomeModifications.addSpawn(biomeSelector -> biomeSelector.hasTag(tag) && biomeSelector.hasTag(BiomeTags.IS_OVERWORLD), spawnGroup, entityType, weight, minGroupSize, maxGroupSize);
		}));
		SetupEvent.EVENT.invoke(new SetupEvent(Runnable::run));
	}

	private static void registerVillagerTrades() {
		var trades = TradeOffers.PROFESSION_TO_LEVELED_TRADE;
		for (var profession : Registry.VILLAGER_PROFESSION) {
			if (profession == null) {
				continue;
			}

			Int2ObjectMap<TradeOffers.Factory[]> profTrades = trades.computeIfAbsent(profession, key -> new Int2ObjectOpenHashMap<>());
			Int2ObjectMap<List<TradeOffers.Factory>> listings = new Int2ObjectOpenHashMap<>();

			for (int i = 1; i <= 5; i++) {
				if (profTrades.containsKey(i)) {
					List<TradeOffers.Factory> list = Arrays.stream(profTrades.get(i)).collect(Collectors.toList());
					listings.put(i, list);
				} else {
					listings.put(i, new ArrayList<>());
				}
			}

			RegisterVillagerTradesEvent.EVENT.invoke(new RegisterVillagerTradesEvent(profession, (i, listing) -> listings.get(i.intValue()).add(listing)));

			for (int i = 1; i <= 5; i++) {
				profTrades.put(i, listings.get(i).toArray(new TradeOffers.Factory[0]));
			}
		}
	}

	private static <T extends MobEntity> void registerPlacement(
		EntityType<T> type,
		RegisterEntitySpawnRestrictionsEvent.Placement<T> placement
	) {
		SpawnRestriction.register(type, placement.location(), placement.heightmap(), placement.predicate());
	}
}
