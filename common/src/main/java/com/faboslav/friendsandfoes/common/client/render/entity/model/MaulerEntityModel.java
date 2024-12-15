package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.client.render.entity.model.animation.ModelPartModelAnimator;
import com.faboslav.friendsandfoes.common.entity.MaulerEntity;
import com.faboslav.friendsandfoes.common.util.animation.AnimationMath;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public final class MaulerEntityModel<T extends MaulerEntity> extends HierarchicalModel<T>
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

	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart upperJaw;
	private final ModelPart lowerJaw;
	private final ModelPart body;
	private final ModelPart frontLeftLeg;
	private final ModelPart frontRightLeg;
	private final ModelPart backLeftLeg;
	private final ModelPart backRightLeg;

	public MaulerEntityModel(ModelPart root) {
		this.root = root;
		this.head = this.root.getChild(MODEL_PART_HEAD);
		this.upperJaw = this.head.getChild(MODEL_PART_UPPER_JAW);
		this.lowerJaw = this.head.getChild(MODEL_PART_LOWER_JAW);
		this.body = this.root.getChild(MODEL_PART_BODY);
		this.frontLeftLeg = this.root.getChild(MODEL_PART_FRONT_LEFT_LEG);
		this.frontRightLeg = this.root.getChild(MODEL_PART_FRONT_RIGHT_LEG);
		this.backLeftLeg = this.root.getChild(MODEL_PART_BACK_LEFT_LEG);
		this.backRightLeg = this.root.getChild(MODEL_PART_BACK_RIGHT_LEG);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();

		root.addOrReplaceChild(MODEL_PART_HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 4.0F));

		PartDefinition head = root.getChild(MODEL_PART_HEAD);
		head.addOrReplaceChild(MODEL_PART_UPPER_JAW, CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -3.0F, -10.0F, 9.0F, 3.0F, 10.0F), PartPose.offset(0.0F, -2.0F, 1.0F));
		head.addOrReplaceChild(MODEL_PART_LOWER_JAW, CubeListBuilder.create().texOffs(0, 13).addBox(-4.5F, 0.0F, -10.0F, 9.0F, 3.0F, 10.0F), PartPose.offset(0.0F, -2.0F, 1.0F));

		root.addOrReplaceChild(MODEL_PART_BODY, CubeListBuilder.create().texOffs(0, 26).addBox(-3.5F, 0.0F, -3.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 20.0F, 1.0F));
		root.addOrReplaceChild(MODEL_PART_FRONT_LEFT_LEG, CubeListBuilder.create().texOffs(0, 5).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(2.5F, 21.0F, -1.0F));
		root.addOrReplaceChild(MODEL_PART_FRONT_RIGHT_LEG, CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(-2.5F, 21.0F, -1.0F));
		root.addOrReplaceChild(MODEL_PART_BACK_LEFT_LEG, CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(2.5F, 21.0F, 3.0F));
		root.addOrReplaceChild(MODEL_PART_BACK_RIGHT_LEG, CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(-2.5F, 21.0F, 3.0F));

		return LayerDefinition.create(modelData, 64, 64);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(
		MaulerEntity mauler,
		float limbAngle,
		float limbDistance,
		float animationProgress,
		float headYaw,
		float headPitch
	) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		float burrowingDownAnimationProgress = mauler.getBurrowingDownAnimationProgress();
		float maulerHeightWithOffset = 0.5625F * 16.0F + 1.0F;

		if (burrowingDownAnimationProgress > 0.0F && burrowingDownAnimationProgress < 1.0F) {
			float targetY = maulerHeightWithOffset * burrowingDownAnimationProgress;
			ModelPartModelAnimator.animateModelPartYPositionWithProgress(mauler.getAnimationContextTracker(), this.root, targetY, AnimationMath.absSin(animationProgress));
			return;
		} else if (mauler.getBurrowingDownAnimationProgress() == 1.0F) {
			this.root.y = maulerHeightWithOffset;
			return;
		}

		this.head.xRot = headPitch * 0.005F;

		float baseSpeed = mauler.isAngry() ? 14.0F:10.0F;
		float jumpHeight = mauler.isAngry() ? -4.5F:-2.5F;

		this.root.y = jumpHeight * Math.abs(Mth.triangleWave(limbAngle, baseSpeed) * limbDistance);

		float legPitch = Mth.triangleWave(limbAngle, baseSpeed) * limbDistance;
		float frontLegPitch = -1.5F * legPitch;
		float backLegPitch = 1.5F * legPitch;

		this.frontLeftLeg.xRot = frontLegPitch;
		this.frontRightLeg.xRot = frontLegPitch;
		this.backLeftLeg.xRot = backLegPitch;
		this.backRightLeg.xRot = backLegPitch;

		if (
			mauler.isAngry()
			&& !mauler.isBurrowedDown()
			&& mauler.isMoving()
			&& mauler.onGround()
			&& mauler.getDeltaMovement().y() <= 0.00001
		) {
			float targetX = AnimationMath.toRadians(5) + AnimationMath.toRadians(-65) * AnimationMath.absSin(animationProgress, 1.0F, 0.35F);
			float delta = AnimationMath.absSin(animationProgress);
			ModelPartModelAnimator.animateModelPartXRotationBasedOnProgress(mauler.getAnimationContextTracker(), this.upperJaw, targetX, delta);
			ModelPartModelAnimator.animateModelPartXRotationBasedOnTicks(mauler.getAnimationContextTracker(), this.lowerJaw, mauler.tickCount, AnimationMath.toRadians(-5), 10);
		} else {
			ModelPartModelAnimator.animateModelPartXRotationBasedOnTicks(mauler.getAnimationContextTracker(), this.upperJaw, mauler.tickCount, 0.0F, 10);
			ModelPartModelAnimator.animateModelPartXRotationBasedOnTicks(mauler.getAnimationContextTracker(), this.lowerJaw, mauler.tickCount, 0.0F, 10);
		}
	}
}