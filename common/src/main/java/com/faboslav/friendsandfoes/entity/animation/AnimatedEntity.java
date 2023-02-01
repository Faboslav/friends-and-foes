package com.faboslav.friendsandfoes.entity.animation;

import com.faboslav.friendsandfoes.client.render.entity.animation.KeyframeAnimation;
import com.faboslav.friendsandfoes.client.render.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.client.render.entity.animation.animator.context.KeyframeAnimationContext;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface AnimatedEntity
{
	AnimationContextTracker getAnimationContextTracker();

	default boolean isKeyframeAnimationAtLastKeyframe(KeyframeAnimation keyframeAnimation) {
		return this.getAnimationContextTracker().get(keyframeAnimation).isAtLastKeyframe();
	}

	default boolean isKeyframeAnimationRunning(KeyframeAnimation keyframeAnimation) {
		return this.getAnimationContextTracker().get(keyframeAnimation).isRunning();
	}

	default void startKeyframeAnimation(KeyframeAnimation keyframeAnimation, int initialTick) {
		KeyframeAnimationContext keyframeAnimationContext = this.getAnimationContextTracker().get(keyframeAnimation);
		keyframeAnimationContext.setInitialTick(initialTick);
		keyframeAnimationContext.getAnimationState().startIfNotRunning(initialTick);
	}

	default void stopKeyframeAnimation(KeyframeAnimation keyframeAnimation) {
		KeyframeAnimationContext keyframeAnimationContext = this.getAnimationContextTracker().get(keyframeAnimation);
		keyframeAnimationContext.setInitialTick(0);
		keyframeAnimationContext.getAnimationState().stop();
	}
}
