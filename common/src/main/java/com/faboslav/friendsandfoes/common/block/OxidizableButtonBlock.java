package com.faboslav.friendsandfoes.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

//? if < 1.21.1 {
/*import net.minecraft.world.InteractionHand;
*///?}

@SuppressWarnings("deprecation")
public final class OxidizableButtonBlock extends CopperButtonBlock implements FriendsAndFoesOxidizable
{
	private final WeatherState oxidationLevel;

	public OxidizableButtonBlock(
		WeatherState oxidationLevel,
		int pressTicks,
		Properties properties
	) {
		super(pressTicks, properties);
		this.oxidationLevel = oxidationLevel;
	}

	@Override
	public void randomTick(
		BlockState state,
		ServerLevel world,
		BlockPos pos,
		RandomSource random
	) {
		//? if >= 1.21.1 {
		this.changeOverTime(state, world, pos, random);
		//?} else {
		/*this.applyChangeOverTime(state, world, pos, random);
		*///?}
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return this.getAge().ordinal() < WeatherState.OXIDIZED.ordinal();
	}

	@Override
	public WeatherState getAge() {
		return this.oxidationLevel;
	}

	@Override
	//? if >= 1.21.1 {
	protected InteractionResult useWithoutItem(
	//?} else {
	/*public InteractionResult use(
	*///?}
		BlockState state,
		Level world,
		BlockPos pos,
		Player player,
		//? if < 1.21.1 {
		/*InteractionHand hand,
		*///?}
		BlockHitResult hit
	) {
		var actionResult = OnUseOxidizable.onOxidizableUse(state, world, pos, player, hit);

		if (actionResult.consumesAction()) {
			return actionResult;
		}

		//? if >= 1.21.1 {
		return super.useWithoutItem(state, world, pos, player, hit);
		//?} else {
		/*return super.use(state, world, pos, player, hand, hit);
		*///?}
	}
}