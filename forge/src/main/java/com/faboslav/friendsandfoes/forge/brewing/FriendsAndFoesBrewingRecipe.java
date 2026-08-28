package com.faboslav.friendsandfoes.forge.brewing;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public final class FriendsAndFoesBrewingRecipe implements IBrewingRecipe
{
	private final Potion input;
	private final Item ingredient;
	private final Potion output;

	public FriendsAndFoesBrewingRecipe(Potion input, Item ingredient, Potion output) {
		this.input = input;
		this.ingredient = ingredient;
		this.output = output;
	}

	@Override
	public boolean isInput(ItemStack stack) {
		return stack.getItem() instanceof PotionItem && PotionUtils.getPotion(stack) == this.input;
	}

	@Override
	public boolean isIngredient(ItemStack stack) {
		return stack.getItem() == this.ingredient;
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		if (!this.isInput(input) || !this.isIngredient(ingredient)) {
			return ItemStack.EMPTY;
		}

		return PotionUtils.setPotion(new ItemStack(input.getItem()), this.output);
	}
}
