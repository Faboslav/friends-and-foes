package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.item.FriendsAndFoesArmorMaterials;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
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

	static {
		COPPER_GOLEM_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("copper_golem_spawn_egg", FriendsAndFoesEntityTypes.COPPER_GOLEM, 0x9A5038, 0xE3826C, new Item.Settings().maxCount(64));
		GLARE_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("glare_spawn_egg", FriendsAndFoesEntityTypes.GLARE, 0x70922D, 0x6A5227, new Item.Settings().maxCount(64));
		ICEOLOGER_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("iceologer_spawn_egg", FriendsAndFoesEntityTypes.ICEOLOGER, 0x173873, 0x949B9B, new Item.Settings().maxCount(64));
		ILLUSIONER_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("illusioner_spawn_egg", () -> EntityType.ILLUSIONER, 0x603E5C, 0x888E8E, new Item.Settings().maxCount(64));
		MAULER_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("mauler_spawn_egg", FriendsAndFoesEntityTypes.MAULER, 0x534F25, 0x817B39, new Item.Settings().maxCount(64));
		MOOBLOOM_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("moobloom_spawn_egg", FriendsAndFoesEntityTypes.MOOBLOOM, 0xF7EDC1, 0xFACA00, new Item.Settings().maxCount(64));
		WILDFIRE_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("wildfire_spawn_egg", FriendsAndFoesEntityTypes.WILDFIRE, 0x6C3100, 0xFFD528, new Item.Settings().maxCount(64));
		BUTTERCUP = RegistryHelper.registerItem("buttercup", () -> new BlockItem(FriendsAndFoesBlocks.BUTTERCUP.get(), new Item.Settings().maxCount(64)));
		ACACIA_BEEHIVE = RegistryHelper.registerItem("acacia_beehive", () -> new BlockItem(FriendsAndFoesBlocks.ACACIA_BEEHIVE.get(), new Item.Settings().maxCount(64)));
		BIRCH_BEEHIVE = RegistryHelper.registerItem("birch_beehive", () -> new BlockItem(FriendsAndFoesBlocks.BIRCH_BEEHIVE.get(), new Item.Settings().maxCount(64)));
		CRIMSON_BEEHIVE = RegistryHelper.registerItem("crimson_beehive", () -> new BlockItem(FriendsAndFoesBlocks.CRIMSON_BEEHIVE.get(), new Item.Settings().maxCount(64)));
		DARK_OAK_BEEHIVE = RegistryHelper.registerItem("dark_oak_beehive", () -> new BlockItem(FriendsAndFoesBlocks.DARK_OAK_BEEHIVE.get(), new Item.Settings().maxCount(64)));
		JUNGLE_BEEHIVE = RegistryHelper.registerItem("jungle_beehive", () -> new BlockItem(FriendsAndFoesBlocks.JUNGLE_BEEHIVE.get(), new Item.Settings().maxCount(64)));
		MANGROVE_BEEHIVE = RegistryHelper.registerItem("mangrove_beehive", () -> new BlockItem(FriendsAndFoesBlocks.MANGROVE_BEEHIVE.get(), new Item.Settings().maxCount(64)));
		SPRUCE_BEEHIVE = RegistryHelper.registerItem("spruce_beehive", () -> new BlockItem(FriendsAndFoesBlocks.SPRUCE_BEEHIVE.get(), new Item.Settings().maxCount(64)));
		WARPED_BEEHIVE = RegistryHelper.registerItem("warped_beehive", () -> new BlockItem(FriendsAndFoesBlocks.WARPED_BEEHIVE.get(), new Item.Settings().maxCount(64)));
		COPPER_BUTTON = RegistryHelper.registerItem("copper_button", () -> new BlockItem(FriendsAndFoesBlocks.COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
		EXPOSED_COPPER_BUTTON = RegistryHelper.registerItem("exposed_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.EXPOSED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
		WEATHERED_COPPER_BUTTON = RegistryHelper.registerItem("weathered_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.WEATHERED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
		OXIDIZED_COPPER_BUTTON = RegistryHelper.registerItem("oxidized_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.OXIDIZED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
		WAXED_COPPER_BUTTON = RegistryHelper.registerItem("waxed_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
		WAXED_EXPOSED_COPPER_BUTTON = RegistryHelper.registerItem("waxed_exposed_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_EXPOSED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
		WAXED_WEATHERED_COPPER_BUTTON = RegistryHelper.registerItem("waxed_weathered_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_WEATHERED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
		WAXED_OXIDIZED_COPPER_BUTTON = RegistryHelper.registerItem("waxed_oxidized_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
		EXPOSED_LIGHTNING_ROD = RegistryHelper.registerItem("exposed_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
		WEATHERED_LIGHTNING_ROD = RegistryHelper.registerItem("weathered_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
		OXIDIZED_LIGHTNING_ROD = RegistryHelper.registerItem("oxidized_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
		WAXED_LIGHTNING_ROD = RegistryHelper.registerItem("waxed_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
		WAXED_EXPOSED_LIGHTNING_ROD = RegistryHelper.registerItem("waxed_exposed_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
		WAXED_WEATHERED_LIGHTNING_ROD = RegistryHelper.registerItem("waxed_weathered_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
		WAXED_OXIDIZED_LIGHTNING_ROD = RegistryHelper.registerItem("waxed_oxidized_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
		WILDFIRE_CROWN = RegistryHelper.registerItem("wildfire_crown", () -> new ArmorItem(FriendsAndFoesArmorMaterials.WILDFIRE, EquipmentSlot.HEAD, (new Item.Settings()).fireproof()));
		WILDFIRE_CROWN_FRAGMENT = RegistryHelper.registerItem("wildfire_crown_fragment", () -> new Item((new Item.Settings())));
	}

	public static void init() {
	}

	public static void postInit() {
		addToItemGroups();
	}

	private static void addToItemGroups() {
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, COPPER_GOLEM_SPAWN_EGG.get(), Items.CAVE_SPIDER_SPAWN_EGG);
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, GLARE_SPAWN_EGG.get(), COPPER_GOLEM_SPAWN_EGG.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, ICEOLOGER_SPAWN_EGG.get(), Items.HORSE_SPAWN_EGG);
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, ILLUSIONER_SPAWN_EGG.get(), ICEOLOGER_SPAWN_EGG.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, MAULER_SPAWN_EGG.get(), Items.MAGMA_CUBE_SPAWN_EGG);
		RegistryHelper.addToItemGroupBefore(ItemGroups.SPAWN_EGGS, MOOBLOOM_SPAWN_EGG.get(), Items.MOOSHROOM_SPAWN_EGG);
		RegistryHelper.addToItemGroupBefore(ItemGroups.SPAWN_EGGS, WILDFIRE_SPAWN_EGG.get(), Items.WITHER_SPAWN_EGG);
		RegistryHelper.addToItemGroupAfter(ItemGroups.NATURAL, BUTTERCUP.get(), Items.DANDELION);

		RegistryHelper.addToItemGroupAfter(ItemGroups.FUNCTIONAL, SPRUCE_BEEHIVE.get(), Items.BEEHIVE);
		RegistryHelper.addToItemGroupAfter(ItemGroups.FUNCTIONAL, BIRCH_BEEHIVE.get(), SPRUCE_BEEHIVE.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.FUNCTIONAL, JUNGLE_BEEHIVE.get(), BIRCH_BEEHIVE.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.FUNCTIONAL, ACACIA_BEEHIVE.get(), JUNGLE_BEEHIVE.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.FUNCTIONAL, DARK_OAK_BEEHIVE.get(), ACACIA_BEEHIVE.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.FUNCTIONAL, MANGROVE_BEEHIVE.get(), DARK_OAK_BEEHIVE.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.FUNCTIONAL, CRIMSON_BEEHIVE.get(), MANGROVE_BEEHIVE.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.FUNCTIONAL, WARPED_BEEHIVE.get(), CRIMSON_BEEHIVE.get());


		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, COPPER_BUTTON.get(), Items.STONE_BUTTON);
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, EXPOSED_COPPER_BUTTON.get(), COPPER_BUTTON.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WEATHERED_COPPER_BUTTON.get(), EXPOSED_COPPER_BUTTON.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, OXIDIZED_COPPER_BUTTON.get(), WEATHERED_COPPER_BUTTON.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WAXED_COPPER_BUTTON.get(), OXIDIZED_COPPER_BUTTON.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WAXED_EXPOSED_COPPER_BUTTON.get(), WAXED_COPPER_BUTTON.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WAXED_WEATHERED_COPPER_BUTTON.get(), WAXED_EXPOSED_COPPER_BUTTON.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WAXED_OXIDIZED_COPPER_BUTTON.get(), WAXED_OXIDIZED_COPPER_BUTTON.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, EXPOSED_LIGHTNING_ROD.get(), Items.LIGHTNING_ROD);
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WEATHERED_LIGHTNING_ROD.get(), EXPOSED_LIGHTNING_ROD.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, OXIDIZED_LIGHTNING_ROD.get(), WEATHERED_LIGHTNING_ROD.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WAXED_LIGHTNING_ROD.get(), WEATHERED_LIGHTNING_ROD.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WAXED_EXPOSED_LIGHTNING_ROD.get(), WAXED_LIGHTNING_ROD.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WAXED_WEATHERED_LIGHTNING_ROD.get(), WAXED_EXPOSED_LIGHTNING_ROD.get());
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WAXED_OXIDIZED_LIGHTNING_ROD.get(), WAXED_WEATHERED_LIGHTNING_ROD.get());

		RegistryHelper.addToItemGroupAfter(ItemGroups.INGREDIENTS, WILDFIRE_CROWN_FRAGMENT.get(), Items.SCUTE);
		RegistryHelper.addToItemGroupAfter(ItemGroups.COMBAT, WILDFIRE_CROWN.get(), Items.TURTLE_HELMET);
	}

	private FriendsAndFoesItems() {
	}
}
