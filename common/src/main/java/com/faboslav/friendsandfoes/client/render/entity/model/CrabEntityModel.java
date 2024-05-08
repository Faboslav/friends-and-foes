package com.faboslav.friendsandfoes.client.render.entity.model;

import com.faboslav.friendsandfoes.entity.CrabEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;

@Environment(EnvType.CLIENT)
public final class CrabEntityModel<T extends CrabEntity> extends AnimatedEntityModel<T>
{
	private static final String MAIN = "main";
	private static final String BODY = "body";
	private static final String LEFT_FEELER = "leftFeeler";
	private static final String RIGHT_FEELER = "rightFeeler";
	private static final String LEFT_CLAW = "leftClaw";
	private static final String LEFT_CLAW_PARTS = "leftClawParts";
	private static final String RIGHT_CLAW = "rightClaw";
	private static final String LEFT_FRONT_LEG_JOINT = "leftFrontLegJoint";
	private static final String LEFT_FRONT_LEG = "leftFrontLeg";
	private static final String LEFT_MIDDLE_LEG_JOINT = "leftMiddleLegJoint";
	private static final String LEFT_MIDDLE_LEG = "leftMiddleLeg";
	private static final String LEFT_BACK_LEG_JOINT = "leftBackLegJoint";
	private static final String LEFT_BACK_LEG = "leftBackLeg";
	private static final String RIGHT_FRONT_LEG_JOINT = "rightFrontLegJoint";
	private static final String RIGHT_FRONT_LEG = "rightFrontLeg";
	private static final String RIGHT_MIDDLE_LEG_JOINT = "rightMiddleLegJoint";
	private static final String RIGHT_MIDDLE_LEG = "rightMiddleLeg";
	private static final String RIGHT_BACK_LEG_JOINT = "rightBackLegJoint";
	private static final String RIGHT_BACK_LEG = "rightBackLeg";

	private final ModelPart main;
	private final ModelPart body;
	private final ModelPart leftFeeler;
	private final ModelPart rightFeeler;
	private final ModelPart leftClaw;
	private final ModelPart leftClawParts;
	private final ModelPart rightClaw;
	private final ModelPart leftFrontLegJoint;
	private final ModelPart leftFrontLeg;
	private final ModelPart leftMiddleLegJoint;
	private final ModelPart leftMiddleLeg;
	private final ModelPart leftBackLegJoint;
	private final ModelPart leftBackLeg;
	private final ModelPart rightFrontLegJoint;
	private final ModelPart rightFrontLeg;
	private final ModelPart rightMiddleLegJoint;
	private final ModelPart rightMiddleLeg;
	private final ModelPart rightBackLegJoint;
	private final ModelPart rightBackLeg;

	public CrabEntityModel(ModelPart root) {
		super(root);
		this.main = this.root.getChild(MAIN);
		this.body = this.main.getChild(BODY);
		this.leftFeeler = this.body.getChild(LEFT_FEELER);
		this.rightFeeler = this.body.getChild(RIGHT_FEELER);
		this.leftClaw = this.body.getChild(LEFT_CLAW);
		this.leftClawParts = this.leftClaw.getChild(LEFT_CLAW_PARTS);
		this.rightClaw = this.body.getChild(RIGHT_CLAW);
		this.leftFrontLegJoint = this.main.getChild(LEFT_FRONT_LEG_JOINT);
		this.leftFrontLeg = this.leftFrontLegJoint.getChild(LEFT_FRONT_LEG);
		this.leftMiddleLegJoint = this.main.getChild(LEFT_MIDDLE_LEG_JOINT);
		this.leftMiddleLeg = this.leftMiddleLegJoint.getChild(LEFT_MIDDLE_LEG);
		this.leftBackLegJoint = this.main.getChild(LEFT_BACK_LEG_JOINT);
		this.leftBackLeg = this.leftBackLegJoint.getChild(LEFT_BACK_LEG);
		this.rightFrontLegJoint = this.main.getChild(RIGHT_FRONT_LEG_JOINT);
		this.rightFrontLeg = this.rightFrontLegJoint.getChild(RIGHT_FRONT_LEG);
		this.rightMiddleLegJoint = this.main.getChild(RIGHT_MIDDLE_LEG_JOINT);
		this.rightMiddleLeg = this.rightMiddleLegJoint.getChild(RIGHT_MIDDLE_LEG);
		this.rightBackLegJoint = this.main.getChild(RIGHT_BACK_LEG_JOINT);
		this.rightBackLeg = this.rightBackLegJoint.getChild(RIGHT_BACK_LEG);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();

		ModelPartData main = root.addChild(MAIN, ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, -1.5708F, -1.5708F, 1.5708F));

