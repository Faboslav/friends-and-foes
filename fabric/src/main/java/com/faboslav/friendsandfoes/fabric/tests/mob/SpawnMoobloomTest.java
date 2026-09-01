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

public final class SpawnMoobloomTest
{
	private static final BlockPos SPAWN_POS = new BlockPos(1, 1, 1);

	//? if >= 1.21.5 {
	@GameTest
	//?} else {
	/*@GameTest(template = FabricGameTest.EMPTY_STRUCTURE)
	*///?}
	public void spawnMoobloom(GameTestHelper helper) {
		ServerLevel level = helper.getLevel();

		var moobloom = FriendsAndFoesEntityTypes.MOOBLOOM.get().create(level/*? if >=1.21.3 {*/, EntitySpawnReason.TRIGGERED/*?}*/);

		if (moobloom == null) {
			GameTestUtil.fail(helper, "Failed to create moobloom entity");
			return;
		}

		BlockPos spawnPos = helper.absolutePos(SPAWN_POS);

		//? if >= 1.21.5 {
		moobloom.snapTo(
		//?} else {
		/*moobloom.moveTo(
		*///?}
			spawnPos.getX() + 0.5D,
			(double) spawnPos.getY(),
			spawnPos.getZ() + 0.5D,
			0.0F,
			0.0F
		);

		if (!level.addFreshEntity(moobloom)) {
			GameTestUtil.fail(helper, "Failed to add moobloom entity to the level");
			return;
		}

		var spawnedMoobloom = level.getEntity(moobloom.getUUID());

		if (spawnedMoobloom != moobloom) {
			GameTestUtil.fail(helper, "Spawned moobloom could not be found in the level by UUID");
			return;
		}

		helper.succeed();
	}
}
