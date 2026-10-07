package com.faboslav.friendsandfoes.common.tests.block;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.tests.GameTestUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;

public final class PlaceButtercupTest
{
	private static final BlockPos BLOCK_POS = new BlockPos(1, 1, 1);

	public static void placeButtercup(GameTestHelper helper) {
		GameTestUtil.testBlockPlacement(helper, BLOCK_POS, FriendsAndFoesBlocks.BUTTERCUP.get(), "buttercup");
	}
}
