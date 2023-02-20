package com.faboslav.friendsandfoes.entity.ai.brain.task;

import com.faboslav.friendsandfoes.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.TuffGolemBrain;
import com.faboslav.friendsandfoes.entity.pose.TuffGolemEntityPose;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

public class TuffGolemSleepTask extends Task<TuffGolemEntity>
{
	private final static int MIN_TICKS_TO_SLEEP = 400; //1200
	private final static int MAX_TICKS_TO_SLEEP = 800; //3600

	public TuffGolemSleepTask() {
		super(ImmutableMap.of(
			FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
		), MIN_TICKS_TO_SLEEP, MAX_TICKS_TO_SLEEP);
	}

	@Override
	protected boolean shouldRun(
		ServerWorld world,
		TuffGolemEntity tuffGolem
	) {
		return tuffGolem.isAtHome() != false;
	}

	@Override
	protected void run(
		ServerWorld world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		if (tuffGolem.isInPose(TuffGolemEntityPose.STANDING.get())) {
			tuffGolem.startSleeping();
		} else if (tuffGolem.isInPose(TuffGolemEntityPose.STANDING_WITH_ITEM.get())) {
			tuffGolem.startSleepingWithItem();
		}
	}

	@Override
	protected boolean shouldKeepRunning(
		ServerWorld world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		return tuffGolem.isInSleepingPose();
	}

	@Override
	protected void finishRunning(
		ServerWorld world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		TuffGolemBrain.setSleepCooldown(tuffGolem);
		tuffGolem.stopMovement();

		if (tuffGolem.isInPose(TuffGolemEntityPose.SLEEPING.get())) {
			tuffGolem.startStanding();
		} else if (tuffGolem.isInPose(TuffGolemEntityPose.SLEEPING_WITH_ITEM.get())) {
			tuffGolem.startStandingWithItem();
		}
	}
}
