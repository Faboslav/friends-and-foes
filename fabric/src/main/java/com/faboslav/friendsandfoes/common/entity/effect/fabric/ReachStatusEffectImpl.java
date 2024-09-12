package com.faboslav.friendsandfoes.common.entity.effect.fabric;

import com.faboslav.friendsandfoes.common.entity.effect.ReachStatusEffect;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.entity.attribute.EntityAttribute;
import org.apache.commons.lang3.NotImplementedException;

/**
 * @see ReachStatusEffect
 */
public class ReachStatusEffectImpl
{
	public static void init() {
		throw new NotImplementedException();
	}

	public static EntityAttribute getReachAttribute() {
		return ReachEntityAttributes.REACH;
	}
}
