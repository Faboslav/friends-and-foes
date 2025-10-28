package com.faboslav.friendsandfoes.common.world.spawner;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.versions.VersionedEntitySpawnReason;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

public final class IllusionerSpawner implements CustomSpawner
{
	private int cooldown;

	public IllusionerSpawner() {
	}

	@Override
	//? if >= 1.21.9 {
	public void tick(ServerLevel world, boolean spawnMonsters)
	//?} else if >=1.21.5 {
	/*public void tick(ServerLevel world, boolean spawnMonsters, boolean spawnAnimals)
	 *///?} else {
	/*public int tick(ServerLevel world, boolean spawnMonsters, boolean spawnAnimals)
	*///?}
	{
		if (
			!spawnMonsters
			|| !FriendsAndFoes.getConfig().enableIllusioner
			|| !FriendsAndFoes.getConfig().enableIllusionerSpawn
		) {
			return /*? if <1.21.5 {*//*0*//*?}*/;
		}

		RandomSource random = world.getRandom();
		--this.cooldown;

		if (this.cooldown > 0) {
			return /*? if <1.21.5 {*//*0*//*?}*/;
		}

		this.cooldown += 12000 + random.nextInt(1000);
		long l = world.getDayTime() / 24000L;

		if (
			l < 5L
			//? if >=1.21.5 {
			|| world.isDarkOutside()
			//?} else {
			/*|| world.isNight()
			*///?}
			|| random.nextIntBetweenInclusive(0, 1) != 0
		) {
			return /*? if <1.21.5 {*//*0*//*?}*/;
		}

		int playerCount = world.players().size();
		if (playerCount == 0) {
			return /*? if <1.21.5 {*//*0*//*?}*/;
		}

		Player playerEntity = world.players().get(random.nextInt(playerCount));

		if (
			playerEntity.isSpectator()
			|| world.isCloseToVillage(playerEntity.blockPosition(), 2)
		) {
			return /*? if <1.21.5 {*//*0*//*?}*/;
		}

		int j = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1:1);
		int k = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1:1);
		MutableBlockPos mutable = playerEntity.blockPosition().mutable().move(j, 0, k);

		var minX = mutable.getX() - 10;
		var minZ = mutable.getZ() - 10;
		var maxX = mutable.getX() + 10;
		var maxZ = mutable.getZ() + 10;
		if (!world.hasChunksAt(minX, minZ, maxX, maxZ)) {
			return /*? if <1.21.5 {*//*0*//*?}*/;
		}

		if (!world.getBiome(mutable).is(FriendsAndFoesTags.HAS_ILLUSIONER)) {
			return /*? if <1.21.5 {*//*0*//*?}*/;
		}

		mutable.setY(world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, mutable).getY());
		BlockState blockState = world.getBlockState(mutable);

		if (
			!NaturalSpawner.isValidEmptySpawnBlock(world, mutable, blockState, blockState.getFluidState(), FriendsAndFoesEntityTypes.ILLUSIONER.get())
			|| !PatrollingMonster.checkPatrollingMonsterSpawnRules(FriendsAndFoesEntityTypes.ILLUSIONER.get(), world, VersionedEntitySpawnReason.PATROL, mutable, random)
		) {
			return /*? if <1.21.5 {*//*0*//*?}*/;
		}

		var illusioner = FriendsAndFoesEntityTypes.ILLUSIONER.get().create(world/*? if >=1.21.3 {*/, VersionedEntitySpawnReason.PATROL/*?}*/);

		if (illusioner == null) {
			return /*? if <1.21.5 {*//*0*//*?}*/;
		}

		illusioner.setPatrolLeader(false);
		illusioner.findPatrolTarget();
		illusioner.setPos(mutable.getX(), mutable.getY(), mutable.getZ());
		illusioner.finalizeSpawn(world, world.getCurrentDifficultyAt(mutable), VersionedEntitySpawnReason.PATROL, null);
		world.addFreshEntityWithPassengers(illusioner);
		return /*? if <1.21.5 {*//*1*//*?}*/;
	}
}
