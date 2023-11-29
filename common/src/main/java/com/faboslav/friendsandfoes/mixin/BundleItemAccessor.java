package com.faboslav.friendsandfoes.mixin;

import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BundleItem.class)
public interface BundleItemAccessor
{
	@Invoker
	static int callAddToBundle(ItemStack bundle, ItemStack stack) {
		throw new UnsupportedOperationException();
	}
}
