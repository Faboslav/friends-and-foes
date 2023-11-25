package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.item.FriendsAndFoesArmorMaterials;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Rarity;

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
	public final static Supplier<Item> RASCAL_SPAWN_EGG;
	public final static Supplier<Item> TUFF_GOLEM_SPAWN_EGG;
	public final static Supplier<Item> WILDFIRE_SPAWN_EGG;
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
	public static final Supplier<Item> WILDFIRE_CROWN;
	public static final Supplier<Item> WILDFIRE_CROWN_FRAGMENT;
	public static final Supplier<Item> TOTEM_OF_FREEZING;
	public static final Supplier<Item> TOTEM_OF_ILLUSION;

	static {
		COPPER_GOLEM_SPAWN_EGG = RegistryHelper.registerItem("copper_golem_spawn_egg", () -> new SpawnEggItem(FriendsAndFoesEntityTypes.COPPER_GOLEM.get(), 0x9A5038, 0xE3826C, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		GLARE_SPAWN_EGG = RegistryHelper.registerItem("glare_spawn_egg", () -> new SpawnEggItem(FriendsAndFoesEntityTypes.GLARE.get(), 0x70922D, 0x6A5227, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		ICEOLOGER_SPAWN_EGG = RegistryHelper.registerItem("iceologer_spawn_egg", () -> new SpawnEggItem(FriendsAndFoesEntityTypes.ICEOLOGER.get(), 0x173873, 0x949B9B, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		ILLUSIONER_SPAWN_EGG = RegistryHelper.registerItem("illusioner_spawn_egg", () -> new SpawnEggItem(EntityType.ILLUSIONER, 0x603E5C, 0x888E8E, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		MAULER_SPAWN_EGG = RegistryHelper.registerItem("mauler_spawn_egg", () -> new SpawnEggItem(FriendsAndFoesEntityTypes.MAULER.get(), 0x534F25, 0x817B39, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		MOOBLOOM_SPAWN_EGG = RegistryHelper.registerItem("moobloom_spawn_egg", () -> new SpawnEggItem(FriendsAndFoesEntityTypes.MOOBLOOM.get(), 0xF7EDC1, 0xFACA00, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		RASCAL_SPAWN_EGG = RegistryHelper.registerItem("rascal_spawn_egg", () -> new SpawnEggItem(FriendsAndFoesEntityTypes.RASCAL.get(), 0x05736A, 0x8A521C, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		TUFF_GOLEM_SPAWN_EGG = RegistryHelper.registerItem("tuff_golem_spawn_egg", () -> new SpawnEggItem(FriendsAndFoesEntityTypes.TUFF_GOLEM.get(), 0xA0A297, 0x5D5D52, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
		WILDFIRE_SPAWN_EGG = RegistryHelper.registerItem("wildfire_spawn_egg", () -> new SpawnEggItem(FriendsAndFoesEntityTypes.WILDFIRE.get(), 0x6C3100, 0xFFD528, new Item.Settings().maxCount(64).group(ItemGroup.MISC)));
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
		WILDFIRE_CROWN = RegistryHelper.registerItem("wildfire_crown", () -> new ArmorItem(FriendsAndFoesArmorMaterials.WILDFIRE, EquipmentSlot.HEAD, (new Item.Settings()).group(ItemGroup.COMBAT).fireproof()));
		WILDFIRE_CROWN_FRAGMENT = RegistryHelper.registerItem("wildfire_crown_fragment", () -> new Item((new Item.Settings()).group(ItemGroup.MATERIALS).fireproof()));
		TOTEM_OF_FREEZING = RegistryHelper.registerItem("totem_of_freezing", () -> new Item((new Item.Settings()).maxCount(1).group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON)));
		TOTEM_OF_ILLUSION = RegistryHelper.registerItem("totem_of_illusion", () -> new Item((new Item.Settings()).maxCount(1).group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON)));
	}

	public static void init() {
	}

	private FriendsAndFoesItems() {
	}
}
