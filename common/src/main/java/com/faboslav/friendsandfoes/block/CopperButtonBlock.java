package com.faboslav.friendsandfoes.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ButtonBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CopperButtonBlock extends ButtonBlock
{
	public CopperButtonBlock(Settings settings, int pressTicks) {
		super(settings, pressTicks, false, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON);
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
		ItemStack itemStack = player.getStackInHand(hand);
		Item itemInHand = itemStack.getItem();

		if (itemInHand instanceof AxeItem) {
			ItemUsageContext itemUsageContext = new ItemUsageContext(player, hand, hit);
			ActionResult itemInHandUsageResult = itemInHand.useOnBlock(itemUsageContext);

			if (itemInHandUsageResult.isAccepted()) {
				return itemInHandUsageResult;
			}
		}

		return super.onUse(state, world, pos, player, hand, hit);
	}
}
