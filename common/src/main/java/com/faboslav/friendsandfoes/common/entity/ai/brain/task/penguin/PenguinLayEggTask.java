package com.faboslav.friendsandfoes.common.entity.ai.brain.task.penguin;

import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import java.util.Map;

import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;

public final class PenguinLayEggTask extends Behavior<PenguinEntity>
{
	private final static float WITHING_DISTANCE = 2.0F;

	public PenguinLayEggTask() {
		super(
			Map.of(
				FriendsAndFoesMemoryModuleTypes.PENGUIN_HAS_EGG.get(), MemoryStatus.VALUE_PRESENT,
				FriendsAndFoesMemoryModuleTypes.PENGUIN_EGG_POS.get(), MemoryStatus.VALUE_PRESENT
			)
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, PenguinEntity penguin) {
		GlobalPos eggSpotPos = penguin.getEggSpotPos();

		if (
			eggSpotPos == null
			|| !penguin.isEggSpotAccessible(eggSpotPos.pos())
			|| !eggSpotPos.pos().closerToCenterThan(penguin.position(), WITHING_DISTANCE)
			|| penguin.getNavigation().isInProgress()
		) {
			return false;
		}

		return true;
	}

	@Override
	protected void start(ServerLevel world, PenguinEntity penguin, long time) {
	}

	@Override
	protected boolean canStillUse(ServerLevel world, PenguinEntity penguin, long time) {
		return false;
	}

	@Override
	protected void tick(ServerLevel world, PenguinEntity penguin, long time) {
	}

	@Override
	protected void stop(ServerLevel world, PenguinEntity penguin, long time) {
		GlobalPos eggSpotPos = penguin.getEggSpotPos();

		if (eggSpotPos != null) {
			world.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, eggSpotPos.pos(), Block.getId(world.getBlockState(eggSpotPos.pos().below())));
			world.playSound(null, eggSpotPos.pos(), FriendsAndFoesSoundEvents.ENTITY_PENGUIN_LAY_EGG.get(), SoundSource.BLOCKS, 0.3f, 0.9f + world.getRandom().nextFloat() * 0.2f);
			world.setBlock(eggSpotPos.pos(), FriendsAndFoesBlocks.PENGUIN_EGG.get().defaultBlockState(), Block.UPDATE_ALL);
		}

		penguin.setHasEgg(false);
		penguin.setInLoveTime(600);
		penguin.getBrain().eraseMemory(FriendsAndFoesMemoryModuleTypes.PENGUIN_EGG_POS.get());
	}
}
