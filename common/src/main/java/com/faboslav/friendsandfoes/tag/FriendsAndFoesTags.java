package com.faboslav.friendsandfoes.tag;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.poi.PointOfInterestType;

/**
 * @see BlockTags
 */
public final class FriendsAndFoesTags
{
	public static final TagKey<Block> COPPER_BUTTONS = blockTag("copper_buttons");
	public static final TagKey<Block> LIGHTNING_RODS = blockTag("lightning_rods");
	public static final TagKey<PointOfInterestType> LIGHTNING_ROD_POI = pointOfInterestTypeTag("lightning_rods");
	public static final TagKey<Block> GLARES_SPAWNABLE_ON = blockTag("glares_spawnable_on");
	public static final TagKey<Block> MAULERS_SPAWNABLE_ON = blockTag("maulers_spawnable_on");
	public static final TagKey<Item> GLARE_FOOD_ITEMS = itemTag("glare_food_items");
	public static final TagKey<Item> GLARE_TEMPT_ITEMS = itemTag("glare_tempt_items");
	public static final TagKey<EntityType<?>> MAULER_PREY = entityTypeTag("mauler_prey");
	public static final TagKey<EntityType<?>> WILDFIRE_ALLIES = entityTypeTag("wildfire_allies");
	public static final TagKey<Biome> HAS_BADLANDS_MAULER = biomeTag("has_badlands_mauler");
	public static final TagKey<Biome> HAS_DESERT_MAULER = biomeTag("has_desert_mauler");
	public static final TagKey<Biome> HAS_GLARE = biomeTag("has_glare");
	public static final TagKey<Biome> HAS_ICEOLOGER = biomeTag("has_iceologer");
	public static final TagKey<Biome> HAS_ILLUSIONER = biomeTag("has_illusioner");
	public static final TagKey<Biome> HAS_LESS_MOOBLOOMS = biomeTag("has_less_mooblooms");
	public static final TagKey<Biome> HAS_MORE_MOOBLOOMS = biomeTag("has_more_mooblooms");
	public static final TagKey<Biome> HAS_SAVANNA_MAULER = biomeTag("has_savanna_mauler");

	private static TagKey<Block> blockTag(String name) {
		return TagKey.of(RegistryKeys.BLOCK, FriendsAndFoes.makeID(name));
	}

	private static TagKey<Item> itemTag(String name) {
		return TagKey.of(RegistryKeys.ITEM, FriendsAndFoes.makeID(name));
	}

	private static TagKey<EntityType<?>> entityTypeTag(String name) {
		return TagKey.of(RegistryKeys.ENTITY_TYPE, FriendsAndFoes.makeID(name));
	}

	private static TagKey<Biome> biomeTag(String name) {
		return TagKey.of(RegistryKeys.BIOME, FriendsAndFoes.makeID(name));
	}

	private static TagKey<PointOfInterestType> pointOfInterestTypeTag(String name) {
		return TagKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, FriendsAndFoes.makeID(name));
	}
}
