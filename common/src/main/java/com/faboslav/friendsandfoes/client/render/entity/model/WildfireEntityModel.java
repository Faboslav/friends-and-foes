package com.faboslav.friendsandfoes.client.render.entity.model;

import com.faboslav.friendsandfoes.entity.WildfireEntity;
import net.minecraft.client.model.*;

public final class WildfireEntityModel<T extends WildfireEntity> extends BaseEntityModel<T>
{
	private static final String MODEL_PART_HEAD = "head";
	private static final String MODEL_PART_HELMET = "helmet";
	private static final String MODEL_PART_BODY = "body";
	private static final String MODEL_PART_FRONT_SHIELD = "frontShield";
	private static final String MODEL_PART_RIGHT_SHIELD = "rightShield";
	private static final String MODEL_PART_BACK_SHIELD = "BackShield";
	private static final String MODEL_PART_LEFT_SHIELD = "LeftShield";

	private final ModelPart head;
	private final ModelPart helmet;
	private final ModelPart body;
	private final ModelPart frontShield;
	private final ModelPart rightShield;
	private final ModelPart backShield;
	private final ModelPart leftShield;

	private final ModelPart[] shields;

	public WildfireEntityModel(ModelPart root) {
		super(root);
		this.head = this.root.getChild(MODEL_PART_HEAD);
		this.helmet = this.head.getChild(MODEL_PART_HELMET);
		this.body = this.root.getChild(MODEL_PART_BODY);
		this.frontShield = this.root.getChild(MODEL_PART_FRONT_SHIELD);
		this.rightShield = this.root.getChild(MODEL_PART_RIGHT_SHIELD);
		this.backShield = this.root.getChild(MODEL_PART_BACK_SHIELD);
		this.leftShield = this.root.getChild(MODEL_PART_LEFT_SHIELD);

		this.shields = new ModelPart[]{
			this.frontShield,
			this.rightShield,
			this.backShield,
			this.leftShield,
		};
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();

		root.addChild(MODEL_PART_HEAD, ModelPartBuilder.create().uv(0, 26).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 3.0F, 0.0F));

		ModelPartData head = root.getChild(MODEL_PART_HEAD);
		head.addChild(MODEL_PART_HELMET, ModelPartBuilder.create().uv(0, 43).cuboid(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F, new Dilation(0.2F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		root.addChild(MODEL_PART_BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 21.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 3.0F, 0.0F));

		root.addChild(MODEL_PART_FRONT_SHIELD, ModelPartBuilder.create().uv(17, 0).cuboid(-5.0F, 1.0F, -10.0F, 10.0F, 17.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, 0.0F, -0.2618F, 0.0F, 0.0F));
		root.addChild(MODEL_PART_RIGHT_SHIELD, ModelPartBuilder.create().uv(17, 0).cuboid(-5.0F, 1.0F, -10.0F, 10.0F, 17.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, 0.0F, -0.2618F, -1.5708F, 0.0F));
		root.addChild(MODEL_PART_BACK_SHIELD, ModelPartBuilder.create().uv(17, 0).cuboid(-5.0F, 1.0F, -10.0F, 10.0F, 17.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, 0.0F, -0.2618F, 3.1416F, 0.0F));
		root.addChild(MODEL_PART_LEFT_SHIELD, ModelPartBuilder.create().uv(17, 0).cuboid(-5.0F, 1.0F, -10.0F, 10.0F, 17.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, 0.0F, -0.2618F, 1.5708F, 0.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(
		T wildfire,
		float limbAngle,
		float limbDistance,
		float animationProgress,
		float headYaw,
		float headPitch
	) {
		int activeShieldsCount = wildfire.getActiveShieldsCount();
		float rotationSpeedMultiplier = Math.max(1, WildfireEntity.DEFAULT_ACTIVE_SHIELDS_COUNT - activeShieldsCount);
		float baseRotationUnit = (2.0F * (float) Math.PI) / activeShieldsCount;
		// I don't know why, but this is just choppy and laggy in the game
		//float bodyCounterRotation = (float) Math.toRadians((wildfire.prevBodyYaw * -1.0F));
		float bodyCounterRotation = 0.0F;
		float additionalShieldRotation = (animationProgress * 0.1F * rotationSpeedMultiplier) % (2.0F * (float) Math.PI);

		for (int i = 0; i < WildfireEntity.DEFAULT_ACTIVE_SHIELDS_COUNT; ++i) {
			if (i > activeShieldsCount) {
				this.shields[i].hidden = true;
				continue;
			} else {
				this.shields[i].hidden = false;
			}

			this.shields[i].yaw = (bodyCounterRotation + (baseRotationUnit * i)) + additionalShieldRotation;
		}

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}
}
