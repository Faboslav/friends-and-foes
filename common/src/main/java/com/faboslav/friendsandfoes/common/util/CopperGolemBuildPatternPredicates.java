package com.faboslav.friendsandfoes.common.util;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LightningRodBlock;
import net.minecraft.util.math.Direction;

import java.util.function.Predicate;

public final class CopperGolemBuildPatternPredicates
{
	public static final Predicate<BlockState> IS_COPPER_GOLEM_LIGHTNING_ROD_PREDICATE = state -> state != null && (
		state == Blocks.LIGHTNING_ROD.getDefaultState().with(LightningRodBlock.FACING, Direction.UP)
		|| state == FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get().getDefaultState().with(LightningRodBlock.FACING, Direction.UP)
		|| state == FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get().getDefaultState().with(LightningRodBlock.FACING, Direction.UP)
		|| state == FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD.get().getDefaultState().with(LightningRodBlock.FACING, Direction.UP)
		|| state == FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get().getDefaultState().with(LightningRodBlock.FACING, Direction.UP)
		|| state == FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get().getDefaultState().with(LightningRodBlock.FACING, Direction.UP)
		|| state == FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get().getDefaultState().with(LightningRodBlock.FACING, Direction.UP)
		|| state == FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get().getDefaultState().with(LightningRodBlock.FACING, Direction.UP)
	);

	public static final Predicate<BlockState> IS_GOLEM_HEAD_PREDICATE = state -> state != null && (
		state.isOf(Blocks.CARVED_PUMPKIN)
		|| state.isOf(Blocks.JACK_O_LANTERN)
	);

	public static final Predicate<BlockState> IS_COPPER_GOLEM_BODY_PREDICATE = state -> state != null && (
		state.isOf(Blocks.COPPER_BLOCK)
		|| state.isOf(Blocks.WEATHERED_COPPER)
		|| state.isOf(Blocks.EXPOSED_COPPER)
		|| state.isOf(Blocks.OXIDIZED_COPPER)
		|| state.isOf(Blocks.WAXED_COPPER_BLOCK)
		|| state.isOf(Blocks.WAXED_WEATHERED_COPPER)
		|| state.isOf(Blocks.WAXED_EXPOSED_COPPER)
		|| state.isOf(Blocks.WAXED_OXIDIZED_COPPER)
	);
}
