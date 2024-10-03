package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.block.FriendsAndFoesOxidizable;
import net.minecraft.block.BlockState;
import net.minecraft.block.LightningRodBlock;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.RodBlock;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(value = LightningRodBlock.class, priority = 1002)
public abstract class LightningRodBlockOxidizableMixin extends RodBlock implements Oxidizable
{
	public LightningRodBlockOxidizableMixin(Settings settings) {
		super(settings);
	}

	@Override
	public Optional<BlockState> getDegradationResult(BlockState state) {
		return FriendsAndFoesOxidizable.getIncreasedOxidationBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
	}
}