package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

public final class VersionedMobEffects
{
	/*? if >=1.21.5 {*/
	public static final Holder<MobEffect> MOVEMENT_SPEED = MobEffects.SPEED;
	public static final Holder<MobEffect> MOVEMENT_SLOWNESS = MobEffects.SLOWNESS;
	/*?} else {*/
	/*public static final Holder<MobEffect> MOVEMENT_SPEED = MobEffects.MOVEMENT_SPEED;
	public static final Holder<MobEffect> MOVEMENT_SLOWNESS = MobEffects.MOVEMENT_SLOWDOWN;
	*///?}
}