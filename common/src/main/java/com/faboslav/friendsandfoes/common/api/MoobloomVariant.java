package com.faboslav.friendsandfoes.common.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
//? >=1.21.5 {
/*import net.minecraft.world.level.block.VegetationBlock;
*///?} else {
import net.minecraft.world.level.block.BushBlock;
//?}

public final class MoobloomVariant
{
	public static final Codec<MoobloomVariant> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
		Codec.STRING.fieldOf("name").forGetter(MoobloomVariant::getName),
		BuiltInRegistries.BLOCK.byNameCodec().fieldOf("flower").forGetter(MoobloomVariant::getFlower),
		TagKey.hashedCodec(Registries.BIOME).fieldOf("biomes").forGetter(MoobloomVariant::getBiomes)
	).apply(instance, instance.stable(MoobloomVariant::new)));

	private final String name;
	//? >=1.21.5 {
	/*private final VegetationBlock flower;
	*///?} else {
	private final BushBlock flower;
	//?}
	private final TagKey<Biome> biomes;

	MoobloomVariant(
		String name,
		Block flower,
		TagKey<Biome> biomes
	) {
		this.name = name;
		//? >=1.21.5 {
		/*this.flower = (VegetationBlock) flower;
		*///?} else {
		this.flower = (BushBlock) flower;
		//?}
		this.biomes = biomes;
	}

	public String getName() {
		return this.name;
	}

	public String getFlowerName() {
		return this.getFlower().getName().toString();
	}

	//? >=1.21.5 {
	/*public VegetationBlock getFlower()
	 *///?} else {
	public BushBlock getFlower()
	//?}
	{
		return this.flower;
	}

	public Item getFlowerAsItem() {
		return this.flower.asItem();
	}

	public TagKey<Biome> getBiomes() {
		return this.biomes;
	}
}
