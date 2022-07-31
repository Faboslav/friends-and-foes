package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.block.Oxidizable;
import com.faboslav.friendsandfoes.block.*;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

/**
 * @see Blocks
 */
public final class ModBlocks
{
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(FriendsAndFoes.MOD_ID, Registry.BLOCK_KEY);

	public static final RegistrySupplier<Block> BUTTERCUP;
	public static final RegistrySupplier<Block> ACACIA_BEEHIVE;
	public static final RegistrySupplier<Block> BIRCH_BEEHIVE;
	public static final RegistrySupplier<Block> CRIMSON_BEEHIVE;
	public static final RegistrySupplier<Block> DARK_OAK_BEEHIVE;
	public static final RegistrySupplier<Block> JUNGLE_BEEHIVE;
	public static final RegistrySupplier<Block> MANGROVE_BEEHIVE;
	public static final RegistrySupplier<Block> SPRUCE_BEEHIVE;
	public static final RegistrySupplier<Block> WARPED_BEEHIVE;
	public static final RegistrySupplier<Block> POTTED_BUTTERCUP;
	public static final RegistrySupplier<Block> COPPER_BUTTON;
	public static final RegistrySupplier<Block> EXPOSED_COPPER_BUTTON;
	public static final RegistrySupplier<Block> WEATHERED_COPPER_BUTTON;
	public static final RegistrySupplier<Block> OXIDIZED_COPPER_BUTTON;
	public static final RegistrySupplier<Block> WAXED_COPPER_BUTTON;
	public static final RegistrySupplier<Block> WAXED_EXPOSED_COPPER_BUTTON;
	public static final RegistrySupplier<Block> WAXED_WEATHERED_COPPER_BUTTON;
	public static final RegistrySupplier<Block> WAXED_OXIDIZED_COPPER_BUTTON;
	public static final RegistrySupplier<Block> EXPOSED_LIGHTNING_ROD;
	public static final RegistrySupplier<Block> WEATHERED_LIGHTNING_ROD;
	public static final RegistrySupplier<Block> OXIDIZED_LIGHTNING_ROD;
	public static final RegistrySupplier<Block> WAXED_LIGHTNING_ROD;
	public static final RegistrySupplier<Block> WAXED_EXPOSED_LIGHTNING_ROD;
	public static final RegistrySupplier<Block> WAXED_WEATHERED_LIGHTNING_ROD;
	public static final RegistrySupplier<Block> WAXED_OXIDIZED_LIGHTNING_ROD;

	static {
		BUTTERCUP = BLOCKS.register("buttercup", () -> new FlowerBlock(StatusEffects.SATURATION, 6, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)));
		POTTED_BUTTERCUP = BLOCKS.register("potted_buttercup", () -> new FlowerPotBlock(BUTTERCUP.get(), AbstractBlock.Settings.of(Material.DECORATION).breakInstantly()));
		ACACIA_BEEHIVE = BLOCKS.register("acacia_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(MapColor.ORANGE).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		BIRCH_BEEHIVE = BLOCKS.register("birch_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(MapColor.PALE_YELLOW).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		CRIMSON_BEEHIVE = BLOCKS.register("crimson_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.of(Material.NETHER_WOOD).mapColor(MapColor.DULL_PINK).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		DARK_OAK_BEEHIVE = BLOCKS.register("dark_oak_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(MapColor.BROWN).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		JUNGLE_BEEHIVE = BLOCKS.register("jungle_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(MapColor.DIRT_BROWN).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		MANGROVE_BEEHIVE = BLOCKS.register("mangrove_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(MapColor.RED).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		SPRUCE_BEEHIVE = BLOCKS.register("spruce_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(MapColor.SPRUCE_BROWN).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		WARPED_BEEHIVE = BLOCKS.register("warped_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.of(Material.NETHER_WOOD).mapColor(MapColor.DARK_AQUA).strength(0.6F).sounds(BlockSoundGroup.WOOD)));
		COPPER_BUTTON = BLOCKS.register("copper_button", () -> new OxidizableButtonBlock(Oxidizable.OxidationLevel.UNAFFECTED, AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER)));
		EXPOSED_COPPER_BUTTON = BLOCKS.register("exposed_copper_button", () -> new OxidizableButtonBlock(Oxidizable.OxidationLevel.EXPOSED, AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER)));
		WEATHERED_COPPER_BUTTON = BLOCKS.register("weathered_copper_button", () -> new OxidizableButtonBlock(Oxidizable.OxidationLevel.WEATHERED, AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER)));
		OXIDIZED_COPPER_BUTTON = BLOCKS.register("oxidized_copper_button", () -> new OxidizableButtonBlock(Oxidizable.OxidationLevel.OXIDIZED, AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER)));
		WAXED_COPPER_BUTTON = BLOCKS.register("waxed_copper_button", () -> new CopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON.get())));
		WAXED_EXPOSED_COPPER_BUTTON = BLOCKS.register("waxed_exposed_copper_button", () -> new ExposedCopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON.get())));
		WAXED_WEATHERED_COPPER_BUTTON = BLOCKS.register("waxed_weathered_copper_button", () -> new WeatheredCopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON.get())));
		WAXED_OXIDIZED_COPPER_BUTTON = BLOCKS.register("waxed_oxidized_copper_button", () -> new OxidizedCopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON.get())));
		EXPOSED_LIGHTNING_ROD = BLOCKS.register("exposed_lightning_rod", () -> new OxidizableLightningRodBlock(Oxidizable.OxidationLevel.EXPOSED, AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		WEATHERED_LIGHTNING_ROD = BLOCKS.register("weathered_lightning_rod", () -> new OxidizableLightningRodBlock(Oxidizable.OxidationLevel.WEATHERED, AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		OXIDIZED_LIGHTNING_ROD = BLOCKS.register("oxidized_lightning_rod", () -> new OxidizableLightningRodBlock(Oxidizable.OxidationLevel.OXIDIZED, AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		WAXED_LIGHTNING_ROD = BLOCKS.register("waxed_lightning_rod", () -> new LightningRodBlock(AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		WAXED_EXPOSED_LIGHTNING_ROD = BLOCKS.register("waxed_exposed_lightning_rod", () -> new LightningRodBlock(AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		WAXED_WEATHERED_LIGHTNING_ROD = BLOCKS.register("waxed_weathered_lightning_rod", () -> new LightningRodBlock(AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		WAXED_OXIDIZED_LIGHTNING_ROD = BLOCKS.register("waxed_oxidized_lightning_rod", () -> new LightningRodBlock(AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
	}

	public static void initRegister() {
		BLOCKS.register();
	}

	public static void init() {
	}

	private ModBlocks() {
	}
}
