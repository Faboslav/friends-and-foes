package com.faboslav.friendsandfoes.entity.passive.ai.goal;

import com.faboslav.friendsandfoes.entity.passive.GlareEntity;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public final class GlareWanderAroundGoal extends Goal
{
	private final GlareEntity glare;

	public GlareWanderAroundGoal(
		GlareEntity glare
	) {
		this.glare = glare;
		this.setControls(EnumSet.of(
			Control.MOVE
		));
	}

	@Override
	public boolean canStart() {
		return this.glare.getNavigation().isIdle()
			   && (
				   this.glare.isTamed() == false
				   || this.glare.isLeashed()
			   )
			   && this.glare.getRandom().nextInt(10) == 0;
	}

	@Override
	public boolean shouldContinue() {
		return this.glare.getNavigation().isFollowingPath();
	}

	@Override
	public void start() {
		Vec3d randomLocation = this.getRandomLocation();

		if (randomLocation == null) {
			return;
		}

		var path = this.glare.getNavigation().findPathTo(
			randomLocation.getX(),
			randomLocation.getY(),
			randomLocation.getZ(),
			0
		);

		this.glare.getNavigation().startMovingAlong(
			path,
			this.glare.getMovementSpeed()
		);
	}

	private Vec3d getRandomLocation() {
		var isAboveSurfaceLevel = this.glare.getBlockPos().getY() >= 63;
		Vec3d rotationVec = this.glare.getRotationVec(0.0F);
		Vec3d positionVec;

		if (isAboveSurfaceLevel) {
			positionVec = AboveGroundTargeting.find(this.glare, 8, 4, rotationVec.getX(), rotationVec.getZ(), 1.5707964F, 1, 1);

			if (positionVec == null) {
				positionVec = NoPenaltySolidTargeting.find(this.glare, 8, 4, -2, rotationVec.getX(), rotationVec.getZ(), 1.5707963705062866D);
			}
		} else {
			positionVec = FuzzyTargeting.find(this.glare, 8, 4);
		}

		return positionVec;
	}
}