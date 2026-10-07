package com.faboslav.friendsandfoes.common.tests.block;

import com.faboslav.friendsandfoes.common.tests.GameTestUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;

//? if >= 26.3 {
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
//?}

public final class PlacePoplarBeehiveTest
{
	private static final BlockPos BLOCK_POS = new BlockPos(1, 1, 1);

	public static void placePoplarBeehive(GameTestHelper helper) {
		//? if >= 26.3 {
		GameTestUtil.testBlockPlacement(helper, BLOCK_POS, FriendsAndFoesBlocks.POPLAR_BEEHIVE.get(), "poplar beehive");
		//?} else {
		/*helper.succeed();
		*///?}
	}
}
