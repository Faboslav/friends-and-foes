package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.versions.VersionedRegistryHolder;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

//? if >= 1.21.1 {
import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
//?}

/**
 * @see net.minecraft.world.item.alchemy.Potions
 */
public final class FriendsAndFoesPotions
{
	public static final ResourcefulRegistry<Potion> POTIONS = ResourcefulRegistries.create(BuiltInRegistries.POTION, FriendsAndFoes.MOD_ID);

	//? if >= 1.21.1 {
	public static final HolderRegistryEntry<Potion> REACHING = POTIONS.registerHolder("reaching", () -> new Potion("reaching", new MobEffectInstance(VersionedRegistryHolder.get(FriendsAndFoesStatusEffects.REACH), FriendsAndFoes.getConfig().crabPotionOfReachingDuration * 20)));
	public static final HolderRegistryEntry<Potion> LONG_REACHING = POTIONS.registerHolder("long_reaching", () -> new Potion("reaching", new MobEffectInstance(VersionedRegistryHolder.get(FriendsAndFoesStatusEffects.REACH), (FriendsAndFoes.getConfig().crabPotionOfReachingDuration * 2) * 20)));
	public static final HolderRegistryEntry<Potion> STRONG_REACHING = POTIONS.registerHolder("strong_reaching", () -> new Potion("reaching", new MobEffectInstance(VersionedRegistryHolder.get(FriendsAndFoesStatusEffects.REACH), (FriendsAndFoes.getConfig().crabPotionOfReachingDuration / 2) * 20, 1)));

	public static final HolderRegistryEntry<Potion> GLIDING = POTIONS.registerHolder("gliding", () -> new Potion("gliding", new MobEffectInstance(VersionedRegistryHolder.get(FriendsAndFoesStatusEffects.GLIDE), FriendsAndFoes.getConfig().penguinPotionOfGlidingDuration * 20)));
	public static final HolderRegistryEntry<Potion> LONG_GLIDING = POTIONS.registerHolder("long_gliding", () -> new Potion("gliding", new MobEffectInstance(VersionedRegistryHolder.get(FriendsAndFoesStatusEffects.GLIDE), ((int)(FriendsAndFoes.getConfig().penguinPotionOfGlidingDuration * 2.66666666667) * 20))));
	public static final HolderRegistryEntry<Potion> STRONG_GLIDING = POTIONS.registerHolder("strong_gliding", () -> new Potion("gliding", new MobEffectInstance(VersionedRegistryHolder.get(FriendsAndFoesStatusEffects.GLIDE), (FriendsAndFoes.getConfig().penguinPotionOfGlidingDuration / 2) * 20, 1)));
	//?} else {
	/*public static final RegistryEntry<Potion> REACHING = POTIONS.register("reaching", () -> new Potion("reaching", new MobEffectInstance(VersionedRegistryHolder.get(FriendsAndFoesStatusEffects.REACH), FriendsAndFoes.getConfig().crabPotionOfReachingDuration * 20)));
	public static final RegistryEntry<Potion> LONG_REACHING = POTIONS.register("long_reaching", () -> new Potion("reaching", new MobEffectInstance(VersionedRegistryHolder.get(FriendsAndFoesStatusEffects.REACH), (FriendsAndFoes.getConfig().crabPotionOfReachingDuration * 2) * 20)));
	public static final RegistryEntry<Potion> STRONG_REACHING = POTIONS.register("strong_reaching", () -> new Potion("reaching", new MobEffectInstance(VersionedRegistryHolder.get(FriendsAndFoesStatusEffects.REACH), (FriendsAndFoes.getConfig().crabPotionOfReachingDuration / 2) * 20, 1)));

	public static final RegistryEntry<Potion> GLIDING = POTIONS.register("gliding", () -> new Potion("gliding", new MobEffectInstance(VersionedRegistryHolder.get(FriendsAndFoesStatusEffects.GLIDE), FriendsAndFoes.getConfig().penguinPotionOfGlidingDuration * 20)));
	public static final RegistryEntry<Potion> LONG_GLIDING = POTIONS.register("long_gliding", () -> new Potion("gliding", new MobEffectInstance(VersionedRegistryHolder.get(FriendsAndFoesStatusEffects.GLIDE), ((int)(FriendsAndFoes.getConfig().penguinPotionOfGlidingDuration * 2.66666666667) * 20))));
	public static final RegistryEntry<Potion> STRONG_GLIDING = POTIONS.register("strong_gliding", () -> new Potion("gliding", new MobEffectInstance(VersionedRegistryHolder.get(FriendsAndFoesStatusEffects.GLIDE), (FriendsAndFoes.getConfig().penguinPotionOfGlidingDuration / 2) * 20, 1)));
	*///?}
}
