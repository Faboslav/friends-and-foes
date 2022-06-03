package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

/**
 * @see Items
 */
public final class ModItems
{
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(FriendsAndFoes.MOD_ID, Registry.ITEM_KEY);

	public final static RegistrySupplier<Item> COPPER_GOLEM_SPAWN_EGG;
	public final static RegistrySupplier<Item> GLARE_SPAWN_EGG;
	public final static RegistrySupplier<Item> ICEOLOGER_SPAWN_EGG;
	public final static RegistrySupplier<Item> ILLUSIONER_SPAWN_EGG;
	public final static RegistrySupplier<Item> MAULER_SPAWN_EGG;
	public final static RegistrySupplier<Item> MOOBLOOM_SPAWN_EGG;
	public static final RegistrySupplier<Item> BUTTERCUP;
	public static final RegistrySupplier<Item> ACACIA_BEEHIVE;
	public static final RegistrySupplier<Item> BIRCH_BEEHIVE;
	public static final RegistrySupplier<Item> CRIMSON_BEEHIVE;
	public static final RegistrySupplier<Item> DARK_OAK_BEEHIVE;
	public static final RegistrySupplier<Item> JUNGLE_BEEHIVE;
	public static final RegistrySupplier<Item> MANGROVE_BEEHIVE;
	public static final RegistrySupplier<Item> SPRUCE_BEEHIVE;
	public static final RegistrySupplier<Item> WARPED_BEEHIVE;
	public static final RegistrySupplier<Item> COPPER_BUTTON;
	public static final RegistrySupplier<Item> EXPOSED_COPPER_BUTTON;
	public static final RegistrySupplier<Item> WEATHERED_COPPER_BUTTON;
	public static final RegistrySupplier<Item> OXIDIZED_COPPER_BUTTON;
	public static final RegistrySupplier<Item> WAXED_COPPER_BUTTON;
	public static final RegistrySupplier<Item> WAXED_EXPOSED_COPPER_BUTTON;
	public static final RegistrySupplier<Item> WAXED_WEATHERED_COPPER_BUTTON;
	public static final RegistrySupplier<Item> WAXED_OXIDIZED_COPPER_BUTTON;

	static {
		COPPER_GOLEM_SPAWN_EGG = ITEMS.register("copper_golem_spawn_egg", () -> new ArchitecturySpawnEggItem(ModEntityTypes.COPPER_GOLEM, 0x9A5038, 0xE3826C, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		GLARE_SPAWN_EGG = ITEMS.register("glare_spawn_egg", () -> new ArchitecturySpawnEggItem(ModEntityTypes.GLARE, 0x70922D, 0x6A5227, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		ICEOLOGER_SPAWN_EGG = ITEMS.register("iceologer_spawn_egg", () -> new ArchitecturySpawnEggItem(ModEntityTypes.ICEOLOGER, 0x173873, 0x949b9b, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		ILLUSIONER_SPAWN_EGG = ITEMS.register("illusioner_spawn_egg", () -> new net.minecraft.item.SpawnEggItem(EntityType.ILLUSIONER, 0x603e5c, 0x888e8e, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		MAULER_SPAWN_EGG = ITEMS.register("mauler_spawn_egg", () -> new ArchitecturySpawnEggItem(ModEntityTypes.MAULER, 0x534F25, 0x817B39, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		MOOBLOOM_SPAWN_EGG = ITEMS.register("moobloom_spawn_egg", () -> new ArchitecturySpawnEggItem(ModEntityTypes.MOOBLOOM, 0xf7EDC1, 0xFACA00, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		BUTTERCUP = ITEMS.register("buttercup", () -> new BlockItem(ModBlocks.BUTTERCUP.get(), new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		ACACIA_BEEHIVE = ITEMS.register("acacia_beehive", () -> new BlockItem(ModBlocks.ACACIA_BEEHIVE.get(), new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		BIRCH_BEEHIVE = ITEMS.register("birch_beehive", () -> new BlockItem(ModBlocks.BIRCH_BEEHIVE.get(), new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		CRIMSON_BEEHIVE = ITEMS.register("crimson_beehive", () -> new BlockItem(ModBlocks.CRIMSON_BEEHIVE.get(), new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		DARK_OAK_BEEHIVE = ITEMS.register("dark_oak_beehive", () -> new BlockItem(ModBlocks.DARK_OAK_BEEHIVE.get(), new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		JUNGLE_BEEHIVE = ITEMS.register("jungle_beehive", () -> new BlockItem(ModBlocks.JUNGLE_BEEHIVE.get(), new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		MANGROVE_BEEHIVE = ITEMS.register("mangrove_beehive", () -> new BlockItem(ModBlocks.MANGROVE_BEEHIVE.get(), new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		SPRUCE_BEEHIVE = ITEMS.register("spruce_beehive", () -> new BlockItem(ModBlocks.SPRUCE_BEEHIVE.get(), new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		WARPED_BEEHIVE = ITEMS.register("warped_beehive", () -> new BlockItem(ModBlocks.WARPED_BEEHIVE.get(), new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		COPPER_BUTTON = ITEMS.register("copper_button", () -> new BlockItem(ModBlocks.COPPER_BUTTON.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		EXPOSED_COPPER_BUTTON = ITEMS.register("exposed_copper_button", () -> new BlockItem(ModBlocks.EXPOSED_COPPER_BUTTON.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WEATHERED_COPPER_BUTTON = ITEMS.register("weathered_copper_button", () -> new BlockItem(ModBlocks.WEATHERED_COPPER_BUTTON.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		OXIDIZED_COPPER_BUTTON = ITEMS.register("oxidized_copper_button", () -> new BlockItem(ModBlocks.OXIDIZED_COPPER_BUTTON.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WAXED_COPPER_BUTTON = ITEMS.register("waxed_copper_button", () -> new BlockItem(ModBlocks.WAXED_COPPER_BUTTON.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WAXED_EXPOSED_COPPER_BUTTON = ITEMS.register("waxed_exposed_copper_button", () -> new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_BUTTON.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WAXED_WEATHERED_COPPER_BUTTON = ITEMS.register("waxed_weathered_copper_button", () -> new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_BUTTON.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WAXED_OXIDIZED_COPPER_BUTTON = ITEMS.register("waxed_oxidized_copper_button", () -> new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
	}

	public static void initRegister() {
		ITEMS.register();
	}

	public static void init() {
	}

	private ModItems() {
	}
}
