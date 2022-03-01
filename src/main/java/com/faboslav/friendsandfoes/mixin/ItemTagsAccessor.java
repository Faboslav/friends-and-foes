package com.faboslav.friendsandfoes.mixin;

import net.minecraft.item.Item;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemTags.class)
public interface ItemTagsAccessor
{
	@Invoker
	static TagKey<Item> invokeRegister(String id) {
		throw new UnsupportedOperationException();
	}
}
