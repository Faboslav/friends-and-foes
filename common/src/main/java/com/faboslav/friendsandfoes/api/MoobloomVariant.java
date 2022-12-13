package com.faboslav.friendsandfoes.api;

import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;

public enum MoobloomVariant
{
	DANDELION("dandelion", (FlowerBlock) Blocks.DANDELION);

	private final String name;
	private final FlowerBlock flowerBlock;

	MoobloomVariant(
		String name,
		FlowerBlock flowerBlock
	) {
		this.name = name;
		this.flowerBlock = flowerBlock;
	}
}
