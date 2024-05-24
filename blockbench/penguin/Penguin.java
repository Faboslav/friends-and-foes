// Made with Blockbench 4.10.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.example.mod;
   
public class Penguin extends EntityModel<Entity> {
	private final ModelPart main;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart bill;
	private final ModelPart earPatches;
	private final ModelPart leftWing;
	private final ModelPart rightWing;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	public Penguin(ModelPart root) {
		this.main = root.getChild("main");
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.bill = root.getChild("bill");
		this.earPatches = root.getChild("earPatches");
		this.leftWing = root.getChild("leftWing");
		this.rightWing = root.getChild("rightWing");
		this.leftLeg = root.getChild("leftLeg");
		this.rightLeg = root.getChild("rightLeg");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 10.0F, 0.0F));

		ModelPartData body = main.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F, 0.0F, -4.5F, 9.0F, 13.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 22).cuboid(-4.0F, -5.0F, -4.0F, 8.0F, 5.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData bill = head.addChild("bill", ModelPartBuilder.create().uv(8, 35).cuboid(-1.0F, -17.0F, -7.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 14.0F, 0.0F));

		ModelPartData earPatches = head.addChild("earPatches", ModelPartBuilder.create().uv(24, 27).cuboid(-4.0F, -2.5F, 0.0F, 8.0F, 3.0F, 8.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, -4.0F, -4.0F));

		ModelPartData leftWing = body.addChild("leftWing", ModelPartBuilder.create().uv(0, 35).cuboid(0.0F, 0.0F, -3.0F, 1.0F, 11.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(4.5F, 1.0F, 0.0F));

		ModelPartData rightWing = body.addChild("rightWing", ModelPartBuilder.create().uv(0, 35).cuboid(-1.0F, 0.0F, -3.0F, 1.0F, 11.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.5F, 1.0F, 0.0F));

		ModelPartData leftLeg = main.addChild("leftLeg", ModelPartBuilder.create().uv(27, 0).cuboid(-1.5F, 0.0F, -5.0F, 3.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, 13.0F, 0.0F));

		ModelPartData rightLeg = main.addChild("rightLeg", ModelPartBuilder.create().uv(27, 0).cuboid(-1.5F, 0.0F, -5.0F, 3.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, 13.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}