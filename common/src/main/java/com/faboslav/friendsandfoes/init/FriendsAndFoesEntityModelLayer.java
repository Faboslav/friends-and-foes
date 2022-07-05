package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.model.*;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IllagerEntityModel;

/**
 * @see EntityModelLayers
 */
@Environment(EnvType.CLIENT)
public final class FriendsAndFoesEntityModelLayer
{
	public static final EntityModelLayer COPPER_GOLEM_LAYER;
	public static final EntityModelLayer GLARE_LAYER;
	public static final EntityModelLayer ICEOLOGER_LAYER;
	public static final EntityModelLayer ICEOLOGER_ICE_CHUNK_LAYER;
	public static final EntityModelLayer MAULER_LAYER;
	public static final EntityModelLayer MOOBLOOM_LAYER;
	public static final EntityModelLayer WILDFIRE_LAYER;

	static {
		COPPER_GOLEM_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("copper_golem"), "main");
		GLARE_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("glare"), "main");
		ICEOLOGER_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("iceologer"), "main");
		ICEOLOGER_ICE_CHUNK_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("iceologer_ice_chunk"), "main");
		MAULER_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("mauler"), "main");
		MOOBLOOM_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("moobloom"), "main");
		WILDFIRE_LAYER = new EntityModelLayer(FriendsAndFoes.makeID("wildfire"), "main");
	}

	public static void init() {
		RegistryHelper.registerEntityModelLayer(COPPER_GOLEM_LAYER, CopperGolemEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(GLARE_LAYER, GlareEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(ICEOLOGER_LAYER, IllagerEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(ICEOLOGER_ICE_CHUNK_LAYER, IceologerIceChunkModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(MAULER_LAYER, MaulerEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(MOOBLOOM_LAYER, CowEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(WILDFIRE_LAYER, WildfireEntityModel::getTexturedModelData);
	}

	private FriendsAndFoesEntityModelLayer() {
	}
}
