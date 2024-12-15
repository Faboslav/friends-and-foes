package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.client.render.entity.animation.WildfireAnimations;
import com.faboslav.friendsandfoes.common.client.render.entity.model.animation.KeyframeModelAnimator;
import com.faboslav.friendsandfoes.common.entity.WildfireEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

import java.util.List;

@Environment(EnvType.CLIENT)
public final class WildfireEntityModel<T extends WildfireEntity> extends HierarchicalModel<T>
{
	private static final String MODEL_PART_BODY = "body";
	private static final String MODEL_PART_HEAD = "head";
	private static final String MODEL_PART_HELMET = "helmet";
	private static final String MODEL_PART_SHIELDS = "shields";
	private static final String MODEL_PART_FRONT_SHIELD = "frontShield";
	private static final String MODEL_PART_RIGHT_SHIELD = "rightShield";
	private static final String MODEL_PART_BACK_SHIELD = "backShield";
	private static final String MODEL_PART_LEFT_SHIELD = "leftShield";

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart helmet;
	private final ModelPart shields;
	private final ModelPart frontShield;
	private final ModelPart rightShield;
	private final ModelPart backShield;
	private final ModelPart leftShield;

	private final List<ModelPart> shieldsModelParts;

	public WildfireEntityModel(ModelPart root) {
		this.root = root;
		this.body = this.root.getChild(MODEL_PART_BODY);
		this.head = this.body.getChild(MODEL_PART_HEAD);
		this.helmet = this.head.getChild(MODEL_PART_HELMET);
		this.shields = this.root.getChild(MODEL_PART_SHIELDS);
		this.frontShield = this.shields.getChild(MODEL_PART_FRONT_SHIELD);
		this.rightShield = this.shields.getChild(MODEL_PART_RIGHT_SHIELD);
		this.backShield = this.shields.getChild(MODEL_PART_BACK_SHIELD);
		this.leftShield = this.shields.getChild(MODEL_PART_LEFT_SHIELD);

		this.shieldsModelParts = this.shields.getAllParts().toList();
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();

		PartDefinition body = root.addOrReplaceChild(MODEL_PART_BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -21.0F, -2.0F, 4.0F, 21.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild(MODEL_PART_HEAD, CubeListBuilder.create().texOffs(0, 26).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

		head.addOrReplaceChild(MODEL_PART_HELMET, CubeListBuilder.create().texOffs(0, 43).addBox(-4.0F, -7.5F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 1.5F, 0.0F));

		PartDefinition shields = root.addOrReplaceChild(MODEL_PART_SHIELDS, CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		shields.addOrReplaceChild(MODEL_PART_FRONT_SHIELD, CubeListBuilder.create().texOffs(17, 0).addBox(-5.0F, 3.5F, -9.5F, 10.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, 0.0F, -0.2618F, 0.0F, 0.0F));
		shields.addOrReplaceChild(MODEL_PART_RIGHT_SHIELD, CubeListBuilder.create().texOffs(17, 0).addBox(-5.0F, 3.5F, -9.5F, 10.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, 0.0F, -0.2618F, 1.5708F, 0.0F));
		shields.addOrReplaceChild(MODEL_PART_BACK_SHIELD, CubeListBuilder.create().texOffs(17, 0).addBox(-5.0F, 3.5F, -9.5F, 10.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, 0.0F, -0.2618F, 3.1416F, 0.0F));
		shields.addOrReplaceChild(MODEL_PART_LEFT_SHIELD, CubeListBuilder.create().texOffs(17, 0).addBox(-5.0F, 3.5F, -9.5F, 10.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, 0.0F, -0.2618F, -1.5708F, 0.0F));

		return LayerDefinition.create(modelData, 64, 64);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(
		WildfireEntity wildfire,
		float limbAngle,
		float limbDistance,
		float animationProgress,
		float headYaw,
		float headPitch
	) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		int activeShieldsCount = wildfire.getActiveShieldsCount();

		float bodyCounterRotation = (float) Math.toRadians((wildfire.getPreciseBodyRotation(animationProgress) * -1.0F));

		/*
		ModelPartModelAnimator.animateModelPartRotationBasedOnTicks(
			wildfire.getAnimationContextTracker(),
			this.shields,
			wildfire.tickCount,
			this.shields.xRot,
			bodyCounterRotation,
			this.shields.zRot,
			1
		);*/

		/*
		float rotationSpeedMultiplier = Math.max(1, WildfireEntity.DEFAULT_ACTIVE_SHIELDS_COUNT - activeShieldsCount);
		float baseRotationUnit = (2.0F * (float) Math.PI) / activeShieldsCount;
		float bodyCounterRotation = (float) Math.toRadians((wildfire.prevBodyYaw * -1.0F));
		float bodyCounterRotation = 0.0F;
		float additionalShieldRotation = (animationProgress * 0.1F * rotationSpeedMultiplier) % (2.0F * (float) Math.PI);
		 */

		for (int i = 0; i < WildfireEntity.DEFAULT_ACTIVE_SHIELDS_COUNT; ++i) {
			this.shieldsModelParts.get(i).skipDraw = i > activeShieldsCount;
		}

		this.updateAnimations(wildfire, limbAngle, limbDistance, animationProgress);

		this.head.yRot = headYaw * 0.017453292F;
		this.head.xRot = headPitch * 0.017453292F;
	}

	public void updateAnimations(
		WildfireEntity wildfire,
		float limbAngle,
		float limbDistance,
		float animationProgress
	) {
		var movementAnimation = wildfire.getMovementAnimation();
		var animations = wildfire.getTrackedAnimations();
		var animationContextTracker = wildfire.getAnimationContextTracker();
		var currentTick = wildfire.tickCount;
		var animationSpeedModifier = wildfire.getAnimationSpeedModifier();

		KeyframeModelAnimator.updateMovementKeyframeAnimations(this, movementAnimation, limbAngle, limbDistance, 1.0F, 1.0F, animationSpeedModifier);
		KeyframeModelAnimator.updateStaticKeyframeAnimation(this, animationContextTracker, WildfireAnimations.SHIELD_ROTATION, currentTick, animationProgress, animationSpeedModifier);
		KeyframeModelAnimator.updateKeyframeAnimations(this, animationContextTracker, animations, currentTick, animationProgress, animationSpeedModifier);
	}
}
