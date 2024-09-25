package com.faboslav.friendsandfoes.common.client.render.entity.animation.animator;

import com.faboslav.friendsandfoes.common.client.render.entity.animation.KeyframeAnimation;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.context.KeyframeAnimationContext;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.context.ModelPartAnimationContext;
import com.faboslav.friendsandfoes.common.client.render.entity.model.AnimatedEntityModel;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.animation.Animation;
import com.faboslav.friendsandfoes.common.entity.animation.Keyframe;
import com.faboslav.friendsandfoes.common.entity.animation.Transformation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class ModelAnimator
{
	private static final Vector3f TEMP = new Vector3f();

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
			animateKeyframe(animatedEntityModel, animation, state.getTimeRunning(), 1.0F, TEMP);
		});
	}

	public static void updateMovementKeyframeAnimations(
		AnimatedEntity animatedEntity,
		AnimatedEntityModel animatedEntityModel,
		long runningTime,
		float scale
	) {
		KeyframeAnimation keyframeAnimation = animatedEntity.getMovementAnimation();
		Animation animation = keyframeAnimation.getAnimation();
		animateKeyframe(animatedEntityModel, animation, runningTime, scale, TEMP);
	}

	public static void animateKeyframe(
		SinglePartEntityModel<?> model,
		Animation animation,
		long runningTime,
		float scale,
		Vector3f vec3f
	) {
		float f = getRunningSeconds(animation, runningTime);
		Iterator var7 = animation.boneAnimations().entrySet().iterator();

		while (var7.hasNext()) {
			Map.Entry<String, List<Transformation>> entry = (Map.Entry) var7.next();
			Optional<ModelPart> optional = model.getChild(entry.getKey());
			List<Transformation> list = entry.getValue();
			optional.ifPresent((part) -> {
				list.forEach((transformation) -> {
					Keyframe[] keyframes = transformation.keyframes();
					int i = Math.max(0, MathHelper.binarySearch(0, keyframes.length, (index) -> {
						return f <= keyframes[index].timestamp();
					}) - 1);
					int j = Math.min(keyframes.length - 1, i + 1);
					Keyframe keyframe = keyframes[i];
					Keyframe keyframe2 = keyframes[j];
					float h = f - keyframe.timestamp();
					float k;
					if (j != i) {
						k = MathHelper.clamp(h / (keyframe2.timestamp() - keyframe.timestamp()), 0.0F, 1.0F);
					} else {
						k = 0.0F;
					}

					keyframe2.interpolation().apply(vec3f, k, keyframes, i, j, scale);
					// TODO rework this properly
					Transformation.Type type = transformation.type();
					if (type == Transformation.Type.TRANSLATE) {
						part.translate(vec3f);
					} else if (type == Transformation.Type.ROTATE) {
						part.rotate(vec3f);
					} else if (type == Transformation.Type.SCALE) {
						part.scale(vec3f);
					}
				});
			});
		}

	}

	private static float getRunningSeconds(Animation animation, long runningTime) {
		float f = (float) runningTime / 1000.0F;
		return animation.looping() ? f % animation.lengthInSeconds():f;
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
		Vector3f targetVector = new Vector3f(targetX, targetY, targetZ);
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
		Vector3f targetVector = new Vector3f(targetX, targetY, targetZ);
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
		Vector3f targetVector,
		float progress
	) {
		AnimationContextTracker animationContextTracker = animatedEntity.getAnimationContextTracker();
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
				case POSITION -> new Vector3f(modelPart.pivotX, modelPart.pivotY, modelPart.pivotZ);
				case ROTATION -> new Vector3f(modelPart.pitch, modelPart.yaw, modelPart.roll);
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
		Vector3f targetVector = new Vector3f(targetX, targetY, targetZ);
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
		Vector3f targetVector = new Vector3f(targetX, targetY, targetZ);
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
		Vector3f targetVector,
		int ticks
	) {
		AnimationContextTracker animationContextTracker = animatedEntity.getAnimationContextTracker();
		int currentEntityTick = ((Entity) animatedEntity).age;

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
				case POSITION -> new Vector3f(modelPart.pivotX, modelPart.pivotY, modelPart.pivotZ);
				case ROTATION -> new Vector3f(modelPart.pitch, modelPart.yaw, modelPart.roll);
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
