// Made with Blockbench 4.9.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class Glare extends EntityModel<Entity> {
	private final ModelPart head;
	private final ModelPart eyes;
	private final ModelPart topAzalea;
	private final ModelPart bottomAzalea;
	private final ModelPart secondLayer;
	private final ModelPart thirdLayer;
	private final ModelPart fourthLayer;
	public Glare(ModelPart root) {
		this.head = root.getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, 0.0F, -3.0F, 12.0F, 9.0F, 9.0F, new Dilation(-0.02F)), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

		ModelPartData eyes = head.addChild("eyes", ModelPartBuilder.create().uv(33, 0).cuboid(2.0F, -1.0F, -0.3F, 2.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(33, 0).cuboid(-4.0F, -1.0F, -0.3F, 2.0F, 2.0F, 1.0F, new Dilation(-0.2F)), ModelTransform.pivot(0.0F, 5.0F, -3.0F));

		ModelPartData topAzalea = head.addChild("topAzalea", ModelPartBuilder.create().uv(0, 18).cuboid(-7.0F, 0.0F, -7.0F, 14.0F, 8.0F, 14.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData bottomAzalea = head.addChild("bottomAzalea", ModelPartBuilder.create().uv(18, 101).mirrored().cuboid(-7.0F, 0.75F, -7.0F, 14.0F, 0.0F, 14.0F, new Dilation(-0.01F)).mirrored(false)
		.uv(0, 40).cuboid(-7.0F, -4.0F, -7.0F, 14.0F, 10.0F, 14.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 8.0F, 0.0F));

		ModelPartData secondLayer = bottomAzalea.addChild("secondLayer", ModelPartBuilder.create().uv(0, 64).cuboid(-6.0F, 0.0F, -6.0F, 12.0F, 7.0F, 12.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

		ModelPartData thirdLayer = secondLayer.addChild("thirdLayer", ModelPartBuilder.create().uv(0, 83).cuboid(-5.0F, 0.0F, -5.0F, 10.0F, 7.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 0.0F));

		ModelPartData fourthLayer = thirdLayer.addChild("fourthLayer", ModelPartBuilder.create().uv(0, 100).cuboid(-4.0F, 0.0F, -4.0F, 8.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 128);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}