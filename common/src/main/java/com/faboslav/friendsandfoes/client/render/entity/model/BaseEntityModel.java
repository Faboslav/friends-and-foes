package com.faboslav.friendsandfoes.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;

@Environment(EnvType.CLIENT)
public abstract class BaseEntityModel<E extends Entity> extends SinglePartEntityModel<E>
{
	protected static final String MODEL_PART_ROOT = "root";
	protected final ModelPart root;

	protected BaseEntityModel(ModelPart root) {
		this.root = root;
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}
