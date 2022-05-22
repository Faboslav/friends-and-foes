package com.faboslav.friendsandfoes.entity.ai.goal;

import com.faboslav.friendsandfoes.entity.MaulerEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public final class MaulerMeeleAttackGoal extends MeleeAttackGoal
{
	public MaulerMeeleAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
		super(mob, speed, pauseWhenMobIdle);
	}

	@Override
	public boolean canStart() {
		if (((MaulerEntity)this.mob).isBurrowedDown()) {
			return false;
		}

		return super.canStart();
	}
}
