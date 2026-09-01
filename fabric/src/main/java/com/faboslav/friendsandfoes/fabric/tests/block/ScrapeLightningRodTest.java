package com.faboslav.friendsandfoes.fabric.tests.block;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.fabric.tests.GameTestUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

//? if >= 1.21.5 {
import net.fabricmc.fabric.api.gametest.v1.GameTest;
//?} else {
/*import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
*///?}

//? if >= 1.21.1 {
import net.minecraft.world.level.GameType;
//?}

public final class ScrapeLightningRodTest
{
	private static final BlockPos BLOCK_POS = new BlockPos(1, 1, 1);

	//? if >= 1.21.5 {
	@GameTest
	//?} else {
	/*@GameTest(template = FabricGameTest.EMPTY_STRUCTURE)
	*///?}
	public void scrapeLightningRod(GameTestHelper helper) {
		//? if <= 1.21.8 {
		/*ServerLevel level = helper.getLevel();
		BlockPos pos = helper.absolutePos(BLOCK_POS);

		helper.setBlock(BLOCK_POS, FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD.get().defaultBlockState());

		//? if >= 1.21.1 {
		Player player = helper.makeMockPlayer(GameType.CREATIVE);
		//?} else {
		/^Player player = helper.makeMockPlayer();
		^///?}
		player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.NETHERITE_AXE));

		helper.useBlock(BLOCK_POS, player);

		if (!level.getBlockState(pos).is(FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get())) {
			GameTestUtil.fail(helper, "Scraping the oxidized lightning rod did not produce a weathered lightning rod");
			return;
		}

		helper.useBlock(BLOCK_POS, player);

		if (!level.getBlockState(pos).is(FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get())) {
			GameTestUtil.fail(helper, "Scraping the weathered lightning rod did not produce an exposed lightning rod");
			return;
		}

		helper.useBlock(BLOCK_POS, player);

		if (!level.getBlockState(pos).is(Blocks.LIGHTNING_ROD)) {
			GameTestUtil.fail(helper, "Scraping the exposed lightning rod did not produce an unaffected lightning rod");
			return;
		}
		*///?}

		helper.succeed();
	}
}
