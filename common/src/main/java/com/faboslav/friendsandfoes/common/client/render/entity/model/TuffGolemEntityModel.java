package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;

@Environment(EnvType.CLIENT)
public final class TuffGolemEntityModel<T extends TuffGolemEntity> extends AnimatedEntityModel<T>
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

	private final ModelPart body;
	private final ModelPart nose;
	private final ModelPart frontCloth;
	private final ModelPart backCloth;
	private final ModelPart clothStand;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public TuffGolemEntityModel(ModelPart root) {
		super(root);
		this.body = this.root.getChild(MODEL_PART_BODY);
		this.nose = this.body.getChild(MODEL_PART_NOSE);
		this.frontCloth = this.body.getChild(MODEL_PART_FRONT_CLOTH);
		this.backCloth = this.body.getChild(MODEL_PART_BACK_CLOTH);
		this.clothStand = this.body.getChild(MODEL_PART_CLOTH_STAND);
		this.leftArm = this.body.getChild(MODEL_PART_LEFT_ARM);
		this.rightArm = this.body.getChild(MODEL_PART_RIGHT_ARM);
		this.leftLeg = this.root.getChild(MODEL_PART_LEFT_LEG);
		this.rightLeg = this.root.getChild(MODEL_PART_RIGHT_LEG);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();

		root.addChild(MODEL_PART_BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -12.0F, -4.0F, 8.0F, 13.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 19.0F, 0.0F));

		ModelPartData body = root.getChild(MODEL_PART_BODY);
		body.addChild(MODEL_PART_NOSE, ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -1.0F, -2.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -7.0F, -4.0F));
		body.addChild(MODEL_PART_FRONT_CLOTH, ModelPartBuilder.create().uv(36, 4).cuboid(-4.0F, 0.0F, 0.0F, 8.0F, 6.0F, 4.0F, new Dilation(0.02F)), ModelTransform.pivot(0.0F, -4.0F, -4.0F));
		body.addChild(MODEL_PART_BACK_CLOTH, ModelPartBuilder.create().uv(40, 18).cuboid(-4.0F, 0.0F, 0.0F, 8.0F, 6.0F, 4.0F, new Dilation(0.02F)), ModelTransform.pivot(0.0F, -4.0F, 0.0F));
		body.addChild(MODEL_PART_CLOTH_STAND, ModelPartBuilder.create().uv(0, 35).cuboid(-4.0F, 0.0F, -4.0F, 8.0F, 5.0F, 7.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, -4.0F, 0.0F));
		body.addChild(MODEL_PART_LEFT_ARM, ModelPartBuilder.create().uv(0, 21).cuboid(-1.0F, -2.0F, -2.0F, 2.0F, 10.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, -4.0F, 0.0F));
		body.addChild(MODEL_PART_RIGHT_ARM, ModelPartBuilder.create().uv(0, 21).mirrored(true).cuboid(0.0F, -2.0F, -2.0F, 2.0F, 10.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, -4.0F, 0.0F));

		root.addChild(MODEL_PART_LEFT_LEG, ModelPartBuilder.create().uv(12, 21).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(-0.0005F)), ModelTransform.pivot(-2.0F, 19.0F, 0.0F));
		root.addChild(MODEL_PART_RIGHT_LEG, ModelPartBuilder.create().uv(12, 21).mirrored(true).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(-0.0005F)), ModelTransform.pivot(2.0F, 19.0F, 0.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(
		T tuffGolem,
		float limbAngle,
		float limbDistance,
		float animationProgress,
		float headYaw,
		float headPitch
	) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.updateMovementKeyframeAnimations(tuffGolem, limbAngle, limbDistance, 4.0F * tuffGolem.getMovementSpeedModifier(), 4.0F * tuffGolem.getMovementSpeedModifier());
		this.updateKeyframeAnimations(tuffGolem, animationProgress);
	}
}