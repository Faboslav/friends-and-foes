package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.registry.ReferenceRegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

/**
 * @see net.minecraft.world.item.alchemy.Potions
 */
public final class FriendsAndFoesPotions
{
	public static final ResourcefulRegistry<Potion> POTIONS = ResourcefulRegistries.create(BuiltInRegistries.POTION, FriendsAndFoes.MOD_ID);

	public static final ReferenceRegistryEntry<Potion> REACHING = POTIONS.registerReference("reaching", () -> new Potion("reaching", new MobEffectInstance(FriendsAndFoesStatusEffects.REACH.referenceRegistryEntry(), 72000)));
	public static final ReferenceRegistryEntry<Potion> LONG_REACHING = POTIONS.registerReference("long_reaching", () -> new Potion("reaching", new MobEffectInstance(FriendsAndFoesStatusEffects.REACH.referenceRegistryEntry(), 144000)));
	public static final ReferenceRegistryEntry<Potion> STRONG_REACHING = POTIONS.registerReference("strong_reaching", () -> new Potion("reaching", new MobEffectInstance(FriendsAndFoesStatusEffects.REACH.referenceRegistryEntry(), 36000, 1)));
}
