package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.client.render.entity.animation.KeyframeAnimation;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.context.KeyframeAnimationContext;
import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.entity.pose.CopperGolemEntityPose;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.entity.AnimationState;
import net.minecraft.nbt.NbtCompound;

@Environment(EnvType.CLIENT)
public final class CopperGolemEntityModel<T extends CopperGolemEntity> extends AnimatedEntityModel<T>
{
	private static final String MODEL_PART_BODY = "body";
	private static final String MODEL_PART_LEFT_ARM = "leftArm";
	private static final String MODEL_PART_RIGHT_ARM = "rightArm";
	private static final String MODEL_PART_LEFT_LEG = "leftLeg";
	private static final String MODEL_PART_RIGHT_LEG = "rightLeg";
	private static final String MODEL_PART_HEAD = "head";
	private static final String MODEL_PART_NOSE = "nose";
	private static final String MODEL_PART_ROD = "rod";

	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart nose;
	private final ModelPart rod;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public CopperGolemEntityModel(ModelPart root) {
		super(root);
		this.body = this.root.getChild(MODEL_PART_BODY);
		this.head = this.body.getChild(MODEL_PART_HEAD);
		this.nose = this.head.getChild(MODEL_PART_NOSE);
		this.rod = this.head.getChild(MODEL_PART_ROD);
		this.leftArm = this.body.getChild(MODEL_PART_LEFT_ARM);
		this.rightArm = this.body.getChild(MODEL_PART_RIGHT_ARM);
		this.leftLeg = this.root.getChild(MODEL_PART_LEFT_LEG);
		this.rightLeg = this.root.getChild(MODEL_PART_RIGHT_LEG);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();

		root.addChild(MODEL_PART_BODY, ModelPartBuilder.create().uv(40, 0).cuboid(-4.0F, -5.0F, -2.0F, 8.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 19.0F, 0.0F));
		ModelPartData body = root.getChild(MODEL_PART_BODY);

		body.addChild(MODEL_PART_HEAD, ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-4.0F, -5.0F, -4.0F, 8.0F, 5.0F, 8.0F, new Dilation(0.001F)).mirrored(false), ModelTransform.pivot(0.0F, -5.0F, 0.0F));
		ModelPartData head = body.getChild(MODEL_PART_HEAD);

		head.addChild(MODEL_PART_ROD, ModelPartBuilder.create().uv(56, 10).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F))
			.uv(40, 9).cuboid(-2.0F, -7.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.0F, 0.0F));
		head.addChild(MODEL_PART_NOSE, ModelPartBuilder.create().uv(56, 15).cuboid(-1.0F, -1.0F, -2.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, -4.0F));

		body.addChild(MODEL_PART_LEFT_ARM, ModelPartBuilder.create().uv(10, 17).cuboid(0.0F, -1.0F, -1.5F, 2.0F, 10.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, -5.0F, 0.0F));
		body.addChild(MODEL_PART_RIGHT_ARM, ModelPartBuilder.create().uv(0, 17).cuboid(-2.0F, -1.0F, -1.5F, 2.0F, 10.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, -5.0F, 0.0F));

		root.addChild(MODEL_PART_LEFT_LEG, ModelPartBuilder.create().uv(34, 17).cuboid(-2.0F, 0.0F, -1.5F, 4.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 19.0F, 0.0F));
		root.addChild(MODEL_PART_RIGHT_LEG, ModelPartBuilder.create().uv(20, 17).cuboid(-2.0F, 0.0F, -1.5F, 4.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 19.0F, 0.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(
		T copperGolem,
		float limbAngle,
		float limbDistance,
		float animationProgress,
		float headYaw,
		float headPitch
	) {
		if (copperGolem.isOxidized()) {
			NbtCompound entitySnapshot = copperGolem.getEntitySnapshot();

			if (entitySnapshot.isEmpty() == false) {
				limbAngle = entitySnapshot.getFloat("limbAngle");
				limbDistance = entitySnapshot.getFloat("lastLimbDistance");
			}

			animationProgress = copperGolem.age;
		}

		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngle(headYaw);

		if (copperGolem.isOxidized()) {
			this.updateStatueKeyframeAnimation(copperGolem);
		}

		this.updateMovementKeyframeAnimations(copperGolem, limbAngle, limbDistance, 2.5F * copperGolem.getAnimationSpeedModifier(), 3.5F * copperGolem.getAnimationSpeedModifier());
		this.updateKeyframeAnimations(copperGolem, animationProgress);
	}

	private void setHeadAngle(float yaw) {
		this.head.yaw = yaw * ((float) Math.PI / 180);
	}

	private void updateStatueKeyframeAnimation(CopperGolemEntity copperGolem) {
		if (copperGolem.isInPose(CopperGolemEntityPose.IDLE)) {
			return;
		}

		KeyframeAnimation keyframeAnimation = copperGolem.getKeyframeAnimationByPose();

		if (keyframeAnimation == null) {
			return;
		}

		int initialTick = copperGolem.age - copperGolem.getCurrentKeyframeAnimationTick();
		int currentTick = copperGolem.age;

		AnimationContextTracker animationContextTracker = copperGolem.getAnimationContextTracker();
		KeyframeAnimationContext keyframeAnimationContext = animationContextTracker.get(keyframeAnimation);
		keyframeAnimationContext.setInitialTick(initialTick);
		keyframeAnimationContext.setCurrentTick(currentTick);
		AnimationState animationState = new AnimationState();
		keyframeAnimationContext.setAnimationState(animationState);
		animationState.start(initialTick);
	}
}