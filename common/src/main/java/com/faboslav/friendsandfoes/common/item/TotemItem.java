package com.faboslav.friendsandfoes.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.List;
import java.util.function.Consumer;

public class TotemItem extends Item
{
	public TotemItem(Properties properties) {
		super(properties);
	}

	//? >=1.21.5 {
	public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, tooltipContext, tooltipDisplay, consumer, tooltipFlag);
		consumer.accept(Component.translatable("friendsandfoes.totem_trigger_tooltip").withStyle(ChatFormatting.GRAY));
	}
	//?} else {
	/*@Override
	/^public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
		tooltipComponents.add(Component.translatable("friendsandfoes.totem_trigger_tooltip").withStyle(ChatFormatting.GRAY));
	}^/*///?}
}
