package com.faboslav.friendsandfoes.common.tests.mob;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.tests.GameTestUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;

//? if >= 1.21.3 {
import net.minecraft.world.entity.EntitySpawnReason;
//?}

public final class SpawnRascalTest
{
	private static final BlockPos SPAWN_POS = new BlockPos(1, 1, 1);

	public static void spawnRascal(GameTestHelper helper) {
		ServerLevel level = helper.getLevel();

		var rascal = FriendsAndFoesEntityTypes.RASCAL.get().create(level/*? if >=1.21.3 {*/, EntitySpawnReason.TRIGGERED/*?}*/);

		if (rascal == null) {
			GameTestUtil.fail(helper, "Failed to create rascal entity");
			return;
		}

		BlockPos spawnPos = helper.absolutePos(SPAWN_POS);

		//? if >= 1.21.5 {
		rascal.snapTo(
		//?} else {
		/*rascal.moveTo(
		*///?}
			spawnPos.getX() + 0.5D,
			(double) spawnPos.getY(),
			spawnPos.getZ() + 0.5D,
			0.0F,
			0.0F
		);

		if (!level.addFreshEntity(rascal)) {
			GameTestUtil.fail(helper, "Failed to add rascal entity to the level");
			return;
		}

		var spawnedRascal = level.getEntity(rascal.getUUID());

		if (spawnedRascal != rascal) {
			GameTestUtil.fail(helper, "Spawned rascal could not be found in the level by UUID");
			return;
		}

		helper.succeed();
	}
}
