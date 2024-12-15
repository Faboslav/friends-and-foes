package com.faboslav.friendsandfoes.common.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;

public final class MoobloomVariant
{
	public static final Codec<MoobloomVariant> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
		Codec.STRING.fieldOf("name").forGetter(MoobloomVariant::getName),
		BuiltInRegistries.BLOCK.byNameCodec().fieldOf("flower").forGetter(MoobloomVariant::getFlower),
		TagKey.hashedCodec(Registries.BIOME).fieldOf("biomes").forGetter(MoobloomVariant::getBiomes)
	).apply(instance, instance.stable(MoobloomVariant::new)));

	private final String name;
	private final BushBlock flower;
	private final TagKey<Biome> biomes;

	MoobloomVariant(
		String name,
		Block flower,
		TagKey<Biome> biomes
	) {
		this.name = name;
		this.flower = (BushBlock) flower;
		this.biomes = biomes;
	}

	public String getName() {
		return this.name;
	}

	public String getFlowerName() {
		return this.getFlower().getName().toString();
	}

	public BushBlock getFlower() {
		return this.flower;
	}

	public Item getFlowerAsItem() {
		return this.flower.asItem();
	}

	public TagKey<Biome> getBiomes() {
		return this.biomes;
	}
}
