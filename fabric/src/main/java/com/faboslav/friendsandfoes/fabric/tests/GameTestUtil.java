package com.faboslav.friendsandfoes.fabric.tests;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Block;

//? if >= 1.21.5 {
import net.minecraft.network.chat.Component;
//?}

public final class GameTestUtil
{
	public static void fail(GameTestHelper helper, String message) {
		//? if >= 1.21.5 {
		helper.fail(Component.literal(message));
		//?} else {
		/*helper.fail(message);
		*///?}
	}

	public static void testBlockPlacement(GameTestHelper helper, BlockPos relativePos, Block block, String blockName) {
		helper.setBlock(relativePos, block);

		BlockPos pos = helper.absolutePos(relativePos);

		if (!helper.getLevel().getBlockState(pos).is(block)) {
			fail(helper, "Failed to place " + blockName);
			return;
		}

		helper.succeed();
	}
}
