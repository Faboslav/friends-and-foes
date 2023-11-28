package com.faboslav.friendsandfoes;

import com.faboslav.friendsandfoes.config.FriendsAndFoesConfig;
import com.faboslav.friendsandfoes.config.omegaconfig.OmegaConfig;
import com.faboslav.friendsandfoes.init.*;
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
		FriendsAndFoesActivities.init();
		FriendsAndFoesBlockSetTypes.init();
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
	}

	public static void postInit() {
		BiomeModifications.addButtercupFeature();
		FriendsAndFoesBlocks.postInit();
		FriendsAndFoesEntityTypes.postInit();
		FriendsAndFoesItems.postInit();
		FriendsAndFoesBlockEntityTypes.postInit();
		FriendsAndFoesVillagerProfessions.postInit();
	}
}
