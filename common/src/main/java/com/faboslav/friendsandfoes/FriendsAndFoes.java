package com.faboslav.friendsandfoes;

import com.faboslav.friendsandfoes.api.MoobloomVariantManager;
import com.faboslav.friendsandfoes.config.FriendsAndFoesConfig;
import com.faboslav.friendsandfoes.config.omegaconfig.OmegaConfig;
import com.faboslav.friendsandfoes.events.RegisterVillagerTradesEvent;
import com.faboslav.friendsandfoes.events.lifecycle.*;
import com.faboslav.friendsandfoes.init.*;
import com.faboslav.friendsandfoes.item.DispenserAddedSpawnEgg;
import com.faboslav.friendsandfoes.modcompat.ModChecker;
import com.faboslav.friendsandfoes.network.MessageHandler;
import com.faboslav.friendsandfoes.network.packet.MoobloomVariantsSyncPacket;
import com.faboslav.friendsandfoes.platform.BiomeModifications;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FriendsAndFoes
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FriendsAndFoes.MOD_ID);
	private static final FriendsAndFoesConfig CONFIG = OmegaConfig.register(FriendsAndFoesConfig.class);
	public static final String MOD_ID = "friendsandfoes";

	public static Identifier makeID(String path) {
		return new Identifier(
			MOD_ID,
			path
		);
	}

	public static String makeStringID(String name) {
		return MOD_ID + ":" + name;
	}

	public static FriendsAndFoesConfig getConfig() {
		return CONFIG;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public static void init() {
		ModChecker.setupModCompat();

		FriendsAndFoesActivities.ACTIVITIES.init();
		FriendsAndFoesBlocks.BLOCKS.init();
		FriendsAndFoesCriterias.init();
		FriendsAndFoesEntityTypes.ENTITY_TYPES.init();
		FriendsAndFoesItems.ITEMS.init();
		FriendsAndFoesMemoryModuleTypes.MEMORY_MODULE_TYPES.init();
		FriendsAndFoesParticleTypes.PARTICLE_TYPES.init();
		FriendsAndFoesPointOfInterestTypes.POINT_OF_INTEREST_TYPES.init();
		FriendsAndFoesSensorTypes.SENSOR_TYPES.init();
		FriendsAndFoesSoundEvents.SOUND_EVENTS.init();
		FriendsAndFoesStatusEffects.STATUS_EFFECTS.init();
		FriendsAndFoesStructureProcessorTypes.STRUCTURE_PROCESSOR.init();
		FriendsAndFoesStructureProcessorTypes.init();
		FriendsAndFoesStructureTypes.STRUCTURES.init();
		FriendsAndFoesVillagerProfessions.VILLAGER_PROFESSIONS.init();

		RegisterReloadListenerEvent.EVENT.addListener(FriendsAndFoes::registerServerDataListeners);
		SetupEvent.EVENT.addListener(FriendsAndFoes::setup);
		DatapackSyncEvent.EVENT.addListener(MoobloomVariantsSyncPacket::sendToClient);
		RegisterFlammabilityEvent.EVENT.addListener(FriendsAndFoesBlocks::registerFlammablity);
		RegisterEntityAttributesEvent.EVENT.addListener(FriendsAndFoesEntityTypes::registerEntityAttributes);
		RegisterEntitySpawnRestrictionsEvent.EVENT.addListener(FriendsAndFoesEntityTypes::registerEntitySpawnRestrictions);
		AddSpawnBiomeModificationsEvent.EVENT.addListener(FriendsAndFoesEntityTypes::addSpawnBiomeModifications);
		RegisterVillagerTradesEvent.EVENT.addListener(FriendsAndFoesVillagerProfessions::registerVillagerTrades);
		SetupEvent.EVENT.addListener(DispenserAddedSpawnEgg::onSetup);
	}

	public static void lateInit() {
		FriendsAndFoesBlockEntityTypes.lateInit();
		FriendsAndFoesPointOfInterestTypes.lateInit();
		BiomeModifications.addButtercupFeature();
	}

	private static void registerServerDataListeners(final RegisterReloadListenerEvent event) {
		event.register(FriendsAndFoes.makeID("moobloom_variants"), MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER);
	}

	private static void setup(final SetupEvent event) {
		MessageHandler.init();
	}
}
