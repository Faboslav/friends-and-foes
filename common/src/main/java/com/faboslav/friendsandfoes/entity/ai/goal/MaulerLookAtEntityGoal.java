package com.faboslav.friendsandfoes.entity.ai.goal;

import com.faboslav.friendsandfoes.entity.MaulerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.mob.MobEntity;

public final class MaulerLookAtEntityGoal extends LookAtEntityGoal
{
	public MaulerLookAtEntityGoal(MobEntity mob, Class<? extends LivingEntity> targetType, float range) {
		super(mob, targetType, range);
	}

	@Override
	public boolean canStart() {
		if (((MaulerEntity) this.mob).isBurrowedDown()) {
			return false;
		}

		return super.canStart();
	}
}
