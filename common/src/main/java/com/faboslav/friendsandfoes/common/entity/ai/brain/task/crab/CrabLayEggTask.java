package com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import java.util.Map;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.TurtleEggBlock;

public final class CrabLayEggTask extends Behavior<CrabEntity>
{
	private final static float WITHING_DISTANCE = 2.0F;

	public CrabLayEggTask() {
		super(
			Map.of(
				FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryStatus.VALUE_PRESENT,
				FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(), MemoryStatus.VALUE_PRESENT
			), 100
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, CrabEntity crab) {
		if (
			crab.getBurrowSpotPos() == null
			|| crab.isBurrowSpotAccessible(crab.getBurrowSpotPos().pos()) == false
			|| crab.getBurrowSpotPos().pos().closerToCenterThan(crab.position(), WITHING_DISTANCE) == false
			|| crab.getNavigation().isInProgress()
		) {
			return false;
		}

		return true;
	}

	@Override
	protected void start(ServerLevel world, CrabEntity crab, long time) {
	}

	@Override
	protected boolean canStillUse(ServerLevel world, CrabEntity crab, long time) {
		return true;
	}

	@Override
	protected void tick(ServerLevel world, CrabEntity crab, long time) {
		var burrowSpotPos = crab.getBurrowSpotPos();

		if (burrowSpotPos == null || time % 5 != 0) {
			return;
		}

		// TODO animation
		world.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, burrowSpotPos.pos(), Block.getId(world.getBlockState(burrowSpotPos.pos().below())));
	}

	@Override
	protected void stop(ServerLevel world, CrabEntity crab, long time) {
		var burrowSpotPos = crab.getBurrowSpotPos();

		if (burrowSpotPos != null) {
			// TODO change sound
			world.playSound(null, burrowSpotPos.pos(), SoundEvents.TURTLE_LAY_EGG, SoundSource.BLOCKS, 0.3f, 0.9f + world.random.nextFloat() * 0.2f);
			world.setBlock(burrowSpotPos.pos(), FriendsAndFoesBlocks.CRAB_EGG.get().defaultBlockState().setValue(TurtleEggBlock.EGGS, crab.getRandom().nextInt(4) + 1), Block.UPDATE_ALL);
		}

		crab.setHasEgg(false);
		crab.setInLoveTime(600);
		crab.getBrain().eraseMemory(FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get());
	}
}
