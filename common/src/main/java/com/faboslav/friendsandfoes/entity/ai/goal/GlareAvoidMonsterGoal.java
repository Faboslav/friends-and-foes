package com.faboslav.friendsandfoes.entity.ai.goal;

import com.faboslav.friendsandfoes.entity.GlareEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;

public final class GlareAvoidMonsterGoal<T extends LivingEntity> extends FleeEntityGoal<T>
{
	private final GlareEntity glare;

	public GlareAvoidMonsterGoal(
		GlareEntity glare,
		Class<T> fleeFromType,
		float distance
	) {
		super(
			glare,
			fleeFromType,
			distance,
			glare.getMovementSpeed(),
			glare.getFastMovementSpeed()
		);
		this.glare = glare;
	}

	public boolean canStart() {
		return !this.glare.isTamed() && super.canStart();
	}
}
