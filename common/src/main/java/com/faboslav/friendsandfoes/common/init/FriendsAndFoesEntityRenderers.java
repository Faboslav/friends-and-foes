package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.client.render.entity.renderer.*;
import com.faboslav.friendsandfoes.common.events.client.RegisterEntityRenderersEvent;
import net.minecraft.client.renderer.entity.EntityRenderers;

//? if <= 1.21.8 {
/*import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.PlayerSkin;
import java.util.Map;
*///?}

/**
 * @see EntityRenderers
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class FriendsAndFoesEntityRenderers
{
	//? if <= 1.21.8 {
	/*private static final Map<PlayerSkin.Model, EntityRendererProvider<PlayerIllusionEntity>> PLAYER_ILLUSION_RENDERER_FACTORIES = Map.of(
		PlayerSkin.Model.WIDE,
		(EntityRendererProvider)context -> new PlayerIllusionEntityRenderer(context, false),
		PlayerSkin.Model.SLIM,
		(EntityRendererProvider)context -> new PlayerIllusionEntityRenderer(context, true)
	);
	*///?}

	public static void registerEntityRenderers(RegisterEntityRenderersEvent event) {
		event.register(FriendsAndFoesEntityTypes.COPPER_GOLEM.get(), CopperGolemEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.CRAB.get(), CrabEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.GLARE.get(), GlareEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.ICEOLOGER.get(), IceologerEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.ICE_CHUNK.get(), IceologerIceChunkRenderer::new);
		event.register(FriendsAndFoesEntityTypes.ILLUSIONER.get(), IllusionerEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.MAULER.get(), MaulerEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.MOOBLOOM.get(), MoobloomEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.RASCAL.get(), RascalEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.TUFF_GOLEM.get(), TuffGolemEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.WILDFIRE.get(), WildfireEntityRenderer::new);
	}

	//? if <= 1.21.8 {
	/*//? if >=1.21.3 {
	public static Map<PlayerSkin.Model, EntityRenderer<? extends PlayerIllusionEntity, ?>> reloadPlayerIllusionRenderers(EntityRendererProvider.Context ctx)
	//?} else {
	/^public static Map<PlayerSkin.Model, EntityRenderer<? extends PlayerIllusionEntity>> reloadPlayerIllusionRenderers(EntityRendererProvider.Context ctx)
	^///?}
	{
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
	*///?}

	private FriendsAndFoesEntityRenderers() {
	}
}
