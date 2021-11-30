package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.registry.BlockRegistry;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.item.HoneycombItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Supplier;

@Mixin(HoneycombItem.class)
public abstract class HoneycombItemMixin
{
    @Shadow
    @Final
    public static final Supplier<BiMap<Block, Block>> UNWAXED_TO_WAXED_BLOCKS = addUnwaxedToWaxedBlocks();

    private static Supplier<BiMap<Block, Block>> addUnwaxedToWaxedBlocks() {
        var currentIncreases = HoneycombItem.UNWAXED_TO_WAXED_BLOCKS.get();
        var newMap = ImmutableBiMap.<Block, Block>builder()
                .putAll(currentIncreases)
                .put(BlockRegistry.COPPER_BUTTON, BlockRegistry.WAXED_COPPER_BUTTON)
                .put(BlockRegistry.EXPOSED_COPPER_BUTTON, BlockRegistry.WAXED_EXPOSED_COPPER_BUTTON)
                .put(BlockRegistry.OXIDIZED_COPPER_BUTTON, BlockRegistry.WAXED_OXIDIZED_COPPER_BUTTON)
                .put(BlockRegistry.WEATHERED_COPPER_BUTTON, BlockRegistry.WAXED_WEATHERED_COPPER_BUTTON)
                .build();
        return Suppliers.memoize(() -> newMap);
    }
}