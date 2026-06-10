package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.entity.animation.RascalAnimations;
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
import com.faboslav.friendsandfoes.common.client.render.entity.state.RascalRenderState;
//?} else {
/*import net.minecraft.client.model.HierarchicalModel;
 *///?}

//? if >=1.21.3 {
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

	//? if >= 1.21.6 {
	private final KeyframeAnimation idleAnimation;
	private final KeyframeAnimation nodAnimation;
	private final KeyframeAnimation giveRewardAnimation;
	private final KeyframeAnimation walkAnimation;
	//?} else {
	/*private final AnimationDefinition idleAnimation;
	private final AnimationDefinition nodAnimation;
	private final AnimationDefinition giveRewardAnimation;
	private final AnimationDefinition walkAnimation;
	*///?}

	public RascalEntityModel(ModelPart root) {
		//? if >=1.21.3 {
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

		//? if >= 1.21.6 {
		this.idleAnimation = RascalAnimations.IDLE.bake(root);
		this.nodAnimation = RascalAnimations.NOD.bake(root);
		this.giveRewardAnimation = RascalAnimations.GIVE_REWARD.bake(root);
		this.walkAnimation = RascalAnimations.WALK.bake(root);
		//?} else {
		/*this.idleAnimation = RascalAnimations.IDLE;
		this.nodAnimation = RascalAnimations.NOD;
		this.giveRewardAnimation = RascalAnimations.GIVE_REWARD;
		this.walkAnimation = RascalAnimations.WALK;
		*///?}
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

	//? if <1.21.3 {
	/*@Override
	public ModelPart root() {
		return this.root;
	}
	*///?}

	@Override
	//? if >=1.21.3 {
	public void setupAnim(RascalRenderState renderState)
	//?} else {
	/*public void setupAnim(T rascal, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch)
	*///?}
	{
		//? if >=1.21.3 {
		super.setupAnim(renderState);
		var rascal = renderState.rascal;
		var limbSwing = renderState.walkAnimationPos;
		var limbSwingAmount = renderState.walkAnimationSpeed;
		var ageInTicks = renderState.ageInTicks;
		//?} else {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		//?}

		VersionedEntityModel.Animate(this, this.idleAnimation, rascal.idleAnimationState, ageInTicks);
		VersionedEntityModel.Animate(this, this.nodAnimation, rascal.nodAnimationState, ageInTicks);
		VersionedEntityModel.Animate(this, this.giveRewardAnimation, rascal.giveRewardAnimationState, ageInTicks);
		VersionedEntityModel.AnimateWalk(this, this.walkAnimation, limbSwing, limbSwingAmount, 1.5F, 2.5F);
	}
}