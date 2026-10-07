package com.faboslav.friendsandfoes.common.tests.block;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.tests.GameTestUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;

public final class OxidizeCopperButtonTest
{
	private static final BlockPos BLOCK_POS = new BlockPos(1, 1, 1);
	private static final int MAX_RANDOM_TICKS = 100_000;

	public static void oxidizeCopperButton(GameTestHelper helper) {
		ServerLevel level = helper.getLevel();
		BlockPos pos = helper.absolutePos(BLOCK_POS);

		helper.setBlock(BLOCK_POS, FriendsAndFoesBlocks.COPPER_BUTTON.get().defaultBlockState());

		var random = level.getRandom();
		boolean reachedExposed = false;
		boolean reachedWeathered = false;
		boolean reachedOxidized = false;

		for (int i = 0; i < MAX_RANDOM_TICKS && !reachedOxidized; i++) {
			level.getBlockState(pos).randomTick(level, pos, random);

			var state = level.getBlockState(pos);

			if (!reachedExposed && state.is(FriendsAndFoesBlocks.EXPOSED_COPPER_BUTTON.get())) {
				reachedExposed = true;
			} else if (!reachedWeathered && state.is(FriendsAndFoesBlocks.WEATHERED_COPPER_BUTTON.get())) {
				reachedWeathered = true;
			} else if (!reachedOxidized && state.is(FriendsAndFoesBlocks.OXIDIZED_COPPER_BUTTON.get())) {
				reachedOxidized = true;
			}
		}

		if (!reachedExposed) {
			GameTestUtil.fail(helper, "Copper button never reached the exposed oxidation level");
			return;
		}

		if (!reachedWeathered) {
			GameTestUtil.fail(helper, "Copper button never reached the weathered oxidation level");
			return;
		}

		if (!reachedOxidized) {
			GameTestUtil.fail(helper, "Copper button never reached the oxidized oxidation level");
			return;
		}

		helper.succeed();
	}
}
