package com.faboslav.friendsandfoes.common.entity.ai.brain.task.tuffgolem;

import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import net.minecraft.entity.ai.brain.task.MoveToTargetTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;

public final class TuffGolemWanderAroundTask extends MoveToTargetTask
{
	@Override
	protected boolean shouldRun(ServerWorld world, MobEntity entity) {
		TuffGolemEntity tuffGolem = (TuffGolemEntity) entity;

		return tuffGolem.isNotImmobilized() && super.shouldRun(world, tuffGolem);
	}
}
