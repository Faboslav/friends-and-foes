package com.faboslav.friendsandfoes.registry;

import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;

public class OxidizableBlockRegistry
{
    public static void init() {
        OxidizableBlocksRegistry.registerOxidizableBlockPair(
                BlockRegistry.COPPER_BUTTON,
                BlockRegistry.EXPOSED_COPPER_BUTTON
        );
        OxidizableBlocksRegistry.registerOxidizableBlockPair(
                BlockRegistry.EXPOSED_COPPER_BUTTON,
                BlockRegistry.WEATHERED_COPPER_BUTTON
        );
        OxidizableBlocksRegistry.registerOxidizableBlockPair(
                BlockRegistry.WEATHERED_COPPER_BUTTON,
                BlockRegistry.OXIDIZED_COPPER_BUTTON
        );

        OxidizableBlocksRegistry.registerWaxableBlockPair(
                BlockRegistry.COPPER_BUTTON,
                BlockRegistry.WAXED_COPPER_BUTTON
        );
        OxidizableBlocksRegistry.registerWaxableBlockPair(
                BlockRegistry.EXPOSED_COPPER_BUTTON,
                BlockRegistry.WAXED_EXPOSED_COPPER_BUTTON
        );
        OxidizableBlocksRegistry.registerWaxableBlockPair(
                BlockRegistry.WEATHERED_COPPER_BUTTON,
                BlockRegistry.WAXED_WEATHERED_COPPER_BUTTON
        );
        OxidizableBlocksRegistry.registerWaxableBlockPair(
                BlockRegistry.OXIDIZED_COPPER_BUTTON,
                BlockRegistry.WAXED_OXIDIZED_COPPER_BUTTON
        );
    }
}
