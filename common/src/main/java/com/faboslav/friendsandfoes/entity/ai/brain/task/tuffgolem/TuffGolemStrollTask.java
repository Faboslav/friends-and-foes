package com.faboslav.friendsandfoes.entity.ai.brain.task.tuffgolem;

import com.faboslav.friendsandfoes.entity.TuffGolemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.StrollTask;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;

public final class TuffGolemStrollTask<E extends LivingEntity> extends StrollTask
{
	public TuffGolemStrollTask(float speed) {
		super(speed);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, PathAwareEntity entity) {
		TuffGolemEntity tuffGolem = (TuffGolemEntity) entity;

		return tuffGolem.isNotImmobilized() && super.shouldRun(world, tuffGolem);
	}
}
