package com.faboslav.friendsandfoes.forge;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.events.AddItemGroupEntriesEvent;
import com.faboslav.friendsandfoes.common.events.entity.EntitySpawnEvent;
import com.faboslav.friendsandfoes.common.events.entity.RegisterVillagerTradesEvent;
import com.faboslav.friendsandfoes.common.events.item.RegisterBrewingRecipesEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.DatapackSyncEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.RegisterEntityAttributesEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.RegisterEntitySpawnRestrictionsEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.RegisterReloadListenerEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.SetupEvent;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStructurePoolElements;
import com.faboslav.friendsandfoes.common.platform.CustomSpawnGroup;
import com.faboslav.friendsandfoes.common.util.CustomRaidMember;
import com.faboslav.friendsandfoes.common.util.ServerWorldSpawnersUtil;
import com.faboslav.friendsandfoes.common.world.spawner.IceologerSpawner;
import com.faboslav.friendsandfoes.common.world.spawner.IllusionerSpawner;
import com.faboslav.friendsandfoes.forge.brewing.FriendsAndFoesBrewingRecipe;
import com.faboslav.friendsandfoes.forge.init.FriendsAndFoesBiomeModifiers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(FriendsAndFoes.MOD_ID)
@SuppressWarnings({"all", "deprecated", "removal"})
public final class FriendsAndFoesForge
{
	public FriendsAndFoesForge() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus eventBus = MinecraftForge.EVENT_BUS;

		FriendsAndFoes.init();
		FriendsAndFoesBiomeModifiers.BIOME_MODIFIER_SERIALIZERS.register(modEventBus);

		if (FMLEnvironment.dist == Dist.CLIENT) {
			FriendsAndFoesForgeClient.init(modEventBus, eventBus);
		}

		eventBus.addListener(FriendsAndFoesForge::onEntitySpawn);
		eventBus.addListener(FriendsAndFoesForge::initSpawners);
		eventBus.addListener(FriendsAndFoesForge::onServerAboutToStart);
		eventBus.addListener(FriendsAndFoesForge::onAddVillagerTrades);
		eventBus.addListener(FriendsAndFoesForge::onAddReloadListeners);
		eventBus.addListener(FriendsAndFoesForge::onDatapackSync);

		modEventBus.addListener(FriendsAndFoesForge::onSetup);
		modEventBus.addListener(FriendsAndFoesForge::onAddItemGroupEntries);
		modEventBus.addListener(FriendsAndFoesForge::onRegisterAttributes);
		modEventBus.addListener(FriendsAndFoesForge::onRegisterSpawnRestrictions);
	}

	private static void onSetup(FMLCommonSetupEvent event) {
		SetupEvent.EVENT.invoke(new SetupEvent(event::enqueueWork));

		event.enqueueWork(() -> {
			FriendsAndFoes.lateInit();

			CustomSpawnGroup.GLARES = MobCategory.create(CustomSpawnGroup.GLARES_INTERNAL_NAME, CustomSpawnGroup.GLARES_NAME, CustomSpawnGroup.GLARES_SPAWN_CAP, CustomSpawnGroup.GLARES_PEACEFUL, CustomSpawnGroup.GLARES_RARE, CustomSpawnGroup.GLARES_IMMEDIATE_DESPAWN_RANGE);
			CustomSpawnGroup.RASCALS = MobCategory.create(CustomSpawnGroup.RASCALS_INTERNAL_NAME, CustomSpawnGroup.RASCALS_NAME, CustomSpawnGroup.RASCALS_SPAWN_CAP, CustomSpawnGroup.RASCALS_PEACEFUL, CustomSpawnGroup.RASCALS_RARE, CustomSpawnGroup.RASCALS_IMMEDIATE_DESPAWN_RANGE);

			if (FriendsAndFoes.getConfig().enableIceologerInRaids) {
				CustomRaidMember.ICEOLOGER = Raid.RaiderType.create(CustomRaidMember.ICEOLOGER_INTERNAL_NAME, FriendsAndFoesEntityTypes.ICEOLOGER.get(), CustomRaidMember.ICEOLOGER_COUNT_IN_WAVE);
			}

			if (FriendsAndFoes.getConfig().enableIllusionerInRaids) {
				CustomRaidMember.ILLUSIONER = Raid.RaiderType.create(CustomRaidMember.ILLUSIONER_INTERNAL_NAME, FriendsAndFoesEntityTypes.ILLUSIONER.get(), CustomRaidMember.ILLUSIONER_COUNT_IN_WAVE);
			}

			RegisterBrewingRecipesEvent.EVENT.invoke(
				new RegisterBrewingRecipesEvent(
					(input, ingredient, output) -> BrewingRecipeRegistry.addRecipe(new FriendsAndFoesBrewingRecipe(input, ingredient, output))
				)
			);
		});
	}

	private static void onEntitySpawn(MobSpawnEvent.FinalizeSpawn event) {
		if (event.isCanceled()) {
			return;
		}

		boolean spawn = EntitySpawnEvent.EVENT.invoke(new EntitySpawnEvent(event.getEntity(), event.getLevel(), event.getEntity().isBaby(), event.getSpawnType()), event.isCanceled());

		if (spawn) {
			event.setSpawnCancelled(true);
		}
	}

	private static void initSpawners(final LevelEvent.Load event) {
		if (
			event.getLevel().isClientSide()
			|| ((ServerLevel) event.getLevel()).dimensionTypeRegistration() != BuiltinDimensionTypes.OVERWORLD
		) {
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

	private static void onServerAboutToStart(ServerAboutToStartEvent event) {
		FriendsAndFoesStructurePoolElements.init(event.getServer());
	}

	private static void onAddVillagerTrades(VillagerTradesEvent event) {
		RegisterVillagerTradesEvent.EVENT.invoke(new RegisterVillagerTradesEvent(event.getType(), (level, listing) -> event.getTrades().get(level.intValue()).add(listing)));
	}

	private static void onAddReloadListeners(AddReloadListenerEvent event) {
		RegisterReloadListenerEvent.EVENT.invoke(new RegisterReloadListenerEvent((id, listener) -> event.addListener(listener)));
	}

	private static void onDatapackSync(OnDatapackSyncEvent event) {
		if (event.getPlayer() != null) {
			DatapackSyncEvent.EVENT.invoke(new DatapackSyncEvent(event.getPlayer()));
		} else {
			event.getPlayerList().getPlayers().forEach(player -> DatapackSyncEvent.EVENT.invoke(new DatapackSyncEvent(player)));
		}
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

	private static void onRegisterSpawnRestrictions(SpawnPlacementRegisterEvent event) {
		RegisterEntitySpawnRestrictionsEvent.EVENT.invoke(new RegisterEntitySpawnRestrictionsEvent(FriendsAndFoesForge.registerEntitySpawnRestriction(event)));
	}

	private static RegisterEntitySpawnRestrictionsEvent.Registrar registerEntitySpawnRestriction(
		SpawnPlacementRegisterEvent event
	) {
		return new RegisterEntitySpawnRestrictionsEvent.Registrar()
		{
			@Override
			public <T extends Mob> void register(
				EntityType<T> type,
				RegisterEntitySpawnRestrictionsEvent.Placement<T> placement
			) {
				event.register(type, placement.location(), placement.heightmap(), placement.predicate(), SpawnPlacementRegisterEvent.Operation.AND);
			}
		};
	}
}
