package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.model.*;
import com.faboslav.friendsandfoes.events.client.RegisterEntityLayersEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;

/**
 * @see EntityModelLayers
 */
@Environment(EnvType.CLIENT)
public final class FriendsAndFoesEntityModelLayers
{
	public static final EntityModelLayer BARNACLE_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("barnacle"), "main");
	public static final EntityModelLayer COPPER_GOLEM_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("copper_golem"), "main");
	public static final EntityModelLayer CRAB_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("crab"), "main");
	public static final EntityModelLayer GLARE_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("glare"), "main");
	public static final EntityModelLayer PENGUIN_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("penguin"), "main");
	public static final EntityModelLayer ICEOLOGER_ICE_CHUNK_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("iceologer_ice_chunk"), "main");
	public static final EntityModelLayer MAULER_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("mauler"), "main");
	public static final EntityModelLayer RASCAL_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("rascal"), "main");
	public static final EntityModelLayer TUFF_GOLEM_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("tuff_golem"), "main");
	public static final EntityModelLayer WILDFIRE_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("wildfire"), "main");

	public static void registerEntityLayers(RegisterEntityLayersEvent event) {
		event.register(BARNACLE_LAYER, BarnacleEntityModel::getTexturedModelData);
		event.register(COPPER_GOLEM_LAYER, CopperGolemEntityModel::getTexturedModelData);
		event.register(CRAB_LAYER, CrabEntityModel::getTexturedModelData);
		event.register(GLARE_LAYER, GlareEntityModel::getTexturedModelData);
		event.register(PENGUIN_LAYER, PenguinEntityModel::getTexturedModelData);
		event.register(ICEOLOGER_ICE_CHUNK_LAYER, IceologerIceChunkModel::getTexturedModelData);
		event.register(MAULER_LAYER, MaulerEntityModel::getTexturedModelData);
		event.register(RASCAL_LAYER, RascalEntityModel::getTexturedModelData);
		event.register(TUFF_GOLEM_LAYER, TuffGolemEntityModel::getTexturedModelData);
		event.register(WILDFIRE_LAYER, WildfireEntityModel::getTexturedModelData);
	}

	private FriendsAndFoesEntityModelLayers() {
	}
}
