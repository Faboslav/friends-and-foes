package com.faboslav.friendsandfoes.fabric.tests.block;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.fabric.tests.GameTestUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;

//? if >= 1.21.5 {
import net.fabricmc.fabric.api.gametest.v1.GameTest;
//?} else {
/*import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
*///?}

public final class OxidizeLightningRodTest
{
	private static final BlockPos BLOCK_POS = new BlockPos(1, 1, 1);
	private static final int MAX_RANDOM_TICKS = 100_000;

	//? if >= 1.21.5 {
	@GameTest
	//?} else {
	/*@GameTest(template = FabricGameTest.EMPTY_STRUCTURE)
	*///?}
	public void oxidizeLightningRod(GameTestHelper helper) {
		//? if <= 1.21.8 {
		/*ServerLevel level = helper.getLevel();
		BlockPos pos = helper.absolutePos(BLOCK_POS);

		FriendsAndFoes.getConfig().enableLightningRodOxidation = true;

		helper.setBlock(BLOCK_POS, Blocks.LIGHTNING_ROD.defaultBlockState());

		var random = level.getRandom();
		boolean reachedExposed = false;
		boolean reachedWeathered = false;
		boolean reachedOxidized = false;

		for (int i = 0; i < MAX_RANDOM_TICKS && !reachedOxidized; i++) {
			level.getBlockState(pos).randomTick(level, pos, random);

			var state = level.getBlockState(pos);

			if (!reachedExposed && state.is(FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get())) {
				reachedExposed = true;
			} else if (!reachedWeathered && state.is(FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get())) {
				reachedWeathered = true;
			} else if (!reachedOxidized && state.is(FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD.get())) {
				reachedOxidized = true;
			}
		}

		if (!reachedExposed) {
			GameTestUtil.fail(helper, "Lightning rod never reached the exposed oxidation level");
			return;
		}

		if (!reachedWeathered) {
			GameTestUtil.fail(helper, "Lightning rod never reached the weathered oxidation level");
			return;
		}

		if (!reachedOxidized) {
			GameTestUtil.fail(helper, "Lightning rod never reached the oxidized oxidation level");
			return;
		}
		*///?}

		helper.succeed();
	}
}
