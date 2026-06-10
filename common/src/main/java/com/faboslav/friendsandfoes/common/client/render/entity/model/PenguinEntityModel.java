package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
import com.faboslav.friendsandfoes.common.entity.animation.PenguinAnimations;
import com.faboslav.friendsandfoes.common.util.animation.AnimationMath;
import com.faboslav.friendsandfoes.common.versions.VersionedEntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

//? if >= 1.21.6 {
import net.minecraft.client.animation.KeyframeAnimation;
//?} else {
/*import net.minecraft.client.animation.AnimationDefinition;
*///?}

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

	//? if >= 1.21.6 {
	private final KeyframeAnimation wingFlapAnimation;
	private final KeyframeAnimation idleAnimation;
	private final KeyframeAnimation idleWaterAnimation;
	private final KeyframeAnimation walkAnimation;
	private final KeyframeAnimation swimAnimation;
	//?} else {
	/*private final AnimationDefinition wingFlapAnimation;
	private final AnimationDefinition idleAnimation;
	private final AnimationDefinition idleWaterAnimation;
	private final AnimationDefinition walkAnimation;
	private final AnimationDefinition swimAnimation;
	*///?}

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

		//? if >= 1.21.6 {
		this.wingFlapAnimation = PenguinAnimations.WING_FLAP.bake(root);
		this.idleAnimation = PenguinAnimations.IDLE.bake(root);
		this.idleWaterAnimation = PenguinAnimations.IDLE_WATER.bake(root);
		this.walkAnimation = PenguinAnimations.WALK.bake(root);
		this.swimAnimation = PenguinAnimations.SWIM.bake(root);
		//?} else {
		/*this.wingFlapAnimation = PenguinAnimations.WING_FLAP;
		this.idleAnimation = PenguinAnimations.IDLE;
		this.idleWaterAnimation = PenguinAnimations.IDLE_WATER;
		this.walkAnimation = PenguinAnimations.WALK;
		this.swimAnimation = PenguinAnimations.SWIM;
		*///?}
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
	/*public void setupAnim(T penguin, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch)
	*///?}
	{
		//? >=1.21.3 {
		super.setupAnim(renderState);
		var penguin = renderState.penguin;
		var limbSwing = renderState.walkAnimationPos;
		var limbSwingAmount = renderState.walkAnimationSpeed;
		var ageInTicks = renderState.ageInTicks;
		//?} else {
		/*this.root().getAllParts().forEach(ModelPart::resetPose);
		*///?}

		var timeMultiplier = penguin.isUnderWater() ? 1.5F : 5.5F;
		var speedMultiplier = penguin.isUnderWater() ? 4.0F : 4.5F;

		VersionedEntityModel.Animate(this, this.wingFlapAnimation, penguin.wingFlapAnimationState, ageInTicks);
		VersionedEntityModel.Animate(this, this.idleAnimation, penguin.idleAnimationState, ageInTicks);
		VersionedEntityModel.Animate(this, this.idleWaterAnimation, penguin.idleWaterAnimationState, ageInTicks);

		if (penguin.isSwimming()) {
			VersionedEntityModel.AnimateWalk(this, this.swimAnimation, limbSwing, limbSwingAmount, timeMultiplier, speedMultiplier);
		} else {
			VersionedEntityModel.AnimateWalk(this, this.walkAnimation, limbSwing, limbSwingAmount, timeMultiplier, speedMultiplier);
		}

		animateSwimming(penguin, ageInTicks);
	}

	public void animateSwimming(
		final PenguinEntity penguin,
		final float ageInTicks
	) {
		float swimProgress = penguin.getSwimProgress(ageInTicks - penguin.tickCount);

		this.main.xRot = Mth.lerp(swimProgress, 0.0F, AnimationMath.toRadians(90.0F));
		this.main.y = Mth.lerp(swimProgress, 11.0F, 21.0F);
		this.main.z = Mth.lerp(swimProgress, 0.0F, -5.0F);

		this.head.xRot = Mth.lerp(swimProgress, 0.0F, AnimationMath.toRadians(-90.0F));
		this.head.y = Mth.lerp(swimProgress, -12.0F, -16.0F);
		this.head.z = Mth.lerp(swimProgress, 0.0F, -2.0F);
	}
}