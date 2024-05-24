package com.faboslav.friendsandfoes.common.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.PlantBlock;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public final class MoobloomVariant
{
	public static final Codec<MoobloomVariant> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
		Codec.STRING.fieldOf("name").forGetter(MoobloomVariant::getName),
		Registry.BLOCK.getCodec().fieldOf("flower").forGetter(MoobloomVariant::getFlower),
		TagKey.codec(Registry.BIOME_KEY).fieldOf("biomes").forGetter(MoobloomVariant::getBiomes)
	).apply(instance, instance.stable(MoobloomVariant::new)));

	private final String name;
	private final PlantBlock flower;
	private final TagKey<Biome> biomes;

	MoobloomVariant(
		String name,
		Block flower,
		TagKey<Biome> biomes
	) {
		this.name = name;
		this.flower = (PlantBlock) flower;
		this.biomes = biomes;
	}

	public String getName() {
		return this.name;
	}

	public String getFlowerName() {
		return this.getFlower().getName().toString();
	}

	public PlantBlock getFlower() {
		return this.flower;
	}

	public Item getFlowerAsItem() {
		return this.flower.asItem();
	}

	public TagKey<Biome> getBiomes() {
		return this.biomes;
	}
}
