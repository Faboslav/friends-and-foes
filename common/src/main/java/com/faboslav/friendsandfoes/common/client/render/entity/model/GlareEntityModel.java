package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.ModelPartAnimator;
import com.faboslav.friendsandfoes.common.client.render.entity.model.animation.ModelPartModelAnimator;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.util.animation.AnimationMath;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;

//? >=1.21.3 {
import net.minecraft.client.model.EntityModel;
import com.faboslav.friendsandfoes.common.client.render.entity.state.GlareRenderState;
import net.minecraft.client.model.geom.builders.MeshTransformer;
//?} else {
/*import net.minecraft.client.model.HierarchicalModel;
 *///?}

//? >=1.21.3 {
public class GlareEntityModel extends EntityModel<GlareRenderState>
//?} else {
/*public final class GlareEntityModel<T extends GlareEntity> extends HierarchicalModel<T>
*///?}
{
	//? >=1.21.3 {
	public static final MeshTransformer BABY_TRANSFORMER = MeshTransformer.scaling(GlareEntity.BABY_SCALE);
	public static final MeshTransformer ADULT_TRANSFORMER = MeshTransformer.scaling(GlareEntity.ADULT_SCALE);
	//?}

	private static final String MODEL_PART_BODY = "body";
	private static final String MODEL_PART_HEAD = "head";
	private static final String MODEL_PART_EYES = "eyes";
	private static final String MODEL_TOP_AZALEA = "topAzalea";
	private static final String MODEL_BOTTOM_AZALEA = "bottomAzalea";
	private static final String MODEL_SECOND_LAYER = "secondLayer";
	private static final String MODEL_THIRD_LAYER = "thirdLayer";
	private static final String MODEL_FOURTH_LAYER = "fourthLayer";

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart eyes;
	private final ModelPart topAzalea;
	private final ModelPart bottomAzalea;
	private final ModelPart secondLayer;
	private final ModelPart thirdLayer;
	private final ModelPart fourthLayer;

	private final ModelPart[] layers;

	public GlareEntityModel(ModelPart root) {
		//? >=1.21.3 {
		super(root);
		//?}

		this.root = root;
		this.body = this.root.getChild(MODEL_PART_BODY);
		this.head = this.body.getChild(MODEL_PART_HEAD);
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

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();

		root.addOrReplaceChild(MODEL_PART_BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));
		PartDefinition body = root.getChild(MODEL_PART_BODY);

		body.addOrReplaceChild(MODEL_PART_HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 0.0F, -3.0F, 12.0F, 9.0F, 9.0F, new CubeDeformation(-0.02F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition head = body.getChild(MODEL_PART_HEAD);

		head.addOrReplaceChild(MODEL_PART_EYES, CubeListBuilder.create().texOffs(33, 0).addBox(2.0F, -1.0F, -0.3F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(33, 0).addBox(-4.0F, -1.0F, -0.3F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 5.0F, -3.0F));
		head.addOrReplaceChild(MODEL_TOP_AZALEA, CubeListBuilder.create().texOffs(0, 18).addBox(-7.0F, 0.0F, -7.0F, 14.0F, 8.0F, 14.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		head.addOrReplaceChild(MODEL_BOTTOM_AZALEA, CubeListBuilder.create().texOffs(18, 101).mirror().addBox(-7.0F, 0.75F, -7.0F, 14.0F, 0.0F, 14.0F, new CubeDeformation(-0.01F)).mirror(false).texOffs(0, 40).addBox(-7.0F, -4.0F, -7.0F, 14.0F, 10.0F, 14.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition bottomAzalea = head.getChild(MODEL_BOTTOM_AZALEA);
		bottomAzalea.addOrReplaceChild(MODEL_SECOND_LAYER, CubeListBuilder.create().texOffs(0, 64).addBox(-6.0F, 0.0F, -6.0F, 12.0F, 7.0F, 12.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition secondLayer = bottomAzalea.getChild(MODEL_SECOND_LAYER);
		secondLayer.addOrReplaceChild(MODEL_THIRD_LAYER, CubeListBuilder.create().texOffs(0, 83).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition thirdLayer = secondLayer.getChild(MODEL_THIRD_LAYER);
		thirdLayer.addOrReplaceChild(MODEL_FOURTH_LAYER, CubeListBuilder.create().texOffs(0, 100).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		return LayerDefinition.create(modelData, 64, 128);
	}

	//? <1.21.3 {
	/*@Override
	public ModelPart root() {
		return this.root;
	}
	*///?}

	@Override
	//? >=1.21.3 {
	public void setupAnim(GlareRenderState renderState)
	//?} else {
	/*public void setupAnim(T glare, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch)
	*///?}
	{
		//? >=1.21.3 {
		var glare = renderState.glare;
		var limbAngle = renderState.walkAnimationPos;
		var limbDistance = renderState.walkAnimationSpeed;
		var animationProgress = renderState.ageInTicks;
		//?}

		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateEyes(glare);
		this.animateFloating(glare, animationProgress);

		float movementForce = Mth.sin(limbAngle * 0.1F) * limbDistance * 0.75F;
		float absMovementForce = Math.abs(movementForce);

		if (absMovementForce >= 0.001F) {
			this.head.xRot = AnimationMath.toRadians(40 * absMovementForce);
			this.head.zRot = AnimationMath.toRadians(15 * movementForce);

			for (ModelPart layer : this.layers) {
				layer.xRot = AnimationMath.toRadians(30 * absMovementForce);
				layer.zRot = AnimationMath.toRadians(15 * movementForce);
			}
		} else {
			this.head.xRot = AnimationMath.toRadians(0.5F * AnimationMath.sin(animationProgress * 0.125F));
			this.head.zRot = AnimationMath.toRadians(0.5F * AnimationMath.cos(animationProgress * 0.125F));

			for (ModelPart layer : this.layers) {
				layer.xRot = AnimationMath.toRadians(0.75F * AnimationMath.sin(animationProgress * 0.1F));
				layer.zRot = AnimationMath.toRadians(0.75F * AnimationMath.cos(animationProgress * 0.1F));
			}
		}
	}

	private void animateFloating(
		GlareEntity glare,
		float animationProgress
	) {
		float verticalFloatingSpeed = glare.isGrumpy() ? 0.3F:0.1F;
		float horizontalFloatingSpeed = glare.isGrumpy() ? 0.15F:0.05F;

		float verticalFloatingOffset;
		float horizontalFloatingOffset;

		if (glare.isOrderedToSit()) {
			verticalFloatingOffset = 0.5F;
			horizontalFloatingOffset = 0.5F;
		} else {
			verticalFloatingOffset = 1.5F;
			horizontalFloatingOffset = 1.0F;
		}

		float targetPivotY = glare.isOrderedToSit() ? 3.0F : 0.11F;
		ModelPartModelAnimator.animateModelPartYPositionBasedOnTicks(glare.getAnimationContextTracker(), this.body, glare.tickCount, targetPivotY, 10);

		if (glare.isGrumpy()) {
			ModelPartAnimator.setXPosition(this.root, AnimationMath.sin(animationProgress, 0.5F));
			ModelPartAnimator.setYPosition(this.root, AnimationMath.absSin(animationProgress, 0.1F));
			ModelPartAnimator.setYRotation(this.root, AnimationMath.sin(animationProgress, 0.05F));
		}

		float verticalFloatingProgress = AnimationMath.sin(animationProgress * verticalFloatingSpeed) * verticalFloatingOffset;
		float horizontalFloatingProgress = AnimationMath.cos(animationProgress * horizontalFloatingSpeed) * horizontalFloatingOffset;

		this.head.y = verticalFloatingProgress;
		this.head.x = horizontalFloatingProgress;
	}

	private void animateEyes(GlareEntity glare) {
		Vec2 targetEyesPositionOffset = glare.getTargetEyesPositionOffset();

		ModelPartModelAnimator.animateModelPartPositionBasedOnTicks(
			glare.getAnimationContextTracker(),
			this.eyes,
			glare.tickCount,
			this.eyes.x + targetEyesPositionOffset.x,
			this.eyes.y + targetEyesPositionOffset.y,
			this.eyes.z,
			GlareEntity.MIN_EYE_ANIMATION_TICK_AMOUNT
		);
	}
}