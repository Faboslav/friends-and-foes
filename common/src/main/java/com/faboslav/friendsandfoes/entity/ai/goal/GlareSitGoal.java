package com.faboslav.friendsandfoes.entity.ai.goal;

import com.faboslav.friendsandfoes.entity.GlareEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public final class GlareSitGoal extends Goal
{
	private final GlareEntity glare;

	public GlareSitGoal(GlareEntity glare) {
		this.glare = glare;
		this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
	}

	@Override
	public void start() {
		this.glare.getNavigation().stop();
		this.glare.setSitting(true);
	}

	@Override
	public boolean canStart() {
		return this.glare.isTamed() != false
			   && this.glare.isLeashed() != true
			   && this.glare.isInsideWaterOrBubbleColumn() != true
			   && this.glare.isSitting() != false;
	}

	@Override
	public boolean shouldContinue() {
		return this.glare.isSitting();
	}

	@Override
	public void tick() {
		this.glare.setVelocity(0, -0.01, 0);
	}

	@Override
	public void stop() {
		this.glare.setSitting(false);
	}
}

