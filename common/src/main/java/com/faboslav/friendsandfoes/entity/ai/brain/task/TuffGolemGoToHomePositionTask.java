package com.faboslav.friendsandfoes.entity.ai.brain.task;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class TuffGolemGoToHomePositionTask extends Task<TuffGolemEntity>
{
	private final static int GO_TO_SLEEP_POSITION_DURATION = 3600;

	public TuffGolemGoToHomePositionTask() {
		super(ImmutableMap.of(
			MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT,
			FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
		), GO_TO_SLEEP_POSITION_DURATION);
	}

	@Override
	protected boolean shouldRun(
		ServerWorld world,
		TuffGolemEntity tuffGolem
	) {
		if (
			tuffGolem.isInSleepingPose()
			|| tuffGolem.isGlued()
			|| tuffGolem.isLeashed()
			|| tuffGolem.hasVehicle()
		) {
			return false;
		}

		FriendsAndFoes.getLogger().info("TuffGolemGoToHomePositionTask true");
		return true;
	}

	@Override
	protected void run(
		ServerWorld serverWorld,
		TuffGolemEntity tuffGolem,
		long l
	) {
		LookTargetUtil.walkTowards(
			tuffGolem,
			new BlockPos(tuffGolem.getHomePos()),
			1.0F,
			0
		);
	}

	@Override
	protected boolean shouldKeepRunning(
		ServerWorld world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		return !tuffGolem.isAtHome()
			   && !tuffGolem.isGlued()
			   && !tuffGolem.isLeashed()
			   && !tuffGolem.hasVehicle();
	}

	@Override
	protected void keepRunning(
		ServerWorld world,
		TuffGolemEntity tuffGolem,
		long time
	) {
	}
}
