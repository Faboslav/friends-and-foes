package com.faboslav.friendsandfoes.common.entity.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class BoatSpeedStatusEffect extends MobEffect
{
	public BoatSpeedStatusEffect(MobEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return false;
	}
}