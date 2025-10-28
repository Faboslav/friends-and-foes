package com.faboslav.friendsandfoes.common.tag;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

/**
 * @see BlockTags
 * @see net.minecraft.tags.PoiTypeTags
 */
public final class FriendsAndFoesTags
{
	public static final TagKey<PoiType> BEEKEEPER_ACQUIRABLE_JOB_SITE = pointOfInterestTypeTag("beekeeper_acquirable_job_site");
	public static final TagKey<Block> COPPER_BUTTONS = blockTag("copper_buttons");
	public static final TagKey<Item> CRAB_TEMPT_ITEMS = itemTag("crab_tempt_items");
	public static final TagKey<Block> CRABS_SPAWNABLE_ON = blockTag("crabs_spawnable_on");
	public static final TagKey<Block> CRAB_BURROW_SPOT_BLOCKS = blockTag("crab_burrow_spot_blocks");
	public static final TagKey<Block> GLARES_SPAWNABLE_ON = blockTag("glares_spawnable_on");
	public static final TagKey<Block> MAULERS_SPAWNABLE_ON = blockTag("maulers_spawnable_on");
	public static final TagKey<Item> GLARE_FOOD_ITEMS = itemTag("glare_food_items");
	public static final TagKey<Item> GLARE_TEMPT_ITEMS = itemTag("glare_tempt_items");
	public static final TagKey<Item> PENGUIN_TEMPT_ITEMS = itemTag("penguin_tempt_items");
	public static final TagKey<Item> TOTEMS = itemTag("totems");
	public static final TagKey<Item> REPAIRS_WILDFIRE_CROWN = itemTag("repairs_wildfire_crown");
	public static final TagKey<Biome> HAS_BARNACLE = biomeTag("has_barnacle");
	public static final TagKey<Biome> HAS_CRAB = biomeTag("has_crab");
	public static final TagKey<EntityType<?>> MAULER_PREY = entityTypeTag("mauler_prey");
	public static final TagKey<EntityType<?>> WILDFIRE_ALLIES = entityTypeTag("wildfire_allies");
	public static final TagKey<Biome> HAS_BADLANDS_MAULER = biomeTag("has_badlands_mauler");
	public static final TagKey<Biome> HAS_DESERT_MAULER = biomeTag("has_desert_mauler");
	public static final TagKey<Biome> HAS_GLARE = biomeTag("has_glare");
	public static final TagKey<Biome> HAS_ICEOLOGER = biomeTag("has_iceologer");
	public static final TagKey<Biome> HAS_ILLUSIONER = biomeTag("has_illusioner");
	public static final TagKey<Biome> HAS_MOOBLOOMS = biomeTag("has_moobloom/any");
	public static final TagKey<Biome> HAS_PENGUIN = biomeTag("has_penguin");
	public static final TagKey<Biome> HAS_RASCAL = biomeTag("has_rascal");
	public static final TagKey<Biome> HAS_SAVANNA_MAULER = biomeTag("has_savanna_mauler");
	//? if <= 1.21.8 {
	/*public static final TagKey<Block> LIGHTNING_RODS = blockTag("lightning_rods");
	public static final TagKey<PoiType> LIGHTNING_ROD_POI = pointOfInterestTypeTag("lightning_rods");
	*///?}

	private static TagKey<Block> blockTag(String name) {
		return TagKey.create(Registries.BLOCK, FriendsAndFoes.makeID(name));
	}

	private static TagKey<Item> itemTag(String name) {
		return TagKey.create(Registries.ITEM, FriendsAndFoes.makeID(name));
	}

	private static TagKey<EntityType<?>> entityTypeTag(String name) {
		return TagKey.create(Registries.ENTITY_TYPE, FriendsAndFoes.makeID(name));
	}

	private static TagKey<Biome> biomeTag(String name) {
		return TagKey.create(Registries.BIOME, FriendsAndFoes.makeID(name));
	}

	private static TagKey<PoiType> pointOfInterestTypeTag(String name) {
		return TagKey.create(Registries.POINT_OF_INTEREST_TYPE, FriendsAndFoes.makeID(name));
	}

	public static void init() {}
}
