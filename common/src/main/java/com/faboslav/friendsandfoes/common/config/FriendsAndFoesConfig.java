package com.faboslav.friendsandfoes.common.config;

public final class FriendsAndFoesConfig
{
	public static final boolean ENABLE_BEEKEEPER_VILLAGER_PROFESSION_DEFAULT_VALUE = true;
	public static final boolean GENERATE_BEEKEEPER_AREA_STRUCTURE_IN_VILLAGES_DEFAULT_VALUE = true;
	public static final int BEEKEEPER_AREA_STRUCTURE_WEIGHT_DEFAULT_VALUE = 2;

	public static final boolean ENABLE_BARNACLE_DEFAULT_VALUE = true;
	public static final boolean ENABLE_BARNACLE_SPAWN_DEFAULT_VALUE = true;
	public static final int BARNACLE_SPAWN_WEIGHT_DEFAULT_VALUE = 4;
	public static final int BARNACLE_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE = 1;
	public static final int BARNACLE_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE = 1;

	//? if <= 1.21.8 {
	/*public static final boolean ENABLE_COPPER_GOLEM_DEFAULT_VALUE = true;
	public static final boolean GENERATE_COPPER_GOLEM_WORKSTATION_STRUCTURE_IN_VILLAGES_DEFAULT_VALUE = true;
	public static final int COPPER_GOLEM_WORKSTATION_STRUCTURE_WEIGHT_DEFAULT_VALUE = 1;
	public static final boolean GENERATE_COPPER_GOLEM_IN_ANCIENT_CITY_DEFAULT_VALUE = true;
	public static final int COPPER_GOLEM_ANCIENT_CITY_CENTER_WEIGHT_DEFAULT_VALUE = 10;
	public static final boolean ENABLE_COPPER_GOLEMS_IN_TRIAL_CHAMBERS_DEFAULT_VALUE = true;
	public static final int COPPER_GOLEM_IN_TRIAL_CHAMBERS_WEIGHT_DEFAULT_VALUE = 4;
	public static final boolean ENABLE_LIGHTNING_ROD_OXIDATION_DEFAULT_VALUE = true;
	*///?}

	public static final boolean ENABLE_CRAB_DEFAULT_VALUE = true;
	public static final boolean ENABLE_CRAB_SPAWN_DEFAULT_VALUE = true;
	public static final int CRAB_SPAWN_WEIGHT_DEFAULT_VALUE = 14;
	public static final int CRAB_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE = 2;
	public static final int CRAB_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE = 4;
	public static final int CRAB_POTION_OF_REACHING_DURATION_DEFAULT_VALUE = 180;
	public static final int REACHING_STATUS_EFFECT_MODIFIER_DEFAULT_VALUE = 1;

	public static final boolean ENABLE_GLARE_DEFAULT_VALUE = true;
	public static final boolean ENABLE_GLARE_SPAWN_DEFAULT_VALUE = true;
	public static final boolean ENABLE_GLARE_GRIEFING_DEFAULT_VALUE = true;
	public static final int GLARE_SPAWN_WEIGHT_DEFAULT_VALUE = 4;
	public static final int GLARE_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE = 1;
	public static final int GLARE_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE = 1;

	public static final boolean ENABLE_ICEOLOGER_DEFAULT_VALUE = true;
	public static final boolean ENABLE_ICEOLOGER_SPAWN_DEFAULT_VALUE = true;
	public static final boolean ENABLE_ICEOLOGER_IN_RAIDS_DEFAULT_VALUE = true;
	public static final boolean FLEE_AWAY_FROM_IRON_GOLEMS_DEFAULT_VALUE = true;
	public static final boolean GENERATE_ICEOLOGER_CABIN_STRUCTURE_DEFAULT_VALUE = true;

