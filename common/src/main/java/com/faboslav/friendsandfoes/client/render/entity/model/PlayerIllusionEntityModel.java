package com.faboslav.friendsandfoes.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;

public final class PlayerIllusionEntityModel<T extends LivingEntity> extends PlayerEntityModel<T>
{
	public PlayerIllusionEntityModel(ModelPart root) {
		super(root, false);
	}

	public PlayerIllusionEntityModel(ModelPart root, boolean thinArms) {
		super(root, thinArms);
	}
}