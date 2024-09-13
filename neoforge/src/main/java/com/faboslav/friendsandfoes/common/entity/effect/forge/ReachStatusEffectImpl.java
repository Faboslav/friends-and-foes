package com.faboslav.friendsandfoes.common.entity.effect.forge;

import com.faboslav.friendsandfoes.common.entity.effect.ReachStatusEffect;
import net.minecraft.entity.attribute.EntityAttribute;
import net.neoforged.neoforge.common.NeoForgeMod;
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
		return NeoForgeMod.BLOCK_REACH.value();
	}
}
