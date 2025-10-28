package com.faboslav.friendsandfoes.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
//? if >=1.21.5 {
import net.minecraft.world.item.component.TooltipDisplay;
import java.util.function.Consumer;
//?}

import java.util.List;

public class TotemItem extends Item
{
	public TotemItem(Properties properties) {
		super(properties);
	}

	//? if >=1.21.5 {
	
	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, tooltipContext, tooltipDisplay, consumer, tooltipFlag);
		consumer.accept(Component.translatable("friendsandfoes.totem_trigger_tooltip").withStyle(ChatFormatting.GRAY));
	}
	//?} else {
	/*@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
		tooltipComponents.add(Component.translatable("friendsandfoes.totem_trigger_tooltip").withStyle(ChatFormatting.GRAY));
	}*///?}
}
