package com.faboslav.friendsandfoes.api;

import net.minecraft.client.render.entity.IllusionerEntityRenderer;
import net.minecraft.entity.mob.IllusionerEntity;

/**
 * @see IllusionerEntityRenderer
 */
public interface IllusionerEntittyRendererAccess
{
	void render();

	boolean isVisible(IllusionerEntity illusioner);
}
