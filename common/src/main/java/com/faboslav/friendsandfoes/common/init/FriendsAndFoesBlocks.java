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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

/**
 * @see Blocks
 */
public final class FriendsAndFoesBlocks
{
	public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, FriendsAndFoes.MOD_ID);

	public static final RegistryEntry<Block> BUTTERCUP = BLOCKS.register("buttercup", () -> new FlowerBlock(MobEffects.SATURATION, 6, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
	public static final RegistryEntry<Block> POTTED_BUTTERCUP = BLOCKS.register("potted_buttercup", () -> new FlowerPotBlock(BUTTERCUP.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));
	public static final RegistryEntry<Block> CRAB_EGG = BLOCKS.register("crab_egg", () -> new CrabEggBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).forceSolidOn().strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion().pushReaction(PushReaction.DESTROY)));
	public static final RegistryEntry<Block> ACACIA_BEEHIVE = BLOCKS.register("acacia_beehive", () -> new BeehiveBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(0.6F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()));
	public static final RegistryEntry<Block> BAMBOO_BEEHIVE = BLOCKS.register("bamboo_beehive", () -> new BeehiveBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.6F).sound(SoundType.BAMBOO_WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()));
	public static final RegistryEntry<Block> BIRCH_BEEHIVE = BLOCKS.register("birch_beehive", () -> new BeehiveBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.6F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()));
	public static final RegistryEntry<Block> CHERRY_BEEHIVE = BLOCKS.register("cherry_beehive", () -> new BeehiveBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(0.6F).sound(SoundType.CHERRY_WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()));
	public static final RegistryEntry<Block> CRIMSON_BEEHIVE = BLOCKS.register("crimson_beehive", () -> new BeehiveBlock(BlockBehaviour.Properties.of().mapColor(MapColor.CRIMSON_STEM).strength(0.6F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS)));
	public static final RegistryEntry<Block> DARK_OAK_BEEHIVE = BLOCKS.register("dark_oak_beehive", () -> new BeehiveBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.6F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()));
	public static final RegistryEntry<Block> JUNGLE_BEEHIVE = BLOCKS.register("jungle_beehive", () -> new BeehiveBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.6F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()));
	public static final RegistryEntry<Block> MANGROVE_BEEHIVE = BLOCKS.register("mangrove_beehive", () -> new BeehiveBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(0.6F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()));
	public static final RegistryEntry<Block> SPRUCE_BEEHIVE = BLOCKS.register("spruce_beehive", () -> new BeehiveBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PODZOL).strength(0.6F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()));
	public static final RegistryEntry<Block> WARPED_BEEHIVE = BLOCKS.register("warped_beehive", () -> new BeehiveBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WARPED_STEM).strength(0.6F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS)));
	public static final RegistryEntry<Block> COPPER_BUTTON = BLOCKS.register("copper_button", () -> new OxidizableButtonBlock(WeatheringCopper.WeatherState.UNAFFECTED, BlockBehaviour.Properties.of().noCollission().strength(0.5F).sound(SoundType.COPPER), 10));
	public static final RegistryEntry<Block> EXPOSED_COPPER_BUTTON = BLOCKS.register("exposed_copper_button", () -> new OxidizableButtonBlock(WeatheringCopper.WeatherState.EXPOSED, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).noCollission().strength(0.5F).sound(SoundType.COPPER), 7));
	public static final RegistryEntry<Block> WEATHERED_COPPER_BUTTON = BLOCKS.register("weathered_copper_button", () -> new OxidizableButtonBlock(WeatheringCopper.WeatherState.WEATHERED, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).noCollission().strength(0.5F).sound(SoundType.COPPER), 4));
	public static final RegistryEntry<Block> OXIDIZED_COPPER_BUTTON = BLOCKS.register("oxidized_copper_button", () -> new OxidizableButtonBlock(WeatheringCopper.WeatherState.OXIDIZED, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).noCollission().strength(0.5F).sound(SoundType.COPPER), 1));
	public static final RegistryEntry<Block> WAXED_COPPER_BUTTON = BLOCKS.register("waxed_copper_button", () -> new CopperButtonBlock(BlockBehaviour.Properties.ofFullCopy(COPPER_BUTTON.get()), 10));
	public static final RegistryEntry<Block> WAXED_EXPOSED_COPPER_BUTTON = BLOCKS.register("waxed_exposed_copper_button", () -> new CopperButtonBlock(BlockBehaviour.Properties.ofFullCopy(COPPER_BUTTON.get()), 7));
	public static final RegistryEntry<Block> WAXED_WEATHERED_COPPER_BUTTON = BLOCKS.register("waxed_weathered_copper_button", () -> new CopperButtonBlock(BlockBehaviour.Properties.ofFullCopy(COPPER_BUTTON.get()), 4));
	public static final RegistryEntry<Block> WAXED_OXIDIZED_COPPER_BUTTON = BLOCKS.register("waxed_oxidized_copper_button", () -> new CopperButtonBlock(BlockBehaviour.Properties.ofFullCopy(COPPER_BUTTON.get()), 1));
	public static final RegistryEntry<Block> EXPOSED_LIGHTNING_ROD = BLOCKS.register("exposed_lightning_rod", () -> new OxidizableLightningRodBlock(WeatheringCopper.WeatherState.EXPOSED, BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHTNING_ROD)));
	public static final RegistryEntry<Block> WEATHERED_LIGHTNING_ROD = BLOCKS.register("weathered_lightning_rod", () -> new OxidizableLightningRodBlock(WeatheringCopper.WeatherState.WEATHERED, BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHTNING_ROD)));
	public static final RegistryEntry<Block> OXIDIZED_LIGHTNING_ROD = BLOCKS.register("oxidized_lightning_rod", () -> new OxidizableLightningRodBlock(WeatheringCopper.WeatherState.OXIDIZED, BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHTNING_ROD)));
	public static final RegistryEntry<Block> WAXED_LIGHTNING_ROD = BLOCKS.register("waxed_lightning_rod", () -> new LightningRodBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHTNING_ROD)));
	public static final RegistryEntry<Block> WAXED_EXPOSED_LIGHTNING_ROD = BLOCKS.register("waxed_exposed_lightning_rod", () -> new LightningRodBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHTNING_ROD)));
	public static final RegistryEntry<Block> WAXED_WEATHERED_LIGHTNING_ROD = BLOCKS.register("waxed_weathered_lightning_rod", () -> new LightningRodBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHTNING_ROD)));
	public static final RegistryEntry<Block> WAXED_OXIDIZED_LIGHTNING_ROD = BLOCKS.register("waxed_oxidized_lightning_rod", () -> new LightningRodBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHTNING_ROD)));

	@Environment(EnvType.CLIENT)
	public static void registerRenderLayers(RegisterRenderLayersEvent event) {
		event.register(
			RenderType.cutout(),
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
