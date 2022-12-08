package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.block.CopperButtonBlock;
import com.faboslav.friendsandfoes.block.Oxidizable;
import com.faboslav.friendsandfoes.block.OxidizableButtonBlock;
import com.faboslav.friendsandfoes.block.OxidizableLightningRodBlock;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Supplier;

/**
 * @see Blocks
 */
public final class FriendsAndFoesBlocks
{
	public static final Supplier<Block> BUTTERCUP;
	public static final Supplier<Block> POTTED_BUTTERCUP;
	public static final Supplier<Block> ACACIA_BEEHIVE;
	public static final Supplier<Block> BIRCH_BEEHIVE;
	public static final Supplier<Block> CRIMSON_BEEHIVE;
	public static final Supplier<Block> DARK_OAK_BEEHIVE;
	public static final Supplier<Block> JUNGLE_BEEHIVE;
	public static final Supplier<Block> MANGROVE_BEEHIVE;
	public static final Supplier<Block> SPRUCE_BEEHIVE;
	public static final Supplier<Block> WARPED_BEEHIVE;
	public static final Supplier<Block> COPPER_BUTTON;
	public static final Supplier<Block> EXPOSED_COPPER_BUTTON;
	public static final Supplier<Block> WEATHERED_COPPER_BUTTON;
	public static final Supplier<Block> OXIDIZED_COPPER_BUTTON;
	public static final Supplier<Block> WAXED_COPPER_BUTTON;
	public static final Supplier<Block> WAXED_EXPOSED_COPPER_BUTTON;
	public static final Supplier<Block> WAXED_WEATHERED_COPPER_BUTTON;
	public static final Supplier<Block> WAXED_OXIDIZED_COPPER_BUTTON;
	public static final Supplier<Block> EXPOSED_LIGHTNING_ROD;
	public static final Supplier<Block> WEATHERED_LIGHTNING_ROD;
	public static final Supplier<Block> OXIDIZED_LIGHTNING_ROD;
	public static final Supplier<Block> WAXED_LIGHTNING_ROD;
	public static final Supplier<Block> WAXED_EXPOSED_LIGHTNING_ROD;
	public static final Supplier<Block> WAXED_WEATHERED_LIGHTNING_ROD;
	public static final Supplier<Block> WAXED_OXIDIZED_LIGHTNING_ROD;

