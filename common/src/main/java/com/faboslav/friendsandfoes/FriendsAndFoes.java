package com.faboslav.friendsandfoes;

import com.faboslav.friendsandfoes.api.MoobloomVariantManager;
import com.faboslav.friendsandfoes.config.FriendsAndFoesConfig;
import com.faboslav.friendsandfoes.config.omegaconfig.OmegaConfig;
import com.faboslav.friendsandfoes.events.lifecycle.*;
import com.faboslav.friendsandfoes.init.*;
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

		FriendsAndFoesActivities.init();
		FriendsAndFoesBlocks.init();
		FriendsAndFoesCriteria.init();
		FriendsAndFoesEntityTypes.init();
		FriendsAndFoesItems.init();
		FriendsAndFoesMemoryModuleTypes.init();
		FriendsAndFoesMemorySensorType.init();
		FriendsAndFoesParticleTypes.init();
		FriendsAndFoesPointOfInterestTypes.init();
		FriendsAndFoesSoundEvents.init();
		FriendsAndFoesStructureProcessorTypes.init();
		FriendsAndFoesStructureTypes.init();
		FriendsAndFoesVillagerProfessions.init();

		RegisterReloadListenerEvent.EVENT.addListener(FriendsAndFoes::registerServerDataListeners);
		RegisterOxidizablesEvent.EVENT.addListener(FriendsAndFoesBlocks::registerOxidizables);
		AddSpawnBiomeModificationsEvent.EVENT.addListener(FriendsAndFoesEntityTypes::addSpawnBiomeModifications);
		SetupEvent.EVENT.addListener(FriendsAndFoes::setup);
		DatapackSyncEvent.EVENT.addListener(MoobloomVariantsSyncPacket::sendToClient);
	}

	public static void postInit() {
		BiomeModifications.addButtercupFeature();
		FriendsAndFoesBlocks.postInit();
		FriendsAndFoesEntityTypes.postInit();
		FriendsAndFoesBlockEntityTypes.postInit();
		FriendsAndFoesStructureProcessorTypes.postInit();
		FriendsAndFoesVillagerProfessions.postInit();
	}

	private static void registerServerDataListeners(final RegisterReloadListenerEvent event) {
		event.register(FriendsAndFoes.makeID("moobloom_variants"), MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER);
	}

	private static void setup(final SetupEvent event) {
		MessageHandler.init();
	}
}
