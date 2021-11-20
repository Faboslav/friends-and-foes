package com.faboslav.friendsandfoes.registry;


import com.faboslav.friendsandfoes.client.render.entity.CopperGolemRenderer;
import com.faboslav.friendsandfoes.client.render.entity.MoobloomEntityRenderer;
import com.faboslav.friendsandfoes.client.render.entity.model.CopperGolemEntityModel;
import com.faboslav.friendsandfoes.config.Settings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

@Environment(EnvType.CLIENT)
public class EntityRendererRegistry
{
    public static final EntityModelLayer COPPER_GOLEM_LAYER = new EntityModelLayer(Settings.makeID("copper_golem"), "main");

    public static void init() {
        net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(EntityRegistry.MOOBLOOM, MoobloomEntityRenderer::new);
        net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(EntityRegistry.COPPER_GOLEM, CopperGolemRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(
                COPPER_GOLEM_LAYER,
                CopperGolemEntityModel::getTexturedModelData
        );
    }
}