package com.faboslav.friendsandfoes.entity.animation;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.animation.KeyframeAnimation;
import com.faboslav.friendsandfoes.client.render.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.client.render.entity.animation.animator.context.KeyframeAnimationContext;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public interface AnimatedEntity
{
	AnimationContextTracker getAnimationContextTracker();

	/**
	 * This is temporarily empty array until all the mobs have keyframe animations
	 */
	default ArrayList<KeyframeAnimation> getAnimations() {
		return new ArrayList<>();
	}

	/**
	 * This is temporarily nullable until all the mobs have keyframe animations
	 */
	@Nullable
	default KeyframeAnimation getMovementAnimation() {
		return null;
	}

	/**
	 * This is temporarily 0 until all the mobs have keyframe animations
	 */
	default int getKeyframeAnimationTicks() {
		return 0;
	}

	default void setKeyframeAnimationTicks(int keyframeAnimationTicks) {
	}

	default boolean isAnyKeyframeAnimationRunning() {
		return this.getKeyframeAnimationTicks() > 0;
	}

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

	default void forceStartKeyframeAnimation(KeyframeAnimation keyframeAnimation, int initialTick) {
		KeyframeAnimationContext keyframeAnimationContext = this.getAnimationContextTracker().get(keyframeAnimation);
		keyframeAnimationContext.setInitialTick(initialTick);
		keyframeAnimationContext.getAnimationState().start(initialTick);
	}

	default void stopRunningKeyframeAnimations() {
		for (KeyframeAnimation keyframeAnimation : this.getAnimations()) {
			if (this.getAnimationContextTracker().get(keyframeAnimation).isRunning() == false) {
				this.stopKeyframeAnimation(keyframeAnimation);
			}
		}
	}

	default void stopKeyframeAnimation(KeyframeAnimation keyframeAnimation) {
		KeyframeAnimationContext keyframeAnimationContext = this.getAnimationContextTracker().get(keyframeAnimation);
		keyframeAnimationContext.setInitialTick(0);
		keyframeAnimationContext.setCurrentTick(0);
		keyframeAnimationContext.getAnimationState().stop();
	}
}
