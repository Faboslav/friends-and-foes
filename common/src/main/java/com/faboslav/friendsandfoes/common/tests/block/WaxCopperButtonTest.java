package com.faboslav.friendsandfoes.common.tests.block;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.tests.GameTestUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;

//? if >= 1.21.1 {
import net.minecraft.world.level.GameType;
//?}

public final class WaxCopperButtonTest
{
	private static final BlockPos BLOCK_POS = new BlockPos(1, 1, 1);
	private static final int RANDOM_TICKS_AFTER_WAX = 10_000;

	public static void waxCopperButton(GameTestHelper helper) {
		ServerLevel level = helper.getLevel();
		BlockPos pos = helper.absolutePos(BLOCK_POS);

		helper.setBlock(BLOCK_POS, FriendsAndFoesBlocks.COPPER_BUTTON.get().defaultBlockState());

		//? if >= 1.21.1 {
		Player player = helper.makeMockPlayer(GameType.CREATIVE);
		//?} else {
		/*Player player = helper.makeMockPlayer();
		*///?}
		player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.HONEYCOMB));

		helper.useBlock(BLOCK_POS, player);

		if (!level.getBlockState(pos).is(FriendsAndFoesBlocks.WAXED_COPPER_BUTTON.get())) {
			GameTestUtil.fail(helper, "Waxing the copper button did not produce a waxed copper button");
			return;
		}

		var random = level.getRandom();

		for (int i = 0; i < RANDOM_TICKS_AFTER_WAX; i++) {
			level.getBlockState(pos).randomTick(level, pos, random);
		}

		if (!level.getBlockState(pos).is(FriendsAndFoesBlocks.WAXED_COPPER_BUTTON.get())) {
			GameTestUtil.fail(helper, "Waxed copper button changed state after random ticks, waxing did not freeze oxidation");
			return;
		}

		helper.succeed();
	}
}
