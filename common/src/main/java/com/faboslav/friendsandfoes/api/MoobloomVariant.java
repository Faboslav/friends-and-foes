package com.faboslav.friendsandfoes.api;

import net.minecraft.block.FlowerBlock;
import net.minecraft.item.Item;

public final class MoobloomVariant
{
	private final String name;
	private final FlowerBlock flowerBlock;

	MoobloomVariant(
		String name,
		FlowerBlock flowerBlock
	) {
		this.name = name;
		this.flowerBlock = flowerBlock;
	}

	public String getName() {
		return this.name;
	}

	public String getFlowerName() {
		return this.getFlowerBlock().getName().toString();
	}

	public FlowerBlock getFlowerBlock() {
		return this.flowerBlock;
	}

	public Item getFlowerBlockAsItem() {
		return this.flowerBlock.asItem();
	}
}
