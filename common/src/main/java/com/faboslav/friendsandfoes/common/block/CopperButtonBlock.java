package com.faboslav.friendsandfoes.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.phys.BlockHitResult;

public class CopperButtonBlock extends ButtonBlock
{
	public CopperButtonBlock(Properties settings, int pressTicks) {
		super(BlockSetType.COPPER, pressTicks, settings);
	}

	@Override
	public SoundEvent getSound(boolean powered) {
		return super.getSound(powered);
	}

	@Override
	protected InteractionResult useWithoutItem(
		BlockState state,
		Level world,
		BlockPos pos,
		Player player,
		BlockHitResult hit
	) {
		var actionResult = OnUseOxidizable.onOxidizableUse(state, world, pos, player, hit);

		if (actionResult.consumesAction()) {
			return actionResult;
		}

		return super.useWithoutItem(state, world, pos, player, hit);
	}
}
