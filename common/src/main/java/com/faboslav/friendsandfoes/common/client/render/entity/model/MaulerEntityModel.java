package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.entity.animation.MaulerAnimations;
import com.faboslav.friendsandfoes.common.versions.VersionedEntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;


//? if >= 1.21.6 {
import net.minecraft.client.animation.KeyframeAnimation;
//?} else {
/*import net.minecraft.client.animation.AnimationDefinition;
 *///?}

//? if >=1.21.3 {
import net.minecraft.client.model.EntityModel;
import com.faboslav.friendsandfoes.common.client.render.entity.state.MaulerRenderState;
//?} else {
/*import net.minecraft.client.model.HierarchicalModel;
*///?}

//? if >=1.21.3 {
public final class MaulerEntityModel extends EntityModel<MaulerRenderState>
//?} else {
/*public final class MaulerEntityModel<T extends MaulerEntity> extends HierarchicalModel<T>
*///?}
{
	private static final String MODEL_PART_MAIN = "main";
	private static final String MODEL_PART_HEAD = "head";
	private static final String MODEL_PART_UPPER_JAW = "upperJaw";
	private static final String MODEL_PART_LOWER_JAW = "lowerJaw";
	private static final String MODEL_PART_BODY = "body";
	private static final String MODEL_PART_FRONT_LEFT_LEG = "frontLeftLeg";
	private static final String MODEL_PART_FRONT_RIGHT_LEG = "frontRightLeg";
	private static final String MODEL_PART_BACK_LEFT_LEG = "backLeftLeg";
	private static final String MODEL_PART_BACK_RIGHT_LEG = "backRightLeg";

	private final ModelPart root;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart upperJaw;
	private final ModelPart lowerJaw;
	private final ModelPart body;
	private final ModelPart frontLeftLeg;
	private final ModelPart frontRightLeg;
	private final ModelPart backLeftLeg;
	private final ModelPart backRightLeg;

	//? if >= 1.21.6 {
	private final KeyframeAnimation idleAnimation;
	private final KeyframeAnimation snapAnimation;
	private final KeyframeAnimation runAnimation;
	private final KeyframeAnimation burrowDownAnimation;
	private final KeyframeAnimation burrowUpAnimation;
	//?} else {
	/*private final AnimationDefinition idleAnimation;
	private final AnimationDefinition snapAnimation;
	private final AnimationDefinition runAnimation;
	private final AnimationDefinition burrowDownAnimation;
	private final AnimationDefinition burrowUpAnimation;
	*///?}

	public MaulerEntityModel(ModelPart root) {
		//? if >=1.21.3 {
		super(root);
		//?}

		this.root = root;
		this.main = this.root.getChild(MODEL_PART_MAIN);
		this.head = this.main.getChild(MODEL_PART_HEAD);
		this.upperJaw = this.head.getChild(MODEL_PART_UPPER_JAW);
		this.lowerJaw = this.head.getChild(MODEL_PART_LOWER_JAW);
		this.body = this.main.getChild(MODEL_PART_BODY);
		this.frontLeftLeg = this.main.getChild(MODEL_PART_FRONT_LEFT_LEG);
		this.frontRightLeg = this.main.getChild(MODEL_PART_FRONT_RIGHT_LEG);
		this.backLeftLeg = this.main.getChild(MODEL_PART_BACK_LEFT_LEG);
		this.backRightLeg = this.main.getChild(MODEL_PART_BACK_RIGHT_LEG);

		//? if >= 1.21.6 {
		this.idleAnimation = MaulerAnimations.IDLE.bake(root);
		this.snapAnimation = MaulerAnimations.SNAP.bake(root);
		this.runAnimation = MaulerAnimations.RUN.bake(root);
		this.burrowDownAnimation = MaulerAnimations.BURROW_DOWN.bake(root);
		this.burrowUpAnimation = MaulerAnimations.BURROW_UP.bake(root);
		//?} else {
		/*this.idleAnimation = MaulerAnimations.IDLE;
		this.snapAnimation = MaulerAnimations.SNAP;
		this.runAnimation = MaulerAnimations.RUN;
		this.burrowDownAnimation = MaulerAnimations.BURROW_DOWN;
		this.burrowUpAnimation = MaulerAnimations.BURROW_UP;
		*///?}
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();

		root.addOrReplaceChild(MODEL_PART_MAIN, CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition main = root.getChild(MODEL_PART_MAIN);

		main.addOrReplaceChild(MODEL_PART_HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 4.0F));

		PartDefinition head = main.getChild(MODEL_PART_HEAD);
		head.addOrReplaceChild(MODEL_PART_UPPER_JAW, CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -3.0F, -10.0F, 9.0F, 3.0F, 10.0F), PartPose.offset(0.0F, -2.0F, 1.0F));
		head.addOrReplaceChild(MODEL_PART_LOWER_JAW, CubeListBuilder.create().texOffs(0, 13).addBox(-4.5F, 0.0F, -10.0F, 9.0F, 3.0F, 10.0F), PartPose.offset(0.0F, -2.0F, 1.0F));

		main.addOrReplaceChild(MODEL_PART_BODY, CubeListBuilder.create().texOffs(0, 26).addBox(-3.5F, 0.0F, -3.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -4.0F, 1.0F));
		main.addOrReplaceChild(MODEL_PART_FRONT_LEFT_LEG, CubeListBuilder.create().texOffs(0, 5).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(2.5F, -3.0F, -1.0F));
		main.addOrReplaceChild(MODEL_PART_FRONT_RIGHT_LEG, CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(-2.5F, -3.0F, -1.0F));
		main.addOrReplaceChild(MODEL_PART_BACK_LEFT_LEG, CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(2.5F, -3.0F, 3.0F));
		main.addOrReplaceChild(MODEL_PART_BACK_RIGHT_LEG, CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(-2.5F, -3.0F, 3.0F));

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
	public void setupAnim(MaulerRenderState renderState)
	//?} else {
	/*public void setupAnim(T mauler, float limbSwing, float limbSwingAmount, float animationProgress, float headYaw, float headPitch)
	*///?}
	{
		//? if >=1.21.3 {
		super.setupAnim(renderState);
		var mauler = renderState.mauler;
		var limbSwing = renderState.walkAnimationPos;
		var limbSwingAmount = renderState.walkAnimationSpeed;
		var ageInTicks = renderState.ageInTicks;
		//?} else {
		/*this.root().getAllParts().forEach(ModelPart::resetPose);
		*///?}

		VersionedEntityModel.Animate(this, this.idleAnimation, mauler.idleAnimationState, ageInTicks);
		VersionedEntityModel.Animate(this, this.snapAnimation, mauler.snapAnimationState, ageInTicks);
		VersionedEntityModel.Animate(this, this.burrowDownAnimation, mauler.burrowDownAnimationState, ageInTicks);
		VersionedEntityModel.Animate(this, this.burrowUpAnimation, mauler.burrowUpAnimationState, ageInTicks);
		VersionedEntityModel.AnimateWalk(this, this.runAnimation, limbSwing, limbSwingAmount, 2.5F, 3.5F);

	}
}