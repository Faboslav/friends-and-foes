package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.mixin.ZombieHorseEntityMixin;

/**
 * @see ZombieHorseEntityMixin
 */
public interface ZombieHorseEntityAccess
{
	boolean isTrapped();

	void setTrapped(boolean isTrapped);
}
