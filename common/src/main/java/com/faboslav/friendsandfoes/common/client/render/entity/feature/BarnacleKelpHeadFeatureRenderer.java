package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.BarnacleEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import java.util.List;
import java.util.stream.IntStream;

//? if >=1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
//?} else {
/*import net.minecraft.client.renderer.MultiBufferSource;
*///?}

//? if >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.BarnacleRenderState;
//?} else {
/*import com.faboslav.friendsandfoes.common.entity.BarnacleEntity;
*///?}

//? if >=1.21.3 {
public final class BarnacleKelpHeadFeatureRenderer extends RenderLayer<BarnacleRenderState, BarnacleEntityModel>
//?} else {
/*public final class BarnacleKelpHeadFeatureRenderer extends RenderLayer<BarnacleEntity, BarnacleEntityModel<BarnacleEntity>>
*///?}
{
	private static final int KELP_HEAD_TEXTURE_FRAMES = 20;
	private static final List<Identifier> KELP_HEAD_TEXTURE_MAP = IntStream.rangeClosed(1, KELP_HEAD_TEXTURE_FRAMES)
		.mapToObj(frame -> FriendsAndFoes.makeID("textures/entity/barnacle/barnacle_kelp_head_" + frame + ".png"))
		.toList();

	//? if >=1.21.3 {
	public BarnacleKelpHeadFeatureRenderer(RenderLayerParent<BarnacleRenderState, BarnacleEntityModel> renderLayerParent) {
		super(renderLayerParent);
	}
	//?} else {
	/*public BarnacleKelpHeadFeatureRenderer(RenderLayerParent<BarnacleEntity, BarnacleEntityModel<BarnacleEntity>> featureRendererContext) {
		super(featureRendererContext);
	}*///?}

	//? if >=1.21.9 {
	public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, BarnacleRenderState renderState, float yRot, float xRot)
	//?} else if >=1.21.3 {
	/*public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, BarnacleRenderState renderState, float yRot, float xRot)
	 *///?} else {
	/*public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, BarnacleEntity barnacle, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float yRot, float xRot)
	 *///?}
	{
		//? if >=1.21.3 {
		if (renderState.isInvisible) {
			return;
		}

		int textureFrame = renderState.isUnderWater ? (int) renderState.ageInTicks % KELP_HEAD_TEXTURE_FRAMES : 0;
		//?} else {
		/*if (barnacle.isInvisible()) {
			return;
		}

		int textureFrame = barnacle.isUnderWater() ? barnacle.tickCount % KELP_HEAD_TEXTURE_FRAMES : 0;
		*///?}
		Identifier kelpTexture = KELP_HEAD_TEXTURE_MAP.get(textureFrame);

		renderColoredCutoutModel(
			this.getParentModel(),
			kelpTexture,
			poseStack,
			//? if >=1.21.9 {
			submitNodeCollector,
			//?} else {
			/*bufferSource,
			 *///?}
			packedLight,
			//? if >=1.21.3 {
			renderState,
			//?} else {
			/*barnacle,
			 *///?}
			//? if >=1.21.9 {
			-1, 1
			//?} else if >= 1.21.1 {
			/*-1
			*///?} else {
			/*1.0F, 1.0F, 1.0F
			*///?}
		);
	}
}
