package com.faboslav.friendsandfoes.common.entity.ai.brain.task.tuffgolem;

import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;

public final class TuffGolemWanderAroundTask extends MoveToTargetSink
{
	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, Mob entity) {
		TuffGolemEntity tuffGolem = (TuffGolemEntity) entity;

		return tuffGolem.isNotImmobilized() && super.checkExtraStartConditions(world, tuffGolem);
	}
}
