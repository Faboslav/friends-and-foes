package com.faboslav.friendsandfoes.client.render.entity.model;

import com.faboslav.friendsandfoes.entity.passive.MaulerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;

@Environment(EnvType.CLIENT)
public class MaulerEntityModel<T extends MaulerEntity> extends AbstractEntityModel<T>
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

	private float buttonPressAnimationProgress;

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
			this.defaultModelTransforms,
			MODEL_PART_ROOT,
			this.root
		);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();

		// Add head
		root.addChild(MODEL_PART_HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 20.0F, 1.0F));

		ModelPartData head = root.getChild(MODEL_PART_HEAD);
		head.addChild(MODEL_PART_UPPER_JAW, ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F, -3.0F, -10.0F, 9.0F, 3.0F, 10.0F), ModelTransform.pivot(0.0F, -3.0F, 5.0F));
		head.addChild(MODEL_PART_LOWER_JAW, ModelPartBuilder.create().uv(0, 13).cuboid(-4.5F, -3.0F, -10.0F, 9.0F, 3.0F, 10.0F), ModelTransform.pivot(0.0F, -3.0F, 5.0F));

		// Add body
		root.addChild(MODEL_PART_BODY, ModelPartBuilder.create().uv(0, 26).cuboid(-3.5F, 0.0F, -3.0F, 7.0F, 2.0F, 6.0F, new Dilation(-0.01F)), ModelTransform.pivot(0.0F, 20.0F, 1.0F));
		root.addChild(MODEL_PART_FRONT_LEFT_LEG, ModelPartBuilder.create().uv(0, 5).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), ModelTransform.pivot(2.5F, 21.0F, -1.0F));
		root.addChild(MODEL_PART_FRONT_RIGHT_LEG, ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -1.0F, -1.5F, 2.0F, 10.0F, 3.0F), ModelTransform.pivot(-2.5F, 21.0F, -1.0F));
		root.addChild(MODEL_PART_BACK_LEFT_LEG, ModelPartBuilder.create().uv(0, 18).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), ModelTransform.pivot(2.5F, 21.0F, 3.0F));
		root.addChild(MODEL_PART_BACK_RIGHT_LEG, ModelPartBuilder.create().uv(0, 13).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), ModelTransform.pivot(-2.5F, 21.0F, 3.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(
		T copperGolem,
		float limbAngle,
		float limbDistance,
		float animationProgress,
		float headYaw,
		float headPitch
	) {
		this.applyModelTransforms(
			this.defaultModelTransforms,
			MODEL_PART_ROOT,
			this.root
		);
	}

	public void animateModel(
		T copperGolem,
		float limbAngle,
		float limbDistance,
		float tickDelta
	) {
	}
}