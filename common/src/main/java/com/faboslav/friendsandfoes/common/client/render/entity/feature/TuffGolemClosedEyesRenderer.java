package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.TuffGolemEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

//? >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.TuffGolemRenderState;
//?} else {
/*import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
*///?}

@Environment(EnvType.CLIENT)
//? >=1.21.3 {
public final class TuffGolemClosedEyesRenderer extends RenderLayer<TuffGolemRenderState, TuffGolemEntityModel>
//?} else {
/*public final class TuffGolemClosedEyesRenderer extends RenderLayer<TuffGolemEntity, TuffGolemEntityModel<TuffGolemEntity>>
*///?}
{
	private static final ResourceLocation CLOSED_EYES_TEXTURE = FriendsAndFoes.makeID("textures/entity/tuff_golem/closed_eyes.png");

	//? >=1.21.3 {
	public TuffGolemClosedEyesRenderer(RenderLayerParent<TuffGolemRenderState, TuffGolemEntityModel> renderLayerParent) {
		super(renderLayerParent);
	}
	//?} else {
	/*public TuffGolemClosedEyesRenderer(RenderLayerParent<TuffGolemEntity, TuffGolemEntityModel<TuffGolemEntity>> featureRendererContext) {
		super(featureRendererContext);
	}*///?}

	//? >=1.21.3 {
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, TuffGolemRenderState renderState, float yRot, float xRot)
	//?} else {
	/*public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, TuffGolemEntity tuffGolem, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch)
	 *///?}
	{
		//? >=1.21.3 {
		var tuffGolem = renderState.tuffGolem;
		//?}

		if (tuffGolem.isInvisible() || !tuffGolem.isInSleepingPose()) {
			return;
		}

		renderColoredCutoutModel(
			this.getParentModel(),
			CLOSED_EYES_TEXTURE,
			poseStack,
			bufferSource,
			packedLight,
			//? >=1.21.3 {
			renderState,
			//?} else {
			/*tuffGolem,
			 *///?}
			-1
		);
	}
}

