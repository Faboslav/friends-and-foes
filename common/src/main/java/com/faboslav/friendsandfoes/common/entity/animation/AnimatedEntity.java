package com.faboslav.friendsandfoes.common.entity.animation;

import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.KeyframeAnimationContext;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public interface AnimatedEntity
{
	AnimationContextTracker getAnimationContextTracker();

	ArrayList<AnimationHolder> getTrackedAnimations();

	AnimationHolder getMovementAnimation();

	int getCurrentAnimationTick();

	void setCurrentAnimationTick(int currentAnimationTick);

	@Nullable
	AnimationHolder getAnimationByPose();

	default int getCurrentKeyframeAnimationTick() {
		AnimationHolder animationHolder = this.getAnimationByPose();

		if (animationHolder == null) {
			return 0;
		}

		int totalAnimationTicks = animationHolder.get().lengthInTicks(this.getAnimationSpeedModifier());
		int remainingAnimationTicks = this.getCurrentAnimationTick();

		return totalAnimationTicks - remainingAnimationTicks;
	}

	default float getAnimationSpeedModifier() {
		return 1.0F;
	}

	default void updateCurrentAnimationTick() {
		if (!this.isAnyKeyframeAnimationRunning()) {
			return;
		}

		this.setCurrentAnimationTick(this.getCurrentAnimationTick() - 1);

		if (this.getCurrentAnimationTick() > 1) {
			return;
		}

		for (AnimationHolder animationHolder : this.getTrackedAnimations()) {
			if (!animationHolder.get().looping()) {
				continue;
			}

			var keyframeAnimationContext = this.getAnimationContextTracker().get(animationHolder);

			if (!keyframeAnimationContext.isRunning()) {
				continue;
			}

			this.setCurrentAnimationTick(animationHolder.get().lengthInTicks(this.getAnimationSpeedModifier()));
		}
	}

	default boolean isAnyKeyframeAnimationRunning() {
		return this.getCurrentAnimationTick() > 0;
	}

	default boolean isKeyframeAnimationAtLastKeyframe(AnimationHolder animationHolder) {
		return this.getAnimationContextTracker().get(animationHolder).isAtLastKeyframe(animationHolder.get().lengthInTicks(this.getAnimationSpeedModifier()));
	}

	default boolean isKeyframeAnimationRunning(AnimationHolder animationHolder) {
		return this.getAnimationContextTracker().get(animationHolder).isRunning();
	}

	default void startKeyframeAnimation(AnimationHolder animationHolder, int initialTick) {
		KeyframeAnimationContext keyframeAnimationContext = this.getAnimationContextTracker().get(animationHolder);
		keyframeAnimationContext.setInitialTick(initialTick);
		keyframeAnimationContext.getAnimationState().start(initialTick);
	}

	default void forceStartKeyframeAnimation(AnimationHolder animationHolder, int initialTick) {
		KeyframeAnimationContext keyframeAnimationContext = this.getAnimationContextTracker().get(animationHolder);
		keyframeAnimationContext.setInitialTick(initialTick);
		keyframeAnimationContext.getAnimationState().start(initialTick);
	}

	default void stopRunningAnimations() {
		for (AnimationHolder animationHolder : this.getTrackedAnimations()) {
			if (!this.getAnimationContextTracker().get(animationHolder).isRunning()) {
				this.stopKeyframeAnimation(animationHolder);
			}
		}
	}

	default void stopKeyframeAnimation(AnimationHolder animationHolder) {
		KeyframeAnimationContext keyframeAnimationContext = this.getAnimationContextTracker().get(animationHolder);
		keyframeAnimationContext.setInitialTick(0);
		keyframeAnimationContext.setCurrentTick(0);
		keyframeAnimationContext.getAnimationState().stop();
	}
}
