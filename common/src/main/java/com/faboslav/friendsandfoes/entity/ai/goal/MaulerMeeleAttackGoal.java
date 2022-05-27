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
	public void start() {
		MaulerEntity mauler = ((MaulerEntity) this.mob);

		if (mauler.isBurrowedDown()) {
			mauler.burrowDownGoal.stop();
		}

		super.start();
	}

	@Override
	public void tick() {
		MaulerEntity mauler = ((MaulerEntity) this.mob);

		if (mauler.getBurrowingDownAnimationProgress() > 0.0F) {
			return;
		}

		super.tick();
	}
}
