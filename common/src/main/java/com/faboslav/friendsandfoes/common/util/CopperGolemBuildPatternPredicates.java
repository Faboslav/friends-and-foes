package com.faboslav.friendsandfoes.common.util;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import java.util.function.Predicate;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;

public final class CopperGolemBuildPatternPredicates
{
	public static final Predicate<BlockState> IS_COPPER_GOLEM_LIGHTNING_ROD_PREDICATE = state -> state != null && (
		state == Blocks.LIGHTNING_ROD.defaultBlockState().setValue(LightningRodBlock.FACING, Direction.UP)
		//? if <= 1.21.8 {
		/*|| state == FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get().defaultBlockState().setValue(LightningRodBlock.FACING, Direction.UP)
		|| state == FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get().defaultBlockState().setValue(LightningRodBlock.FACING, Direction.UP)
		|| state == FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD.get().defaultBlockState().setValue(LightningRodBlock.FACING, Direction.UP)
		|| state == FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get().defaultBlockState().setValue(LightningRodBlock.FACING, Direction.UP)
		|| state == FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get().defaultBlockState().setValue(LightningRodBlock.FACING, Direction.UP)
		|| state == FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get().defaultBlockState().setValue(LightningRodBlock.FACING, Direction.UP)
		|| state == FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get().defaultBlockState().setValue(LightningRodBlock.FACING, Direction.UP)
		*///?} else {
		|| state == Blocks.WEATHERED_LIGHTNING_ROD.defaultBlockState().setValue(LightningRodBlock.FACING, Direction.UP)
		|| state == Blocks.EXPOSED_LIGHTNING_ROD.defaultBlockState().setValue(LightningRodBlock.FACING, Direction.UP)
		|| state == Blocks.OXIDIZED_LIGHTNING_ROD.defaultBlockState().setValue(LightningRodBlock.FACING, Direction.UP)
		|| state == Blocks.WAXED_LIGHTNING_ROD.defaultBlockState().setValue(LightningRodBlock.FACING, Direction.UP)
		|| state == Blocks.WAXED_WEATHERED_LIGHTNING_ROD.defaultBlockState().setValue(LightningRodBlock.FACING, Direction.UP)
		|| state == Blocks.WAXED_EXPOSED_LIGHTNING_ROD.defaultBlockState().setValue(LightningRodBlock.FACING, Direction.UP)
		|| state == Blocks.WAXED_OXIDIZED_LIGHTNING_ROD.defaultBlockState().setValue(LightningRodBlock.FACING, Direction.UP)
		//?}
	);

	public static final Predicate<BlockState> IS_GOLEM_HEAD_PREDICATE = state -> state != null && (
		state.is(Blocks.CARVED_PUMPKIN)
		|| state.is(Blocks.JACK_O_LANTERN)
	);

	public static final Predicate<BlockState> IS_COPPER_GOLEM_BODY_PREDICATE = state -> state != null && (
		state.is(Blocks.COPPER_BLOCK)
		|| state.is(Blocks.WEATHERED_COPPER)
		|| state.is(Blocks.EXPOSED_COPPER)
		|| state.is(Blocks.OXIDIZED_COPPER)
		|| state.is(Blocks.WAXED_COPPER_BLOCK)
		|| state.is(Blocks.WAXED_WEATHERED_COPPER)
		|| state.is(Blocks.WAXED_EXPOSED_COPPER)
		|| state.is(Blocks.WAXED_OXIDIZED_COPPER)
	);
}
