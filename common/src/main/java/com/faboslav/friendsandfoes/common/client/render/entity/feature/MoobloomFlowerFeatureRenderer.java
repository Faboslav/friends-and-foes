package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.entity.MoobloomEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

@Environment(EnvType.CLIENT)
public final class MoobloomFlowerFeatureRenderer<T extends MoobloomEntity> extends RenderLayer<T, CowModel<T>>
{
	private final BlockRenderDispatcher blockRenderManager;

	public MoobloomFlowerFeatureRenderer(
		RenderLayerParent<T, CowModel<T>> featureRendererContext
	) {
		super(featureRendererContext);
		this.blockRenderManager = Minecraft.getInstance().getBlockRenderer();
	}

	public void render(
		PoseStack matrixStack,
		MultiBufferSource vertexConsumerProvider,
		int light,
		T moobloomEntity,
		float f,
		float g,
		float h,
		float j,
		float k,
		float l
	) {
		if (!moobloomEntity.isBaby() && !moobloomEntity.isInvisible()) {
			BushBlock flower = moobloomEntity.getVariant().getFlower();
			BlockState blockState = moobloomEntity.getVariant().getFlower().defaultBlockState();

			if (flower instanceof DoublePlantBlock) {
				blockState = moobloomEntity.getVariant().getFlower().defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);
			}

			int overlay = LivingEntityRenderer.getOverlayCoords(moobloomEntity, 0.0F);
			float scaleFactor = 0.8F;
			float yOffset = -0.5F;

			if (flower instanceof DoublePlantBlock) {
				scaleFactor = 0.6F;
				yOffset = -0.666F;
			}

			Minecraft minecraftClient = Minecraft.getInstance();
			boolean renderAsModel = minecraftClient.shouldEntityAppearGlowing(moobloomEntity) && moobloomEntity.isInvisible();
			BakedModel bakedModel = this.blockRenderManager.getBlockModel(blockState);

			// Head
			matrixStack.pushPose();
			this.getParentModel().getHead().translateAndRotate(matrixStack);
			matrixStack.translate(0.09D, -0.6D, -0.185D);
			matrixStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
			matrixStack.translate(-0.5D, yOffset, -0.5D);
			this.renderFlower(matrixStack, vertexConsumerProvider, light, renderAsModel, blockState, overlay, bakedModel);
			matrixStack.popPose();

			// Body
			matrixStack.pushPose();
			matrixStack.translate(0.22D, -0.28D, -0.06D);
			matrixStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
			matrixStack.translate(-0.5D, yOffset, -0.5D);
			this.renderFlower(matrixStack, vertexConsumerProvider, light, renderAsModel, blockState, overlay, bakedModel);
			matrixStack.popPose();

			matrixStack.pushPose();
			matrixStack.translate(-0.2D, -0.22D, 0.01D);
			matrixStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
			matrixStack.translate(-0.5D, yOffset, -0.5D);
			this.renderFlower(matrixStack, vertexConsumerProvider, light, renderAsModel, blockState, overlay, bakedModel);
			matrixStack.popPose();

			matrixStack.pushPose();
			matrixStack.translate(0.03D, -0.28D, 0.47D);
			matrixStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
			matrixStack.translate(-0.5D, yOffset, -0.5D);
			this.renderFlower(matrixStack, vertexConsumerProvider, light, renderAsModel, blockState, overlay, bakedModel);
			matrixStack.popPose();
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
			this.blockRenderManager.getModelRenderer().renderModel(matrices.last(), vertexConsumers.getBuffer(RenderType.outline(InventoryMenu.BLOCK_ATLAS)), moobloomState, moobloomModel, 0.0F, 0.0F, 0.0F, light, overlay);
		} else {
			this.blockRenderManager.renderSingleBlock(moobloomState, matrices, vertexConsumers, light, overlay);
		}

	}
}
