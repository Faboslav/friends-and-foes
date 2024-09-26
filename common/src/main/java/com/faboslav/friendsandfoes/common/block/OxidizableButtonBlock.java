package com.faboslav.friendsandfoes.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public final class OxidizableButtonBlock extends CopperButtonBlock implements Oxidizable
{
	private final OxidationLevel oxidationLevel;

	public OxidizableButtonBlock(
		OxidationLevel oxidationLevel,
		Settings settings,
		int pressTicks
	) {
		super(settings, pressTicks);
		this.oxidationLevel = oxidationLevel;
	}

	@Override
	public void randomTick(
		BlockState state,
		ServerWorld world,
		BlockPos pos,
		Random random
	) {
		this.tickDegradation(state, world, pos, random);
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent();
	}

	@Override
	public OxidationLevel getDegradationLevel() {
		return this.oxidationLevel;
	}

	@Override
	protected ActionResult onUse(
		BlockState state,
		World world,
		BlockPos pos,
		PlayerEntity player,
		BlockHitResult hit
	) {
		Hand hand = Hand.MAIN_HAND;
		ItemStack itemStack = player.getStackInHand(hand);
		Item itemInHand = itemStack.getItem();
		ItemUsageContext itemUsageContext = new ItemUsageContext(player, hand, hit);

		if (itemInHand instanceof HoneycombItem || itemInHand instanceof AxeItem) {
			ActionResult itemInHandUsageResult = itemInHand.useOnBlock(itemUsageContext);

			if (itemInHandUsageResult.isAccepted()) {
				return itemInHandUsageResult;
			}
		}

		return super.onUse(state, world, pos, player, hit);
	}
}