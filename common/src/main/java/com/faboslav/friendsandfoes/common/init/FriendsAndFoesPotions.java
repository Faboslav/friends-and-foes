package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.registry.ReferenceRegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;

/**
 * @see net.minecraft.potion.Potions
 */
public final class FriendsAndFoesPotions {
	public static final ResourcefulRegistry<Potion> POTIONS = ResourcefulRegistries.create(Registries.POTION, FriendsAndFoes.MOD_ID);

	public static final ReferenceRegistryEntry<Potion> REACHING = POTIONS.registerReference("reaching", () -> new Potion("reaching", new StatusEffectInstance(FriendsAndFoesStatusEffects.REACH.reference(), 72000)));
	public static final ReferenceRegistryEntry<Potion> LONG_REACHING = POTIONS.registerReference("long_reaching", () -> new Potion("reaching", new StatusEffectInstance(FriendsAndFoesStatusEffects.REACH.reference(), 144000)));
	public static final ReferenceRegistryEntry<Potion> STRONG_REACHING = POTIONS.registerReference("strong_reaching", () -> new Potion("reaching", new StatusEffectInstance(FriendsAndFoesStatusEffects.REACH.reference(), 1800, 1)));
}
