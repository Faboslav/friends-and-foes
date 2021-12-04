package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.registry.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class RegistryInit
{
    public static void init() {
        BlockRegistry.init();
        BlockTagRegistry.init();
        OxidizableBlockRegistry.init();
        ItemRegistry.init();
        EntityRegistry.init();
        SoundRegistry.init();
        VillagerProfessionRegistry.init();
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        BlockRenderLayerMapRegistry.init();
        EntityRendererRegistry.init();
    }

    @Environment(EnvType.SERVER)
    public static void initServer() {
        StructurePoolInit.init();
    }
}
