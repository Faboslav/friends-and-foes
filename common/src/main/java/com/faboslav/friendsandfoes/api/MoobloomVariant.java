package com.faboslav.friendsandfoes.api;

import net.minecraft.block.PlantBlock;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public final class MoobloomVariant
{
	private final String name;
	private final PlantBlock flower;
	private final TagKey<Biome> biomes;

	MoobloomVariant(
		String name,
		PlantBlock flower,
		TagKey<Biome> biomes
	) {
		this.name = name;
		this.flower = flower;
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
