package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.mixin.IllusionerEntityMixin;
import net.minecraft.world.entity.monster.Illusioner;

/**
 * @see IllusionerEntityMixin
 */
public interface IllusionerEntityAccess
{
	void friendsandfoes_setIllusioner(Illusioner illusionerEntity);

	void friendsandfoes_setIsIllusion(boolean isIllusion);

	void friendsandfoes_setTicksUntilDespawn(int ticksUntilDespawn);

	boolean friendsandfoes_tryToTeleport(int x, int y, int z);

	void friendsandfoes_spawnCloudParticles();
}
