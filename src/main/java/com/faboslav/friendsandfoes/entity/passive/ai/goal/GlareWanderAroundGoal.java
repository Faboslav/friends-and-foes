package com.faboslav.friendsandfoes.entity.passive.ai.goal;

import com.faboslav.friendsandfoes.entity.passive.GlareEntity;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

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
			   && this.glare.getRandom().nextInt(10) == 0;
	}

	@Override
	public boolean shouldContinue() {
		return this.glare.getNavigation().isFollowingPath();
	}

	@Override
	public void start() {
		Vec3d vec3d = this.getRandomLocation();
		if (vec3d != null) {
			this.glare.getNavigation().startMovingAlong(
				this.glare.getNavigation().findPathTo(
					new BlockPos(vec3d),
					1
				),
				this.glare.getMovementSpeed()
			);
		}
	}

	@Nullable
	private Vec3d getRandomLocation() {
		Vec3d vec3d3 = AboveGroundTargeting.find(this.glare, 8, 7, this.glare.getX(), this.glare.getZ(), 1.5707964F, 3, 1);
		return vec3d3 != null ? vec3d3:NoPenaltySolidTargeting.find(this.glare, 8, 4, -2, this.glare.getX(), this.glare.getZ(), (float) Math.PI / 2F);
	}
}