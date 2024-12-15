package com.faboslav.friendsandfoes.common.client.render.entity.model;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

public final class PlayerIllusionEntityModel<T extends LivingEntity> extends PlayerModel<T>
{
	public PlayerIllusionEntityModel(ModelPart root, boolean slim) {
		super(root, slim);
	}
}