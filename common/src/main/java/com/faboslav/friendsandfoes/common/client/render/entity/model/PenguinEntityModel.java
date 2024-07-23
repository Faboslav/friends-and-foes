package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;

@Environment(EnvType.CLIENT)
public final class PenguinEntityModel<T extends PenguinEntity> extends AnimatedEntityModel<T>
{
	private static final String MAIN = "main";
	private static final String BODY = "body";
	private static final String HEAD = "head";
	private static final String BILL = "bill";
	private static final String EAR_PATCHES = "earPatches";
	private static final String LEFT_WING = "leftWing";
	private static final String RIGHT_WING = "rightWing";
	private static final String LEFT_LEG = "leftLeg";
	private static final String RIGHT_LEG = "rightLeg";

	private final ModelPart main;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart bill;
	private final ModelPart earPatches;
	private final ModelPart leftWing;
	private final ModelPart rightWing;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public PenguinEntityModel(ModelPart root) {
		super(root);
		this.main = this.root.getChild(MAIN);
		this.body = this.main.getChild(BODY);
		this.head = this.body.getChild(HEAD);
		this.bill = this.head.getChild(BILL);
		this.earPatches = this.head.getChild(EAR_PATCHES);
		this.leftWing = this.body.getChild(LEFT_WING);
		this.rightWing = this.body.getChild(RIGHT_WING);
		this.leftLeg = this.main.getChild(LEFT_LEG);
		this.rightLeg = this.main.getChild(RIGHT_LEG);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();

		ModelPartData main = root.addChild(MAIN, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 10.0F, 0.0F));

		ModelPartData body = main.addChild(BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -12.0F, -5.0F, 10.0F, 12.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 13.0F, 0.0F));

		ModelPartData head = body.addChild(HEAD, ModelPartBuilder.create().uv(29, 13).cuboid(-4.0F, -5.0F, -4.0F, 8.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -12.0F, 0.0F));

		ModelPartData bill = head.addChild(BILL, ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -17.0F, -6.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 14.0F, 0.0F));

		ModelPartData earPatches = head.addChild(EAR_PATCHES, ModelPartBuilder.create().uv(0, 20).cuboid(-5.0F, -2.0F, 0.0F, 10.0F, 3.0F, 8.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, -4.0F, -4.0F));

		ModelPartData leftWing = body.addChild(LEFT_WING, ModelPartBuilder.create().uv(30, 25).cuboid(0.0F, -1.0F, -3.0F, 1.0F, 9.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, -10.0F, -1.0F));

		ModelPartData rightWing = body.addChild(RIGHT_WING, ModelPartBuilder.create().uv(30, 25).cuboid(-1.0F, -1.0F, -3.0F, 1.0F, 9.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, -10.0F, -1.0F));

		ModelPartData leftLeg = main.addChild(LEFT_LEG, ModelPartBuilder.create().uv(28, 0).cuboid(-1.5F, 0.0F, -5.0F, 3.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, 13.0F, 0.0F));

		ModelPartData rightLeg = main.addChild(RIGHT_LEG, ModelPartBuilder.create().uv(28, 0).cuboid(-1.5F, 0.0F, -5.0F, 3.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, 13.0F, 0.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(
		T penguin,
		float limbAngle,
		float limbDistance,
		float animationProgress,
		float headYaw,
		float headPitch
	) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.updateMovementKeyframeAnimations(penguin, limbAngle, limbDistance, 2.5F, 4.5F);
		this.updateKeyframeAnimations(penguin, animationProgress);
	}
}