package com.faboslav.friendsandfoes.config;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.config.omegaconfig.api.Config;

public class FriendsAndFoesConfig implements Config
{
	public static boolean enableCopperGolem = true;
	public static boolean generateCopperGolemAreaStructure = true;
	public static boolean enableGlareSpawn = true;
	public static int glareSpawnWeight = 32;
	public static int glareSpawnMinGroupSize = 1;
	public static int glareSpawnMaxGroupSize = 1;
	public static boolean enableIceologerSpawn = true;
	public static int iceologerSpawnWeight = 32;
	public static int iceologerSpawnMinGroupSize = 1;
	public static int iceologerSpawnMaxGroupSize = 1;
	public static boolean enableMoobloomSpawn = true;
	public static int moobloomSpawnWeight = 32;
	public static int moobloomSpawnMinGroupSize = 2;
	public static int moobloomSpawnMaxGroupSize = 4;
	public static boolean enableBeekeeperVillagerProfession = true;
	public static boolean generateBeekeeperAreaStructure = true;
	public static boolean enableIllusionerSpawn = true;
	public static boolean enableIllusionerInRaids = true;
	public static boolean generateIllusionerShackStructure = true;
	public static boolean generateIceologerCabinStructure = true;

	@Override
	public String getName() {
		return FriendsAndFoes.MOD_ID;
	}
}
