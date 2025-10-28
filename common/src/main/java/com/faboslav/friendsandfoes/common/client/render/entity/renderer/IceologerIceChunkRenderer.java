package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.IceologerIceChunkModel;
import com.faboslav.friendsandfoes.common.entity.IceologerIceChunkEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

//? if >=1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
//?} else {
/*import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
*///?}

//? if >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.IceologerIceChunkRenderState;
 //?} else {
/*import net.minecraft.client.Minecraft;
*///?}

@SuppressWarnings({"rawtypes", "unchecked"})
//? if >=1.21.3 {
public class IceologerIceChunkRenderer extends EntityRenderer<IceologerIceChunkEntity, IceologerIceChunkRenderState>
//?} else {
/*public final class IceologerIceChunkRenderer extends EntityRenderer<IceologerIceChunkEntity>
*///?}
{
	private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/illager/ice_chunk.png");
	//? if >=1.21.3 {
	private final IceologerIceChunkModel model;
	//?} else {
	/*private final IceologerIceChunkModel<IceologerIceChunkEntity> model;
	*///?}

	public IceologerIceChunkRenderer(Context context) {
		super(context);
		this.model = new IceologerIceChunkModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.ICEOLOGER_ICE_CHUNK_LAYER));
	}

	//? if >= 1.21.9 {
	public void submit(IceologerIceChunkRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState)
	//?} else if >=1.21.3 {
	/*public void render(IceologerIceChunkRenderState renderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i)
	*///?} else {
	/*public void render(IceologerIceChunkEntity iceChunk, float f, float tickDelta, PoseStack poseStack, MultiBufferSource multiBufferSource, int i)
	*///?}
	{
		//? if >= 1.21.3 {
		var iceChunk = renderState.iceologerIceChunk;
		var ageInTicks = renderState.ageInTicks;
		//?} else {
		/*var ageInTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
		*///?}
		float animationProgress = iceChunk.getSummonAnimationProgress();

		if(animationProgress == 0.0F) {
			return;
		}

		var summonAnimationProgress = Mth.lerp(
			ageInTicks,
			iceChunk.getLastSummonAnimationProgress(),
			iceChunk.getSummonAnimationProgress()
		);

		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(90.0F - 180.0F - iceChunk.getYRot()));
		//? if >= 1.21.3 {
		this.model.setupAnim(renderState);
		//?} else {
		/*this.model.setupAnim(iceChunk, 0.0F, 0.0F, 0.0F, iceChunk.getYRot(), iceChunk.getXRot());
		*///?}
		poseStack.scale(summonAnimationProgress, summonAnimationProgress, summonAnimationProgress);

		//? if >= 1.21.9 {
		submitNodeCollector.submitModel(this.model, renderState, poseStack, this.model.renderType(TEXTURE), renderState.lightCoords, OverlayTexture.NO_OVERLAY, renderState.outlineColor, null);
		//?} else if >= 1.21.3 {
		/*VertexConsumer vertexConsumer = multiBufferSource.getBuffer(this.model.renderType(TEXTURE));
		this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, -1);
		*///?} else {
		/*VertexConsumer vertexConsumer = multiBufferSource.getBuffer(this.model.renderType(TEXTURE));
		this.model.setupAnim(iceChunk, 0.0F, 0.0F, 0.0F, iceChunk.getYRot(), iceChunk.getXRot());
		this.model.prepareMobModel(iceChunk, 0.0F, 0.0F, tickDelta);
		this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, -1);
		*///?}
		poseStack.popPose();


		//? if >= 1.21.9 {
		super.submit(renderState, poseStack, submitNodeCollector, cameraRenderState);
		//?} else if >=1.21.3 {
		/*super.render(renderState, poseStack, multiBufferSource, i);
		*///?} else {
		/*super.render(iceChunk, f, tickDelta, poseStack, multiBufferSource, i);
		*///?}
	}

	//? if >=1.21.3 {
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
	public ResourceLocation getTextureLocation(IceologerIceChunkEntity iceologerIceChunk)
	{
		return TEXTURE;
	}
	*///?}
}

