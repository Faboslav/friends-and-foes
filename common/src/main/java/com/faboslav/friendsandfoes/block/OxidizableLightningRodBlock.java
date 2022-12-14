package com.faboslav.friendsandfoes.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.LightningRodBlock;

public final class OxidizableLightningRodBlock extends LightningRodBlock implements Oxidizable
{
	private final OxidationLevel OxidationLevel;

	public OxidizableLightningRodBlock(
		OxidationLevel OxidationLevel,
		Settings settings
	) {
		super(settings);
		this.OxidationLevel = OxidationLevel;
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent();
	}

	@Override
	public OxidationLevel getDegradationLevel() {
		return this.OxidationLevel;
	}
}