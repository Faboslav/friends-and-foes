package com.faboslav.friendsandfoes.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("deprecation")
public class OxidizableButtonBlock extends CopperButtonBlock implements Oxidizable
{
    public static final int EXPOSED_PRESS_TICKS = 7;
    public static final int WEATHERED_PRESS_TICKS = 4;
    public static final int OXIDIZED_PRESS_TICKS = 1;

    private final OxidationLevel OxidationLevel;

    public OxidizableButtonBlock(
            OxidationLevel OxidationLevel,
            Settings settings
    ) {
        super(settings);
        this.OxidationLevel = OxidationLevel;
    }

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
        ItemUsageContext itemUsageContext = new ItemUsageContext(player, hand, hit);

        if (itemInHand instanceof HoneycombItem || itemInHand instanceof AxeItem) {
            ActionResult itemInHandUsageResult = itemInHand.useOnBlock(itemUsageContext);

            if (itemInHandUsageResult.isAccepted()) {
                return itemInHandUsageResult;
            }
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }
}