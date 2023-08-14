package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.mixin.ZombieHorseEntityMixin;

/**
 * @see ZombieHorseEntityMixin
 */
public interface ZombieHorseEntityAccess
{
	boolean friendsandfoes_isTrapped();

	void friendsandfoes_setTrapped(boolean isTrapped);
}
