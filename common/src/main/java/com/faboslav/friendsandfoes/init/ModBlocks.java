package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.block.*;
import com.faboslav.friendsandfoes.util.OxidizableBlockHelper;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

/**
 * @see Blocks
 */
public class ModBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(FriendsAndFoes.MOD_ID, Registry.BLOCK_KEY);

	public static Block BUTTERCUP;
	public static Block ACACIA_BEEHIVE;
	public static final Block BIRCH_BEEHIVE;
	public static final Block CRIMSON_BEEHIVE;
	public static final Block DARK_OAK_BEEHIVE;
	public static final Block JUNGLE_BEEHIVE;
	public static final Block SPRUCE_BEEHIVE;
	public static final Block WARPED_BEEHIVE;
	public static final Block POTTED_BUTTERCUP;
	public static final Block COPPER_BUTTON;
	public static final Block EXPOSED_COPPER_BUTTON;
	public static final Block WEATHERED_COPPER_BUTTON;
	public static final Block OXIDIZED_COPPER_BUTTON;
	public static final Block WAXED_COPPER_BUTTON;
	public static final Block WAXED_EXPOSED_COPPER_BUTTON;
	public static final Block WAXED_WEATHERED_COPPER_BUTTON;
	public static final Block WAXED_OXIDIZED_COPPER_BUTTON;

	static {
		BUTTERCUP = register("buttercup", new FlowerBlock(StatusEffects.SATURATION, 6, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)));
		POTTED_BUTTERCUP = register("potted_buttercup", new FlowerPotBlock(BUTTERCUP, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly()));
		ACACIA_BEEHIVE = register("acacia_beehive", new BeehiveBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(MapColor.ORANGE).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		BIRCH_BEEHIVE = register("birch_beehive", new BeehiveBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(MapColor.PALE_YELLOW).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		CRIMSON_BEEHIVE = register("crimson_beehive", new BeehiveBlock(AbstractBlock.Settings.of(Material.NETHER_WOOD).mapColor(MapColor.DULL_PINK).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		DARK_OAK_BEEHIVE = register("dark_oak_beehive", new BeehiveBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(MapColor.BROWN).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		JUNGLE_BEEHIVE = register("jungle_beehive", new BeehiveBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(MapColor.DIRT_BROWN).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		SPRUCE_BEEHIVE = register("spruce_beehive", new BeehiveBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(MapColor.SPRUCE_BROWN).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		WARPED_BEEHIVE = register("warped_beehive", new BeehiveBlock(AbstractBlock.Settings.of(Material.NETHER_WOOD).mapColor(MapColor.DARK_AQUA).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		COPPER_BUTTON = register("copper_button", new OxidizableButtonBlock(Oxidizable.OxidationLevel.UNAFFECTED, AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER)));
		EXPOSED_COPPER_BUTTON = register("exposed_copper_button", new OxidizableButtonBlock(Oxidizable.OxidationLevel.EXPOSED, AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER)));
		WEATHERED_COPPER_BUTTON = register("weathered_copper_button", new OxidizableButtonBlock(Oxidizable.OxidationLevel.WEATHERED, AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER)));
		OXIDIZED_COPPER_BUTTON = register("oxidized_copper_button", new OxidizableButtonBlock(Oxidizable.OxidationLevel.OXIDIZED, AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER)));
		WAXED_COPPER_BUTTON = register("waxed_copper_button", new CopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON)));
		WAXED_EXPOSED_COPPER_BUTTON = register("waxed_exposed_copper_button", new ExposedCopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON)));
		WAXED_WEATHERED_COPPER_BUTTON = register("waxed_weathered_copper_button", new WeatheredCopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON)));
		WAXED_OXIDIZED_COPPER_BUTTON = register("waxed_oxidized_copper_button", new OxidizedCopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON)));
	}

	public static Block register(
		String name,
		Block block
	) {
		BLOCKS.register(name, () -> block);

		for (BlockState blockState : block.getStateManager().getStates()) {
			Block.STATE_IDS.add(blockState);
		}

		return block;
	}

	public static void init() {
		initOxidizableBlocks();
		BLOCKS.register();
	}

	public static void initOxidizableBlocks() {
		OxidizableBlockHelper.registerOxidizableBlockPair(ModBlocks.COPPER_BUTTON, ModBlocks.EXPOSED_COPPER_BUTTON);
		OxidizableBlockHelper.registerOxidizableBlockPair(ModBlocks.EXPOSED_COPPER_BUTTON, ModBlocks.WEATHERED_COPPER_BUTTON);
		OxidizableBlockHelper.registerOxidizableBlockPair(ModBlocks.WEATHERED_COPPER_BUTTON, ModBlocks.OXIDIZED_COPPER_BUTTON);
		OxidizableBlockHelper.registerWaxableBlockPair(ModBlocks.COPPER_BUTTON, ModBlocks.WAXED_COPPER_BUTTON);
		OxidizableBlockHelper.registerWaxableBlockPair(ModBlocks.EXPOSED_COPPER_BUTTON, ModBlocks.WAXED_EXPOSED_COPPER_BUTTON);
		OxidizableBlockHelper.registerWaxableBlockPair(ModBlocks.WEATHERED_COPPER_BUTTON, ModBlocks.WAXED_WEATHERED_COPPER_BUTTON);
		OxidizableBlockHelper.registerWaxableBlockPair(ModBlocks.OXIDIZED_COPPER_BUTTON, ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON);
	}
}
