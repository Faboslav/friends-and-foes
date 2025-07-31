package com.faboslav.friendsandfoes.common.entity.effect;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BoatSpeedStatusEffect extends MobEffect
{
	public BoatSpeedStatusEffect(MobEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
		this.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, FriendsAndFoes.makeID("effect.boat_speed"), FriendsAndFoes.getConfig().penguinBoatSpeedStatusEffectModifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return false;
	}
}