package com.faboslav.friendsandfoes.init;

import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.render.RenderLayer;

public final class ModRenderType
{
	public static void init() {
		RenderTypeRegistry.register(RenderLayer.getCutout(), ModBlocks.BUTTERCUP);
		RenderTypeRegistry.register(RenderLayer.getCutout(), ModBlocks.POTTED_BUTTERCUP);
	}
}
