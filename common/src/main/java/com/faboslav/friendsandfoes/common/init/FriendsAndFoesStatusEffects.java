package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.effect.ReachStatusEffect;
import com.faboslav.friendsandfoes.common.init.registry.ReferenceRegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;

/**
 * @see net.minecraft.entity.effect.StatusEffects
 */
public final class FriendsAndFoesStatusEffects
{
	public static final ResourcefulRegistry<StatusEffect> STATUS_EFFECTS = ResourcefulRegistries.create(Registries.STATUS_EFFECT, FriendsAndFoes.MOD_ID);

	public static final ReferenceRegistryEntry<StatusEffect> REACH = STATUS_EFFECTS.registerReference("reach", () -> new ReachStatusEffect(StatusEffectCategory.BENEFICIAL, 3364735));
}
