package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.client.render.entity.renderer.*;
import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.faboslav.friendsandfoes.common.events.client.RegisterEntityRenderersEvent;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.util.SkinTextures;

import java.util.Map;

/**
 * @see EntityRenderers
 */
@Environment(EnvType.CLIENT)
public final class FriendsAndFoesEntityRenderers
{
	private static final Map<SkinTextures.Model, EntityRendererFactory<PlayerIllusionEntity>> PLAYER_ILLUSION_RENDERER_FACTORIES = Map.of(SkinTextures.Model.WIDE, (context) -> {
		return new PlayerIllusionEntityRenderer(context, false);
	}, SkinTextures.Model.SLIM, (context) -> {
		return new PlayerIllusionEntityRenderer(context, true);
	});

	public static void registerEntityRenderers(RegisterEntityRenderersEvent event) {
		event.register(FriendsAndFoesEntityTypes.COPPER_GOLEM.get(), CopperGolemEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.CRAB.get(), CrabEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.GLARE.get(), GlareEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.ICEOLOGER.get(), IceologerEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.ICE_CHUNK.get(), IceologerIceChunkRenderer::new);
		event.register(FriendsAndFoesEntityTypes.MAULER.get(), MaulerEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.MOOBLOOM.get(), MoobloomEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.RASCAL.get(), RascalEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.TUFF_GOLEM.get(), TuffGolemEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.WILDFIRE.get(), WildfireEntityRenderer::new);
	}

	public static Map<SkinTextures.Model, EntityRenderer<? extends PlayerIllusionEntity>> reloadPlayerIllusionRenderers(
		EntityRendererFactory.Context ctx
	) {
		ImmutableMap.Builder builder = ImmutableMap.builder();
		PLAYER_ILLUSION_RENDERER_FACTORIES.forEach((model, factory) -> {
			try {
				builder.put(model, factory.create(ctx));
			} catch (Exception exception) {
				throw new IllegalArgumentException("Failed to create player illusion model for " + model, exception);
			}
		});
		return builder.build();
	}

	private FriendsAndFoesEntityRenderers() {
	}
}
