package com.faboslav.friendsandfoes.common.entity.ai.brain.task.tuffgolem;

import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.WaitTask;
import net.minecraft.server.world.ServerWorld;

public final class TuffGolemWaitTask extends WaitTask
{
	public TuffGolemWaitTask(int minRunTime, int maxRunTime) {
		super(minRunTime, maxRunTime);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
		TuffGolemEntity tuffGolem = (TuffGolemEntity) entity;

		return tuffGolem.isNotImmobilized() && super.shouldRun(world, tuffGolem);
	}
}
