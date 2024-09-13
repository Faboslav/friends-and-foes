package com.faboslav.friendsandfoes.common.entity.effect.forge;

import com.faboslav.friendsandfoes.common.entity.effect.ReachStatusEffect;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraftforge.common.ForgeMod;

/**
 * @see ReachStatusEffect
 */
public class ReachStatusEffectImpl
{
	public static EntityAttribute getReachAttribute() {
		return ForgeMod.BLOCK_REACH.get();
	}
}
