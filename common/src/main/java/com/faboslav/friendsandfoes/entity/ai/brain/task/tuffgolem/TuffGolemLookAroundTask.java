package com.faboslav.friendsandfoes.entity.ai.brain.task.tuffgolem;

import com.faboslav.friendsandfoes.entity.TuffGolemEntity;
import net.minecraft.entity.ai.brain.task.LookAroundTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;

public final class TuffGolemLookAroundTask extends LookAroundTask
{
	public TuffGolemLookAroundTask(int minRunTime, int maxRunTime) {
		super(minRunTime, maxRunTime);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, MobEntity entity) {
		TuffGolemEntity tuffGolem = (TuffGolemEntity) entity;

		return tuffGolem.isNotImmobilized() && super.shouldRun(world, tuffGolem);
	}
}