	public static final boolean ENABLE_ILLUSIONER_DEFAULT_VALUE = true;
	public static final boolean ENABLE_ILLUSIONER_SPAWN_DEFAULT_VALUE = true;
	public static final boolean ENABLE_ILLUSIONER_IN_RAIDS_DEFAULT_VALUE = true;
	public static final boolean REPLACE_VANILLA_ILLUSIONER_DEFAULT_VALUE = true;
	public static final boolean GENERATE_ILLUSIONER_SHACK_STRUCTURE_DEFAULT_VALUE = true;
	public static final boolean GENERATE_ILLUSIONER_TRAINING_GROUNDS_STRUCTURE_DEFAULT_VALUE = true;
	public static final int ILLUSIONER_MAX_ILLUSIONS_COUNT_DEFAULT_VALUE = 9;
	public static final int ILLUSIONER_ILLUSION_LIFETIME_TICKS_DEFAULT_VALUE = 600;
	public static final int ILLUSIONER_INVISIBILITY_TICKS_DEFAULT_VALUE = 60;

	public static final boolean ENABLE_MAULER_DEFAULT_VALUE = true;
	public static final boolean ENABLE_MAULER_SPAWN_DEFAULT_VALUE = true;
	public static final boolean ENABLE_MAULER_SPAWN_IN_DESERT_DEFAULT_VALUE = true;
	public static final int MAULER_DESERT_SPAWN_WEIGHT_DEFAULT_VALUE = 8;
	public static final int MAULER_DESERT_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE = 1;
	public static final int MAULER_DESERT_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE = 1;
	public static final boolean ENABLE_MAULER_SPAWN_IN_BADLANDS_DEFAULT_VALUE = true;
	public static final int MAULER_BADLANDS_SPAWN_WEIGHT_DEFAULT_VALUE = 16;
	public static final int MAULER_BADLANDS_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE = 1;
	public static final int MAULER_BADLANDS_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE = 1;
	public static final boolean ENABLE_MAULER_SPAWN_IN_SAVANNA_DEFAULT_VALUE = true;
	public static final int MAULER_SAVANNA_SPAWN_WEIGHT_DEFAULT_VALUE = 32;
	public static final int MAULER_SAVANNA_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE = 1;
	public static final int MAULER_SAVANNA_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE = 1;

	public static final boolean ENABLE_MOOBLOOM_DEFAULT_VALUE = true;
	public static final boolean ENABLE_MOOBLOOM_SPAWN_DEFAULT_VALUE = true;
	public static final int MOOBLOOM_SPAWN_WEIGHT_DEFAULT_VALUE = 4;
	public static final int MOOBLOOM_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE = 2;
	public static final int MOOBLOOM_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE = 4;

	public static final boolean ENABLE_PENGUIN_DEFAULT_VALUE = true;
	public static final boolean ENABLE_PENGUIN_SPAWN_DEFAULT_VALUE = true;
	public static final int PENGUIN_SPAWN_WEIGHT_DEFAULT_VALUE = 4;
	public static final int PENGUIN_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE = 4;
	public static final int PENGUIN_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE = 8;
	public static final float PENGUINS_GLIDE_STATUS_EFFECT_MODIFIER_DEFAULT_VALUE = 0.5F;
	public static final int PENGUIN_POTION_OF_GLIDING_DURATION_DEFAULT_VALUE = 180;

	public static final boolean ENABLE_RASCAL_DEFAULT_VALUE = true;
	public static final boolean ENABLE_RASCAL_SPAWN_DEFAULT_VALUE = true;
	public static final int RASCAL_SPAWN_WEIGHT_DEFAULT_VALUE = 4;
	public static final int RASCAL_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE = 1;
	public static final int RASCAL_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE = 1;
	public static final boolean RASCAL_GIVE_REWARD_IN_BUNDLE_DEFAULT_VALUE = true;

	public static final boolean ENABLE_TUFF_GOLEM_DEFAULT_VALUE = true;
	public static final boolean GENERATE_TUFF_GOLEM_IN_STRONGHOLD_DEFAULT_VALUE = true;

	public static final boolean ENABLE_WILDFIRE_DEFAULT_VALUE = true;
	public static final boolean GENERATE_CITADEL_STRUCTURE_DEFAULT_VALUE = true;
	public static final boolean ENABLE_ZOMBIE_HORSE_TRAP_DEFAULT_VALUE = true;

