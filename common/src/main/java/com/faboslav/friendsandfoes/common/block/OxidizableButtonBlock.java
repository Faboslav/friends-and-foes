package com.faboslav.friendsandfoes.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

@SuppressWarnings("deprecation")
public final class OxidizableButtonBlock extends CopperButtonBlock implements FriendsAndFoesOxidizable
{
	private final WeatherState oxidationLevel;

	public OxidizableButtonBlock(
		WeatherState oxidationLevel,
		Properties settings,
		int pressTicks
	) {
		super(settings, pressTicks);
		this.oxidationLevel = oxidationLevel;
	}

	@Override
	public void randomTick(
		BlockState state,
		ServerLevel world,
		BlockPos pos,
		RandomSource random
	) {
		this.changeOverTime(state, world, pos, random);
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return FriendsAndFoesOxidizable.getNext(state.getBlock()).isPresent();
	}

	@Override
	public WeatherState getAge() {
		return this.oxidationLevel;
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