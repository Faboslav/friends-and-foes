package com.faboslav.friendsandfoes.registry;

import com.faboslav.friendsandfoes.block.CopperButtonBlock;
import com.faboslav.friendsandfoes.block.OxidizableButtonBlock;
import com.faboslav.friendsandfoes.config.Settings;
import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

import java.util.List;

/**
 * @see Blocks
 */
public class BlockRegistry
{
    public static final List<Block> BLOCKS = Lists.newArrayList();
    public static final Block BUTTERCUP;
    public static final Block POTTED_BUTTERCUP;
    public static final Block COPPER_BUTTON;
    public static final Block EXPOSED_COPPER_BUTTON;
    public static final Block OXIDIZED_COPPER_BUTTON;
    public static final Block WAXED_COPPER_BUTTON;
    public static final Block WAXED_EXPOSED_COPPER_BUTTON;
    public static final Block WAXED_OXIDIZED_COPPER_BUTTON;
    public static final Block WAXED_WEATHERED_COPPER_BUTTON;
    public static final Block WEATHERED_COPPER_BUTTON;

    public static Block register(
            String name,
            Block block
    ) {
        Registry.register(Registry.BLOCK, Settings.makeID(name), block);
        BLOCKS.add(block);

        return block;
    }

    static {
        BUTTERCUP = register("buttercup", new FlowerBlock(StatusEffects.LUCK, 6, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)));
        POTTED_BUTTERCUP = register("potted_buttercup", new FlowerPotBlock(BUTTERCUP, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly()));
        COPPER_BUTTON = register("copper_button", new OxidizableButtonBlock(Oxidizable.OxidationLevel.UNAFFECTED, FabricBlockSettings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER)));
        EXPOSED_COPPER_BUTTON = register("exposed_copper_button", new OxidizableButtonBlock(Oxidizable.OxidationLevel.EXPOSED, FabricBlockSettings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER)));
        OXIDIZED_COPPER_BUTTON = register("oxidized_copper_button", new OxidizableButtonBlock(Oxidizable.OxidationLevel.OXIDIZED, FabricBlockSettings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER)));
        WAXED_COPPER_BUTTON = register("waxed_copper_button", new CopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON)));
        WAXED_EXPOSED_COPPER_BUTTON = register("waxed_exposed_copper_button", new CopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON)));
        WAXED_OXIDIZED_COPPER_BUTTON = register("waxed_oxidized_copper_button", new CopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON)));
        WAXED_WEATHERED_COPPER_BUTTON = register("waxed_weathered_copper_button", new CopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON)));
        WEATHERED_COPPER_BUTTON = register("weathered_copper_button", new OxidizableButtonBlock(Oxidizable.OxidationLevel.WEATHERED, FabricBlockSettings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER)));

        for (Block block : Registry.BLOCK) {
            for (BlockState blockState : block.getStateManager().getStates()) {
                Block.STATE_IDS.add(blockState);
            }
        }
    }

    public static void init() {}
}
