package com.faboslav.friendsandfoes.neoforge.datagen;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import net.minecraft.data.DataGenerator;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import net.neoforged.neoforge.common.data.ExistingFileHelper;

// Source: https://github.com/BluSunrize/ImmersiveEngineering/blob/1.20.1/src/datagen/java/blusunrize/immersiveengineering/data/IEDataGenerator.java
//@EventBusSubscriber(modid = FriendsAndFoes.MOD_ID)
public class StructureNbtUpdaterDatagen
{
	//@SubscribeEvent
	public static void gatherData(

		GatherDataEvent event

	) {

		ExistingFileHelper exHelper = event.getExistingFileHelper();
		DataGenerator gen = event.getGenerator();
		final var output = gen.getPackOutput();

		if (event.includeServer()) {
			gen.addProvider(true, new StructureNbtUpdater("structures", FriendsAndFoes.MOD_ID, exHelper, output));
		}

	}
}
