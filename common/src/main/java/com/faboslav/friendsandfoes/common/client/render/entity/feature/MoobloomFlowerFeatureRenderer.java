package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

//? >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.MoobloomRenderState;
//?} else {
/*import com.faboslav.friendsandfoes.common.entity.MoobloomEntity;
*///?}

//? >=1.21.3 {
public final class MoobloomFlowerFeatureRenderer extends RenderLayer<MoobloomRenderState, CowModel>
//?} else {
/*public final class MoobloomFlowerFeatureRenderer<T extends MoobloomEntity> extends RenderLayer<T, CowModel<T>>
*///?}
{
	private final BlockRenderDispatcher blockRenderer;

	//? >=1.21.3 {
	public MoobloomFlowerFeatureRenderer(RenderLayerParent<MoobloomRenderState, CowModel> renderer, BlockRenderDispatcher blockRenderer) {
		super(renderer);
		this.blockRenderer = blockRenderer;
	}
	//?} else {
	/*public MoobloomFlowerFeatureRenderer(
		RenderLayerParent<T, CowModel<T>> featureRendererContext
	) {
		super(featureRendererContext);
		this.blockRenderer = Minecraft.getInstance().getBlockRenderer();
	}*///?}

	//? >=1.21.3 {
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, MoobloomRenderState moobloomRenderState, float yRot, float xRot)
	//?} else {
	/*public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T moobloom, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch)
	*///?}
	{
		//? >=1.21.3 {
		var moobloom = moobloomRenderState.moobloom;
		int overlay = LivingEntityRenderer.getOverlayCoords(moobloomRenderState, 0.0F);
		//?} else {
		/*int overlay = LivingEntityRenderer.getOverlayCoords(moobloom, 0.0F);
		*///?}

		if (!moobloom.isBaby() && !moobloom.isInvisible()) {
			BushBlock flower = moobloom.getVariant().getFlower();
			BlockState blockState = moobloom.getVariant().getFlower().defaultBlockState();

			if (flower instanceof DoublePlantBlock) {
				blockState = moobloom.getVariant().getFlower().defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);
			}


			float scaleFactor = 0.8F;
			float yOffset = -0.5F;

			if (flower instanceof DoublePlantBlock) {
				scaleFactor = 0.6F;
				yOffset = -0.666F;
			}

			Minecraft minecraftClient = Minecraft.getInstance();
			boolean renderAsModel = minecraftClient.shouldEntityAppearGlowing(moobloom) && moobloom.isInvisible();
			BakedModel bakedModel = this.blockRenderer.getBlockModel(blockState);

			// Head
			poseStack.pushPose();
			this.getParentModel().getHead().translateAndRotate(poseStack);
			poseStack.translate(0.09D, -0.6D, -0.185D);
			poseStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
			poseStack.translate(-0.5D, yOffset, -0.5D);
			this.renderFlower(poseStack, bufferSource, packedLight, renderAsModel, blockState, overlay, bakedModel);
			poseStack.popPose();

			// Body
			poseStack.pushPose();
			poseStack.translate(0.22D, -0.28D, -0.06D);
			poseStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
			poseStack.translate(-0.5D, yOffset, -0.5D);
			this.renderFlower(poseStack, bufferSource, packedLight, renderAsModel, blockState, overlay, bakedModel);
			poseStack.popPose();

			poseStack.pushPose();
			poseStack.translate(-0.2D, -0.22D, 0.01D);
			poseStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
			poseStack.translate(-0.5D, yOffset, -0.5D);
			this.renderFlower(poseStack, bufferSource, packedLight, renderAsModel, blockState, overlay, bakedModel);
			poseStack.popPose();

			poseStack.pushPose();
			poseStack.translate(0.03D, -0.28D, 0.47D);
			poseStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
			poseStack.translate(-0.5D, yOffset, -0.5D);
			this.renderFlower(poseStack, bufferSource, packedLight, renderAsModel, blockState, overlay, bakedModel);
			poseStack.popPose();
		}
	}

	private void renderFlower(
		PoseStack matrices,
		MultiBufferSource vertexConsumers,
		int light,
		boolean renderAsModel,
		BlockState moobloomState,
		int overlay,
		BakedModel moobloomModel
	) {
		if (renderAsModel) {
			this.blockRenderer.getModelRenderer().renderModel(matrices.last(), vertexConsumers.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)), moobloomState, moobloomModel, 0.0F, 0.0F, 0.0F, light, overlay);
		} else {
			this.blockRenderer.renderSingleBlock(moobloomState, matrices, vertexConsumers, light, overlay);
		}

	}
}
