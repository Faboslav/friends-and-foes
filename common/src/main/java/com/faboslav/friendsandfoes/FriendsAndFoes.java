package com.faboslav.friendsandfoes;

import com.faboslav.friendsandfoes.init.*;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FriendsAndFoes
{
	public static final Logger LOGGER = LoggerFactory.getLogger(FriendsAndFoes.MOD_ID);
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

	public static void initRegisters() {
		ModBlocks.initRegister();
		ModBlockEntityTypes.initRegister();
		ModCriteria.init();
		ModEntity.initRegister();
		ModItems.initRegister();
		ModSounds.initRegister();
		ModVillagerProfessions.initRegister();
		ModStructureFeatures.initRegister();
	}

	public static void initCustomRegisters() {
		ModBlocks.init();
		ModBlockEntityTypes.init();
		ModEntity.init();
		ModItems.init();
		ModSounds.init();
		ModVillagerProfessions.init();
		ModStructureFeatures.init();
	}
}
