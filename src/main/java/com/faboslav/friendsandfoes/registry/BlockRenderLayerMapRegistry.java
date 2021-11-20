package com.faboslav.friendsandfoes.registry;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class BlockRenderLayerMapRegistry
{
    public static void init() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BUTTERCUP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.POTTED_BUTTERCUP, RenderLayer.getCutout());
    }
}
