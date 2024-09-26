package com.faboslav.friendsandfoes.common.entity.ai.goal.mauler;

import com.faboslav.friendsandfoes.common.entity.MaulerEntity;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public final class MaulerWanderAroundFarGoal extends WanderAroundFarGoal
{
	public MaulerWanderAroundFarGoal(PathAwareEntity pathAwareEntity, double d) {
		super(pathAwareEntity, d);
	}

	@Override
	public boolean canStart() {
		if (((MaulerEntity) this.mob).isBurrowedDown()) {
			return false;
		}

		return super.canStart();
	}
}
