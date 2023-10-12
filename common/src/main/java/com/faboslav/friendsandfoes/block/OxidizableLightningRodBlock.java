package com.faboslav.friendsandfoes.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.LightningRodBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

@SuppressWarnings("deprecation")
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
	public void randomTick(
		BlockState state,
		ServerWorld world,
		BlockPos pos,
		Random random
	) {
		super.randomTick(state, world, pos, random);
		this.tickDegradation(state, world, pos, random);
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent();
	}

	public OxidationLevel getDegradationLevel() {
		return this.OxidationLevel;
	}
}