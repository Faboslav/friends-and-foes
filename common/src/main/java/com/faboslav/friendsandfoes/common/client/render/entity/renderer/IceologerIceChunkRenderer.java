package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.IceologerIceChunkModel;
import com.faboslav.friendsandfoes.common.entity.IceologerIceChunkEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class IceologerIceChunkRenderer extends EntityRenderer<IceologerIceChunkEntity>
{
	private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/illager/ice_chunk.png");
	private final IceologerIceChunkModel<IceologerIceChunkEntity> model;

	public IceologerIceChunkRenderer(Context context) {
		super(context);
		this.model = new IceologerIceChunkModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.ICEOLOGER_ICE_CHUNK_LAYER));
	}

	public void render(
		IceologerIceChunkEntity iceChunk,
		float f,
		float tickDelta,
		PoseStack matrixStack,
		MultiBufferSource vertexConsumerProvider,
		int i
	) {
		var summonAnimationProgress = Mth.lerp(
			Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true),
			iceChunk.getLastSummonAnimationProgress(),
			iceChunk.getSummonAnimationProgress()
		);
		matrixStack.pushPose();
		matrixStack.mulPose(Axis.YP.rotationDegrees(90.0F - 180.0F - iceChunk.getYRot()));
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.renderType(TEXTURE));
		this.model.setupAnim(iceChunk, 0.0F, 0.0F, 0.0F, iceChunk.getYRot(), iceChunk.getXRot());
		this.model.prepareMobModel(iceChunk, 0.0F, 0.0F, tickDelta);
		matrixStack.scale(summonAnimationProgress, summonAnimationProgress, summonAnimationProgress);
		this.model.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, -1);
		matrixStack.popPose();
		super.render(iceChunk, f, tickDelta, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	public ResourceLocation getTextureLocation(IceologerIceChunkEntity entity) {
		return TEXTURE;
	}
}

