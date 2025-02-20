package com.faboslav.friendsandfoes.common.client.render.entity.model.animation;

import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.KeyframeAnimator;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.KeyframeAnimationContext;
import com.faboslav.friendsandfoes.common.entity.animation.AnimationDefinition;
import com.faboslav.friendsandfoes.common.entity.animation.AnimationState;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Vector3f;

import java.util.*;

//? >=1.21.3 {
import net.minecraft.client.model.EntityModel;
//?} else {
/*import net.minecraft.client.model.HierarchicalModel;
*///?}

@Environment(EnvType.CLIENT)
public final class KeyframeModelAnimator
{
	private static final Vector3f TEMP = new Vector3f();

	public static void updateKeyframeAnimations(
		//? >=1.21.3 {
		EntityModel<?> model,
		//?} else {
		/*HierarchicalModel<?> model,
		*///?}
		AnimationContextTracker animationContextTracker,
		ArrayList<AnimationHolder> animations,
		int currentTick,
		float animationProgress,
		float speedModifier
	) {
		animations.forEach((keyframeAnimation -> {
			updateKeyframeAnimation(model, animationContextTracker, keyframeAnimation, currentTick, animationProgress, speedModifier);
		}));
	}

	public static void updateMovementKeyframeAnimations(
		//? >=1.21.3 {
		EntityModel<?> model,
		//?} else {
		/*HierarchicalModel<?> model,
		 *///?}
		AnimationHolder movementAnimation,
		float limbAngle,
		float limbDistance,
		float limbAngleScale,
		float limbDistanceScale,
		float speedModifier
	) {
		long runningTime = (long) (limbAngle * 50.0F * limbAngleScale);
		float scale = Math.min(limbDistance * limbDistanceScale, 1.0F);
		updateMovementKeyframeAnimation(model, movementAnimation, runningTime, scale, speedModifier);
	}

	public static void updateStaticKeyframeAnimation(
		//? >=1.21.3 {
		EntityModel<?> model,
		//?} else {
		/*HierarchicalModel<?> model,
		 *///?}
		AnimationContextTracker animationContextTracker,
		AnimationHolder animationHolder,
		int currentTick,
		float animationProgress,
		float speedModifier
	) {
		var animation = animationHolder.get();

		KeyframeAnimationContext keyframeAnimationContext = animationContextTracker.get(animationHolder);
		keyframeAnimationContext.setCurrentTick(currentTick);
		AnimationState animationState = keyframeAnimationContext.getAnimationState();

		if(!animationState.isStarted()) {
			animationState.start(currentTick);
		}

		animationState.updateTime(animationProgress, 1.0F);
		KeyframeAnimator.animateKeyframe(model, animation, animationState.getAccumulatedTime(), 1.0F, TEMP, speedModifier);
	}

	public static void updateKeyframeAnimation(
		//? >=1.21.3 {
		EntityModel<?> model,
		//?} else {
		/*HierarchicalModel<?> model,
		 *///?}
		AnimationContextTracker animationContextTracker,
		AnimationHolder animationHolder,
		int currentTick,
		float animationProgress,
		float speedModifier
	) {
		var animation = animationHolder.get();

		KeyframeAnimationContext keyframeAnimationContext = animationContextTracker.get(animationHolder);
		keyframeAnimationContext.setCurrentTick(currentTick);
		AnimationState animationState = keyframeAnimationContext.getAnimationState();

		animationState.ifStarted((state) -> {
			state.updateTime(animationProgress, 1.0F);
			KeyframeAnimator.animateKeyframe(model, animation, state.getAccumulatedTime(), 1.0F, TEMP, speedModifier);
		});
	}

	public static void updateMovementKeyframeAnimation(
		//? >=1.21.3 {
		EntityModel<?> model,
		//?} else {
		/*HierarchicalModel<?> model,
		 *///?}
		AnimationHolder movementAnimation,
		long runningTime,
		float scale,
		float speedModifier
	) {
		AnimationDefinition animation = movementAnimation.get();
		KeyframeAnimator.animateKeyframe(model, animation, runningTime, scale, TEMP, speedModifier);
	}
}