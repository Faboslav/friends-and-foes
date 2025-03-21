package com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.common.client.render.entity.animation.CrabAnimations;
import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.entity.pose.CrabEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.util.MovementUtil;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;

public final class CrabDanceTask extends MultiTickTask<CrabEntity>
{
	private final static int DANCE_DURATION = CrabAnimations.DANCE.getAnimationLengthInTicks() * 60;

	public CrabDanceTask() {
		super(
			Map.of(
				FriendsAndFoesMemoryModuleTypes.CRAB_IS_DANCING.get(), MemoryModuleState.VALUE_PRESENT,
				MemoryModuleType.BREED_TARGET, MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.TEMPTING_PLAYER, MemoryModuleState.VALUE_ABSENT,
				FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryModuleState.VALUE_ABSENT,
				FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(), MemoryModuleState.VALUE_ABSENT
			), DANCE_DURATION
		);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, CrabEntity crab) {
		return !crab.isClimbing()
			   && crab.isDancing();
	}

	@Override
	protected void run(ServerWorld world, CrabEntity crab, long time) {
		MovementUtil.stopMovement(crab);
		crab.startDanceAnimation();
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, CrabEntity crab, long time) {
		return !crab.isClimbing()
			   && crab.isDancing();
	}

	@Override
	protected void finishRunning(ServerWorld world, CrabEntity crab, long time) {
		crab.setPose(CrabEntityPose.IDLE);
	}
}
