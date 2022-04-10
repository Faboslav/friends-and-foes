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
	public int maulerpawnWeight = 32;
	public int maulerSpawnMinGroupSize = 1;
	public int maulerSpawnMaxGroupSize = 1;
	public boolean enableMoobloomSpawn = true;
	public int moobloomSpawnWeight = 32;
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

	@Override
	public String getName() {
		return FriendsAndFoes.MOD_ID;
	}
}
