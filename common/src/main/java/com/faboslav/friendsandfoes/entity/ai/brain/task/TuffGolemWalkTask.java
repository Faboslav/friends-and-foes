package com.faboslav.friendsandfoes.entity.ai.brain.task;

import com.faboslav.friendsandfoes.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.entity.pose.TuffGolemEntityPose;
import net.minecraft.entity.ai.brain.task.WalkTask;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;

public final class TuffGolemWalkTask extends WalkTask
{
	public TuffGolemWalkTask(float speed) {
		super(speed);
	}

	@Override
	protected boolean shouldRun(
		ServerWorld world,
		PathAwareEntity pathAwareEntity
	) {
		TuffGolemEntity tuffGolem = (TuffGolemEntity) pathAwareEntity;

		return !tuffGolem.isInSleepingPose()
			   && !tuffGolem.isGlued()
			   && !tuffGolem.isLeashed()
			   && !tuffGolem.hasVehicle();
	}

	@Override
	protected void run(
		ServerWorld serverWorld,
		PathAwareEntity pathAwareEntity,
		long l
	) {
		TuffGolemEntity tuffGolem = (TuffGolemEntity) pathAwareEntity;
		if (tuffGolem.wasInPose(TuffGolemEntityPose.SLEEPING)) {
			tuffGolem.startStanding();
		} else if (tuffGolem.wasInPose(TuffGolemEntityPose.SLEEPING_WITH_ITEM)) {
			tuffGolem.startStandingWithItem();
		}

		super.run(serverWorld, tuffGolem, l);
	}
}
