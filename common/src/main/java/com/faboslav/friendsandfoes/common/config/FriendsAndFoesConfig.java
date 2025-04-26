package com.faboslav.friendsandfoes.common.config;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.*;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

import java.nio.file.Path;

public final class FriendsAndFoesConfig
{
	public static ConfigClassHandler<FriendsAndFoesConfig> HANDLER = ConfigClassHandler.createBuilder(FriendsAndFoesConfig.class)
		.id(FriendsAndFoes.makeID(FriendsAndFoes.MOD_ID))
		.serializer(config -> GsonConfigSerializerBuilder.create(config).setPath(Path.of("config", FriendsAndFoes.MOD_ID + ".json")).build())
		.build();

	private boolean isLoaded = false;
	private boolean isLoading = false;

	private static final String MOBS_CATEGORY = "mobs";
	private static final String BEEKEEPER_GROUP = "beekeeper";
	private static final String COPPER_GOLEM_GROUP = "copper_golem";
	private static final String CRAB_GROUP = "crab";
	private static final String GLARE_GROUP = "glare";
	private static final String ICEOLOGER_GROUP = "iceologer";
	private static final String ILLUSIONER_GROUP = "illusioner";
	private static final String MAULER_GROUP = "mauler";
	private static final String MOOBLOOM_GROUP = "moobloom";
	private static final String RASCAL_GROUP = "rascal";
	private static final String TUFF_GOLEM_GROUP = "tuff_golem";
	private static final String WILDFIRE_GROUP = "wildfire";
	private static final String ZOMBIE_HORSE_GROUP = "zombie_horse";

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = BEEKEEPER_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableBeekeeperVillagerProfession = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = BEEKEEPER_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean generateBeekeeperAreaStructureInVillages = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = BEEKEEPER_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int beekeeperAreaStructureWeight = 2;

	@SerialEntry()
	@CustomDescription("Copper Golem")
	@AutoGen(category = MOBS_CATEGORY, group = COPPER_GOLEM_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableCopperGolem = true;

	@SerialEntry()
	@CustomDescription("Copper Golem")
	@AutoGen(category = MOBS_CATEGORY, group = COPPER_GOLEM_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean generateCopperGolemWorkstationStructureInVillages = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = COPPER_GOLEM_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int copperGolemWorkstationStructureWeight = 1;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = COPPER_GOLEM_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean generateCopperGolemInAncientCity = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = COPPER_GOLEM_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int copperGolemAncientCityCenterWeight = 10;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = COPPER_GOLEM_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableCopperGolemsInTrialChambers = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = COPPER_GOLEM_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int copperGolemInTrialChambersWeight = 4;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = COPPER_GOLEM_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableLightningRodOxidation = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = CRAB_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableCrab = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = CRAB_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableCrabSpawn = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = CRAB_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int crabSpawnWeight = 14;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = CRAB_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int crabSpawnMinGroupSize = 2;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = CRAB_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int crabSpawnMaxGroupSize = 4;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = CRAB_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int reachingStatusEffectModifier = 1;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = GLARE_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableGlare = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = GLARE_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableGlareSpawn = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = GLARE_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableGlareGriefing = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = GLARE_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int glareSpawnWeight = 4;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = GLARE_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int glareSpawnMinGroupSize = 1;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = GLARE_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int glareSpawnMaxGroupSize = 1;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = ICEOLOGER_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableIceologer = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = ICEOLOGER_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableIceologerSpawn = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = ICEOLOGER_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableIceologerInRaids = true;

	@SerialEntry()
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	@AutoGen(category = MOBS_CATEGORY, group = ICEOLOGER_GROUP)
	public boolean generateIceologerCabinStructure = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = ILLUSIONER_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableIllusioner = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = ILLUSIONER_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableIllusionerSpawn = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = ILLUSIONER_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableIllusionerInRaids = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = ILLUSIONER_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean generateIllusionerShackStructure = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = ILLUSIONER_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean generateIllusionerTrainingGroundsStructure = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = ILLUSIONER_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean replaceVanillaIllusioner = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = ILLUSIONER_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int illusionerMaxIllusionsCount = 9;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = ILLUSIONER_GROUP)
	@IntSlider(min = 0, max = 1800, step = 1)
	public int illusionerIllusionLifetimeTicks = 600;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = ILLUSIONER_GROUP)
	@IntSlider(min = 0, max = 180, step = 1)
	public int illusionerInvisibilityTicks = 60;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MAULER_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableMauler = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MAULER_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableMaulerSpawn = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MAULER_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableMaulerSpawnInDesert = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MAULER_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int maulerDesertSpawnWeight = 8;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MAULER_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int maulerDesertSpawnMinGroupSize = 1;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MAULER_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int maulerDesertSpawnMaxGroupSize = 1;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MAULER_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableMaulerSpawnInBadlands = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MAULER_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int maulerBadlandsSpawnWeight = 16;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MAULER_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int maulerBadlandsSpawnMinGroupSize = 1;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MAULER_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int maulerBadlandsSpawnMaxGroupSize = 1;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MAULER_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableMaulerSpawnInSavanna = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MAULER_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int maulerSavannaSpawnWeight = 32;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MAULER_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int maulerSavannaSpawnMinGroupSize = 1;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MAULER_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int maulerSavannaSpawnMaxGroupSize = 1;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MOOBLOOM_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableMoobloom = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MOOBLOOM_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableMoobloomSpawn = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MOOBLOOM_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int moobloomSpawnWeight = 4;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MOOBLOOM_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int moobloomSpawnMinGroupSize = 2;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = MOOBLOOM_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int moobloomSpawnMaxGroupSize = 4;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = RASCAL_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableRascal = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = RASCAL_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableRascalSpawn = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = RASCAL_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int rascalSpawnWeight = 4;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = RASCAL_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int rascalSpawnMinGroupSize = 1;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = RASCAL_GROUP)
	@IntSlider(min = 0, max = 100, step = 1)
	public int rascalSpawnMaxGroupSize = 1;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = TUFF_GOLEM_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableTuffGolem = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = TUFF_GOLEM_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean generateTuffGolemInStronghold = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = WILDFIRE_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableWildfire = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = WILDFIRE_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean generateCitadelStructure = true;

	@SerialEntry()
	@AutoGen(category = MOBS_CATEGORY, group = ZOMBIE_HORSE_GROUP)
	@Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
	public boolean enableZombieHorseTrap = true;

	public void load() {
		HANDLER.load();
	}
}