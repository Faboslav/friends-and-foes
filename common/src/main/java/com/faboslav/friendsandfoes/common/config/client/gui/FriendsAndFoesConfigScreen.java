package com.faboslav.friendsandfoes.common.config.client.gui;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import net.minecraft.client.gui.screens.Screen;

//? if yacl {
import com.faboslav.friendsandfoes.common.config.FriendsAndFoesConfig;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import net.minecraft.network.chat.Component;
import java.util.function.Consumer;
import java.util.function.Supplier;
//?}

public final class FriendsAndFoesConfigScreen
{
	public Screen generateScreen(Screen parent) {
		//? if yacl {
		var config = FriendsAndFoes.getConfig();

		return YetAnotherConfigLib.createBuilder()
			.title(Component.translatable("yacl3.config.friendsandfoes:friendsandfoes"))
			.category(ConfigCategory.createBuilder()
				.name(Component.translatable("yacl3.config.friendsandfoes:friendsandfoes.category.mobs"))
				.group(group("mobs", "beekeeper")
					.option(bool("enableBeekeeperVillagerProfession", FriendsAndFoesConfig.ENABLE_BEEKEEPER_VILLAGER_PROFESSION_DEFAULT_VALUE, () -> config.enableBeekeeperVillagerProfession, value -> config.enableBeekeeperVillagerProfession = value))
					.option(bool("generateBeekeeperAreaStructureInVillages", FriendsAndFoesConfig.GENERATE_BEEKEEPER_AREA_STRUCTURE_IN_VILLAGES_DEFAULT_VALUE, () -> config.generateBeekeeperAreaStructureInVillages, value -> config.generateBeekeeperAreaStructureInVillages = value))
					.option(slider("beekeeperAreaStructureWeight", FriendsAndFoesConfig.BEEKEEPER_AREA_STRUCTURE_WEIGHT_DEFAULT_VALUE, () -> config.beekeeperAreaStructureWeight, value -> config.beekeeperAreaStructureWeight = value, 0, 100))
					.build())
				//? if <= 1.21.8 {
				/*.group(group("mobs", "copper_golem")
					.option(bool("enableCopperGolem", FriendsAndFoesConfig.ENABLE_COPPER_GOLEM_DEFAULT_VALUE, () -> config.enableCopperGolem, value -> config.enableCopperGolem = value))
					.option(bool("generateCopperGolemWorkstationStructureInVillages", FriendsAndFoesConfig.GENERATE_COPPER_GOLEM_WORKSTATION_STRUCTURE_IN_VILLAGES_DEFAULT_VALUE, () -> config.generateCopperGolemWorkstationStructureInVillages, value -> config.generateCopperGolemWorkstationStructureInVillages = value))
					.option(slider("copperGolemWorkstationStructureWeight", FriendsAndFoesConfig.COPPER_GOLEM_WORKSTATION_STRUCTURE_WEIGHT_DEFAULT_VALUE, () -> config.copperGolemWorkstationStructureWeight, value -> config.copperGolemWorkstationStructureWeight = value, 0, 100))
					.option(bool("generateCopperGolemInAncientCity", FriendsAndFoesConfig.GENERATE_COPPER_GOLEM_IN_ANCIENT_CITY_DEFAULT_VALUE, () -> config.generateCopperGolemInAncientCity, value -> config.generateCopperGolemInAncientCity = value))
					.option(slider("copperGolemAncientCityCenterWeight", FriendsAndFoesConfig.COPPER_GOLEM_ANCIENT_CITY_CENTER_WEIGHT_DEFAULT_VALUE, () -> config.copperGolemAncientCityCenterWeight, value -> config.copperGolemAncientCityCenterWeight = value, 0, 100))
					.option(bool("enableCopperGolemsInTrialChambers", FriendsAndFoesConfig.ENABLE_COPPER_GOLEMS_IN_TRIAL_CHAMBERS_DEFAULT_VALUE, () -> config.enableCopperGolemsInTrialChambers, value -> config.enableCopperGolemsInTrialChambers = value))
					.option(slider("copperGolemInTrialChambersWeight", FriendsAndFoesConfig.COPPER_GOLEM_IN_TRIAL_CHAMBERS_WEIGHT_DEFAULT_VALUE, () -> config.copperGolemInTrialChambersWeight, value -> config.copperGolemInTrialChambersWeight = value, 0, 100))
					.option(bool("enableLightningRodOxidation", FriendsAndFoesConfig.ENABLE_LIGHTNING_ROD_OXIDATION_DEFAULT_VALUE, () -> config.enableLightningRodOxidation, value -> config.enableLightningRodOxidation = value))
					.build())
				*///?}
				.group(group("mobs", "barnacle")
					.option(bool("enableBarnacle", FriendsAndFoesConfig.ENABLE_BARNACLE_DEFAULT_VALUE, () -> config.enableBarnacle, value -> config.enableBarnacle = value))
					.option(bool("enableBarnacleSpawn", FriendsAndFoesConfig.ENABLE_BARNACLE_SPAWN_DEFAULT_VALUE, () -> config.enableBarnacleSpawn, value -> config.enableBarnacleSpawn = value))
					.option(slider("barnacleSpawnWeight", FriendsAndFoesConfig.BARNACLE_SPAWN_WEIGHT_DEFAULT_VALUE, () -> config.barnacleSpawnWeight, value -> config.barnacleSpawnWeight = value, 0, 100))
					.option(slider("barnacleSpawnMinGroupSize", FriendsAndFoesConfig.BARNACLE_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE, () -> config.barnacleSpawnMinGroupSize, value -> config.barnacleSpawnMinGroupSize = value, 0, 100))
					.option(slider("barnacleSpawnMaxGroupSize", FriendsAndFoesConfig.BARNACLE_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE, () -> config.barnacleSpawnMaxGroupSize, value -> config.barnacleSpawnMaxGroupSize = value, 0, 100))
					.build())
				.group(group("mobs", "crab")
					.option(bool("enableCrab", FriendsAndFoesConfig.ENABLE_CRAB_DEFAULT_VALUE, () -> config.enableCrab, value -> config.enableCrab = value))
					.option(bool("enableCrabSpawn", FriendsAndFoesConfig.ENABLE_CRAB_SPAWN_DEFAULT_VALUE, () -> config.enableCrabSpawn, value -> config.enableCrabSpawn = value))
					.option(slider("crabSpawnWeight", FriendsAndFoesConfig.CRAB_SPAWN_WEIGHT_DEFAULT_VALUE, () -> config.crabSpawnWeight, value -> config.crabSpawnWeight = value, 0, 100))
					.option(slider("crabSpawnMinGroupSize", FriendsAndFoesConfig.CRAB_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE, () -> config.crabSpawnMinGroupSize, value -> config.crabSpawnMinGroupSize = value, 0, 100))
					.option(slider("crabSpawnMaxGroupSize", FriendsAndFoesConfig.CRAB_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE, () -> config.crabSpawnMaxGroupSize, value -> config.crabSpawnMaxGroupSize = value, 0, 100))
					.option(field("crabPotionOfReachingDuration", FriendsAndFoesConfig.CRAB_POTION_OF_REACHING_DURATION_DEFAULT_VALUE, () -> config.crabPotionOfReachingDuration, value -> config.crabPotionOfReachingDuration = value, 1, 100000))
					.option(slider("reachingStatusEffectModifier", FriendsAndFoesConfig.REACHING_STATUS_EFFECT_MODIFIER_DEFAULT_VALUE, () -> config.reachingStatusEffectModifier, value -> config.reachingStatusEffectModifier = value, 0, 100))
					.build())
				.group(group("mobs", "glare")
					.option(bool("enableGlare", FriendsAndFoesConfig.ENABLE_GLARE_DEFAULT_VALUE, () -> config.enableGlare, value -> config.enableGlare = value))
					.option(bool("enableGlareSpawn", FriendsAndFoesConfig.ENABLE_GLARE_SPAWN_DEFAULT_VALUE, () -> config.enableGlareSpawn, value -> config.enableGlareSpawn = value))
					.option(bool("enableGlareGriefing", FriendsAndFoesConfig.ENABLE_GLARE_GRIEFING_DEFAULT_VALUE, () -> config.enableGlareGriefing, value -> config.enableGlareGriefing = value))
					.option(slider("glareSpawnWeight", FriendsAndFoesConfig.GLARE_SPAWN_WEIGHT_DEFAULT_VALUE, () -> config.glareSpawnWeight, value -> config.glareSpawnWeight = value, 0, 100))
					.option(slider("glareSpawnMinGroupSize", FriendsAndFoesConfig.GLARE_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE, () -> config.glareSpawnMinGroupSize, value -> config.glareSpawnMinGroupSize = value, 0, 100))
					.option(slider("glareSpawnMaxGroupSize", FriendsAndFoesConfig.GLARE_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE, () -> config.glareSpawnMaxGroupSize, value -> config.glareSpawnMaxGroupSize = value, 0, 100))
					.build())
				.group(group("mobs", "iceologer")
					.option(bool("enableIceologer", FriendsAndFoesConfig.ENABLE_ICEOLOGER_DEFAULT_VALUE, () -> config.enableIceologer, value -> config.enableIceologer = value))
					.option(bool("enableIceologerSpawn", FriendsAndFoesConfig.ENABLE_ICEOLOGER_SPAWN_DEFAULT_VALUE, () -> config.enableIceologerSpawn, value -> config.enableIceologerSpawn = value))
					.option(bool("enableIceologerInRaids", FriendsAndFoesConfig.ENABLE_ICEOLOGER_IN_RAIDS_DEFAULT_VALUE, () -> config.enableIceologerInRaids, value -> config.enableIceologerInRaids = value))
					.option(bool("fleeAwayFromIronGolems", FriendsAndFoesConfig.FLEE_AWAY_FROM_IRON_GOLEMS_DEFAULT_VALUE, () -> config.fleeAwayFromIronGolems, value -> config.fleeAwayFromIronGolems = value))
					.option(bool("generateIceologerCabinStructure", FriendsAndFoesConfig.GENERATE_ICEOLOGER_CABIN_STRUCTURE_DEFAULT_VALUE, () -> config.generateIceologerCabinStructure, value -> config.generateIceologerCabinStructure = value))
					.build())
				.group(group("mobs", "illusioner")
					.option(bool("enableIllusioner", FriendsAndFoesConfig.ENABLE_ILLUSIONER_DEFAULT_VALUE, () -> config.enableIllusioner, value -> config.enableIllusioner = value))
					.option(bool("enableIllusionerSpawn", FriendsAndFoesConfig.ENABLE_ILLUSIONER_SPAWN_DEFAULT_VALUE, () -> config.enableIllusionerSpawn, value -> config.enableIllusionerSpawn = value))
					.option(bool("enableIllusionerInRaids", FriendsAndFoesConfig.ENABLE_ILLUSIONER_IN_RAIDS_DEFAULT_VALUE, () -> config.enableIllusionerInRaids, value -> config.enableIllusionerInRaids = value))
					.option(bool("replaceVanillaIllusioner", FriendsAndFoesConfig.REPLACE_VANILLA_ILLUSIONER_DEFAULT_VALUE, () -> config.replaceVanillaIllusioner, value -> config.replaceVanillaIllusioner = value))
					.option(bool("generateIllusionerShackStructure", FriendsAndFoesConfig.GENERATE_ILLUSIONER_SHACK_STRUCTURE_DEFAULT_VALUE, () -> config.generateIllusionerShackStructure, value -> config.generateIllusionerShackStructure = value))
					.option(bool("generateIllusionerTrainingGroundsStructure", FriendsAndFoesConfig.GENERATE_ILLUSIONER_TRAINING_GROUNDS_STRUCTURE_DEFAULT_VALUE, () -> config.generateIllusionerTrainingGroundsStructure, value -> config.generateIllusionerTrainingGroundsStructure = value))
					.option(slider("illusionerMaxIllusionsCount", FriendsAndFoesConfig.ILLUSIONER_MAX_ILLUSIONS_COUNT_DEFAULT_VALUE, () -> config.illusionerMaxIllusionsCount, value -> config.illusionerMaxIllusionsCount = value, 0, 100))
					.option(slider("illusionerIllusionLifetimeTicks", FriendsAndFoesConfig.ILLUSIONER_ILLUSION_LIFETIME_TICKS_DEFAULT_VALUE, () -> config.illusionerIllusionLifetimeTicks, value -> config.illusionerIllusionLifetimeTicks = value, 0, 1800))
					.option(slider("illusionerInvisibilityTicks", FriendsAndFoesConfig.ILLUSIONER_INVISIBILITY_TICKS_DEFAULT_VALUE, () -> config.illusionerInvisibilityTicks, value -> config.illusionerInvisibilityTicks = value, 0, 180))
					.build())
				.group(group("mobs", "mauler")
					.option(bool("enableMauler", FriendsAndFoesConfig.ENABLE_MAULER_DEFAULT_VALUE, () -> config.enableMauler, value -> config.enableMauler = value))
					.option(bool("enableMaulerSpawn", FriendsAndFoesConfig.ENABLE_MAULER_SPAWN_DEFAULT_VALUE, () -> config.enableMaulerSpawn, value -> config.enableMaulerSpawn = value))
					.option(bool("enableMaulerSpawnInDesert", FriendsAndFoesConfig.ENABLE_MAULER_SPAWN_IN_DESERT_DEFAULT_VALUE, () -> config.enableMaulerSpawnInDesert, value -> config.enableMaulerSpawnInDesert = value))
					.option(slider("maulerDesertSpawnWeight", FriendsAndFoesConfig.MAULER_DESERT_SPAWN_WEIGHT_DEFAULT_VALUE, () -> config.maulerDesertSpawnWeight, value -> config.maulerDesertSpawnWeight = value, 0, 100))
					.option(slider("maulerDesertSpawnMinGroupSize", FriendsAndFoesConfig.MAULER_DESERT_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE, () -> config.maulerDesertSpawnMinGroupSize, value -> config.maulerDesertSpawnMinGroupSize = value, 0, 100))
					.option(slider("maulerDesertSpawnMaxGroupSize", FriendsAndFoesConfig.MAULER_DESERT_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE, () -> config.maulerDesertSpawnMaxGroupSize, value -> config.maulerDesertSpawnMaxGroupSize = value, 0, 100))
					.option(bool("enableMaulerSpawnInBadlands", FriendsAndFoesConfig.ENABLE_MAULER_SPAWN_IN_BADLANDS_DEFAULT_VALUE, () -> config.enableMaulerSpawnInBadlands, value -> config.enableMaulerSpawnInBadlands = value))
					.option(slider("maulerBadlandsSpawnWeight", FriendsAndFoesConfig.MAULER_BADLANDS_SPAWN_WEIGHT_DEFAULT_VALUE, () -> config.maulerBadlandsSpawnWeight, value -> config.maulerBadlandsSpawnWeight = value, 0, 100))
					.option(slider("maulerBadlandsSpawnMinGroupSize", FriendsAndFoesConfig.MAULER_BADLANDS_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE, () -> config.maulerBadlandsSpawnMinGroupSize, value -> config.maulerBadlandsSpawnMinGroupSize = value, 0, 100))
					.option(slider("maulerBadlandsSpawnMaxGroupSize", FriendsAndFoesConfig.MAULER_BADLANDS_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE, () -> config.maulerBadlandsSpawnMaxGroupSize, value -> config.maulerBadlandsSpawnMaxGroupSize = value, 0, 100))
					.option(bool("enableMaulerSpawnInSavanna", FriendsAndFoesConfig.ENABLE_MAULER_SPAWN_IN_SAVANNA_DEFAULT_VALUE, () -> config.enableMaulerSpawnInSavanna, value -> config.enableMaulerSpawnInSavanna = value))
					.option(slider("maulerSavannaSpawnWeight", FriendsAndFoesConfig.MAULER_SAVANNA_SPAWN_WEIGHT_DEFAULT_VALUE, () -> config.maulerSavannaSpawnWeight, value -> config.maulerSavannaSpawnWeight = value, 0, 100))
					.option(slider("maulerSavannaSpawnMinGroupSize", FriendsAndFoesConfig.MAULER_SAVANNA_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE, () -> config.maulerSavannaSpawnMinGroupSize, value -> config.maulerSavannaSpawnMinGroupSize = value, 0, 100))
					.option(slider("maulerSavannaSpawnMaxGroupSize", FriendsAndFoesConfig.MAULER_SAVANNA_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE, () -> config.maulerSavannaSpawnMaxGroupSize, value -> config.maulerSavannaSpawnMaxGroupSize = value, 0, 100))
					.build())
				.group(group("mobs", "moobloom")
					.option(bool("enableMoobloom", FriendsAndFoesConfig.ENABLE_MOOBLOOM_DEFAULT_VALUE, () -> config.enableMoobloom, value -> config.enableMoobloom = value))
					.option(bool("enableMoobloomSpawn", FriendsAndFoesConfig.ENABLE_MOOBLOOM_SPAWN_DEFAULT_VALUE, () -> config.enableMoobloomSpawn, value -> config.enableMoobloomSpawn = value))
					.option(slider("moobloomSpawnWeight", FriendsAndFoesConfig.MOOBLOOM_SPAWN_WEIGHT_DEFAULT_VALUE, () -> config.moobloomSpawnWeight, value -> config.moobloomSpawnWeight = value, 0, 100))
					.option(slider("moobloomSpawnMinGroupSize", FriendsAndFoesConfig.MOOBLOOM_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE, () -> config.moobloomSpawnMinGroupSize, value -> config.moobloomSpawnMinGroupSize = value, 0, 100))
					.option(slider("moobloomSpawnMaxGroupSize", FriendsAndFoesConfig.MOOBLOOM_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE, () -> config.moobloomSpawnMaxGroupSize, value -> config.moobloomSpawnMaxGroupSize = value, 0, 100))
					.build())
				.group(group("mobs", "penguin")
					.option(bool("enablePenguin", FriendsAndFoesConfig.ENABLE_PENGUIN_DEFAULT_VALUE, () -> config.enablePenguin, value -> config.enablePenguin = value))
					.option(bool("enablePenguinSpawn", FriendsAndFoesConfig.ENABLE_PENGUIN_SPAWN_DEFAULT_VALUE, () -> config.enablePenguinSpawn, value -> config.enablePenguinSpawn = value))
					.option(slider("penguinSpawnWeight", FriendsAndFoesConfig.PENGUIN_SPAWN_WEIGHT_DEFAULT_VALUE, () -> config.penguinSpawnWeight, value -> config.penguinSpawnWeight = value, 0, 100))
					.option(slider("penguinSpawnMinGroupSize", FriendsAndFoesConfig.PENGUIN_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE, () -> config.penguinSpawnMinGroupSize, value -> config.penguinSpawnMinGroupSize = value, 0, 100))
					.option(slider("penguinSpawnMaxGroupSize", FriendsAndFoesConfig.PENGUIN_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE, () -> config.penguinSpawnMaxGroupSize, value -> config.penguinSpawnMaxGroupSize = value, 0, 100))
					.option(field("penguinPotionOfGlidingDuration", FriendsAndFoesConfig.PENGUIN_POTION_OF_GLIDING_DURATION_DEFAULT_VALUE, () -> config.penguinPotionOfGlidingDuration, value -> config.penguinPotionOfGlidingDuration = value, 1, 100000))
					.option(slider("penguinsGlideStatusEffectModifier", FriendsAndFoesConfig.PENGUINS_GLIDE_STATUS_EFFECT_MODIFIER_DEFAULT_VALUE, () -> config.penguinsGlideStatusEffectModifier, value -> config.penguinsGlideStatusEffectModifier = value, 0, 100))
					.build())
				.group(group("mobs", "rascal")
					.option(bool("enableRascal", FriendsAndFoesConfig.ENABLE_RASCAL_DEFAULT_VALUE, () -> config.enableRascal, value -> config.enableRascal = value))
					.option(bool("enableRascalSpawn", FriendsAndFoesConfig.ENABLE_RASCAL_SPAWN_DEFAULT_VALUE, () -> config.enableRascalSpawn, value -> config.enableRascalSpawn = value))
					.option(slider("rascalSpawnWeight", FriendsAndFoesConfig.RASCAL_SPAWN_WEIGHT_DEFAULT_VALUE, () -> config.rascalSpawnWeight, value -> config.rascalSpawnWeight = value, 0, 100))
					.option(slider("rascalSpawnMinGroupSize", FriendsAndFoesConfig.RASCAL_SPAWN_MIN_GROUP_SIZE_DEFAULT_VALUE, () -> config.rascalSpawnMinGroupSize, value -> config.rascalSpawnMinGroupSize = value, 0, 100))
					.option(slider("rascalSpawnMaxGroupSize", FriendsAndFoesConfig.RASCAL_SPAWN_MAX_GROUP_SIZE_DEFAULT_VALUE, () -> config.rascalSpawnMaxGroupSize, value -> config.rascalSpawnMaxGroupSize = value, 0, 100))
					.option(bool("rascalGiveRewardInBundle", FriendsAndFoesConfig.RASCAL_GIVE_REWARD_IN_BUNDLE_DEFAULT_VALUE, () -> config.rascalGiveRewardInBundle, value -> config.rascalGiveRewardInBundle = value))
					.build())
				.group(group("mobs", "tuff_golem")
					.option(bool("enableTuffGolem", FriendsAndFoesConfig.ENABLE_TUFF_GOLEM_DEFAULT_VALUE, () -> config.enableTuffGolem, value -> config.enableTuffGolem = value))
					.option(bool("generateTuffGolemInStronghold", FriendsAndFoesConfig.GENERATE_TUFF_GOLEM_IN_STRONGHOLD_DEFAULT_VALUE, () -> config.generateTuffGolemInStronghold, value -> config.generateTuffGolemInStronghold = value))
					.build())
				.group(group("mobs", "wildfire")
					.option(bool("enableWildfire", FriendsAndFoesConfig.ENABLE_WILDFIRE_DEFAULT_VALUE, () -> config.enableWildfire, value -> config.enableWildfire = value))
					.option(bool("generateCitadelStructure", FriendsAndFoesConfig.GENERATE_CITADEL_STRUCTURE_DEFAULT_VALUE, () -> config.generateCitadelStructure, value -> config.generateCitadelStructure = value))
					.build())
				.group(group("mobs", "zombie_horse")
					.option(bool("enableZombieHorseTrap", FriendsAndFoesConfig.ENABLE_ZOMBIE_HORSE_TRAP_DEFAULT_VALUE, () -> config.enableZombieHorseTrap, value -> config.enableZombieHorseTrap = value))
					.build())
				.build())
			.save(FriendsAndFoesConfig::save)
			.build()
			.generateScreen(parent);
		//?} else {
		/*return null;
		*///?}
	}

