package com.faboslav.friendsandfoes.util;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import net.minecraft.entity.LivingEntity;

public final class ModelAnimationHelper
{
	public static float getAnimationProgress(
		LivingEntity entity,
		float tickDelta
	) {
		return (float) entity.age + tickDelta;
	}

	public static float getTickDelta() {
		return FriendsAndFoes.serverTickDeltaCounter.tickDelta;
	}

	private ModelAnimationHelper() {
	}
}
