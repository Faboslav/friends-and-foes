package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.mixin.BrewingRecipeRegistryMixin;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;

/**
 * @see net.minecraft.recipe.BrewingRecipeRegistry
 */
public final class FriendsAndFoesRecipes
{
	public static void registerBrewingRecipes() {
		BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.AWKWARD, FriendsAndFoesItems.CRAB_CLAW.get(), FriendsAndFoesPotions.REACHING.get());
		BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(FriendsAndFoesPotions.REACHING.get(), Items.REDSTONE, FriendsAndFoesPotions.LONG_REACHING.get());
	}
}
