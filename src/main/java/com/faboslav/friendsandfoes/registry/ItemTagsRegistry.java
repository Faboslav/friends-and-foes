package com.faboslav.friendsandfoes.registry;

import com.faboslav.friendsandfoes.config.Settings;
import com.faboslav.friendsandfoes.mixin.ItemTagsAccessor;
import net.minecraft.item.Item;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;

/**
 * @see ItemTags
 */
public class ItemTagsRegistry
{
	public static final TagKey<Item> COPPER_BUTTONS;

	static {
		COPPER_BUTTONS = register("copper_buttons");
	}

	private static TagKey<Item> register(String name) {
		return ItemTagsAccessor.invokeRegister(Settings.makeStringID(name));
	}

	public static void init() {
	}
}
