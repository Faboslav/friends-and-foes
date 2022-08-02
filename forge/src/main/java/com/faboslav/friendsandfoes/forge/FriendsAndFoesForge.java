package com.faboslav.friendsandfoes.forge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.FriendsAndFoesClient;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.platform.forge.RegistryHelperImpl;
import com.faboslav.friendsandfoes.util.CustomRaidMember;
import com.faboslav.friendsandfoes.util.ServerWorldSpawnersUtil;
import com.faboslav.friendsandfoes.util.UpdateChecker;
import com.faboslav.friendsandfoes.world.spawner.IceologerSpawner;
import com.faboslav.friendsandfoes.world.spawner.IllusionerSpawner;
import net.minecraft.SharedConstants;
import net.minecraft.block.Oxidizable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.item.HoneycombItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.Map;
import java.util.function.Supplier;

import static com.faboslav.friendsandfoes.FriendsAndFoes.serverTickDeltaCounter;

@Mod(FriendsAndFoes.MOD_ID)
public final class FriendsAndFoesForge
{
	public FriendsAndFoesForge() {
		UpdateChecker.checkForNewUpdates();
		FriendsAndFoes.init();

		if (FMLEnvironment.dist == Dist.CLIENT) {
			FriendsAndFoesClient.init();
		}

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		RegistryHelperImpl.BLOCKS.register(bus);
		FriendsAndFoesEntityTypes.previousUseChoiceTypeRegistrations = SharedConstants.useChoiceTypeRegistrations;
		SharedConstants.useChoiceTypeRegistrations = false;
		RegistryHelperImpl.ENTITY_TYPES.register(bus);
		SharedConstants.useChoiceTypeRegistrations = FriendsAndFoesEntityTypes.previousUseChoiceTypeRegistrations;
		RegistryHelperImpl.ITEMS.register(bus);
		RegistryHelperImpl.POINT_OF_INTEREST_TYPES.register(bus);
		RegistryHelperImpl.SOUND_EVENTS.register(bus);
		RegistryHelperImpl.VILLAGER_PROFESSIONS.register(bus);

		bus.addListener(FriendsAndFoesForge::init);
		bus.addListener(FriendsAndFoesForge::registerEntityAttributes);

		var forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(FriendsAndFoesForge::initSpawners);
		forgeBus.addListener(FriendsAndFoesForge::initTickDeltaCounter);

		MinecraftForge.EVENT_BUS.register(this);
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

	private static void initTickDeltaCounter(final ServerTickEvent event) {
		if (event.phase != TickEvent.Phase.START) {
			return;
		}

		serverTickDeltaCounter.beginRenderTick(Util.getMeasuringTimeMs());
	}
}
