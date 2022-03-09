package com.faboslav.friendsandfoes.util;

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
		// TODO resolve server stuff
		return 0.0f;
		//return FriendsAndFoesServer.serverTickDeltaCounter.tickDelta;
	}
}
