package com.faboslav.friendsandfoes.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class BlockRenderLayerMapRegistry
{
	public static void init() {
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BUTTERCUP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.POTTED_BUTTERCUP, RenderLayer.getCutout());
	}
}
