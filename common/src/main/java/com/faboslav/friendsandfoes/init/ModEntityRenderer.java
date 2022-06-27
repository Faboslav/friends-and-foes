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
	public static final EntityModelLayer WILDFIRE_LAYER;

	static {
		COPPER_GOLEM_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("copper_golem"), "main");
		GLARE_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("glare"), "main");
		ICEOLOGER_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("iceologer"), "main");
		ICEOLOGER_ICE_CHUNK_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("iceologer_ice_chunk"), "main");
		MAULER_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("mauler"), "main");
		MOOBLOOM_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("moobloom"), "main");
		WILDFIRE_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("wildfire"), "main");
	}

	public static void init() {
		EntityRendererRegistry.register(ModEntityTypes.COPPER_GOLEM, CopperGolemEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.GLARE, GlareEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.ICEOLOGER, IceologerEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.ICE_CHUNK, IceologerIceChunkRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.MAULER, MaulerEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.MOOBLOOM, MoobloomEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.WILDFIRE, WildfireEntityRenderer::new);
	}

	private ModEntityRenderer() {
	}
}
