package com.faboslav.friendsandfoes.common.client.render.entity.model.animation;

import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.ModelPartAnimationType;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.ModelPartAnimator;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.context.ModelPartAnimationContext;
import org.joml.Vector3f;

import net.minecraft.client.model.geom.ModelPart;

public final class ModelPartModelAnimator
{
	public static void animateModelPartXPositionWithProgress(
		AnimationContextTracker animationContextTracker,
		ModelPart modelPart,
		float targetX, float
		progress
	) {
		animateModelPartPositionWithProgress(
			animationContextTracker,
			modelPart,
			targetX,
			modelPart.y,
			modelPart.z,
			progress
		);
	}

	public static void animateModelPartYPositionWithProgress(
		AnimationContextTracker animationContextTracker,
		ModelPart modelPart,
		float targetY,
		float progress
	) {
		animateModelPartPositionWithProgress(
			animationContextTracker,
			modelPart,
			modelPart.x,
			targetY,
			modelPart.z,
			progress
		);
	}

	public static void animateModelPartZPositionWithProgress(
		AnimationContextTracker animationContextTracker,
		ModelPart modelPart,
		float targetZ,
		float progress
	) {
		animateModelPartPositionWithProgress(
			animationContextTracker,
			modelPart,
			modelPart.x,
			modelPart.y,
			targetZ,
			progress
		);
	}

	public static void animateModelPartPositionWithProgress(
		AnimationContextTracker animationContextTracker,
		ModelPart modelPart,
		float targetX,
		float targetY,
		float targetZ,
		float progress
	) {
		Vector3f targetVector = new Vector3f(targetX, targetY, targetZ);
		animateWithProgress(
			animationContextTracker,
			modelPart,
			ModelPartAnimationType.POSITION,
			targetVector,
			progress
		);
	}

	public static void animateModelPartXRotationBasedOnProgress(
		AnimationContextTracker animationContextTracker,
		ModelPart modelPart,
		float targetX,
		float progress
	) {
		animateModelPartRotationBasedOnProgress(
			animationContextTracker,
			modelPart,
			targetX,
			modelPart.yRot,
			modelPart.zRot,
			progress
		);
	}

	public static void animateModelPartYRotationBasedOnProgress(
		AnimationContextTracker animationContextTracker,
		ModelPart modelPart,
		float targetY,
		float progress
	) {
		animateModelPartRotationBasedOnProgress(
			animationContextTracker,
			modelPart,
			modelPart.xRot,
			targetY,
			modelPart.zRot,
			progress
		);
	}

	public static void animateZRotationBasedOnProgress(
		AnimationContextTracker animationContextTracker,
		ModelPart modelPart,
		float targetZ,
		float progress
	) {
		animateModelPartRotationBasedOnProgress(
			animationContextTracker,
			modelPart,
			targetZ,
			modelPart.yRot,
			modelPart.zRot,
			progress
		);
	}

	public static void animateModelPartRotationBasedOnProgress(
		AnimationContextTracker animationContextTracker,
		ModelPart modelPart,
		float targetX,
		float targetY,
		float targetZ,
		float progress
	) {
		Vector3f targetVector = new Vector3f(targetX, targetY, targetZ);
		animateWithProgress(
			animationContextTracker,
			modelPart,
			ModelPartAnimationType.ROTATION,
			targetVector,
			progress
		);
	}

