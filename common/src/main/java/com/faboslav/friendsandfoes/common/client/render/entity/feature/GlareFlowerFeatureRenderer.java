package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.GlareEntityModel;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public final class GlareFlowerFeatureRenderer extends RenderLayer<GlareEntity, GlareEntityModel<GlareEntity>>
{
	public GlareFlowerFeatureRenderer(RenderLayerParent<GlareEntity, GlareEntityModel<GlareEntity>> featureRendererContext) {
		super(featureRendererContext);
	}

	public void render(
		PoseStack matrixStack,
		MultiBufferSource vertexConsumerProvider,
		int light,
		GlareEntity glare,
		float f,
		float g,
		float h,
		float j,
		float k,
		float l
	) {
		if (glare.isInvisible()) {
			return;
		}

		String string = ChatFormatting.stripFormatting(glare.getName().getString());

		if (
			"Anna".equals(string)
			|| glare.isTame()
		) {
			ResourceLocation identifier = FriendsAndFoes.makeID("textures/entity/glare/flowering_glare.png");

			renderColoredCutoutModel(
				this.getParentModel(),
				identifier,
				matrixStack,
				vertexConsumerProvider,
				light,
				glare,
				-1
			);
		}
	}
}

