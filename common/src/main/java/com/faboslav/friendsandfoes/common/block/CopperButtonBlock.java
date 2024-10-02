package com.faboslav.friendsandfoes.common.block;

import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ButtonBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CopperButtonBlock extends ButtonBlock
{
	public CopperButtonBlock(Settings settings, int pressTicks) {
		super(BlockSetType.COPPER, pressTicks, settings);
	}

	@Override
	public SoundEvent getClickSound(boolean powered) {
		return super.getClickSound(powered);
	}

	@Override
	public ActionResult onUse(
		BlockState state,
		World world,
		BlockPos pos,
		PlayerEntity player,
		Hand hand,
		BlockHitResult hit
	) {
		var actionResult = OnUseOxidizable.onOxidizableUse(state, world, pos, player, hand, hit);

		if (actionResult.isAccepted()) {
			return actionResult;
		}

		return super.onUse(state, world, pos, player, hand, hit);
	}
}
