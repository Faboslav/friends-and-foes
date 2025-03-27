package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.client.render.entity.model.animation.KeyframeModelAnimator;
import com.faboslav.friendsandfoes.common.entity.RascalEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

//? >=1.21.3 {
import net.minecraft.client.model.EntityModel;
import com.faboslav.friendsandfoes.common.client.render.entity.state.RascalRenderState;
//?} else {
/*import net.minecraft.client.model.HierarchicalModel;
 *///?}

//? >=1.21.3 {
public final class RascalEntityModel extends EntityModel<RascalRenderState>
//?} else {
/*public final class RascalEntityModel<T extends RascalEntity> extends HierarchicalModel<T>
*///?}
{
	private static final String MODEL_PART_HEAD = "head";
	private static final String MODEL_PART_BODY = "body";
	private static final String MODEL_PART_BAG = "bag";
	private static final String MODEL_PART_LEFT_ARM = "leftArm";
	private static final String MODEL_PART_RIGHT_ARM = "rightArm";
	private static final String MODEL_PART_LEFT_LEG = "leftLeg";
	private static final String MODEL_PART_RIGHT_LEG = "rightLeg";

	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart bag;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public RascalEntityModel(ModelPart root) {
		//? >=1.21.3 {
		super(root);
		//?}

		this.root = root;
		this.head = this.root.getChild(MODEL_PART_HEAD);
		this.body = this.root.getChild(MODEL_PART_BODY);
		this.bag = this.root.getChild(MODEL_PART_BAG);
		this.leftArm = this.root.getChild(MODEL_PART_LEFT_ARM);
		this.rightArm = this.root.getChild(MODEL_PART_RIGHT_ARM);
		this.leftLeg = this.root.getChild(MODEL_PART_LEFT_LEG);
		this.rightLeg = this.root.getChild(MODEL_PART_RIGHT_LEG);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();

		root.addOrReplaceChild(MODEL_PART_HEAD, CubeListBuilder.create().texOffs(0, 52).addBox(-4.0F, -2.0F, -5.0F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
			.texOffs(28, 36).addBox(-4.0F, -3.0F, -5.0F, 8.0F, 9.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 9.0F, -5.0F));
		root.addOrReplaceChild(MODEL_PART_BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 0.0F, -4.0F, 12.0F, 15.0F, 8.0F, new CubeDeformation(-0.01F))
			.texOffs(0, 23).addBox(-6.0F, 12.0F, -4.0F, 12.0F, 5.0F, 8.0F, new CubeDeformation(-0.5F))
			.texOffs(44, 0).addBox(3.0F, 0.0F, -4.0F, 2.0F, 15.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 4.0F, 0.0F));
		root.addOrReplaceChild(MODEL_PART_BAG, CubeListBuilder.create().texOffs(0, 36).addBox(-4.0F, -0.5F, -0.5F, 8.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 7.5F, 4.5F));
		root.addOrReplaceChild(MODEL_PART_LEFT_ARM, CubeListBuilder.create().texOffs(50, 28).mirror().addBox(-3.0F, -2.0F, -2.0F, 3.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.0F, 11.0F, 0.0F));
		root.addOrReplaceChild(MODEL_PART_RIGHT_ARM, CubeListBuilder.create().texOffs(50, 28).addBox(0.0F, -2.0F, -2.0F, 3.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 11.0F, 0.0F));
		root.addOrReplaceChild(MODEL_PART_LEFT_LEG, CubeListBuilder.create().texOffs(28, 54).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 18.0F, 0.0F));
		root.addOrReplaceChild(MODEL_PART_RIGHT_LEG, CubeListBuilder.create().texOffs(28, 54).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 18.0F, 0.0F));

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
	public void setupAnim(RascalRenderState renderState)
	//?} else {
	/*public void setupAnim(T rascal, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch)
	*///?}
	{
		//? >=1.21.3 {
		var rascal = renderState.rascal;
		var limbAngle = renderState.walkAnimationPos;
		var limbDistance = renderState.walkAnimationSpeed;
		var animationProgress = renderState.ageInTicks;
		//?}
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.updateAnimations(rascal, limbAngle, limbDistance, animationProgress);
	}

	public void updateAnimations(
		RascalEntity rascal,
		float limbAngle,
		float limbDistance,
		float animationProgress
	) {
		var movementAnimation = rascal.getMovementAnimation();
		var animations = rascal.getTrackedAnimations();
		var animationContextTracker = rascal.getAnimationContextTracker();
		var currentTick = rascal.tickCount;
		var animationSpeedModifier = 1.0F;

		KeyframeModelAnimator.updateMovementKeyframeAnimations(this, movementAnimation, limbAngle, limbDistance, 1.5F, 2.5F, animationSpeedModifier);
		KeyframeModelAnimator.updateKeyframeAnimations(this, animationContextTracker, animations, currentTick, animationProgress, animationSpeedModifier);
	}
}