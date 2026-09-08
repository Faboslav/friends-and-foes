//? if <= 1.21.8 {
/*package com.faboslav.friendsandfoes.fabric.tests.block;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.fabric.tests.GameTestUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.phys.AABB;

//? if >= 1.21.5 {
import net.fabricmc.fabric.api.gametest.v1.GameTest;
//?} else {
/^import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
^///?}

public final class BuildCopperGolemTest
{
	private static final BlockPos BODY_POS = new BlockPos(1, 1, 1);
	private static final BlockPos HEAD_POS = new BlockPos(1, 2, 1);
	private static final BlockPos LIGHTNING_ROD_POS = new BlockPos(1, 3, 1);

	//? if >= 1.21.5 {
	@GameTest
	//?} else {
	/^@GameTest(template = FabricGameTest.EMPTY_STRUCTURE)
	^///?}
	public void buildCopperGolem(GameTestHelper helper) {
		ServerLevel level = helper.getLevel();

		FriendsAndFoes.getConfig().enableCopperGolem = true;

		helper.setBlock(BODY_POS, Blocks.COPPER_BLOCK.defaultBlockState());
		helper.setBlock(HEAD_POS, Blocks.CARVED_PUMPKIN.defaultBlockState());

		// Placing the lightning rod last is what triggers the pattern search.
		helper.setBlock(
			LIGHTNING_ROD_POS,
			Blocks.LIGHTNING_ROD.defaultBlockState().setValue(LightningRodBlock.FACING, Direction.UP)
		);

		BlockPos bodyPos = helper.absolutePos(BODY_POS);
		var nearbyCopperGolems = level.getEntitiesOfClass(CopperGolemEntity.class, new AABB(bodyPos).inflate(3.0D));

		if (nearbyCopperGolems.isEmpty()) {
			GameTestUtil.fail(helper, "Copper golem build pattern did not spawn a copper golem");
			return;
		}

		helper.succeed();
	}
}
*///?}
