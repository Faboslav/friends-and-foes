package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.events.item.RegisterBrewingRecipesEvent;
import com.faboslav.friendsandfoes.common.versions.VersionedRegistryHolder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;

/**
 * @see net.minecraft.world.item.alchemy.PotionBrewing
 */
public final class FriendsAndFoesRecipes
{
	public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
		event.registrator().accept(Potions.AWKWARD, FriendsAndFoesItems.CRAB_CLAW.get(), VersionedRegistryHolder.get(FriendsAndFoesPotions.REACHING));
		event.registrator().accept(VersionedRegistryHolder.get(FriendsAndFoesPotions.REACHING), Items.REDSTONE, VersionedRegistryHolder.get(FriendsAndFoesPotions.LONG_REACHING));
		event.registrator().accept(VersionedRegistryHolder.get(FriendsAndFoesPotions.REACHING), Items.GLOWSTONE_DUST, VersionedRegistryHolder.get(FriendsAndFoesPotions.STRONG_REACHING));

		event.registrator().accept(Potions.AWKWARD, FriendsAndFoesItems.PENGUIN_FEATHER.get(), VersionedRegistryHolder.get(FriendsAndFoesPotions.GLIDING));
		event.registrator().accept(VersionedRegistryHolder.get(FriendsAndFoesPotions.GLIDING), Items.REDSTONE, VersionedRegistryHolder.get(FriendsAndFoesPotions.LONG_GLIDING));
		event.registrator().accept(VersionedRegistryHolder.get(FriendsAndFoesPotions.GLIDING), Items.GLOWSTONE_DUST, VersionedRegistryHolder.get(FriendsAndFoesPotions.STRONG_GLIDING));
	}
}
