package com.faboslav.friendsandfoes.common.entity.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

//? if >= 1.21.1 {
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import com.faboslav.friendsandfoes.common.FriendsAndFoes;
//?}

public class ReachStatusEffect extends MobEffect
{
	public ReachStatusEffect(MobEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
		//? if >= 1.21.1 {
		this.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, FriendsAndFoes.makeID("effect.reach"), FriendsAndFoes.getConfig().reachingStatusEffectModifier, AttributeModifier.Operation.ADD_VALUE);
		//?}
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