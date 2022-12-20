package com.faboslav.friendsandfoes.client.render.entity.model;

import com.faboslav.friendsandfoes.entity.MaulerEntity;
import com.faboslav.friendsandfoes.util.animation.AnimationMath;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public final class MaulerEntityModel<T extends MaulerEntity> extends AnimatedEntityModel<T>
{
	private static final String MODEL_PART_ROOT = "root";
	private static final String MODEL_PART_HEAD = "head";
	private static final String MODEL_PART_UPPER_JAW = "upperJaw";
	private static final String MODEL_PART_LOWER_JAW = "lowerJaw";
	private static final String MODEL_PART_BODY = "body";
	private static final String MODEL_PART_FRONT_LEFT_LEG = "frontLeftLeg";
	private static final String MODEL_PART_FRONT_RIGHT_LEG = "frontRightLeg";
	private static final String MODEL_PART_BACK_LEFT_LEG = "backLeftLeg";
	private static final String MODEL_PART_BACK_RIGHT_LEG = "backRightLeg";

	private final ModelPart head;
	private final ModelPart upperJaw;
	private final ModelPart lowerJaw;
	private final ModelPart body;
	private final ModelPart frontLeftLeg;
	private final ModelPart frontRightLeg;
	private final ModelPart backLeftLeg;
	private final ModelPart backRightLeg;

	public MaulerEntityModel(ModelPart root) {
		super(root);
		this.head = this.root.getChild(MODEL_PART_HEAD);
		this.upperJaw = this.head.getChild(MODEL_PART_UPPER_JAW);
		this.lowerJaw = this.head.getChild(MODEL_PART_LOWER_JAW);
		this.body = this.root.getChild(MODEL_PART_BODY);
		this.frontLeftLeg = this.root.getChild(MODEL_PART_FRONT_LEFT_LEG);
		this.frontRightLeg = this.root.getChild(MODEL_PART_FRONT_RIGHT_LEG);
		this.backLeftLeg = this.root.getChild(MODEL_PART_BACK_LEFT_LEG);
		this.backRightLeg = this.root.getChild(MODEL_PART_BACK_RIGHT_LEG);

		this.setCurrentModelTransforms(
			MODEL_PART_ROOT,
			this.root
		);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();

		root.addChild(MODEL_PART_HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 20.0F, 4.0F));

		ModelPartData head = root.getChild(MODEL_PART_HEAD);
		head.addChild(MODEL_PART_UPPER_JAW, ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F, -3.0F, -10.0F, 9.0F, 3.0F, 10.0F), ModelTransform.pivot(0.0F, -2.0F, 1.0F));
		head.addChild(MODEL_PART_LOWER_JAW, ModelPartBuilder.create().uv(0, 13).cuboid(-4.5F, 0.0F, -10.0F, 9.0F, 3.0F, 10.0F), ModelTransform.pivot(0.0F, -2.0F, 1.0F));

		root.addChild(MODEL_PART_BODY, ModelPartBuilder.create().uv(0, 26).cuboid(-3.5F, 0.0F, -3.0F, 7.0F, 2.0F, 6.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 20.0F, 1.0F));
		root.addChild(MODEL_PART_FRONT_LEFT_LEG, ModelPartBuilder.create().uv(0, 5).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), ModelTransform.pivot(2.5F, 21.0F, -1.0F));
		root.addChild(MODEL_PART_FRONT_RIGHT_LEG, ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), ModelTransform.pivot(-2.5F, 21.0F, -1.0F));
		root.addChild(MODEL_PART_BACK_LEFT_LEG, ModelPartBuilder.create().uv(0, 18).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), ModelTransform.pivot(2.5F, 21.0F, 3.0F));
		root.addChild(MODEL_PART_BACK_RIGHT_LEG, ModelPartBuilder.create().uv(0, 13).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), ModelTransform.pivot(-2.5F, 21.0F, 3.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(
		T mauler,
		float limbAngle,
		float limbDistance,
		float animationProgress,
		float headYaw,
		float headPitch
	) {
		this.applyModelTransforms(MODEL_PART_ROOT, this.root);
		this.modelAnimator.setEntity(mauler);

		float burrowingDownAnimationProgress = mauler.getBurrowingDownAnimationProgress();
		float maulerHeightWithOffset = 0.5625F * 16.0F + 1.0F;

		if (burrowingDownAnimationProgress > 0.0F && burrowingDownAnimationProgress < 1.0F) {
			float targetY = maulerHeightWithOffset * burrowingDownAnimationProgress;
			this.modelAnimator.animateYPositionWithProgress(this.root, targetY, AnimationMath.absSin(animationProgress));
			return;
		} else if (mauler.getBurrowingDownAnimationProgress() == 1.0F) {
			this.root.pivotY = maulerHeightWithOffset;
			return;
		}

		this.head.pitch = headPitch * 0.005F;

		float baseSpeed = mauler.hasAngerTime() ? 14.0F:10.0F;
		float jumpHeight = mauler.hasAngerTime() ? -4.5F:-2.5F;

		this.root.pivotY = jumpHeight * Math.abs(MathHelper.wrap(limbAngle, baseSpeed) * limbDistance);

		float legPitch = MathHelper.wrap(limbAngle, baseSpeed) * limbDistance;
		float frontLegPitch = -1.5F * legPitch;
		float backLegPitch = 1.5F * legPitch;

		this.frontLeftLeg.pitch = frontLegPitch;
		this.frontRightLeg.pitch = frontLegPitch;
		this.backLeftLeg.pitch = backLegPitch;
		this.backRightLeg.pitch = backLegPitch;

		if (
			mauler.hasAngerTime()
			&& mauler.isBurrowedDown() == false
			&& mauler.isMoving()
			&& mauler.isOnGround()
			&& mauler.getVelocity().getY() <= 0.00001
		) {
			float targetX = AnimationMath.toRadians(5) + AnimationMath.toRadians(-65) * AnimationMath.absSin(animationProgress, 1.0F, 0.35F);
			float delta = AnimationMath.absSin(animationProgress);
			this.modelAnimator.animateXRotationWithProgress(this.upperJaw, targetX, delta);
			this.modelAnimator.animateXRotationOverTicks(this.lowerJaw, AnimationMath.toRadians(-5), 10);
		} else {
			this.modelAnimator.animateXRotationOverTicks(this.upperJaw, 0.0F, 10);
			this.modelAnimator.animateXRotationOverTicks(this.lowerJaw, 0.0F, 10);
		}
	}
}