package com.faboslav.friendsandfoes.common.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;

public final class PlayerIllusionEntityModel<T extends LivingEntity> extends PlayerEntityModel<T>
{
	public PlayerIllusionEntityModel(ModelPart root, boolean slim) {
		super(root, slim);
	}
}