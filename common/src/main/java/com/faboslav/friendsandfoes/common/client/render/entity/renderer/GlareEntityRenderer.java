package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.GlareFlowerFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.model.GlareEntityModel;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.PoseStack;

@SuppressWarnings({"all"})

public final class GlareEntityRenderer extends MobRenderer<GlareEntity, GlareEntityModel<GlareEntity>>

{
	private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/glare/glare.png");

	public GlareEntityRenderer(EntityRendererProvider.Context context) {

		super(context, new GlareEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.GLARE_LAYER)), 0.4F);

		this.addLayer(new GlareFlowerFeatureRenderer(this));
	}

	@Override
	protected void scale(GlareEntity glare, PoseStack poseStack, float partialTickTime) {
		float scale = glare.getAgeScale();
		poseStack.scale(scale, scale, scale);
	}

	@Override

	public ResourceLocation getTextureLocation(GlareEntity glare)

	{
		return TEXTURE;
	}
}