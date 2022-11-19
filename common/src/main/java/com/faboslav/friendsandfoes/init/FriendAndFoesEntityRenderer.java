package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.client.render.entity.renderer.*;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderers;

/**
 * @see EntityRenderers
 */
@Environment(EnvType.CLIENT)
public final class FriendAndFoesEntityRenderer
{
	public static void postInit() {
		RegistryHelper.registerEntityRenderer(FriendsAndFoesEntityTypes.COPPER_GOLEM, CopperGolemEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(FriendsAndFoesEntityTypes.GLARE, GlareEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(FriendsAndFoesEntityTypes.ICEOLOGER, IceologerEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(FriendsAndFoesEntityTypes.ICE_CHUNK, IceologerIceChunkRenderer::new);
		RegistryHelper.registerEntityRenderer(FriendsAndFoesEntityTypes.MAULER, MaulerEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(FriendsAndFoesEntityTypes.MOOBLOOM, MoobloomEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(FriendsAndFoesEntityTypes.WILDFIRE, WildfireEntityRenderer::new);
	}

	private FriendAndFoesEntityRenderer() {
	}
}
