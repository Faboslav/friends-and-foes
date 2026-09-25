package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.mixin.AbstractBoatMixin;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * @see AbstractBoatMixin
 */
public interface BoatEntityAccess
{
	void friendsandfoes$setTentaclePull(@Nullable Vec3 tentaclePull);
}
