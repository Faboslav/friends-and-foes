package com.faboslav.friendsandfoes.common.entity.effect;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStatusEffects;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;

public class LongReachStatusEffect extends StatusEffect
{
	public LongReachStatusEffect(StatusEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return false;
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		double reachModifier = FriendsAndFoes.getConfig().longReachStatusEffectModifier + (float) amplifier;
	}

	@Override
	public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		super.onRemoved(entity, attributes, amplifier);
		customOnRemoved(entity, attributes, amplifier);
	}

	@Override
	@ExpectPlatform
	public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		super.onApplied(entity, attributes, amplifier);
		customOnApplied(entity, attributes, amplifier);
	}

	@ExpectPlatform
	public static void customOnRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		throw new NotImplementedException();
	}

	@ExpectPlatform
	public static void customOnApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		throw new NotImplementedException();
	}

	public static double getModifiedReachDistance(@Nullable LivingEntity entity, double currentReachDistance) {
		if (entity != null && entity.hasStatusEffect(FriendsAndFoesStatusEffects.LONG_REACH.get())) {
			StatusEffectInstance longReach = entity.getStatusEffect(FriendsAndFoesStatusEffects.LONG_REACH.get());
			// TODO change use proper things
			//return currentReachDistance + LongReachStatusEffect.REACH_MODIFIER + (float) longReach.getAmplifier();
		}

		return currentReachDistance;
	}

	public static double getModifiedSquaredReachDistance(@Nullable LivingEntity entity, double currentReachDistance) {
		double reachDistance = getModifiedReachDistance(entity, Math.sqrt(currentReachDistance));
		return reachDistance * reachDistance;
	}
}