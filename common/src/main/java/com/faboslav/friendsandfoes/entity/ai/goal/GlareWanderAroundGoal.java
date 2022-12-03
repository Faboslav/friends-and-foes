package com.faboslav.friendsandfoes.entity.ai.goal;

import com.faboslav.friendsandfoes.entity.GlareEntity;
import com.faboslav.friendsandfoes.entity.ai.pathing.CachedPathHolder;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.EnumSet;

public final class GlareWanderAroundGoal extends Goal
{
	private final GlareEntity glare;
	private CachedPathHolder cachedPathHolder;

	public GlareWanderAroundGoal(
		GlareEntity glare
	) {
		this.glare = glare;
		this.setControls(EnumSet.of(
			Control.MOVE
		));
	}

	@Override
	public boolean canStart() {
		return this.glare.getNavigation().isIdle()
			   && (
				   this.glare.isTamed() == false
				   || this.glare.isLeashed()
			   )
			   && this.glare.getRandom().nextInt(10) == 0;
	}

	@Override
	public boolean shouldContinue() {
		return this.glare.getNavigation().isFollowingPath();
	}

	@Override
	public void start() {
		if (
			this.cachedPathHolder == null
			|| this.cachedPathHolder.pathTimer > 50
			|| this.cachedPathHolder.cachedPath == null
			|| (this.glare.getMovementSpeed() <= 0.05d && this.cachedPathHolder.pathTimer > 5)
			|| this.glare.getBlockPos().getManhattanDistance(this.cachedPathHolder.cachedPath.getTarget()) <= 4
		) {
			BlockPos.Mutable mutable = new BlockPos.Mutable().set(glare.getBlockPos());
			World world = glare.getWorld();
			int currentGroundBlockPosY = this.getGroundBlockPosition().getY();
			int blockRange;

			for (int attempt = 0; attempt < 10; attempt++) {
				blockRange = 12 - attempt;

				mutable.set(glare.getBlockPos()).set(
					this.glare.getBlockPos().getX() + this.glare.getRandom().nextBetween(-blockRange, blockRange),
					this.glare.getRandom().nextBetween(currentGroundBlockPosY - blockRange, currentGroundBlockPosY + blockRange),
					this.glare.getBlockPos().getZ() + this.glare.getRandom().nextBetween(-blockRange, blockRange)
				);

				if (world.getBlockState(mutable).isAir()) {
					break;
				}
			}

			Path newPath = glare.getNavigation().findPathTo(mutable, 1);
			glare.getNavigation().startMovingAlong(newPath, 1);

			if (this.cachedPathHolder == null) {
				this.cachedPathHolder = new CachedPathHolder();
			}

			this.cachedPathHolder.cachedPath = newPath;
			this.cachedPathHolder.pathTimer = 0;
		} else {
			glare.getNavigation().startMovingAlong(this.cachedPathHolder.cachedPath, 1);
			this.cachedPathHolder.pathTimer += 1;
		}
	}

	private BlockPos getGroundBlockPosition() {
		World world = this.glare.getWorld();
		BlockPos.Mutable mutable = new BlockPos.Mutable().set(this.glare.getBlockPos());
		int worldBottomY = this.glare.getWorld().getBottomY();
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