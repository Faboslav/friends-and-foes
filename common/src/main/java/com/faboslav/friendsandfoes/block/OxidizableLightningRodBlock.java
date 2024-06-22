package com.faboslav.friendsandfoes.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.LightningRodBlock;
import net.minecraft.block.Oxidizable;

public final class OxidizableLightningRodBlock extends LightningRodBlock implements Oxidizable
{
	private final OxidationLevel oxidationLevel;

	public OxidizableLightningRodBlock(
		OxidationLevel oxidationLevel,
		Settings settings
	) {
		super(settings);
		this.oxidationLevel = oxidationLevel;
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent();
	}

	@Override
	public OxidationLevel getDegradationLevel() {
		return this.oxidationLevel;
	}
}