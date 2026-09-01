package com.faboslav.friendsandfoes.fabric.tests.mob;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.fabric.tests.GameTestUtil;import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;

//? if >= 1.21.5 {
import net.fabricmc.fabric.api.gametest.v1.GameTest;
//?} else {
/*import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
*///?}

//? if >= 1.21.3 {
import net.minecraft.world.entity.EntitySpawnReason;
//?}

public final class SpawnBarnacleTest
{
	private static final BlockPos SPAWN_POS = new BlockPos(1, 1, 1);

	//? if >= 1.21.5 {
	@GameTest
	//?} else {
	/*@GameTest(template = FabricGameTest.EMPTY_STRUCTURE)
	*///?}
	public void spawnBarnacle(GameTestHelper helper) {
		ServerLevel level = helper.getLevel();

		var barnacle = FriendsAndFoesEntityTypes.BARNACLE.get().create(level/*? if >=1.21.3 {*/, EntitySpawnReason.TRIGGERED/*?}*/);

		if (barnacle == null) {
			GameTestUtil.fail(helper, "Failed to create barnacle entity");
			return;
		}

		BlockPos spawnPos = helper.absolutePos(SPAWN_POS);

		//? if >= 1.21.5 {
		barnacle.snapTo(
		//?} else {
		/*barnacle.moveTo(
		*///?}
			spawnPos.getX() + 0.5D,
			(double) spawnPos.getY(),
			spawnPos.getZ() + 0.5D,
			0.0F,
			0.0F
		);

		if (!level.addFreshEntity(barnacle)) {
			GameTestUtil.fail(helper, "Failed to add barnacle entity to the level");
			return;
		}

		var spawnedBarnacle = level.getEntity(barnacle.getUUID());

		if (spawnedBarnacle != barnacle) {
			GameTestUtil.fail(helper, "Spawned barnacle could not be found in the level by UUID");
			return;
		}

		helper.succeed();
	}
}
