package com.faboslav.friendsandfoes.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.entity.CrabEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import net.minecraft.block.Block;
import net.minecraft.block.TurtleEggBlock;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.WorldEvents;

import java.util.Map;

public final class CrabLayEggTask extends Task<CrabEntity>
{
	private final static float WITHING_DISTANCE = 2.0F;

	public CrabLayEggTask() {
		super(
			Map.of(
				FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryModuleState.VALUE_PRESENT,
				FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(), MemoryModuleState.VALUE_PRESENT
			), 100
		);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, CrabEntity crab) {
		if (
			crab.getBurrowSpotPos() == null
			|| crab.isBurrowSpotAccessible(crab.getBurrowSpotPos().getPos()) == false
			|| crab.getBurrowSpotPos().getPos().isWithinDistance(crab.getPos(), WITHING_DISTANCE) == false
			|| crab.getNavigation().isFollowingPath()
		) {
			return false;
		}

		return true;
	}

	@Override
	protected void run(ServerWorld world, CrabEntity crab, long time) {
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, CrabEntity crab, long time) {
		return true;
	}

	@Override
	protected void keepRunning(ServerWorld world, CrabEntity crab, long time) {
		var sandSpot = crab.getBurrowSpotPos();

		if (sandSpot == null || time % 5 != 0) {
			return;
		}

		world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, sandSpot.getPos(), Block.getRawIdFromState(world.getBlockState(sandSpot.getPos().down())));
	}

	@Override
	protected void finishRunning(ServerWorld world, CrabEntity crab, long time) {
		var sandSpot = crab.getBurrowSpotPos();

		if (sandSpot != null) {
			world.playSound(null, sandSpot.getPos(), SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS, 0.3f, 0.9f + world.random.nextFloat() * 0.2f);
			world.setBlockState(sandSpot.getPos(), FriendsAndFoesBlocks.CRAB_EGG.get().getDefaultState().with(TurtleEggBlock.EGGS, crab.getRandom().nextInt(4) + 1), Block.NOTIFY_ALL);
		}

		crab.setHasEgg(false);
		crab.setLoveTicks(600);
		crab.getBrain().forget(FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get());
	}
}
