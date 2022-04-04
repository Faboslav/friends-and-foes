// Made with Blockbench 4.2.1
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports

	package com.example.mod;

	public class custom_model extends EntityModel<Entity> {
private final ModelPart head;
	private final ModelPart upperJaw;
	private final ModelPart lowerJaw;
	private final ModelPart body;
	private final ModelPart frontLeftleg;
	private final ModelPart frontRightLeg;
	private final ModelPart backLeftLeg;
	private final ModelPart backRightLeg;
public custom_model() {
		textureWidth = 64;
		textureHeight = 64;
		head = new ModelPart(this);
		head.setPivot(0.0F, 14.0F, 0.0F);
		

		upperJaw = new ModelPart(this);
		upperJaw.setPivot(0.0F, 3.0F, 5.0F);
		head.addChild(upperJaw);
		upperJaw.setTextureOffset(0, 0).addCuboid(-4.5F, -3.0F, -10.0F, 9.0F, 3.0F, 10.0F, 0.0F, false);

		lowerJaw = new ModelPart(this);
		lowerJaw.setPivot(0.0F, 3.0F, 5.0F);
		head.addChild(lowerJaw);
		lowerJaw.setTextureOffset(0, 13).addCuboid(-4.5F, 0.0F, -10.0F, 9.0F, 3.0F, 10.0F, 0.0F, false);

		body = new ModelPart(this);
		body.setPivot(0.0F, 24.0F, 0.0F);
		body.setTextureOffset(0, 26).addCuboid(-3.5F, -4.0F, -2.0F, 7.0F, 2.0F, 6.0F, -0.01F, false);

		frontLeftleg = new ModelPart(this);
		frontLeftleg.setPivot(2.5F, 21.0F, -1.0F);
		frontLeftleg.setTextureOffset(0, 5).addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);

		frontRightLeg = new ModelPart(this);
		frontRightLeg.setPivot(-2.5F, 21.0F, -1.0F);
		frontRightLeg.setTextureOffset(0, 0).addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, true);

		backLeftLeg = new ModelPart(this);
		backLeftLeg.setPivot(2.5F, 21.0F, 3.0F);
		backLeftLeg.setTextureOffset(0, 18).addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);

		backRightLeg = new ModelPart(this);
		backRightLeg.setPivot(-2.5F, 21.0F, 3.0F);
		backRightLeg.setTextureOffset(0, 13).addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
}
@Override
public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
}
@Override
public void render(MatrixStack matrixStack, VertexConsumer	buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		frontLeftleg.render(matrixStack, buffer, packedLight, packedOverlay);
		frontRightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		backLeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		backRightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
}
public void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
}

	}