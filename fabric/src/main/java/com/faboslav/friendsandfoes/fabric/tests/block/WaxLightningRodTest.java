//? if <= 1.21.8 {
/*package com.faboslav.friendsandfoes.fabric.tests.block;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
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
/^import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
^///?}

//? if >= 1.21.1 {
import net.minecraft.world.level.GameType;
//?}

public final class WaxLightningRodTest
{
	private static final BlockPos BLOCK_POS = new BlockPos(1, 1, 1);
	private static final int RANDOM_TICKS_AFTER_WAX = 10_000;

	//? if >= 1.21.5 {
	@GameTest
	//?} else {
	/^@GameTest(template = FabricGameTest.EMPTY_STRUCTURE)
	^///?}
	public void waxLightningRod(GameTestHelper helper) {
		ServerLevel level = helper.getLevel();
		BlockPos pos = helper.absolutePos(BLOCK_POS);

		FriendsAndFoes.getConfig().enableLightningRodOxidation = true;

		helper.setBlock(BLOCK_POS, Blocks.LIGHTNING_ROD.defaultBlockState());

		//? if >= 1.21.1 {
		Player player = helper.makeMockPlayer(GameType.CREATIVE);
		//?} else {
		/^Player player = helper.makeMockPlayer();
		^///?}
		player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.HONEYCOMB));

		helper.useBlock(BLOCK_POS, player);

		if (!level.getBlockState(pos).is(FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get())) {
			GameTestUtil.fail(helper, "Waxing the lightning rod did not produce a waxed lightning rod");
			return;
		}

		var random = level.getRandom();

		for (int i = 0; i < RANDOM_TICKS_AFTER_WAX; i++) {
			level.getBlockState(pos).randomTick(level, pos, random);
		}

		if (!level.getBlockState(pos).is(FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get())) {
			GameTestUtil.fail(helper, "Waxed lightning rod changed state after random ticks, waxing did not freeze oxidation");
			return;
		}

		helper.succeed();
	}
}
*///?}
