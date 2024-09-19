package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import com.faboslav.friendsandfoes.common.item.DispenserAddedSpawnEgg;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Rarity;

/**
 * @see Items
 */
public final class FriendsAndFoesItems
{
	public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(Registries.ITEM, FriendsAndFoes.MOD_ID);

	public final static RegistryEntry<Item> COPPER_GOLEM_SPAWN_EGG = ITEMS.register("copper_golem_spawn_egg", () -> new DispenserAddedSpawnEgg(FriendsAndFoesEntityTypes.COPPER_GOLEM, 0x9A5038, 0xE3826C, new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> CRAB_SPAWN_EGG = ITEMS.register("crab_spawn_egg", () -> new DispenserAddedSpawnEgg(FriendsAndFoesEntityTypes.CRAB, 0x333077, 0xFE984B, new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> GLARE_SPAWN_EGG = ITEMS.register("glare_spawn_egg", () -> new DispenserAddedSpawnEgg(FriendsAndFoesEntityTypes.GLARE, 0x70922D, 0x6A5227, new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> ICEOLOGER_SPAWN_EGG = ITEMS.register("iceologer_spawn_egg", () -> new DispenserAddedSpawnEgg(FriendsAndFoesEntityTypes.ICEOLOGER, 0x173873, 0x949B9B, new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> ILLUSIONER_SPAWN_EGG = ITEMS.register("illusioner_spawn_egg", () -> new DispenserAddedSpawnEgg(() -> EntityType.ILLUSIONER, 0x603E5C, 0x888E8E, new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> MAULER_SPAWN_EGG = ITEMS.register("mauler_spawn_egg", () -> new DispenserAddedSpawnEgg(FriendsAndFoesEntityTypes.MAULER, 0x534F25, 0x817B39, new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> MOOBLOOM_SPAWN_EGG = ITEMS.register("moobloom_spawn_egg", () -> new DispenserAddedSpawnEgg(FriendsAndFoesEntityTypes.MOOBLOOM, 0xF7EDC1, 0xFACA00, new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> RASCAL_SPAWN_EGG = ITEMS.register("rascal_spawn_egg", () -> new DispenserAddedSpawnEgg(FriendsAndFoesEntityTypes.RASCAL, 0x05736A, 0x8A521C, new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> TUFF_GOLEM_SPAWN_EGG = ITEMS.register("tuff_golem_spawn_egg", () -> new DispenserAddedSpawnEgg(FriendsAndFoesEntityTypes.TUFF_GOLEM, 0xA0A297, 0x5D5D52, new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> WILDFIRE_SPAWN_EGG = ITEMS.register("wildfire_spawn_egg", () -> new DispenserAddedSpawnEgg(FriendsAndFoesEntityTypes.WILDFIRE, 0x6C3100, 0xFFD528, new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> BUTTERCUP = ITEMS.register("buttercup", () -> new BlockItem(FriendsAndFoesBlocks.BUTTERCUP.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> CRAB_CLAW = ITEMS.register("crab_claw", () -> new Item((new Item.Settings())));
	public final static RegistryEntry<Item> CRAB_EGG = ITEMS.register("crab_egg", () -> new BlockItem(FriendsAndFoesBlocks.CRAB_EGG.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> ACACIA_BEEHIVE = ITEMS.register("acacia_beehive", () -> new BlockItem(FriendsAndFoesBlocks.ACACIA_BEEHIVE.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> BAMBOO_BEEHIVE = ITEMS.register("bamboo_beehive", () -> new BlockItem(FriendsAndFoesBlocks.BAMBOO_BEEHIVE.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> BIRCH_BEEHIVE = ITEMS.register("birch_beehive", () -> new BlockItem(FriendsAndFoesBlocks.BIRCH_BEEHIVE.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> CHERRY_BEEHIVE = ITEMS.register("cherry_beehive", () -> new BlockItem(FriendsAndFoesBlocks.CHERRY_BEEHIVE.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> CRIMSON_BEEHIVE = ITEMS.register("crimson_beehive", () -> new BlockItem(FriendsAndFoesBlocks.CRIMSON_BEEHIVE.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> DARK_OAK_BEEHIVE = ITEMS.register("dark_oak_beehive", () -> new BlockItem(FriendsAndFoesBlocks.DARK_OAK_BEEHIVE.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> JUNGLE_BEEHIVE = ITEMS.register("jungle_beehive", () -> new BlockItem(FriendsAndFoesBlocks.JUNGLE_BEEHIVE.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> MANGROVE_BEEHIVE = ITEMS.register("mangrove_beehive", () -> new BlockItem(FriendsAndFoesBlocks.MANGROVE_BEEHIVE.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> SPRUCE_BEEHIVE = ITEMS.register("spruce_beehive", () -> new BlockItem(FriendsAndFoesBlocks.SPRUCE_BEEHIVE.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> WARPED_BEEHIVE = ITEMS.register("warped_beehive", () -> new BlockItem(FriendsAndFoesBlocks.WARPED_BEEHIVE.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> COPPER_BUTTON = ITEMS.register("copper_button", () -> new BlockItem(FriendsAndFoesBlocks.COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> EXPOSED_COPPER_BUTTON = ITEMS.register("exposed_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.EXPOSED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> WEATHERED_COPPER_BUTTON = ITEMS.register("weathered_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.WEATHERED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> OXIDIZED_COPPER_BUTTON = ITEMS.register("oxidized_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.OXIDIZED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> WAXED_COPPER_BUTTON = ITEMS.register("waxed_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> WAXED_EXPOSED_COPPER_BUTTON = ITEMS.register("waxed_exposed_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_EXPOSED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> WAXED_WEATHERED_COPPER_BUTTON = ITEMS.register("waxed_weathered_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_WEATHERED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> WAXED_OXIDIZED_COPPER_BUTTON = ITEMS.register("waxed_oxidized_copper_button", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> EXPOSED_LIGHTNING_ROD = ITEMS.register("exposed_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> WEATHERED_LIGHTNING_ROD = ITEMS.register("weathered_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> OXIDIZED_LIGHTNING_ROD = ITEMS.register("oxidized_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> WAXED_LIGHTNING_ROD = ITEMS.register("waxed_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> WAXED_EXPOSED_LIGHTNING_ROD = ITEMS.register("waxed_exposed_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> WAXED_WEATHERED_LIGHTNING_ROD = ITEMS.register("waxed_weathered_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> WAXED_OXIDIZED_LIGHTNING_ROD = ITEMS.register("waxed_oxidized_lightning_rod", () -> new BlockItem(FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
	public final static RegistryEntry<Item> WILDFIRE_CROWN = ITEMS.register("wildfire_crown", () -> new ArmorItem(FriendsAndFoesArmorMaterials.WILDFIRE.referenceRegistryEntry(), ArmorItem.Type.HELMET, (new Item.Settings().maxCount(1)).fireproof().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(37))));
	public final static RegistryEntry<Item> WILDFIRE_CROWN_FRAGMENT = ITEMS.register("wildfire_crown_fragment", () -> new Item((new Item.Settings()).fireproof()));
	public final static RegistryEntry<Item> TOTEM_OF_FREEZING = ITEMS.register("totem_of_freezing", () -> new Item((new Item.Settings()).maxCount(1).rarity(Rarity.UNCOMMON)));
	public final static RegistryEntry<Item> TOTEM_OF_ILLUSION = ITEMS.register("totem_of_illusion", () -> new Item((new Item.Settings()).maxCount(1).rarity(Rarity.UNCOMMON)));

	private FriendsAndFoesItems() {
	}
}
