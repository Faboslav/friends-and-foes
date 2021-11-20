package com.faboslav.friendsandfoes.entity.passive.ai.goal;

import com.faboslav.friendsandfoes.entity.passive.CopperGolemEntity;
import net.minecraft.entity.ai.goal.WanderAroundGoal;

public class CopperGolemWanderAroundGoal extends WanderAroundGoal
{
    protected CopperGolemEntity copperGolem;

    public CopperGolemWanderAroundGoal(CopperGolemEntity copperGolemEntity) {
        super(copperGolemEntity, 0.0F);
        this.copperGolem = copperGolemEntity;
    }

    @Override
    public void start() {
        this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.copperGolem.getCustomMovementSpeed());
    }

    @Override
    public boolean shouldContinue() {
        if (this.copperGolem.isOxidized()) {
            return false;
        }

        return super.shouldContinue();
    }
}
