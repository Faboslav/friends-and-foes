package com.faboslav.friendsandfoes.entity.passive.ai.goal;

import com.faboslav.friendsandfoes.entity.passive.CopperGolemEntity;
import com.faboslav.friendsandfoes.init.ModSounds;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.entity.ai.goal.Goal;

public class CopperGolemSpinHeadGoal extends Goal
{
	private final CopperGolemEntity copperGolem;
	private int spinHeadProgress;

	public CopperGolemSpinHeadGoal(CopperGolemEntity copperGolem) {
		this.copperGolem = copperGolem;
	}

	@Override
	public boolean canStart() {
		if (this.copperGolem.isOxidized()) {
			return false;
		} else if (this.copperGolem.getNavigation().isFollowingPath()) {
			return false;
		} else if (this.copperGolem.isPressingButton()) {
			return false;
		}

		return this.copperGolem.getTicksUntilNextHeadSpin() == 0;
	}

	@Override
	public boolean shouldContinue() {
		if (this.copperGolem.isOxidized()) {
			return false;
		}

		return this.spinHeadProgress <= 20;
	}

	public void start() {
		this.spinHeadProgress = 0;
		this.copperGolem.setIsSpinningHead(true);
		this.copperGolem.playSound(ModSounds.ENTITY_COPPER_GOLEM_HEAD_SPIN, 1.0F, copperGolem.getSoundPitch() - 1.5F);
	}

	@Override
	public void stop() {
		this.copperGolem.setIsSpinningHead(false);
		this.copperGolem.setTicksUntilNextHeadSpin(
			RandomGenerator.generateInt(
				CopperGolemEntity.MIN_TICKS_UNTIL_NEXT_HEAD_SPIN,
				CopperGolemEntity.MAX_TICKS_UNTIL_NEXT_HEAD_SPIN
			)
		);
	}

	@Override
	public void tick() {
		this.spinHeadProgress++;
	}
}