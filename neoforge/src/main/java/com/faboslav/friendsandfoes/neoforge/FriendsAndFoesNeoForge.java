package com.faboslav.friendsandfoes.neoforge;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.events.AddItemGroupEntriesEvent;
import com.faboslav.friendsandfoes.common.events.entity.RegisterVillagerTradesEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.*;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStructurePoolElements;
import com.faboslav.friendsandfoes.common.util.ServerWorldSpawnersUtil;
import com.faboslav.friendsandfoes.common.util.UpdateChecker;
import com.faboslav.friendsandfoes.common.world.spawner.IceologerSpawner;
import com.faboslav.friendsandfoes.common.world.spawner.IllusionerSpawner;
import com.faboslav.friendsandfoes.neoforge.init.FriendsAndFoesBiomeModifiers;
import com.faboslav.friendsandfoes.neoforge.mixin.FireBlockAccessor;
import com.faboslav.friendsandfoes.neoforge.network.NeoForgeNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.dimension.DimensionTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@Mod(FriendsAndFoes.MOD_ID)
public final class FriendsAndFoesNeoForge
{
	public FriendsAndFoesNeoForge(IEventBus modEventBus) {
		IEventBus eventBus = NeoForge.EVENT_BUS;

		UpdateChecker.checkForNewUpdates();

		FriendsAndFoes.init();
		FriendsAndFoesBiomeModifiers.BIOME_MODIFIERS.register(modEventBus);

		if (FMLEnvironment.dist == Dist.CLIENT) {
			FriendsAndFoesNeoForgeClient.init(modEventBus, eventBus);
		}

		eventBus.addListener(FriendsAndFoesNeoForge::initSpawners);
		eventBus.addListener(FriendsAndFoesNeoForge::onServerAboutToStartEvent);
		eventBus.addListener(FriendsAndFoesNeoForge::onAddVillagerTrades);
		eventBus.addListener(FriendsAndFoesNeoForge::onRegisterBrewingRecipes);
		eventBus.addListener(FriendsAndFoesNeoForge::onAddReloadListeners);
		eventBus.addListener(FriendsAndFoesNeoForge::onDatapackSync);

		modEventBus.addListener(FriendsAndFoesNeoForge::onSetup);
		modEventBus.addListener(FriendsAndFoesNeoForge::onNetworkSetup);
		modEventBus.addListener(FriendsAndFoesNeoForge::onRegisterAttributes);
		modEventBus.addListener(FriendsAndFoesNeoForge::onRegisterSpawnRestrictions);
		modEventBus.addListener(FriendsAndFoesNeoForge::onAddItemGroupEntries);
	}

	private static void onSetup(final FMLCommonSetupEvent event) {
		SetupEvent.EVENT.invoke(new SetupEvent(event::enqueueWork));

		event.enqueueWork(() -> {
			FriendsAndFoes.lateInit();
			
			RegisterFlammabilityEvent.EVENT.invoke(new RegisterFlammabilityEvent((item, igniteOdds, burnOdds) ->
				((FireBlockAccessor) Blocks.FIRE).invokeRegisterFlammableBlock(item, igniteOdds, burnOdds)));
		});
	}

	private static void onAddReloadListeners(AddReloadListenerEvent event) {
		RegisterReloadListenerEvent.EVENT.invoke(new RegisterReloadListenerEvent((id, listener) -> event.addListener(listener)));
	}

	private static void onDatapackSync(OnDatapackSyncEvent event) {
		if (FMLEnvironment.dist.isDedicatedServer()) {
			if (event.getPlayer() != null) {
				DatapackSyncEvent.EVENT.invoke(new DatapackSyncEvent(event.getPlayer()));
			} else {
				event.getPlayerList().getPlayerList().forEach(player -> DatapackSyncEvent.EVENT.invoke(new DatapackSyncEvent(player)));
			}
		}
	}

	public static void onNetworkSetup(RegisterPayloadHandlersEvent event) {
		NeoForgeNetworking.setupNetwork(event);
	}

	private static void onAddVillagerTrades(VillagerTradesEvent event) {
		RegisterVillagerTradesEvent.EVENT.invoke(new RegisterVillagerTradesEvent(event.getType(), (i, listing) -> event.getTrades().get(i.intValue()).add(listing)));
	}

	private static void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event) {
		com.faboslav.friendsandfoes.common.events.item.RegisterBrewingRecipesEvent.EVENT.invoke(new com.faboslav.friendsandfoes.common.events.item.RegisterBrewingRecipesEvent(event.getBuilder()::registerPotionRecipe));
	}

	private static void onAddItemGroupEntries(BuildCreativeModeTabContentsEvent event) {
		AddItemGroupEntriesEvent.EVENT.invoke(
			new AddItemGroupEntriesEvent(
				AddItemGroupEntriesEvent.Type.toType(Registries.ITEM_GROUP.getKey(event.getTab()).orElse(null)),
				event.getTab(),
				event.hasPermissions(),
				event::add
			)
		);
	}

	private static void onRegisterAttributes(EntityAttributeCreationEvent event) {
		RegisterEntityAttributesEvent.EVENT.invoke(new RegisterEntityAttributesEvent((entity, builder) -> event.put(entity, builder.build())));
	}

	private static void onRegisterSpawnRestrictions(RegisterSpawnPlacementsEvent event) {
		RegisterEntitySpawnRestrictionsEvent.EVENT.invoke(new RegisterEntitySpawnRestrictionsEvent(FriendsAndFoesNeoForge.registerEntitySpawnRestriction(event)));
	}

	private static RegisterEntitySpawnRestrictionsEvent.Registrar registerEntitySpawnRestriction(
		RegisterSpawnPlacementsEvent event
	) {
		return new RegisterEntitySpawnRestrictionsEvent.Registrar()
		{
			@Override
			public <T extends MobEntity> void register(
				EntityType<T> type,
				RegisterEntitySpawnRestrictionsEvent.Placement<T> placement
			) {
				event.register(type, placement.location(), placement.heightmap(), placement.predicate(), RegisterSpawnPlacementsEvent.Operation.AND);
			}
		};
	}

	private static void initSpawners(final LevelEvent.Load event) {
		if (
			event.getLevel().isClient()
			|| ((ServerWorld) event.getLevel()).getDimensionEntry() != DimensionTypes.OVERWORLD) {
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
