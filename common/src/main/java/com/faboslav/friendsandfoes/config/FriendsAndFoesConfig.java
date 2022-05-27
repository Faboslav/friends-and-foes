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
	public int maulerDesertSpawnWeight = 32;
	public int maulerDesertSpawnMinGroupSize = 1;
	public int maulerDesertSpawnMaxGroupSize = 1;
	public int maulerBadlandsSpawnWeight = 32;
	public int maulerBadlandsSpawnMinGroupSize = 1;
	public int maulerBadlandsSpawnMaxGroupSize = 1;
	public int maulerSavannaSpawnWeight = 32;
	public int maulerSavannaSpawnMinGroupSize = 1;
	public int maulerSavannaSpawnMaxGroupSize = 1;
	public boolean enableMoobloomSpawn = true;
	public int moobloomFlowerForestSpawnWeight = 32;
	public int moobloomFlowerForestSpawnMinGroupSize = 2;
	public int moobloomFlowerForestSpawnMaxGroupSize = 4;
	public int moobloomMeadowSpawnWeight = 2;
	public int moobloomMeadowSpawnMinGroupSize = 2;
	public int moobloomMeadowSpawnMaxGroupSize = 4;
	public boolean enableBeekeeperVillagerProfession = true;
	public boolean generateBeekeeperAreaStructure = true;
	public boolean enableIllusionerSpawn = true;
	public boolean enableIllusionerInRaids = true;
	public boolean generateIllusionerShackStructure = true;
	public boolean enableIceologerSpawn = true;
	public boolean enableIceologerInRaids = true;
	public boolean generateIceologerCabinStructure = true;

	@Override
	public String getName() {
		return FriendsAndFoes.MOD_ID;
	}
}
