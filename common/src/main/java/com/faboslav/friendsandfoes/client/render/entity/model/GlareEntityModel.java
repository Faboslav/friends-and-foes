package com.faboslav.friendsandfoes.client.render.entity.model;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.animation.animator.ModelPartAnimator;
import com.faboslav.friendsandfoes.entity.GlareEntity;
import com.faboslav.friendsandfoes.util.animation.AnimationMath;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public final class GlareEntityModel<T extends GlareEntity> extends AnimatedEntityModel<T>
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
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();

		root.addChild(MODEL_PART_HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, 0.0F, -3.0F, 12.0F, 9.0F, 9.0F, new Dilation(-0.02F)), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

		ModelPartData head = root.getChild(MODEL_PART_HEAD);
		head.addChild(MODEL_PART_EYES, ModelPartBuilder.create().uv(33, 0).cuboid(2.0F, -1.0F, -0.3F, 2.0F, 2.0F, 1.0F, new Dilation(-0.2F)).uv(33, 0).cuboid(-4.0F, -1.0F, -0.3F, 2.0F, 2.0F, 1.0F, new Dilation(-0.2F)), ModelTransform.pivot(0.0F, 5.0F, -3.0F));
		head.addChild(MODEL_TOP_AZALEA,ModelPartBuilder.create().uv(0, 18).cuboid(-7.0F, 0.0F, -7.0F, 14.0F, 8.0F, 14.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		head.addChild(MODEL_BOTTOM_AZALEA, ModelPartBuilder.create().uv(18, 101).mirrored().cuboid(-7.0F, 0.75F, -7.0F, 14.0F, 0.0F, 14.0F, new Dilation(-0.01F)).mirrored(false).uv(0, 40).cuboid(-7.0F, -4.0F, -7.0F, 14.0F, 10.0F, 14.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 8.0F, 0.0F));

		ModelPartData bottomAzalea = head.getChild(MODEL_BOTTOM_AZALEA);
		bottomAzalea.addChild(MODEL_SECOND_LAYER, ModelPartBuilder.create().uv(0, 64).cuboid(-6.0F, 0.0F, -6.0F, 12.0F, 7.0F, 12.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

		ModelPartData secondLayer = bottomAzalea.getChild(MODEL_SECOND_LAYER);
		secondLayer.addChild(MODEL_THIRD_LAYER, ModelPartBuilder.create().uv(0, 83).cuboid(-5.0F, 0.0F, -5.0F, 10.0F, 7.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 0.0F));

		ModelPartData thirdLayer = secondLayer.getChild(MODEL_THIRD_LAYER);
		thirdLayer.addChild(MODEL_FOURTH_LAYER, ModelPartBuilder.create().uv(0, 100).cuboid(-4.0F, 0.0F, -4.0F, 8.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 0.0F));

		return TexturedModelData.of(modelData, 64, 128);
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
		this.getPart().traverse().forEach(ModelPart::resetTransform);

		this.animateEyes(glare);
		this.animateFloating(glare, animationProgress);

		if(limbAngle < 0.1F && limbDistance < 0.1F) {
			//FriendsAndFoes.getLogger().info("yas");
			//limbAngle = 0.1F;
			//limbDistance = 0.1F;
		}

		float movementForce = MathHelper.sin(limbAngle * 0.1F) * limbDistance * 0.75F;
		float absMovementForce = Math.abs(movementForce);

		this.head.pitch = AnimationMath.toRadians(40 * absMovementForce);
		this.head.roll = AnimationMath.toRadians(15 * movementForce);

		for (ModelPart layer : this.layers) {
			layer.pitch = AnimationMath.toRadians(20 * absMovementForce);
			layer.roll = AnimationMath.toRadians(10 * movementForce);
		}

		if(glare.isMoving()) {

		}

		//Vec3d velocity = glare.changeLookDirection();

		//var xVelocity = velocity.getComponentAlongAxis(Direction.Axis.X);
		//var zVelocity = velocity.getComponentAlongAxis(Direction.Axis.Z);

		//this.head.pitch = AnimationMath.toRadians(180 * velocity.x);
		//this.head.roll = AnimationMath.toRadians(-1* (180 * velocity.z));
		//this.head.roll = AnimationMath.toRadians(10* (Math.min(AnimationMath.lerp(animationProgress, glare.prevHeadYaw, glare.getHeadYaw()), 1)));

		//FriendsAndFoes.getLogger().info("pitch: " + String.valueOf(glare.getPitch()));
		//FriendsAndFoes.getLogger().info("prevpitch: " + String.valueOf(glare.prevPitch));


		//FriendsAndFoes.getLogger().info(String.valueOf(glare.getMovementDirection()));
		//FriendsAndFoes.getLogger().info("limbAngle: "+ limbAngle);
		//FriendsAndFoes.getLogger().info("limbDistance: "+ limbDistance);

		//FriendsAndFoes.getLogger().info(String.valueOf(velocity.x));
		float speed = 1.0F;
		float degree = 2.0F;

		//this.head.pitch = AnimationMath.toRadians(45 * Math.abs(AnimationMath.cos((limbAngle * 0.1F) * limbDistance * 0.1F + 17)));

		for (ModelPart layer : this.layers) {
			//layer.pitch = (float) (Math.cos(limbAngle));
				//layer.roll = currentRollLayerAnimationProgress * glare.getCurrentLayersRoll();
		}
		//this.animateHead(glare, animationProgress);
		//this.animateLayers(glare, animationProgress);
	}

	@Override
	public void animateModel(
		T glare,
		float limbAngle,
		float limbDistance,
		float tickDelta
	) {
		float targetLayerPitch;
		float targetLayerRoll;

		if (
			glare.isMoving()
			&& (glare.getHoldingEntity() instanceof LeashKnotEntity) == false
			&& glare.isGrumpy() == false
		) {
			targetLayerPitch = (float) Math.toRadians(10);
			targetLayerRoll = (float) Math.toRadians(1);
		} else {
			targetLayerPitch = (float) Math.toRadians(1);
			targetLayerRoll = (float) Math.toRadians(1);
		}

		tickDelta = (float) Math.abs(Math.sin(tickDelta)) * 0.1f;

		float layerPitch = MathHelper.lerp(
			tickDelta,
			glare.getCurrentLayersPitch(),
			targetLayerPitch
		);
		float layerRoll = MathHelper.lerp(tickDelta,
			glare.getCurrentLayersRoll(),
			targetLayerRoll
		);

		glare.setCurrentLayerPitch(layerPitch);
		glare.setCurrentLayerRoll(layerRoll);
	}

	private void animateFloating(
		T glare,
		float animationProgress
	) {
		if (glare.isGrumpy()) {
			ModelPartAnimator.setXPosition(this.root, AnimationMath.sin(animationProgress, 0.5F));
			ModelPartAnimator.setYPosition(this.root, AnimationMath.absSin(animationProgress, 0.1F));
			ModelPartAnimator.setYRotation(this.root, AnimationMath.sin(animationProgress, 0.05F));
		} else {
			float targetPivotY = glare.isSitting() ? 3.0F : 0.11F;
			animateModelPartYPositionBasedOnTicks(glare, this.root, targetPivotY, 10);

			float verticalFloatingProgress = AnimationMath.sin(animationProgress * 0.1F) * (glare.isSitting() ? 0.5F : 1.5F);
			float horizontalFloatingProgress = AnimationMath.cos(animationProgress * 0.05F) * (glare.isSitting() ? 0.5F : 1.0F);

			this.head.pivotY = verticalFloatingProgress;
			this.head.pivotX = horizontalFloatingProgress;
		}
	}

	private void animateEyes(T glare) {
		Vec2f targetEyesPositionOffset = glare.getTargetEyesPositionOffset();

		animateModelPartPositionBasedOnTicks(
			glare,
			this.eyes,
			this.eyes.pivotX + targetEyesPositionOffset.x,
			this.eyes.pivotY + targetEyesPositionOffset.y,
			this.eyes.pivotZ,
			GlareEntity.MIN_EYE_ANIMATION_TICK_AMOUNT
		);
	}
}