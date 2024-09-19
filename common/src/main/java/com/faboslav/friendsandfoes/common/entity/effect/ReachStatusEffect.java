package com.faboslav.friendsandfoes.common.entity.effect;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ReachStatusEffect extends StatusEffect
{
	public ReachStatusEffect(StatusEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
		// TODO check it
		this.addAttributeModifier(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE, "9a6163d0-6aac-4d9a-ace1-74e2c47d5cd9", FriendsAndFoes.getConfig().reachingStatusEffectModifier, EntityAttributeModifier.Operation.ADD_VALUE);
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return false;
	}
}