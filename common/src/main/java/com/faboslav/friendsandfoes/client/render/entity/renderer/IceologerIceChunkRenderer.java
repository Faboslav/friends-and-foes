package com.faboslav.friendsandfoes.client.render.entity.renderer;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.model.IceologerIceChunkModel;
import com.faboslav.friendsandfoes.entity.IceologerIceChunkEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityModelLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class IceologerIceChunkRenderer extends EntityRenderer<IceologerIceChunkEntity>
{
	private static final Identifier TEXTURE = FriendsAndFoes.makeID("textures/entity/illager/ice_chunk.png");
	private final IceologerIceChunkModel<IceologerIceChunkEntity> model;

	public IceologerIceChunkRenderer(Context context) {
		super(context);
		this.model = new IceologerIceChunkModel(context.getPart(FriendsAndFoesEntityModelLayer.ICEOLOGER_ICE_CHUNK_LAYER));
	}

	public void render(
		IceologerIceChunkEntity iceChunk,
		float f,
		float tickDelta,
		MatrixStack matrixStack,
		VertexConsumerProvider vertexConsumerProvider,
		int i
	) {
		var summonAnimationProgress = MathHelper.lerp(
			MinecraftClient.getInstance().getTickDelta(),
			iceChunk.getLastSummonAnimationProgress(),
			iceChunk.getSummonAnimationProgress()
		);
		matrixStack.push();
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F - 180.0F - iceChunk.getYaw()));
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));
		this.model.setAngles(iceChunk, 0.0F, 0.0F, 0.0F, iceChunk.getYaw(), iceChunk.getPitch());
		this.model.animateModel(iceChunk, 0.0F, 0.0F, tickDelta);
		matrixStack.scale(summonAnimationProgress, summonAnimationProgress, summonAnimationProgress);
		this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStack.pop();
		super.render(iceChunk, f, tickDelta, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	public Identifier getTexture(IceologerIceChunkEntity entity) {
		return TEXTURE;
	}
}

