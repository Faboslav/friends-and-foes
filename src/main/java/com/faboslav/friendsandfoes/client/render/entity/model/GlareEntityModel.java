package com.faboslav.friendsandfoes.client.render.entity.model;

import com.faboslav.friendsandfoes.entity.passive.GlareEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class GlareEntityModel<T extends GlareEntity> extends AbstractEntityModel<T>
{
	private static final String MODEL_PART_ROOT = "root";
	private static final String MODEL_PART_HEAD = "head";
	private static final String MODEL_PART_EYES = "eyes";
	private static final String MODEL_TOP_AZALEA = "topAzalea";
	private static final String MODEL_BOTTOM_AZALEA = "bottomAzalea";
	private static final String MODEL_SECOND_LAYER = "secondLayer";
	private static final String MODEL_THIRD_LAYER = "thirdLayer";
	private static final String MODEL_FOURTH_LAYER = "fourthLayer";

	private final ModelPart head;
	private final ModelPart eyes;
	private final ModelPart topAzalea;
	private final ModelPart bottomAzalea;
	private final ModelPart secondLayer;
	private final ModelPart thirdLayer;
	private final ModelPart fourthLayer;

	private final ModelPart[] layers;

	private float layerPitch;
	private float layerRoll;
	private float pitchLayerAnimationProgress;
	private float rollLayerAnimationProgress;
	private float eyesOffsetPivotX;
	private float eyesOffsetPivotY;

	public GlareEntityModel(ModelPart root) {
		super(root);
		this.head = this.root.getChild(MODEL_PART_HEAD);
		this.eyes = this.head.getChild(MODEL_PART_EYES);
		this.topAzalea = this.head.getChild(MODEL_TOP_AZALEA);
		this.bottomAzalea = this.head.getChild(MODEL_BOTTOM_AZALEA);
		this.secondLayer = this.bottomAzalea.getChild(MODEL_SECOND_LAYER);
		this.thirdLayer = this.secondLayer.getChild(MODEL_THIRD_LAYER);
		this.fourthLayer = this.thirdLayer.getChild(MODEL_FOURTH_LAYER);

		this.layers = new ModelPart[]{
			this.secondLayer,
			this.thirdLayer,
			this.fourthLayer,
		};

		this.setCurrentModelTransforms(
			this.defaultModelTransforms,
			MODEL_PART_ROOT,
			this.root
		);

		this.eyesOffsetPivotX = 0.0F;
		this.eyesOffsetPivotY = 0.0F;
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();

		root.addChild(MODEL_PART_HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, 0.0F, -3.0F, 12.0F, 9.0F, 9.0F, new Dilation(-0.02F)), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

		ModelPartData head = root.getChild(MODEL_PART_HEAD);
		head.addChild(MODEL_PART_EYES, ModelPartBuilder.create().uv(36, 0).cuboid(2.0F, -1.0F, -0.3F, 2.0F, 2.0F, 1.0F, new Dilation(-0.29F)).uv(36, 0).cuboid(-4.0F, -1.0F, -0.3F, 2.0F, 2.0F, 1.0F, new Dilation(-0.29F)), ModelTransform.pivot(0.0F, 5.0F, -3.0F));
		head.addChild(MODEL_TOP_AZALEA, ModelPartBuilder.create().uv(72, 0).cuboid(-7.0F, 0.0F, -7.0F, 14.0F, 8.0F, 14.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		head.addChild(MODEL_BOTTOM_AZALEA, ModelPartBuilder.create().uv(0, 114).cuboid(-7.0F, 0.75F, -7.0F, 14.0F, 0.0F, 14.0F, new Dilation(-0.01F)).uv(72, 22).cuboid(-7.0F, -4.0F, -7.0F, 14.0F, 10.0F, 14.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 8.0F, 0.0F));

		ModelPartData bottomAzalea = head.getChild(MODEL_BOTTOM_AZALEA);
		bottomAzalea.addChild(MODEL_SECOND_LAYER, ModelPartBuilder.create().uv(80, 46).cuboid(-6.0F, 0.0F, -6.0F, 12.0F, 7.0F, 12.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

		ModelPartData secondLayer = bottomAzalea.getChild(MODEL_SECOND_LAYER);
		secondLayer.addChild(MODEL_THIRD_LAYER, ModelPartBuilder.create().uv(88, 65).cuboid(-5.0F, 0.0F, -5.0F, 10.0F, 7.0F, 10.0F), ModelTransform.pivot(0.0F, 2.0F, 0.0F));

		ModelPartData thirdLayer = secondLayer.getChild(MODEL_THIRD_LAYER);
		thirdLayer.addChild(MODEL_FOURTH_LAYER, ModelPartBuilder.create().uv(96, 82).cuboid(-4.0F, 0.0F, -4.0F, 8.0F, 7.0F, 8.0F), ModelTransform.pivot(0.0F, 2.0F, 0.0F));

		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void animateModel(
		T glare,
		float limbAngle,
		float limbDistance,
		float tickDelta
	) {
		boolean isMoving = !glare.isOnGround() && glare.getVelocity().lengthSquared() >= 0.0001;

		float targetLayerPitch;
		float targetLayerRoll;

		if (
			isMoving
			&& !(glare.getHoldingEntity() instanceof LeashKnotEntity)
			&& !glare.isGrumpy()
		) {
			targetLayerPitch = (float) Math.toRadians(10);
			targetLayerRoll = (float) Math.toRadians(1);
		} else {
			targetLayerPitch = (float) Math.toRadians(1);
			targetLayerRoll = (float) Math.toRadians(1);
		}

		this.layerPitch = MathHelper.lerp((float) Math.abs(Math.sin(tickDelta)) * 0.1f, this.layerPitch, targetLayerPitch);
		this.layerRoll = MathHelper.lerp((float) Math.abs(Math.sin(tickDelta)) * 0.1f, this.layerRoll, targetLayerRoll);
	}

	@Override
	public void setAngles(
		T glare,
		float limbAngle,
		float limbDistance,
		float animationProgress,
		float headYaw,
		float headPitch
	) {
		this.applyModelTransforms(
			this.defaultModelTransforms,
			MODEL_PART_ROOT,
			this.root
		);

		this.animateEyes(
			glare,
			animationProgress
		);
		this.animateHead(
			glare,
			animationProgress
		);
		this.animateLayers(
			glare,
			animationProgress
		);
	}

	private void animateHead(
		T glare,
		float animationProgress
	) {
		if (glare.isGrumpy()) {
			this.root.pivotX = MathHelper.sin(animationProgress * 0.25F) * 0.4F;
			this.root.pivotY = Math.abs(0.25F + MathHelper.sin(animationProgress * 0.25F)) * -10.0F;
			this.root.yaw = MathHelper.sin(animationProgress * 0.5F) * 0.01F;
			//this.root.roll = MathHelper.sin(animationProgress * 0.1F) * 0.05F;
		}

		//this.topAzalea.pivotX = MathHelper.sin((animationProgress * 0.1F)) * 0.1F;
		//this.bottomAzalea.pivotX = MathHelper.sin((animationProgress * 0.1F)) * 0.1F;
	}

	private void animateEyes(
		T glare,
		float animationProgress
	) {
		float targetEyesOffsetPivotX = glare.getEyePositionOffset().x;
		float targetEyesOffsetPivotY = glare.getEyePositionOffset().y;
		animationProgress = (float) Math.abs(Math.sin(animationProgress)) * 0.5f;

		this.eyesOffsetPivotX = MathHelper.lerp(
			animationProgress,
			this.eyesOffsetPivotX,
			targetEyesOffsetPivotX
		);
		this.eyesOffsetPivotY = MathHelper.lerp(
			animationProgress,
			this.eyesOffsetPivotY,
			targetEyesOffsetPivotY
		);

		this.eyes.pivotX += this.eyesOffsetPivotX;
		this.eyes.pivotY += this.eyesOffsetPivotY;
	}

	private void animateLayers(
		T glare,
		float animationProgress
	) {
		for (int i = 0; i < this.layers.length; ++i) {
			float layerAnimationProgress = (animationProgress * 0.1F);
			float targetPitchLayerAnimationProgress = (float) Math.sin(layerAnimationProgress);
			float targetRollLayerAnimationProgress = (float) Math.cos(layerAnimationProgress);

			if (glare.isMoving()) {
				targetPitchLayerAnimationProgress = Math.abs(targetPitchLayerAnimationProgress);
				targetRollLayerAnimationProgress = Math.abs(targetRollLayerAnimationProgress);
			}

			this.pitchLayerAnimationProgress = MathHelper.lerp(
				(float) Math.abs(Math.sin(animationProgress)) * 0.1f,
				this.pitchLayerAnimationProgress,
				targetPitchLayerAnimationProgress
			);

			this.rollLayerAnimationProgress = MathHelper.lerp(
				(float) Math.abs(Math.sin(animationProgress)) * 0.1F,
				this.rollLayerAnimationProgress,
				targetRollLayerAnimationProgress
			);

			this.layers[i].pitch = this.pitchLayerAnimationProgress * this.layerPitch;
			this.layers[i].roll = this.rollLayerAnimationProgress * this.layerRoll;
		}
	}
}