package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.effect.ReachStatusEffect;
import com.faboslav.friendsandfoes.common.init.registry.ReferenceRegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * @see net.minecraft.world.effect.MobEffects
 */
public final class FriendsAndFoesStatusEffects
{
	public static final ResourcefulRegistry<MobEffect> STATUS_EFFECTS = ResourcefulRegistries.create(BuiltInRegistries.MOB_EFFECT, FriendsAndFoes.MOD_ID);

	public static final ReferenceRegistryEntry<MobEffect> REACH = STATUS_EFFECTS.registerReference("reach", () -> new ReachStatusEffect(MobEffectCategory.BENEFICIAL, 0xFE984B));
}
