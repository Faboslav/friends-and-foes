package com.faboslav.friendsandfoes.registry;

import com.faboslav.friendsandfoes.config.Settings;
import com.faboslav.friendsandfoes.mixin.BlockTagsAccessor;
import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;

/**
 * @see BlockTags
 */
public class BlockTagRegistry
{
    public static final Tag.Identified<Block> COPPER_BUTTONS;

    private static Tag.Identified<Block> register(String name) {
        return BlockTagsAccessor.invokeRegister(Settings.makeStringID(name));
    }

    static {
        COPPER_BUTTONS = register("copper_buttons");
    }

    public static void init() {}
}
