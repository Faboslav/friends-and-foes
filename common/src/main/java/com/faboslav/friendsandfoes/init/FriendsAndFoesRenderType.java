package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public final class FriendsAndFoesRenderType
{
	public static void postInit() {
		RegistryHelper.registerRenderType(RenderLayer.getCutout(), FriendsAndFoesBlocks.BUTTERCUP.get());
		RegistryHelper.registerRenderType(RenderLayer.getCutout(), FriendsAndFoesBlocks.POTTED_BUTTERCUP.get());
	}

	private FriendsAndFoesRenderType() {
	}
}
