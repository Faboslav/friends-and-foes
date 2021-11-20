package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.registry.*;

public class RegistryInit
{
    public static void init() {
        BlockRegistry.init();
        BlockTagRegistry.init();
        ItemRegistry.init();
        EntityRegistry.init();
        SoundRegistry.init();
    }

    public static void initClient() {
        BlockRenderLayerMapRegistry.init();
        EntityRendererRegistry.init();
    }
}
