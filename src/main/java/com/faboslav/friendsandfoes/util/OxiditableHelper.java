package com.faboslav.friendsandfoes.util;

import com.faboslav.friendsandfoes.registry.BlockRegistry;
import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;

public final class OxiditableHelper
{
    public static final Map<Block, Block> OXIDATION_LEVEL_INCREASES = new HashMap<>();
    public static final Map<Block, Block> OXIDATION_LEVEL_DECREASES = new HashMap<>();

    static {
        OXIDATION_LEVEL_INCREASES.put(
                BlockRegistry.COPPER_BUTTON,
                BlockRegistry.EXPOSED_COPPER_BUTTON
        );
        OXIDATION_LEVEL_INCREASES.put(
                BlockRegistry.EXPOSED_COPPER_BUTTON,
                BlockRegistry.WEATHERED_COPPER_BUTTON
        );
        OXIDATION_LEVEL_INCREASES.put(
                BlockRegistry.WEATHERED_COPPER_BUTTON,
                BlockRegistry.OXIDIZED_COPPER_BUTTON
        );

        OXIDATION_LEVEL_DECREASES.put(
                BlockRegistry.OXIDIZED_COPPER_BUTTON,
                BlockRegistry.WEATHERED_COPPER_BUTTON
        );
        OXIDATION_LEVEL_DECREASES.put(
                BlockRegistry.WEATHERED_COPPER_BUTTON,
                BlockRegistry.EXPOSED_COPPER_BUTTON
        );
        OXIDATION_LEVEL_DECREASES.put(
                BlockRegistry.EXPOSED_COPPER_BUTTON,
                BlockRegistry.COPPER_BUTTON
        );
    }
}
