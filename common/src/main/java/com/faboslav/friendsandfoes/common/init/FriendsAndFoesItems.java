package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.item.TotemItem;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.faboslav.friendsandfoes.common.mixin.SpawnEggItemAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.ComposterBlock;

import java.util.function.Function;
import java.util.function.Supplier;

//? >=1.21.4 {
import net.minecraft.world.item.equipment.ArmorType;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.builtin.ResourcefulItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
//?} else {
/*import com.teamresourceful.resourcefullib.common.registry.ItemLikeResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
*///?}

/**
 * @see Items
 */
public final class FriendsAndFoesItems
{
	//? >=1.21.4 {
	public static final ResourcefulItemRegistry ITEMS = ResourcefulRegistries.createForItems(FriendsAndFoes.MOD_ID);
	//?} else {
	/*public static final ItemLikeResourcefulRegistry<Item> ITEMS = new ItemLikeResourcefulRegistry<>(BuiltInRegistries.ITEM, FriendsAndFoes.MOD_ID);
	*///?}

	public final static RegistryEntry<Item> COPPER_GOLEM_SPAWN_EGG = registerSpawnEgg("copper_golem_spawn_egg", FriendsAndFoesEntityTypes.COPPER_GOLEM, 0xFF9A5038, 0xFFFC998);
	public final static RegistryEntry<Item> CRAB_SPAWN_EGG = registerSpawnEgg("crab_spawn_egg", FriendsAndFoesEntityTypes.CRAB, 0xFF333077, 0xFFFE984B);
	public final static RegistryEntry<Item> GLARE_SPAWN_EGG = registerSpawnEgg("glare_spawn_egg", FriendsAndFoesEntityTypes.GLARE, 0xFF70922D, 0xFF6A5227);
	public final static RegistryEntry<Item> ICEOLOGER_SPAWN_EGG = registerSpawnEgg("iceologer_spawn_egg", FriendsAndFoesEntityTypes.ICEOLOGER, 0xFF173873, 0xFF949B9B);
	public final static RegistryEntry<Item> ILLUSIONER_SPAWN_EGG = registerSpawnEgg("illusioner_spawn_egg", () -> EntityType.ILLUSIONER, 0xFF603E5C, 0xFF888E8E);
	public final static RegistryEntry<Item> MAULER_SPAWN_EGG = registerSpawnEgg("mauler_spawn_egg", FriendsAndFoesEntityTypes.MAULER, 0xFF534F25, 0xFF817B39);
	public final static RegistryEntry<Item> MOOBLOOM_SPAWN_EGG = registerSpawnEgg("moobloom_spawn_egg", FriendsAndFoesEntityTypes.MOOBLOOM, 0xFFF7EDC1, 0xFFFACA00);
	public final static RegistryEntry<Item> RASCAL_SPAWN_EGG = registerSpawnEgg("rascal_spawn_egg", FriendsAndFoesEntityTypes.RASCAL, 0xFF05736A, 0xFF8A521C);
	public final static RegistryEntry<Item> TUFF_GOLEM_SPAWN_EGG = registerSpawnEgg("tuff_golem_spawn_egg", FriendsAndFoesEntityTypes.TUFF_GOLEM, 0xFFA0A297, 0xFF5D5D52);
	public final static RegistryEntry<Item> WILDFIRE_SPAWN_EGG = registerSpawnEgg("wildfire_spawn_egg", FriendsAndFoesEntityTypes.WILDFIRE, 0xFF6C3100, 0xFFFFD528);