	public static final boolean ENABLE_ACACIA_BEEHIVE_DEFAULT_VALUE = true;
	public static final boolean ENABLE_BAMBOO_BEEHIVE_DEFAULT_VALUE = true;
	public static final boolean ENABLE_BIRCH_BEEHIVE_DEFAULT_VALUE = true;
	public static final boolean ENABLE_CHERRY_BEEHIVE_DEFAULT_VALUE = true;
	public static final boolean ENABLE_CRIMSON_BEEHIVE_DEFAULT_VALUE = true;
	public static final boolean ENABLE_DARK_OAK_BEEHIVE_DEFAULT_VALUE = true;
	public static final boolean ENABLE_JUNGLE_BEEHIVE_DEFAULT_VALUE = true;
	public static final boolean ENABLE_MANGROVE_BEEHIVE_DEFAULT_VALUE = true;
	public static final boolean ENABLE_SPRUCE_BEEHIVE_DEFAULT_VALUE = true;
	public static final boolean ENABLE_PALE_OAK_BEEHIVE_DEFAULT_VALUE = true;
	public static final boolean ENABLE_WARPED_BEEHIVE_DEFAULT_VALUE = true;


	public boolean enableBeekeeperVillagerProfession = ENABLE_BEEKEEPER_VILLAGER_PROFESSION_DEFAULT_VALUE;
	public boolean generateBeekeeperAreaStructureInVillages = GENERATE_BEEKEEPER_AREA_STRUCTURE_IN_VILLAGES_DEFAULT_VALUE;
	public int beekeeperAreaStructureWeight = BEEKEEPER_AREA_STRUCTURE_WEIGHT_DEFAULT_VALUE;

	public boolean enableBarnacle = ENABLE_BARNACLE_DEFAULT_VALUE;
	public boolean enableBarnacleSpawn = ENABLE_BARNACLE_SPAWN_DEFAULT_VALUE;
	public int barnacleSpawnWeight = BARNACLE_SPAWN_WEIGHT_DEFAULT_VALUE;
	public int barnacleSpawnMinGroupSize = BARNACLE_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE;
	public int barnacleSpawnMaxGroupSize = BARNACLE_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE;

	//? if <= 1.21.8 {
	/*public boolean enableCopperGolem = ENABLE_COPPER_GOLEM_DEFAULT_VALUE;
	public boolean generateCopperGolemWorkstationStructureInVillages = GENERATE_COPPER_GOLEM_WORKSTATION_STRUCTURE_IN_VILLAGES_DEFAULT_VALUE;
	public int copperGolemWorkstationStructureWeight = COPPER_GOLEM_WORKSTATION_STRUCTURE_WEIGHT_DEFAULT_VALUE;
	public boolean generateCopperGolemInAncientCity = GENERATE_COPPER_GOLEM_IN_ANCIENT_CITY_DEFAULT_VALUE;
	public int copperGolemAncientCityCenterWeight = COPPER_GOLEM_ANCIENT_CITY_CENTER_WEIGHT_DEFAULT_VALUE;
	public boolean enableCopperGolemsInTrialChambers = ENABLE_COPPER_GOLEMS_IN_TRIAL_CHAMBERS_DEFAULT_VALUE;
	public int copperGolemInTrialChambersWeight = COPPER_GOLEM_IN_TRIAL_CHAMBERS_WEIGHT_DEFAULT_VALUE;
	public boolean enableLightningRodOxidation = ENABLE_LIGHTNING_ROD_OXIDATION_DEFAULT_VALUE;
	*///?}

	public boolean enableCrab = ENABLE_CRAB_DEFAULT_VALUE;
	public boolean enableCrabSpawn = ENABLE_CRAB_SPAWN_DEFAULT_VALUE;
	public int crabSpawnWeight = CRAB_SPAWN_WEIGHT_DEFAULT_VALUE;
	public int crabSpawnMinGroupSize = CRAB_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE;
	public int crabSpawnMaxGroupSize = CRAB_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE;
	public int crabPotionOfReachingDuration = CRAB_POTION_OF_REACHING_DURATION_DEFAULT_VALUE;
	public int reachingStatusEffectModifier = REACHING_STATUS_EFFECT_MODIFIER_DEFAULT_VALUE;

