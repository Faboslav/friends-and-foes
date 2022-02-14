package com.faboslav.friendsandfoes.config;

import draylar.omegaconfig.api.Config;

public class FriendsAndFoesConfig implements Config
{
	public boolean enableCopperGolem = true;
	public boolean generateCopperGolemAreaStructure = true;
	public boolean enableGlareSpawn = true;
	public boolean enableMoobloomSpawn = true;
	public boolean enableBeekeeperVillagerProfession = true;
	public boolean generateBeekeeperAreaStructure = true;
	public boolean enableIllusionerInRaids = true;
	public boolean generateIllusionerShackStructure = true;

	@Override
	public String getName() {
		return Settings.MOD_ID;
	}
}
