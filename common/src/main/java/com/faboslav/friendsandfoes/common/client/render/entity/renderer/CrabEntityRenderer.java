package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.CrabEntityModel;
import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class CrabEntityRenderer extends MobRenderer<CrabEntity, CrabEntityModel<CrabEntity>>
{
	private static final float SHADOW_RADIUS = 0.5F;

	public CrabEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new CrabEntityModel<>(context.bakeLayer(FriendsAndFoesEntityModelLayers.CRAB_LAYER)), SHADOW_RADIUS);
	}

	@Override
	public ResourceLocation getTextureLocation(CrabEntity entity) {
		return FriendsAndFoes.makeID("textures/entity/crab/crab.png");
	}

	@Override
	public void render(
		CrabEntity crab,
		float f,
		float tickDelta,
		PoseStack matrixStack,
		MultiBufferSource vertexConsumerProvider,
		int i
	) {
		this.shadowRadius = SHADOW_RADIUS * crab.getSize().getScaleModifier();

		if (crab.isBaby()) {
			this.shadowRadius *= 0.5F;
		}

		super.render(crab, f, tickDelta, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	protected void scale(CrabEntity crab, PoseStack matrixStack, float f) {
		CrabEntity.CrabSize size = crab.getSize();
		float scaleModifier = size.getScaleModifier();
		matrixStack.scale(scaleModifier, scaleModifier, scaleModifier);

		if (crab.isBaby()) {
			matrixStack.scale(scaleModifier * 0.3F, scaleModifier * 0.3F, scaleModifier * 0.3F);
		} else {
			matrixStack.scale(scaleModifier, scaleModifier, scaleModifier);
		}
	}
}