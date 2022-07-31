package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

/**
 * @see net.minecraft.tag.BlockTags
 */
public class ModTags
{
	public static final TagKey<Block> COPPER_BUTTONS = blockTag("copper_buttons");
	public static final TagKey<Block> LIGHTNING_RODS = blockTag("lightning_rods");
	public static final TagKey<Block> GLARE_SPAWNABLE_ON = blockTag("glare_spawnable_on");
	public static final TagKey<Block> MAULERS_SPAWNABLE_ON = blockTag("maulers_spawnable_on");
	public static final TagKey<EntityType<?>> MAULER_PREY = entityTypeTag("mauler_prey");
	public static final TagKey<Biome> HAS_BADLANDS_MAULER = biomeTag("has_badlands_mauler");
	public static final TagKey<Biome> HAS_DESERT_MAULER = biomeTag("has_desert_mauler");
	public static final TagKey<Biome> HAS_GLARE = biomeTag("has_glare");
	public static final TagKey<Biome> HAS_ICEOLOGER = biomeTag("has_iceologer");
	public static final TagKey<Biome> HAS_ILLUSIONER = biomeTag("has_illusioner");
	public static final TagKey<Biome> HAS_LESS_MOOBLOOMS = biomeTag("has_less_mooblooms");
	public static final TagKey<Biome> HAS_MORE_MOOBLOOMS = biomeTag("has_more_mooblooms");
	public static final TagKey<Biome> HAS_SAVANNA_MAULER = biomeTag("has_savanna_mauler");

	private static TagKey<Block> blockTag(String name) {
		return TagKey.of(Registry.BLOCK_KEY, FriendsAndFoes.makeID(name));
	}

	private static TagKey<EntityType<?>> entityTypeTag(String name) {
		return TagKey.of(Registry.ENTITY_TYPE_KEY, FriendsAndFoes.makeID(name));
	}

	private static TagKey<Biome> biomeTag(String name) {
		return TagKey.of(Registry.BIOME_KEY, FriendsAndFoes.makeID(name));
	}
}
