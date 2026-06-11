package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.client.render.entity.model.animation.KeyframeModelAnimator;
import com.faboslav.friendsandfoes.common.client.render.entity.state.RascalRenderState;
import com.faboslav.friendsandfoes.common.entity.animation.RascalAnimations;
import com.faboslav.friendsandfoes.common.entity.animation.TuffGolemAnimations;
import com.faboslav.friendsandfoes.common.versions.VersionedEntityModel;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

//? if >=1.21.3 {
import net.minecraft.client.model.EntityModel;
import com.faboslav.friendsandfoes.common.client.render.entity.state.TuffGolemRenderState;
//?} else {
/*import net.minecraft.client.model.HierarchicalModel;
import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
*///?}

//? if >=1.21.3 {
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

	//? if >= 1.21.6 {
	private final KeyframeAnimation showItemAnimation;
	private final KeyframeAnimation hideItemAnimation;
	private final KeyframeAnimation sleepAnimation;
	private final KeyframeAnimation sleepWithItemAnimation;
	private final KeyframeAnimation wakeAnimation;
	private final KeyframeAnimation wakeWithItemAnimation;
	private final KeyframeAnimation wakeAndShowItemAnimation;
	private final KeyframeAnimation wakeAndHideItemAnimation;
	private final KeyframeAnimation walkAnimation;
	private final KeyframeAnimation walkWithItemAnimation;
	//?} else {
	/*private final AnimationDefinition showItemAnimation;
	private final AnimationDefinition hideItemAnimation;
	private final AnimationDefinition sleepAnimation;
	private final AnimationDefinition sleepWithItemAnimation;
	private final AnimationDefinition wakeAnimation;
	private final AnimationDefinition wakeWithItemAnimation;
	private final AnimationDefinition wakeAndShowItemAnimation;
	private final AnimationDefinition wakeAndHideItemAnimation;
	private final AnimationDefinition walkAnimation;
	private final AnimationDefinition walkWithItemAnimation;
	*///?}

	public TuffGolemEntityModel(ModelPart root) {
		//? if >=1.21.3 {
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

		//? if >= 1.21.6 {
		this.showItemAnimation = TuffGolemAnimations.SHOW_ITEM.bake(root);
		this.hideItemAnimation = TuffGolemAnimations.HIDE_ITEM.bake(root);
		this.sleepAnimation = TuffGolemAnimations.SLEEP.bake(root);
		this.sleepWithItemAnimation = TuffGolemAnimations.SLEEP_WITH_ITEM.bake(root);
		this.wakeAnimation = TuffGolemAnimations.WAKE.bake(root);
		this.wakeWithItemAnimation = TuffGolemAnimations.WAKE_WITH_ITEM.bake(root);
		this.wakeAndShowItemAnimation = TuffGolemAnimations.WAKE_AND_SHOW_ITEM.bake(root);
		this.wakeAndHideItemAnimation = TuffGolemAnimations.WAKE_AND_HIDE_ITEM.bake(root);
		this.walkAnimation = TuffGolemAnimations.WALK.bake(root);
		this.walkWithItemAnimation = TuffGolemAnimations.WALK_WITH_ITEM.bake(root);
		//?} else {
		/*this.showItemAnimation = TuffGolemAnimations.SHOW_ITEM;
		this.hideItemAnimation = TuffGolemAnimations.HIDE_ITEM;
		this.sleepAnimation = TuffGolemAnimations.SLEEP;
		this.sleepWithItemAnimation = TuffGolemAnimations.SLEEP_WITH_ITEM;
		this.wakeAnimation = TuffGolemAnimations.WAKE;
		this.wakeWithItemAnimation = TuffGolemAnimations.WAKE_WITH_ITEM;
		this.wakeAndShowItemAnimation = TuffGolemAnimations.WAKE_AND_SHOW_ITEM;
		this.wakeAndHideItemAnimation = TuffGolemAnimations.WAKE_AND_HIDE_ITEM;
		this.walkAnimation = TuffGolemAnimations.WALK;
		this.walkWithItemAnimation = TuffGolemAnimations.WALK_WITH_ITEM;
		*///?}
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

	//? if <1.21.3 {
	/*@Override
	public ModelPart root() {
		return this.root;
	}
	*///?}

	@Override
	//? if >=1.21.3 {
	public void setupAnim(TuffGolemRenderState renderState)
	//?} else {
	/*public void setupAnim(T tuffGolem, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch)
	 *///?}
	{
		//? if >=1.21.3 {
		super.setupAnim(renderState);
		var tuffGolem = renderState.tuffGolem;
		var limbSwing = renderState.walkAnimationPos;
		var limbSwingAmount = renderState.walkAnimationSpeed;
		var ageInTicks = renderState.ageInTicks;
		//?} else {
		/*this.root().getAllParts().forEach(ModelPart::resetPose);
		*///?}

		var walkMultiplier = 4.0F * tuffGolem.getMovementSpeedModifier();

		if(tuffGolem.isHoldingItem()) {
			VersionedEntityModel.AnimateWalk(this, this.walkWithItemAnimation, limbSwing, limbSwingAmount, walkMultiplier, walkMultiplier);
		} else {
			VersionedEntityModel.AnimateWalk(this, this.walkAnimation, limbSwing, limbSwingAmount, walkMultiplier, walkMultiplier);
		}
		
		VersionedEntityModel.Animate(this, this.showItemAnimation, tuffGolem.showItemAnimationState, ageInTicks);
		VersionedEntityModel.Animate(this, this.hideItemAnimation, tuffGolem.hideItemAnimationState, ageInTicks);
		VersionedEntityModel.Animate(this, this.sleepAnimation, tuffGolem.sleepAnimationState, ageInTicks);
		VersionedEntityModel.Animate(this, this.sleepWithItemAnimation, tuffGolem.sleepWithItemAnimationState, ageInTicks);
		VersionedEntityModel.Animate(this, this.wakeAnimation, tuffGolem.wakeAnimationState, ageInTicks);
		VersionedEntityModel.Animate(this, this.wakeWithItemAnimation, tuffGolem.wakeWithItemAnimationState, ageInTicks);
		VersionedEntityModel.Animate(this, this.wakeAndShowItemAnimation, tuffGolem.wakeAndShowItemAnimationState, ageInTicks);
		VersionedEntityModel.Animate(this, this.wakeAndHideItemAnimation, tuffGolem.wakeAndHideItemAnimationState, ageInTicks);
	}
}