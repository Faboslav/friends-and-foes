package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.client.render.entity.renderer.*;
import com.faboslav.friendsandfoes.events.client.RegisterEntityRenderersEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderers;

/**
 * @see EntityRenderers
 */
@Environment(EnvType.CLIENT)
public final class FriendsAndFoesEntityRenderers
{
	public static void registerEntityRenderers(RegisterEntityRenderersEvent event) {
		event.register(FriendsAndFoesEntityTypes.BARNACLE.get(), BarnacleEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.COPPER_GOLEM.get(), CopperGolemEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.CRAB.get(), CrabEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.GLARE.get(), GlareEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.PENGUIN.get(), PenguinEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.ICEOLOGER.get(), IceologerEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.ICE_CHUNK.get(), IceologerIceChunkRenderer::new);
		event.register(FriendsAndFoesEntityTypes.MAULER.get(), MaulerEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.MOOBLOOM.get(), MoobloomEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.RASCAL.get(), RascalEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.TUFF_GOLEM.get(), TuffGolemEntityRenderer::new);
		event.register(FriendsAndFoesEntityTypes.WILDFIRE.get(), WildfireEntityRenderer::new);
	}

	private FriendsAndFoesEntityRenderers() {
	}
}
