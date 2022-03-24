package com.faboslav.friendsandfoes.entity.passive.ai.goal;

import com.faboslav.friendsandfoes.entity.passive.GlareEntity;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class GlareWanderAroundGoal extends Goal
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
		Vec3d vec3d = this.glare.getRotationVec(0.0F);
		Vec3d vec3d2 = AboveGroundTargeting.find(this.glare, 8, 7, vec3d.getX(), vec3d.getZ(), 1.5707964F, 1, 1);

		if (vec3d2 != null) {
			return vec3d2;
		}

		return NoPenaltySolidTargeting.find(this.glare, 8, 4, -2, vec3d.getX(), vec3d.getZ(), 1.5707963705062866D);
	}
}