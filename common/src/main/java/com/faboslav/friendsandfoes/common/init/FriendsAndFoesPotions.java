package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

/**
 * @see net.minecraft.world.item.alchemy.Potions
 */
public final class FriendsAndFoesPotions
{
	public static final ResourcefulRegistry<Potion> POTIONS = ResourcefulRegistries.create(BuiltInRegistries.POTION, FriendsAndFoes.MOD_ID);

	public static final HolderRegistryEntry<Potion> REACHING = POTIONS.registerHolder("reaching", () -> new Potion("reaching", new MobEffectInstance(FriendsAndFoesStatusEffects.REACH.holder(), 72000)));
	public static final HolderRegistryEntry<Potion> LONG_REACHING = POTIONS.registerHolder("long_reaching", () -> new Potion("reaching", new MobEffectInstance(FriendsAndFoesStatusEffects.REACH.holder(), 144000)));
	public static final HolderRegistryEntry<Potion> STRONG_REACHING = POTIONS.registerHolder("strong_reaching", () -> new Potion("reaching", new MobEffectInstance(FriendsAndFoesStatusEffects.REACH.holder(), 36000, 1)));
}
