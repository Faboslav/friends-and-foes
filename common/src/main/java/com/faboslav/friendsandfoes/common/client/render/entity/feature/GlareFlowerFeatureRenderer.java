package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.GlareEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.client.renderer.MultiBufferSource;

import com.faboslav.friendsandfoes.common.entity.GlareEntity;

public final class GlareFlowerFeatureRenderer extends RenderLayer<GlareEntity, GlareEntityModel<GlareEntity>>

{
	private static final ResourceLocation FLOWERING_TEXTURE = FriendsAndFoes.makeID("textures/entity/glare/flowering_glare.png");

	public GlareFlowerFeatureRenderer(RenderLayerParent<GlareEntity, GlareEntityModel<GlareEntity>> featureRendererContext) {
		super(featureRendererContext);
	}

	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, GlareEntity glare, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float yRot, float xRot)

	{

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

				bufferSource,

				packedLight,

				glare,

				-1

			);
		}
	}
}

