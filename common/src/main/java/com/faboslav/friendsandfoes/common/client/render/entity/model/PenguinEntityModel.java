package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.client.render.entity.model.animation.KeyframeModelAnimator;
import com.faboslav.friendsandfoes.common.client.render.entity.model.animation.ModelPartModelAnimator;
import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
import com.faboslav.friendsandfoes.common.util.animation.AnimationMath;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
//? >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.PenguinRenderState;
import net.minecraft.client.model.EntityModel;
//?} else {
/*import net.minecraft.client.model.HierarchicalModel;
*///?}

//? >=1.21.3 {
public final class PenguinEntityModel extends EntityModel<PenguinRenderState>
//?} else {
/*public final class PenguinEntityModel<T extends PenguinEntity> extends HierarchicalModel<T>
*///?}
{
	private static final String MAIN = "main";
	private static final String BODY = "body";
	private static final String HEAD = "head";
	private static final String BILL = "bill";
	private static final String EAR_PATCHES = "earPatches";
	private static final String LEFT_WING = "leftWing";
	private static final String RIGHT_WING = "rightWing";
	private static final String LEFT_LEG = "leftLeg";
	private static final String RIGHT_LEG = "rightLeg";

	private final ModelPart root;
	private final ModelPart main;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart bill;
	private final ModelPart earPatches;
	private final ModelPart leftWing;
	private final ModelPart rightWing;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public PenguinEntityModel(ModelPart root) {
		//? >=1.21.3 {
		super(root);
		//?}

		this.root = root;
		this.main = this.root.getChild(MAIN);
		this.body = this.main.getChild(BODY);
		this.head = this.body.getChild(HEAD);
		this.bill = this.head.getChild(BILL);
		this.earPatches = this.head.getChild(EAR_PATCHES);
		this.leftWing = this.body.getChild(LEFT_WING);
		this.rightWing = this.body.getChild(RIGHT_WING);
		this.leftLeg = this.main.getChild(LEFT_LEG);
		this.rightLeg = this.main.getChild(RIGHT_LEG);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();

		root.addOrReplaceChild(MAIN, CubeListBuilder.create(), PartPose.offset(0.0F, 11.0F, 0.0F));
		PartDefinition main = root.getChild(MAIN);

		main.addOrReplaceChild(BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -12.0F, -4.0F, 10.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));
		PartDefinition body = main.getChild(BODY);

		body.addOrReplaceChild(HEAD, CubeListBuilder.create().texOffs(29, 13).addBox(-4.0F, -5.0F, -3.0F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));
		PartDefinition head = body.getChild(HEAD);

		head.addOrReplaceChild(BILL, CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -17.0F, -5.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));
		head.addOrReplaceChild(EAR_PATCHES, CubeListBuilder.create().texOffs(0, 20).addBox(-5.0F, -2.0F, 1.0F, 10.0F, 3.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -4.0F, -4.0F));
		body.addOrReplaceChild(LEFT_WING, CubeListBuilder.create().texOffs(30, 25).addBox(0.0F, -1.0F, -2.0F, 1.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -10.0F, -1.0F));
		body.addOrReplaceChild(RIGHT_WING, CubeListBuilder.create().texOffs(30, 25).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -10.0F, -1.0F));
		main.addOrReplaceChild(LEFT_LEG, CubeListBuilder.create().texOffs(28, 0).addBox(-1.5F, 0.0F, -4.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 12.0F, 0.0F));
		main.addOrReplaceChild(RIGHT_LEG, CubeListBuilder.create().texOffs(28, 0).addBox(-1.5F, 0.0F, -4.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 12.0F, 0.0F));

		return LayerDefinition.create(modelData, 64, 64);
	}

	//? <1.21.3 {
	/*@Override
	public ModelPart root() {
		return this.root;
	}
	*///?}

	@Override
	//? >=1.21.3 {
	public void setupAnim(PenguinRenderState renderState)
	//?} else {
	/*public void setupAnim(T penguin, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch)
	*///?}
	{
		//? >=1.21.3 {
		var penguin = renderState.penguin;
		var limbAngle = renderState.walkAnimationPos;
		var limbDistance = renderState.walkAnimationSpeed;
		var animationProgress = renderState.ageInTicks;
		//?}
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateSwimming(penguin);
		this.updateAnimations(penguin, limbAngle, limbDistance, animationProgress);
	}

	public void animateSwimming(
		PenguinEntity penguin
	) {
		var penguinContextTracker = penguin.getAnimationContextTracker();
		var transitionTicks = 25;
		if(penguin.isUnderWater()) {
			ModelPartModelAnimator.animateModelPartXRotationBasedOnTicks(penguinContextTracker, this.main, penguin.tickCount, AnimationMath.toRadians(90.0F), transitionTicks);
			ModelPartModelAnimator.animateModelPartPositionBasedOnTicks(penguinContextTracker, this.main, penguin.tickCount, 0, 21, -5, transitionTicks);
			ModelPartModelAnimator.animateModelPartXRotationBasedOnTicks(penguinContextTracker, this.head, penguin.tickCount, AnimationMath.toRadians(-90.0F), transitionTicks);
			ModelPartModelAnimator.animateModelPartPositionBasedOnTicks(penguinContextTracker, this.head, penguin.tickCount, 0, -16, -2, transitionTicks);
		} else {
			ModelPartModelAnimator.animateModelPartXPositionBasedOnTicks(penguinContextTracker, this.main, penguin.tickCount, AnimationMath.toRadians(0.0F), transitionTicks);
			ModelPartModelAnimator.animateModelPartPositionBasedOnTicks(penguinContextTracker, this.main, penguin.tickCount, 0, 11, 0 , transitionTicks);
			ModelPartModelAnimator.animateModelPartXRotationBasedOnTicks(penguinContextTracker, this.head, penguin.tickCount, AnimationMath.toRadians(0.0F), transitionTicks);
			ModelPartModelAnimator.animateModelPartPositionBasedOnTicks(penguinContextTracker, this.head, penguin.tickCount, 0, -12, 0, transitionTicks);
		}
	}

	public void updateAnimations(
		PenguinEntity penguin,
		float limbAngle,
		float limbDistance,
		float animationProgress
	) {
		var movementAnimation = penguin.getMovementAnimation();
		var animations = penguin.getTrackedAnimations();
		var animationContextTracker = penguin.getAnimationContextTracker();
		var currentTick = penguin.tickCount;
		var animationSpeedModifier = 1.0F;

		var limbAngleScale = penguin.isUnderWater() ? 1.5F : 5.5F;
		var limbDistanceScale = penguin.isUnderWater() ? 4.0F : 4.5F;

		KeyframeModelAnimator.updateMovementKeyframeAnimations(this, movementAnimation, limbAngle, limbDistance, limbAngleScale, limbDistanceScale, animationSpeedModifier);
		KeyframeModelAnimator.updateKeyframeAnimations(this, animationContextTracker, animations, currentTick, animationProgress, animationSpeedModifier);
	}
}