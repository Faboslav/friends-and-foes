package com.faboslav.friendsandfoes.common.config.client.gui;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import net.minecraft.client.gui.screens.Screen;

//? yacl {
import com.faboslav.friendsandfoes.common.config.FriendsAndFoesConfig;
/*import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import net.minecraft.network.chat.Component;
import java.util.function.Consumer;
import java.util.function.Supplier;
*///?}

public final class FriendsAndFoesConfigScreen
{
	public Screen generateScreen(Screen parent) {
		//? yacl {
		var config = FriendsAndFoes.getConfig();

		/*return YetAnotherConfigLib.createBuilder()
			.title(Component.translatable("yacl3.config.friendsandfoes:friendsandfoes"))
			.category(ConfigCategory.createBuilder()
				.name(Component.translatable("yacl3.config.friendsandfoes:friendsandfoes.category.mobs"))
				.group(group("mobs", "beekeeper")
					.option(bool("enableBeekeeperVillagerProfession", config.enableBeekeeperVillagerProfession, () -> config.enableBeekeeperVillagerProfession, value -> config.enableBeekeeperVillagerProfession = value))
					.option(bool("generateBeekeeperAreaStructureInVillages", config.generateBeekeeperAreaStructureInVillages, () -> config.generateBeekeeperAreaStructureInVillages, value -> config.generateBeekeeperAreaStructureInVillages = value))
					.option(slider("beekeeperAreaStructureWeight", config.beekeeperAreaStructureWeight, () -> config.beekeeperAreaStructureWeight, value -> config.beekeeperAreaStructureWeight = value, 0, 100))
					.build())
				//? if <= 1.21.8 {
				/^.group(group("mobs", "copper_golem")
					.option(bool("enableCopperGolem", config.enableCopperGolem, () -> config.enableCopperGolem, value -> config.enableCopperGolem = value))
					.option(bool("generateCopperGolemWorkstationStructureInVillages", config.generateCopperGolemWorkstationStructureInVillages, () -> config.generateCopperGolemWorkstationStructureInVillages, value -> config.generateCopperGolemWorkstationStructureInVillages = value))
					.option(slider("copperGolemWorkstationStructureWeight", config.copperGolemWorkstationStructureWeight, () -> config.copperGolemWorkstationStructureWeight, value -> config.copperGolemWorkstationStructureWeight = value, 0, 100))
					.option(bool("generateCopperGolemInAncientCity", config.generateCopperGolemInAncientCity, () -> config.generateCopperGolemInAncientCity, value -> config.generateCopperGolemInAncientCity = value))
					.option(slider("copperGolemAncientCityCenterWeight", config.copperGolemAncientCityCenterWeight, () -> config.copperGolemAncientCityCenterWeight, value -> config.copperGolemAncientCityCenterWeight = value, 0, 100))
					.option(bool("enableCopperGolemsInTrialChambers", config.enableCopperGolemsInTrialChambers, () -> config.enableCopperGolemsInTrialChambers, value -> config.enableCopperGolemsInTrialChambers = value))
					.option(slider("copperGolemInTrialChambersWeight", config.copperGolemInTrialChambersWeight, () -> config.copperGolemInTrialChambersWeight, value -> config.copperGolemInTrialChambersWeight = value, 0, 100))
					.option(bool("enableLightningRodOxidation", config.enableLightningRodOxidation, () -> config.enableLightningRodOxidation, value -> config.enableLightningRodOxidation = value))
					.build())
				^///?}
				.group(group("mobs", "crab")
					.option(bool("enableCrab", config.enableCrab, () -> config.enableCrab, value -> config.enableCrab = value))
					.option(bool("enableCrabSpawn", config.enableCrabSpawn, () -> config.enableCrabSpawn, value -> config.enableCrabSpawn = value))
					.option(slider("crabSpawnWeight", config.crabSpawnWeight, () -> config.crabSpawnWeight, value -> config.crabSpawnWeight = value, 0, 100))
					.option(slider("crabSpawnMinGroupSize", config.crabSpawnMinGroupSize, () -> config.crabSpawnMinGroupSize, value -> config.crabSpawnMinGroupSize = value, 0, 100))
					.option(slider("crabSpawnMaxGroupSize", config.crabSpawnMaxGroupSize, () -> config.crabSpawnMaxGroupSize, value -> config.crabSpawnMaxGroupSize = value, 0, 100))
					.option(field("crabPotionOfReachingDuration", config.crabPotionOfReachingDuration, () -> config.crabPotionOfReachingDuration, value -> config.crabPotionOfReachingDuration = value, 1, 100000))
					.option(slider("reachingStatusEffectModifier", config.reachingStatusEffectModifier, () -> config.reachingStatusEffectModifier, value -> config.reachingStatusEffectModifier = value, 0, 100))
					.build())
				.group(group("mobs", "glare")
					.option(bool("enableGlare", config.enableGlare, () -> config.enableGlare, value -> config.enableGlare = value))
					.option(bool("enableGlareSpawn", config.enableGlareSpawn, () -> config.enableGlareSpawn, value -> config.enableGlareSpawn = value))
					.option(bool("enableGlareGriefing", config.enableGlareGriefing, () -> config.enableGlareGriefing, value -> config.enableGlareGriefing = value))
					.option(slider("glareSpawnWeight", config.glareSpawnWeight, () -> config.glareSpawnWeight, value -> config.glareSpawnWeight = value, 0, 100))
					.option(slider("glareSpawnMinGroupSize", config.glareSpawnMinGroupSize, () -> config.glareSpawnMinGroupSize, value -> config.glareSpawnMinGroupSize = value, 0, 100))
					.option(slider("glareSpawnMaxGroupSize", config.glareSpawnMaxGroupSize, () -> config.glareSpawnMaxGroupSize, value -> config.glareSpawnMaxGroupSize = value, 0, 100))
					.build())
				.group(group("mobs", "iceologer")
					.option(bool("enableIceologer", config.enableIceologer, () -> config.enableIceologer, value -> config.enableIceologer = value))
					.option(bool("enableIceologerSpawn", config.enableIceologerSpawn, () -> config.enableIceologerSpawn, value -> config.enableIceologerSpawn = value))
					.option(bool("enableIceologerInRaids", config.enableIceologerInRaids, () -> config.enableIceologerInRaids, value -> config.enableIceologerInRaids = value))
					.option(bool("fleeAwayFromIronGolems", config.fleeAwayFromIronGolems, () -> config.fleeAwayFromIronGolems, value -> config.fleeAwayFromIronGolems = value))
					.option(bool("generateIceologerCabinStructure", config.generateIceologerCabinStructure, () -> config.generateIceologerCabinStructure, value -> config.generateIceologerCabinStructure = value))
					.build())
				.group(group("mobs", "illusioner")
					.option(bool("enableIllusioner", config.enableIllusioner, () -> config.enableIllusioner, value -> config.enableIllusioner = value))
					.option(bool("enableIllusionerSpawn", config.enableIllusionerSpawn, () -> config.enableIllusionerSpawn, value -> config.enableIllusionerSpawn = value))
					.option(bool("enableIllusionerInRaids", config.enableIllusionerInRaids, () -> config.enableIllusionerInRaids, value -> config.enableIllusionerInRaids = value))
					.option(bool("replaceVanillaIllusioner", config.replaceVanillaIllusioner, () -> config.replaceVanillaIllusioner, value -> config.replaceVanillaIllusioner = value))
					.option(bool("generateIllusionerShackStructure", config.generateIllusionerShackStructure, () -> config.generateIllusionerShackStructure, value -> config.generateIllusionerShackStructure = value))
					.option(bool("generateIllusionerTrainingGroundsStructure", config.generateIllusionerTrainingGroundsStructure, () -> config.generateIllusionerTrainingGroundsStructure, value -> config.generateIllusionerTrainingGroundsStructure = value))
					.option(slider("illusionerMaxIllusionsCount", config.illusionerMaxIllusionsCount, () -> config.illusionerMaxIllusionsCount, value -> config.illusionerMaxIllusionsCount = value, 0, 100))
					.option(slider("illusionerIllusionLifetimeTicks", config.illusionerIllusionLifetimeTicks, () -> config.illusionerIllusionLifetimeTicks, value -> config.illusionerIllusionLifetimeTicks = value, 0, 1800))
					.option(slider("illusionerInvisibilityTicks", config.illusionerInvisibilityTicks, () -> config.illusionerInvisibilityTicks, value -> config.illusionerInvisibilityTicks = value, 0, 180))
					.build())
				.group(group("mobs", "mauler")
					.option(bool("enableMauler", config.enableMauler, () -> config.enableMauler, value -> config.enableMauler = value))
					.option(bool("enableMaulerSpawn", config.enableMaulerSpawn, () -> config.enableMaulerSpawn, value -> config.enableMaulerSpawn = value))
					.option(bool("enableMaulerSpawnInDesert", config.enableMaulerSpawnInDesert, () -> config.enableMaulerSpawnInDesert, value -> config.enableMaulerSpawnInDesert = value))
					.option(slider("maulerDesertSpawnWeight", config.maulerDesertSpawnWeight, () -> config.maulerDesertSpawnWeight, value -> config.maulerDesertSpawnWeight = value, 0, 100))
					.option(slider("maulerDesertSpawnMinGroupSize", config.maulerDesertSpawnMinGroupSize, () -> config.maulerDesertSpawnMinGroupSize, value -> config.maulerDesertSpawnMinGroupSize = value, 0, 100))
					.option(slider("maulerDesertSpawnMaxGroupSize", config.maulerDesertSpawnMaxGroupSize, () -> config.maulerDesertSpawnMaxGroupSize, value -> config.maulerDesertSpawnMaxGroupSize = value, 0, 100))
					.option(bool("enableMaulerSpawnInBadlands", config.enableMaulerSpawnInBadlands, () -> config.enableMaulerSpawnInBadlands, value -> config.enableMaulerSpawnInBadlands = value))
					.option(slider("maulerBadlandsSpawnWeight", config.maulerBadlandsSpawnWeight, () -> config.maulerBadlandsSpawnWeight, value -> config.maulerBadlandsSpawnWeight = value, 0, 100))
					.option(slider("maulerBadlandsSpawnMinGroupSize", config.maulerBadlandsSpawnMinGroupSize, () -> config.maulerBadlandsSpawnMinGroupSize, value -> config.maulerBadlandsSpawnMinGroupSize = value, 0, 100))
					.option(slider("maulerBadlandsSpawnMaxGroupSize", config.maulerBadlandsSpawnMaxGroupSize, () -> config.maulerBadlandsSpawnMaxGroupSize, value -> config.maulerBadlandsSpawnMaxGroupSize = value, 0, 100))
					.option(bool("enableMaulerSpawnInSavanna", config.enableMaulerSpawnInSavanna, () -> config.enableMaulerSpawnInSavanna, value -> config.enableMaulerSpawnInSavanna = value))
					.option(slider("maulerSavannaSpawnWeight", config.maulerSavannaSpawnWeight, () -> config.maulerSavannaSpawnWeight, value -> config.maulerSavannaSpawnWeight = value, 0, 100))
					.option(slider("maulerSavannaSpawnMinGroupSize", config.maulerSavannaSpawnMinGroupSize, () -> config.maulerSavannaSpawnMinGroupSize, value -> config.maulerSavannaSpawnMinGroupSize = value, 0, 100))
					.option(slider("maulerSavannaSpawnMaxGroupSize", config.maulerSavannaSpawnMaxGroupSize, () -> config.maulerSavannaSpawnMaxGroupSize, value -> config.maulerSavannaSpawnMaxGroupSize = value, 0, 100))
					.build())
				.group(group("mobs", "moobloom")
					.option(bool("enableMoobloom", config.enableMoobloom, () -> config.enableMoobloom, value -> config.enableMoobloom = value))
					.option(bool("enableMoobloomSpawn", config.enableMoobloomSpawn, () -> config.enableMoobloomSpawn, value -> config.enableMoobloomSpawn = value))
					.option(slider("moobloomSpawnWeight", config.moobloomSpawnWeight, () -> config.moobloomSpawnWeight, value -> config.moobloomSpawnWeight = value, 0, 100))
					.option(slider("moobloomSpawnMinGroupSize", config.moobloomSpawnMinGroupSize, () -> config.moobloomSpawnMinGroupSize, value -> config.moobloomSpawnMinGroupSize = value, 0, 100))
					.option(slider("moobloomSpawnMaxGroupSize", config.moobloomSpawnMaxGroupSize, () -> config.moobloomSpawnMaxGroupSize, value -> config.moobloomSpawnMaxGroupSize = value, 0, 100))
					.build())
				.group(group("mobs", "rascal")
					.option(bool("enableRascal", config.enableRascal, () -> config.enableRascal, value -> config.enableRascal = value))
					.option(bool("enableRascalSpawn", config.enableRascalSpawn, () -> config.enableRascalSpawn, value -> config.enableRascalSpawn = value))
					.option(slider("rascalSpawnWeight", config.rascalSpawnWeight, () -> config.rascalSpawnWeight, value -> config.rascalSpawnWeight = value, 0, 100))
					.option(slider("rascalSpawnMinGroupSize", config.rascalSpawnMinGroupSize, () -> config.rascalSpawnMinGroupSize, value -> config.rascalSpawnMinGroupSize = value, 0, 100))
					.option(slider("rascalSpawnMaxGroupSize", config.rascalSpawnMaxGroupSize, () -> config.rascalSpawnMaxGroupSize, value -> config.rascalSpawnMaxGroupSize = value, 0, 100))
					.option(bool("rascalGiveRewardInBundle", config.rascalGiveRewardInBundle, () -> config.rascalGiveRewardInBundle, value -> config.rascalGiveRewardInBundle = value))
					.build())
				.group(group("mobs", "tuff_golem")
					.option(bool("enableTuffGolem", config.enableTuffGolem, () -> config.enableTuffGolem, value -> config.enableTuffGolem = value))
					.option(bool("generateTuffGolemInStronghold", config.generateTuffGolemInStronghold, () -> config.generateTuffGolemInStronghold, value -> config.generateTuffGolemInStronghold = value))
					.build())
				.group(group("mobs", "wildfire")
					.option(bool("enableWildfire", config.enableWildfire, () -> config.enableWildfire, value -> config.enableWildfire = value))
					.option(bool("generateCitadelStructure", config.generateCitadelStructure, () -> config.generateCitadelStructure, value -> config.generateCitadelStructure = value))
					.build())
				.group(group("mobs", "zombie_horse")
					.option(bool("enableZombieHorseTrap", config.enableZombieHorseTrap, () -> config.enableZombieHorseTrap, value -> config.enableZombieHorseTrap = value))
					.build())
				.build())
			.save(FriendsAndFoesConfig::save)
			.build()
			.generateScreen(parent);
		*///?} else {
		/*return null;
		*///?}
	}

	//? yacl {
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

	private static Option<Integer> field(String key, int initialValue, Supplier<Integer> getter, Consumer<Integer> setter, int min, int max) {
		return Option.<Integer>createBuilder()
			.name(Component.translatable("yacl3.config.friendsandfoes:friendsandfoes." + key))
			.binding(initialValue, getter, setter)
			.controller(option -> IntegerFieldControllerBuilder.create(option).range(min, max))
			.build();
	}
	//?}
}