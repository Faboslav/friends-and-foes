package com.faboslav.friendsandfoes.common.tests.block;

import com.faboslav.friendsandfoes.common.tests.GameTestUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;

//? if >= 1.21.4 {
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
//?}

public final class PlacePaleOakBeehiveTest
{
	private static final BlockPos BLOCK_POS = new BlockPos(1, 1, 1);

	public static void placePaleOakBeehive(GameTestHelper helper) {
		//? if >= 1.21.4 {
		GameTestUtil.testBlockPlacement(helper, BLOCK_POS, FriendsAndFoesBlocks.PALE_OAK_BEEHIVE.get(), "pale oak beehive");
		//?} else {
		/*helper.succeed();
		*///?}
	}
}
