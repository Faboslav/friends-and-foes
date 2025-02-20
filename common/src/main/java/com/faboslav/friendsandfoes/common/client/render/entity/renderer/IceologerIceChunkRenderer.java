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
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

//? >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.IceologerIceChunkRenderState;
 //?} else {
/*import net.minecraft.client.Minecraft;
*///?}

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
//? >=1.21.3 {
public class IceologerIceChunkRenderer extends EntityRenderer<IceologerIceChunkEntity, IceologerIceChunkRenderState>
//?} else {
/*public final class IceologerIceChunkRenderer extends EntityRenderer<IceologerIceChunkEntity>
*///?}
{
	private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/illager/ice_chunk.png");
	//? >=1.21.3 {
	private final IceologerIceChunkModel model;
	//?} else {
	/*private final IceologerIceChunkModel<IceologerIceChunkEntity> model;
	*///?}

	public IceologerIceChunkRenderer(Context context) {
		super(context);
		this.model = new IceologerIceChunkModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.ICEOLOGER_ICE_CHUNK_LAYER));
	}

	//? >=1.21.3 {
	@Override
	public void render(IceologerIceChunkRenderState renderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
		var iceChunk = renderState.iceologerIceChunk;
		float animationProgress = iceChunk.getSummonAnimationProgress();

		if(animationProgress == 0.0F) {
			return;
		}

		var summonAnimationProgress = Mth.lerp(
			renderState.ageInTicks,
			iceChunk.getLastSummonAnimationProgress(),
			iceChunk.getSummonAnimationProgress()
		);

		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(90.0F - 180.0F - iceChunk.getYRot()));
		this.model.setupAnim(renderState);
		VertexConsumer vertexConsumer = multiBufferSource.getBuffer(this.model.renderType(TEXTURE));
		poseStack.scale(summonAnimationProgress, summonAnimationProgress, summonAnimationProgress);
		this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, -1);
		poseStack.popPose();
		super.render(renderState, poseStack, multiBufferSource, i);
	}

	@Override
	public IceologerIceChunkRenderState createRenderState() {
		return new IceologerIceChunkRenderState();
	}

	@Override
	public void extractRenderState(IceologerIceChunkEntity iceologerIceChunk, IceologerIceChunkRenderState renderState, float partialTick) {
		super.extractRenderState(iceologerIceChunk, renderState, partialTick);
		renderState.iceologerIceChunk = iceologerIceChunk;
	}
	//?} else {
	/*@Override
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
	public ResourceLocation getTextureLocation(IceologerIceChunkEntity iceologerIceChunk)
	{
		return TEXTURE;
	}
	*///?}
}

