package com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import net.minecraft.block.Block;
import net.minecraft.block.TurtleEggBlock;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.WorldEvents;

import java.util.Map;

public final class CrabLayEggTask extends MultiTickTask<CrabEntity>
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
		return crab.getBurrowSpotPos() != null
			   && crab.isBurrowSpotAccessible(crab.getBurrowSpotPos().getPos())
			   && crab.getBurrowSpotPos().getPos().isWithinDistance(crab.getPos(), WITHING_DISTANCE)
			   && !crab.getNavigation().isFollowingPath();
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
		var burrowSpotPos = crab.getBurrowSpotPos();

		if (burrowSpotPos == null || time % 5 != 0) {
			return;
		}

		// TODO animation
		world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, burrowSpotPos.getPos(), Block.getRawIdFromState(world.getBlockState(burrowSpotPos.getPos().down())));
	}

	@Override
	protected void finishRunning(ServerWorld world, CrabEntity crab, long time) {
		var burrowSpotPos = crab.getBurrowSpotPos();

		if (burrowSpotPos != null) {
			// TODO change sound
			world.playSound(null, burrowSpotPos.getPos(), SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS, 0.3f, 0.9f + world.random.nextFloat() * 0.2f);
			world.setBlockState(burrowSpotPos.getPos(), FriendsAndFoesBlocks.CRAB_EGG.get().getDefaultState().with(TurtleEggBlock.EGGS, crab.getRandom().nextInt(4) + 1), Block.NOTIFY_ALL);
		}

		crab.setHasEgg(false);
		crab.setLoveTicks(600);
		crab.getBrain().forget(FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get());
	}
}
