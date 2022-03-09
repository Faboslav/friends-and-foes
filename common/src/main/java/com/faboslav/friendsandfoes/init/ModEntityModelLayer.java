package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.client.render.entity.model.CopperGolemEntityModel;
import com.faboslav.friendsandfoes.client.render.entity.model.GlareEntityModel;
import com.faboslav.friendsandfoes.client.render.entity.model.IceologerIceChunkModel;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.IllagerEntityModel;

public final class ModEntityModelLayer
{
	public static void init() {
		EntityModelLayerRegistry.register(ModEntityRenderer.COPPER_GOLEM_LAYER, CopperGolemEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.register(ModEntityRenderer.GLARE_LAYER, GlareEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.register(ModEntityRenderer.ICEOLOGER_LAYER, IllagerEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.register(ModEntityRenderer.ICEOLOGER_ICE_CHUNK_LAYER, IceologerIceChunkModel::getTexturedModelData);
		EntityModelLayerRegistry.register(ModEntityRenderer.MOOBLOOM_LAYER, CowEntityModel::getTexturedModelData);
	}
}
