package com.faboslav.friendsandfoes.common.entity.effect;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ReachStatusEffect extends MobEffect
{
	public ReachStatusEffect(MobEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
		this.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, FriendsAndFoes.makeID("effect.reach"), FriendsAndFoes.getConfig().reachingStatusEffectModifier, AttributeModifier.Operation.ADD_VALUE);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return false;
	}
}