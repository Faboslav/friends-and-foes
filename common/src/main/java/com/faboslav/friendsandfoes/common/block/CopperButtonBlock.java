package com.faboslav.friendsandfoes.common.block;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CopperButtonBlock extends AbstractButtonBlock
{
	public static final int PRESS_TICKS = 10;

	public CopperButtonBlock(Settings settings) {
		super(false, settings);
	}

	@Override
	public SoundEvent getClickSound(boolean powered) {
		return powered ? SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON:SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF;
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
