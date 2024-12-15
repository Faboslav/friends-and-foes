package com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public final class CrabGoToHomePositionTask extends Behavior<CrabEntity>
{
	private final static int GO_TO_HOME_POSITION_DURATION = 2400;

	public CrabGoToHomePositionTask() {
		super(ImmutableMap.of(
			MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_ABSENT,
			MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
			FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryStatus.VALUE_PRESENT
		), GO_TO_HOME_POSITION_DURATION);
	}

	@Override
	protected boolean checkExtraStartConditions(
		ServerLevel world,
		CrabEntity crab
	) {
		return !crab.isCloseToHomePos(3.0F)
			   && !crab.isLeashed()
			   && !crab.isPassenger();
	}

	@Override
	protected void start(
		ServerLevel serverWorld,
		CrabEntity crab,
		long l
	) {
		this.walkTowardsHomePos(crab);
	}

	@Override
	protected boolean canStillUse(
		ServerLevel world,
		CrabEntity crab,
		long time
	) {
		return !crab.isAtHomePos()
			   && !crab.isLeashed()
			   && !crab.isPassenger();
	}

	@Override
	protected void tick(
		ServerLevel world,
		CrabEntity crab,
		long time
	) {
		if (crab.getNavigation().isInProgress()) {
			return;
		}

		this.walkTowardsHomePos(crab);
	}

	@Override
	protected void stop(
		ServerLevel world,
		CrabEntity crab,
		long time
	) {

		crab.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
		crab.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
	}

	private void walkTowardsHomePos(
		CrabEntity crab
	) {
		BehaviorUtils.setWalkAndLookTargetMemories(
			crab,
			BlockPos.containing(crab.getHomePos()),
			1.0F,
			0
		);
	}
}
