package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.MaulerEntityModel;
import com.faboslav.friendsandfoes.common.entity.MaulerEntity;
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
public final class MaulerEntityRenderer extends MobRenderer<MaulerEntity, MaulerEntityModel<MaulerEntity>>
{
	private static final float SHADOW_RADIUS = 0.35F;

	public MaulerEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new MaulerEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.MAULER_LAYER)), SHADOW_RADIUS);
	}

	@Override
	public ResourceLocation getTextureLocation(MaulerEntity mauler) {
		return FriendsAndFoes.makeID("textures/entity/mauler/" + mauler.getMaulerType().getName() + ".png");
	}

	@Override
	public void render(
		MaulerEntity mauler,
		float f,
		float tickDelta,
		PoseStack matrixStack,
		MultiBufferSource vertexConsumerProvider,
		int i
	) {
		this.shadowRadius = mauler.isBurrowedDown() ? 0.0F:SHADOW_RADIUS;
		super.render(mauler, f, tickDelta, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	protected void scale(MaulerEntity mauler, PoseStack matrixStack, float f) {
		float size = mauler.getSize();
		matrixStack.scale(size, size, size);
	}
}