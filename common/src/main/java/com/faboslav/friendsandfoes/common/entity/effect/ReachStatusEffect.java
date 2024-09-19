package com.faboslav.friendsandfoes.common.entity.effect;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import org.apache.commons.lang3.NotImplementedException;

public class ReachStatusEffect extends StatusEffect
{
	public ReachStatusEffect(StatusEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
		this.addAttributeModifier(ReachStatusEffect.getReachAttribute(), "9a6163d0-6aac-4d9a-ace1-74e2c47d5cd9", FriendsAndFoes.getConfig().reachingStatusEffectModifier, EntityAttributeModifier.Operation.ADDITION);
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return false;
	}

	@ExpectPlatform
	public static EntityAttribute getReachAttribute() {
		throw new NotImplementedException();
	}
}