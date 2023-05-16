package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.mixin.IllusionerEntityMixin;
import net.minecraft.entity.mob.IllusionerEntity;

/**
 * @see IllusionerEntityMixin
 */
public interface IllusionerEntityAccess
{
	void setIllusioner(IllusionerEntity illusionerEntity);

	void setIsIllusion(boolean isIllusion);

	void setTicksUntilDespawn(int ticksUntilDespawn);

	boolean tryToTeleport(int x, int y, int z);

	void spawnCloudParticles();
}
