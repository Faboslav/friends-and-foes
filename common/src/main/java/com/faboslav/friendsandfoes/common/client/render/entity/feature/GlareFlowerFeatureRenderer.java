package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.GlareEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;

//? if <= 1.21.8 {
/*import net.minecraft.client.renderer.MultiBufferSource;
*///?}

//? if >=1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
//?} else {
/*import net.minecraft.client.renderer.MultiBufferSource;
*///?}

//? if >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.GlareRenderState;
//?} else {
/*import com.faboslav.friendsandfoes.common.entity.GlareEntity;
*///?}

//? if >=1.21.3 {
public final class GlareFlowerFeatureRenderer extends RenderLayer<GlareRenderState, GlareEntityModel>
//?} else {
/*public final class GlareFlowerFeatureRenderer extends RenderLayer<GlareEntity, GlareEntityModel<GlareEntity>>
*///?}
{
	private static final Identifier FLOWERING_TEXTURE = FriendsAndFoes.makeID("textures/entity/glare/flowering_glare.png");

	//? if >=1.21.3 {
	public GlareFlowerFeatureRenderer(RenderLayerParent<GlareRenderState, GlareEntityModel> renderLayerParent) {
		super(renderLayerParent);
	}
	//?} else {
	/*public GlareFlowerFeatureRenderer(RenderLayerParent<GlareEntity, GlareEntityModel<GlareEntity>> featureRendererContext) {
		super(featureRendererContext);
	}*///?}

	//? if >=1.21.9 {
	public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, GlareRenderState renderState, float yRot, float xRot)
	//?} else if >=1.21.3 {
	/*public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, GlareRenderState renderState, float yRot, float xRot)
	*///?} else {
	/*public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, GlareEntity glare, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float yRot, float xRot)
	*///?}
	{
		//? if >=1.21.3 {
		var glare = renderState.glare;
		//?}

		if (glare.isInvisible()) {
			return;
		}

		String string = ChatFormatting.stripFormatting(glare.getName().getString());

		if (
			"Anna".equals(string)
			|| glare.isTame()
		) {
			renderColoredCutoutModel(
				this.getParentModel(),
				FLOWERING_TEXTURE,
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
				/*glare,
				*///?}
				-1
				//? if >=1.21.9 {
				, 0
				//?}
			);
		}
	}
}