	public final static RegistryEntry<Item> BUTTERCUP = registerItem("buttercup", (properties) -> new BlockItem(FriendsAndFoesBlocks.BUTTERCUP.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> CRAB_CLAW = registerItem("crab_claw", Item::new, Item.Properties::new);
	public final static RegistryEntry<Item> CRAB_EGG = registerItem("crab_egg", (properties) -> new BlockItem(FriendsAndFoesBlocks.CRAB_EGG.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> ACACIA_BEEHIVE = registerItem("acacia_beehive", (properties) -> new BlockItem(FriendsAndFoesBlocks.ACACIA_BEEHIVE.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> BAMBOO_BEEHIVE = registerItem("bamboo_beehive", (properties) -> new BlockItem(FriendsAndFoesBlocks.BAMBOO_BEEHIVE.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> BIRCH_BEEHIVE = registerItem("birch_beehive", (properties) -> new BlockItem(FriendsAndFoesBlocks.BIRCH_BEEHIVE.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> CHERRY_BEEHIVE = registerItem("cherry_beehive", (properties) -> new BlockItem(FriendsAndFoesBlocks.CHERRY_BEEHIVE.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> CRIMSON_BEEHIVE = registerItem("crimson_beehive", (properties) -> new BlockItem(FriendsAndFoesBlocks.CRIMSON_BEEHIVE.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> DARK_OAK_BEEHIVE = registerItem("dark_oak_beehive", (properties) -> new BlockItem(FriendsAndFoesBlocks.DARK_OAK_BEEHIVE.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> JUNGLE_BEEHIVE = registerItem("jungle_beehive", (properties) -> new BlockItem(FriendsAndFoesBlocks.JUNGLE_BEEHIVE.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> MANGROVE_BEEHIVE = registerItem("mangrove_beehive", (properties) -> new BlockItem(FriendsAndFoesBlocks.MANGROVE_BEEHIVE.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> SPRUCE_BEEHIVE = registerItem("spruce_beehive", (properties) -> new BlockItem(FriendsAndFoesBlocks.SPRUCE_BEEHIVE.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> WARPED_BEEHIVE = registerItem("warped_beehive", (properties) -> new BlockItem(FriendsAndFoesBlocks.WARPED_BEEHIVE.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> COPPER_BUTTON = registerItem("copper_button", (properties) -> new BlockItem(FriendsAndFoesBlocks.COPPER_BUTTON.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> EXPOSED_COPPER_BUTTON = registerItem("exposed_copper_button", (properties) -> new BlockItem(FriendsAndFoesBlocks.EXPOSED_COPPER_BUTTON.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> WEATHERED_COPPER_BUTTON = registerItem("weathered_copper_button", (properties) -> new BlockItem(FriendsAndFoesBlocks.WEATHERED_COPPER_BUTTON.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> OXIDIZED_COPPER_BUTTON = registerItem("oxidized_copper_button", (properties) -> new BlockItem(FriendsAndFoesBlocks.OXIDIZED_COPPER_BUTTON.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> WAXED_COPPER_BUTTON = registerItem("waxed_copper_button", (properties) -> new BlockItem(FriendsAndFoesBlocks.WAXED_COPPER_BUTTON.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> WAXED_EXPOSED_COPPER_BUTTON = registerItem("waxed_exposed_copper_button", (properties) -> new BlockItem(FriendsAndFoesBlocks.WAXED_EXPOSED_COPPER_BUTTON.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> WAXED_WEATHERED_COPPER_BUTTON = registerItem("waxed_weathered_copper_button", (properties) -> new BlockItem(FriendsAndFoesBlocks.WAXED_WEATHERED_COPPER_BUTTON.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> WAXED_OXIDIZED_COPPER_BUTTON = registerItem("waxed_oxidized_copper_button", (properties) -> new BlockItem(FriendsAndFoesBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get(), properties),() -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> EXPOSED_LIGHTNING_ROD = registerItem("exposed_lightning_rod", (properties) -> new BlockItem(FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> WEATHERED_LIGHTNING_ROD = registerItem("weathered_lightning_rod", (properties) -> new BlockItem(FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> OXIDIZED_LIGHTNING_ROD = registerItem("oxidized_lightning_rod", (properties) -> new BlockItem(FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> WAXED_LIGHTNING_ROD = registerItem("waxed_lightning_rod", (properties) -> new BlockItem(FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> WAXED_EXPOSED_LIGHTNING_ROD = registerItem("waxed_exposed_lightning_rod", (properties) -> new BlockItem(FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> WAXED_WEATHERED_LIGHTNING_ROD = registerItem("waxed_weathered_lightning_rod", (properties) -> new BlockItem(FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get(), properties), () -> new Item.Properties().stacksTo(64));
	public final static RegistryEntry<Item> WAXED_OXIDIZED_LIGHTNING_ROD = registerItem("waxed_oxidized_lightning_rod", (properties) -> new BlockItem(FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get(), properties), () -> new Item.Properties().stacksTo(64));

	//? >=1.21.3 {
	public final static RegistryEntry<Item> WILDFIRE_CROWN = registerItem("wildfire_crown", (properties) -> new ArmorItem(FriendsAndFoesArmorMaterials.WILDFIRE, ArmorType.HELMET, properties), () -> new Item.Properties().stacksTo(1).fireResistant().durability(ArmorType.HELMET.getDurability(37)));
	//?} else {
	/*public final static RegistryEntry<Item> WILDFIRE_CROWN = registerItem("wildfire_crown", (properties) -> new ArmorItem(FriendsAndFoesArmorMaterials.WILDFIRE.holder(), ArmorItem.Type.HELMET, properties), () -> new Item.Properties().stacksTo(1).fireResistant().durability(ArmorItem.Type.HELMET.getDurability(37)));
	*///?}
	public final static RegistryEntry<Item> WILDFIRE_CROWN_FRAGMENT = registerItem("wildfire_crown_fragment", Item::new, () -> new Item.Properties().fireResistant());
	public final static RegistryEntry<Item> TOTEM_OF_FREEZING = registerItem("totem_of_freezing", TotemItem::new, () -> new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
	public final static RegistryEntry<Item> TOTEM_OF_ILLUSION = registerItem("totem_of_illusion", TotemItem::new, () -> new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
	public static final RegistryEntry<Item> MUSIC_DISC_AROUND_THE_CORNER = registerItem("music_disc_around_the_corner", Item::new, () -> new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(FriendsAndFoesJukeboxSongs.AROUND_THE_CORNER));


	private FriendsAndFoesItems() {
	}

	private static RegistryEntry<Item> registerItem(String id, Function<Item.Properties, Item> factory, Supplier<Item.Properties> getter) {
		//? >=1.21.4 {
		return ITEMS.register(id, factory, getter);
		//?} else {
		/*return ITEMS.register(id, () -> factory.apply(getter.get()));
		*///?}
	}

	private static RegistryEntry<Item> registerSpawnEgg(
		String id,
		Supplier<? extends EntityType<? extends Mob>> typeIn,
		int primaryColorIn,
		int secondaryColorIn
	) {
		return ITEMS.register(id, () -> {
			//? >=1.21.4 {
			var spawnEgg = new SpawnEggItem(typeIn.get(), new Item.Properties().stacksTo(64).setId(ResourceKey.create(Registries.ITEM, FriendsAndFoes.makeID(id))));
			 /*?}*/
			//? =1.21.3 {
			/*var spawnEgg = new SpawnEggItem(typeIn.get(), primaryColorIn, secondaryColorIn, new Item.Properties().stacksTo(64).setId(ResourceKey.create(Registries.ITEM, FriendsAndFoes.makeID(id))));
			*///?}
			//? <=1.21.1 {
			/*var spawnEgg = new SpawnEggItem(typeIn.get(), primaryColorIn, secondaryColorIn, new Item.Properties().stacksTo(64));
			 *///?}
			var spawnEggMap = SpawnEggItemAccessor.friendsandfoes$getSpawnEggs();
			spawnEggMap.put(typeIn.get(), spawnEgg);

			return spawnEgg;
		});
	}

	public static void registerCompostableItems() {
		ComposterBlock.add(0.65F, FriendsAndFoesItems.BUTTERCUP.get());
	}
}
