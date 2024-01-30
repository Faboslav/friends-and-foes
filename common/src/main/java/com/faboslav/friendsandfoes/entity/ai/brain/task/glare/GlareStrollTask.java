package com.faboslav.friendsandfoes.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.entity.GlareEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Map;

public class GlareStrollTask extends Task<GlareEntity>
{
	public GlareStrollTask() {
		super(
			Map.of(
				MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT
			)
		);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, GlareEntity glare) {
		return glare.getNavigation().isIdle();
	}

	@Override
	protected void run(ServerWorld world, GlareEntity glare, long time) {
		this.updateCachedPathHolder(glare);
		glare.getNavigation().startMovingAlong(glare.cachedPathHolder.cachedPath, 1);
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, GlareEntity glare, long time) {
		return glare.getNavigation().isFollowingPath();
	}

	public void updateCachedPathHolder(GlareEntity glare) {
		if (
			glare.cachedPathHolder.pathTimer > 20
			|| glare.cachedPathHolder.cachedPath == null
			|| (glare.getMovementSpeed() <= 0.05d && glare.cachedPathHolder.pathTimer > 5)
			|| glare.getBlockPos().getManhattanDistance(glare.cachedPathHolder.cachedPath.getTarget()) <= 4
		) {
			BlockPos.Mutable mutable = new BlockPos.Mutable().set(glare.getBlockPos());
			World world = glare.getWorld();
			int currentGroundBlockPosY = this.getGroundBlockPosition(glare).getY();
			int blockRange;
			boolean isSkyVisible = world.isSkyVisible(glare.getBlockPos());
			boolean isCloseToGround = Math.abs(currentGroundBlockPosY - glare.getY()) < 3;

			for (int attempt = 0; attempt < 10; attempt++) {
				blockRange = 12 - attempt;

				int x = glare.getBlockPos().getX() + glare.getRandom().nextBetween(-blockRange, blockRange);
				int y;
				int z = glare.getBlockPos().getZ() + glare.getRandom().nextBetween(-blockRange, blockRange);

				if (isSkyVisible) {
					if (isCloseToGround) {
						y = glare.getRandom().nextBetween(currentGroundBlockPosY, currentGroundBlockPosY + blockRange / 2);
					} else {
						y = glare.getRandom().nextBetween(currentGroundBlockPosY - blockRange / 4, currentGroundBlockPosY + blockRange / 4);
					}
				} else {
					if (isCloseToGround) {
						y = glare.getRandom().nextBetween(currentGroundBlockPosY, currentGroundBlockPosY + blockRange);
					} else {
						y = glare.getRandom().nextBetween(currentGroundBlockPosY - blockRange / 2, currentGroundBlockPosY + blockRange / 2);
					}
				}

				mutable.set(glare.getBlockPos()).set(x, y, z);

				if (world.getBlockState(mutable).isAir()) {
					break;
				}
			}

			Path newPath = glare.getNavigation().findPathTo(mutable, 1);

			glare.cachedPathHolder.cachedPath = newPath;
			glare.cachedPathHolder.pathTimer = 0;
		} else {
			glare.cachedPathHolder.pathTimer += 1;
		}
	}

	private BlockPos getGroundBlockPosition(GlareEntity glare) {
		World world = glare.getWorld();
		BlockPos.Mutable mutable = new BlockPos.Mutable().set(glare.getBlockPos());
		int worldBottomY = glare.getWorld().getBottomY();
		BlockState currentMutableBlockState = world.getBlockState(mutable);

		while (
			currentMutableBlockState.isAir()
			&& mutable.getY() > worldBottomY
		) {
			mutable.move(Direction.DOWN);
			currentMutableBlockState = world.getBlockState(mutable);
		}

		return mutable;
	}
}