	public boolean enableGlare = ENABLE_GLARE_DEFAULT_VALUE;
	public boolean enableGlareSpawn = ENABLE_GLARE_SPAWN_DEFAULT_VALUE;
	public boolean enableGlareGriefing = ENABLE_GLARE_GRIEFING_DEFAULT_VALUE;
	public int glareSpawnWeight = GLARE_SPAWN_WEIGHT_DEFAULT_VALUE;
	public int glareSpawnMinGroupSize = GLARE_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE;
	public int glareSpawnMaxGroupSize = GLARE_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE;

	public boolean enableIceologer = ENABLE_ICEOLOGER_DEFAULT_VALUE;
	public boolean enableIceologerSpawn = ENABLE_ICEOLOGER_SPAWN_DEFAULT_VALUE;
	public boolean enableIceologerInRaids = ENABLE_ICEOLOGER_IN_RAIDS_DEFAULT_VALUE;
	public boolean fleeAwayFromIronGolems = FLEE_AWAY_FROM_IRON_GOLEMS_DEFAULT_VALUE;
	public boolean generateIceologerCabinStructure = GENERATE_ICEOLOGER_CABIN_STRUCTURE_DEFAULT_VALUE;

	public boolean enableIllusioner = ENABLE_ILLUSIONER_DEFAULT_VALUE;
	public boolean enableIllusionerSpawn = ENABLE_ILLUSIONER_SPAWN_DEFAULT_VALUE;
	public boolean enableIllusionerInRaids = ENABLE_ILLUSIONER_IN_RAIDS_DEFAULT_VALUE;
	public boolean replaceVanillaIllusioner = REPLACE_VANILLA_ILLUSIONER_DEFAULT_VALUE;
	public boolean generateIllusionerShackStructure = GENERATE_ILLUSIONER_SHACK_STRUCTURE_DEFAULT_VALUE;
	public boolean generateIllusionerTrainingGroundsStructure = GENERATE_ILLUSIONER_TRAINING_GROUNDS_STRUCTURE_DEFAULT_VALUE;
	public int illusionerMaxIllusionsCount = ILLUSIONER_MAX_ILLUSIONS_COUNT_DEFAULT_VALUE;
	public int illusionerIllusionLifetimeTicks = ILLUSIONER_ILLUSION_LIFETIME_TICKS_DEFAULT_VALUE;
	public int illusionerInvisibilityTicks = ILLUSIONER_INVISIBILITY_TICKS_DEFAULT_VALUE;

	public boolean enableMauler = ENABLE_MAULER_DEFAULT_VALUE;
	public boolean enableMaulerSpawn = ENABLE_MAULER_SPAWN_DEFAULT_VALUE;
	public boolean enableMaulerSpawnInDesert = ENABLE_MAULER_SPAWN_IN_DESERT_DEFAULT_VALUE;
	public int maulerDesertSpawnWeight = MAULER_DESERT_SPAWN_WEIGHT_DEFAULT_VALUE;
	public int maulerDesertSpawnMinGroupSize = MAULER_DESERT_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE;
	public int maulerDesertSpawnMaxGroupSize = MAULER_DESERT_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE;
	public boolean enableMaulerSpawnInBadlands = ENABLE_MAULER_SPAWN_IN_BADLANDS_DEFAULT_VALUE;
	public int maulerBadlandsSpawnWeight = MAULER_BADLANDS_SPAWN_WEIGHT_DEFAULT_VALUE;
	public int maulerBadlandsSpawnMinGroupSize = MAULER_BADLANDS_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE;
	public int maulerBadlandsSpawnMaxGroupSize = MAULER_BADLANDS_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE;
	public boolean enableMaulerSpawnInSavanna = ENABLE_MAULER_SPAWN_IN_SAVANNA_DEFAULT_VALUE;
	public int maulerSavannaSpawnWeight = MAULER_SAVANNA_SPAWN_WEIGHT_DEFAULT_VALUE;
	public int maulerSavannaSpawnMinGroupSize = MAULER_SAVANNA_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE;
	public int maulerSavannaSpawnMaxGroupSize = MAULER_SAVANNA_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE;

