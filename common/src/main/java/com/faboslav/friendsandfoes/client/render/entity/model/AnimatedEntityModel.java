package com.faboslav.friendsandfoes.client.render.entity.model;

import com.faboslav.friendsandfoes.client.render.entity.animation.animator.ModelAnimator;
import com.faboslav.friendsandfoes.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.mixin.ModelPartAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.entity.Entity;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public abstract class AnimatedEntityModel<E extends Entity> extends BaseEntityModel<E>
{
	protected final Map<String, ModelTransform> defaultModelTransforms;

	protected AnimatedEntityModel(ModelPart root) {
		super(root);

		this.defaultModelTransforms = new HashMap<>();

		this.setCurrentModelTransforms(
			MODEL_PART_ROOT,
			this.root
		);
	}

	protected void setCurrentModelTransforms(
		String modelPartName,
		ModelPart modelPart
	) {
		this.defaultModelTransforms.put(modelPartName, modelPart.getTransform());

		ModelPartAccessor modelPartAccessor = ((ModelPartAccessor) (Object) modelPart);
		modelPartAccessor.getChildren().forEach(this::setCurrentModelTransforms);
	}

	protected void applyModelTransforms(
		String modelPartName,
		ModelPart modelPart
	) {
		ModelTransform defaultModelTransform = this.defaultModelTransforms.get(modelPartName);
		modelPart.setTransform(defaultModelTransform);

		ModelPartAccessor modelPartAccessor = ((ModelPartAccessor) (Object) modelPart);
		modelPartAccessor.getChildren().forEach(this::applyModelTransforms);
	}

	public void animateModelPartXPositionBasedOnProgress(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetX,
		float progress
	) {
		ModelAnimator.animateModelPartXPositionWithProgress(
			animatedEntity,
			modelPart,
			targetX,
			progress
		);
	}

	public void animateModelPartYPositionBasedOnProgress(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetY,
		float progress
	) {
		ModelAnimator.animateModelPartYPositionWithProgress(
			animatedEntity,
			modelPart,
			targetY,
			progress
		);
	}

	public void animateModelPartZPositionBasedOnProgress(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetZ,
		float progress
	) {
		ModelAnimator.animateModelPartYPositionWithProgress(
			animatedEntity,
			modelPart,
			targetZ,
			progress
		);
	}

	public void animateModelPartPositionBasedOnProgress(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetX,
		float targetY,
		float targetZ,
		float progress
	) {
		ModelAnimator.animateModelPartPositionWithProgress(
			animatedEntity,
			modelPart,
			targetX,
			targetY,
			targetZ,
			progress
		);
	}

	public static void animateModelPartXRotationBasedOnProgress(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetX,
		float progress
	) {
		ModelAnimator.animateModelPartXRotationBasedOnProgress(
			animatedEntity,
			modelPart,
			targetX,
			progress
		);
	}

	public static void animateModelPartYRotationBasedOnProgress(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetY,
		float progress
	) {
		ModelAnimator.animateModelPartYRotationBasedOnProgress(
			animatedEntity,
			modelPart,
			targetY,
			progress
		);
	}

	public static void animateZRotationBasedOnProgress(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetZ,
		float progress
	) {
		ModelAnimator.animateZRotationBasedOnProgress(
			animatedEntity,
			modelPart,
			targetZ,
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
		ModelAnimator.animateModelPartRotationBasedOnProgress(
			animatedEntity,
			modelPart,
			targetX,
			targetY,
			targetZ,
			progress
		);
	}

	public static void animateModelPartXPositionBasedOnTicks(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetX,
		int ticks
	) {
		ModelAnimator.animateModelPartXPositionBasedOnTicks(
			animatedEntity,
			modelPart,
			targetX,
			ticks
		);
	}

	public static void animateModelPartYPositionBasedOnTicks(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetY,
		int ticks
	) {
		ModelAnimator.animateModelPartYPositionBasedOnTicks(
			animatedEntity,
			modelPart,
			targetY,
			ticks
		);
	}

	public static void animateZPositionBasedOnTicks(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetX,
		int ticks
	) {
		ModelAnimator.animateZPositionBasedOnTicks(
			animatedEntity,
			modelPart,
			targetX,
			ticks
		);
	}

	public static void animateModelPartPositionBasedOnTicks(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetX,
		float targetY,
		float targetZ,
		int ticks
	) {
		ModelAnimator.animateModelPartModelPartPositionBasedOnTicks(
			animatedEntity,
			modelPart,
			targetX,
			targetY,
			targetZ,
			ticks
		);
	}

	public static void animateModelPartXRotationBasedOnTicks(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetX,
		int ticks
	) {
		ModelAnimator.animateModelPartXRotationBasedOnTicks(
			animatedEntity,
			modelPart,
			targetX,
			ticks
		);
	}

	public static void animateModelPartYRotationBasedOnTicks(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetY,
		int ticks
	) {
		ModelAnimator.animateModelPartYRotationBasedOnTicks(
			animatedEntity,
			modelPart,
			targetY,
			ticks
		);
	}

	public static void animateModelPartZRotationBasedOnTicks(
		AnimatedEntity animatedEntity,
		ModelPart modelPart,
		float targetZ,
		int ticks
	) {
		ModelAnimator.animateModelPartZRotationBasedOnTicks(
			animatedEntity,
			modelPart,
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
		ModelAnimator.animateModelPartRotationBasedOnTicks(
			animatedEntity,
			modelPart,
			targetX,
			targetY,
			targetZ,
			ticks
		);
	}

	protected void updateKeyframeAnimations(
		AnimatedEntity animatedEntity,
		float animationProgress
	) {
		animatedEntity.getAnimations().forEach((keyframeAnimation -> {
			ModelAnimator.updateKeyframeAnimations(animatedEntity, this, keyframeAnimation, animationProgress);
		}));
	}

	protected void updateMovementKeyframeAnimations(
		AnimatedEntity animatedEntity,
		float limbAngle,
		float limbDistance,
		float limbAngleScale,
		float limbDistanceScale
	) {
		long runningTime = (long) (limbAngle * 50.0F * limbAngleScale);
		float scale = Math.min(limbDistance * limbDistanceScale, 1.0F);
		ModelAnimator.updateMovementKeyframeAnimations(animatedEntity, this, runningTime, scale);
	}
}