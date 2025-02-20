package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.client.render.entity.model.animation.KeyframeModelAnimator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

//? >=1.21.3 {
import net.minecraft.client.model.EntityModel;
import com.faboslav.friendsandfoes.common.client.render.entity.state.TuffGolemRenderState;
//?} else {
/*import net.minecraft.client.model.HierarchicalModel;
import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
*///?}

@Environment(EnvType.CLIENT)
//? >=1.21.3 {
public final class TuffGolemEntityModel extends EntityModel<TuffGolemRenderState>
//?} else {
/*public final class TuffGolemEntityModel<T extends TuffGolemEntity> extends HierarchicalModel<T>
*///?}
{
	private static final String MODEL_PART_BODY = "body";
	private static final String MODEL_PART_NOSE = "nose";
	private static final String MODEL_PART_FRONT_CLOTH = "frontCloth";
	private static final String MODEL_PART_BACK_CLOTH = "backCloth";
	private static final String MODEL_PART_CLOTH_STAND = "clothStand";
	private static final String MODEL_PART_LEFT_ARM = "leftArm";
	private static final String MODEL_PART_RIGHT_ARM = "rightArm";
	private static final String MODEL_PART_LEFT_LEG = "leftLeg";
	private static final String MODEL_PART_RIGHT_LEG = "rightLeg";

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart nose;
	private final ModelPart frontCloth;
	private final ModelPart backCloth;
	private final ModelPart clothStand;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public TuffGolemEntityModel(ModelPart root) {
		//? >=1.21.3 {
		super(root);
		//?}

		this.root = root;
		this.body = this.root.getChild(MODEL_PART_BODY);
		this.nose = this.body.getChild(MODEL_PART_NOSE);
		this.frontCloth = this.body.getChild(MODEL_PART_FRONT_CLOTH);
		this.backCloth = this.body.getChild(MODEL_PART_BACK_CLOTH);
		this.clothStand = this.body.getChild(MODEL_PART_CLOTH_STAND);
		this.leftArm = this.body.getChild(MODEL_PART_LEFT_ARM);
		this.rightArm = this.body.getChild(MODEL_PART_RIGHT_ARM);
		this.leftLeg = this.root.getChild(MODEL_PART_LEFT_LEG);
		this.rightLeg = this.root.getChild(MODEL_PART_RIGHT_LEG);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();

		root.addOrReplaceChild(MODEL_PART_BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -12.0F, -4.0F, 8.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.0F, 0.0F));

		PartDefinition body = root.getChild(MODEL_PART_BODY);
		body.addOrReplaceChild(MODEL_PART_NOSE, CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -4.0F));
		body.addOrReplaceChild(MODEL_PART_FRONT_CLOTH, CubeListBuilder.create().texOffs(36, 4).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 6.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, -4.0F, -4.0F));
		body.addOrReplaceChild(MODEL_PART_BACK_CLOTH, CubeListBuilder.create().texOffs(40, 18).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 6.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, -4.0F, 0.0F));
		body.addOrReplaceChild(MODEL_PART_CLOTH_STAND, CubeListBuilder.create().texOffs(0, 35).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -4.0F, 0.0F));
		body.addOrReplaceChild(MODEL_PART_LEFT_ARM, CubeListBuilder.create().texOffs(0, 21).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -4.0F, 0.0F));
		body.addOrReplaceChild(MODEL_PART_RIGHT_ARM, CubeListBuilder.create().texOffs(0, 21).mirror(true).addBox(0.0F, -2.0F, -2.0F, 2.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -4.0F, 0.0F));

		root.addOrReplaceChild(MODEL_PART_LEFT_LEG, CubeListBuilder.create().texOffs(12, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(-0.0005F)), PartPose.offset(-2.0F, 19.0F, 0.0F));
		root.addOrReplaceChild(MODEL_PART_RIGHT_LEG, CubeListBuilder.create().texOffs(12, 21).mirror(true).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(-0.0005F)), PartPose.offset(2.0F, 19.0F, 0.0F));

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
	public void setupAnim(TuffGolemRenderState renderState)
	//?} else {
	/*public void setupAnim(T tuffGolem, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch)
	*///?}
	{
		//? >=1.21.3 {
		var tuffGolem = renderState.tuffGolem;
		var limbAngle = renderState.walkAnimationPos;
		var limbDistance = renderState.walkAnimationSpeed;
		var animationProgress = renderState.ageInTicks;
		//?}

		var movementAnimation = tuffGolem.getMovementAnimation();
		var animations = tuffGolem.getTrackedAnimations();
		var animationContextTracker = tuffGolem.getAnimationContextTracker();
		var currentTick = tuffGolem.tickCount;
		var animationSpeedModifier = 1.0F;

		KeyframeModelAnimator.updateMovementKeyframeAnimations(this, movementAnimation, limbAngle, limbDistance, 4.0F * tuffGolem.getMovementSpeedModifier(), 4.0F * tuffGolem.getMovementSpeedModifier(), animationSpeedModifier);
		KeyframeModelAnimator.updateKeyframeAnimations(this, animationContextTracker, animations, currentTick, animationProgress, animationSpeedModifier);
	}
}