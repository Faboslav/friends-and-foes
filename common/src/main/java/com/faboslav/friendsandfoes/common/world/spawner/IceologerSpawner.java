package com.faboslav.friendsandfoes.common.world.spawner;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.IceologerEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

public final class IceologerSpawner implements CustomSpawner
{
	private int cooldown;

	public IceologerSpawner() {
	}

	@Override
	public int tick(ServerLevel world, boolean spawnMonsters, boolean spawnAnimals) {
		if (
			!spawnMonsters
			|| !FriendsAndFoes.getConfig().enableIceologerSpawn
		) {
			return 0;
		}

		RandomSource random = world.random;
		--this.cooldown;

		if (this.cooldown > 0) {
			return 0;
		}

		this.cooldown += 12000 + random.nextInt(1000);
		long l = world.getDayTime() / 24000L;

		if (
			l < 5L
			|| !world.isDay()
			|| random.nextIntBetweenInclusive(0, 1) != 0
		) {
			return 0;
		}

		int playerCount = world.players().size();
		if (playerCount == 0) {
			return 0;
		}

		Player playerEntity = world.players().get(random.nextInt(playerCount));

		if (
			playerEntity.isSpectator()
			|| world.isCloseToVillage(playerEntity.blockPosition(), 2)
		) {
			return 0;
		}

		int j = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1:1);
		int k = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1:1);
		MutableBlockPos mutable = playerEntity.blockPosition().mutable().move(j, 0, k);

		var minX = mutable.getX() - 10;
		var minZ = mutable.getZ() - 10;
		var maxX = mutable.getX() + 10;
		var maxZ = mutable.getZ() + 10;

		if (!world.hasChunksAt(minX, minZ, maxX, maxZ)) {
			return 0;
		}

		if (!world.getBiome(mutable).is(FriendsAndFoesTags.HAS_ICEOLOGER)) {
			return 0;
		}

		mutable.setY(world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, mutable).getY());
		BlockState blockState = world.getBlockState(mutable);

		if (
			!NaturalSpawner.isValidEmptySpawnBlock(world, mutable, blockState, blockState.getFluidState(), FriendsAndFoesEntityTypes.ICEOLOGER.get())
			|| !PatrollingMonster.checkPatrollingMonsterSpawnRules(FriendsAndFoesEntityTypes.ICEOLOGER.get(), world, MobSpawnType.PATROL, mutable, random)
		) {
			return 0;
		}

		var iceologer = FriendsAndFoesEntityTypes.ICEOLOGER.get().create(world);

		if (iceologer == null) {
			return 0;
		}

		iceologer.setPatrolLeader(false);
		iceologer.findPatrolTarget();
		iceologer.setPos(mutable.getX(), mutable.getY(), mutable.getZ());
		iceologer.finalizeSpawn(world, world.getCurrentDifficultyAt(mutable), MobSpawnType.PATROL, null);
		world.addFreshEntityWithPassengers(iceologer);
		return 1;
	}
}
