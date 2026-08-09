package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.CrabEntityModel;
import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.PoseStack;

@SuppressWarnings({"all"})

public final class CrabEntityRenderer extends MobRenderer<CrabEntity, CrabEntityModel<CrabEntity>>

{
	private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/crab/crab.png");

	public CrabEntityRenderer(EntityRendererProvider.Context context) {

		super(context, new CrabEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.CRAB_LAYER)), 0.5F);

	}

	@Override

	protected float getShadowRadius(CrabEntity crab)

	{

		var shadowRadius = super.getShadowRadius(crab);

		var isBaby = crab.isBaby();

		shadowRadius = shadowRadius * crab.getSize().getScaleModifier();

		if(isBaby) {
			shadowRadius = shadowRadius * 0.5F;
		}

		return shadowRadius;
	}

	@Override
	protected void scale(CrabEntity crab, PoseStack poseStack, float partialTickTime) {
		float scale = crab.getAgeScale();
		poseStack.scale(scale, scale, scale);
	}

	@Override

		public ResourceLocation getTextureLocation(CrabEntity crab)

	{
		return TEXTURE;
	}
}