package com.faboslav.friendsandfoes.init;

import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public final class ModRenderType
{
	public static void init() {
		RenderTypeRegistry.register(RenderLayer.getCutout(), ModBlocks.BUTTERCUP.get());
		RenderTypeRegistry.register(RenderLayer.getCutout(), ModBlocks.POTTED_BUTTERCUP.get());
	}

	private ModRenderType() {
	}
}
