package com.faboslav.friendsandfoes.common.entity.ai.brain.task.tuffgolem;

import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.GoTowardsLookTarget;
import net.minecraft.server.world.ServerWorld;

public final class TuffGolemGoTowardsLookTarget extends GoTowardsLookTarget
{
	public TuffGolemGoTowardsLookTarget(float speed, int completionRange) {
		super(speed, completionRange);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
		TuffGolemEntity tuffGolem = (TuffGolemEntity) entity;

		return tuffGolem.isNotImmobilized() && super.shouldRun(world, tuffGolem);
	}
}
