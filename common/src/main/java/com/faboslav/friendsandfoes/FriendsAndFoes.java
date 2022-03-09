package com.faboslav.friendsandfoes;

import com.faboslav.friendsandfoes.init.*;
import com.faboslav.friendsandfoes.world.gen.feature.StructureFeature;
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

	public static void init() {
		ModEntity.init();
		ModBlocks.init();
		ModItems.init();
		ModCriteria.init();
		ModSounds.init();
		ModVillagerProfessions.init();
		StructureFeature.init();
	}
}