		ModelPartData body = main.addChild(BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -6.0F, -5.0F, 14.0F, 6.0F, 10.0F, new Dilation(0.0F))
			.uv(38, 0).cuboid(-6.0F, -7.0F, -5.0F, 12.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

		ModelPartData leftFeeler = body.addChild(LEFT_FEELER, ModelPartBuilder.create().uv(38, 1).mirrored().cuboid(-1.0F, 0.0F, -1.05F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.0F, -6.0F, -4.25F, -0.3491F, 0.0F, 0.0F));

		ModelPartData rightFeeler = body.addChild(RIGHT_FEELER, ModelPartBuilder.create().uv(38, 1).cuboid(-1.0F, 0.0F, -1.05F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -6.0F, -4.25F, -0.3491F, 0.0F, 0.0F));

		ModelPartData leftClaw = body.addChild(LEFT_CLAW, ModelPartBuilder.create(), ModelTransform.of(7.0F, -3.0F, -5.0F, 0.0F, -1.0472F, -0.1309F));

		ModelPartData leftClawParts = leftClaw.addChild(LEFT_CLAW_PARTS, ModelPartBuilder.create().uv(0, 16).cuboid(-11.75F, -3.5F, -2.5F, 12.0F, 7.0F, 5.0F, new Dilation(0.0F))
			.uv(0, 28).cuboid(-10.75F, 0.5F, -1.5F, 10.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 1.0F, -2.5F));

		ModelPartData rightClaw = body.addChild(RIGHT_CLAW, ModelPartBuilder.create().uv(44, 16).mirrored().cuboid(-2.0F, -1.5F, -4.0F, 6.0F, 6.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-7.0F, -2.0F, -5.0F, 0.0F, 0.829F, 0.2182F));

		ModelPartData leftFrontLegJoint = main.addChild(LEFT_FRONT_LEG_JOINT, ModelPartBuilder.create(), ModelTransform.of(7.0F, -3.5F, -1.0F, 0.0F, 0.6109F, 0.0F));

		ModelPartData leftFrontLeg = leftFrontLegJoint.addChild(LEFT_FRONT_LEG, ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(0.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.829F));

		ModelPartData leftMiddleLegJoint = main.addChild(LEFT_MIDDLE_LEG_JOINT, ModelPartBuilder.create(), ModelTransform.of(7.0F, -3.5F, 1.0F, 0.0F, 0.3054F, 0.0F));

		ModelPartData leftMiddleLeg = leftMiddleLegJoint.addChild(LEFT_MIDDLE_LEG, ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(0.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		ModelPartData leftBackLegJoint = main.addChild(LEFT_BACK_LEG_JOINT, ModelPartBuilder.create(), ModelTransform.pivot(7.0F, -3.5F, 3.0F));

		ModelPartData leftBackLeg = leftBackLegJoint.addChild(LEFT_BACK_LEG, ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(0.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		ModelPartData rightFrontLegJoint = main.addChild(RIGHT_FRONT_LEG_JOINT, ModelPartBuilder.create(), ModelTransform.of(-7.0F, -3.5F, -1.0F, 0.0F, -0.6109F, 0.0F));

		ModelPartData rightFrontLeg = rightFrontLegJoint.addChild(RIGHT_FRONT_LEG, ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.829F));

		ModelPartData rightMiddleLegJoint = main.addChild(RIGHT_MIDDLE_LEG_JOINT, ModelPartBuilder.create(), ModelTransform.of(-7.0F, -3.5F, 1.0F, 0.0F, -0.3054F, 0.0F));

		ModelPartData rightMiddleLeg = rightMiddleLegJoint.addChild(RIGHT_MIDDLE_LEG, ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		ModelPartData rightBackLegJoint = main.addChild(RIGHT_BACK_LEG_JOINT, ModelPartBuilder.create(), ModelTransform.pivot(-7.0F, -3.5F, 3.0F));

		ModelPartData rightBackLeg = rightBackLegJoint.addChild(RIGHT_BACK_LEG, ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(
		T crab,
		float limbAngle,
		float limbDistance,
		float animationProgress,
		float headYaw,
		float headPitch
	) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);

		if(crab.isMoving()) {
			// this.updateMovementKeyframeAnimations(crab, limbAngle, limbDistance, 2.5F, 4.5F);
		}

		this.updateKeyframeAnimations(crab, animationProgress);


		float pivotX = 0.0F;
		float pivotY = crab.isClimbing() ? 17.0F:24.0F;
		float pivotZ = crab.isClimbing() ? -5.0F:0.0F;
		float pitch = -1.5708F;
		float yaw = crab.isClimbing() ? 0.0F:-1.5708F;
		float roll = 1.5708F;

		animateModelPartPositionBasedOnTicks(
			crab,
			this.main,
			pivotX,
			pivotY,
			pivotZ,
			10
		);
		animateModelPartRotationBasedOnTicks(
			crab,
			this.main,
			pitch,
			yaw,
			roll,
			10
		);
	}
}