package com.faboslav.friendsandfoes;

import com.faboslav.friendsandfoes.config.FriendsAndFoesConfig;
import com.faboslav.friendsandfoes.config.omegaconfig.OmegaConfig;
import com.faboslav.friendsandfoes.init.*;
import com.faboslav.friendsandfoes.util.ServerTickDeltaCounter;
import com.faboslav.friendsandfoes.util.UpdateChecker;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public final class FriendsAndFoes
{
	private static final FriendsAndFoesConfig CONFIG = OmegaConfig.register(FriendsAndFoesConfig.class);
	private static final Logger LOGGER = LoggerFactory.getLogger(FriendsAndFoes.MOD_ID);
	public static final String MOD_ID = "friendsandfoes";
	public static final String MOD_VERSION = "1.5.1";
	public static final ServerTickDeltaCounter serverTickDeltaCounter = new ServerTickDeltaCounter(20.0F, 0L);

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

	public static void checkForNewUpdates() {
		CompletableFuture.runAsync(() -> {
			if (FriendsAndFoes.getConfig().checkForNewUpdates) {
				String latestVersion = UpdateChecker.getLatestVersion();

				if (latestVersion == null) {
					return;
				}

				if (latestVersion.equals(MOD_VERSION) == false) {
					getLogger().info("[Friends&Foes] An update is available! You're using {} version but the latest version is {}!", MOD_VERSION, latestVersion);
				}
			}
		});
	}

	public static void initRegisters() {
		ModBlocks.initRegister();
		ModCriteria.init();
		ModEntityTypes.initRegister();
		ModItems.initRegister();
		ModPointOfInterestTypes.initRegister();
		ModSounds.initRegister();
		ModVillagerProfessions.initRegister();
	}

	public static void initCustomRegisters() {
		ModBlocks.init();
		ModEntityTypes.init();
		ModItems.init();
		ModBlockEntityTypes.init();
		ModSounds.init();
		ModPointOfInterestTypes.init();
		ModVillagerProfessions.init();
	}
}
