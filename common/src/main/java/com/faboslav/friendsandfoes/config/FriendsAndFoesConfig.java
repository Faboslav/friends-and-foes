package com.faboslav.friendsandfoes.config;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.config.annotation.Category;
import com.faboslav.friendsandfoes.config.annotation.Description;
import com.faboslav.friendsandfoes.config.omegaconfig.api.Config;

public final class FriendsAndFoesConfig implements Config
{
	@Category("General")
	@Description("Check if the new update of mod is available (when enabled, info is in the console)")
	public boolean checkForNewUpdates = true;

	@Category("Copper Golem")
	@Description("Enable")
	public boolean enableCopperGolem = true;

	@Description("Generate copper golem area structure in villages")
	public boolean generateCopperGolemAreaStructure = true;

	@Description("Copper Golem area structure spawn chance")
	public int copperGolemAreaStructureWeight = 1;

	@Description("Occasionally generate copper golem in the center piece in the ancient cities")
	public boolean generateCopperGolemInAncientCity = true;

	@Description("Copper Golem in ancient cities spawn chance")
	public int copperGolemAncientCityCenterWeight = 3;

	@Category("Glare")
	@Description("Enable")
	public boolean enableGlare = true;

	@Description("Enable spawn")
	public boolean enableGlareSpawn = true;

	@Description("Whenever will glare shake off glow berries and eat glow berries")
	public boolean enableGlareGriefing = true;

	@Description("Spawn weight")
	public int glareSpawnWeight = 4;

	@Description("Minimal spawn group size")
	public int glareSpawnMinGroupSize = 1;

	@Description("Maximal spawn group size")
	public int glareSpawnMaxGroupSize = 1;

	@Category("Mauler")
	@Description("Enable")
	public boolean enableMauler = true;

	@Description("Enable spawn")
	public boolean enableMaulerSpawn = true;

	@Description("Spawn weight in desert biome")
	public int maulerDesertSpawnWeight = 8;

	@Description("Minimal spawn group size in desert biome")
	public int maulerDesertSpawnMinGroupSize = 1;

	@Description("Maximal spawn group size in desert biome")
	public int maulerDesertSpawnMaxGroupSize = 1;

	@Description("Spawn weight in badlands biome")
	public int maulerBadlandsSpawnWeight = 16;

	@Description("Minimal spawn group size in badlands biome")
	public int maulerBadlandsSpawnMinGroupSize = 1;

	@Description("Maximal spawn group size in badlands biome")
	public int maulerBadlandsSpawnMaxGroupSize = 1;

	@Description("Spawn weight in savanna biome")
	public int maulerSavannaSpawnWeight = 32;

	@Description("Minimal spawn group size in savanna biome")
	public int maulerSavannaSpawnMinGroupSize = 1;

	@Description("Maximal spawn group size in savanna biome")
	public int maulerSavannaSpawnMaxGroupSize = 1;

	@Category("Moobloom")
	@Description("Enable")
	public boolean enableMoobloom = true;

	@Description("Enable spawn")
	public boolean enableMoobloomSpawn = true;

	@Description("Spawn weight in flower forest biome")
	public int moobloomFlowerForestSpawnWeight = 32;

	@Description("Minimal spawn group size in flower forest biome")
	public int moobloomFlowerForestSpawnMinGroupSize = 2;

	@Description("Maximal spawn group size in flower forest biome")
	public int moobloomFlowerForestSpawnMaxGroupSize = 4;

	@Description("Spawn weight in meadow biome")
	public int moobloomMeadowSpawnWeight = 2;

	@Description("Minimal spawn group size in meadow biome")
	public int moobloomMeadowSpawnMinGroupSize = 2;

	@Description("Maximal spawn group size in meadow biome")
	public int moobloomMeadowSpawnMaxGroupSize = 4;

	@Category("Iceologer")
	@Description("Enable")
	public boolean enableIceologer = true;

	@Description("Enable spawn")
	public boolean enableIceologerSpawn = true;

	@Description("Enable in raids")
	public boolean enableIceologerInRaids = true;

	@Description("Generate iceologer cabin structure")
	public boolean generateIceologerCabinStructure = true;

	@Category("Illusioner")
	@Description("Enable")
	public boolean enableIllusioner = true;

	@Description("Enable spawn")
	public boolean enableIllusionerSpawn = true;

	@Description("Enable in raids")
	public boolean enableIllusionerInRaids = true;

	@Description("Generate illusioner shack structure")
	public boolean generateIllusionerShackStructure = true;

	@Description("Generate illusioner training grounds")
	public boolean generateIllusionerTrainingGroundsStructure = true;

	@Category("Zombie Horse")
	@Description("Enable trap")
	public boolean enableZombieHorseTrap = true;

	@Category("Wildfire")
	@Description("Enable wildfire")
	public boolean enableWildfire = true;

	@Description("Generate citadel structure")
	public boolean generateCitadelStructure = true;

	@Category("Beekeeper")
	@Description("Enable villager profession")
	public boolean enableBeekeeperVillagerProfession = true;

	@Description("Generate beekeeper area structure in villages")
	public boolean generateBeekeeperAreaStructure = true;

	@Description("Beekeeper area structure spawn chance")
	public int beekeeperAreaStructureWeight = 2;

	@Override
	public String getName() {
		return FriendsAndFoes.MOD_ID;
	}
}
