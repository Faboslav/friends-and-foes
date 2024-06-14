package com.faboslav.friendsandfoes.mixin;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BrewingRecipeRegistry.class)
public interface BrewingRecipeRegistryMixin
{
	@Invoker("registerPotionRecipe")
	static void invokeRegisterPotionRecipe(Potion input, Item item, Potion output) {
	}
}