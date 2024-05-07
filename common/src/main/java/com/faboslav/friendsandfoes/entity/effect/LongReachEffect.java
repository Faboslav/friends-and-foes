package com.faboslav.friendsandfoes.entity.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public final class LongReachEffect extends StatusEffect
{
	public LongReachEffect(StatusEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return false;
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
	}
}