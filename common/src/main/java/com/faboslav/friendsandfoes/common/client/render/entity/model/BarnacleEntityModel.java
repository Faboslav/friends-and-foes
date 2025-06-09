package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.client.render.entity.model.animation.KeyframeModelAnimator;
import com.faboslav.friendsandfoes.common.entity.BarnacleEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
//? >=1.21.3 {
import net.minecraft.client.model.EntityModel;
import com.faboslav.friendsandfoes.common.client.render.entity.state.BarnacleRenderState;
//?} else {
/*import net.minecraft.client.model.HierarchicalModel;
*///?}

//? >=1.21.3 {
public final class BarnacleEntityModel extends EntityModel<BarnacleRenderState>
//?} else {
/*public final class BarnacleEntityModel<T extends BarnacleEntity> extends HierarchicalModel<T>
*///?}
{
	private static final String MODEL_PART_BODY = "body";
	private static final String MODEL_PART_HEAD = "head";
	private static final String MODEL_PART_TENTACLE = "tentacle";
	private static final String MODEL_PART_TOP_LEFT_MOUTH = "topLeftMouth";
	private static final String MODEL_PART_TOP_RIGHT_MOUTH = "topRightMouth";
	private static final String MODEL_PART_BOTTOM_LEFT_MOUTH = "bottomLeftMouth";
	private static final String MODEL_PART_BOTTOM_RIGHT_MOUTH = "bottomRightMouth";
	private static final String MODEL_PART_KELP = "kelp";

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart tentacle;
	private final ModelPart topLeftMouth;
	private final ModelPart topRightMouth;
	private final ModelPart bottomLeftMouth;
	private final ModelPart bottomRightMouth;
	private final ModelPart kelp;

	public BarnacleEntityModel(ModelPart root) {
		//? >=1.21.3 {
		super(root);
		//?}

		this.root = root;
		this.body = this.root.getChild(MODEL_PART_BODY);
		this.head = this.body.getChild(MODEL_PART_HEAD);
		this.tentacle = this.body.getChild(MODEL_PART_TENTACLE);
		this.topLeftMouth = this.body.getChild(MODEL_PART_TOP_LEFT_MOUTH);
		this.topRightMouth = this.body.getChild(MODEL_PART_TOP_RIGHT_MOUTH);
		this.bottomLeftMouth = this.body.getChild(MODEL_PART_BOTTOM_LEFT_MOUTH);
		this.bottomRightMouth = this.body.getChild(MODEL_PART_BOTTOM_RIGHT_MOUTH);
		this.kelp = this.body.getChild(MODEL_PART_KELP);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();

		root.addOrReplaceChild(MODEL_PART_BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.getChild(MODEL_PART_BODY);
		body.addOrReplaceChild(MODEL_PART_HEAD, CubeListBuilder.create().texOffs(0, 28).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 9.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -6.0F, 11.0F));
		body.addOrReplaceChild(MODEL_PART_TENTACLE, CubeListBuilder.create().texOffs(31, 62).addBox(0.0F, -0.5F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 11.0F, 0.0F, 3.1416F, 0.0F));
		body.addOrReplaceChild(MODEL_PART_TOP_LEFT_MOUTH, CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -4.0F, -22.0F, 6.0F, 6.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, -6.0F, 11.0F, 0.0F, 0.0F, -1.5708F));
		body.addOrReplaceChild(MODEL_PART_TOP_RIGHT_MOUTH, CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -4.0F, -22.0F, 6.0F, 6.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -6.0F, 11.0F, 0.0F, 0.0F, 1.5708F));
		body.addOrReplaceChild(MODEL_PART_BOTTOM_LEFT_MOUTH, CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, 0.0F, -22.0F, 6.0F, 6.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, -6.0F, 11.0F));
		body.addOrReplaceChild(MODEL_PART_BOTTOM_RIGHT_MOUTH, CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -22.0F, 6.0F, 6.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -6.0F, 11.0F));
		body.addOrReplaceChild(MODEL_PART_KELP, CubeListBuilder.create().texOffs(0, 45).addBox(-4.0F, -4.0F, 1.0F, 8.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 19.0F));

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
	public void setupAnim(BarnacleRenderState renderState)
	//?} else {
	/*public void setupAnim(T rascal, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch)
	*///?}
	{
		//? >=1.21.3 {
		var barnacle = renderState.barnacle;
		var limbAngle = renderState.walkAnimationPos;
		var limbDistance = renderState.walkAnimationSpeed;
		var animationProgress = renderState.ageInTicks;
		//?}
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.updateAnimations(barnacle, limbAngle, limbDistance, animationProgress);
	}

	public void updateAnimations(
		BarnacleEntity barnacle,
		float limbAngle,
		float limbDistance,
		float animationProgress
	) {
		var movementAnimation = barnacle.getMovementAnimation();
		var animations = barnacle.getTrackedAnimations();
		var animationContextTracker = barnacle.getAnimationContextTracker();
		var currentTick = barnacle.tickCount;
		var animationSpeedModifier = 1.0F;

		KeyframeModelAnimator.updateMovementKeyframeAnimations(this, movementAnimation, limbAngle, limbDistance, 1.5F, 2.5F, animationSpeedModifier);
		KeyframeModelAnimator.updateKeyframeAnimations(this, animationContextTracker, animations, currentTick, animationProgress, animationSpeedModifier);
	}
}