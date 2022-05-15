package com.faboslav.friendsandfoes.config;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.config.omegaconfig.api.Config;

public final class FriendsAndFoesConfig implements Config
{
	public boolean enableCopperGolem = true;
	public boolean generateCopperGolemAreaStructure = true;
	public boolean enableGlareSpawn = true;
	public int glareSpawnWeight = 32;
	public int glareSpawnMinGroupSize = 1;
	public int glareSpawnMaxGroupSize = 1;
	public boolean enableMaulerSpawn = true;
	public int maulerDesertSpawnWeight = 16;
	public int maulerDesertSpawnMinGroupSize = 1;
	public int maulerDesertSpawnMaxGroupSize = 1;
	public int maulerBadlandsSpawnWeight = 16;
	public int maulerBadlandsSpawnMinGroupSize = 1;
	public int maulerBadlandsSpawnMaxGroupSize = 1;
	public int maulerSwampSpawnWeight = 16;
	public int maulerSwampSpawnMinGroupSize = 1;
	public int maulerSwampSpawnMaxGroupSize = 1;
	public boolean enableMoobloomSpawn = true;
	public int moobloomSpawnWeight = 16;
	public int moobloomSpawnMinGroupSize = 2;
	public int moobloomSpawnMaxGroupSize = 4;
	public boolean enableBeekeeperVillagerProfession = true;
	public boolean generateBeekeeperAreaStructure = true;
	public boolean enableIllusionerSpawn = true;
	public boolean enableIllusionerInRaids = true;
	public boolean generateIllusionerShackStructure = true;
	public boolean enableIceologerSpawn = true;
	public boolean enableIceologerInRaids = true;
	public boolean generateIceologerCabinStructure = true;

	public int configVersion = 1;

	@Override
	public String getName() {
		return FriendsAndFoes.MOD_ID;
	}

	@Override
	public void save() {
		if (configVersion == 1) {
			this.moobloomSpawnWeight = 16;
			this.configVersion = 2;
		}

		Config.super.save();
	}
}
