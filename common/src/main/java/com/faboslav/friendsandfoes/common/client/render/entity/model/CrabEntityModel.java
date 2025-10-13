package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.client.render.entity.model.animation.KeyframeModelAnimator;
import com.faboslav.friendsandfoes.common.client.render.entity.model.animation.ModelPartModelAnimator;
import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

//? if >=1.21.3 {
import net.minecraft.client.model.EntityModel;
import com.faboslav.friendsandfoes.common.client.render.entity.state.CrabRenderState;
import net.minecraft.client.model.geom.builders.MeshTransformer;
//?} else {
/*import net.minecraft.client.model.HierarchicalModel;
 *///?}

//? if >=1.21.3 {
public class CrabEntityModel extends EntityModel<CrabRenderState>
//?} else {
/*public final class CrabEntityModel<T extends CrabEntity> extends HierarchicalModel<T>
*///?}
{
	//? if >=1.21.3 {
	public static final MeshTransformer BABY_TRANSFORMER = MeshTransformer.scaling(CrabEntity.BABY_SCALE);
	//?}

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

	private final ModelPart root;
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
		//? if >=1.21.3 {
		super(root);
		//?}

		this.root = root;
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

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();

		PartDefinition main = root.addOrReplaceChild(MAIN, CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, -1.5708F, -1.5708F, 1.5708F));
		PartDefinition body = main.addOrReplaceChild(BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -6.0F, -5.0F, 14.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
			.texOffs(38, 0).addBox(-6.0F, -7.0F, -5.0F, 12.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));
		PartDefinition leftFeeler = body.addOrReplaceChild(LEFT_FEELER, CubeListBuilder.create().texOffs(38, 1).mirror().addBox(-1.0F, 0.0F, -1.05F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, -6.0F, -4.25F, -0.3491F, 0.0F, 0.0F));
		PartDefinition rightFeeler = body.addOrReplaceChild(RIGHT_FEELER, CubeListBuilder.create().texOffs(38, 1).addBox(-1.0F, 0.0F, -1.05F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -6.0F, -4.25F, -0.3491F, 0.0F, 0.0F));
		PartDefinition leftClaw = body.addOrReplaceChild(LEFT_CLAW, CubeListBuilder.create(), PartPose.offsetAndRotation(7.0F, -3.0F, -5.0F, 0.0F, -1.0472F, -0.1309F));
		PartDefinition leftClawParts = leftClaw.addOrReplaceChild(LEFT_CLAW_PARTS, CubeListBuilder.create().texOffs(0, 16).addBox(-11.75F, -3.5F, -2.5F, 12.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
			.texOffs(0, 28).addBox(-10.75F, 0.5F, -1.5F, 10.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 1.0F, -2.5F));
		PartDefinition rightClaw = body.addOrReplaceChild(RIGHT_CLAW, CubeListBuilder.create().texOffs(44, 16).mirror().addBox(-2.0F, -1.5F, -4.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.0F, -2.0F, -5.0F, 0.0F, 0.829F, 0.2182F));
		PartDefinition leftFrontLegJoint = main.addOrReplaceChild(LEFT_FRONT_LEG_JOINT, CubeListBuilder.create(), PartPose.offsetAndRotation(7.0F, -3.5F, -1.0F, 0.0F, 0.6109F, 0.0F));
		PartDefinition leftFrontLeg = leftFrontLegJoint.addOrReplaceChild(LEFT_FRONT_LEG, CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.829F));
		PartDefinition leftMiddleLegJoint = main.addOrReplaceChild(LEFT_MIDDLE_LEG_JOINT, CubeListBuilder.create(), PartPose.offsetAndRotation(7.0F, -3.5F, 1.0F, 0.0F, 0.3054F, 0.0F));
		PartDefinition leftMiddleLeg = leftMiddleLegJoint.addOrReplaceChild(LEFT_MIDDLE_LEG, CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
		PartDefinition leftBackLegJoint = main.addOrReplaceChild(LEFT_BACK_LEG_JOINT, CubeListBuilder.create(), PartPose.offset(7.0F, -3.5F, 3.0F));
		PartDefinition leftBackLeg = leftBackLegJoint.addOrReplaceChild(LEFT_BACK_LEG, CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
		PartDefinition rightFrontLegJoint = main.addOrReplaceChild(RIGHT_FRONT_LEG_JOINT, CubeListBuilder.create(), PartPose.offsetAndRotation(-7.0F, -3.5F, -1.0F, 0.0F, -0.6109F, 0.0F));
		PartDefinition rightFrontLeg = rightFrontLegJoint.addOrReplaceChild(RIGHT_FRONT_LEG, CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.829F));
		PartDefinition rightMiddleLegJoint = main.addOrReplaceChild(RIGHT_MIDDLE_LEG_JOINT, CubeListBuilder.create(), PartPose.offsetAndRotation(-7.0F, -3.5F, 1.0F, 0.0F, -0.3054F, 0.0F));
		PartDefinition rightMiddleLeg = rightMiddleLegJoint.addOrReplaceChild(RIGHT_MIDDLE_LEG, CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition rightBackLegJoint = main.addOrReplaceChild(RIGHT_BACK_LEG_JOINT, CubeListBuilder.create(), PartPose.offset(-7.0F, -3.5F, 3.0F));
		PartDefinition rightBackLeg = rightBackLegJoint.addOrReplaceChild(RIGHT_BACK_LEG, CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		return LayerDefinition.create(modelData, 64, 64);
	}

	//? if <1.21.3 {
	/*@Override
	public ModelPart root() {
		return this.root;
	}
	*///?}

	@Override
	//? if >=1.21.3 {
	public void setupAnim(CrabRenderState renderState)
	//?} else {
	/*public void setupAnim(T crab, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch)
	*///?}
	{
		//? if >=1.21.3 {
		var crab = renderState.crab;
		var limbAngle = renderState.walkAnimationPos;
		var limbDistance = renderState.walkAnimationSpeed;
		var animationProgress = renderState.ageInTicks;
		//?}

		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.updateKeyframeAnimations(crab, limbAngle, limbDistance, animationProgress);
		this.updateModelPartAnimations(crab);
	}

	public void updateKeyframeAnimations(
		CrabEntity crab,
		float limbAngle,
		float limbDistance,
		float animationProgress
	) {
		var movementAnimation = crab.getMovementAnimation();
		var animations = crab.getTrackedAnimations();
		var animationContextTracker = crab.getAnimationContextTracker();
		var currentTick = crab.tickCount;
		var animationSpeedModifier = 1.0F;

		KeyframeModelAnimator.updateMovementKeyframeAnimations(this, movementAnimation, limbAngle, limbDistance, 2.5F, 4.5F, animationSpeedModifier);
		KeyframeModelAnimator.updateKeyframeAnimations(this, animationContextTracker, animations, currentTick, animationProgress, animationSpeedModifier);
	}

	public void updateModelPartAnimations(
		CrabEntity crab
	) {
		float scaleModifier = crab.getSize().getScaleModifier();

		float pivotX = 0.0F;
		float pivotY = crab.onClimbable() ? 17.0F * scaleModifier:24.0F;
		float pivotZ = crab.onClimbable() ? -9.0F * scaleModifier:0.0F;
		float pitch = -1.5708F;
		float yaw = crab.onClimbable() ? 0.0F:-1.5708F;
		float roll = 1.5708F;

		ModelPartModelAnimator.animateModelPartPositionBasedOnTicks(
			crab.getAnimationContextTracker(),
			this.main,
			crab.tickCount,
			pivotX,
			pivotY,
			pivotZ,
			20
		);
		ModelPartModelAnimator.animateModelPartRotationBasedOnTicks(
			crab.getAnimationContextTracker(),
			this.main,
			crab.tickCount,
			pitch,
			yaw,
			roll,
			20
		);
	}
}