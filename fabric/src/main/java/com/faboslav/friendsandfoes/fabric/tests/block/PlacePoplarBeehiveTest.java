package com.faboslav.friendsandfoes.fabric.tests.block;

import com.faboslav.friendsandfoes.fabric.tests.GameTestUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;

//? if >= 1.21.5 {
import net.fabricmc.fabric.api.gametest.v1.GameTest;
//?} else {
/*import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
*///?}

//? if >= 26.3 {
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
//?}

public final class PlacePoplarBeehiveTest
{
	private static final BlockPos BLOCK_POS = new BlockPos(1, 1, 1);

	//? if >= 1.21.5 {
	@GameTest
	//?} else {
	/*@GameTest(template = FabricGameTest.EMPTY_STRUCTURE)
	*///?}
	public void placePoplarBeehive(GameTestHelper helper) {
		//? if >= 26.3 {
		GameTestUtil.testBlockPlacement(helper, BLOCK_POS, FriendsAndFoesBlocks.POPLAR_BEEHIVE.get(), "poplar beehive");
		//?} else {
		/*helper.succeed();
		*///?}
	}
}
