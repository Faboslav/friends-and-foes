package com.faboslav.friendsandfoes.common.entity.ai.brain.task.tuffgolem;

import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;

public final class TuffGolemLookAroundTask extends LookAtTargetSink
{
	public TuffGolemLookAroundTask(int minRunTime, int maxRunTime) {
		super(minRunTime, maxRunTime);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, Mob entity) {
		TuffGolemEntity tuffGolem = (TuffGolemEntity) entity;

		return tuffGolem.isNotImmobilized() && super.checkExtraStartConditions(world, tuffGolem);
	}
}
