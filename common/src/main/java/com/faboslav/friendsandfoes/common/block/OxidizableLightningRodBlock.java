//? if <= 1.21.8 {
/*package com.faboslav.friendsandfoes.common.block;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.config.FriendsAndFoesConfig;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public final class OxidizableLightningRodBlock extends LightningRodBlock implements WeatheringCopper
{
	private final WeatherState oxidationLevel;

	public OxidizableLightningRodBlock(
		WeatherState oxidationLevel,
		Properties settings
	) {
		super(settings);
		this.oxidationLevel = oxidationLevel;
	}

	@Override
	public void randomTick(
		BlockState state,
		ServerLevel world,
		BlockPos pos,
		RandomSource random
	) {
		if(FriendsAndFoes.getConfig().enableLightningRodOxidation) {
			this.changeOverTime(state, world, pos, random);
		}
	}

	@Override
	public boolean isRandomlyTicking(BlockState blockState) {
		if(!FriendsAndFoes.getConfig().enableLightningRodOxidation) {
			return false;
		}

		return this.getAge().ordinal() < WeatherState.OXIDIZED.ordinal();
	}

	@Override
	public WeatherState getAge() {
		return this.oxidationLevel;
	}

	@Override
	public InteractionResult useWithoutItem(
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
*///?}