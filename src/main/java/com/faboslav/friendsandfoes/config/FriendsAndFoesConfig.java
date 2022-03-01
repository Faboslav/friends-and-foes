package com.faboslav.friendsandfoes.config;

import draylar.omegaconfig.api.Config;

public class FriendsAndFoesConfig implements Config
{
	public boolean enableCopperGolem = true;
	public boolean generateCopperGolemAreaStructure = true;
	public boolean enableGlareSpawn = true;
	public int glareSpawnWeight = 32;
	public int glareSpawnMinGroupSize = 1;
	public int glareSpawnMaxGroupSize = 1;
	public boolean enableMoobloomSpawn = true;
	public int moobloomSpawnWeight = 32;
	public int moobloomSpawnMinGroupSize = 2;
	public int moobloomSpawnMaxGroupSize = 4;
	public boolean enableBeekeeperVillagerProfession = true;
	public boolean generateBeekeeperAreaStructure = true;
	public boolean enableIllusionerInRaids = true;
	public boolean generateIllusionerShackStructure = true;

	@Override
	public String getName() {
		return Settings.MOD_ID;
	}
}
