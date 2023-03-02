package com.faboslav.friendsandfoes.entity.ai.brain.task.tuffgolem;

import com.faboslav.friendsandfoes.entity.TuffGolemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.WaitTask;
import net.minecraft.server.world.ServerWorld;

public class TuffGolemWaitTask extends WaitTask
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
