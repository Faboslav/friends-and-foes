package com.faboslav.friendsandfoes.fabric.tests.block;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.fabric.tests.GameTestUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

//? if >= 1.21.5 {
import net.fabricmc.fabric.api.gametest.v1.GameTest;
//?} else {
/*import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
*///?}

public final class BuildTuffGolemTest
{
	private static final BlockPos TUFF_POS = new BlockPos(1, 1, 1);
	private static final BlockPos WOOL_POS = new BlockPos(1, 2, 1);
	private static final BlockPos HEAD_POS = new BlockPos(1, 3, 1);

	//? if >= 1.21.5 {
	@GameTest
	//?} else {
	/*@GameTest(template = FabricGameTest.EMPTY_STRUCTURE)
	*///?}
	public void buildTuffGolem(GameTestHelper helper) {
		ServerLevel level = helper.getLevel();

		FriendsAndFoes.getConfig().enableTuffGolem = true;

		helper.setBlock(TUFF_POS, Blocks.TUFF.defaultBlockState());
		//? if >= 26.2 {
		helper.setBlock(WOOL_POS, Blocks.WOOL.white().defaultBlockState());
		//?} else {
		/*helper.setBlock(WOOL_POS, Blocks.WHITE_WOOL.defaultBlockState());
		*///?}

		// Placing the carved pumpkin last is what triggers the pattern search.
		helper.setBlock(HEAD_POS, Blocks.CARVED_PUMPKIN.defaultBlockState());

		BlockPos tuffPos = helper.absolutePos(TUFF_POS);
		var nearbyTuffGolems = level.getEntitiesOfClass(TuffGolemEntity.class, new AABB(tuffPos).inflate(3.0D));

		if (nearbyTuffGolems.isEmpty()) {
			GameTestUtil.fail(helper, "Tuff golem build pattern did not spawn a tuff golem");
			return;
		}

		helper.succeed();
	}
}
