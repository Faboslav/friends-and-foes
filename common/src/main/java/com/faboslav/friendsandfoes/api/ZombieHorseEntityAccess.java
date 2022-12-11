package com.faboslav.friendsandfoes.api;

import com.faboslav.friendsandfoes.mixin.ZombieHorseEntityMixin;

/**
 * @see ZombieHorseEntityMixin
 */
public interface ZombieHorseEntityAccess
{
	boolean isTrapped();

	void setTrapped(boolean isTrapped);
}
