package com.faboslav.friendsandfoes.client.render.entity.model;

import com.faboslav.friendsandfoes.entity.BarnacleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public final class BarnacleEntityModel<T extends BarnacleEntity> extends AnimatedEntityModel<T>
{
	private static final String MODEL_PART_HEAD = "head";
	private static final String MODEL_PART_TENTACLE = "tentacle";
	private static final String MODEL_PART_TOP_LEFT_MOUTH = "topLeftMouth";
	private static final String MODEL_PART_TOP_RIGHT_MOUTH = "topRightMouth";
	private static final String MODEL_PART_BOTTOM_LEFT_MOUTH = "bottomLeftMouth";
	private static final String MODEL_PART_BOTTOM_RIGHT_MOUTH = "bottomRightMouth";
	private static final String MODEL_PART_KELP = "kelp";

	private final ModelPart head;
	private final ModelPart tentacle;
	private final ModelPart topLeftMouth;
	private final ModelPart topRightMouth;
	private final ModelPart bottomLeftMouth;
	private final ModelPart bottomRightMouth;
	private final ModelPart kelp;

	public BarnacleEntityModel(ModelPart root) {
		super(root);
		this.head = this.root.getChild(MODEL_PART_HEAD);
		this.tentacle = this.root.getChild(MODEL_PART_TENTACLE);
		this.topLeftMouth = this.root.getChild(MODEL_PART_TOP_LEFT_MOUTH);
		this.topRightMouth = this.root.getChild(MODEL_PART_TOP_RIGHT_MOUTH);
		this.bottomLeftMouth = this.root.getChild(MODEL_PART_BOTTOM_LEFT_MOUTH);
		this.bottomRightMouth = this.root.getChild(MODEL_PART_BOTTOM_RIGHT_MOUTH);
		this.kelp = this.root.getChild(MODEL_PART_KELP);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();

		root.addChild(MODEL_PART_HEAD, ModelPartBuilder.create().uv(0, 28).cuboid(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 9.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 22.0F, 1.5F));
		root.addChild(MODEL_PART_TENTACLE, ModelPartBuilder.create().uv(31, 62).cuboid(0.0F, -0.5F, -1.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 18.0F, 1.5F, 0.0F, 3.1416F, 0.0F));
		root.addChild(MODEL_PART_TOP_LEFT_MOUTH, ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(0.0F, -4.0F, -22.0F, 6.0F, 6.0F, 22.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(4.0F, 18.0F, 1.5F, 0.0F, 0.0F, -1.5708F));
		root.addChild(MODEL_PART_TOP_RIGHT_MOUTH, ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -4.0F, -22.0F, 6.0F, 6.0F, 22.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, 18.0F, 1.5F, 0.0F, 0.0F, 1.5708F));
		root.addChild(MODEL_PART_BOTTOM_LEFT_MOUTH, ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-4.0F, 0.0F, -22.0F, 6.0F, 6.0F, 22.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(4.0F, 18.0F, 1.5F));
		root.addChild(MODEL_PART_BOTTOM_RIGHT_MOUTH, ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, 0.0F, -22.0F, 6.0F, 6.0F, 22.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 18.0F, 1.5F));
		root.addChild(MODEL_PART_KELP, ModelPartBuilder.create().uv(0, 45).cuboid(-4.0F, -10.0F, 10.5F, 8.0F, 8.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(
		T barnacle,
		float limbAngle,
		float limbDistance,
		float animationProgress,
		float headYaw,
		float headPitch
	) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.updateMovementKeyframeAnimations(barnacle, limbAngle, limbDistance, 1.5F, 2.5F);
		this.updateKeyframeAnimations(barnacle, animationProgress);

		this.root.pitch = headPitch * 0.017453292F;
		this.root.yaw = headYaw * 0.017453292F;
		if (barnacle.getVelocity().horizontalLengthSquared() > 1.0E-7) {
			this.root.pitch += -0.05F - 0.05F * MathHelper.cos(animationProgress * 0.3F);
		}
	}
}