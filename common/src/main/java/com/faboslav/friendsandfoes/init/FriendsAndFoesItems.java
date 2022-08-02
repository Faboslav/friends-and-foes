package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.item.DispensableSpawnEggItem;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;

import java.util.function.Supplier;

/**
 * @see Items
 */
public final class FriendsAndFoesItems
{
	public final static Supplier<Item> COPPER_GOLEM_SPAWN_EGG;
	public final static Supplier<Item> GLARE_SPAWN_EGG;
	public final static Supplier<Item> ICEOLOGER_SPAWN_EGG;
	public final static Supplier<Item> ILLUSIONER_SPAWN_EGG;
	public final static Supplier<Item> MAULER_SPAWN_EGG;
	public final static Supplier<Item> MOOBLOOM_SPAWN_EGG;
	public static final Supplier<Item> BUTTERCUP;
	public static final Supplier<Item> ACACIA_BEEHIVE;
	public static final Supplier<Item> BIRCH_BEEHIVE;
	public static final Supplier<Item> CRIMSON_BEEHIVE;
	public static final Supplier<Item> DARK_OAK_BEEHIVE;
	public static final Supplier<Item> JUNGLE_BEEHIVE;
	public static final Supplier<Item> MANGROVE_BEEHIVE;
	public static final Supplier<Item> SPRUCE_BEEHIVE;
	public static final Supplier<Item> WARPED_BEEHIVE;
	public static final Supplier<Item> COPPER_BUTTON;
	public static final Supplier<Item> EXPOSED_COPPER_BUTTON;
	public static final Supplier<Item> WEATHERED_COPPER_BUTTON;
	public static final Supplier<Item> OXIDIZED_COPPER_BUTTON;
	public static final Supplier<Item> WAXED_COPPER_BUTTON;
	public static final Supplier<Item> WAXED_EXPOSED_COPPER_BUTTON;
	public static final Supplier<Item> WAXED_WEATHERED_COPPER_BUTTON;
	public static final Supplier<Item> WAXED_OXIDIZED_COPPER_BUTTON;
	public static final Supplier<Item> EXPOSED_LIGHTNING_ROD;
	public static final Supplier<Item> WEATHERED_LIGHTNING_ROD;
	public static final Supplier<Item> OXIDIZED_LIGHTNING_ROD;
	public static final Supplier<Item> WAXED_LIGHTNING_ROD;
	public static final Supplier<Item> WAXED_EXPOSED_LIGHTNING_ROD;
	public static final Supplier<Item> WAXED_WEATHERED_LIGHTNING_ROD;
	public static final Supplier<Item> WAXED_OXIDIZED_LIGHTNING_ROD;

