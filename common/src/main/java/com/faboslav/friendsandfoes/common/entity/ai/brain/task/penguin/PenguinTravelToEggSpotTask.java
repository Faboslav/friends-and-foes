package com.faboslav.friendsandfoes.common.entity.ai.brain.task.penguin;

import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public final class PenguinTravelToEggSpotTask extends Behavior<PenguinEntity>
{
	private static final int MAX_TRAVELLING_TICKS = 600;
	private final static float WITHING_DISTANCE = 1.5F;

	public PenguinTravelToEggSpotTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.PENGUIN_HAS_EGG.get(), MemoryStatus.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.PENGUIN_EGG_POS.get(), MemoryStatus.VALUE_PRESENT
		), MAX_TRAVELLING_TICKS);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, PenguinEntity penguin) {
		return penguin.getEggSpotPos() != null;
	}

	@Override
	protected void start(ServerLevel world, PenguinEntity penguin, long time) {
		this.walkTowardsEggSpot(penguin);
	}

	@Override
	protected boolean canStillUse(ServerLevel world, PenguinEntity penguin, long time) {
		GlobalPos eggSpotPos = penguin.getEggSpotPos();

		if (eggSpotPos == null || !penguin.isEggSpotAccessible(eggSpotPos.pos())) {
			return false;
		}

		return true;
	}

	protected void tick(ServerLevel world, PenguinEntity penguin, long time) {
		if (penguin.getNavigation().isInProgress()) {
			return;
		}

		this.walkTowardsEggSpot(penguin);
	}

	@Override
	protected void stop(ServerLevel world, PenguinEntity penguin, long time) {
		penguin.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
		GlobalPos eggSpotPos = penguin.getEggSpotPos();

		if (
			eggSpotPos != null &&
			(
				eggSpotPos.pos().closerToCenterThan(penguin.position(), WITHING_DISTANCE) == false
				|| penguin.isEggSpotAccessible(eggSpotPos.pos()) == false
			)
		) {
			penguin.setHasEgg(false);
			penguin.setInLoveTime(600);
			penguin.getBrain().eraseMemory(FriendsAndFoesMemoryModuleTypes.PENGUIN_EGG_POS.get());
		}
	}

	private void walkTowardsEggSpot(PenguinEntity penguin) {
		GlobalPos eggSpotPos = penguin.getBrain().getMemoryInternal(FriendsAndFoesMemoryModuleTypes.PENGUIN_EGG_POS.get()).orElse(null);

		if (eggSpotPos == null) {
			return;
		}

		BehaviorUtils.setWalkAndLookTargetMemories(
			penguin,
			new BlockPos(eggSpotPos.pos()),
			0.6F,
			0
		);
	}
}
