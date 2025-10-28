package com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.common.entity.animation.CrabAnimations;
import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.entity.pose.FriendsAndFoesEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.util.MovementUtil;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public final class CrabDanceTask extends Behavior<CrabEntity>
{
	private final static int DANCE_DURATION = CrabAnimations.DANCE.get().lengthInTicks() * 60;

	public CrabDanceTask() {
		super(
			Map.of(
				FriendsAndFoesMemoryModuleTypes.CRAB_IS_DANCING.get(), MemoryStatus.VALUE_PRESENT,
				MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT,
				FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryStatus.VALUE_ABSENT,
				FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(), MemoryStatus.VALUE_ABSENT
			), DANCE_DURATION
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, CrabEntity crab) {
		return !crab.onClimbable()
			   && crab.isDancing();
	}

	@Override
	protected void start(ServerLevel world, CrabEntity crab, long time) {
		MovementUtil.stopMovement(crab);
		crab.startDanceAnimation();
	}

	@Override
	protected boolean canStillUse(ServerLevel world, CrabEntity crab, long time) {
		return !crab.onClimbable()
			   && crab.isDancing();
	}

	@Override
	protected void stop(ServerLevel world, CrabEntity crab, long time) {
		crab.setEntityPose(FriendsAndFoesEntityPose.IDLE);
	}
}
