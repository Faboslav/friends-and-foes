package com.faboslav.friendsandfoes.common.config;

public final class FriendsAndFoesConfig
{
	public boolean enableBeekeeperVillagerProfession = true;
	public boolean generateBeekeeperAreaStructureInVillages = true;
	public int beekeeperAreaStructureWeight = 2;

	//? if <= 1.21.8 {
	/*public boolean enableCopperGolem = true;
	public boolean generateCopperGolemWorkstationStructureInVillages = true;
	public int copperGolemWorkstationStructureWeight = 1;
	public boolean generateCopperGolemInAncientCity = true;
	public int copperGolemAncientCityCenterWeight = 10;
	public boolean enableCopperGolemsInTrialChambers = true;
	public int copperGolemInTrialChambersWeight = 4;
	public boolean enableLightningRodOxidation = true;
	*///?}
	public boolean enableCrab = true;
	public boolean enableCrabSpawn = true;
	public int crabSpawnWeight = 14;
	public int crabSpawnMinGroupSize = 2;
	public int crabSpawnMaxGroupSize = 4;
	public int crabPotionOfReachingDuration = 3600;
	public int reachingStatusEffectModifier = 1;

	public boolean enableGlare = true;
	public boolean enableGlareSpawn = true;
	public boolean enableGlareGriefing = true;
	public int glareSpawnWeight = 4;
	public int glareSpawnMinGroupSize = 1;
	public int glareSpawnMaxGroupSize = 1;

	public boolean enableIceologer = true;
	public boolean enableIceologerSpawn = true;
	public boolean enableIceologerInRaids = true;
	public boolean fleeAwayFromIronGolems = true;
	public boolean generateIceologerCabinStructure = true;

	public boolean enableIllusioner = true;
	public boolean enableIllusionerSpawn = true;
	public boolean enableIllusionerInRaids = true;
	public boolean replaceVanillaIllusioner = true;
	public boolean generateIllusionerShackStructure = true;
	public boolean generateIllusionerTrainingGroundsStructure = true;
	public int illusionerMaxIllusionsCount = 9;
	public int illusionerIllusionLifetimeTicks = 600;
	public int illusionerInvisibilityTicks = 60;

	public boolean enableMauler = true;
	public boolean enableMaulerSpawn = true;
	public boolean enableMaulerSpawnInDesert = true;
	public int maulerDesertSpawnWeight = 8;
	public int maulerDesertSpawnMinGroupSize = 1;
	public int maulerDesertSpawnMaxGroupSize = 1;
	public boolean enableMaulerSpawnInBadlands = true;
	public int maulerBadlandsSpawnWeight = 16;
	public int maulerBadlandsSpawnMinGroupSize = 1;
	public int maulerBadlandsSpawnMaxGroupSize = 1;
	public boolean enableMaulerSpawnInSavanna = true;
	public int maulerSavannaSpawnWeight = 32;
	public int maulerSavannaSpawnMinGroupSize = 1;
	public int maulerSavannaSpawnMaxGroupSize = 1;

	public boolean enableMoobloom = true;
	public boolean enableMoobloomSpawn = true;
	public int moobloomSpawnWeight = 4;
	public int moobloomSpawnMinGroupSize = 2;
	public int moobloomSpawnMaxGroupSize = 4;

	public boolean enableRascal = true;
	public boolean enableRascalSpawn = true;
	public int rascalSpawnWeight = 4;
	public int rascalSpawnMinGroupSize = 1;
	public int rascalSpawnMaxGroupSize = 1;
	public boolean rascalGiveRewardInBundle = true;

	public boolean enableTuffGolem = true;
	public boolean generateTuffGolemInStronghold = true;

	public boolean enableWildfire = true;
	public boolean generateCitadelStructure = true;
	public boolean enableZombieHorseTrap = true;

	public static void load() {
		FriendsAndFoesConfigSerializer.load();
	}

	public static void save() {
		FriendsAndFoesConfigSerializer.save();
	}
}