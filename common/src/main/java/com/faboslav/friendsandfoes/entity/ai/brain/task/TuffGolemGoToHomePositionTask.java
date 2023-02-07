package com.faboslav.friendsandfoes.entity.ai.brain.task;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.TuffGolemBrain;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class TuffGolemGoToHomePositionTask extends Task<TuffGolemEntity>
{
	private final static int GO_TO_SLEEP_POSITION_DURATION = 3600;

	public TuffGolemGoToHomePositionTask() {
		super(ImmutableMap.of(
			FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
		), GO_TO_SLEEP_POSITION_DURATION);
	}

	@Override
	protected boolean shouldRun(
		ServerWorld world,
		TuffGolemEntity tuffGolem
	) {
		if (
			tuffGolem.isGlued()
			|| tuffGolem.isLeashed()
			|| tuffGolem.isAtHome()
		) {
			return false;
		}

		FriendsAndFoes.getLogger().info("TuffGolemGoToHomePositionTask true");
		return true;
	}

	@Override
	protected boolean shouldKeepRunning(
		ServerWorld world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		return tuffGolem.isAtHomePos() == false;
	}

	@Override
	protected void keepRunning(
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

		FriendsAndFoes.getLogger().info("walking");
	}

	@Override
	protected void finishRunning(
		ServerWorld world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		FriendsAndFoes.getLogger().info("stop");
		tuffGolem.getNavigation().stop();
		tuffGolem.setSpawnYaw(tuffGolem.getHomeYaw());
	}
}
