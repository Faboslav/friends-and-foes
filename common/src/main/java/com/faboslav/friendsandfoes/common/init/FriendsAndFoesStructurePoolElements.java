package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.util.StructurePoolHelper;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;

/**
 * @see net.minecraft.structure.pool.StructurePool
 */
public final class FriendsAndFoesStructurePoolElements
{
	// TODO change to event?
	public static void init(MinecraftServer server) {
		Registry<StructurePool> templatePoolRegistry = server.getRegistryManager().get(RegistryKeys.TEMPLATE_POOL);

		Identifier plainsPoolLocation = Identifier.of("minecraft:village/plains/houses");
		Identifier desertPoolLocation = Identifier.of("minecraft:village/desert/houses");
		Identifier savannaPoolLocation = Identifier.of("minecraft:village/savanna/houses");
		Identifier taigaPoolLocation = Identifier.of("minecraft:village/taiga/houses");
		Identifier ancientCityCenterPoolLocation = Identifier.of("minecraft:ancient_city/city_center");

		if (FriendsAndFoes.getConfig().generateBeekeeperAreaStructure) {
			StructurePoolHelper.addElementToPool(templatePoolRegistry, plainsPoolLocation, "village/plains/houses/plains_beekeeper_area", FriendsAndFoes.getConfig().beekeeperAreaStructureWeight);
			StructurePoolHelper.addElementToPool(templatePoolRegistry, savannaPoolLocation, "village/savanna/houses/savanna_beekeeper_area", FriendsAndFoes.getConfig().beekeeperAreaStructureWeight);
			StructurePoolHelper.addElementToPool(templatePoolRegistry, taigaPoolLocation, "village/taiga/houses/taiga_beekeeper_area", FriendsAndFoes.getConfig().beekeeperAreaStructureWeight);
		}

		if (FriendsAndFoes.getConfig().generateCopperGolemAreaStructure) {
			StructurePoolHelper.addElementToPool(templatePoolRegistry, plainsPoolLocation, "village/plains/houses/plains_copper_golem_area", FriendsAndFoes.getConfig().copperGolemAreaStructureWeight);
			StructurePoolHelper.addElementToPool(templatePoolRegistry, desertPoolLocation, "village/desert/houses/desert_copper_golem_area", FriendsAndFoes.getConfig().copperGolemAreaStructureWeight);
			StructurePoolHelper.addElementToPool(templatePoolRegistry, savannaPoolLocation, "village/savanna/houses/savanna_copper_golem_area", FriendsAndFoes.getConfig().copperGolemAreaStructureWeight);
			StructurePoolHelper.addElementToPool(templatePoolRegistry, taigaPoolLocation, "village/taiga/houses/taiga_copper_golem_area", FriendsAndFoes.getConfig().copperGolemAreaStructureWeight);
		}

		if (FriendsAndFoes.getConfig().generateCopperGolemInAncientCity) {
			StructurePoolHelper.addElementToPool(templatePoolRegistry, ancientCityCenterPoolLocation, "ancient_city/city_center/city_center_1", FriendsAndFoes.getConfig().copperGolemAncientCityCenterWeight);
			StructurePoolHelper.addElementToPool(templatePoolRegistry, ancientCityCenterPoolLocation, "ancient_city/city_center/city_center_2", FriendsAndFoes.getConfig().copperGolemAncientCityCenterWeight);
			StructurePoolHelper.addElementToPool(templatePoolRegistry, ancientCityCenterPoolLocation, "ancient_city/city_center/city_center_3", FriendsAndFoes.getConfig().copperGolemAncientCityCenterWeight);
		}
	}

	private FriendsAndFoesStructurePoolElements() {
	}
}