	static {
		COPPER_GOLEM_SPAWN_EGG = RegistryHelper.registerItem("copper_golem_spawn_egg", () -> new DispensableSpawnEggItem(FriendsAndFoesEntityTypes.COPPER_GOLEM, 0x9A5038, 0xE3826C, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		GLARE_SPAWN_EGG = RegistryHelper.registerItem("glare_spawn_egg", () -> new DispensableSpawnEggItem(FriendsAndFoesEntityTypes.GLARE, 0x70922D, 0x6A5227, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		ICEOLOGER_SPAWN_EGG = RegistryHelper.registerItem("iceologer_spawn_egg", () -> new DispensableSpawnEggItem(FriendsAndFoesEntityTypes.ICEOLOGER, 0x173873, 0x949b9b, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		ILLUSIONER_SPAWN_EGG = RegistryHelper.registerItem("illusioner_spawn_egg", () -> new SpawnEggItem(EntityType.ILLUSIONER, 0x603e5c, 0x888e8e, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		MAULER_SPAWN_EGG = RegistryHelper.registerItem("mauler_spawn_egg", () -> new DispensableSpawnEggItem(FriendsAndFoesEntityTypes.MAULER, 0x534F25, 0x817B39, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		MOOBLOOM_SPAWN_EGG = RegistryHelper.registerItem("moobloom_spawn_egg", () -> new DispensableSpawnEggItem(FriendsAndFoesEntityTypes.MOOBLOOM, 0xf7EDC1, 0xFACA00, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		BUTTERCUP = RegistryHelper.registerItem("buttercup", () -> new BlockItem(FriendsAndFoesBlocks.BUTTERCUP.get(), new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		ACACIA_BEEHIVE = RegistryHelper.registerItem("acacia_beehive", () -> new BlockItem(FriendsAndFoesBlocks.ACACIA_BEEHIVE.get(), new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		BIRCH_BEEHIVE = RegistryHelper.registerItem("birch_beehive", () -> new BlockItem(FriendsAndFoesBlocks.BIRCH_BEEHIVE.get(), new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		CRIMSON_BEEHIVE = RegistryHelper.registerItem("crimson_beehive", () -> new BlockItem(FriendsAndFoesBlocks.CRIMSON_BEEHIVE.get(), new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		DARK_OAK_BEEHIVE = RegistryHelper.registerItem("dark_oak_beehive", () -> new BlockItem(FriendsAndFoesBlocks.DARK_OAK_BEEHIVE.get(), new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		JUNGLE_BEEHIVE = RegistryHelper.registerItem("jungle_beehive", () -> new BlockItem(FriendsAndFoesBlocks.JUNGLE_BEEHIVE.get(), new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		MANGROVE_BEEHIVE = RegistryHelper.registerItem("mangrove_beehive", () -> new BlockItem(FriendsAndFoesBlocks.MANGROVE_BEEHIVE.get(), new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		SPRUCE_BEEHIVE = RegistryHelper.registerItem("spruce_beehive", () -> new BlockItem(FriendsAndFoesBlocks.SPRUCE_BEEHIVE.get(), new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		WARPED_BEEHIVE = RegistryHelper.registerItem("warped_beehive", () -> new BlockItem(FriendsAndFoesBlocks.WARPED_BEEHIVE.get(), new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		COPPER_BUTTON = RegistryHelper.registerItem("copper_button", () -> new BlockItem(FriendsAndFoesBlocks.COPPER_BUTTON.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		EXPOSED_COPPER_BUTTON = RegistryHelper.registerItem("exposed_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.EXPOSED_COPPER_BUTTON.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WEATHERED_COPPER_BUTTON = RegistryHelper.registerItem("weathered_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.WEATHERED_COPPER_BUTTON.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		OXIDIZED_COPPER_BUTTON = RegistryHelper.registerItem("oxidized_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.OXIDIZED_COPPER_BUTTON.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WAXED_COPPER_BUTTON = RegistryHelper.registerItem("waxed_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_COPPER_BUTTON.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WAXED_EXPOSED_COPPER_BUTTON = RegistryHelper.registerItem("waxed_exposed_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_EXPOSED_COPPER_BUTTON.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WAXED_WEATHERED_COPPER_BUTTON = RegistryHelper.registerItem("waxed_weathered_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_WEATHERED_COPPER_BUTTON.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WAXED_OXIDIZED_COPPER_BUTTON = RegistryHelper.registerItem("waxed_oxidized_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		EXPOSED_LIGHTNING_ROD = RegistryHelper.registerItem("exposed_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WEATHERED_LIGHTNING_ROD = RegistryHelper.registerItem("weathered_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		OXIDIZED_LIGHTNING_ROD = RegistryHelper.registerItem("oxidized_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WAXED_LIGHTNING_ROD = RegistryHelper.registerItem("waxed_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WAXED_EXPOSED_LIGHTNING_ROD = RegistryHelper.registerItem("waxed_exposed_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WAXED_WEATHERED_LIGHTNING_ROD = RegistryHelper.registerItem("waxed_weathered_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WAXED_OXIDIZED_LIGHTNING_ROD = RegistryHelper.registerItem("waxed_oxidized_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get(), new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
	}

	public static void init() {
	}

	private FriendsAndFoesItems() {
	}
}
