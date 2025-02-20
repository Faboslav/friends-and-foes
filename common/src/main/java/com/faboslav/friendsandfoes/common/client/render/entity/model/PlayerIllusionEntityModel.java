package com.faboslav.friendsandfoes.common.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;

//? >=1.21.3 {

//?} else {
/*import net.minecraft.world.entity.LivingEntity;
*///?}

@Environment(EnvType.CLIENT)
//? >=1.21.3 {
public final class PlayerIllusionEntityModel extends PlayerModel
//?} else {
/*public final class PlayerIllusionEntityModel<T extends LivingEntity> extends PlayerModel<T>
*///?}
{
	public PlayerIllusionEntityModel(ModelPart root, boolean slim) {
		super(root, slim);
	}
}