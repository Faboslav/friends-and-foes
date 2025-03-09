package com.faboslav.friendsandfoes.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class TotemItem extends Item
{

	public TotemItem(Settings settings) {
		super(settings);
	}

	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("friendsandfoes.totem_trigger_tooltip").formatted(Formatting.GRAY));
	}
}