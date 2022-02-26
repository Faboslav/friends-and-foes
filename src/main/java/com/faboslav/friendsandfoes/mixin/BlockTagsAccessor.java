package com.faboslav.friendsandfoes.mixin;

import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockTags.class)
public interface BlockTagsAccessor
{
	@Invoker
	static TagKey<Block> invokeRegister(String id) {
		throw new UnsupportedOperationException();
	}
}
