package com.faboslav.friendsandfoes.forge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.events.AddItemGroupEntriesEvent;
import com.faboslav.friendsandfoes.common.events.RegisterItemGroupsEvent;
import com.faboslav.friendsandfoes.common.events.RegisterVillagerTradesEvent;
import com.faboslav.friendsandfoes.common.events.block.RegisterBlockSetTypeEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.*;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStructurePoolElements;
import com.faboslav.friendsandfoes.common.init.registry.forge.ResourcefulRegistriesImpl;
import com.faboslav.friendsandfoes.common.util.CustomRaidMember;
import com.faboslav.friendsandfoes.common.util.ServerWorldSpawnersUtil;
import com.faboslav.friendsandfoes.common.util.UpdateChecker;
import com.faboslav.friendsandfoes.common.world.spawner.IceologerSpawner;
import com.faboslav.friendsandfoes.common.world.spawner.IllusionerSpawner;
import com.faboslav.friendsandfoes.forge.init.FriendsAndFoesBiomeModifiers;
import com.faboslav.friendsandfoes.forge.mixin.FireBlockAccessor;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
<<<<<<< HEAD
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.item.ItemGroup;
=======
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
>>>>>>> 1.19.4
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
<<<<<<< HEAD
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
=======
import net.minecraftforge.event.CreativeModeTabEvent;
>>>>>>> 1.19.4
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.List;

@Mod(FriendsAndFoes.MOD_ID)
public final class FriendsAndFoesForge
{
	public FriendsAndFoesForge() {
		UpdateChecker.checkForNewUpdates();

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus eventBus = MinecraftForge.EVENT_BUS;

		modEventBus.addListener(EventPriority.NORMAL, ResourcefulRegistriesImpl::onRegisterForgeRegistries);
		FriendsAndFoes.init();
		FriendsAndFoesBiomeModifiers.BIOME_MODIFIERS.register(modEventBus);

		if (FMLEnvironment.dist == Dist.CLIENT) {
			FriendsAndFoesForgeClient.init(modEventBus, eventBus);
		}

<<<<<<< HEAD
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

		modEventBus.addListener(FriendsAndFoesForge::registerEntityAttributes);
		modEventBus.addListener(FriendsAndFoesForge::addItemsToTabs);

=======
>>>>>>> 1.19.4
		eventBus.addListener(FriendsAndFoesForge::initSpawners);
		eventBus.addListener(FriendsAndFoesForge::onAddVillagerTrades);
		eventBus.addListener(FriendsAndFoesForge::onAddReloadListeners);
		eventBus.addListener(FriendsAndFoesForge::onDatapackSync);
		modEventBus.addListener(FriendsAndFoesForge::onSetup);
		modEventBus.addListener(FriendsAndFoesForge::onRegisterAttributes);
		modEventBus.addListener(FriendsAndFoesForge::onRegisterSpawnRestrictions);
		modEventBus.addListener(FriendsAndFoesForge::onRegisterItemGroups);
		modEventBus.addListener(FriendsAndFoesForge::onAddItemGroupEntries);
	}

	private static void onSetup(final FMLCommonSetupEvent event) {
		SetupEvent.EVENT.invoke(new SetupEvent(event::enqueueWork));

		event.enqueueWork(() -> {
			FriendsAndFoes.lateInit();

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

			RegisterBlockSetTypeEvent.EVENT.invoke(new RegisterBlockSetTypeEvent(BlockSetType::register));
			RegisterFlammabilityEvent.EVENT.invoke(new RegisterFlammabilityEvent((item, igniteOdds, burnOdds) ->
				((FireBlockAccessor) Blocks.FIRE).invokeRegisterFlammableBlock(item, igniteOdds, burnOdds)));
		});
	}

<<<<<<< HEAD
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
=======
	private static void onAddReloadListeners(AddReloadListenerEvent event) {
		RegisterReloadListenerEvent.EVENT.invoke(new RegisterReloadListenerEvent((id, listener) -> event.addListener(listener)));
>>>>>>> 1.19.4
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

	private static void onAddVillagerTrades(VillagerTradesEvent event) {
		RegisterVillagerTradesEvent.EVENT.invoke(new RegisterVillagerTradesEvent(event.getType(), (i, listing) -> event.getTrades().get(i.intValue()).add(listing)));
	}

	private static void onRegisterItemGroups(CreativeModeTabEvent.Register event) {
		RegisterItemGroupsEvent.EVENT.invoke(new RegisterItemGroupsEvent((id, operator, initialDisplayItems) ->
			event.registerCreativeModeTab(id, builder -> {
				operator.accept(builder);
				builder.entries((flag, output) -> {
					List<ItemStack> stacks = Lists.newArrayList();
					initialDisplayItems.accept(stacks);
					output.addAll(stacks);
				});
			})
		));
	}

	private static void onAddItemGroupEntries(CreativeModeTabEvent.BuildContents event) {
		AddItemGroupEntriesEvent.EVENT.invoke(new AddItemGroupEntriesEvent(AddItemGroupEntriesEvent.Type.toType(event.getTab()), event.getTab(), event.hasPermissions(), event::add));
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
			public <T extends MobEntity> void register(
				EntityType<T> type,
				RegisterEntitySpawnRestrictionsEvent.Placement<T> placement
			) {
				event.register(type, placement.location(), placement.heightmap(), placement.predicate(), SpawnPlacementRegisterEvent.Operation.AND);
			}
		};
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
