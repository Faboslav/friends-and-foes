package com.faboslav.friendsandfoes.entity.ai.goal;

import com.faboslav.friendsandfoes.entity.CopperGolemEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;

import java.util.function.Predicate;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class CopperGolemFleeEntityGoal extends FleeEntityGoal
{
	public CopperGolemFleeEntityGoal(
		PathAwareEntity fleeingEntity,
		Class classToFleeFrom,
		float fleeDistance,
		double fleeSlowSpeed,
		double fleeFastSpeed,
		Predicate inclusionSelector
	) {
		super(fleeingEntity, classToFleeFrom, fleeDistance, fleeSlowSpeed, fleeFastSpeed, inclusionSelector);
	}

	@Override
	public boolean canStart() {
		if (((CopperGolemEntity) this.mob).isOxidized()) {
			return false;
		}

		TargetPredicate withinRangePredicate = TargetPredicate.createNonAttackable().setBaseMaxDistance(this.fleeDistance).setPredicate(inclusionSelector.and(extraInclusionSelector));
		this.targetEntity = this.mob.world.getClosestEntity(this.mob.world.getEntitiesByClass(this.classToFleeFrom, this.mob.getBoundingBox().expand(this.fleeDistance, 3.0D, this.fleeDistance), (livingEntity) -> {
			return true;
		}), withinRangePredicate, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ());
		if (this.targetEntity == null) {
			return false;
		} else {
			Vec3d vec3d = NoPenaltyTargeting.findFrom(this.mob, 16, 7, this.targetEntity.getPos());
			if (vec3d == null) {
				return false;
			} else if (this.targetEntity.squaredDistanceTo(vec3d.x, vec3d.y, vec3d.z) < this.targetEntity.squaredDistanceTo(this.mob)) {
				return false;
			} else {
				this.fleePath = this.fleeingEntityNavigation.findPathTo(vec3d.x, vec3d.y, vec3d.z, 0);
				return this.fleePath != null;
			}
		}
	}

	@Override
	public boolean shouldContinue() {
		if (((CopperGolemEntity) this.mob).isOxidized()) {
			return false;
		}

		return super.shouldContinue();
	}

	@Override
	public void tick() {
		this.mob.getNavigation().setSpeed(this.mob.getMovementSpeed());
	}
}
