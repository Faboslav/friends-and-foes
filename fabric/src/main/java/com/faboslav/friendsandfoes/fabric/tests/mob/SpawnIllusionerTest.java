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

public final class SpawnIllusionerTest
{
	private static final BlockPos SPAWN_POS = new BlockPos(1, 1, 1);

	//? if >= 1.21.5 {
	@GameTest
	//?} else {
	/*@GameTest(template = FabricGameTest.EMPTY_STRUCTURE)
	*///?}
	public void spawnIllusioner(GameTestHelper helper) {
		ServerLevel level = helper.getLevel();

		var illusioner = FriendsAndFoesEntityTypes.ILLUSIONER.get().create(level/*? if >=1.21.3 {*/, EntitySpawnReason.TRIGGERED/*?}*/);

		if (illusioner == null) {
			GameTestUtil.fail(helper, "Failed to create illusioner entity");
			return;
		}

		BlockPos spawnPos = helper.absolutePos(SPAWN_POS);

		//? if >= 1.21.5 {
		illusioner.snapTo(
		//?} else {
		/*illusioner.moveTo(
		*///?}
			spawnPos.getX() + 0.5D,
			(double) spawnPos.getY(),
			spawnPos.getZ() + 0.5D,
			0.0F,
			0.0F
		);

		if (!level.addFreshEntity(illusioner)) {
			GameTestUtil.fail(helper, "Failed to add illusioner entity to the level");
			return;
		}

		var spawnedIllusioner = level.getEntity(illusioner.getUUID());

		if (spawnedIllusioner != illusioner) {
			GameTestUtil.fail(helper, "Spawned illusioner could not be found in the level by UUID");
			return;
		}

		helper.succeed();
	}
}
