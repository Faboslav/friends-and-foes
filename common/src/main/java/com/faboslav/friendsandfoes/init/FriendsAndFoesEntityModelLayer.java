package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.model.*;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;

/**
 * @see EntityModelLayers
 */
@Environment(EnvType.CLIENT)
public final class FriendsAndFoesEntityModelLayer
{
	public static final EntityModelLayer BARNACLE_LAYER;
	public static final EntityModelLayer COPPER_GOLEM_LAYER;
	public static final EntityModelLayer GLARE_LAYER;
	public static final EntityModelLayer ICEOLOGER_ICE_CHUNK_LAYER;
	public static final EntityModelLayer MAULER_LAYER;
	public static final EntityModelLayer RASCAL_LAYER;
	public static final EntityModelLayer TUFF_GOLEM_LAYER;
	public static final EntityModelLayer WILDFIRE_LAYER;

	static {
		BARNACLE_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("barnacle"), "main");
		COPPER_GOLEM_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("copper_golem"), "main");
		GLARE_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("glare"), "main");
		ICEOLOGER_ICE_CHUNK_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("iceologer_ice_chunk"), "main");
		MAULER_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("mauler"), "main");
		RASCAL_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("rascal"), "main");
		TUFF_GOLEM_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("tuff_golem"), "main");
		WILDFIRE_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("wildfire"), "main");
	}

	public static void init() {
		RegistryHelper.registerEntityModelLayer(BARNACLE_LAYER, BarnacleEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(COPPER_GOLEM_LAYER, CopperGolemEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(GLARE_LAYER, GlareEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(ICEOLOGER_ICE_CHUNK_LAYER, IceologerIceChunkModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(MAULER_LAYER, MaulerEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(RASCAL_LAYER, RascalEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(TUFF_GOLEM_LAYER, TuffGolemEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(WILDFIRE_LAYER, WildfireEntityModel::getTexturedModelData);
	}

	private FriendsAndFoesEntityModelLayer() {
	}
}
