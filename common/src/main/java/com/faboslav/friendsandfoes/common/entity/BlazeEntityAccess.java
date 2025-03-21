package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.mixin.BlazeEntityMixin;
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
