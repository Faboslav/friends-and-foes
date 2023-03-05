package com.faboslav.friendsandfoes.client.render.entity.animation.animator;

import com.faboslav.friendsandfoes.client.render.entity.animation.KeyframeAnimation;
import com.faboslav.friendsandfoes.client.render.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.client.render.entity.animation.animator.context.KeyframeAnimationContext;
import com.faboslav.friendsandfoes.client.render.entity.animation.animator.context.ModelPartAnimationContext;
import com.faboslav.friendsandfoes.client.render.entity.model.AnimatedEntityModel;
import com.faboslav.friendsandfoes.entity.animation.AnimatedEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public final class ModelAnimator
{
	public static void updateKeyframeAnimations(
		AnimatedEntity animatedEntity,
		AnimatedEntityModel animatedEntityModel,
		KeyframeAnimation keyframeAnimation,
		float animationProgress
	) {
		AnimationContextTracker animationContextTracker = animatedEntity.getAnimationContextTracker();
		Animation animation = keyframeAnimation.getAnimation();
		int currentTick = ((Entity) animatedEntity).age;

		KeyframeAnimationContext keyframeAnimationContext = animationContextTracker.get(keyframeAnimation);
		keyframeAnimationContext.setCurrentTick(currentTick);
		AnimationState animationState = keyframeAnimationContext.getAnimationState();

		animationState.update(animationProgress, 1.0F);
		animationState.run((state) -> {
			AnimationHelper.animate(animatedEntityModel, animation, state.getTimeRunning(), 1.0F, new Vec3f());
		});
	}

	public static void animateModelPartXPositionWithProgress(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetX, float
		progress
	) {
		animateModelPartPositionWithProgress(
			animatedEntity,
			modelPart,
			targetX,
			modelPart.pivotY,
			modelPart.pivotZ,
			progress
		);
	}

	public static void animateModelPartYPositionWithProgress(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetY,
		float progress
	) {
		animateModelPartPositionWithProgress(
			animatedEntity,
			modelPart,
			modelPart.pivotX,
			targetY,
			modelPart.pivotZ,
			progress
		);
	}

	public static void animateModelPartZPositionWithProgress(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetZ,
		float progress
	) {
		animateModelPartPositionWithProgress(
			animatedEntity,
			modelPart,
			modelPart.pivotX,
			modelPart.pivotY,
			targetZ,
			progress
		);
	}

	public static void animateModelPartPositionWithProgress(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetX,
		float targetY,
		float targetZ,
		float progress
	) {
		Vec3f targetVector = new Vec3f(targetX, targetY, targetZ);
		animateWithProgress(
			animatedEntity,
			modelPart,
			ModelPartAnimationType.POSITION,
			targetVector,
			progress
		);
	}

	public static void animateModelPartXRotationBasedOnProgress(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetX,
		float progress
	) {
		animateModelPartRotationBasedOnProgress(
			animatedEntity,
			modelPart,
			targetX,
			modelPart.yaw,
			modelPart.roll,
			progress
		);
	}

	public static void animateModelPartYRotationBasedOnProgress(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetY,
		float progress
	) {
		animateModelPartRotationBasedOnProgress(
			animatedEntity,
			modelPart,
			modelPart.pitch,
			targetY,
			modelPart.roll,
			progress
		);
	}

	public static void animateZRotationBasedOnProgress(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetZ,
		float progress
	) {
		animateModelPartRotationBasedOnProgress(
			animatedEntity,
			modelPart,
			targetZ,
			modelPart.yaw,
			modelPart.roll,
			progress
		);
	}

	public static void animateModelPartRotationBasedOnProgress(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetX,
		float targetY,
		float targetZ,
		float progress
	) {
		Vec3f targetVector = new Vec3f(targetX, targetY, targetZ);
		animateWithProgress(
			animatedEntity,
			modelPart,
			ModelPartAnimationType.ROTATION,
			targetVector,
			progress
		);
	}

	private static void animateWithProgress(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		ModelPartAnimationType animationType,
		Vec3f targetVector,
		float progress
	) {
		AnimationContextTracker animationContextTracker = animatedEntity.getAnimationContextTracker();
		String modelPartName = modelPart.toString();
		ModelPartAnimationContext animationContext;
		Vec3f animationCurrentVector;
		Vec3f animationTargetVector;

		if (animationContextTracker.contains(modelPartName, animationType)) {
			animationContext = animationContextTracker.get(modelPartName, animationType);
			animationCurrentVector = animationContext.getCurrentVector();
			animationTargetVector = animationContext.getTargetVector();

			if (animationTargetVector.equals(targetVector) == false) {
				animationContextTracker.remove(modelPartName, animationType);
				animationContext = ModelPartAnimationContext.createWithProgress(
					progress,
					targetVector,
					animationCurrentVector
				);
				animationContextTracker.add(modelPartName, animationType, animationContext);
			}
		} else {
			animationCurrentVector = switch (animationType) {
				case POSITION -> new Vec3f(modelPart.pivotX, modelPart.pivotY, modelPart.pivotZ);
				case ROTATION -> new Vec3f(modelPart.pitch, modelPart.yaw, modelPart.roll);
			};

			animationContext = ModelPartAnimationContext.createWithProgress(
				progress,
				targetVector,
				animationCurrentVector
			);
			animationContextTracker.add(modelPartName, animationType, animationContext);
		}

		animationContext.setProgress(progress);
		animationContext.recalculateCurrentVector();
		animationCurrentVector = animationContext.getCurrentVector();

		switch (animationType) {
			case POSITION -> ModelPartAnimator.setPosition(modelPart, animationCurrentVector);
			case ROTATION -> ModelPartAnimator.setRotation(modelPart, animationCurrentVector);
		}
	}

	public static void animateModelPartXPositionBasedOnTicks(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetX,
		int ticks
	) {
		animateModelPartModelPartPositionBasedOnTicks(
			animatedEntity,
			modelPart,
			targetX,
			modelPart.pivotY,
			modelPart.pivotZ,
			ticks
		);
	}

	public static void animateModelPartYPositionBasedOnTicks(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetY,
		int ticks
	) {
		animateModelPartModelPartPositionBasedOnTicks(
			animatedEntity,
			modelPart,
			modelPart.pivotX,
			targetY,
			modelPart.pivotZ,
			ticks
		);
	}

	public static void animateZPositionBasedOnTicks(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetX,
		int ticks
	) {
		animateModelPartModelPartPositionBasedOnTicks(
			animatedEntity,
			modelPart,
			targetX,
			modelPart.pivotY,
			modelPart.pivotZ,
			ticks
		);
	}

	public static void animateModelPartModelPartPositionBasedOnTicks(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetX,
		float targetY,
		float targetZ,
		int ticks
	) {
		Vec3f targetVector = new Vec3f(targetX, targetY, targetZ);
		animateModelPartBasedOnTicks(
			animatedEntity,
			modelPart,
			ModelPartAnimationType.POSITION,
			targetVector,
			ticks
		);
	}

	public static void animateModelPartXRotationBasedOnTicks(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetX,
		int ticks
	) {
		animateModelPartRotationBasedOnTicks(
			animatedEntity,
			modelPart,
			targetX,
			modelPart.yaw,
			modelPart.roll,
			ticks
		);
	}

	public static void animateModelPartYRotationBasedOnTicks(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetY,
		int ticks
	) {
		animateModelPartRotationBasedOnTicks(
			animatedEntity,
			modelPart,
			modelPart.pitch,
			targetY,
			modelPart.roll,
			ticks
		);
	}

	public static void animateModelPartZRotationBasedOnTicks(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetZ,
		int ticks
	) {
		animateModelPartRotationBasedOnTicks(
			animatedEntity,
			modelPart,
			modelPart.yaw,
			modelPart.roll,
			targetZ,
			ticks
		);
	}

	public static void animateModelPartRotationBasedOnTicks(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetX,
		float targetY,
		float targetZ,
		int ticks
	) {
		Vec3f targetVector = new Vec3f(targetX, targetY, targetZ);
		animateModelPartBasedOnTicks(
			animatedEntity,
			modelPart,
			ModelPartAnimationType.ROTATION,
			targetVector,
			ticks
		);
	}

	private static void animateModelPartBasedOnTicks(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		ModelPartAnimationType animationType,
		Vec3f targetVector,
		int ticks
	) {
		AnimationContextTracker animationContextTracker = animatedEntity.getAnimationContextTracker();
		int currentEntityTick = ((Entity) animatedEntity).age;

		String modelPartName = modelPart.toString();
		ModelPartAnimationContext animationContext;
		Vec3f currentVector;
		Vec3f animationTargetVector;

		if (animationContextTracker.contains(modelPartName, animationType)) {
			animationContext = animationContextTracker.get(modelPartName, animationType);
			currentVector = animationContext.getCurrentVector();

			if (currentVector.equals(targetVector)) {
				switch (animationType) {
					case POSITION -> ModelPartAnimator.setPosition(modelPart, targetVector);
					case ROTATION -> ModelPartAnimator.setRotation(modelPart, targetVector);
				}

				return;
			}

			animationTargetVector = animationContext.getTargetVector();

			if (animationTargetVector.equals(targetVector) == false) {
				animationContextTracker.remove(modelPartName, animationType);
				animationContext = ModelPartAnimationContext.createWithTicks(
					currentEntityTick,
					ticks,
					targetVector,
					currentVector
				);
				animationContextTracker.add(modelPartName, animationType, animationContext);
			}
		} else {
			currentVector = switch (animationType) {
				case POSITION -> new Vec3f(modelPart.pivotX, modelPart.pivotY, modelPart.pivotZ);
				case ROTATION -> new Vec3f(modelPart.pitch, modelPart.yaw, modelPart.roll);
			};

			animationContext = ModelPartAnimationContext.createWithTicks(
				currentEntityTick,
				ticks,
				targetVector,
				currentVector
			);
			animationContextTracker.add(modelPartName, animationType, animationContext);
		}

		animationContext.setCurrentTick(currentEntityTick);
		animationContext.recalculateProgress();
		animationContext.recalculateCurrentVector();
		currentVector = animationContext.getCurrentVector();

		switch (animationType) {
			case POSITION -> ModelPartAnimator.setPosition(modelPart, currentVector);
			case ROTATION -> ModelPartAnimator.setRotation(modelPart, currentVector);
		}
	}
}
