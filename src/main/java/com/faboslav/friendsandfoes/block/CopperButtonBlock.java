package com.faboslav.friendsandfoes.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BlockState;
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

public class CopperButtonBlock extends AbstractButtonBlock
{
    public static final int PRESS_TICKS = 10;

    public CopperButtonBlock(AbstractBlock.Settings settings) {
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
