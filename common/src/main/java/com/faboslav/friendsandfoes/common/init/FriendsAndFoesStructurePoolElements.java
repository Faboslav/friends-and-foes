package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.util.StructurePoolHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

/**
 * @see net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool
 */
public final class FriendsAndFoesStructurePoolElements
{
	public static void init(MinecraftServer server) {
		Registry<StructureTemplatePool> templatePoolRegistry;
		//? if >=1.21.3 {
		templatePoolRegistry = server.registryAccess().lookupOrThrow(Registries.TEMPLATE_POOL);
		//?} else {
		/*templatePoolRegistry = server.registryAccess().registryOrThrow(Registries.TEMPLATE_POOL);
		*///?}

		ResourceLocation plainsPoolLocation = ResourceLocation.parse("minecraft:village/plains/houses");
		ResourceLocation savannaPoolLocation = ResourceLocation.parse("minecraft:village/savanna/houses");
		ResourceLocation taigaPoolLocation = ResourceLocation.parse("minecraft:village/taiga/houses");

		//? if <= 1.21.8 {
		/*ResourceLocation desertPoolLocation = ResourceLocation.parse("minecraft:village/desert/houses");
		ResourceLocation ancientCityCenterPoolLocation = ResourceLocation.parse("minecraft:ancient_city/city_center");
		ResourceLocation trialChambersCorridorPoolLocation = ResourceLocation.parse("minecraft:trial_chambers/corridor");
		*///?}

		if (FriendsAndFoes.getConfig().generateBeekeeperAreaStructureInVillages) {
			StructurePoolHelper.addLegacyElementToPool(templatePoolRegistry, plainsPoolLocation, "village/plains/houses/plains_beekeeper_area", FriendsAndFoes.getConfig().beekeeperAreaStructureWeight);
			StructurePoolHelper.addLegacyElementToPool(templatePoolRegistry, savannaPoolLocation, "village/savanna/houses/savanna_beekeeper_area", FriendsAndFoes.getConfig().beekeeperAreaStructureWeight);
			StructurePoolHelper.addLegacyElementToPool(templatePoolRegistry, taigaPoolLocation, "village/taiga/houses/taiga_beekeeper_area", FriendsAndFoes.getConfig().beekeeperAreaStructureWeight);
		}

		//? if <= 1.21.8 {
		/*if(FriendsAndFoes.getConfig().enableCopperGolemsInTrialChambers) {
			StructurePoolHelper.addSingleElementToPool(templatePoolRegistry, trialChambersCorridorPoolLocation, "trial_chambers/corridor/entrance_1_copper_golem", FriendsAndFoes.getConfig().copperGolemInTrialChambersWeight);
			StructurePoolHelper.addSingleElementToPool(templatePoolRegistry, trialChambersCorridorPoolLocation, "trial_chambers/corridor/entrance_2_copper_golem", FriendsAndFoes.getConfig().copperGolemInTrialChambersWeight);
			StructurePoolHelper.addSingleElementToPool(templatePoolRegistry, trialChambersCorridorPoolLocation, "trial_chambers/corridor/entrance_3_copper_golem", FriendsAndFoes.getConfig().copperGolemInTrialChambersWeight);
		}

		if (FriendsAndFoes.getConfig().generateCopperGolemWorkstationStructureInVillages) {
			StructurePoolHelper.addLegacyElementToPool(templatePoolRegistry, plainsPoolLocation, "village/plains/houses/plains_copper_golem_area", FriendsAndFoes.getConfig().copperGolemWorkstationStructureWeight);
			StructurePoolHelper.addLegacyElementToPool(templatePoolRegistry, desertPoolLocation, "village/desert/houses/desert_copper_golem_area", FriendsAndFoes.getConfig().copperGolemWorkstationStructureWeight);
			StructurePoolHelper.addLegacyElementToPool(templatePoolRegistry, savannaPoolLocation, "village/savanna/houses/savanna_copper_golem_area", FriendsAndFoes.getConfig().copperGolemWorkstationStructureWeight);
			StructurePoolHelper.addLegacyElementToPool(templatePoolRegistry, taigaPoolLocation, "village/taiga/houses/taiga_copper_golem_area", FriendsAndFoes.getConfig().copperGolemWorkstationStructureWeight);
		}

		if (FriendsAndFoes.getConfig().generateCopperGolemInAncientCity) {
			StructurePoolHelper.addLegacyElementToPool(templatePoolRegistry, ancientCityCenterPoolLocation, "ancient_city/city_center/city_center_1", FriendsAndFoes.getConfig().copperGolemAncientCityCenterWeight);
			StructurePoolHelper.addLegacyElementToPool(templatePoolRegistry, ancientCityCenterPoolLocation, "ancient_city/city_center/city_center_2", FriendsAndFoes.getConfig().copperGolemAncientCityCenterWeight);
			StructurePoolHelper.addLegacyElementToPool(templatePoolRegistry, ancientCityCenterPoolLocation, "ancient_city/city_center/city_center_3", FriendsAndFoes.getConfig().copperGolemAncientCityCenterWeight);
		}
		*///?}
	}

	private FriendsAndFoesStructurePoolElements() {
	}
}
