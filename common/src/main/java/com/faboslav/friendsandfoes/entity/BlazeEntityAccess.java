package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.mixin.BlazeEntityMixin;
import org.jetbrains.annotations.Nullable;

/**
 * @see BlazeEntityMixin
 */
public interface BlazeEntityAccess
{
	void friendsandfoes_setWildfire(WildfireEntity wildfire);

	@Nullable
	WildfireEntity friendsandfoes_getWildfire();
}
