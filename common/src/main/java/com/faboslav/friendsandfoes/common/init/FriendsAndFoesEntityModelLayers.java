package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.*;
import com.faboslav.friendsandfoes.common.events.client.RegisterEntityLayersEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;

/**
 * @see ModelLayers
 */
@Environment(EnvType.CLIENT)
public final class FriendsAndFoesEntityModelLayers
{
	public static final ModelLayerLocation COPPER_GOLEM_LAYER = new ModelLayerLocation(FriendsAndFoes.makeID("copper_golem"), "main");
	public static final ModelLayerLocation CRAB_LAYER = new ModelLayerLocation(FriendsAndFoes.makeID("crab"), "main");
	public static final ModelLayerLocation GLARE_LAYER = new ModelLayerLocation(FriendsAndFoes.makeID("glare"), "main");
	public static final ModelLayerLocation ICEOLOGER_LAYER = new ModelLayerLocation(FriendsAndFoes.makeID("iceologer"), "main");
	public static final ModelLayerLocation ICEOLOGER_ICE_CHUNK_LAYER = new ModelLayerLocation(FriendsAndFoes.makeID("iceologer_ice_chunk"), "main");
	public static final ModelLayerLocation ILLUSIONER_LAYER = new ModelLayerLocation(FriendsAndFoes.makeID("illusioner"), "main");
	public static final ModelLayerLocation MAULER_LAYER = new ModelLayerLocation(FriendsAndFoes.makeID("mauler"), "main");
	public static final ModelLayerLocation MOOBLOOM_LAYER = new ModelLayerLocation(FriendsAndFoes.makeID("moobloom"), "main");
	//? >=1.21.3 {
	public static final ModelLayerLocation MOOBLOOM_BABY_LAYER = new ModelLayerLocation(FriendsAndFoes.makeID("moobloom_baby"), "main");
	//?}
	public static final ModelLayerLocation RASCAL_LAYER = new ModelLayerLocation(FriendsAndFoes.makeID("rascal"), "main");
	public static final ModelLayerLocation TUFF_GOLEM_LAYER = new ModelLayerLocation(FriendsAndFoes.makeID("tuff_golem"), "main");
	public static final ModelLayerLocation WILDFIRE_LAYER = new ModelLayerLocation(FriendsAndFoes.makeID("wildfire"), "main");

	public static void registerEntityLayers(RegisterEntityLayersEvent event) {
		event.register(COPPER_GOLEM_LAYER, CopperGolemEntityModel::getTexturedModelData);
		event.register(CRAB_LAYER, CrabEntityModel::getTexturedModelData);
		event.register(GLARE_LAYER, GlareEntityModel::getTexturedModelData);
		event.register(ICEOLOGER_LAYER, IllagerModel::createBodyLayer);
		event.register(ICEOLOGER_ICE_CHUNK_LAYER, IceologerIceChunkModel::getTexturedModelData);
		event.register(ILLUSIONER_LAYER, IllagerModel::createBodyLayer);
		event.register(MAULER_LAYER, MaulerEntityModel::getTexturedModelData);
		event.register(MOOBLOOM_LAYER, CowModel::createBodyLayer);
		//? >=1.21.3 {
		event.register(MOOBLOOM_BABY_LAYER, () -> CowModel.createBodyLayer().apply(CowModel.BABY_TRANSFORMER));
		//?}
		event.register(RASCAL_LAYER, RascalEntityModel::getTexturedModelData);
		event.register(TUFF_GOLEM_LAYER, TuffGolemEntityModel::getTexturedModelData);
		event.register(WILDFIRE_LAYER, WildfireEntityModel::getTexturedModelData);
	}

	private FriendsAndFoesEntityModelLayers() {
	}
}
