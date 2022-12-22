package com.faboslav.friendsandfoes.api;

import net.minecraft.block.PlantBlock;
import net.minecraft.item.Item;

public final class MoobloomVariant
{
	private final String name;
	private final PlantBlock flower;

	MoobloomVariant(
		String name,
		PlantBlock flower
	) {
		this.name = name;
		this.flower = flower;
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
}
