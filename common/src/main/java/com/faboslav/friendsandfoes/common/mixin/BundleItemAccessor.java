package com.faboslav.friendsandfoes.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.item.BundleItem;

//? if <1.20.5 {
/*import org.spongepowered.asm.mixin.gen.Invoker;
import net.minecraft.world.item.ItemStack;
*///?}

@Mixin(BundleItem.class)
public interface BundleItemAccessor
{
	//? if <1.20.5 {
	/*@Invoker("add")
	static int friendsandfoes$addToBundle(ItemStack bundleStack, ItemStack insertedStack) {
		throw new AssertionError();
	}
	*///?}
}
