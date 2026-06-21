//? if <= 1.21.8 {
/*package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.CopperGolemAnimations;
import com.faboslav.friendsandfoes.common.mixin.KeyframeAnimationMixin;
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
/^import net.minecraft.client.animation.AnimationDefinition;
 ^///?}


//? if >=1.21.3 {
import net.minecraft.client.model.EntityModel;
import com.faboslav.friendsandfoes.common.client.render.entity.state.CopperGolemRenderState;
//?} else {
/^import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import net.minecraft.client.model.HierarchicalModel;
^///?}

//? if >=1.21.3 {
public class CopperGolemEntityModel extends EntityModel<CopperGolemRenderState>
//?} else {
/^public final class CopperGolemEntityModel<T extends CopperGolemEntity> extends HierarchicalModel<T>
^///?}
{
	private static final String MODEL_PART_BODY = "body";
	private static final String MODEL_PART_LEFT_ARM = "leftArm";
	private static final String MODEL_PART_RIGHT_ARM = "rightArm";
	private static final String MODEL_PART_LEFT_LEG = "leftLeg";
	private static final String MODEL_PART_RIGHT_LEG = "rightLeg";
	private static final String MODEL_PART_HEAD = "head";
	private static final String MODEL_PART_NOSE = "nose";
	private static final String MODEL_PART_ROD = "rod";

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart nose;
	private final ModelPart rod;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	//? if >= 1.21.6 {
	private KeyframeAnimation walkAnimation;
	private KeyframeAnimation spinHeadAnimation;
	private KeyframeAnimation pressButtonUp;
	private KeyframeAnimation pressButtonDown;
	//?} else {
	/^private AnimationDefinition walkAnimation;
	private AnimationDefinition spinHeadAnimation;
	private AnimationDefinition pressButtonUp;
	private AnimationDefinition pressButtonDown;
	^///?}

	public CopperGolemEntityModel(ModelPart root) {
		//? if >=1.21.3 {
		super(root);
		//?}

		this.root = root;
		this.body = this.root.getChild(MODEL_PART_BODY);
		this.head = this.body.getChild(MODEL_PART_HEAD);
		this.nose = this.head.getChild(MODEL_PART_NOSE);
		this.rod = this.head.getChild(MODEL_PART_ROD);
		this.leftArm = this.body.getChild(MODEL_PART_LEFT_ARM);
		this.rightArm = this.body.getChild(MODEL_PART_RIGHT_ARM);
		this.leftLeg = this.root.getChild(MODEL_PART_LEFT_LEG);
		this.rightLeg = this.root.getChild(MODEL_PART_RIGHT_LEG);

		//? if >= 1.21.6 {
		this.walkAnimation = CopperGolemAnimations.WALK.bake(root);
		this.spinHeadAnimation = CopperGolemAnimations.SPIN_HEAD.bake(root);
		this.pressButtonUp = CopperGolemAnimations.PRESS_BUTTON_UP.bake(root);
		this.pressButtonDown = CopperGolemAnimations.PRESS_BUTTON_DOWN.bake(root);
		//?} else {
		/^this.walkAnimation = CopperGolemAnimations.WALK;
		this.spinHeadAnimation = CopperGolemAnimations.SPIN_HEAD;
		this.pressButtonUp = CopperGolemAnimations.PRESS_BUTTON_UP;
		this.pressButtonDown = CopperGolemAnimations.PRESS_BUTTON_DOWN;
		^///?}
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();

		root.addOrReplaceChild(MODEL_PART_BODY, CubeListBuilder.create().texOffs(40, 0).addBox(-4.0F, -5.0F, -2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.0F, 0.0F));
		PartDefinition body = root.getChild(MODEL_PART_BODY);

		body.addOrReplaceChild(MODEL_PART_HEAD, CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, -5.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(0.0F, -5.0F, 0.0F));
		PartDefinition head = body.getChild(MODEL_PART_HEAD);

		head.addOrReplaceChild(MODEL_PART_ROD, CubeListBuilder.create().texOffs(56, 10).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(40, 9).addBox(-2.0F, -7.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));
		head.addOrReplaceChild(MODEL_PART_NOSE, CubeListBuilder.create().texOffs(56, 15).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -4.0F));

		body.addOrReplaceChild(MODEL_PART_LEFT_ARM, CubeListBuilder.create().texOffs(10, 17).addBox(0.0F, -1.0F, -1.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -5.0F, 0.0F));
		body.addOrReplaceChild(MODEL_PART_RIGHT_ARM, CubeListBuilder.create().texOffs(0, 17).addBox(-2.0F, -1.0F, -1.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -5.0F, 0.0F));

		root.addOrReplaceChild(MODEL_PART_LEFT_LEG, CubeListBuilder.create().texOffs(34, 17).addBox(-2.0F, 0.0F, -1.5F, 4.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 19.0F, 0.0F));
		root.addOrReplaceChild(MODEL_PART_RIGHT_LEG, CubeListBuilder.create().texOffs(20, 17).addBox(-2.0F, 0.0F, -1.5F, 4.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 19.0F, 0.0F));

		return LayerDefinition.create(modelData, 64, 64);
	}

	//? if <1.21.3 {
	/^@Override
	public ModelPart root() {
		return this.root;
	}
	^///?}

	@Override
	//? if >=1.21.3 {
	public void setupAnim(CopperGolemRenderState renderState)
	//?} else {
	/^public void setupAnim(T copperGolem, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch)
	^///?}
	{
		//? if >=1.21.3 {
		super.setupAnim(renderState);
		var copperGolem = renderState.copperGolem;
		var limbSwing = renderState.walkAnimationPos;
		var limbSwingAmount = renderState.walkAnimationSpeed;
		var ageInTicks = renderState.ageInTicks;
		var headYaw = renderState.yRot;
		//?} else {
		/^this.root().getAllParts().forEach(ModelPart::resetPose);
		^///?}

		var animationSpeedModifier = copperGolem.getAnimationSpeedModifier();

		if (copperGolem.isOxidized()) {
			ageInTicks = copperGolem.getCurrentAnimationTick();
		}

		this.setHeadAngle(headYaw);
		//FriendsAndFoes.getLogger().info(String.valueOf(limbSwing));
		//FriendsAndFoes.getLogger().info(String.valueOf(limbSwingAmount));

		VersionedEntityModel.AnimateWalk(this, this.walkAnimation, limbSwing, limbSwingAmount, 2.5F * copperGolem.getMovementSpeedModifier(), 3.5F * copperGolem.getMovementSpeedModifier());
		VersionedEntityModel.Animate(this, this.spinHeadAnimation, copperGolem.spinHeadAnimationState, ageInTicks, animationSpeedModifier);
		VersionedEntityModel.Animate(this, this.pressButtonUp, copperGolem.pressButtonUpAnimationState, ageInTicks, animationSpeedModifier);
		VersionedEntityModel.Animate(this, this.pressButtonDown, copperGolem.pressButtonDownAnimationState, ageInTicks, animationSpeedModifier);
	}

	private void setHeadAngle(float yaw) {
		this.head.yRot = yaw * ((float) Math.PI / 180);
	}
}
*///?}