	static {
		BUTTERCUP = RegistryHelper.registerBlock("buttercup", () -> new FlowerBlock(StatusEffects.SATURATION, 6, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)));
		POTTED_BUTTERCUP = RegistryHelper.registerBlock("potted_buttercup", () -> new FlowerPotBlock(BUTTERCUP.get(), AbstractBlock.Settings.of(Material.DECORATION).breakInstantly()));
		ACACIA_BEEHIVE = RegistryHelper.registerBlock("acacia_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(MapColor.ORANGE).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		BIRCH_BEEHIVE = RegistryHelper.registerBlock("birch_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(MapColor.PALE_YELLOW).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		CRIMSON_BEEHIVE = RegistryHelper.registerBlock("crimson_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.of(Material.NETHER_WOOD).mapColor(MapColor.DULL_PINK).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		DARK_OAK_BEEHIVE = RegistryHelper.registerBlock("dark_oak_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(MapColor.BROWN).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		JUNGLE_BEEHIVE = RegistryHelper.registerBlock("jungle_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(MapColor.DIRT_BROWN).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		MANGROVE_BEEHIVE = RegistryHelper.registerBlock("mangrove_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(MapColor.RED).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		SPRUCE_BEEHIVE = RegistryHelper.registerBlock("spruce_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(MapColor.SPRUCE_BROWN).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		WARPED_BEEHIVE = RegistryHelper.registerBlock("warped_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.of(Material.NETHER_WOOD).mapColor(MapColor.DARK_AQUA).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		COPPER_BUTTON = RegistryHelper.registerBlock("copper_button", () -> new OxidizableButtonBlock(Oxidizable.OxidationLevel.UNAFFECTED, AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER), 10));
		EXPOSED_COPPER_BUTTON = RegistryHelper.registerBlock("exposed_copper_button", () -> new OxidizableButtonBlock(Oxidizable.OxidationLevel.EXPOSED, AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER), 7));
		WEATHERED_COPPER_BUTTON = RegistryHelper.registerBlock("weathered_copper_button", () -> new OxidizableButtonBlock(Oxidizable.OxidationLevel.WEATHERED, AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER), 4));
		OXIDIZED_COPPER_BUTTON = RegistryHelper.registerBlock("oxidized_copper_button", () -> new OxidizableButtonBlock(Oxidizable.OxidationLevel.OXIDIZED, AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER), 1));
		WAXED_COPPER_BUTTON = RegistryHelper.registerBlock("waxed_copper_button", () -> new CopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON.get()), 10));
		WAXED_EXPOSED_COPPER_BUTTON = RegistryHelper.registerBlock("waxed_exposed_copper_button", () -> new CopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON.get()), 7));
		WAXED_WEATHERED_COPPER_BUTTON = RegistryHelper.registerBlock("waxed_weathered_copper_button", () -> new CopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON.get()), 4));
		WAXED_OXIDIZED_COPPER_BUTTON = RegistryHelper.registerBlock("waxed_oxidized_copper_button", () -> new CopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON.get()), 1));
		EXPOSED_LIGHTNING_ROD = RegistryHelper.registerBlock("exposed_lightning_rod", () -> new OxidizableLightningRodBlock(Oxidizable.OxidationLevel.EXPOSED, AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		WEATHERED_LIGHTNING_ROD = RegistryHelper.registerBlock("weathered_lightning_rod", () -> new OxidizableLightningRodBlock(Oxidizable.OxidationLevel.WEATHERED, AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		OXIDIZED_LIGHTNING_ROD = RegistryHelper.registerBlock("oxidized_lightning_rod", () -> new OxidizableLightningRodBlock(Oxidizable.OxidationLevel.OXIDIZED, AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		WAXED_LIGHTNING_ROD = RegistryHelper.registerBlock("waxed_lightning_rod", () -> new LightningRodBlock(AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		WAXED_EXPOSED_LIGHTNING_ROD = RegistryHelper.registerBlock("waxed_exposed_lightning_rod", () -> new LightningRodBlock(AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		WAXED_WEATHERED_LIGHTNING_ROD = RegistryHelper.registerBlock("waxed_weathered_lightning_rod", () -> new LightningRodBlock(AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		WAXED_OXIDIZED_LIGHTNING_ROD = RegistryHelper.registerBlock("waxed_oxidized_lightning_rod", () -> new LightningRodBlock(AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
	}

	public static void init() {
	}

	public static void postInit() {
		registerFlammableBlocks();
	}

	private static void registerFlammableBlocks() {
		int beehiveBurnChance = 5;
		int beehiveSpreadChance = 20;

		RegistryHelper.registerFlammableBlock(ACACIA_BEEHIVE, beehiveBurnChance, beehiveSpreadChance);
		RegistryHelper.registerFlammableBlock(BIRCH_BEEHIVE, beehiveBurnChance, beehiveSpreadChance);
		RegistryHelper.registerFlammableBlock(CRIMSON_BEEHIVE, beehiveBurnChance, beehiveSpreadChance);
		RegistryHelper.registerFlammableBlock(DARK_OAK_BEEHIVE, beehiveBurnChance, beehiveSpreadChance);
		RegistryHelper.registerFlammableBlock(JUNGLE_BEEHIVE, beehiveBurnChance, beehiveSpreadChance);
		RegistryHelper.registerFlammableBlock(MANGROVE_BEEHIVE, beehiveBurnChance, beehiveSpreadChance);
		RegistryHelper.registerFlammableBlock(SPRUCE_BEEHIVE, beehiveBurnChance, beehiveSpreadChance);
		RegistryHelper.registerFlammableBlock(WARPED_BEEHIVE, beehiveBurnChance, beehiveSpreadChance);
	}

	private FriendsAndFoesBlocks() {
	}
}
