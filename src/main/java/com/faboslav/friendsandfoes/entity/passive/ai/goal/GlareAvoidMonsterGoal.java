package com.faboslav.friendsandfoes.entity.passive.ai.goal;

import com.faboslav.friendsandfoes.entity.passive.GlareEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;

public class GlareAvoidMonsterGoal<T extends LivingEntity> extends FleeEntityGoal<T>
{
	private final GlareEntity glare;

	public GlareAvoidMonsterGoal(
		GlareEntity glare,
		Class<T> fleeFromType,
		float distance,
		double slowSpeed,
		double fastSpeed
	) {
		super(glare, fleeFromType, distance, slowSpeed, fastSpeed);
		this.glare = glare;
	}

	public boolean canStart() {
		return super.canStart() && !this.glare.isTamed();
	}
}
