package com.faboslav.friendsandfoes.entity.ai.goal;

import com.faboslav.friendsandfoes.entity.CopperGolemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.mob.MobEntity;

public final class CopperGolemLookAtEntityGoal extends LookAtEntityGoal
{
	public CopperGolemLookAtEntityGoal(
		MobEntity mobEntity,
		Class<? extends LivingEntity> class_,
		float f
	) {
		super(mobEntity, class_, f);
	}

	@Override
	public boolean shouldContinue() {
		if (((CopperGolemEntity) this.mob).isOxidized()) {
			return false;
		}

		return super.shouldContinue();
	}
}
