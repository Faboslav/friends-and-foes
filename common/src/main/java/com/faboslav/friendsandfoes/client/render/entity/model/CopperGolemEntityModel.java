package com.faboslav.friendsandfoes.client.render.entity.model;

import com.faboslav.friendsandfoes.entity.passive.CopperGolemEntity;
import com.faboslav.friendsandfoes.util.ModelAnimationHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CopperGolemEntityModel<T extends CopperGolemEntity> extends AbstractEntityModel<T>
{
	private static final String MODEL_PART_ROOT = "root";
	private static final String MODEL_PART_BODY = "body";
	private static final String MODEL_PART_LEFT_ARM = "leftArm";
	private static final String MODEL_PART_RIGHT_ARM = "rightArm";
	private static final String MODEL_PART_LEFT_LEG = "leftLeg";
	private static final String MODEL_PART_RIGHT_LEG = "rightLeg";
	private static final String MODEL_PART_HEAD = "head";
	private static final String MODEL_PART_NOSE = "nose";
	private static final String MODEL_PART_ROD = "rod";

	private final ModelPart head;
	private final ModelPart nose;
	private final ModelPart rod;
	private final ModelPart body;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	private float buttonPressAnimationProgress;

	public CopperGolemEntityModel(ModelPart root) {
		super(root);
		this.head = this.root.getChild(MODEL_PART_HEAD);
		this.nose = this.head.getChild(MODEL_PART_NOSE);
		this.rod = this.head.getChild(MODEL_PART_ROD);
		this.body = this.root.getChild(MODEL_PART_BODY);
		this.leftArm = this.root.getChild(MODEL_PART_LEFT_ARM);
		this.rightArm = this.root.getChild(MODEL_PART_RIGHT_ARM);
		this.leftLeg = this.root.getChild(MODEL_PART_LEFT_LEG);
		this.rightLeg = this.root.getChild(MODEL_PART_RIGHT_LEG);

		this.setCurrentModelTransforms(
			this.defaultModelTransforms,
			MODEL_PART_ROOT,
			this.root
		);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();

		// Add head
		root.addChild(MODEL_PART_HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -5.0F, -4.0F, 8.0F, 5.0F, 8.0F), ModelTransform.pivot(0.0F, 14.0F, 0.0F));

		ModelPartData head = root.getChild(MODEL_PART_HEAD);
		head.addChild(MODEL_PART_NOSE, ModelPartBuilder.create().uv(56, 15).cuboid(-1.0F, -1.0F, -2.0F, 2.0F, 3.0F, 2.0F), ModelTransform.pivot(0.0F, -1.0F, -4.0F));
		head.addChild(MODEL_PART_ROD, ModelPartBuilder.create().uv(56, 10).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F).uv(40, 10).cuboid(-2.0F, -7.0F, -2.0F, 4.0F, 4.0F, 4.0F), ModelTransform.pivot(0.0F, -5.0F, 0.0F));

		// Add body
		root.addChild(MODEL_PART_BODY, ModelPartBuilder.create().uv(40, 0).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 5.0F, 4.0F), ModelTransform.pivot(0.0F, 14.0F, 0.0F));
		root.addChild(MODEL_PART_LEFT_ARM, ModelPartBuilder.create().uv(10, 17).cuboid(0.0F, -1.0F, -1.5F, 2.0F, 10.0F, 3.0F), ModelTransform.pivot(4.0F, 14.0F, 0.0F));
		root.addChild(MODEL_PART_RIGHT_ARM, ModelPartBuilder.create().uv(0, 17).cuboid(-2.0F, -1.0F, -1.5F, 2.0F, 10.0F, 3.0F), ModelTransform.pivot(-4.0F, 14.0F, 0.0F));
		root.addChild(MODEL_PART_LEFT_LEG, ModelPartBuilder.create().uv(34, 17).cuboid(-2.0F, 0.0F, -1.5F, 4.0F, 5.0F, 3.0F), ModelTransform.pivot(2.0F, 19.0F, 0.0F));
		root.addChild(MODEL_PART_RIGHT_LEG, ModelPartBuilder.create().uv(20, 17).cuboid(-2.0F, 0.0F, -1.5F, 4.0F, 5.0F, 3.0F), ModelTransform.pivot(-2.0F, 19.0F, 0.0F));

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
		float tickDelta = ModelAnimationHelper.getTickDelta();

		if (copperGolem.isOxidized()) {
			NbtCompound modelAngles = copperGolem.getEntitySnapshot();

			tickDelta = modelAngles.getFloat("tickDelta");
			limbAngle = modelAngles.getFloat("limbAngle");
			limbDistance = modelAngles.getFloat("lastLimbDistance");
		} else {
			if (copperGolem.hurtTime == 0) {
				limbDistance *= 1.5;
				limbAngle *= 1.5;
			}
		}

		float headSpinAnimationProgress = copperGolem.getHeadSpinAnimationProgress();
		animationProgress = ModelAnimationHelper.getAnimationProgress(copperGolem, tickDelta);

		this.applyModelTransforms(
			this.defaultModelTransforms,
			MODEL_PART_ROOT,
			this.root
		);

		this.rightLeg.pitch = -1.5F * MathHelper.wrap(limbAngle, 13.0F) * limbDistance;
		this.leftLeg.pitch = 1.5F * MathHelper.wrap(limbAngle, 13.0F) * limbDistance;
		this.rightLeg.yaw = 0.0F;
		this.leftLeg.yaw = 0.0F;

		if (headSpinAnimationProgress > 0.0F && headSpinAnimationProgress <= 1.0F) {
			this.head.yaw = (float) MathHelper.lerp(headSpinAnimationProgress, 0, Math.PI * 2);
		} else {
			this.head.yaw = headYaw * 0.017453292F;
			this.head.pitch = headPitch * 0.017453292F;
		}

		if (copperGolem.getButtonPressAnimationProgress() > 0.0F) {
			if (copperGolem.getButtonPressAnimationProgress() == 1.0F) {
				this.leftArm.pitch = (float) Math.toRadians(-180 + 30 * MathHelper.abs(MathHelper.sin(animationProgress * 0.5F)));
				this.rightArm.pitch = (float) Math.toRadians(-180 + 30 * MathHelper.abs(MathHelper.cos(animationProgress * 0.5F)));
				this.leftArm.roll = (float) Math.toRadians(20);
				this.rightArm.roll = (float) Math.toRadians(-20);
			} else {
				this.leftArm.pitch = (float) Math.toRadians(-165 * this.buttonPressAnimationProgress);
				this.rightArm.pitch = (float) Math.toRadians(-165 * this.buttonPressAnimationProgress);
				this.leftArm.roll = (float) Math.toRadians(20 * this.buttonPressAnimationProgress);
				this.rightArm.roll = (float) Math.toRadians(-20 * this.buttonPressAnimationProgress);
			}
		} else {
			this.leftArm.pitch = (-0.2F + 1.5F * MathHelper.wrap(limbAngle, 13.0F)) * limbDistance;
			this.rightArm.pitch = (-0.2F - 1.5F * MathHelper.wrap(limbAngle, 13.0F)) * limbDistance;
		}

		if (
			copperGolem.isOnGround()
			&& copperGolem.isOxidized() == false
			&& (
				copperGolem.prevX != copperGolem.getX()
				|| copperGolem.prevZ != copperGolem.getZ()
			)
		) {
			this.rod.pivotY += MathHelper.abs(MathHelper.sin(animationProgress * 0.35F));
		}
	}

	public void animateModel(
		T copperGolem,
		float limbAngle,
		float limbDistance,
		float tickDelta
	) {
		tickDelta = ModelAnimationHelper.getTickDelta();

		if (copperGolem.isOxidized()) {
			NbtCompound modelAngles = copperGolem.getEntitySnapshot();
			tickDelta = modelAngles.getFloat("tickDelta");
		}

		this.buttonPressAnimationProgress = copperGolem.getLastButtonPressAnimationProgress() + (copperGolem.getButtonPressAnimationProgress() - copperGolem.getLastButtonPressAnimationProgress()) * tickDelta;
	}
}