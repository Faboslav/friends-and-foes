package com.faboslav.friendsandfoes.registry;

import com.faboslav.friendsandfoes.config.Settings;
import com.faboslav.friendsandfoes.mixin.BlockTagsAccessor;
import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;

/**
 * @see BlockTags
 */
public class BlockTagRegistry
{
	public static final TagKey<Block> COPPER_BUTTONS;

	static {
		COPPER_BUTTONS = register("copper_buttons");
	}

	private static TagKey<Block> register(String name) {
		return BlockTagsAccessor.invokeRegister(Settings.makeStringID(name));
	}

	public static void init() {
	}
}
