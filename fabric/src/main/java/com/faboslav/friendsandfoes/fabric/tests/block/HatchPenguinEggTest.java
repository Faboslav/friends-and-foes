package com.faboslav.friendsandfoes.fabric.tests.block;

import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.fabric.tests.GameTestUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;

//? if >= 1.21.5 {
import net.fabricmc.fabric.api.gametest.v1.GameTest;
//?} else {
/*import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
*///?}

public final class HatchPenguinEggTest
{
	private static final BlockPos EGG_POS = new BlockPos(1, 1, 1);
	private static final int HATCH_TICKS_NEEDED = 3;

	//? if >= 1.21.5 {
	@GameTest
	//?} else {
	/*@GameTest(template = FabricGameTest.EMPTY_STRUCTURE)
	*///?}
	public void hatchPenguinEgg(GameTestHelper helper) {
		ServerLevel level = helper.getLevel();
		BlockPos pos = helper.absolutePos(EGG_POS);

		helper.setBlock(EGG_POS, FriendsAndFoesBlocks.PENGUIN_EGG.get().defaultBlockState());

		var random = level.getRandom();

		for (int i = 0; i < HATCH_TICKS_NEEDED; i++) {
			level.getBlockState(pos).tick(level, pos, random);
		}

		if (level.getBlockState(pos).is(FriendsAndFoesBlocks.PENGUIN_EGG.get())) {
			GameTestUtil.fail(helper, "Penguin egg never hatched");
			return;
		}

		var penguins = level.getEntitiesOfClass(PenguinEntity.class, new AABB(pos).inflate(2.0D));

		if (penguins.isEmpty()) {
			GameTestUtil.fail(helper, "Penguin egg hatched but no penguin was spawned nearby");
			return;
		}

		helper.succeed();
	}
}
