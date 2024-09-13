package com.faboslav.friendsandfoes.common.entity.effect.fabric;

import com.faboslav.friendsandfoes.common.entity.effect.ReachStatusEffect;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.entity.attribute.EntityAttribute;

/**
 * @see ReachStatusEffect
 */
public class ReachStatusEffectImpl
{
	public static EntityAttribute getReachAttribute() {
		return ReachEntityAttributes.REACH;
	}
}
