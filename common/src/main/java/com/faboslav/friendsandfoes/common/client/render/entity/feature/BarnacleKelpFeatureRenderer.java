package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.BarnacleEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
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
	private static final ResourceLocation KELP_TEXTURE = FriendsAndFoes.makeID("textures/entity/barnacle/barnacle_kelp.png");

	//? >=1.21.3 {
	public BarnacleKelpFeatureRenderer(RenderLayerParent<BarnacleRenderState, BarnacleEntityModel> renderLayerParent) {
		super(renderLayerParent);
	}
	//?} else {
	/*public BarnacleKelpFeatureRenderer(RenderLayerParent<BarnacleEntity, BarnacleEntityModel<BarnacleEntity>> featureRendererContext) {
		super(featureRendererContext);
	}*///?}

	//? >=1.21.3 {
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, BarnacleRenderState renderState, float yRot, float xRot)
	//?} else {
	/*public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, BarnacleEntity barnacle, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch)
	 *///?}
	{
		//? >=1.21.3 {
		var barnacle = renderState.barnacle;
		//?}

		if (barnacle.isInvisible()) {
			return;
		}

		renderColoredCutoutModel(
			this.getParentModel(),
			KELP_TEXTURE,
			poseStack,
			bufferSource,
			packedLight,
			//? >=1.21.3 {
			renderState,
			//?} else {
			/*barnacle,
			 *///?}
			-1
		);
	}
}
