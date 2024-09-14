package com.faboslav.friendsandfoes.common.entity.effect;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ReachStatusEffect extends StatusEffect
{
	public ReachStatusEffect(StatusEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
		this.addAttributeModifier(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE, FriendsAndFoes.makeID("effect.reach"), FriendsAndFoes.getConfig().reachingStatusEffectModifier, EntityAttributeModifier.Operation.ADD_VALUE);
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return false;
	}
}