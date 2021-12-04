package com.faboslav.friendsandfoes.util;

import com.faboslav.friendsandfoes.FriendsAndFoesServer;
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
        return FriendsAndFoesServer.serverTickDeltaCounter.tickDelta;
    }
}
