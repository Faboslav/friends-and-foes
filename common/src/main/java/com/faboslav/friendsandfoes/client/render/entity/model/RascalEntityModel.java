package com.faboslav.friendsandfoes.client.render.entity.model;

import com.faboslav.friendsandfoes.client.render.entity.animation.RascalAnimations;
import com.faboslav.friendsandfoes.entity.RascalEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public final class RascalEntityModel<T extends RascalEntity> extends AnimatedEntityModel<T>
{
	private static final String MODEL_PART_HEAD = "head";
	private static final String MODEL_PART_BODY = "body";
	private static final String MODEL_PART_BAG = "bag";
	private static final String MODEL_PART_LEFT_ARM = "leftArm";
	private static final String MODEL_PART_RIGHT_ARM = "rightArm";
	private static final String MODEL_PART_LEFT_LEG = "leftLeg";
	private static final String MODEL_PART_RIGHT_LEG = "rightLeg";

	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart bag;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public RascalEntityModel(ModelPart root) {
		super(root);
		this.head = this.root.getChild(MODEL_PART_HEAD);
		this.body = this.root.getChild(MODEL_PART_BODY);
		this.bag = this.root.getChild(MODEL_PART_BAG);
		this.leftArm = this.root.getChild(MODEL_PART_LEFT_ARM);
		this.rightArm = this.root.getChild(MODEL_PART_RIGHT_ARM);
		this.leftLeg = this.root.getChild(MODEL_PART_LEFT_LEG);
		this.rightLeg = this.root.getChild(MODEL_PART_RIGHT_LEG);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();

		root.addChild(MODEL_PART_HEAD, ModelPartBuilder.create().uv(0, 52).cuboid(-4.0F, -2.0F, -5.0F, 8.0F, 6.0F, 6.0F, new Dilation(0.0F))
			.uv(28, 36).cuboid(-4.0F, -3.0F, -5.0F, 8.0F, 9.0F, 6.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 9.0F, -5.0F));
		root.addChild(MODEL_PART_BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, 0.0F, -4.0F, 12.0F, 15.0F, 8.0F, new Dilation(-0.01F))
			.uv(0, 23).cuboid(-6.0F, 12.0F, -4.0F, 12.0F, 5.0F, 8.0F, new Dilation(-0.5F))
			.uv(44, 0).cuboid(3.0F, 0.0F, -4.0F, 2.0F, 15.0F, 8.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));
		root.addChild(MODEL_PART_BAG, ModelPartBuilder.create().uv(0, 36).cuboid(-4.0F, -0.5F, -0.5F, 8.0F, 9.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 7.5F, 4.5F));
		root.addChild(MODEL_PART_LEFT_ARM, ModelPartBuilder.create().uv(50, 28).mirrored().cuboid(-3.0F, -2.0F, -2.0F, 3.0F, 10.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-6.0F, 11.0F, 0.0F));
		root.addChild(MODEL_PART_RIGHT_ARM, ModelPartBuilder.create().uv(50, 28).cuboid(0.0F, -2.0F, -2.0F, 3.0F, 10.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(6.0F, 11.0F, 0.0F));
		root.addChild(MODEL_PART_LEFT_LEG, ModelPartBuilder.create().uv(28, 54).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-3.0F, 18.0F, 0.0F));
		root.addChild(MODEL_PART_RIGHT_LEG, ModelPartBuilder.create().uv(28, 54).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 18.0F, 0.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void animateModel(
		T rascal,
		float limbAngle,
		float limbDistance,
		float tickDelta
	) {
		//this.updateAnimations(rascal, limbAngle, limbDistance);
	}

	@Override
	public void setAngles(
		T rascal,
		float limbAngle,
		float limbDistance,
		float animationProgress,
		float headYaw,
		float headPitch
	) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.updateMovementKeyframeAnimations(rascal, limbAngle, limbDistance, 1.5F, 2.5F);
		this.updateKeyframeAnimations(rascal, animationProgress);
	}

	private void updateAnimations(
		T rascal,
		float limbAngle,
		float limbDistance
	) {
		this.applyModelTransforms(MODEL_PART_ROOT, this.root);

		this.rightLeg.pitch = -2.0F * MathHelper.wrap(limbAngle, 13.0F) * limbDistance;
		this.leftLeg.pitch = 2.0F * MathHelper.wrap(limbAngle, 13.0F) * limbDistance;
		this.rightLeg.yaw = 0.0F;
		this.leftLeg.yaw = 0.0F;
		this.leftArm.pitch = (-0.2F + 2.0F * MathHelper.wrap(limbAngle, 13.0F)) * limbDistance;
		this.rightArm.pitch = (-0.2F - 2.0F * MathHelper.wrap(limbAngle, 13.0F)) * limbDistance;
	}
}