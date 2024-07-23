package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.mixin.ZombieHorseEntityMixin;

/**
 * @see ZombieHorseEntityMixin
 */
public interface ZombieHorseEntityAccess
{
	boolean friendsandfoes_isTrapped();

	void friendsandfoes_setTrapped(boolean isTrapped);
}
