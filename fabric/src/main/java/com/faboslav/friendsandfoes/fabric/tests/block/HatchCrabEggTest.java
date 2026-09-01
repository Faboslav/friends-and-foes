package com.faboslav.friendsandfoes.fabric.tests.block;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.fabric.tests.GameTestUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

//? if >= 1.21.5 {
import net.fabricmc.fabric.api.gametest.v1.GameTest;
//?} else {
/*import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
*///?}

public final class HatchCrabEggTest
{
	private static final BlockPos SAND_POS = new BlockPos(1, 1, 1);
	private static final BlockPos EGG_POS = new BlockPos(1, 2, 1);
	private static final int MAX_RANDOM_TICKS = 200_000;

	//? if >= 1.21.5 {
	@GameTest
	//?} else {
	/*@GameTest(template = FabricGameTest.EMPTY_STRUCTURE)
	*///?}
	public void hatchCrabEgg(GameTestHelper helper) {
		ServerLevel level = helper.getLevel();
		BlockPos pos = helper.absolutePos(EGG_POS);

		helper.setBlock(SAND_POS, Blocks.SAND);
		helper.setBlock(EGG_POS, FriendsAndFoesBlocks.CRAB_EGG.get().defaultBlockState());

		var random = level.getRandom();
		boolean hatched = false;

		for (int i = 0; i < MAX_RANDOM_TICKS && !hatched; i++) {
			level.getBlockState(pos).randomTick(level, pos, random);

			if (!level.getBlockState(pos).is(FriendsAndFoesBlocks.CRAB_EGG.get())) {
				hatched = true;
			}
		}

		if (!hatched) {
			GameTestUtil.fail(helper, "Crab egg never hatched");
			return;
		}

		var crabs = level.getEntitiesOfClass(CrabEntity.class, new AABB(pos).inflate(2.0D));

		if (crabs.isEmpty()) {
			GameTestUtil.fail(helper, "Crab egg hatched but no crab was spawned nearby");
			return;
		}

		helper.succeed();
	}
}
