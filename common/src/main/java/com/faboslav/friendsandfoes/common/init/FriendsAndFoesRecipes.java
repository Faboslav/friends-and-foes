package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.events.item.RegisterBrewingRecipesEvent;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;

/**
 * @see net.minecraft.world.item.alchemy.PotionBrewing
 */
public final class FriendsAndFoesRecipes
{
	public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
		event.registrator().accept(Potions.AWKWARD, FriendsAndFoesItems.CRAB_CLAW.get(), FriendsAndFoesPotions.REACHING.referenceRegistryEntry());
		event.registrator().accept(FriendsAndFoesPotions.REACHING.referenceRegistryEntry(), Items.REDSTONE, FriendsAndFoesPotions.LONG_REACHING.referenceRegistryEntry());
		event.registrator().accept(FriendsAndFoesPotions.REACHING.referenceRegistryEntry(), Items.GLOWSTONE_DUST, FriendsAndFoesPotions.STRONG_REACHING.referenceRegistryEntry());
	}
}
