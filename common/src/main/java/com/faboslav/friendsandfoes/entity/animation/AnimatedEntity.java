package com.faboslav.friendsandfoes.entity.animation;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.animation.KeyframeAnimation;
import com.faboslav.friendsandfoes.client.render.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.client.render.entity.animation.animator.context.KeyframeAnimationContext;

public interface AnimatedEntity
{
	AnimationContextTracker getAnimationContextTracker();

	default boolean isKeyframeAnimationAtLastKeyframe(KeyframeAnimation keyframeAnimation) {
		FriendsAndFoes.getLogger().info(keyframeAnimation.getName());
		return this.getAnimationContextTracker().get(keyframeAnimation.getName()).isAtLastKeyframe();
	}

	default boolean isKeyframeAnimationRunning(KeyframeAnimation keyframeAnimation) {
		return this.getAnimationContextTracker().get(keyframeAnimation.getName()).isRunning();
	}

	default void startKeyframeAnimation(KeyframeAnimation keyframeAnimation, int initialTick) {
		FriendsAndFoes.getLogger().info("starting");
		KeyframeAnimationContext keyframeAnimationContext = this.getAnimationContextTracker().get(keyframeAnimation.getName());
		keyframeAnimationContext.setInitialTick(initialTick);
		keyframeAnimationContext.getAnimationState().startIfNotRunning(initialTick);
	}

	default void stopKeyframeAnimation(KeyframeAnimation keyframeAnimation) {
		FriendsAndFoes.getLogger().info("stoping");
		KeyframeAnimationContext keyframeAnimationContext = this.getAnimationContextTracker().get(keyframeAnimation.getName());
		keyframeAnimationContext.setInitialTick(0);
		keyframeAnimationContext.getAnimationState().stop();
	}
}
