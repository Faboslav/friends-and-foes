package com.faboslav.friendsandfoes.common.entity.effect.neoforge;

import com.faboslav.friendsandfoes.common.entity.effect.ReachStatusEffect;
import net.minecraft.entity.attribute.EntityAttribute;
import net.neoforged.neoforge.common.NeoForgeMod;

/**
 * @see ReachStatusEffect
 */
public class ReachStatusEffectImpl
{
	public static EntityAttribute getReachAttribute() {
		return NeoForgeMod.BLOCK_REACH.value();
	}
}
