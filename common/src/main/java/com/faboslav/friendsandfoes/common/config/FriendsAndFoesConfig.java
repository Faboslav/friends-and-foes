package com.faboslav.friendsandfoes.common.config;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.config.annotation.Category;
import com.faboslav.friendsandfoes.common.config.annotation.Description;
import com.faboslav.friendsandfoes.common.config.omegaconfig.api.Config;

public final class FriendsAndFoesConfig implements Config
{
	@Category("General")
	@Description("Check if the new update of mod is available")
	public boolean checkForNewUpdates = false;

	@Category("Copper Golem")
	@Description("Enable")
	public boolean enableCopperGolem = true;

	@Description("Generate copper golem area structure in villages")
	public boolean generateCopperGolemAreaStructure = true;

	@Description("Copper Golem area structure spawn chance")
	public int copperGolemAreaStructureWeight = 1;

	@Description("Generate copper golem in the center piece in the ancient cities")
	public boolean generateCopperGolemInAncientCity = true;

	@Description("Generate copper golem in the center piece in the ancient cities spawn chance")
	public int copperGolemAncientCityCenterWeight = 10;

	@Category("Crab")
	@Description("Enable")
	public boolean enableCrab = true;

	@Description("Enable spawn")
	public boolean enableCrabSpawn = true;

	@Description("Spawn weight")
	public int crabSpawnWeight = 14;

	@Description("Minimal spawn group size")
	public int crabSpawnMinGroupSize = 2;

	@Description("Maximal spawn group size")
	public int crabSpawnMaxGroupSize = 4;

	@Description("Reach status effect modifier (range in blocks)")
	public int reachingStatusEffectModifier = 1;

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

	@Description("Spawn weight")
	public int moobloomSpawnWeight = 4;

	@Description("Minimal spawn group size")
	public int moobloomSpawnMinGroupSize = 2;

	@Description("Maximal spawn group size")
	public int moobloomSpawnMaxGroupSize = 4;

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

	@Category("Rascal")
	@Description("Enable rascal")
	public boolean enableRascal = true;

	@Description("Enable rascal spawn")
	public boolean enableRascalSpawn = true;

	@Category("Tuff Golem")
	@Description("Enable tuff golem")
	public boolean enableTuffGolem = true;

	@Description("Generate tuff golem in stronghold libraries")
	public boolean generateTuffGolemInStronghold = true;

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
