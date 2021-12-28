package com.faboslav.friendsandfoes.entity.passive.ai.goal;

import java.util.EnumSet;

import com.faboslav.friendsandfoes.entity.passive.CopperGolemEntity;
import com.faboslav.friendsandfoes.entity.passive.GlareEntity;
import org.jetbrains.annotations.Nullable;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class GlareWanderAroundGoal extends Goal
{
    private GlareEntity glare;

    public GlareWanderAroundGoal(
            GlareEntity glare
    ) {
        this.glare = glare;
        this.setControls(EnumSet.of(
                Control.MOVE,
                Control.LOOK
        ));
    }

    public boolean canStart() {
        return this.glare.getNavigation().isIdle() && this.glare.getRandom().nextInt(10) == 0;
    }

    public boolean shouldContinue() {
        return this.glare.getNavigation().isFollowingPath();
    }

    public void start() {
        Vec3d vec3d = this.getRandomLocation();
        if (vec3d != null) {
            this.glare.getNavigation().startMovingAlong(this.glare.getNavigation().findPathTo(new BlockPos(vec3d), 1), 1.0D);
        }

    }

    @Nullable
    private Vec3d getRandomLocation() {
        Vec3d vec3d2 = this.glare.getRotationVec(0.0F);
        Vec3d vec3d3 = AboveGroundTargeting.find(this.glare, 8, 7, vec3d2.x, vec3d2.z, 1.5707964F, 3, 1);
        return vec3d3 != null ? vec3d3 : NoPenaltySolidTargeting.find(this.glare, 8, 4, -2, vec3d2.x, vec3d2.z, (float)Math.PI / 2F);
    }
}