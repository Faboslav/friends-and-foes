package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.client.render.entity.renderer.BarnacleEntityRenderer;
import com.faboslav.friendsandfoes.common.entity.BarnacleEntity;
import com.faboslav.friendsandfoes.common.entity.animation.BarnacleAnimations;
import com.faboslav.friendsandfoes.common.versions.VersionedEntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

//? if >= 1.21.6 {
import net.minecraft.client.animation.KeyframeAnimation;
//?} else {
/*import net.minecraft.client.animation.AnimationDefinition;
*///?}

//? if >=1.21.3 {
import net.minecraft.client.model.EntityModel;
import com.faboslav.friendsandfoes.common.client.render.entity.state.BarnacleRenderState;
//?} else {
/*import net.minecraft.client.model.HierarchicalModel;
*///?}

//? if >=1.21.3 {
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
	private static final float TENTACLE_LENGTH = 26.0F;
	private static final float MODEL_Y_OFFSET = 1.501F;
	private static final float PIXELS_PER_BLOCK = 16.0F;
	private static final float MAX_SWIMMING_PITCH = 45.0F;
	private static final int TENTACLE_ATTACK_ANIMATION_DELAY = Math.max(0, BarnacleEntity.TENTACLE_GRAB_DURATION - Math.round(BarnacleAnimations.TENTACLE_ATTACK.lengthInSeconds() * 20.0F));

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart tentacle;
	private final ModelPart topLeftMouth;
	private final ModelPart topRightMouth;
	private final ModelPart bottomLeftMouth;
	private final ModelPart bottomRightMouth;
	private final ModelPart kelp;

	//? if >= 1.21.6 {
	private final KeyframeAnimation idleAnimation;
	private final KeyframeAnimation swimAnimation;
	private final KeyframeAnimation tentacleAttackAnimation;
	private final KeyframeAnimation attackAnimation;
	//?} else {
	/*private final AnimationDefinition idleAnimation;
	private final AnimationDefinition swimAnimation;
	private final AnimationDefinition tentacleAttackAnimation;
	private final AnimationDefinition attackAnimation;
	*///?}

	public BarnacleEntityModel(ModelPart root) {
		//? if >=1.21.3 {
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

		//? if >= 1.21.6 {
		this.idleAnimation = BarnacleAnimations.IDLE.bake(root);
		this.swimAnimation = BarnacleAnimations.SWIM.bake(root);
		this.tentacleAttackAnimation = BarnacleAnimations.TENTACLE_ATTACK.bake(root);
		this.attackAnimation = BarnacleAnimations.ATTACK.bake(root);
		//?} else {
		/*this.idleAnimation = BarnacleAnimations.IDLE;
		this.swimAnimation = BarnacleAnimations.SWIM;
		this.tentacleAttackAnimation = BarnacleAnimations.TENTACLE_ATTACK;
		this.attackAnimation = BarnacleAnimations.ATTACK;
		*///?}
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();

		root.addOrReplaceChild(MODEL_PART_BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 18.0F, 0.0F));

		PartDefinition body = root.getChild(MODEL_PART_BODY);
		body.addOrReplaceChild(MODEL_PART_HEAD, CubeListBuilder.create().texOffs(0, 28).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 9.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 11.0F));
		body.addOrReplaceChild(MODEL_PART_TENTACLE, CubeListBuilder.create().texOffs(10, 37).addBox(-0.5F, -0.5F, -26.0F, 1.0F, 1.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 11.0F));
		body.addOrReplaceChild(MODEL_PART_TOP_LEFT_MOUTH, CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -4.0F, -22.0F, 6.0F, 6.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, 0.0F, 11.0F, 0.0F, 0.0F, -1.5708F));
		body.addOrReplaceChild(MODEL_PART_TOP_RIGHT_MOUTH, CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -4.0F, -22.0F, 6.0F, 6.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, 11.0F, 0.0F, 0.0F, 1.5708F));
		body.addOrReplaceChild(MODEL_PART_BOTTOM_LEFT_MOUTH, CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, 0.0F, -22.0F, 6.0F, 6.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, 0.0F, 11.0F));
		body.addOrReplaceChild(MODEL_PART_BOTTOM_RIGHT_MOUTH, CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -22.0F, 6.0F, 6.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 0.0F, 11.0F));
		body.addOrReplaceChild(MODEL_PART_KELP, CubeListBuilder.create().texOffs(0, 45).addBox(-4.0F, -4.0F, 1.0F, 8.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 19.0F));

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
	public void setupAnim(BarnacleRenderState renderState)
	//?} else {
	/*public void setupAnim(T barnacle, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch)
	*///?}
	{
		//? if >=1.21.3 {
		super.setupAnim(renderState);

		this.updateKeyframeAnimations(renderState.idleAnimationState, renderState.tentacleAttackAnimationState, renderState.attackAnimationState, renderState.walkAnimationPos, renderState.walkAnimationSpeed, renderState.ageInTicks);

		boolean tentacleAimsBody = this.updateTentacle(
			renderState.x,
			renderState.y,
			renderState.z,
			renderState.bodyRot,
			renderState.tentacleTargetPosition,
			renderState.tentacleExtension
		);

		if (!tentacleAimsBody) {
			this.updateSwimmingPitch(renderState.xRot, renderState.isInWater);
		}
		//?} else {
		/*this.root().getAllParts().forEach(ModelPart::resetPose);

		this.updateKeyframeAnimations(barnacle.idleAnimationState, barnacle.tentacleAttackAnimationState, barnacle.attackAnimationState, limbSwing, limbSwingAmount, ageInTicks);

		float partialTick = ageInTicks - barnacle.tickCount;
		Entity tentacleTarget = barnacle.getTentacleTarget();

		boolean tentacleAimsBody = this.updateTentacle(
			Mth.lerp(partialTick, barnacle.xOld, barnacle.getX()),
			Mth.lerp(partialTick, barnacle.yOld, barnacle.getY()),
			Mth.lerp(partialTick, barnacle.zOld, barnacle.getZ()),
			Mth.rotLerp(partialTick, barnacle.yBodyRotO, barnacle.yBodyRot),
			tentacleTarget == null ? null : BarnacleEntity.getTentacleAttachPosition(tentacleTarget, partialTick),
			barnacle.getTentacleExtension(partialTick)
		);

		if (!tentacleAimsBody) {
			this.updateSwimmingPitch(headPitch, barnacle.isInWater());
		}
		*///?}
	}

	public void updateKeyframeAnimations(
		AnimationState idleAnimationState,
		AnimationState tentacleAttackAnimationState,
		AnimationState attackAnimationState,
		float limbSwing,
		float limbSwingAmount,
		float ageInTicks
	) {
		VersionedEntityModel.animate(this, this.idleAnimation, idleAnimationState, ageInTicks);
		VersionedEntityModel.animateWalk(this, this.swimAnimation, limbSwing, limbSwingAmount, 2.5F, 4.0F);
		VersionedEntityModel.animate(this, this.tentacleAttackAnimation, tentacleAttackAnimationState, ageInTicks - TENTACLE_ATTACK_ANIMATION_DELAY);
		VersionedEntityModel.animate(this, this.attackAnimation, attackAnimationState, ageInTicks);
	}

	private void updateSwimmingPitch(float xRot, boolean isInWater) {
		if (!isInWater) {
			return;
		}

		this.body.xRot += Mth.clamp(xRot, -MAX_SWIMMING_PITCH, MAX_SWIMMING_PITCH) * Mth.DEG_TO_RAD;
	}

	private boolean updateTentacle(
		double x,
		double y,
		double z,
		float bodyRot,
		@Nullable Vec3 targetPosition,
		float extension
	) {
		if (targetPosition == null || extension <= 0.0F) {
			this.tentacle.visible = false;
			return false;
		}

		this.tentacle.visible = true;
		this.tentacle.resetPose();

		Vec3 relativePosition = targetPosition.subtract(x, y, z).yRot((bodyRot - 180.0F) * Mth.DEG_TO_RAD);
		Vec3 targetFromBody = new Vec3(
			-relativePosition.x / BarnacleEntityRenderer.SCALE * PIXELS_PER_BLOCK - this.body.x,
			(MODEL_Y_OFFSET - relativePosition.y / BarnacleEntityRenderer.SCALE) * PIXELS_PER_BLOCK - this.body.y,
			relativePosition.z / BarnacleEntityRenderer.SCALE * PIXELS_PER_BLOCK - this.body.z
		);

		this.body.yRot = getYaw(targetFromBody) * extension;
		this.body.xRot = getPitch(targetFromBody) * extension;

		Vec3 targetFromTentacle = targetFromBody
			.yRot(-this.body.yRot)
			.xRot(this.body.xRot)
			.subtract(this.tentacle.x, this.tentacle.y, this.tentacle.z);

		this.tentacle.yRot = getYaw(targetFromTentacle);
		this.tentacle.xRot = getPitch(targetFromTentacle);
		this.tentacle.zScale = (float) (targetFromTentacle.length() / TENTACLE_LENGTH) * extension;

		return true;
	}

	private static float getYaw(Vec3 direction) {
		return (float) Mth.atan2(-direction.x, -direction.z);
	}

	private static float getPitch(Vec3 direction) {
		return (float) Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z));
	}
}
