package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.TuffGolemEntityModel;
import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public final class TuffGolemClothFeatureRenderer extends RenderLayer<TuffGolemEntity, TuffGolemEntityModel<TuffGolemEntity>>
{
	public TuffGolemClothFeatureRenderer(RenderLayerParent<TuffGolemEntity, TuffGolemEntityModel<TuffGolemEntity>> featureRendererContext) {
		super(featureRendererContext);
	}

	public void render(
		PoseStack matrixStack,
		MultiBufferSource vertexConsumerProvider,
		int light,
		TuffGolemEntity tuffGolem,
		float f,
		float g,
		float h,
		float j,
		float k,
		float l
	) {
		if (tuffGolem.isInvisible()) {
			return;
		}

		ResourceLocation identifier = FriendsAndFoes.makeID("textures/entity/tuff_golem/" + tuffGolem.getColor().getName() + ".png");

		renderColoredCutoutModel(
			this.getParentModel(),
			identifier,
			matrixStack,
			vertexConsumerProvider,
			light,
			tuffGolem,
			-1
		);
	}
}

