package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.block.CopperButtonBlock;
import com.faboslav.friendsandfoes.common.block.CrabEggBlock;
import com.faboslav.friendsandfoes.common.block.OxidizableButtonBlock;
import com.faboslav.friendsandfoes.common.block.OxidizableLightningRodBlock;
import com.faboslav.friendsandfoes.common.events.client.RegisterRenderLayersEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.RegisterFlammabilityEvent;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.sound.BlockSoundGroup;

/**
 * @see Blocks
 */
public final class FriendsAndFoesBlocks
{
	public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(Registries.BLOCK, FriendsAndFoes.MOD_ID);

	public static final RegistryEntry<Block> BUTTERCUP = BLOCKS.register("buttercup", () -> new FlowerBlock(StatusEffects.SATURATION, 6, AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY)));
	public static final RegistryEntry<Block> POTTED_BUTTERCUP = BLOCKS.register("potted_buttercup", () -> new FlowerPotBlock(BUTTERCUP.get(), AbstractBlock.Settings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
	public static final RegistryEntry<Block> CRAB_EGG = BLOCKS.register("crab_egg", () -> new CrabEggBlock(AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).solid().strength(0.5F).sounds(BlockSoundGroup.METAL).ticksRandomly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
	public static final RegistryEntry<Block> ACACIA_BEEHIVE = BLOCKS.register("acacia_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).strength(0.6F).sounds(BlockSoundGroup.WOOD).instrument(NoteBlockInstrument.BASS).burnable()));
	public static final RegistryEntry<Block> BAMBOO_BEEHIVE = BLOCKS.register("bamboo_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).strength(0.6F).sounds(BlockSoundGroup.BAMBOO_WOOD).instrument(NoteBlockInstrument.BASS).burnable()));
	public static final RegistryEntry<Block> BIRCH_BEEHIVE = BLOCKS.register("birch_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(0.6F).sounds(BlockSoundGroup.WOOD).instrument(NoteBlockInstrument.BASS).burnable()));
	public static final RegistryEntry<Block> CHERRY_BEEHIVE = BLOCKS.register("cherry_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_WHITE).strength(0.6F).sounds(BlockSoundGroup.CHERRY_WOOD).instrument(NoteBlockInstrument.BASS).burnable()));
	public static final RegistryEntry<Block> CRIMSON_BEEHIVE = BLOCKS.register("crimson_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.DULL_PINK).strength(0.6F).sounds(BlockSoundGroup.WOOD).instrument(NoteBlockInstrument.BASS)));
	public static final RegistryEntry<Block> DARK_OAK_BEEHIVE = BLOCKS.register("dark_oak_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.BROWN).strength(0.6F).sounds(BlockSoundGroup.WOOD).instrument(NoteBlockInstrument.BASS).burnable()));
	public static final RegistryEntry<Block> JUNGLE_BEEHIVE = BLOCKS.register("jungle_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).strength(0.6F).sounds(BlockSoundGroup.WOOD).instrument(NoteBlockInstrument.BASS).burnable()));
	public static final RegistryEntry<Block> MANGROVE_BEEHIVE = BLOCKS.register("mangrove_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.RED).strength(0.6F).sounds(BlockSoundGroup.WOOD).instrument(NoteBlockInstrument.BASS).burnable()));
	public static final RegistryEntry<Block> SPRUCE_BEEHIVE = BLOCKS.register("spruce_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.SPRUCE_BROWN).strength(0.6F).sounds(BlockSoundGroup.WOOD).instrument(NoteBlockInstrument.BASS).burnable()));
	public static final RegistryEntry<Block> WARPED_BEEHIVE = BLOCKS.register("warped_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_AQUA).strength(0.6F).sounds(BlockSoundGroup.WOOD).instrument(NoteBlockInstrument.BASS)));
	public static final RegistryEntry<Block> COPPER_BUTTON = BLOCKS.register("copper_button", () -> new OxidizableButtonBlock(Oxidizable.OxidationLevel.UNAFFECTED, AbstractBlock.Settings.create().noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER), 10));
	public static final RegistryEntry<Block> EXPOSED_COPPER_BUTTON = BLOCKS.register("exposed_copper_button", () -> new OxidizableButtonBlock(Oxidizable.OxidationLevel.EXPOSED, AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER), 7));
	public static final RegistryEntry<Block> WEATHERED_COPPER_BUTTON = BLOCKS.register("weathered_copper_button", () -> new OxidizableButtonBlock(Oxidizable.OxidationLevel.WEATHERED, AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER), 4));
	public static final RegistryEntry<Block> OXIDIZED_COPPER_BUTTON = BLOCKS.register("oxidized_copper_button", () -> new OxidizableButtonBlock(Oxidizable.OxidationLevel.OXIDIZED, AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER), 1));
	public static final RegistryEntry<Block> WAXED_COPPER_BUTTON = BLOCKS.register("waxed_copper_button", () -> new CopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON.get()), 10));
	public static final RegistryEntry<Block> WAXED_EXPOSED_COPPER_BUTTON = BLOCKS.register("waxed_exposed_copper_button", () -> new CopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON.get()), 7));
	public static final RegistryEntry<Block> WAXED_WEATHERED_COPPER_BUTTON = BLOCKS.register("waxed_weathered_copper_button", () -> new CopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON.get()), 4));
	public static final RegistryEntry<Block> WAXED_OXIDIZED_COPPER_BUTTON = BLOCKS.register("waxed_oxidized_copper_button", () -> new CopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON.get()), 1));
	public static final RegistryEntry<Block> EXPOSED_LIGHTNING_ROD = BLOCKS.register("exposed_lightning_rod", () -> new OxidizableLightningRodBlock(Oxidizable.OxidationLevel.EXPOSED, AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
	public static final RegistryEntry<Block> WEATHERED_LIGHTNING_ROD = BLOCKS.register("weathered_lightning_rod", () -> new OxidizableLightningRodBlock(Oxidizable.OxidationLevel.WEATHERED, AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
	public static final RegistryEntry<Block> OXIDIZED_LIGHTNING_ROD = BLOCKS.register("oxidized_lightning_rod", () -> new OxidizableLightningRodBlock(Oxidizable.OxidationLevel.OXIDIZED, AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
	public static final RegistryEntry<Block> WAXED_LIGHTNING_ROD = BLOCKS.register("waxed_lightning_rod", () -> new LightningRodBlock(AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
	public static final RegistryEntry<Block> WAXED_EXPOSED_LIGHTNING_ROD = BLOCKS.register("waxed_exposed_lightning_rod", () -> new LightningRodBlock(AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
	public static final RegistryEntry<Block> WAXED_WEATHERED_LIGHTNING_ROD = BLOCKS.register("waxed_weathered_lightning_rod", () -> new LightningRodBlock(AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
	public static final RegistryEntry<Block> WAXED_OXIDIZED_LIGHTNING_ROD = BLOCKS.register("waxed_oxidized_lightning_rod", () -> new LightningRodBlock(AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));

	@Environment(EnvType.CLIENT)
	public static void registerRenderLayers(RegisterRenderLayersEvent event) {
		event.register(
			RenderLayer.getCutout(),
			FriendsAndFoesBlocks.BUTTERCUP.get(),
			FriendsAndFoesBlocks.POTTED_BUTTERCUP.get()
		);
	}

	public static void registerFlammablity(RegisterFlammabilityEvent event) {
		FriendsAndFoesBlocks.BLOCKS.stream()
			.map(RegistryEntry::get)
			.filter(block -> block instanceof BeehiveBlock && (block != WARPED_BEEHIVE && block != CRIMSON_BEEHIVE)) // TODO check this
			.map(block -> (BeehiveBlock) block)
			.forEach(block -> event.register(block, 20, 5));
	}

	private FriendsAndFoesBlocks() {
	}
}
