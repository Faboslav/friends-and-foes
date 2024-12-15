package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.GlareFlowerFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.model.GlareEntityModel;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class GlareEntityRenderer extends MobRenderer<GlareEntity, GlareEntityModel<GlareEntity>>
{
	public GlareEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new GlareEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.GLARE_LAYER)), 0.4F);
		this.addLayer(new GlareFlowerFeatureRenderer(this));
	}

	@Override
	protected void scale(GlareEntity glare, PoseStack matrixStack, float amount) {
		if (!glare.isBaby()) {
			matrixStack.scale(0.8F, 0.8F, 0.8F);
		} else {
			matrixStack.scale(0.4F, 0.4F, 0.4F);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(GlareEntity entity) {
		return FriendsAndFoes.makeID("textures/entity/glare/glare.png");
	}
}