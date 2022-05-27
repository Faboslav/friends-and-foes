package com.faboslav.friendsandfoes.entity.ai.goal;

import com.faboslav.friendsandfoes.entity.CopperGolemEntity;
import net.minecraft.entity.ai.goal.WanderAroundGoal;

public final class CopperGolemWanderAroundGoal extends WanderAroundGoal
{
	private final CopperGolemEntity copperGolem;

	public CopperGolemWanderAroundGoal(CopperGolemEntity copperGolemEntity) {
		super(copperGolemEntity, 0.0F);
		this.copperGolem = copperGolemEntity;
	}

	@Override
	public void start() {
		this.mob.getNavigation().startMovingTo(
			this.targetX,
			this.targetY,
			this.targetZ,
			this.copperGolem.getMovementSpeed()
		);
	}

	@Override
	public boolean shouldContinue() {
		if (this.copperGolem.isOxidized()) {
			return false;
		}

		return super.shouldContinue();
	}
}
