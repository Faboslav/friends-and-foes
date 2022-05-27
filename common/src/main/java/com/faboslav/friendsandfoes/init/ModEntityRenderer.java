package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.*;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.model.EntityModelLayer;

/**
 * @see EntityRenderers
 */
@Environment(EnvType.CLIENT)
public final class ModEntityRenderer
{
	public static final EntityModelLayer COPPER_GOLEM_LAYER;
	public static final EntityModelLayer GLARE_LAYER;
	public static final EntityModelLayer ICEOLOGER_LAYER;
	public static final EntityModelLayer ICEOLOGER_ICE_CHUNK_LAYER;
	public static final EntityModelLayer MAULER_LAYER;
	public static final EntityModelLayer MOOBLOOM_LAYER;

	static {
		COPPER_GOLEM_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("copper_golem"), "main");
		GLARE_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("glare"), "main");
		ICEOLOGER_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("iceologer"), "main");
		ICEOLOGER_ICE_CHUNK_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("iceologer_ice_chunk"), "main");
		MAULER_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("mauler"), "main");
		MOOBLOOM_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("moobloom"), "main");
	}

	public static void init() {
		EntityRendererRegistry.register(ModEntity.COPPER_GOLEM::get, CopperGolemEntityRenderer::new);
		EntityRendererRegistry.register(ModEntity.GLARE::get, GlareEntityRenderer::new);
		EntityRendererRegistry.register(ModEntity.ICEOLOGER::get, IceologerEntityRenderer::new);
		EntityRendererRegistry.register(ModEntity.ICE_CHUNK::get, IceologerIceChunkRenderer::new);
		EntityRendererRegistry.register(ModEntity.MAULER::get, MaulerEntityRenderer::new);
		EntityRendererRegistry.register(ModEntity.MOOBLOOM::get, MoobloomEntityRenderer::new);
	}

	private ModEntityRenderer() {
	}
}
