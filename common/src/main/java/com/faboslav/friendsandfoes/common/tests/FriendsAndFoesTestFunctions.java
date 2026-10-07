package com.faboslav.friendsandfoes.common.tests;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.tests.block.*;
import com.faboslav.friendsandfoes.common.tests.mob.*;
import net.minecraft.gametest.framework.GameTestHelper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

//? if >= 1.21.5 {
import net.minecraft.resources.Identifier;
import java.util.function.BiConsumer;
//?} else {
/*import net.minecraft.gametest.framework.TestFunction;
import java.util.Collection;
*///?}

public final class FriendsAndFoesTestFunctions
{
	private static final Map<String, Consumer<GameTestHelper>> TESTS = new LinkedHashMap<>();

	static {
		//? if <= 1.21.8 {
		/*TESTS.put("build_copper_golem", BuildCopperGolemTest::buildCopperGolem);
		*///?}
		TESTS.put("build_tuff_golem", BuildTuffGolemTest::buildTuffGolem);
		TESTS.put("hatch_crab_egg", HatchCrabEggTest::hatchCrabEgg);
		TESTS.put("hatch_penguin_egg", HatchPenguinEggTest::hatchPenguinEgg);
		TESTS.put("oxidize_copper_button", OxidizeCopperButtonTest::oxidizeCopperButton);
		//? if <= 1.21.8 {
		/*TESTS.put("oxidize_lightning_rod", OxidizeLightningRodTest::oxidizeLightningRod);
		*///?}
		TESTS.put("place_acacia_beehive", PlaceAcaciaBeehiveTest::placeAcaciaBeehive);
		TESTS.put("place_bamboo_beehive", PlaceBambooBeehiveTest::placeBambooBeehive);
		TESTS.put("place_birch_beehive", PlaceBirchBeehiveTest::placeBirchBeehive);
		TESTS.put("place_buttercup", PlaceButtercupTest::placeButtercup);
		TESTS.put("place_cherry_beehive", PlaceCherryBeehiveTest::placeCherryBeehive);
		TESTS.put("place_crimson_beehive", PlaceCrimsonBeehiveTest::placeCrimsonBeehive);
		TESTS.put("place_dark_oak_beehive", PlaceDarkOakBeehiveTest::placeDarkOakBeehive);
		TESTS.put("place_jungle_beehive", PlaceJungleBeehiveTest::placeJungleBeehive);
		TESTS.put("place_mangrove_beehive", PlaceMangroveBeehiveTest::placeMangroveBeehive);
		//? if >= 1.21.4 {
		TESTS.put("place_pale_oak_beehive", PlacePaleOakBeehiveTest::placePaleOakBeehive);
		//?}
		//? if >= 26.3 {
		TESTS.put("place_poplar_beehive", PlacePoplarBeehiveTest::placePoplarBeehive);
		//?}
		TESTS.put("place_potted_buttercup", PlacePottedButtercupTest::placePottedButtercup);
		TESTS.put("place_spruce_beehive", PlaceSpruceBeehiveTest::placeSpruceBeehive);
		TESTS.put("place_warped_beehive", PlaceWarpedBeehiveTest::placeWarpedBeehive);
		TESTS.put("scrape_copper_button", ScrapeCopperButtonTest::scrapeCopperButton);
		//? if <= 1.21.8 {
		/*TESTS.put("scrape_lightning_rod", ScrapeLightningRodTest::scrapeLightningRod);
		*///?}
		TESTS.put("wax_copper_button", WaxCopperButtonTest::waxCopperButton);
		//? if <= 1.21.8 {
		/*TESTS.put("wax_lightning_rod", WaxLightningRodTest::waxLightningRod);
		*///?}
		TESTS.put("spawn_barnacle", SpawnBarnacleTest::spawnBarnacle);
		//? if <= 1.21.8 {
		/*TESTS.put("spawn_copper_golem", SpawnCopperGolemTest::spawnCopperGolem);
		*///?}
		TESTS.put("spawn_crab", SpawnCrabTest::spawnCrab);
		TESTS.put("spawn_glare", SpawnGlareTest::spawnGlare);
		TESTS.put("spawn_iceologer", SpawnIceologerTest::spawnIceologer);
		TESTS.put("spawn_illusioner", SpawnIllusionerTest::spawnIllusioner);
		TESTS.put("spawn_mauler", SpawnMaulerTest::spawnMauler);
		TESTS.put("spawn_moobloom", SpawnMoobloomTest::spawnMoobloom);
		TESTS.put("spawn_penguin", SpawnPenguinTest::spawnPenguin);
		//? if <= 1.21.8 {
		/*TESTS.put("spawn_player_illusion", SpawnPlayerIllusionTest::spawnPlayerIllusion);
		*///?}
		TESTS.put("spawn_rascal", SpawnRascalTest::spawnRascal);
		TESTS.put("spawn_tuff_golem", SpawnTuffGolemTest::spawnTuffGolem);
		TESTS.put("spawn_wildfire", SpawnWildfireTest::spawnWildfire);
	}

	//? if >= 1.21.5 {
	public static void register(BiConsumer<Identifier, Consumer<GameTestHelper>> registrar) {
		TESTS.forEach((id, test) -> registrar.accept(FriendsAndFoes.makeID(id), test));
	}
	//?} else {
	/*public static Collection<TestFunction> createTestFunctions() {
		return TESTS.entrySet().stream()
			.map(test -> new TestFunction("defaultBatch", FriendsAndFoes.MOD_ID + "." + test.getKey(), FriendsAndFoes.makeStringID("gametest/empty"), 100, 0L, true, test.getValue()))
			.toList();
	}
	*///?}
}
