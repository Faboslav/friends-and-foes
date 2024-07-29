package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.mixin.BrewingRecipeRegistryMixin;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;

/**
 * @see net.minecraft.recipe.BrewingRecipeRegistry
 */
public final class FriendsAndFoesRecipes
{
	public static void registerBrewingRecipes() {
		BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.AWKWARD, FriendsAndFoesItems.CRAB_CLAW.get(), FriendsAndFoesPotions.REACHING.get());
		BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(FriendsAndFoesPotions.REACHING.get(), Items.REDSTONE, FriendsAndFoesPotions.LONG_REACHING.get());
		BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(FriendsAndFoesPotions.REACHING.get(), Items.GLOWSTONE_DUST, FriendsAndFoesPotions.STRONG_REACHING.get());
	}
}
