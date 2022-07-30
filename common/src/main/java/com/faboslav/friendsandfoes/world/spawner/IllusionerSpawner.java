package com.faboslav.friendsandfoes.world.spawner;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.spawner.Spawner;

public final class IllusionerSpawner implements Spawner
{
	private int cooldown;

	public IllusionerSpawner() {
	}

	@Override
	public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
		if (
			spawnMonsters == false
			|| FriendsAndFoes.getConfig().enableIllusioner == false
			|| FriendsAndFoes.getConfig().enableIllusionerSpawn == false
		) {
			return 0;
		}

		Random random = world.getRandom();
		--this.cooldown;

		if (this.cooldown > 0) {
			return 0;
		}

		this.cooldown += 12000 + random.nextInt(1000);
		long l = world.getTimeOfDay() / 24000L;

		if (
			l < 5L
			|| world.isDay() == false
			|| RandomGenerator.generateInt(0, 1) != 0
		) {
			return 0;
		}

		int playerCount = world.getPlayers().size();
		if (playerCount == 0) {
			return 0;
		}

		PlayerEntity playerEntity = world.getPlayers().get(random.nextInt(playerCount));

		if (
			playerEntity.isSpectator()
			|| world.isNearOccupiedPointOfInterest(playerEntity.getBlockPos(), 2)
		) {
			return 0;
		}

		int j = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1:1);
		int k = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1:1);
		Mutable mutable = playerEntity.getBlockPos().mutableCopy().move(j, 0, k);

		var minX = mutable.getX() - 10;
		var minZ = mutable.getZ() - 10;
		var maxX = mutable.getX() + 10;
		var maxZ = mutable.getZ() + 10;
		if (world.isRegionLoaded(minX, minZ, maxX, maxZ) == false) {
			return 0;
		}

		if (world.getBiome(mutable).isIn(FriendsAndFoesTags.HAS_ILLUSIONER) == false) {
			return 0;
		}

		mutable.setY(world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, mutable).getY());
		BlockState blockState = world.getBlockState(mutable);

		if (
			SpawnHelper.isClearForSpawn(world, mutable, blockState, blockState.getFluidState(), EntityType.ILLUSIONER) == false
			|| PatrolEntity.canSpawn(EntityType.ILLUSIONER, world, SpawnReason.PATROL, mutable, random) == false
		) {
			return 0;
		}

		var illusioner = EntityType.ILLUSIONER.create(world);

		if (illusioner == null) {
			return 0;
		}

		illusioner.setPatrolLeader(false);
		illusioner.setRandomPatrolTarget();
		illusioner.setPosition(mutable.getX(), mutable.getY(), mutable.getZ());
		illusioner.initialize(world, world.getLocalDifficulty(mutable), SpawnReason.PATROL, null, null);
		world.spawnEntityAndPassengers(illusioner);
		return 1;
	}
}
