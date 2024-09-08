package com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.common.client.render.entity.animation.CrabAnimations;
import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CrabBrain;
import com.faboslav.friendsandfoes.common.entity.pose.CrabEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;

public final class CrabDanceTask extends Task<CrabEntity>
{
	private final static int DANCE_DURATION = CrabAnimations.WAVE.getAnimationLengthInTicks() * 60;

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
		if (
			crab.isClimbing()
			|| !crab.isDancing()
		) {
			return false;
		}

		return true;
	}

	@Override
	protected void run(ServerWorld world, CrabEntity crab, long time) {
		crab.getBrain().forget(MemoryModuleType.WALK_TARGET);
		crab.getNavigation().setSpeed(0);
		crab.getNavigation().stop();
		crab.getNavigation().tick();
		crab.getMoveControl().tick();

		crab.setMovementSpeed(0.0F);
		crab.prevHorizontalSpeed = 0.0F;
		crab.horizontalSpeed = 0.0F;
		crab.sidewaysSpeed = 0.0F;
		crab.upwardSpeed = 0.0F;

		crab.getLookControl().tick();

		crab.startDanceAnimation();
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, CrabEntity crab, long time) {
		if (
			crab.isClimbing()
			|| !crab.isDancing()
		) {
			return false;
		}

		return true;
	}

	@Override
	protected void finishRunning(ServerWorld world, CrabEntity crab, long time) {
		crab.setPose(CrabEntityPose.IDLE);
	}
}
