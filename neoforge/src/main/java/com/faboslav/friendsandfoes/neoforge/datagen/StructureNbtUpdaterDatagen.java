package com.faboslav.friendsandfoes.neoforge.datagen;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import net.minecraft.data.DataGenerator;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

//? >=1.21.4 {
//?} else {
/*import net.neoforged.neoforge.common.data.ExistingFileHelper;
*///?}

// Source: https://github.com/BluSunrize/ImmersiveEngineering/blob/1.20.1/src/datagen/java/blusunrize/immersiveengineering/data/IEDataGenerator.java
@EventBusSubscriber(modid = FriendsAndFoes.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class StructureNbtUpdaterDatagen
{
	@SubscribeEvent
	public static void gatherData(
		//? >=1.21.4 {
		GatherDataEvent.Server event
		//?} else {
		/*GatherDataEvent event
		*///?}
	) {
		//? >=1.21.4 {
		ResourceManager resourceManager = event.getResourceManager(PackType.SERVER_DATA);
		DataGenerator gen = event.getGenerator();
		final var output = gen.getPackOutput();
		gen.addProvider(true, new StructureNbtUpdater("structure", FriendsAndFoes.MOD_ID, resourceManager, output));
		//?} else {
		/*ExistingFileHelper exHelper = event.getExistingFileHelper();
		DataGenerator gen = event.getGenerator();
		final var output = gen.getPackOutput();

		if (event.includeServer()) {
			gen.addProvider(true, new StructureNbtUpdater("structures", FriendsAndFoes.MOD_ID, exHelper, output));
		}
		*///?}
	}
}
