package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.client.render.entity.renderer.*;
import com.faboslav.friendsandfoes.entity.PlayerIllusionEntity;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.SkinTextures;

import java.util.Map;

/**
 * @see EntityRenderers
 */
@Environment(EnvType.CLIENT)
public final class FriendAndFoesEntityRenderer
{
	private static final Map<SkinTextures.Model, EntityRendererFactory<PlayerIllusionEntity>> PLAYER_ILLUSION_RENDERER_FACTORIES = Map.of(SkinTextures.Model.WIDE, (context) -> {
		return new PlayerIllusionEntityRenderer(context, false);
	}, SkinTextures.Model.SLIM, (context) -> {
		return new PlayerIllusionEntityRenderer(context, true);
	});

	public static void postInit() {
		RegistryHelper.registerEntityRenderer(FriendsAndFoesEntityTypes.COPPER_GOLEM, CopperGolemEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(FriendsAndFoesEntityTypes.GLARE, GlareEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(FriendsAndFoesEntityTypes.ICEOLOGER, IceologerEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(FriendsAndFoesEntityTypes.ICE_CHUNK, IceologerIceChunkRenderer::new);
		RegistryHelper.registerEntityRenderer(FriendsAndFoesEntityTypes.MAULER, MaulerEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(FriendsAndFoesEntityTypes.MOOBLOOM, MoobloomEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(FriendsAndFoesEntityTypes.RASCAL, RascalEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(FriendsAndFoesEntityTypes.TUFF_GOLEM, TuffGolemEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(FriendsAndFoesEntityTypes.WILDFIRE, WildfireEntityRenderer::new);
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

	private FriendAndFoesEntityRenderer() {
	}
}
