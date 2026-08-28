package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.BarnacleEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;

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
public final class BarnacleKelpFeatureRenderer extends RenderLayer<BarnacleRenderState, BarnacleEntityModel>
//?} else {
/*public final class BarnacleKelpFeatureRenderer extends RenderLayer<BarnacleEntity, BarnacleEntityModel<BarnacleEntity>>
*///?}
{
	private static final Identifier KELP_TEXTURE = FriendsAndFoes.makeID("textures/entity/barnacle/barnacle_kelp.png");

	//? if >=1.21.3 {
	public BarnacleKelpFeatureRenderer(RenderLayerParent<BarnacleRenderState, BarnacleEntityModel> renderLayerParent) {
		super(renderLayerParent);
	}
	//?} else {
	/*public BarnacleKelpFeatureRenderer(RenderLayerParent<BarnacleEntity, BarnacleEntityModel<BarnacleEntity>> featureRendererContext) {
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
		var barnacle = renderState.barnacle;
		//?}

		if (barnacle.isInvisible()) {
			return;
		}

		renderColoredCutoutModel(
			this.getParentModel(),
			KELP_TEXTURE,
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
			-1, 0
			//?} else if >= 1.21.1 {
			/*-1
			*///?} else {
			/*1.0F, 1.0F, 1.0F
			*///?}
		);
	}
}