	private static void animateWithProgress(
		AnimationContextTracker animationContextTracker,
		ModelPart modelPart,
		ModelPartAnimationType animationType,
		Vector3f targetVector,
		float progress
	) {
		String modelPartName = modelPart.toString();
		ModelPartAnimationContext animationContext;
		Vector3f animationCurrentVector;
		Vector3f animationTargetVector;

		if (animationContextTracker.contains(modelPartName, animationType)) {
			animationContext = animationContextTracker.get(modelPartName, animationType);
			animationCurrentVector = animationContext.getCurrentVector();
			animationTargetVector = animationContext.getTargetVector();

			if (!animationTargetVector.equals(targetVector)) {
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
				case POSITION -> new Vector3f(modelPart.x, modelPart.y, modelPart.z);
				case ROTATION -> new Vector3f(modelPart.xRot, modelPart.yRot, modelPart.zRot);
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
		AnimationContextTracker animationContextTracker,
		ModelPart modelPart,
		int currentEntityTick,
		float targetX,
		int ticks
	) {
		animateModelPartPositionBasedOnTicks(
			animationContextTracker,
			modelPart,
			currentEntityTick,
			targetX,
			modelPart.y,
			modelPart.z,
			ticks
		);
	}

	public static void animateModelPartYPositionBasedOnTicks(
		AnimationContextTracker animationContextTracker,
		ModelPart modelPart,
		int currentEntityTick,
		float targetY,
		int ticks
	) {
		animateModelPartPositionBasedOnTicks(
			animationContextTracker,
			modelPart,
			currentEntityTick,
			modelPart.x,
			targetY,
			modelPart.z,
			ticks
		);
	}

	public static void animateZPositionBasedOnTicks(
		AnimationContextTracker animationContextTracker,
		ModelPart modelPart,
		int currentEntityTick,
		float targetX,
		int ticks
	) {
		animateModelPartPositionBasedOnTicks(
			animationContextTracker,
			modelPart,
			currentEntityTick,
			targetX,
			modelPart.y,
			modelPart.z,
			ticks
		);
	}

	public static void animateModelPartPositionBasedOnTicks(
		AnimationContextTracker animationContextTracker,
		ModelPart modelPart,
		int currentEntityTick,
		float targetX,
		float targetY,
		float targetZ,
		int ticks
	) {
		Vector3f targetVector = new Vector3f(targetX, targetY, targetZ);
		animateModelPartBasedOnTicks(
			animationContextTracker,
			modelPart,
			currentEntityTick,
			ModelPartAnimationType.POSITION,
			targetVector,
			ticks
		);
	}

	public static void animateModelPartXRotationBasedOnTicks(
		AnimationContextTracker animationContextTracker,
		ModelPart modelPart,
		int currentEntityTick,
		float targetX,
		int ticks
	) {
		animateModelPartRotationBasedOnTicks(
			animationContextTracker,
			modelPart,
			currentEntityTick,
			targetX,
			modelPart.yRot,
			modelPart.zRot,
			ticks
		);
	}

	public static void animateModelPartYRotationBasedOnTicks(
		AnimationContextTracker animationContextTracker,
		ModelPart modelPart,
		int currentEntityTick,
		float targetY,
		int ticks
	) {
		animateModelPartRotationBasedOnTicks(
			animationContextTracker,
			modelPart,
			currentEntityTick,
			modelPart.xRot,
			targetY,
			modelPart.zRot,
			ticks
		);
	}

	public static void animateModelPartZRotationBasedOnTicks(
		AnimationContextTracker animationContextTracker,
		ModelPart modelPart,
		int currentEntityTick,
		float targetZ,
		int ticks
	) {
		animateModelPartRotationBasedOnTicks(
			animationContextTracker,
			modelPart,
			currentEntityTick,
			modelPart.yRot,
			modelPart.zRot,
			targetZ,
			ticks
		);
	}

	public static void animateModelPartRotationBasedOnTicks(
		AnimationContextTracker animationContextTracker,
		ModelPart modelPart,
		int currentEntityTick,
		float targetX,
		float targetY,
		float targetZ,
		int ticks
	) {
		Vector3f targetVector = new Vector3f(targetX, targetY, targetZ);
		animateModelPartBasedOnTicks(
			animationContextTracker,
			modelPart,
			currentEntityTick,
			ModelPartAnimationType.ROTATION,
			targetVector,
			ticks
		);
	}

	private static void animateModelPartBasedOnTicks(
		AnimationContextTracker animationContextTracker,
		ModelPart modelPart,
		int currentEntityTick,
		ModelPartAnimationType animationType,
		Vector3f targetVector,
		int ticks
	) {

		String modelPartName = modelPart.toString();
		ModelPartAnimationContext animationContext;
		Vector3f currentVector;
		Vector3f animationTargetVector;

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

			if (!animationTargetVector.equals(targetVector)) {
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
				case POSITION -> new Vector3f(modelPart.x, modelPart.y, modelPart.z);
				case ROTATION -> new Vector3f(modelPart.xRot, modelPart.yRot, modelPart.zRot);
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
