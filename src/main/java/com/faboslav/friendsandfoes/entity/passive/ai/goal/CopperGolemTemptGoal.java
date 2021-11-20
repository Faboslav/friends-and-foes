package com.faboslav.friendsandfoes.entity.passive.ai.goal;

import com.faboslav.friendsandfoes.entity.passive.CopperGolemEntity;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.recipe.Ingredient;

public class CopperGolemTemptGoal extends TemptGoal
{
    private final CopperGolemEntity copperGolem;

    public CopperGolemTemptGoal(
            CopperGolemEntity copperGolemEntity,
            Ingredient ingredient
    ) {
        super(copperGolemEntity, 0.0F, ingredient, false);
        this.copperGolem = copperGolemEntity;
    }

    @Override
    public void tick() {
        this.mob.getLookControl().lookAt(this.closestPlayer, (float)(this.mob.getMaxHeadRotation() + 20), (float)this.mob.getMaxLookPitchChange());
        if (this.mob.squaredDistanceTo(this.closestPlayer) < 6.25D) {
            this.mob.getNavigation().stop();
        } else {
            this.mob.getNavigation().startMovingTo(this.closestPlayer, this.copperGolem.getCustomMovementSpeed());
        }
    }

    @Override
    public boolean shouldContinue() {
        if (this.copperGolem.isOxidized()) {
            return false;
        }

        return super.shouldContinue();
    }
}
