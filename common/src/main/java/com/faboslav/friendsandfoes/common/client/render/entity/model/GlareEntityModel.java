package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.ModelPartAnimator;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.util.animation.AnimationMath;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;

@Environment(EnvType.CLIENT)
public final class GlareEntityModel<T extends GlareEntity> extends AnimatedEntityModel<T>
{
	private static final String MODEL_PART_ROOT = "root";
	private static final String MODEL_PART_HEAD = "head";
	private static final String MODEL_PART_EYES = "eyes";
	private static final String MODEL_TOP_AZALEA = "topAzalea";
	private static final String MODEL_BOTTOM_AZALEA = "bottomAzalea";
	private static final String MODEL_SECOND_LAYER = "secondLayer";
	private static final String MODEL_THIRD_LAYER = "thirdLayer";
	private static final String MODEL_FOURTH_LAYER = "fourthLayer";

	private final ModelPart head;
	private final ModelPart eyes;
	private final ModelPart topAzalea;
	private final ModelPart bottomAzalea;
	private final ModelPart secondLayer;
	private final ModelPart thirdLayer;
	private final ModelPart fourthLayer;

	private final ModelPart[] layers;

	public GlareEntityModel(ModelPart root) {
		super(root);
		this.head = this.root.getChild(MODEL_PART_HEAD);
		this.eyes = this.head.getChild(MODEL_PART_EYES);
		this.topAzalea = this.head.getChild(MODEL_TOP_AZALEA);
		this.bottomAzalea = this.head.getChild(MODEL_BOTTOM_AZALEA);
		this.secondLayer = this.bottomAzalea.getChild(MODEL_SECOND_LAYER);
		this.thirdLayer = this.secondLayer.getChild(MODEL_THIRD_LAYER);
		this.fourthLayer = this.thirdLayer.getChild(MODEL_FOURTH_LAYER);

		this.layers = new ModelPart[]{
			this.secondLayer,
			this.thirdLayer,
			this.fourthLayer,
		};
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();

		root.addChild(MODEL_PART_HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, 0.0F, -3.0F, 12.0F, 9.0F, 9.0F, new Dilation(-0.02F)), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

		ModelPartData head = root.getChild(MODEL_PART_HEAD);
		head.addChild(MODEL_PART_EYES, ModelPartBuilder.create().uv(33, 0).cuboid(2.0F, -1.0F, -0.3F, 2.0F, 2.0F, 1.0F, new Dilation(-0.2F)).uv(33, 0).cuboid(-4.0F, -1.0F, -0.3F, 2.0F, 2.0F, 1.0F, new Dilation(-0.2F)), ModelTransform.pivot(0.0F, 5.0F, -3.0F));
		head.addChild(MODEL_TOP_AZALEA, ModelPartBuilder.create().uv(0, 18).cuboid(-7.0F, 0.0F, -7.0F, 14.0F, 8.0F, 14.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		head.addChild(MODEL_BOTTOM_AZALEA, ModelPartBuilder.create().uv(18, 101).mirrored().cuboid(-7.0F, 0.75F, -7.0F, 14.0F, 0.0F, 14.0F, new Dilation(-0.01F)).mirrored(false).uv(0, 40).cuboid(-7.0F, -4.0F, -7.0F, 14.0F, 10.0F, 14.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 8.0F, 0.0F));

		ModelPartData bottomAzalea = head.getChild(MODEL_BOTTOM_AZALEA);
		bottomAzalea.addChild(MODEL_SECOND_LAYER, ModelPartBuilder.create().uv(0, 64).cuboid(-6.0F, 0.0F, -6.0F, 12.0F, 7.0F, 12.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

		ModelPartData secondLayer = bottomAzalea.getChild(MODEL_SECOND_LAYER);
		secondLayer.addChild(MODEL_THIRD_LAYER, ModelPartBuilder.create().uv(0, 83).cuboid(-5.0F, 0.0F, -5.0F, 10.0F, 7.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 0.0F));

		ModelPartData thirdLayer = secondLayer.getChild(MODEL_THIRD_LAYER);
		thirdLayer.addChild(MODEL_FOURTH_LAYER, ModelPartBuilder.create().uv(0, 100).cuboid(-4.0F, 0.0F, -4.0F, 8.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 0.0F));

		return TexturedModelData.of(modelData, 64, 128);
	}

	@Override
	public void setAngles(
		T glare,
		float limbAngle,
		float limbDistance,
		float animationProgress,
		float headYaw,
		float headPitch
	) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);

		this.animateEyes(glare);
		this.animateFloating(glare, animationProgress);

		float movementForce = MathHelper.sin(limbAngle * 0.1F) * limbDistance * 0.75F;
		float absMovementForce = Math.abs(movementForce);

		if (absMovementForce >= 0.001F) {
			this.head.pitch = AnimationMath.toRadians(40 * absMovementForce);
			this.head.roll = AnimationMath.toRadians(15 * movementForce);

			for (ModelPart layer : this.layers) {
				layer.pitch = AnimationMath.toRadians(30 * absMovementForce);
				layer.roll = AnimationMath.toRadians(15 * movementForce);
			}
		} else {
			this.head.pitch = AnimationMath.toRadians(0.5F * AnimationMath.sin(animationProgress * 0.125F));
			this.head.roll = AnimationMath.toRadians(0.5F * AnimationMath.cos(animationProgress * 0.125F));

			for (ModelPart layer : this.layers) {
				layer.pitch = AnimationMath.toRadians(0.75F * AnimationMath.sin(animationProgress * 0.1F));
				layer.roll = AnimationMath.toRadians(0.75F * AnimationMath.cos(animationProgress * 0.1F));
			}
		}
	}

	@Override
	public void animateModel(
		T glare,
		float limbAngle,
		float limbDistance,
		float tickDelta
	) {

	}

	private void animateFloating(
		T glare,
		float animationProgress
	) {
		float verticalFloatingSpeed = glare.isGrumpy() ? 0.3F:0.1F;
		float horizontalFloatingSpeed = glare.isGrumpy() ? 0.15F:0.05F;

		float verticalFloatingOffset;
		float horizontalFloatingOffset;

		if (glare.isSitting()) {
			verticalFloatingOffset = 0.5F;
			horizontalFloatingOffset = 0.5F;
		} else {
			verticalFloatingOffset = 1.5F;
			horizontalFloatingOffset = 1.0F;
		}

		float targetPivotY = glare.isSitting() ? 3.0F:0.11F;
		animateModelPartYPositionBasedOnTicks(glare, this.root, targetPivotY, 10);

		if (glare.isGrumpy()) {
			ModelPartAnimator.setXPosition(this.root, AnimationMath.sin(animationProgress, 0.5F));
			ModelPartAnimator.setYPosition(this.root, AnimationMath.absSin(animationProgress, 0.1F));
			ModelPartAnimator.setYRotation(this.root, AnimationMath.sin(animationProgress, 0.05F));
		}

		float verticalFloatingProgress = AnimationMath.sin(animationProgress * verticalFloatingSpeed) * verticalFloatingOffset;
		float horizontalFloatingProgress = AnimationMath.cos(animationProgress * horizontalFloatingSpeed) * horizontalFloatingOffset;

		this.head.pivotY = verticalFloatingProgress;
		this.head.pivotX = horizontalFloatingProgress;
	}

	private void animateEyes(T glare) {
		Vec2f targetEyesPositionOffset = glare.getTargetEyesPositionOffset();

		animateModelPartPositionBasedOnTicks(
			glare,
			this.eyes,
			this.eyes.pivotX + targetEyesPositionOffset.x,
			this.eyes.pivotY + targetEyesPositionOffset.y,
			this.eyes.pivotZ,
			GlareEntity.MIN_EYE_ANIMATION_TICK_AMOUNT
		);
	}
}