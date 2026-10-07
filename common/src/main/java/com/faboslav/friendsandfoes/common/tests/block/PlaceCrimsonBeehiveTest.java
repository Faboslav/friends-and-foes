package com.faboslav.friendsandfoes.common.tests.block;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.tests.GameTestUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;

public final class PlaceCrimsonBeehiveTest
{
	private static final BlockPos BLOCK_POS = new BlockPos(1, 1, 1);

	public static void placeCrimsonBeehive(GameTestHelper helper) {
		GameTestUtil.testBlockPlacement(helper, BLOCK_POS, FriendsAndFoesBlocks.CRIMSON_BEEHIVE.get(), "crimson beehive");
	}
}
