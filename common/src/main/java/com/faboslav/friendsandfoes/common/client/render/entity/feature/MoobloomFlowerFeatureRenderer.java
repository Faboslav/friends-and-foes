package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

//? if >= 1.21.11 {
import net.minecraft.client.renderer.rendertype.RenderTypes;
//?} else {
/*import net.minecraft.client.renderer.RenderType;
*///?}

//? if >=1.21.9 {
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
//?} else {
/*import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
*///?}

//? if >=1.21.5 {
import net.minecraft.client.renderer.block.model.BlockStateModel;
//?} else {
/*import net.minecraft.client.resources.model.BakedModel;
 *///?}

//? if >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.MoobloomRenderState;
//?} else {
/*import com.faboslav.friendsandfoes.common.entity.MoobloomEntity;
 *///?}

//? if >=1.21.3 {
public final class MoobloomFlowerFeatureRenderer extends RenderLayer<MoobloomRenderState, CowModel>
//?} else {
	/*public final class MoobloomFlowerFeatureRenderer<T extends MoobloomEntity> extends RenderLayer<T, CowModel<T>>
	 *///?}
{
	private final BlockRenderDispatcher blockRenderer;

	//? if >=1.21.3 {
	public MoobloomFlowerFeatureRenderer(RenderLayerParent<MoobloomRenderState, CowModel> renderer, BlockRenderDispatcher blockRenderer) {
		super(renderer);
		this.blockRenderer = blockRenderer;
	}
	//?} else {
	/*public MoobloomFlowerFeatureRenderer(RenderLayerParent<T, CowModel<T>> featureRendererContext) {
		super(featureRendererContext);
		this.blockRenderer = Minecraft.getInstance().getBlockRenderer();
	}*///?}

	//? if >=1.21.9 {
	public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, MoobloomRenderState moobloomRenderState, float yRot, float xRot)
	//?} else if >=1.21.3 {
	/*public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, MoobloomRenderState moobloomRenderState, float yRot, float xRot)
	 *///?} else {
	/*public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T moobloom, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float yRot, float xRot)
	 *///?}
	{
		//? if >=1.21.3 {
		var moobloom = moobloomRenderState.moobloom;
		int overlay = LivingEntityRenderer.getOverlayCoords(moobloomRenderState, 0.0F);
		//?} else {
		/*int overlay = LivingEntityRenderer.getOverlayCoords(moobloom, 0.0F);
		 *///?}

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

			//? if >=1.21.5 {
			BlockStateModel model = this.blockRenderer.getBlockModel(blockState);
			//?} else {
			/*BakedModel model = this.blockRenderer.getBlockModel(blockState);
			 *///?}

			// Head
			poseStack.pushPose();
			this.getParentModel().getHead().translateAndRotate(poseStack);
			poseStack.translate(0.09D, -0.6D, -0.185D);
			poseStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
			poseStack.translate(-0.5D, yOffset, -0.5D);
			//? if >=1.21.9 {
			this.renderFlower(poseStack, submitNodeCollector, packedLight, renderAsModel, blockState, overlay, model);
			//?} else {
			/*this.renderFlower(poseStack, bufferSource, packedLight, renderAsModel, blockState, overlay, model);
			 *///?}
			poseStack.popPose();

			// Body 1
			poseStack.pushPose();
			poseStack.translate(0.22D, -0.28D, -0.06D);
			poseStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
			poseStack.translate(-0.5D, yOffset, -0.5D);
			//? if >=1.21.9 {
			this.renderFlower(poseStack, submitNodeCollector, packedLight, renderAsModel, blockState, overlay, model);
			//?} else {
			/*this.renderFlower(poseStack, bufferSource, packedLight, renderAsModel, blockState, overlay, model);
			 *///?}
			poseStack.popPose();

			// Body 2
			poseStack.pushPose();
			poseStack.translate(-0.2D, -0.22D, 0.01D);
			poseStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
			poseStack.translate(-0.5D, yOffset, -0.5D);
			//? if >=1.21.9 {
			this.renderFlower(poseStack, submitNodeCollector, packedLight, renderAsModel, blockState, overlay, model);
			//?} else {
			/*this.renderFlower(poseStack, bufferSource, packedLight, renderAsModel, blockState, overlay, model);
			 *///?}
			poseStack.popPose();

			// Body 3
			poseStack.pushPose();
			poseStack.translate(0.03D, -0.28D, 0.47D);
			poseStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
			poseStack.translate(-0.5D, yOffset, -0.5D);
			//? if >=1.21.9 {
			this.renderFlower(poseStack, submitNodeCollector, packedLight, renderAsModel, blockState, overlay, model);
			//?} else {
			/*this.renderFlower(poseStack, bufferSource, packedLight, renderAsModel, blockState, overlay, model);
			 *///?}
			poseStack.popPose();
		}
	}

	// unified name, versioned parameters + body
	private void renderFlower(
		PoseStack poseStack,
		//? if >=1.21.9 {
		SubmitNodeCollector submitNodeCollector,
		//?} else {
		/*MultiBufferSource multiBufferSource,
		 *///?}
		int light,
		boolean renderAsModel,
		BlockState blockState,
		int overlay,
		//? if >=1.21.5 {
		BlockStateModel model
		//?} else {
		/*BakedModel model
		 *///?}
	) {
		//? if >=1.21.9 {
		if (renderAsModel) {
			//? if >= 1.21.11 {
			submitNodeCollector.submitBlockModel(poseStack, RenderTypes.outline(TextureAtlas.LOCATION_BLOCKS), model, 0.0F, 0.0F, 0.0F, light, overlay, 0);
			//?} else {
			/*submitNodeCollector.submitBlockModel(poseStack, RenderType.outline(TextureAtlas.LOCATION_BLOCKS), model, 0.0F, 0.0F, 0.0F, light, overlay, 0);
			*///?}
		} else {
			submitNodeCollector.submitBlock(poseStack, blockState, light, overlay, 0);
		}
		//?} else if >=1.21.5 {
		/*if (renderAsModel) {
			ModelBlockRenderer.renderModel(poseStack.last(), multiBufferSource.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)), model, 0.0F, 0.0F, 0.0F, light, overlay);
		} else {
			this.blockRenderer.renderSingleBlock(blockState, poseStack, multiBufferSource, light, overlay);
		}
		*///?} else {
		/*if (renderAsModel) {
			this.blockRenderer.getModelRenderer().renderModel(poseStack.last(), multiBufferSource.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)), blockState, model, 0.0F, 0.0F, 0.0F, light, overlay);
		} else {
			this.blockRenderer.renderSingleBlock(blockState, poseStack, multiBufferSource, light, overlay);
		}
		*///?}
	}
}