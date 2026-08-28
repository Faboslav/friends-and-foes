package com.faboslav.friendsandfoes.common.entity.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class PenguinsGlideStatusEffect extends MobEffect
{
	public PenguinsGlideStatusEffect(MobEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
	}

	@Override
	//? if >= 1.21.1 {
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier)
	//?} else {
	/*public boolean isDurationEffectTick(int duration, int amplifier)
	*///?}
	{
		return false;
	}
}