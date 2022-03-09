package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.*;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public final class ModEntityRenderer
{
	public static final EntityModelLayer COPPER_GOLEM_LAYER;
	public static final EntityModelLayer GLARE_LAYER;
	public static final EntityModelLayer ICEOLOGER_LAYER;
	public static final EntityModelLayer ICEOLOGER_ICE_CHUNK_LAYER;
	public static final EntityModelLayer MOOBLOOM_LAYER;

	static {
		COPPER_GOLEM_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("copper_golem"), "main");
		GLARE_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("glare"), "main");
		ICEOLOGER_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("iceologer"), "main");
		ICEOLOGER_ICE_CHUNK_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("iceologer_ice_chunk"), "main");
		MOOBLOOM_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("moobloom"), "main");
	}

	public static void init() {
		EntityRendererRegistry.register(() -> ModEntity.COPPER_GOLEM, CopperGolemEntityRenderer::new);
		EntityRendererRegistry.register(() -> ModEntity.GLARE, GlareEntityRenderer::new);
		EntityRendererRegistry.register(() -> ModEntity.ICEOLOGER, IceologerEntityRenderer::new);
		EntityRendererRegistry.register(() -> ModEntity.ICE_CHUNK, IceologerIceChunkRenderer::new);
		EntityRendererRegistry.register(() -> ModEntity.MOOBLOOM, MoobloomEntityRenderer::new);
	}
}
