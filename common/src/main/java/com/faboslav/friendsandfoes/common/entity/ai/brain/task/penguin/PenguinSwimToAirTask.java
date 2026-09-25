package com.faboslav.friendsandfoes.common.entity.ai.brain.task.penguin;

import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;

import java.util.Map;

public final class PenguinSwimToAirTask extends Behavior<PenguinEntity>
{
	private static final int LOW_AIR_SUPPLY_THRESHOLD = 140;
	private static final double SWIM_TO_AIR_SPEED = 1.0D;

	public PenguinSwimToAirTask() {
		super(Map.of());
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, PenguinEntity penguin) {
		return penguin.getAirSupply() < LOW_AIR_SUPPLY_THRESHOLD;
	}

	@Override
	protected boolean canStillUse(ServerLevel world, PenguinEntity penguin, long time) {
		return this.checkExtraStartConditions(world, penguin);
	}

	@Override
	protected void start(ServerLevel world, PenguinEntity penguin, long time) {
		this.swimTowardsAir(penguin);
	}

	@Override
	protected void tick(ServerLevel world, PenguinEntity penguin, long time) {
		this.swimTowardsAir(penguin);
	}

	private void swimTowardsAir(PenguinEntity penguin) {
		BlockPos airPos = this.findAirPosition(penguin);
		penguin.getNavigation().moveTo(airPos.getX(), airPos.getY() + 1, airPos.getZ(), SWIM_TO_AIR_SPEED);
	}

	private BlockPos findAirPosition(PenguinEntity penguin) {
		int x = Mth.floor(penguin.getX());
		int y = Mth.floor(penguin.getY());
		int z = Mth.floor(penguin.getZ());
		var world = penguin.level();

		for (BlockPos pos : BlockPos.betweenClosed(x - 1, y, z - 1, x + 1, y + 8, z + 1)) {
			if (this.givesAir(world, pos)) {
				return pos.immutable();
			}
		}

		return BlockPos.containing(penguin.getX(), penguin.getY() + 8.0D, penguin.getZ());
	}

	private boolean givesAir(LevelReader world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		//? if >= 1.20.6 {
		boolean isPathfindable = state.isPathfindable(PathComputationType.LAND);
		//?} else {
		/*boolean isPathfindable = state.isPathfindable(world, pos, PathComputationType.LAND);
		*///?}

		return (world.getFluidState(pos).isEmpty() || state.is(Blocks.BUBBLE_COLUMN)) && isPathfindable;
	}
}