	public boolean enableMoobloom = ENABLE_MOOBLOOM_DEFAULT_VALUE;
	public boolean enableMoobloomSpawn = ENABLE_MOOBLOOM_SPAWN_DEFAULT_VALUE;
	public int moobloomSpawnWeight = MOOBLOOM_SPAWN_WEIGHT_DEFAULT_VALUE;
	public int moobloomSpawnMinGroupSize = MOOBLOOM_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE;
	public int moobloomSpawnMaxGroupSize = MOOBLOOM_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE;

	public boolean enablePenguin = ENABLE_PENGUIN_DEFAULT_VALUE;
	public boolean enablePenguinSpawn = ENABLE_PENGUIN_SPAWN_DEFAULT_VALUE;
	public int penguinSpawnWeight = PENGUIN_SPAWN_WEIGHT_DEFAULT_VALUE;
	public int penguinSpawnMinGroupSize = PENGUIN_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE;
	public int penguinSpawnMaxGroupSize = PENGUIN_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE;
	public float penguinsGlideStatusEffectModifier = PENGUINS_GLIDE_STATUS_EFFECT_MODIFIER_DEFAULT_VALUE;
	public int penguinPotionOfGlidingDuration = PENGUIN_POTION_OF_GLIDING_DURATION_DEFAULT_VALUE;

	public boolean enableRascal = ENABLE_RASCAL_DEFAULT_VALUE;
	public boolean enableRascalSpawn = ENABLE_RASCAL_SPAWN_DEFAULT_VALUE;
	public int rascalSpawnWeight = RASCAL_SPAWN_WEIGHT_DEFAULT_VALUE;
	public int rascalSpawnMinGroupSize = RASCAL_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE;
	public int rascalSpawnMaxGroupSize = RASCAL_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE;
	public boolean rascalGiveRewardInBundle = RASCAL_GIVE_REWARD_IN_BUNDLE_DEFAULT_VALUE;

	public boolean enableTuffGolem = ENABLE_TUFF_GOLEM_DEFAULT_VALUE;
	public boolean generateTuffGolemInStronghold = GENERATE_TUFF_GOLEM_IN_STRONGHOLD_DEFAULT_VALUE;

	public boolean enableWildfire = ENABLE_WILDFIRE_DEFAULT_VALUE;
	public boolean generateCitadelStructure = GENERATE_CITADEL_STRUCTURE_DEFAULT_VALUE;
	public boolean enableZombieHorseTrap = ENABLE_ZOMBIE_HORSE_TRAP_DEFAULT_VALUE;

	public boolean enableAcaciaBeehive = ENABLE_ACACIA_BEEHIVE_DEFAULT_VALUE;
	public boolean enableBambooBeehive = ENABLE_BAMBOO_BEEHIVE_DEFAULT_VALUE;
	public boolean enableBirchBeehive = ENABLE_BIRCH_BEEHIVE_DEFAULT_VALUE;
	public boolean enableCherryBeehive = ENABLE_CHERRY_BEEHIVE_DEFAULT_VALUE;
	public boolean enableCrimsonBeehive = ENABLE_CRIMSON_BEEHIVE_DEFAULT_VALUE;
	public boolean enableDarkOakBeehive = ENABLE_DARK_OAK_BEEHIVE_DEFAULT_VALUE;
	public boolean enableJungleBeehive = ENABLE_JUNGLE_BEEHIVE_DEFAULT_VALUE;
	public boolean enableMangroveBeehive = ENABLE_MANGROVE_BEEHIVE_DEFAULT_VALUE;
	public boolean enableSpruceBeehive = ENABLE_SPRUCE_BEEHIVE_DEFAULT_VALUE;
	public boolean enablePaleOakBeehive = ENABLE_PALE_OAK_BEEHIVE_DEFAULT_VALUE;
	public boolean enableWarpedBeehive = ENABLE_WARPED_BEEHIVE_DEFAULT_VALUE;

	public static void load() {
		FriendsAndFoesConfigSerializer.load();
	}

	public static void save() {
		FriendsAndFoesConfigSerializer.save();
	}
}
