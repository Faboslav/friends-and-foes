package com.faboslav.friendsandfoes.util;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import net.minecraft.entity.LivingEntity;

public class ModelAnimationHelper
{
    public static float getAnimationProgress(
            LivingEntity entity,
            float tickDelta
    ) {
        return (float) entity.age + getTickDelta();
    }

    public static float getTickDelta() {
        return FriendsAndFoes.serverTickDeltaCounter.tickDelta;
    }
}
