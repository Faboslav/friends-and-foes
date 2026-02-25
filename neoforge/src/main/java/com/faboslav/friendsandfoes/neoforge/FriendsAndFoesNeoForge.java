package com.faboslav.friendsandfoes.neoforge;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.events.AddItemGroupEntriesEvent;
import com.faboslav.friendsandfoes.common.events.entity.EntitySpawnEvent;
import com.faboslav.friendsandfoes.common.events.entity.RegisterVillagerTradesEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.*;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStructurePoolElements;
import com.faboslav.friendsandfoes.common.util.ServerWorldSpawnersUtil;
import com.faboslav.friendsandfoes.common.world.spawner.IceologerSpawner;
import com.faboslav.friendsandfoes.common.world.spawner.IllusionerSpawner;
import com.faboslav.friendsandfoes.neoforge.init.FriendsAndFoesBiomeModifiers;
import com.faboslav.friendsandfoes.neoforge.mixin.FireBlockAccessor;
import com.faboslav.friendsandfoes.neoforge.platform.EntitySerializers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

//? if >=1.21.4 {
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
//?} else {
/*import net.neoforged.neoforge.event.AddReloadListenerEvent;
*///?}

@Mod(FriendsAndFoes.MOD_ID)
public final class FriendsAndFoesNeoForge
{
	public FriendsAndFoesNeoForge(ModContainer modContainer, IEventBus modEventBus) {
		IEventBus eventBus = NeoForge.EVENT_BUS;

		FriendsAndFoes.init();
		FriendsAndFoesBiomeModifiers.BIOME_MODIFIERS.register(modEventBus);

		if (FMLEnvironment./*? if >= 1.21.9 {*/ getDist() /*?} else {*/ /*dist *//*?}*/== Dist.CLIENT) {
			FriendsAndFoesNeoForgeClient.init(modEventBus, eventBus);
		}

		eventBus.addListener(FriendsAndFoesNeoForge::initSpawners);
		eventBus.addListener(FriendsAndFoesNeoForge::onServerAboutToStartEvent);
		eventBus.addListener(FriendsAndFoesNeoForge::onAddVillagerTrades);
		eventBus.addListener(FriendsAndFoesNeoForge::onRegisterBrewingRecipes);
		eventBus.addListener(FriendsAndFoesNeoForge::onAddReloadListeners);
		eventBus.addListener(FriendsAndFoesNeoForge::onDatapackSync);
		eventBus.addListener(FriendsAndFoesNeoForge::onEntitySpawn);

		modEventBus.addListener(FriendsAndFoesNeoForge::onSetup);
		modEventBus.addListener(FriendsAndFoesNeoForge::onRegisterAttributes);
		modEventBus.addListener(FriendsAndFoesNeoForge::onRegisterSpawnRestrictions);
		modEventBus.addListener(FriendsAndFoesNeoForge::onAddItemGroupEntries);

		EntitySerializers.ENTITY_DATA_SERIALIZERS.register(modEventBus);
	}

	private static void onSetup(final FMLCommonSetupEvent event) {
		SetupEvent.EVENT.invoke(new SetupEvent(event::enqueueWork));

		event.enqueueWork(() -> {
			FriendsAndFoes.lateInit();

			RegisterFlammabilityEvent.EVENT.invoke(new RegisterFlammabilityEvent((item, igniteOdds, burnOdds) ->
				((FireBlockAccessor) Blocks.FIRE).invokeRegisterFlammableBlock(item, igniteOdds, burnOdds)));
		});
	}

	private static void onEntitySpawn(FinalizeSpawnEvent event) {
		if(event.isCanceled()) {
			return;
		}

		boolean spawn = EntitySpawnEvent.EVENT.invoke(new EntitySpawnEvent(event.getEntity(), event.getLevel(), event.getEntity().isBaby(), event.getSpawnType()), event.isCanceled());

		if(spawn) {
			event.setSpawnCancelled(true);
		}
	}

	private static void onAddReloadListeners(
		//? if >=1.21.4 {
		AddServerReloadListenersEvent event
		//?} else {
		/*AddReloadListenerEvent event
		 *///?}
	) {
		//? if >=1.21.4 {
		RegisterReloadListenerEvent.EVENT.invoke(new RegisterReloadListenerEvent(event::addListener));
		//?} else {
		/*RegisterReloadListenerEvent.EVENT.invoke(new RegisterReloadListenerEvent((id, listener) -> event.addListener(listener)));
		 *///?}
	}

	private static void onDatapackSync(OnDatapackSyncEvent event) {
		if (event.getPlayer() != null) {
			DatapackSyncEvent.EVENT.invoke(new DatapackSyncEvent(event.getPlayer()));
		} else {
			event.getPlayerList().getPlayers().forEach(player -> DatapackSyncEvent.EVENT.invoke(new DatapackSyncEvent(player)));
		}
	}

	private static void onAddVillagerTrades(VillagerTradesEvent event) {
		RegisterVillagerTradesEvent.EVENT.invoke(new RegisterVillagerTradesEvent(event.getType(), (i, listing) -> event.getTrades().get(i.intValue()).add(listing)));
	}

	private static void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event) {
		com.faboslav.friendsandfoes.common.events.item.RegisterBrewingRecipesEvent.EVENT.invoke(new com.faboslav.friendsandfoes.common.events.item.RegisterBrewingRecipesEvent(event.getBuilder()::addMix));
	}

	private static void onAddItemGroupEntries(BuildCreativeModeTabContentsEvent event) {
		AddItemGroupEntriesEvent.EVENT.invoke(
			new AddItemGroupEntriesEvent(
				AddItemGroupEntriesEvent.Type.toType(BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(event.getTab()).orElse(null)),
				event.getTab(),
				event.hasPermissions(),
				event::accept
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
			public <T extends Mob> void register(
				EntityType<T> type,
				RegisterEntitySpawnRestrictionsEvent.Placement<T> placement
			) {
				event.register(type, placement.location(), placement.heightmap(), placement.predicate(), RegisterSpawnPlacementsEvent.Operation.AND);
			}
		};
	}

	private static void initSpawners(final LevelEvent.Load event) {
		if (
			event.getLevel().isClientSide()
			|| ((ServerLevel) event.getLevel()).dimensionTypeRegistration() != BuiltinDimensionTypes.OVERWORLD) {
			return;
		}

		var server = event.getLevel().getServer();

		if (server == null) {
			return;
		}

		var world = server.overworld();

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
