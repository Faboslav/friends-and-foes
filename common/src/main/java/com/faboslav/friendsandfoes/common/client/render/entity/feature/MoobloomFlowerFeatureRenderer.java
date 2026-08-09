package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlas;

import net.minecraft.client.renderer.RenderType;

import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.MultiBufferSource;

import net.minecraft.client.resources.model.BakedModel;

import com.faboslav.friendsandfoes.common.entity.MoobloomEntity;

	public final class MoobloomFlowerFeatureRenderer<T extends MoobloomEntity> extends RenderLayer<T, CowModel<T>>

{

	private final BlockRenderDispatcher blockRenderer;

	public MoobloomFlowerFeatureRenderer(RenderLayerParent<T, CowModel<T>> featureRendererContext) {
		super(featureRendererContext);
		this.blockRenderer = Minecraft.getInstance().getBlockRenderer();
	}

	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T moobloom, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float yRot, float xRot)

	{

		int overlay = LivingEntityRenderer.getOverlayCoords(moobloom, 0.0F);

		if (!moobloom.isBaby() && !moobloom.isInvisible()) {
			var flower = moobloom.getVariant().getFlower();
			BlockState blockState = flower.defaultBlockState();

			if (flower instanceof DoublePlantBlock) {
				blockState = blockState.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);
			}

			float scaleFactor = (flower instanceof DoublePlantBlock) ? 0.6F : 0.8F;
			float yOffset    = (flower instanceof DoublePlantBlock) ? -0.666F : -0.5F;

			Minecraft minecraft = Minecraft.getInstance();
			boolean renderAsModel = minecraft.shouldEntityAppearGlowing(moobloom) && moobloom.isInvisible();

			BakedModel model = this.blockRenderer.getBlockModel(blockState);

			// Head
			poseStack.pushPose();
			this.getParentModel().getHead().translateAndRotate(poseStack);
			poseStack.translate(0.09D, -0.6D, -0.185D);
			poseStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
			poseStack.translate(-0.5D, yOffset, -0.5D);

			this.renderFlower(poseStack, bufferSource, packedLight, renderAsModel, blockState, overlay, model);

			poseStack.popPose();

			// Body 1
			poseStack.pushPose();
			poseStack.translate(0.22D, -0.28D, -0.06D);
			poseStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
			poseStack.translate(-0.5D, yOffset, -0.5D);

			this.renderFlower(poseStack, bufferSource, packedLight, renderAsModel, blockState, overlay, model);

			poseStack.popPose();

			// Body 2
			poseStack.pushPose();
			poseStack.translate(-0.2D, -0.22D, 0.01D);
			poseStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
			poseStack.translate(-0.5D, yOffset, -0.5D);

			this.renderFlower(poseStack, bufferSource, packedLight, renderAsModel, blockState, overlay, model);

			poseStack.popPose();

			// Body 3
			poseStack.pushPose();
			poseStack.translate(0.03D, -0.28D, 0.47D);
			poseStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
			poseStack.translate(-0.5D, yOffset, -0.5D);

			this.renderFlower(poseStack, bufferSource, packedLight, renderAsModel, blockState, overlay, model);

			poseStack.popPose();
		}
	}

	private void renderFlower(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, boolean renderAsModel, BlockState blockState, int overlay, BakedModel model) {
		if (renderAsModel) this.blockRenderer.getModelRenderer().renderModel(poseStack.last(), multiBufferSource.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)), blockState, model, 0.0F, 0.0F, 0.0F, light, overlay); else this.blockRenderer.renderSingleBlock(blockState, poseStack, multiBufferSource, light, overlay);
	}

}