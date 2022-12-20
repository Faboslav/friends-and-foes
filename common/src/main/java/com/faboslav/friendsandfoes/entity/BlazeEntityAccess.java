package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.mixin.BlazeEntityMixin;
import org.jetbrains.annotations.Nullable;

/**
 * @see BlazeEntityMixin
 */
public interface BlazeEntityAccess
{
	void setWildfire(WildfireEntity wildfire);

	@Nullable
	WildfireEntity getWildfire();
}
