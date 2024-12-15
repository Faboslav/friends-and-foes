package com.faboslav.friendsandfoes.common.entity.ai.goal.mauler;

import com.faboslav.friendsandfoes.common.entity.MaulerEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;

public final class MaulerLookAtEntityGoal extends LookAtPlayerGoal
{
	public MaulerLookAtEntityGoal(Mob mob, Class<? extends LivingEntity> targetType, float range) {
		super(mob, targetType, range);
	}

	@Override
	public boolean canUse() {
		if (((MaulerEntity) this.mob).isBurrowedDown()) {
			return false;
		}

		return super.canUse();
	}
}
