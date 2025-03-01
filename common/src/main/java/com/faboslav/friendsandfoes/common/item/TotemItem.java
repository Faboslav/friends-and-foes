package com.faboslav.friendsandfoes.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class TotemItem extends Item
{
	public TotemItem(Properties properties) {
		super(properties);
	}

	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
		tooltipComponents.add(Component.translatable("friendsandfoes.totem_trigger_tooltip").withStyle(ChatFormatting.GRAY));
	}
}
