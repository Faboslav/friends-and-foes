package com.faboslav.friendsandfoes.client.render.entity.animation;

import com.faboslav.friendsandfoes.entity.AnimatedEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public final class ModelAnimator
{
	AnimatedEntity entity;

	public void setEntity(AnimatedEntity entity) {
		this.entity = entity;
	}

	public void animateXPositionWithProgress(ModelPart modelPart, float targetX, float progress) {
		this.animatePositionWithProgress(modelPart, targetX, modelPart.pivotY, modelPart.pivotZ, progress);
	}

	public void animateYPositionWithProgress(ModelPart modelPart, float targetY, float progress) {
		this.animatePositionWithProgress(modelPart, modelPart.pivotX, targetY, modelPart.pivotZ, progress);
	}

	public void animateZPositionWithProgress(ModelPart modelPart, float targetZ, float progress) {
		this.animatePositionWithProgress(modelPart, modelPart.pivotX, modelPart.pivotY, targetZ, progress);
	}

	public void animatePositionWithProgress(
		ModelPart modelPart,
		float targetX,
		float targetY,
		float targetZ,
		float progress
	) {
		Vec3d targetVector = new Vec3d(targetX, targetY, targetZ);
		this.animateWithProgress(modelPart, ModelPartAnimationType.POSITION, targetVector, progress);
	}

	public void animateXRotationWithProgress(ModelPart modelPart, float targetX, float progress) {
		this.animateRotationWithProgress(modelPart, targetX, modelPart.yaw, modelPart.roll, progress);
	}

	public void animateYRotationWithProgress(ModelPart modelPart, float targetY, float progress) {
		this.animateRotationWithProgress(modelPart, modelPart.pitch, targetY, modelPart.roll, progress);
	}

	public void animateZRotationWithProgress(ModelPart modelPart, float targetX, float progress) {
		this.animateRotationWithProgress(modelPart, targetX, modelPart.yaw, modelPart.roll, progress);
	}

	public void animateRotationWithProgress(
		ModelPart modelPart,
		float targetX,
		float targetY,
		float targetZ,
		float progress
	) {
		Vec3d targetVector = new Vec3d(targetX, targetY, targetZ);
		this.animateWithProgress(modelPart, ModelPartAnimationType.ROTATION, targetVector, progress);
	}

	private void animateWithProgress(
		ModelPart modelPart,
		ModelPartAnimationType animationType,
		Vec3d targetVector,
		float progress
	) {
		String modelPartName = modelPart.toString();
		ModelPartAnimationContext animationContext;
		Vec3d animationCurrentVector;
		Vec3d animationTargetVector;

		if (this.getAnimationContextTracker().contains(modelPartName, animationType)) {
			animationContext = this.getAnimationContextTracker().get(modelPartName, animationType);
			animationCurrentVector = animationContext.getCurrentVector();
			animationTargetVector = animationContext.getTargetVector();

			if (animationTargetVector.equals(targetVector) == false) {
				this.getAnimationContextTracker().remove(modelPartName, animationType);
				animationContext = ModelPartAnimationContext.createWithProgress(
					progress,
					targetVector,
					animationCurrentVector
				);
				this.getAnimationContextTracker().add(modelPartName, animationType, animationContext);
			}
		} else {
			animationCurrentVector = switch (animationType) {
				case POSITION -> new Vec3d(modelPart.pivotX, modelPart.pivotY, modelPart.pivotZ);
				case ROTATION -> new Vec3d(modelPart.pitch, modelPart.yaw, modelPart.roll);
			};

			animationContext = ModelPartAnimationContext.createWithProgress(
				progress,
				targetVector,
				animationCurrentVector
			);
			this.getAnimationContextTracker().add(modelPartName, animationType, animationContext);
		}

		animationContext.setProgress(progress);
		animationContext.recalculateCurrentVector();
		animationCurrentVector = animationContext.getCurrentVector();

		switch (animationType) {
			case POSITION -> ModelPartAnimator.setPosition(modelPart, animationCurrentVector);
			case ROTATION -> ModelPartAnimator.setRotation(modelPart, animationCurrentVector);
		}
	}

	public void animateXPositionOverTicks(ModelPart modelPart, float targetX, int ticks) {
		this.animatePositionOverTicks(modelPart, targetX, modelPart.pivotY, modelPart.pivotZ, ticks);
	}

	public void animateYPositionOverTicks(ModelPart modelPart, float targetY, int ticks) {
		this.animatePositionOverTicks(modelPart, modelPart.pivotX, targetY, modelPart.pivotZ, ticks);
	}

	public void animateZPositionOverTicks(ModelPart modelPart, float targetX, int ticks) {
		this.animatePositionOverTicks(modelPart, targetX, modelPart.pivotY, modelPart.pivotZ, ticks);
	}

	public void animatePositionOverTicks(
		ModelPart modelPart,
		float targetX,
		float targetY,
		float targetZ,
		int ticks
	) {
		Vec3d targetVector = new Vec3d(targetX, targetY, targetZ);
		this.animateOverTicks(modelPart, ModelPartAnimationType.POSITION, targetVector, ticks);
	}

	public void animateXRotationOverTicks(ModelPart modelPart, float targetX, int ticks) {
		this.animateRotationOverTicks(modelPart, targetX, modelPart.yaw, modelPart.roll, ticks);
	}

	public void animateYRotationOverTicks(ModelPart modelPart, float targetY, int ticks) {
		this.animateRotationOverTicks(modelPart, modelPart.pitch, targetY, modelPart.roll, ticks);
	}

	public void animateZRotationOverTicks(ModelPart modelPart, float targetZ, int ticks) {
		this.animateRotationOverTicks(modelPart, modelPart.yaw, modelPart.roll, targetZ, ticks);
	}

	public void animateRotationOverTicks(
		ModelPart modelPart,
		float targetX,
		float targetY,
		float targetZ,
		int ticks
	) {
		Vec3d targetVector = new Vec3d(targetX, targetY, targetZ);
		this.animateOverTicks(modelPart, ModelPartAnimationType.ROTATION, targetVector, ticks);
	}

	private void animateOverTicks(
		ModelPart modelPart,
		ModelPartAnimationType animationType,
		Vec3d targetVector,
		int ticks
	) {
		String modelPartName = modelPart.toString();
		ModelPartAnimationContext animationContext;
		Vec3d currentVector;
		Vec3d animationTargetVector;

		if (this.getAnimationContextTracker().contains(modelPartName, animationType)) {
			animationContext = this.getAnimationContextTracker().get(modelPartName, animationType);
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
				this.getAnimationContextTracker().remove(modelPartName, animationType);
				animationContext = ModelPartAnimationContext.createWithTicks(
					this.getEntityCurrentTick(),
					ticks,
					targetVector,
					currentVector
				);
				this.getAnimationContextTracker().add(modelPartName, animationType, animationContext);
			}
		} else {
			currentVector = switch (animationType) {
				case POSITION -> new Vec3d(modelPart.pivotX, modelPart.pivotY, modelPart.pivotZ);
				case ROTATION -> new Vec3d(modelPart.pitch, modelPart.yaw, modelPart.roll);
			};

			animationContext = ModelPartAnimationContext.createWithTicks(
				this.getEntityCurrentTick(),
				ticks,
				targetVector,
				currentVector
			);
			this.getAnimationContextTracker().add(modelPartName, animationType, animationContext);
		}

		animationContext.setCurrentTick(this.getEntityCurrentTick());
		animationContext.recalculateProgress();
		animationContext.recalculateCurrentVector();
		currentVector = animationContext.getCurrentVector();

		switch (animationType) {
			case POSITION -> ModelPartAnimator.setPosition(modelPart, currentVector);
			case ROTATION -> ModelPartAnimator.setRotation(modelPart, currentVector);
		}
	}

	private AnimationContextTracker getAnimationContextTracker() {
		return this.entity.getAnimationContextTracker();
	}

	private int getEntityCurrentTick() {
		return ((Entity) this.entity).age;
	}
}