	//? if yacl {
	private static OptionGroup.Builder group(String category, String group) {
		return OptionGroup.createBuilder()
			.name(Component.translatable("yacl3.config.friendsandfoes:friendsandfoes.category." + category + ".group." + group));
	}

	private static Option<Boolean> bool(String key, boolean initialValue, Supplier<Boolean> getter, Consumer<Boolean> setter) {
		return Option.<Boolean>createBuilder()
			.name(Component.translatable("yacl3.config.friendsandfoes:friendsandfoes." + key))
			.binding(initialValue, getter, setter)
			.controller(opt -> BooleanControllerBuilder.create(opt).formatValue(val -> val ? Component.literal("Yes") : Component.literal("No")).coloured(true))
			.build();
	}

	private static Option<Integer> slider(String key, int initialValue, Supplier<Integer> getter, Consumer<Integer> setter, int min, int max) {
		return Option.<Integer>createBuilder()
			.name(Component.translatable("yacl3.config.friendsandfoes:friendsandfoes." + key))
			.binding(initialValue, getter, setter)
			.controller(option -> IntegerSliderControllerBuilder.create(option).range(min, max).step(1))
			.build();
	}

	private static Option<Float> slider(String key, float initialValue, Supplier<Float> getter, Consumer<Float> setter, float min, float max) {
		return Option.<Float>createBuilder()
			.name(Component.translatable("yacl3.config.friendsandfoes:friendsandfoes." + key))
			.binding(initialValue, getter, setter)
			.controller(option -> FloatSliderControllerBuilder.create(option).range(min, max).step(0.1f))
			.build();
	}

	private static Option<Integer> field(String key, int initialValue, Supplier<Integer> getter, Consumer<Integer> setter, int min, int max) {
		return Option.<Integer>createBuilder()
			.name(Component.translatable("yacl3.config.friendsandfoes:friendsandfoes." + key))
			.binding(initialValue, getter, setter)
			.controller(option -> IntegerFieldControllerBuilder.create(option).range(min, max))
			.build();
	}
	//?}
}